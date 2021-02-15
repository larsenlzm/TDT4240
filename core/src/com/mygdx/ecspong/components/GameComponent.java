package com.mygdx.ecspong.components;

import com.badlogic.ashley.core.Component;

public class GameComponent implements Component {
    public enum GameState{
        GAME_START,
        GAME_PLAYING,
        GAME_WIN_P1,
        GAME_WIN_P2,
    }

    public GameState gameState;

    public GameComponent create() {
        this.gameState = GameState.GAME_START;
        return this;
    }

    public GameComponent setState(GameState state){
        this.gameState = state;
        return this;
    }
}
