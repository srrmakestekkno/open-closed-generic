package com.srr.generics.classes;

import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandDispatcher;
import com.srr.generics.interfaces.CommandHandler;
import com.srr.generics.interfaces.CommandHandlerWrapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandDispatcherImpl implements CommandDispatcher {
    private final Map<Class<? extends Command>, CommandHandlerWrapper> wrappers = new ConcurrentHashMap<>();

    // uses java virtual threads for real paralell async
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    // register handlers
    public <T extends Command> void registerHandlers(Class<T> commandType, List<CommandHandler<T>> handlers) {
        wrappers.put(commandType, new CommandHandlerWrapperImpl<>(handlers));
    }

    @Override
    public void dispatchAsync(Command command) {
        CommandHandlerWrapper wrapper = wrappers.get(command.getClass());

        if (wrapper == null) {
            throw new IllegalArgumentException("Ingen handlere registrert for: " + command.getClass().getSimpleName());
        }

        // send executor so wrapper can spin up a thread per handler
        wrapper.handleAsync(command, executor);
    }

    // clean opp thread-pool when finish
    public void shutdown() {
        executor.shutdown();
    }
}
