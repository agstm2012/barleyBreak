package com.badlogic.androidgames.TRASH;

import java.util.List;

import android.graphics.Color;
import com.badlogic.androidgames.barleybreak.*;
import com.badlogic.androidgames.barleybreak.World;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Input.TouchEvent;

public class MyGameScreen extends Screen{
    enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    World world = new World();
    int step = 10;

    //????
    GameState state = GameState.Ready;
    String score = "0";

	public MyGameScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
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

    private void updateReady(List<TouchEvent> touchEvents) {
        if(touchEvents.size() > 0)
            state = GameState.Running;
    }

    private void updateRunning(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                //Button setMainMenuScreen
                /*
                if(event.x > 256 && event.y > 0 && event.y <= 64) {
                    game.setScreen(new MainMenuScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                } */

                //click on square
                if(event.x > world.getSquare().getX() && event.x <= world.getSquare().getX() + 64
                        && event.y > world.getSquare().getY() && event.y <= world.getSquare().getY() + 64) {
                    world.getSquare().setSkin(!world.getSquare().isSkin());
                }

                //click 1 control
                if(event.x > 0 && event.x <= 64 && event.y > 416 && event.y <= 480 && isDown(world.getSquare())) {
                    world.getSquare().setY(world.getSquare().getY() + step);
                    world.setScore(world.getScore() + 1);
                }
                //click 2 control
                if(event.x > 88 && event.x <= 152 && event.y > 416 && event.y < 480 && isLeft(world.getSquare())) {
                    world.getSquare().setX(world.getSquare().getX() - step);
                    world.setScore(world.getScore() + 1);
                }
                //click 3 control
                if(event.x > 172 && event.x <= 236 && event.y > 416 && event.y < 480 && isRight(world.getSquare())) {
                    world.getSquare().setX(world.getSquare().getX() + step);
                    world.setScore(world.getScore() + 1);
                }
                //click 4 control
                if(event.x > 256 && event.x <= 320 && event.y > 416 && event.y < 480 && isUp(world.getSquare())) {
                    world.getSquare().setY(world.getSquare().getY() - step);
                    world.setScore(world.getScore() + 1);
                }
                //Pause
                if(event.type == TouchEvent.TOUCH_UP) {
                    if(event.x < 64 && event.y < 64) {
                        if(Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.Paused;
                        return;
                    }
                }

                if(world.gameOver) {
                    if(Settings.soundEnabled)
                        Assets.bitten.play(1);
                    state = GameState.GameOver;
                }
            }
        }
    }

    public boolean isRight(Square square) {
        if(square.getX() + 64 < 320) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLeft(Square square) {
        if(square.getX() - step >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDown(Square square) {
        if(square.getY() + 66 + step < 416) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUp(Square square) {
        if(square.getY() - step >= 64) {
            return true;
        } else {
            return false;
        }
    }

    private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
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

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
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

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();      
        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);

        if(state == GameState.Ready)
            drawReadyUI();
        if(state == GameState.Running)
            drawRunningUI();
        if(state == GameState.Paused)
            drawPausedUI();
        if(state == GameState.GameOver)
            drawGameOverUI();

        drawText(g, String.valueOf(world.getScore()), g.getWidth() / 2 - score.length()*20 / 2, 10);
        //drawnumber
        drawText(g, String.valueOf(world.getSquare().getValue()), world.getSquare().getX() + 22, world.getSquare().getY() + 17);
    }

    private void drawWorld(com.badlogic.androidgames.barleybreak.World world) {
        Graphics g = game.getGraphics();
        if(!world.getSquare().isSkin()) {
            g.drawPixmap(Assets.square, world.getSquare().getX(), world.getSquare().getY());
        } else {
            g.drawPixmap(Assets.squarex, world.getSquare().getX(), world.getSquare().getY());
        }
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        g.myDrawRect(0, 0, 320, 64, Color.BLACK);
        g.myDrawRect(0, 416 - calcBtRect(), 320, 64 + calcBtRect(), Color.BLACK);
        g.drawPixmap(Assets.buttons_turn, 0, 416, 0, 0, 64, 64);
        g.drawPixmap(Assets.buttons_turn, 88, 416, 0, 64, 64, 64);
        g.drawPixmap(Assets.buttons_turn, 172, 416, 64, 0, 64, 64);
        g.drawPixmap(Assets.buttons_turn, 256, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
    }
    //расчет разницы между нижним прямоугольником и квадратом чтобы они сливались
    //raznica rect i square
    //dob pr9m pr-ka
    private int calcBtRect() {
        int y = 64;
        while(y < 416) {
            y = y + step;
        }
        return Math.abs(416 - y);
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

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
	
}
