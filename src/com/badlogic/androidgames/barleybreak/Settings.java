package com.badlogic.androidgames.barleybreak;

import com.badlogic.androidgames.framework.FileIO;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: ivanPC
 * Date: 01.07.13
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    public static boolean soundEnabled = true;
    public static String[] results = {"32 - 12:32", "124 - 44:00", "15 - 01:13", "122 - 12:12", "14 - 00:12"};

    public static void load(FileIO files) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    files.readFile(".bb")));
            soundEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                results[i] = in.readLine();
            }
        } catch (IOException e) {
            // :( It's ok we have defaults
        } catch (NumberFormatException e) {
            // :/ It's ok, defaults save our day
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    public static void save(FileIO files) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    files.writeFile(".bb")));
            out.write(Boolean.toString(soundEnabled));
            for (int i = 0; i < 5; i++) {
                out.write(results[i]);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (out != null)
                    out.close();
                } catch (IOException e) {
            }
        }
    }
    //ПЕРЕПИСАТЬ И РАСПАРСИТЬ ПО ЧЕЛОВЕЧЕСКИ
    public static void addScore(String score) {

    }
}
