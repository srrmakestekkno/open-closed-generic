package com.srr.generics.classes;

import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandHandler;
import com.srr.generics.interfaces.CommandHandlerWrapper;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommandHandlerWrapperImpl<T extends Command> implements CommandHandlerWrapper {
    private final List<CommandHandler<T>> handlers;

    public CommandHandlerWrapperImpl(List<CommandHandler<T>> handlers) {
        this.handlers = handlers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleAsync(Command command, ExecutorService executor) {
        // Safe cast: Dispatcher guarantees match between Command and Wrapper
        T typedCommand = (T) command;

        // each handler is sent to their own virtual thread
        for (CommandHandler<T> handler : handlers) {
            executor.submit(() -> handler.handle(typedCommand));
        }
    }
}
