package com.tct.tctcampaign.repo;

import com.tct.tctcampaign.model.db.Question;
import com.tct.tctcampaign.rowmapper.QuestionRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;
import java.util.List;

@Repository
public class QuestionRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Question> findAll(){
        String query = "select  * from tbl_m_questions_repo where status_desc != 'DELETED'";
        List<Question> questions = jdbcTemplate.query(query, new QuestionRowMapper());
        return questions;
    };

    public List<Question> findAllApproved(){
        String query = "select  * from tbl_m_questions_repo where status_desc = 'APPROVED'";
        List<Question> questions = jdbcTemplate.query(query, new QuestionRowMapper());
        return questions;
    };

    public Question findByQuestionId(Integer questionId){
        String query = "select  * from tbl_m_questions_repo where question_id = "+questionId;;
        List<Question> questions = jdbcTemplate.query(query, new QuestionRowMapper());
        if(questions.size() > 0){
            return questions.get(0);
        }
        return null;
    };

    public List<String> searchByQuestionName(String question_name){
        String query = "select question_name from tbl_m_questions_repo where status_desc != 'DELETED' and question_name like '%"+question_name+"%'";
        List<String> questions = jdbcTemplate.queryForList(query,String.class);
        return questions;
    };

    public List<String> searchApprovedQuestionByNameByQuestionName(String question_name){
        String query = "select question_name from tbl_m_questions_repo where status_desc = 'APPROVED' and " +
                "question_name like '%"+question_name+"%'";
        List<String> questions = jdbcTemplate.queryForList(query,String.class);
        return questions;
    };

    public List<Question> deleteByQuestionId(Integer questionId , String user){
        String query = "update tbl_m_questions_repo set status_desc = ? , changed_by = ? , changed_date = ? where question_id = ?";
        jdbcTemplate.update(query,"DELETED",user, new Date(),  questionId);
        return findAll();
    };

    public List<Question> searchByQuestionNameWhole(String question_name){
        String query = "select * from tbl_m_questions_repo where question_name = '"+question_name+"'";
        List<Question> questions = jdbcTemplate.query(query,new QuestionRowMapper());
        return questions;
    };

    public Question insert(Question question) {
        String query = "insert into tbl_m_questions_repo (" +
                "question_name," +
                "question_desc," +
                "responsetype_id," +
                "category_id," +
                "created_by," +
                "changed_by," +
                "approved_by," +
                "status_desc," +
                "created_date," +
                "changed_date," +
                "approved_date," +
                "comments," +
                "question_name_ta," +
                "question_desc_ta," +
                "response_type ) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,question.getQuestionName());
                ps.setString(2,question.getQuestionDesc());
                ps.setInt(3,question.getResponseId());
                ps.setInt(4,question.getCategoryId());
                ps.setString(5,question.getCreatedBy());
                ps.setString(6,question.getChangedBy());
                ps.setString(7,question.getApprovedBy());
                ps.setString(8,question.getStatusDesc());
                ps.setTimestamp(9, new java.sql.Timestamp(question.getCreatedDate().getTime()));
                ps.setTimestamp(10,new java.sql.Timestamp(question.getChangedDate().getTime()));
                ps.setNull(11, Types.NULL);
                ps.setString(12,question.getComments());
                ps.setString(13,"");
                ps.setString(14,"");
                ps.setString(15,question.getResponseType());
            return ps;
            }
        }, holder);

        question.setQuestionId((int) holder.getKey().longValue());

        return question;
    };

    public Question save(Question question) {
        String query = "update tbl_m_questions_repo set " +
                "question_name = ?," +
                "question_desc = ?," +
                "responsetype_id = ?," +
                "category_id = ?," +
                "created_by = ?," +
                "changed_by = ?," +
                "approved_by = ?," +
                "status_desc = ?," +
                "created_date = ?," +
                "changed_date = ?," +
                "approved_date = ?," +
                "comments = ?," +
                "question_name_ta = ?," +
                "question_desc_ta = ?," +
                "response_type = ? " +
                "where question_id = ?";
        jdbcTemplate.update(query,
                question.getQuestionName(),
                question.getQuestionDesc(),
                question.getResponseId(),
                question.getCategoryId(),
                question.getCreatedBy(),
                question.getChangedBy(),
                question.getApprovedBy(),
                question.getStatusDesc(),
                question.getCreatedDate(),
                question.getChangedDate(),
                question.getApprovedDate(),
                question.getComments(),
                "",
                "",
                question.getResponseType(),
                question.getQuestionId()
                );
        return question;
    }
}
