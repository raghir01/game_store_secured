package com.company.gamestore.dao;

import com.company.gamestore.model.ProcessingFee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ProcessingFeeDaoJdbcTemplateImpl implements ProcessingFeeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ProcessingFeeDaoJdbcTemplateImpl(JdbcTemplate newjdbcTemplate){
        this.jdbcTemplate = newjdbcTemplate;
    }

    //Prepared statement
    private static final String SELECT_PROCESSINGFEE_BY_ITEMTYPE_SQL =
            "select * from processing_fee where product_type= ?";

    private ProcessingFee mapRowToProcessngFee(ResultSet rs, int rowNum) throws SQLException {
        ProcessingFee processingFee = new ProcessingFee();
        processingFee.setProductType(rs.getString("product_type"));
        processingFee.setFee(rs.getBigDecimal("fee"));

        return processingFee;
    }

    @Override
    public ProcessingFee getProcessingFeeByItemType(String itemType) {

        try {
            return jdbcTemplate.queryForObject(SELECT_PROCESSINGFEE_BY_ITEMTYPE_SQL, this::mapRowToProcessngFee, itemType);
        }catch (EmptyResultDataAccessException e){
            // if there is no match for this album id, return null
            return null;
        }
    }
}
