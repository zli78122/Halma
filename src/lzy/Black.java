package lzy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Black {
    static List<Point> blackCamp = new ArrayList<>();
    static List<Point> whiteCamp = new ArrayList<>();
    private char[][] map = new char[16][16];
    private List<Point> white = new ArrayList<>();
    private List<Point> black = new ArrayList<>();
    private int depth = 0;
    private int value = 0;
    private String color;
    private List<Black> lists = new ArrayList<>();
    private String pathStr = "";
    boolean onlyMax = false;
    public boolean isFinished = false;
    private boolean campIn = false;
    private int newX;
    private int newY;

    public Black(char[][] map, String color) {
        this.color = color;
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

    public Black(char[][] map, String color, String pathStr, int newX, int newY) {
        this.color = color;
        this.map = map;
        this.pathStr = pathStr;
        this.newX = newX;
        this.newY = newY;
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

    public void eval() {
        int val = 0;
        for (Point point : black) {
            int f1 = -199, f2 = -99, f3 = -101;
            int x = point.x;
            int y = point.y;
            if (!whiteCamp.contains(point) && !blackCamp.contains(point)) {
                int a = 26 - x - y;
                int b = Math.abs(x - y);
                val = val + (f1 * a * a) + (f2 * b);

                if ((x - 1) >= 2 && (y - 1) >= 2 && (x + 1) <= 13 && (y + 1) <= 13) {
                    if (map[y - 1][x - 1] == 'B' && map[y + 1][x + 1] == '.') {
                        val += 16;
                    }
                    if (map[y - 1][x] == 'B' && map[y + 1][x] == '.') {
                        val += 11;
                    }
                    if (map[y][x - 1] == 'B' && map[y][x + 1] == '.') {
                        val += 11;
                    }
                    if (map[y + 1][x + 1] != '.' && map[y + 2][x + 2] == '.') {
                        val += 16;
                    }
                    if (map[y + 1][x] != '.' && map[y + 2][x] == '.') {
                        val += 11;
                    }
                    if (map[y][x + 1] != '.' && map[y][x + 2] == '.') {
                        val += 11;
                    }
                    if (map[y + 1][x + 1] == 'W' && map[y - 1][x - 1] == '.') {
                        val -= 16;
                    }
                    if (map[y + 1][x] == 'W' && map[y - 1][x] == '.') {
                        val -= 11;
                    }
                    if (map[y][x + 1] == 'W' && map[y][x - 1] == '.') {
                        val -= 11;
                    }
                    if (map[y][x + 1] == '.' && map[y + 1][x + 1] == '.' && map[y + 1][x] == '.') {
                        val -= 199;
                        if (map[y - 1][x - 1] == '.' && map[y - 1][x] == '.' && map[y - 1][x + 1] == '.' && map[y + 1][x - 1] == '.' && map[y][x - 1] == '.') {
                            val -= 99;
                        }
                    }
                } else {
                    val -= 100;
                }
            } else if (blackCamp.contains(point)) {
                int a = 16 - x - y;
                val = val + (f1 * a * a);
                if (map[y + 1][x + 1] != '.' && map[y + 2][x + 2] == '.') {
                    val = val + (8 * a * a);
                } else if ((map[y][x + 1] != '.' && map[y][x + 2] == '.') || (map[y + 1][x] != '.' && map[y + 2][x] == '.')) {
                    val = val + (4 * a * a);
                }
                if (map[y + 1][x + 1] == '.' && map[y + 1][x] == '.' && map[y][x + 1] == '.') {
                    val = val + (f3 * a * a);
                }
            } else {
                val += 30000;
                int a = 30 - x - y;
                val = val - (a * 3);
            }
        }
        for (Point point : white) {
            int f1 = 199, f2 = 99;
            int x = point.x;
            int y = point.y;
            if (!blackCamp.contains(point) && !whiteCamp.contains(point)) {
                int a = x + y - 6;
                int b = Math.abs(x - y);
                val = val + (a * a * f1) + (b * f2);

                if ((x - 1) >= 2 && (y - 1) >= 2 && (x + 1) <= 13 && (y + 1) <= 13) {
                    if (map[y - 1][x - 1] == '.' && map[y + 1][x + 1] == 'W') {
                        val -= 16;
                    }
                    if (map[y - 1][x] == '.' && map[y + 1][x] == 'W') {
                        val -= 9;
                    }
                    if (map[y][x - 1] == '.' && map[y][x + 1] == 'W') {
                        val -= 11;
                    }
                    if (map[y - 1][x - 1] != '.' && map[y - 2][x - 2] == '.') {
                        val -= 16;
                    }
                    if (map[y - 1][x] != '.' && map[y - 2][x] == '.') {
                        val -= 11;
                    }
                    if (map[y][x - 1] != '.' && map[y][x - 2] == '.') {
                        val -= 9;
                    }
                    if (map[y + 1][x + 1] == '.' && map[y - 1][x - 1] == 'B') {
                        val += 16;
                    }
                    if (map[y + 1][x] == '.' && map[y - 1][x] == 'B') {
                        val += 9;
                    }
                    if (map[y][x + 1] == '.' && map[y][x - 1] == 'B') {
                        val += 11;
                    }
                    if (map[y - 1][x - 1] == '.' && map[y - 1][x] == '.' && map[y][x - 1] == '.') {
                        val += 199;
                        if (map[y - 1][x + 1] == '.' && map[y][x + 1] == '.' && map[y + 1][x + 1] == '.' && map[y + 1][x] == '.' && map[y + 1][x - 1] == '.') {
                            val += 99;
                        }
                    }
                } else {
                    val += 100;
                }
            } else {
                val -= 30000;
                int a = x + y;
                val = val + (a * 3);
            }
        }
        this.value = val;
    }

    public List<Black> nextSteps(boolean isDeadBoard) {
        if (this.color.equals("BLACK")) {
            blackNext(isDeadBoard);
        } else {
            whiteNext(isDeadBoard);
        }
        if (this.campIn && this.lists.size() > 0) {
            List<Black> list1 = new ArrayList<>();
            List<Black> list2 = new ArrayList<>();
            Point p;
            for (Black list : lists) {
                p = new Point(list.newX, list.newY);
                if (blackCamp.contains(p) || whiteCamp.contains(p)) {
                    list2.add(list);
                } else {
                    list1.add(list);
                }
            }
            if (list1.size() > 0) {
                return list1;
            } else {
                return list2;
            }
        }
        return this.lists;
    }

    private void whiteNext(boolean isDeadBoard) {
        List<String> path = new ArrayList<>();

        List<Point> whiteCampPoint = new ArrayList<>();
        for (Point point : whiteCamp) {
            if (map[point.y][point.x] == 'W') {
                whiteCampPoint.add(point);
            }
        }

        if (whiteCampPoint.size() > 0 && !isDeadBoard) {
            this.campIn = true;
            for (Point campChess : whiteCampPoint) {
                int y = campChess.y;
                int x = campChess.x;
                path.add(x + "," + y);

                walk(x, y, path);

                List<Point> isVisited = new ArrayList<>();
                isVisited.add(campChess);
                jump(x, y, isVisited, x, y, path);

                path.remove(path.size() - 1);
            }
        } else {
            for (Point chess : white) {
                int y = chess.y;
                int x = chess.x;
                path.add(x + "," + y);

                walk(x, y, path);

                List<Point> isVisited = new ArrayList<>();
                isVisited.add(chess);
                jump(x, y, isVisited, x, y, path);

                path.remove(path.size() - 1);
            }
        }
    }

    private void blackNext(boolean isDeadBoard) {
        List<String> path = new ArrayList<>();

        List<Point> blackCampPoint = new ArrayList<>();
        for (Point point : blackCamp) {
            if (map[point.y][point.x] == 'B') {
                blackCampPoint.add(point);
            }
        }

        if (blackCampPoint.size() > 0 && !isDeadBoard) {
            this.campIn = true;
            for (Point campChess : blackCampPoint) {
                int y = campChess.y;
                int x = campChess.x;
                path.add(x + "," + y);

                walk(x, y, path);

                List<Point> isVisited = new ArrayList<>();
                isVisited.add(campChess);
                jump(x, y, isVisited, x, y, path);

                path.remove(path.size() - 1);
            }
        } else {
            for (Point chess : black) {
                int y = chess.y;
                int x = chess.x;
                path.add(x + "," + y);

                walk(x, y, path);

                List<Point> isVisited = new ArrayList<>();
                isVisited.add(chess);
                jump(x, y, isVisited, x, y, path);

                path.remove(path.size() - 1);
            }
        }
    }

    public void walk(int x, int y, List<String> path) {
        if ((y - 1) >= 0 && (x - 1 >= 0) && map[y - 1][x - 1] == '.') {
            path.add((x - 1) + "," + (y - 1));
            Black temp = newState(y, x, y - 1, x - 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((y - 1) >= 0 && map[y - 1][x] == '.') {
            path.add((x) + "," + (y - 1));
            Black temp = newState(y, x, y - 1, x, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((y - 1) >= 0 && (x + 1 < 16) && map[y - 1][x + 1] == '.') {
            path.add((x + 1) + "," + (y - 1));
            Black temp = newState(y, x, y - 1, x + 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((x + 1 < 16) && map[y][x + 1] == '.') {
            path.add((x + 1) + "," + (y));
            Black temp = newState(y, x, y, x + 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((y + 1) < 16 && (x + 1 < 16) && map[y + 1][x + 1] == '.') {
            path.add((x + 1) + "," + (y + 1));
            Black temp = newState(y, x, y + 1, x + 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((y + 1) < 16 && map[y + 1][x] == '.') {
            path.add((x) + "," + (y + 1));
            Black temp = newState(y, x, y + 1, x, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((y + 1) < 16 && (x - 1 >= 0) && map[y + 1][x - 1] == '.') {
            path.add((x - 1) + "," + (y + 1));
            Black temp = newState(y, x, y + 1, x - 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
        if ((x - 1 >= 0) && map[y][x - 1] == '.') {
            path.add((x - 1) + "," + (y));
            Black temp = newState(y, x, y, x - 1, path, false);
            if (temp != null) {
                this.lists.add(temp);
            }
            path.remove(path.size() - 1);
        }
    }

    private void jump(int x, int y, List<Point> isVisited, int oriX, int oriY, List<String> path) {
        Point p = new Point(x - 2, y - 2);
        if ((y - 2) >= 0 && (x - 2) >= 0 && map[y - 2][x - 2] == '.' && map[y - 1][x - 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y - 2, x - 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x - 2, y - 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x, y - 2);
        if ((y - 2) >= 0 && map[y - 2][x] == '.' && map[y - 1][x] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y - 2, x, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x, y - 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x + 2, y - 2);
        if ((y - 2) >= 0 && (x + 2) < 16 && map[y - 2][x + 2] == '.' && map[y - 1][x + 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y - 2, x + 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x + 2, y - 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x + 2, y);
        if ((x + 2) < 16 && map[y][x + 2] == '.' && map[y][x + 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y, x + 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x + 2, y, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x + 2, y + 2);
        if ((y + 2) < 16 && (x + 2) < 16 && map[y + 2][x + 2] == '.' && map[y + 1][x + 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y + 2, x + 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x + 2, y + 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x, y + 2);
        if ((y + 2) < 16 && map[y + 2][x] == '.' && map[y + 1][x] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y + 2, x, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x, y + 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x - 2, y + 2);
        if ((y + 2) < 16 && (x - 2) >= 0 && map[y + 2][x - 2] == '.' && map[y + 1][x - 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y + 2, x - 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x - 2, y + 2, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
        p = new Point(x - 2, y);
        if ((x - 2) >= 0 && map[y][x - 2] == '.' && map[y][x - 1] != '.' && !isVisited.contains(p)) {
            path.add(p.x + "," + p.y);
            Black temp = newState(oriY, oriX, y, x - 2, path, true);
            if (temp != null) {
                this.lists.add(temp);
            }
            isVisited.add(p);
            jump(x - 2, y, isVisited, oriX, oriY, path);
            path.remove(path.size() - 1);
        }
    }

    public Black newState(int y, int x, int newY, int newX, List<String> path, boolean isJump) {
        Point ori = new Point(x, y);
        Point newL = new Point(newX, newY);

        if (this.color.equals("BLACK")) {
            if (blackCamp.contains(ori) && blackCamp.contains(newL) && ((x + y) >= (newX + newY))) {
                return null;
            }
            if (!blackCamp.contains(ori) && blackCamp.contains(newL)) {
                return null;
            }
            if (whiteCamp.contains(ori) && !whiteCamp.contains(newL)) {
                return null;
            }
            if (x >= newX && y >= newY) {
                return null;
            }
            for (int i = 1; i < path.size() - 1; i++) {
                String temp = path.get(i);
                int a = Integer.parseInt(temp.split(",")[0]);
                int b = Integer.parseInt(temp.split(",")[1]);
                if (a >= newX && b >= newY) {
                    return null;
                }
            }
        } else {
            if (whiteCamp.contains(ori) && whiteCamp.contains(newL) && ((30 - x - y) >= (30 - newX - newY))) {
                return null;
            }
            if (!whiteCamp.contains(ori) && whiteCamp.contains(newL)) {
                return null;
            }
            if (blackCamp.contains(ori) && !blackCamp.contains(newL)) {
                return null;
            }
            if (x <= newX && y <= newY) {
                return null;
            }
            for (int i = 1; i < path.size() - 1; i++) {
                String temp = path.get(i);
                int a = Integer.parseInt(temp.split(",")[0]);
                int b = Integer.parseInt(temp.split(",")[1]);
                if (a <= newX && b <= newY) {
                    return null;
                }
            }
        }

        char[][] clone = new char[16][16];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                clone[i][j] = this.map[i][j];
            }
        }
        clone[newY][newX] = clone[y][x];
        clone[y][x] = '.';

        StringBuilder sb = new StringBuilder();
        String JOrE = "";
        if (isJump) {
            JOrE = "J ";
        } else {
            JOrE = "E ";
        }
        for (int i = 0; i < path.size() - 1; i++) {
            String temp = "";
            if (i + 2 == path.size()) {
                temp = JOrE + path.get(i) + " " + path.get(i + 1);
            } else {
                temp = JOrE + path.get(i) + " " + path.get(i + 1) + "\n";
            }
            sb.append(temp);
        }
        String str = sb.toString();
        if (!onlyMax) {
            return new Black(clone, this.color.equals("BLACK") ? "WHITE" : "BLACK", str, newX, newY);
        } else {
            return new Black(clone, this.color, str, newX, newY);
        }
    }

    public boolean isWin() {
        for (Point wp : whiteCamp) {
            if (map[wp.y][wp.x] != 'B') {
                return false;
            }
        }
        this.isFinished = true;
        return true;
    }

    public boolean isLose() {
        for (Point bp : blackCamp) {
            if (map[bp.y][bp.x] != 'W') {
                return false;
            }
        }
        this.isFinished = true;
        return true;
    }

    public String curSituation() {
        Point bMin = new Point(15, 15);
        Point wMax = new Point(0, 0);
        for (int i = 0; i < 19; i++) {
            if (bMin.x > black.get(i).x) {
                bMin.x = black.get(i).x;
            }
            if (bMin.y > black.get(i).y) {
                bMin.y = black.get(i).y;
            }
            if (wMax.x < white.get(i).x) {
                wMax.x = white.get(i).x;
            }
            if (wMax.y < white.get(i).y) {
                wMax.y = white.get(i).y;
            }
        }
        int num = 0;
        for (Point bc : blackCamp) {
            if (map[bc.y][bc.x] != '.') {
                num++;
            }
        }
        for (Point bc : whiteCamp) {
            if (map[bc.y][bc.x] != '.') {
                num++;
            }
        }
        if (num > 30 || (bMin.x >= wMax.x && bMin.y >= wMax.y)) {
            return "maxMax";
        }
        return "miniMax";
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

    public String getPathStr() {
        return pathStr;
    }

    public char[][] getMap() {
        return map;
    }

    static {
        blackCamp.add(new Point(0, 0));
        blackCamp.add(new Point(0, 1));
        blackCamp.add(new Point(0, 2));
        blackCamp.add(new Point(0, 3));
        blackCamp.add(new Point(0, 4));
        blackCamp.add(new Point(1, 0));
        blackCamp.add(new Point(1, 1));
        blackCamp.add(new Point(1, 2));
        blackCamp.add(new Point(1, 3));
        blackCamp.add(new Point(1, 4));
        blackCamp.add(new Point(2, 0));
        blackCamp.add(new Point(2, 1));
        blackCamp.add(new Point(2, 2));
        blackCamp.add(new Point(2, 3));
        blackCamp.add(new Point(3, 0));
        blackCamp.add(new Point(3, 1));
        blackCamp.add(new Point(3, 2));
        blackCamp.add(new Point(4, 0));
        blackCamp.add(new Point(4, 1));
        whiteCamp.add(new Point(15, 15));
        whiteCamp.add(new Point(15, 14));
        whiteCamp.add(new Point(15, 13));
        whiteCamp.add(new Point(15, 12));
        whiteCamp.add(new Point(15, 11));
        whiteCamp.add(new Point(14, 15));
        whiteCamp.add(new Point(14, 14));
        whiteCamp.add(new Point(14, 13));
        whiteCamp.add(new Point(14, 12));
        whiteCamp.add(new Point(14, 11));
        whiteCamp.add(new Point(13, 15));
        whiteCamp.add(new Point(13, 14));
        whiteCamp.add(new Point(13, 13));
        whiteCamp.add(new Point(13, 12));
        whiteCamp.add(new Point(12, 15));
        whiteCamp.add(new Point(12, 14));
        whiteCamp.add(new Point(12, 13));
        whiteCamp.add(new Point(11, 15));
        whiteCamp.add(new Point(11, 14));
    }
}
