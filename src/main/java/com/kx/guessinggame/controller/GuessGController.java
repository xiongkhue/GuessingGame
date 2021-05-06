/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kx.guessinggame.dto.Game;
import com.kx.guessinggame.dto.Round;
import com.kx.guessinggame.service.GameService;
import com.kx.guessinggame.service.RoundService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author khuxi
 */
public class GuessGController {
    
    private final GameService gSL;
    private final RoundService rSL;

    public GuessGController(GameService gSL, RoundService rSL) {
        this.gSL = gSL;
        this.rSL = rSL;
    }

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        Game newGame = gSL.add(game);
        newGame.setAnswer("Hidden");
        Round round = new Round();
        round.setGameId(newGame.getGameId());
        String r = newGame.getGameId() + "." + newGame.getRound();
        round.setRoundId(r);
        round.setStampDate(LocalDate.now());
        round.setStampTime(LocalTime.now());
        round.setGuess("");
        rSL.add(round);
        return newGame;
    }

    @PostMapping("/guess")
    public Round calculate(@RequestBody ObjectNode json) {
        String guess = json.get("guess").asText();
        int gameId = json.get("gameId").asInt();

        Game game = gSL.findById(gameId);
        if (game.isFinished()) {

        }

        Round round = rSL.add(game, guess);
        if (round.getExact() == 4) {
            game.setFinished(true);
        }
        game.setRound(game.getRound() + 1);
        update(game.getGameId(), game);
        return round;
    }

    @GetMapping("/games")
    public List<Game> allGame() {
        return gSL.getAll();
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> allRound(@PathVariable("gameId") int gameId) {
        return rSL.getAll(gameId);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<Game> findByIdGame(@PathVariable("gameId") int id) {
        Game result = gSL.findById(id);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        if (!result.isFinished()) {
            result.setAnswer("Hidden");
        }
        return ResponseEntity.ok(result);
    }

    public ResponseEntity update(@PathVariable int id, @RequestBody Game game) {
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != game.getGameId()) {
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (gSL.update(game)) {
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
}
