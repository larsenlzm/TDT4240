package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    public int upKey;
    public int downKey;
    public int score;
    public int playerNum;

    public PlayerComponent create(int upKey, int downKey, int playerNum) {
        this.downKey = downKey;
        this.upKey = upKey;
        this.playerNum = playerNum;
        return this;
    }

    public PlayerComponent setScore(int score) {
        this.score = score;
        return this;
    }
}