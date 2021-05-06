/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.dao;

import com.kx.guessinggame.dto.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author khuxi
 */
@Repository
public class GameDaoImpl implements GameDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Game add(Game game) {

        final String sql = "INSERT INTO games(answer, round, finished) VALUES(?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {

            PreparedStatement statement = conn.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, game.getAnswer());
            statement.setInt(2, game.getRound());
            statement.setBoolean(3, game.isFinished());
            return statement;

        }, keyHolder);

        game.setGameId(keyHolder.getKey().intValue());

        return game;
    }

    @Override
    public List<Game> getAll() {
        List<Game> getAllList = new ArrayList();
        List<Game> temp = new ArrayList();
        boolean isFinished = false;
        final String sqla = "SELECT * FROM games WHERE finished = ?;";
        final String sqlb = "SELECT * FROM games WHERE finished != ?;";
        getAllList = jdbcTemplate.query(sqla, new GameMapper(), isFinished);
        temp = jdbcTemplate.query(sqlb, new GameMapper(), isFinished);
        getAllList.stream().forEach(elt -> elt.setAnswer("Hidden"));
        getAllList.addAll(temp);
        
        return getAllList;
    }

    @Override
    public Game findById(int id) {
        final String sql = "SELECT * "
                + "FROM games WHERE gameId = ?;";

        return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
    }

    @Override
    public boolean update(Game game) {

        final String sql = "UPDATE games SET "
                + "answer = ?, "
                + "round = ?, "
                + "finished = ? "
                + "WHERE gameId = ?;";

        return jdbcTemplate.update(sql,
                game.getAnswer(), game.getRound(), game.isFinished(), game.getGameId())> 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM games WHERE gameId = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setGameId(rs.getInt("gameId"));
            g.setAnswer(rs.getString("answer"));
            g.setRound(rs.getInt("round"));
            g.setFinished(rs.getBoolean("finished"));
            return g;
        }
    }    
}
