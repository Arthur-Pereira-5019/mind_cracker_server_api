package com.arthur_pereira.mind_cracker_server_api.data.board;

import com.arthur_pereira.mind_cracker_server_api.data.card.CardDifficulty;
import com.arthur_pereira.mind_cracker_server_api.exception.common.ImpossibleConversionException;

public enum BoardPositionType {
    ANY_SIMPLE_CARD(1),
    EASY(2),
    MEDIUM(3),
    HARD(4),
    SPECIAL(5);

    private final int id;

    BoardPositionType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public CardDifficulty toCardDifficulty() {
        switch (this) {
            case EASY: return CardDifficulty.EASY;
            case MEDIUM: return CardDifficulty.MEDIUM;
            case HARD: return CardDifficulty.HARD;
            case ANY_SIMPLE_CARD: {
                //Random.nextInt() call optimization;
                long odd = System.nanoTime() % 3;
                if(odd == 0L) {
                    return CardDifficulty.EASY;
                }
                if(odd == 1L) {
                    return CardDifficulty.MEDIUM;
                }
                return CardDifficulty.HARD;
            }
        }
        throw new ImpossibleConversionException("The provided position isn't Common-Card related!");
    }

    public boolean isACardDifficulty() {
        return this != BoardPositionType.SPECIAL;
    }
}
