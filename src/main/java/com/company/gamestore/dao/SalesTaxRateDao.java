package com.company.gamestore.dao;

import com.company.gamestore.model.SalesTaxRate;

public interface SalesTaxRateDao {
    SalesTaxRate getTaxByState(String state);
}
