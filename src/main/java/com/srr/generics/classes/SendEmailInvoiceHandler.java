package com.srr.generics.classes;

import com.srr.generics.interfaces.Command;
import com.srr.generics.interfaces.CommandHandler;
import com.srr.generics.records.InvoicePaidCommand;

public class SendEmailInvoiceHandler implements CommandHandler<InvoicePaidCommand> {
    @Override
    public void handleAsync(InvoicePaidCommand command) {
        System.out.println("[Handler 1] Starts email confirmation for invoice: " + command.invoiceId());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Handler 1] Email sent for " + command.invoiceId());
    }
}
