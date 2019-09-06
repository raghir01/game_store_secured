package com.company.gamestore.dao;

import com.company.gamestore.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceDaoJdbcTemplateImpl implements InvoiceDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InvoiceDaoJdbcTemplateImpl(JdbcTemplate newjdbcTemplate){
        this.jdbcTemplate = newjdbcTemplate;
    }

    //Prepared Statement
    private static final String INSERT_INVOICE_SQL =
            "insert into invoice (name, street, city, state, zipcode, item_type, item_id, unit_price, quantity, subtotal," +
                    "tax, processing_fee, total) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_INVOICE_SQL =
            "select * from invoice";

    private static final String DELETE_INVOICE_SQL =
            "delete from invoice where invoice_id = ?";

    @Override
    @Transactional
    public Invoice addInvoice(Invoice invoice) {
        jdbcTemplate.update(
                INSERT_INVOICE_SQL,
                invoice.getName(),
                invoice.getStreet(),
                invoice.getCity(),
                invoice.getState(),
                invoice.getZipcode(),
                invoice.getItemType(),
                invoice.getItemId(),
                invoice.getUnitPrice(),
                invoice.getQuantity(),
                invoice.getSubtotal(),
                invoice.getTax(),
                invoice.getProcessingFee(),
                invoice.getTotal());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        invoice.setInvoiceId(id);
        return invoice;
    }

    private Invoice mapRowToInvoice(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(rs.getInt("invoice_id"));
        invoice.setName(rs.getString("name"));
        invoice.setStreet(rs.getString("street"));
        invoice.setCity(rs.getString("city"));
        invoice.setState(rs.getString("state"));
        invoice.setZipcode(rs.getString("zipcode"));
        invoice.setItemType(rs.getString("item_type"));
        invoice.setItemId(rs.getInt("item_id"));
        invoice.setUnitPrice(rs.getBigDecimal("unit_price"));
        invoice.setQuantity(rs.getInt("quantity"));
        invoice.setSubtotal(rs.getBigDecimal("subtotal"));
        invoice.setTax(rs.getBigDecimal("tax"));
        invoice.setProcessingFee(rs.getBigDecimal("processing_fee"));
        invoice.setTotal(rs.getBigDecimal("total"));

        return invoice;
    }


    @Override
    public List<Invoice> getAllInvoices() {
        return jdbcTemplate.query(SELECT_ALL_INVOICE_SQL, this::mapRowToInvoice);
    }

    @Override
    public void deleteInvoice(int invoiceId) {
        jdbcTemplate.update(DELETE_INVOICE_SQL, invoiceId);

    }
}
