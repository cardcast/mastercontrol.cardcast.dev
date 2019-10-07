package dev.cardcast.bullying.entities.card;

import lombok.Getter;

public enum Rank {

    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    WOMAN(12),
    KING(13),
    ACE(14),
    SANDER(15);


    @Getter
    private final int rank;

    Rank(int rank) {
        this.rank = rank;
    }
}
