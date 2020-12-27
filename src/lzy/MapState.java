package lzy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MapState {
    private char[][] map = new char[16][16];
    private List<Point> white = new ArrayList<>();
    private List<Point> black = new ArrayList<>();
    private String color;
    private String oppositeColor;
    private int depth = 0;
    private int value;

    public MapState(int value) {
        this.value = value;
    }

    public MapState(char[][] map, String color) {
        this.color = color;
        this.oppositeColor = (color.equals("BLACK") ? "WHITE" : "BLACK");
        this.map = map;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (map[i][j] == 'W') {
                    this.white.add(new Point(j, i));
                } else if (map[i][j] == 'B') {
                    this.black.add(new Point(j, i));
                }
            }
        }
    }

    private MapState newState(int y, int x, int newY, int newX) {
        char[][] clone = new char[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                clone[i][j] = this.map[i][j];
            }
        }
        clone[y][x] = '.';
        clone[newY][newX] = this.color.charAt(0);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(clone[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        return new MapState(clone, oppositeColor);
    }

    public List<MapState> nextSteps() {
        List<MapState> lists = new ArrayList<>();
        List<Point> allPoints = null;
        if ("BLACK".equals(this.color)) {
            allPoints = black;
        } else {
            allPoints = white;
        }
        for (Point chess : allPoints) {
            int y = chess.y;
            int x = chess.x;
            if ((y - 1) >= 0 && (x - 1 >= 0) && map[y - 1][x - 1] == '.') {
                lists.add(newState(y, x, y - 1, x - 1));
            }
            if ((y - 1) >= 0 && map[y - 1][x] == '.') {
                lists.add(newState(y, x, y - 1, x));
            }
            if ((y - 1) >= 0 && (x + 1 < 16) && map[y - 1][x + 1] == '.') {
                lists.add(newState(y, x, y - 1, x + 1));
            }
            if ((x + 1 < 16) && map[y][x + 1] == '.') {
                lists.add(newState(y, x, y, x + 1));
            }
            if ((y + 1) < 16 && (x + 1 < 16) && map[y + 1][x + 1] == '.') {
                lists.add(newState(y, x, y + 1, x + 1));
            }
            if ((y + 1) < 16 && map[y + 1][x] == '.') {
                lists.add(newState(y, x, y + 1, x));
            }
            if ((y + 1) < 16 && (x - 1 >= 0) && map[y + 1][x - 1] == '.') {
                lists.add(newState(y, x, y + 1, x - 1));
            }
            if ((x - 1 >= 0) && map[y][x - 1] == '.') {
                lists.add(newState(y, x, y, x - 1));
            }

            if ((y - 2) >= 0 && (x - 2) >= 0 && map[y - 2][x - 2] == '.' && map[y - 1][x - 1] != '.') {
                lists.add(newState(y, x, y - 2, x - 2));
            }
            if ((y - 2) >= 0 && map[y - 2][x] == '.' && map[y - 1][x] != '.') {
                lists.add(newState(y, x, y - 2, x));
            }
            if ((y - 2) >= 0 && (x + 2) < 16 && map[y - 2][x + 2] == '.' && map[y - 1][x + 1] != '.') {
                lists.add(newState(y, x, y - 2, x + 2));
            }
            if ((x + 2) < 16 && map[y][x + 2] == '.' && map[y][x + 1] != '.') {
                lists.add(newState(y, x, y, x + 2));
            }
            if ((y + 2) < 16 && (x + 2) < 16 && map[y + 2][x + 2] == '.' && map[y + 1][x + 1] != '.') {
                lists.add(newState(y, x, y + 2, x + 2));
            }
            if ((y + 2) < 16 && map[y + 2][x] == '.' && map[y + 1][x] != '.') {
                lists.add(newState(y, x, y + 2, x));
            }
            if ((y + 2) < 16 && (x - 2) >= 0 && map[y + 2][x - 2] == '.' && map[y + 1][x - 1] != '.') {
                lists.add(newState(y, x, y + 2, x - 2));
            }
            if ((x - 2) >= 0 && map[y][x - 2] == '.' && map[y][x - 1] != '.') {
                lists.add(newState(y, x, y, x - 2));
            }
        }
        return lists;
    }


    public char[][] getMap() {
        return map;
    }

    public void setMap(char[][] map) {
        this.map = map;
    }

    public List<Point> getWhite() {
        return white;
    }

    public void setWhite(List<Point> white) {
        this.white = white;
    }

    public List<Point> getBlack() {
        return black;
    }

    public void setBlack(List<Point> black) {
        this.black = black;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
