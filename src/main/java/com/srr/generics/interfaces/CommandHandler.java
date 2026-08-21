package com.srr.generics.interfaces;

public interface CommandHandler<T extends Command> {
    void handle(T command); // Virtual Threads makes this async
}
