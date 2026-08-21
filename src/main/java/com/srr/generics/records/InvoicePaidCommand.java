package com.srr.generics.records;

import com.srr.generics.interfaces.Command;

public record InvoicePaidCommand (String invoiceId, double amount) implements Command {}