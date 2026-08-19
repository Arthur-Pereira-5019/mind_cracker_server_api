package com.arthur_pereira.mind_cracker_server_api.data.match;

public enum ToleratedAnswerConfiguration {
    LOW_TOLERANCE(1),
    MEDIUM_TOLERANCE(2),
    HIGH_TOLERANCE(3);

    public final int value;

    ToleratedAnswerConfiguration(int value) {
        this.value = value;
    }
}
