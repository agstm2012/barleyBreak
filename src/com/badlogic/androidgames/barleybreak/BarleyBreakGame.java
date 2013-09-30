package com.badlogic.androidgames.barleybreak;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class BarleyBreakGame extends AndroidGame {
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
} 
