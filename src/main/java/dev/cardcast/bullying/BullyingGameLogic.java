package dev.cardcast.bullying;

import dev.cardcast.bullying.entities.Game;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.card.Card;
import dev.cardcast.bullying.util.DeckGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BullyingGameLogic implements IGameLogic {
    private final int AMOUNT_OF_CARDS_PER_PLAYER = 7;

    private static BullyingGameLogic instance = null;
    private static CardRules rules = new CardRules();

    public BullyingGameLogic() {
    }

    public static BullyingGameLogic getInstance() {
        if (instance == null)
            instance = new BullyingGameLogic();

        return instance;
    }

    // region private internal functions

    private void onDeckEmpty(Game game) {
        List<Card> deck = game.getDeck();
        List<Card> stack = game.getStack();

        Card lastCard = game.getTopCardFromStack();
        deck.addAll(stack);
        deck.remove(lastCard);
        stack.clear();
        stack.add(lastCard);

        Collections.shuffle(deck);
    }

    private Card drawTopCard(Game game) {
        if (game.getDeck().isEmpty()) {
            onDeckEmpty(game);
        }
        Card topCard = game.getTopCardFromDeck();
        game.getDeck().remove(topCard);
        return topCard;
    }

    private void distributeCardsAtStart(Game game) {
        for (Player player : game.getPlayers()) {
            for (int i = 0; i < AMOUNT_OF_CARDS_PER_PLAYER; i++) {
                player.getHand().getCards().add(drawTopCard(game));
            }
        }
        game.getStack().add(drawTopCard(game));
    }

    // endregion

    // region public functions from Interface

    @Override
    public void startGame(Game game) {
        game.getDeck().clear();
        game.getStack().clear();
        game.getDeck().addAll(DeckGenerator.generateBullyingDeck());
        Collections.shuffle(game.getDeck());
        distributeCardsAtStart(game);

        game.setTurnIndex(0);
        game.setClockwise(true);
        game.setNumberToDraw(0);
        Bullying.getLogger().info("A new has started");
    }

    @Override
    public boolean playCard(Game game, Player player, Card card) {

        if (player.getHand().getCards().stream().noneMatch(card1 -> card1.equals(card))) {
            Bullying.getLogger().warning(String.format("'%s', tries playing card: '%s', which they do not have", player.getName(), card));
            return false;
        }
        if (!game.isTheirTurn(player)) {
            Bullying.getLogger().warning(String.format("'%s', tried playing card: '%s', but failed because it is not their turn", player.getName(), card));
            return false;
        }
        Bullying.getLogger().info(String.format("'%s', tries playing card: '%s'", player.getName(), card));

        return rules.playCard(game, player, card);
    }

    @Override
    public boolean playerWon(Game game, Player player) {
        return player.getHand().getCards().isEmpty();
    }


    @Override
    public List<Card> drawCard(Game game, Player player) {
        List<Card> cards = new ArrayList<>();
        if (!game.isTheirTurn(player) || player.isDoneDrawing()) {
            return null; // Drawing cards is not allowed
        }
        if (game.getNumberToDraw() > 0) {
            // The player was bullied, so draw multiple cards
            for (int i = 0; i < game.getNumberToDraw(); i++) {
                Card fuckyoucard = drawTopCard(game);
                player.getHand().getCards().add(fuckyoucard);
                cards.add(fuckyoucard);
            }
            game.setNumberToDraw(0);
        } else {
            // Just a normal draw
            Card topCard = drawTopCard(game);
            player.getHand().getCards().add(topCard);
            cards.add(topCard);
            player.setDoneDrawing(true);
        }

        Bullying.getLogger().info(String.format("'%s' has drawn a new card(s): %s", player.getName(), cards.toString()));
        return cards;
    }

    @Override
    public boolean endTurn(Game game, Player player) {
        if (!game.isTheirTurn(player) || !player.isDoneDrawing()) {
            Bullying.getLogger().warning(String.format("'%s' could not end turn, it is not allowed", player.getName()));
            return false; // Ending the turn is not allowed
        }
        rules.passTurn(game);
        Bullying.getLogger().info(String.format("'%s' passed their turn to '%s'", player.getName(), game.getPlayers().get(game.getTurnIndex()).getName()));

        return true;
    }
    // endregion
}
