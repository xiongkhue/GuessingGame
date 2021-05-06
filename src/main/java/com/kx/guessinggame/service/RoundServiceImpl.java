/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.service;

import com.kx.guessinggame.dao.RoundDao;
import com.kx.guessinggame.dto.Game;
import com.kx.guessinggame.dto.Round;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author khuxi
 */
@Repository
public class RoundServiceImpl implements RoundService{

    RoundDao dao;
    
    public RoundServiceImpl(RoundDao dao) {
        this.dao = dao;
    }

    @Override
    public Round add(Game game, String guess) {
        Round round = new Round();
        int p = 0;
        int e = 0;
        int rd = game.getRound() + 1;
        String r = game.getGameId()+"."+rd;
        String[] strAnswer = game.getAnswer().split("");
        int[] intAnswer = new int[strAnswer.length];
        String[] strGuess = guess.split("");
        int[] intGuess = new int[strGuess.length];
        
        for (int i = 0; i < strAnswer.length; i++) {
            intAnswer[i] = Integer.parseInt(strAnswer[i]);
        }
        for (int i = 0; i < strGuess.length; i++) {
            intGuess[i] = Integer.parseInt(strGuess[i]);
        }
        for (int i = 0; i < intGuess.length; i++) {
            if (intAnswer[i] == intGuess[i]) {
                e++;
            } else if (contains(intAnswer, intGuess[i])) {
                p++;
            }
        }
        
        round.setExact(e);
        round.setPartial(p);
        round.setRoundId(r);
        round.setRound(rd);
        round.setGuess(guess);
        round.setGameId(game.getGameId());
        round.setStampDate(LocalDate.now());
        round.setStampTime(LocalTime.now());
        return dao.add(round);
    }

    @Override
    public Round findById(String id) {
        Round round = dao.findById(id);
        return round;
    }

    @Override
    public List<Round> getAll(int gameId) {
        return dao.getAll(gameId);
    }

    @Override
    public boolean deleteById(int gameId) {
        return dao.deleteById(gameId);
    }
    
    public boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }

    @Override
    public Round add(Round round) {
        return dao.add(round);
    }    
    
}
