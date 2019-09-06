package com.company.gamestore.dao;

import com.company.gamestore.model.ProcessingFee;

public interface ProcessingFeeDao {

    ProcessingFee getProcessingFeeByItemType(String itemType);
}
