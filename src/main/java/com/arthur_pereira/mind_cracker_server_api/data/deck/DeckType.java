package com.arthur_pereira.mind_cracker_server_api.data.deck;

public enum DeckType {
    BOARD_GAME,
    LEADERBOARD,
    OPTIONAL;

    public static DeckType fromValue(int value) {
        if(value == 1) {
            return BOARD_GAME;
        } else if(value == 2) {
            return LEADERBOARD;
        }
        return OPTIONAL;
    }
}
