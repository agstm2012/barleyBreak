package com.badlogic.androidgames.barleybreak;

import java.util.ArrayList;
import java.util.List;

public class World {
    public Square square;
    public boolean gameOver = false;
    public int score = 0;
    int[][] field;
    List<Square> squares;

    public World() {
        square = new Square(0, 64, 3);
        squares = new ArrayList<Square>();
        generateList();
    }

    public void generateField() {

        field = new int[4][4];
        for (int i = 0; i < 16; i++) {
            int x = (int) (Math.random() * 4);
            int y = (int) (Math.random() * 4);
            while (!(field[x][y] == 0)) {
                x = (int) (Math.random() * 4);
                y = (int) (Math.random() * 4);
            }
            field[x][y] = i;
        }
            /*
        field[0][0] = 1;
        field[0][1] = 5;
        field[0][2] = 9;
        field[0][3] = 13;

        field[1][0] = 2;
        field[1][1] = 6;
        field[1][2] = 10;
        field[1][3] = 14;

        field[2][0] = 3;
        field[2][1] = 7;
        field[2][2] = 11;
        field[2][3] = 0;

        field[3][0] = 4;
        field[3][1] = 8;
        field[3][2] = 12;
        field[3][3] = 15;    */

    }

    public void generateList() {
        int size = 64;
        int startx = 30;
        int starty = 100;

        generateField();


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Square newSquare = new Square(i * size + startx, j * size + starty, field[i][j]);
                squares.add(newSquare);
            }
        }
    }

    //get clicked square
    public Square getClickedSquare(int x, int y) {
        Square findSquare = null;
        StringBuilder builder = new StringBuilder();
        int size = 64;
        for (Square square : squares) {
            if (square.getX() <= x && square.getY() <= y && square.getX() + size >= x && square.getY() + size >= y) {
                findSquare = square;
                builder.append("Find element: " + String.valueOf(findSquare.getValue()) + " ");
            }
        }
        if (findSquare != null) {
            for (Square square : squares) {
                if (square.getX() + 64 == findSquare.getX() && square.getY() == findSquare.getY() && square.getValue() == 0) {
                    builder.append("lS " + String.valueOf(square.getValue()) + " ");
                    replacePos(square, findSquare);
                }

                if (square.getX() - 64 == findSquare.getX() && square.getY() == findSquare.getY() && square.getValue() == 0) {
                    builder.append("rS " + String.valueOf(square.getValue()) + " ");
                    replacePos(square, findSquare);
                }

                if (square.getX() == findSquare.getX() && square.getY() - 64 == findSquare.getY() && square.getValue() == 0) {
                    builder.append("bS " + String.valueOf(square.getValue()) + " ");
                    replacePos(square, findSquare);
                }

                if (square.getX() == findSquare.getX() && square.getY() + 64 == findSquare.getY() && square.getValue() == 0) {
                    builder.append("tS " + String.valueOf(square.getValue()) + " ");
                    replacePos(square, findSquare);
                }
            }
            //Log.d("myLogs", builder.toString());
        } else {
            //Log.d("myLogs", "empty");
        }

        return findSquare;
    }

    public void replacePos(Square zeroS, Square findS) {
        try {
            Square temp = findS.clone();
            findS.setValue(zeroS.getValue());
            zeroS.setValue(temp.getValue());
        } catch (CloneNotSupportedException e) {
        }
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void setSquares(List<Square> squares) {
        this.squares = squares;
    }

    public List<Integer> getNums() {
        List<Integer> nums = new ArrayList<Integer>();
        for(Square square:squares) {
            nums.add(square.getValue());
        }
        return nums;
    }
}

