/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.dto;

/**
 *
 * @author khuxi
 */
public class Game {
    
    private int gameId;
    private String answer;
    private int round;
    private boolean finished;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        if (obj instanceof Game) {
        Game game = (Game) obj;
        return (this.getGameId() == (game.getGameId()) && 
        game.getAnswer().equals(this.getAnswer()) && 
        (game.isFinished() == (this.isFinished())));
    }
    return false;
    }

    @Override
    public int hashCode() {
        return this.getGameId();
    }
}
