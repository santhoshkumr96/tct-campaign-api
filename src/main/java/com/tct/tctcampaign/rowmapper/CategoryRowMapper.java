package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.CategoryDao;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CategoryRowMapper implements RowMapper<CategoryDao> {
    @Override
    public CategoryDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        CategoryDao obj = new CategoryDao();
        if(Objects.nonNull(obj)){
            obj.setCategoryId(rs.getInt("CATEGORY_ID"));
            obj.setCategoryDesc(rs.getString("CATEGORY_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
            obj.setCategoryDescTa(rs.getString("CATEGORY_DESC_TA"));
        }
        return obj;
    }
}
