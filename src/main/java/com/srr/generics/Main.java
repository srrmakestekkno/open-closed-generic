package com.srr.generics;

import com.srr.generics.classes.CommandDispatcherImpl;
import com.srr.generics.classes.SendEmailInvoiceHandler;
import com.srr.generics.classes.UpdateAccountingHandler;
import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandDispatcher;
import com.srr.generics.interfaces.CommandHandler;
import com.srr.generics.records.InvoicePaidCommand;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        System.out.println("Starting integrations service...");

        // create dispatcher
        CommandDispatcherImpl dispatcher = new CommandDispatcherImpl();

        // register handlers in dispatcher (should bne automated somewhere)
        List<CommandHandler<InvoicePaidCommand>> handlers = List.of(
                new SendEmailInvoiceHandler(),
                new UpdateAccountingHandler()
        );
        dispatcher.registerHandlers(InvoicePaidCommand.class, handlers);

        // create test command
        InvoicePaidCommand command = new InvoicePaidCommand("INV-2026-001", 14500.00);

        // send the command
        System.out.println("Sending command to dispatcher...");
        dispatcher.dispatchAsync(command);
        System.out.println("Command sent. (main thread continues");

        dispatcher.shutdown();
    }
}
