/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.dao;

import com.kx.guessinggame.dto.Round;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author khuxi
 */
@Repository
public class RoundDaoImpl implements RoundDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoundDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Round add(Round round) {

        final String sql = "INSERT INTO rounds(gameId, roundId, round, guess, partial, exact, stampDate, stampTime) VALUES(?,?,?,?,?,?,?,?);";

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql);
            statement.setInt(1, round.getGameId());
            statement.setString(2, round.getRoundId());
            statement.setInt(3, round.getRound());
            statement.setString(4, round.getGuess());
            statement.setInt(5, round.getPartial());
            statement.setInt(6, round.getExact());
            statement.setDate(7, Date.valueOf(round.getStampDate()));
            statement.setTime(8, Time.valueOf(round.getStampTime()));
            return statement;

        });

        return round;
    }

    @Override
    public List<Round> getAll(int gameId) {
        final String SELECT_ROUNDS_FOR_GAME = "SELECT * FROM rounds WHERE gameId = ? ORDER BY round DESC;";
        List<Round> rounds = jdbcTemplate.query(SELECT_ROUNDS_FOR_GAME, 
                new RoundMapper(), gameId);
        
        //addRoomAndEmployeesToMeetings(meetings);
        
        return rounds;
    }
    
    @Override
    public Round findById(String id) {
        
        final String sql = "SELECT * "
                + "FROM rounds WHERE roundId = ?;";

        return jdbcTemplate.queryForObject(sql, new RoundMapper(), id);
    }
    
    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM rounds WHERE gameId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }
    
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setGameId(rs.getInt("gameId"));
            r.setRoundId(rs.getString("roundId"));
            r.setRound(rs.getInt("round"));
            r.setGuess(rs.getString("guess"));
            r.setPartial(rs.getInt("partial"));
            r.setExact(rs.getInt("exact"));
            r.setStampDate(rs.getDate("stampDate").toLocalDate());
            r.setStampTime(rs.getTime("stampTime").toLocalTime());
            return r;
        }
    }    
}
