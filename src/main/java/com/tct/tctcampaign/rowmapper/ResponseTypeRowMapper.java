package com.tct.tctcampaign.rowmapper;

import com.tct.tctcampaign.model.db.CategoryDao;
import com.tct.tctcampaign.model.db.ResponseTypeDao;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ResponseTypeRowMapper implements RowMapper<ResponseTypeDao> {

    @Override
    public ResponseTypeDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        ResponseTypeDao obj = new ResponseTypeDao();
        if(Objects.nonNull(obj)){
            obj.setResponseId(rs.getInt("RESPONSETYPE_ID"));
            obj.setResponseDesc(rs.getString("RESPONSETYPE_DESC"));
            obj.setCreatedBy(rs.getString("CREATED_BY"));
            obj.setChangedBy(rs.getString("CHANGED_BY"));
            obj.setApprovedBy(rs.getString("APPROVED_BY"));
            obj.setStatusDesc(rs.getString("STATUS_DESC"));
            obj.setCreatedDate(rs.getDate("CREATED_DATE"));
            obj.setChangedDate(rs.getDate("CHANGED_DATE"));
            obj.setApprovedDate(rs.getDate("APPROVED_DATE"));
            obj.setComments(rs.getString("COMMENTS"));
        }
        return obj;
    }
}
