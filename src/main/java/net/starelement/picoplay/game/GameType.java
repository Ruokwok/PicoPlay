package net.starelement.picoplay.game;

public enum GameType {

    SINGLE_SCORE(0),
    SINGLE_TIE(1),
    TEAM_SCORE(2),
    TEAM_TIE(3);

    private int i;
    GameType(int i) {
        this.i = i;
    }

}
