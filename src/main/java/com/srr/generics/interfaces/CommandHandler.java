package com.srr.generics.interfaces;

public interface CommandHandler<T extends Command> {
    void handle(T command); // Hold denne synkron, Virtual Threads gjør den asynkron!
}
