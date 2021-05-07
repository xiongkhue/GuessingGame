/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author khuxi
 */
@Entity
@Table(name = "rounds")
public class Round {
    
    @Column(name="gameId")
    private int gameId;
    
    @Id
    @Column(name="roundId")
    private String roundId;
    
    @Column(name="round")
    private int round;
    
    @Column(name="guess")
    private String guess;
    
    @Column(name="partial")
    private int partial;
    
    @Column(name="exact")
    private int exact;
    
    @Column(name="stampDate")
    private LocalDate stampDate;
    
    @Column(name="stampTime")
    private LocalTime stampTime;

    public LocalDate getStampDate() {
        return stampDate;
    }

    public void setStampDate(LocalDate stampDate) {
        this.stampDate = stampDate;
    }

    public LocalTime getStampTime() {
        return stampTime;
    }

    public void setStampTime(LocalTime stampTime) {
        this.stampTime = stampTime;
    }
    
    public int getGameId(){
        return gameId;
    }
    
    public void setGameId(int gameId){
        this.gameId = gameId;
    }
    
    public String getRoundId(){
        return roundId;
    }
    
    public void setRoundId(String roundId){
        this.roundId = roundId;
    }
    
    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getPartial() {
        return partial;
    }

    public void setPartial(int partial) {
        this.partial = partial;
    }

    public int getExact() {
        return exact;
    }

    public void setExact(int exact) {
        this.exact = exact;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
    
    @Override
    public boolean equals(Object obj) 
    { 
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        if (obj instanceof Round) {
        Round round = (Round) obj;
        return (round.getGameId() == this.getGameId() && round.getRoundId().equals(this.getRoundId())
                && round.getRound() == (this.getRound()) && round.getGuess().equals(this.getGuess())
                && round.getPartial() == (this.getPartial()) && round.getExact() == (this.getExact())); 
    }
    return false;
    } 
      
    @Override
    public int hashCode() 
    {
        return this.getRound(); 
    }
}
