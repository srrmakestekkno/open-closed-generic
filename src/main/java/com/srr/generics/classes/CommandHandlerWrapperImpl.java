package com.srr.generics.classes;

import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandHandler;

import java.util.List;

public class CommandHandlerWrapper<T extends Command> implements CommandHandlerWrapper {
    private final List<CommandHandler<T>> handlers;

    public CommandHandlerWrapper(List<CommandHandler<T>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handleAsync(Command command) {
        T typedCommand = (T) command;
        for (CommandHandler<T> handler : handlers) {
            handler.handleAsync(typedCommand);
        }
    }
}
