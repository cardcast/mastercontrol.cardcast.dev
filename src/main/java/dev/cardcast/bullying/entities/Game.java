package dev.cardcast.bullying.entities;

import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.entities.card.Rank;
import dev.cardcast.bullying.entities.card.Suit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Game {


    public Game(List<Player> players){
        this.players = players;
    }

    @Getter
    private List<Player> players = new ArrayList<>();

    @Getter
    private List<Card> stack = new ArrayList<>();

    @Getter
    private List<Card> deck = new ArrayList<>();

    public boolean hisTurn (Player player) {
        return turnIndex == players.indexOf(player);
    }

    public Card getTopCardFromStack(){
        return stack.get(stack.size() - 1);
    }

    private int turnIndex = 0;
    public int numberToDraw = 0;
    private boolean clockwise = true;
}
