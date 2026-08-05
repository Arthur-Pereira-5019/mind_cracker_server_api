package com.arthur_pereira.mind_cracker_server_api.model;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;
import com.arthur_pereira.mind_cracker_server_api.exception.DomainException;
import com.arthur_pereira.mind_cracker_server_api.mapper.BoardPositionsMapper;
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

    @Convert(converter = BoardPositionsMapper.class)
    @Column(columnDefinition = "TEXT")
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
