package com.srr.generics.classes;

import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandDispatcher implements CommandDispatcher {

    // Trådsikkert register som holder på wrapperne per kommando-klasse
    private final Map<Class<? extends Command>, CommandHandlerWrapper> wrappers = new ConcurrentHashMap<>();

    // Bruker Java Virtual Threads for asynkron kjøring (tilsvarer Task/async/await)
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    // Registrerer handlere (Erstatter funksjonaliteten til ActivatorUtilities/DI-oppslag)
    public <T extends Command> void registerHandlers(Class<T> commandType, List<CommandHandler<T>> handlers) {
        wrappers.put(commandType, new CommandHandlerWrapper<>(handlers));
    }

    @Override
    public void dispatchAsync(Command command) {
        CommandHandlerWrapper wrapper = wrappers.get(command.getClass());

        if (wrapper == null) {
            throw new IllegalArgumentException("Ingen handlere registrert for: " + command.getClass().getSimpleName());
        }

        // Kjører wrapperen asynkront på en virtuell tråd (tilsvarer await i C#)
        executor.submit(() -> wrapper.handleAsync(command));
    }

    // Metode for å rydde opp tråd-poolen ved avslutning
    public void shutdown() {
        executor.shutdown();
    }
}
