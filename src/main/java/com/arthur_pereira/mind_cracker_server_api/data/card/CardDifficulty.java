package com.arthur_pereira.mind_cracker_server_api.data.card;

public enum CardDifficulty {
    EASY,
    MEDIUM,
    HARD;

    public static CardDifficulty fromLevel(int level) {
        if(level == 1) {
            return EASY;
        } else if (level == 2) {
            return MEDIUM;
        }
        return HARD;
    }
}
