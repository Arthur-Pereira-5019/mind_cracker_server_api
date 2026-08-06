package com.arthur_pereira.mind_cracker_server_api.dto.board;

import com.arthur_pereira.mind_cracker_server_api.data.board.BoardPositionType;

public record BoardCreationDTO(boolean forcedShuffle,
                               int maxBoardLength,
                               BoardPositionType defaultPositionType
) { }
