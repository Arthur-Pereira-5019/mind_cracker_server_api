package com.arthur_pereira.mind_cracker_server_api.data.board;

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
}
