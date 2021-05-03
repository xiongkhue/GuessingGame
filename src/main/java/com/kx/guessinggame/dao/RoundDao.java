/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kx.guessinggame.dao;

import com.kx.guessinggame.dto.Round;
import java.util.List;

/**
 *
 * @author khuxi
 */
public interface RoundDao {
    
    Round add(Round round);
    Round findById(String id);
    List<Round> getAll(int gameId);

    boolean deleteById(int gameId);
}
