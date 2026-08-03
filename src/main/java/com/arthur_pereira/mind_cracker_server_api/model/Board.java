package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import jakarta.persistence.*;

import java.util.Arrays;

@Entity
@Table
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boardId;

    @Column
    private boolean forcedShuffle;

    @ElementCollection
    @CollectionTable(
            name = "board_positions",
            joinColumns = @JoinColumn(name = "card_id")
    )
    @Column(name = "position")
    @Enumerated(EnumType.ORDINAL)
    private BoardPositionType[] boardPositions;

    public Board(boolean forcedShuffle, int boardLength) {
        this.forcedShuffle = forcedShuffle;
        boardPositions = new BoardPositionType[boardLength];
    }

    public Board() {
    }

    public void setForcedShuffle(boolean forcedShuffle) {
        this.forcedShuffle = forcedShuffle;
    }

    public void setBoardLength(int boardLength) {
        boardPositions = Arrays.copyOf(boardPositions,boardLength);
    }

    public void setBoardPositionType(int position, BoardPositionType type) {
        if(position > boardPositions.length || position < 0) {
            throw new DomainException("Postion out of the Board length.");
        }
        boardPositions[position] = type;
    }

    public boolean isShuffleForced() {
        return forcedShuffle;
    }

    public int getBoardLength() {
        return boardPositions.length;
    }

}
