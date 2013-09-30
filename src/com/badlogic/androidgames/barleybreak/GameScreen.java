package com.badlogic.androidgames.barleybreak;

import android.graphics.Color;
import com.badlogic.androidgames.TRASH.Settings;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 03.07.13
 * Time: 11:09
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen extends Screen {

    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    GameState state = GameState.Ready;

    World world = new World();

    public GameScreen(Game game){
        super(game);
    }

    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if(state == GameState.Ready)
            updateReady(touchEvents);
        if(state == GameState.Running)
            updateRunning(touchEvents);
        if(state == GameState.Paused)
            updatePaused(touchEvents);
        if(state == GameState.GameOver)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<Input.TouchEvent> touchEvents) {
        if(touchEvents.size() > 0) {
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                Input.TouchEvent event = touchEvents.get(i);
                if(event.type == Input.TouchEvent.TOUCH_UP) {
                    state = GameState.Running;
                }
            }
        }
    }

    private void updateRunning(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                world.getClickedSquare(event.x, event.y);
                //CHECK GAMEOVER
                int[] numbs = {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15, 4, 8, 12, 0};
                if(checkGameOver(numbs)) {
                    world.setGameOver(true);
                    state = GameState.GameOver;
                }

                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }

            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x < 64 && event.y < 64) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.Paused;
                    return;
                }
            }
        }
    }

    private void updatePaused(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x > 80 && event.x <= 240) {
                    if(event.y > 100 && event.y <= 148) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Running;
                        return;
                    }
                    if(event.y > 148 && event.y < 196) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<Input.TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {
                if(event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264) {
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    public boolean checkGameOver(int[] numbs) {
        List<Integer> listN = world.getNums();
        int count = 0;
        for(int i = 0; i < numbs.length; i++) {
            if(listN.get(i) == numbs[i]) {
                count++;
            }
        }

        if(count == numbs.length) {
            return true;
        } else {
            return false;
        }
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        drawWorld();

        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();
    }

    private void drawWorld() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        List<Square> squares = world.getSquares();
        for(Square square:squares) {
            if(square.getValue() != 0) {
                g.drawPixmap(Assets.square, square.getX(), square.getY());
                drawText(g, String.valueOf(square.getValue()), square.getX() + 22, square.getY() + 17);
            }
        }

        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        /*
        int x = 0;
        for(Integer num:world.getNums()) {
            drawText(g, String.valueOf(num), x, 416);
            x = x + 30;
        }

        if(world.isGameOver()) {
            g.drawPixmap(Assets.squarex, 0, 340);
        } else {
            g.drawPixmap(Assets.square, 0, 340);
        } */
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    public void pause() {
    }


    public void resume() {
    }


    public void dispose() {
    }
}
