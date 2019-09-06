package com.company.gamestore.dao;

import com.company.gamestore.model.SalesTaxRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SalesTaxRateDaoJdbcTemplateImpl implements SalesTaxRateDao{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public SalesTaxRateDaoJdbcTemplateImpl(JdbcTemplate newjdbcTemplate){
        this.jdbcTemplate = newjdbcTemplate;
    }

    //Prepared Statement
    private static final String SELECT_TAXRATE_BY_STATE_SQL =
            "select * from sales_tax_rate where state = ?";

    private SalesTaxRate mapRowToSalesTaxRate(ResultSet rs, int rowNum) throws SQLException {
        SalesTaxRate salesTaxRate = new SalesTaxRate();
        salesTaxRate.setState(rs.getString("state"));
        salesTaxRate.setRate(rs.getBigDecimal("rate"));

        return salesTaxRate;
    }

    @Override
    public SalesTaxRate getTaxByState(String state) {
        return jdbcTemplate.queryForObject(SELECT_TAXRATE_BY_STATE_SQL, this::mapRowToSalesTaxRate, state);
    }
}
