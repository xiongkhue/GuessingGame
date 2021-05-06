/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.service;

import com.kx.guessinggame.dao.GameDao;
import com.kx.guessinggame.dto.Game;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author khuxi
 */
@Repository
public class GameServiceImpl implements GameService{

    GameDao dao;
    
    public GameServiceImpl(GameDao dao) {
        this.dao = dao;
    }

    @Override
    public Game add(Game game) {
        int[] nums = new int[4];
        nums[0] = 11;
        nums[1] = 11;
        nums[2] = 11;
        nums[3] = 11;
        for (int i = 0; i < nums.length; i++) {
            int n = (int)(Math.random()*9);
            while (contains(nums, n)) {
                n = (int)(Math.random()*9);
            }
            nums[i] = n;

        }
        
        String strNums = "";

        for(int i=0;i<nums.length;i++)
        {
            strNums = strNums + Integer.toString(nums[i]);
        }
        
        Game newGame = new Game();
        newGame.setAnswer(strNums);
        newGame.setRound(0);
        return dao.add(newGame);
    }

    @Override
    public boolean update(Game game) {
        return dao.update(game);
    }

    @Override
    public List<Game> getAll() {
        return dao.getAll();
    }

    @Override
    public Game findById(int id) {
        return dao.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        return dao.deleteById(id);
    }
    
    public boolean contains(final int[] arr, final int key) {
        return Arrays.stream(arr).anyMatch(i -> i == key);
    }    
}
