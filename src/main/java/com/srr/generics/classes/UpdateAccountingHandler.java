package com.srr.generics.classes;

import com.srr.generics.interfaces.CommandHandler;
import com.srr.generics.records.InvoicePaidCommand;

public class UpdateAccountingHandler implements CommandHandler<InvoicePaidCommand> {
    @Override
    public void handleAsync(InvoicePaidCommand command) {
        System.out.println("[Handler 2] Updates accounting for amount: " + command.amount());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[Handler 2] Accounting updated for " + command.invoiceId());
    }
}
