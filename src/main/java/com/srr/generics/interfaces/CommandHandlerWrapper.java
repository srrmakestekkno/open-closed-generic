package com.srr.generics.interfaces;

import java.util.concurrent.ExecutorService;

public interface CommandHandlerWrapper {
    void handleAsync(Command command, ExecutorService executor);
}
