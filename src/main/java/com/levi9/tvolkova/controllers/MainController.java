package com.levi9.tvolkova.controllers;

import com.levi9.tvolkova.domain.BoardView;
import com.levi9.tvolkova.domain.Game;
import com.levi9.tvolkova.domain.GameManager;
import com.levi9.tvolkova.domain.Player;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Tetiana Volkova
 */
@Controller
@RequestMapping("/")
public class MainController {

    private static String GAME_FIELD = "gamefield";

    @Autowired
    private GameManager gameManager;

    @Autowired
    private WaitingPlayers waitingPlayers;


    @RequestMapping(method = RequestMethod.GET)
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String startGame(@RequestParam("playerName") String playerName,
                            @CookieValue(value = "playerId", defaultValue = "notRegistered") String playerId,
                            HttpServletResponse response, Model model) {

        Game existingGame = gameManager.findGame(playerId);
        if (existingGame != null) {
            BoardView boardView = new BoardView(existingGame, playerId).build();
            model.addAttribute("boardView", boardView);
            return GAME_FIELD;
        }

        if (waitingPlayers.contains(playerId)) {
            waitingPlayers.remove(playerId);
        }

        String newPlayerId = UUID.randomUUID().toString();
        String newplayerName = playerName;
        int newPitSetId;
        String viewName;

        if (waitingPlayers.isEmpty()) {
            if (StringUtils.isEmpty(newplayerName)) {
                newplayerName = "Player_1";
            }
            newPitSetId = 0;
            waitingPlayers.add(new Player(newplayerName, newPlayerId, newPitSetId));
            viewName = "opponentwaiting";

        } else {
            if (StringUtils.isEmpty(newplayerName)) {
                newplayerName = "Player_2";
            }
            newPitSetId = 1;

            Player player = waitingPlayers.pop();
            Player opponent = new Player(newplayerName, newPlayerId, newPitSetId);

            Game newGame = gameManager.registerPlayers(player, opponent);
            BoardView boardView = new BoardView(newGame, opponent).build();
            model.addAttribute("boardView", boardView);
            viewName = GAME_FIELD;
        }
        setUserCookies(response, newPlayerId, newplayerName, newPitSetId, 300);

        return viewName;
    }

    @RequestMapping(value = "/canplay", method = RequestMethod.GET)
    public String canPlay(@CookieValue(value = "playerId", defaultValue = "notRegistered") String playerId,
                          Model model) {
        Game existingGame = gameManager.findGame(playerId);
        if (existingGame != null) {
            BoardView boardView = new BoardView(existingGame, playerId).build();
            model.addAttribute("boardView", boardView);
            return GAME_FIELD;
        }
        return "opponentwaiting";
    }

    @RequestMapping(value = "/{pitId}", method = RequestMethod.GET)
    public String turn(@PathVariable int pitId, Model model,
                       @CookieValue(value = "playerId") String playerId,
                       @CookieValue(value = "playerName") String playerName,
                       @CookieValue(value = "pitSetId") int pitSetId) {
        Player player = new Player(playerName, playerId, pitSetId);
        Game game = gameManager.turn(player, pitId);
        if (game == null) {
            return "welcome";
        }
        BoardView boardView = new BoardView(game, player).build();
        model.addAttribute("boardView", boardView);
        return GAME_FIELD;
    }

    @RequestMapping(value = "/state", method = RequestMethod.GET)
    public String getState(@CookieValue(value = "playerId") String playerId,
                           @CookieValue(value = "playerName") String playerName,
                           @CookieValue(value = "pitSetId") int pitSetId,
                           Model model) {
        Player player = new Player(playerName, playerId, pitSetId);
        Game game = gameManager.findGame(playerId);
        BoardView boardView = new BoardView(game, player).build();
        model.addAttribute("boardView", boardView);
        return GAME_FIELD;
    }

    private void setUserCookies(HttpServletResponse response, String playerId, String playerName, int pitSetId, int maxAge) {
        Cookie playerIdCookie = new Cookie("playerId", playerId);
        playerIdCookie.setMaxAge(maxAge);
        response.addCookie(playerIdCookie);

        Cookie playerNameCookie = new Cookie("playerName", playerName);
        playerNameCookie.setMaxAge(maxAge);
        response.addCookie(playerNameCookie);

        Cookie pitSetIdCookie = new Cookie("pitSetId", String.valueOf(pitSetId));
        pitSetIdCookie.setMaxAge(maxAge);
        response.addCookie(pitSetIdCookie);
    }
}
