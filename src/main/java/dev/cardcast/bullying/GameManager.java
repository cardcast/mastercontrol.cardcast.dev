package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameManager implements IGameManager {

    private static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }


    @Getter
    private List<PlayerContainer> containers = new ArrayList<>();

    @Override
    public Lobby tryJoinLobby(Player player, String code) {
        for (PlayerContainer container : this.getContainers()) {
            if (container instanceof Lobby) {
                Lobby lobby = (Lobby) container;
                if (lobby.getCode().equals(code)) {
                    lobby.getPlayers().add(player);
                }
            }
        }
        return null;
    }

    @Override
    public Lobby createLobby(boolean isPublic, int maxPlayers, Host host) {
        Lobby lobby = new Lobby(isPublic, maxPlayers, host);
        this.containers.add(lobby);
        return lobby;
    }

    @Override
    public void addPlayer(Lobby lobby, Player player) {
        lobby.addPlayer(player);
    }

    @Override
    public void removePlayer(Lobby lobby, Player player) {
        lobby.getPlayers().remove(player);
    }


    @Override
    public Game startGame(Lobby lobby) {
        Game game = new Game(lobby.getHost(), lobby.getPlayers());
        this.containers.add(game);
        BullyingGameLogic.getInstance().startGame(game);
        this.containers.remove(lobby);
        return game;
    }

    @Override
    public PlayerContainer findPlayer(Player player) {
        for (PlayerContainer container : this.containers) {
            if (container.getPlayers().contains(player)) {
                return container;
            }
        }
        return null;
    }
}

