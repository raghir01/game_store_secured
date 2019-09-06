package com.company.gamestore.dao;

import com.company.gamestore.model.Invoice;

import java.util.List;

public interface InvoiceDao {

    Invoice addInvoice(Invoice invoice);

    List<Invoice> getAllInvoices();

    void deleteInvoice(int invoiceId);
}
