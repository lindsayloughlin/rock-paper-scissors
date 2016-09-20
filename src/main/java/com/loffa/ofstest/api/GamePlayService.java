package com.loffa.ofstest.api;

import com.loffa.ofstest.core.GameContent;
import com.loffa.ofstest.core.MoveMade;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lloughlin on 13/09/2016.
 */
public class GamePlayService {

    Map<String, GameContent> multiplayerGames = new ConcurrentHashMap<>();
    public synchronized  String  requestGame(MoveMade moveMade) {

        // If there an existing free slot?
//        for (Map.Entry<String,GameContent> gameEntry : multiplayerGames.entrySet()) {
//            GameContent gameEntry = gameEntry.getValue();
//            if (null == gameEntry.playerTwoMove) {
//                // Woot free spot.
//
//                // Remove existing game code and add in a new Node.
//                String gameKey = gameEntry.getKey();
//                GameContent oldData =
//                GameContent.newBuilder()
//                        .withResultType()
//
//                multiplayerGames.remove(gameKey);
//
//
//                gameEntry.getValue().playerTwoMove = moveMade;
//            }
//        }

        // No games available create a new one.
        return "new game";
    }

//    boolean signedUserName(String username) {
//        for (MoveMade move : multiplayerGames.containsKey()) {
//            if (move.username.equals(username)) {
//                return true;
//            }
//        }
//        return false;
//    }


}
