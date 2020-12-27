package lzy;

import org.junit.Test;

import java.io.*;

public class TestPlay {
    char[][] map = new char[16][16];

    // White First
    @Test
    public void m() {
        long blackTime = 0, whiteTime = 0, start, end, maxBlack = 0, maxWhite = 0, count = -60;
        BufferedWriter bw = null;
        BufferedReader br = null;
        Black b;
        White w;
        Agent a = new Agent();
        AgentWhite aw = new AgentWhite();
        int blackCount = 0;
        int whiteCount = 0;

        try {
            File srcFile = new File("input.txt");
            File destFile = new File("output.txt");
            FileReader fr = new FileReader(srcFile);
            FileWriter fw = new FileWriter(destFile);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            for (int i = 0; i < 16; i++) {
                String row = br.readLine();
                bw.write(row);
                bw.newLine();
                for (int j = 0; j < 16; j++) {
                    map[i][j] = row.charAt(j);
                }
            }
            bw.newLine();

            while (true) {
                w = new White(map, "WHITE");
                start = System.currentTimeMillis();
                White white = aw.alphaBetaSearch(w);
                end = System.currentTimeMillis();
                maxWhite = Math.max(maxWhite, end - start);
                whiteTime += (end - start);

                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        map[i][j] = white.getMap()[i][j];
                    }
                }
                whiteCount++;
                System.out.println(whiteCount + " White");
                System.out.println(end - start);
                System.out.println(white.getPathStr());
                System.out.println();

                bw.write(whiteCount + " White");
                bw.newLine();
                bw.write(white.getPathStr());
                bw.newLine();
                bw.newLine();

                if (white.isLose() || white.isWin()) {
                    break;
                }

                b = new Black(map, "BLACK");
                start = System.currentTimeMillis();
                Black black = a.alphaBetaSearch(b);
                end = System.currentTimeMillis();
                maxBlack = Math.max(maxBlack, end - start);
                blackTime += (end - start);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        map[i][j] = black.getMap()[i][j];
                        if (map[i][j] == 'B') {
                            count += (i + j);
                        }
                    }
                }

                blackCount++;
                System.out.println(blackCount + " Black");
                System.out.println(end - start);
                System.out.println(black.getPathStr());
                System.out.println();

                bw.write(blackCount + " Black");
                bw.newLine();
                bw.write(black.getPathStr());
                bw.newLine();
                bw.newLine();

                if (black.isWin() || black.isLose()) {
                    break;
                }
                System.out.println("------------------------------");
                System.out.println(count);
                System.out.println("------------------------------");
                count = -60;
            }
            System.out.println("blackTime:" + blackTime + " " + maxBlack);
            System.out.println("whiteTime:" + whiteTime + " " + maxWhite);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Black First
    @Test
    public void m2() {
        long blackTime = 0, whiteTime = 0, start, end, maxBlack = 0, maxWhite = 0, count = -60;
        BufferedWriter bw = null;
        BufferedReader br = null;
        Black b;
        White w;
        Agent a = new Agent();
        AgentWhite aw = new AgentWhite();
        int blackCount = 0;
        int whiteCount = 0;

        try {
            File srcFile = new File("input.txt");
            File destFile = new File("output.txt");
            FileReader fr = new FileReader(srcFile);
            FileWriter fw = new FileWriter(destFile);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            for (int i = 0; i < 16; i++) {
                String row = br.readLine();
                bw.write(row);
                bw.newLine();
                for (int j = 0; j < 16; j++) {
                    map[i][j] = row.charAt(j);
                }
            }
            bw.newLine();

            while (true) {
                b = new Black(map, "BLACK");
                start = System.currentTimeMillis();
                Black black = a.alphaBetaSearch(b);
                end = System.currentTimeMillis();
                maxBlack = Math.max(maxBlack, end - start);
                blackTime += (end - start);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        map[i][j] = black.getMap()[i][j];
                    }
                }

                blackCount++;
                System.out.println(blackCount + " Black");
                System.out.println(end - start);
                System.out.println(black.getPathStr());
                System.out.println();

                bw.write(blackCount + " Black");
                bw.newLine();
                bw.write(black.getPathStr());
                bw.newLine();
                bw.newLine();

                if (black.isWin() || black.isLose()) {
                    break;
                }

                w = new White(map, "WHITE");
                start = System.currentTimeMillis();
                White white = aw.alphaBetaSearch(w);
                end = System.currentTimeMillis();
                maxWhite = Math.max(maxWhite, end - start);
                whiteTime += (end - start);

                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        map[i][j] = white.getMap()[i][j];
                        if (map[i][j] == 'W') {
                            count += (i + j);
                        }
                    }
                }
                whiteCount++;
                System.out.println(whiteCount + " White");
                System.out.println(end - start);
                System.out.println(white.getPathStr());
                System.out.println();

                bw.write(whiteCount + " White");
                bw.newLine();
                bw.write(white.getPathStr());
                bw.newLine();
                bw.newLine();

                System.out.println("------------------------------");
                System.out.println(count);
                System.out.println("------------------------------");

                if (white.isLose() || white.isWin()) {
                    break;
                }

                count = -60;
            }

            System.out.println("blackTime:" + blackTime + " " + maxBlack);
            System.out.println("whiteTime:" + whiteTime + " " + maxWhite);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void m3() {
        String gameType;
        String color;
        float time;
        long blackTime = 0, whiteTime = 0, start, end, maxBlack = 0, maxWhite = 0;
        BufferedWriter bw = null;
        BufferedReader br = null;
        int blackCount = 0;
        int whiteCount = 0;
        Black b;
        White w;
        try {
            File srcFile = new File("input.txt");
            File destFile = new File("output.txt");
            FileReader fr = new FileReader(srcFile);
            FileWriter fw = new FileWriter(destFile);
            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            gameType = br.readLine();
            color = br.readLine();
            time = Float.parseFloat(br.readLine());
            for (int i = 0; i < 16; i++) {
                String row = br.readLine();
                for (int j = 0; j < 16; j++) {
                    map[i][j] = row.charAt(j);
                }
            }
            start = System.currentTimeMillis();
            if (color.equals("BLACK")) {
                Agent agent = new Agent();
                Black black = new Black(map, "BLACK");
                b = agent.alphaBetaSearch(black);
                bw.write(b.getPathStr());
            } else if (color.equals("WHITE")) {
                AgentWhite agentWhite = new AgentWhite();
                White white = new White(map, "WHITE");
                w = agentWhite.alphaBetaSearch(white);
                bw.write(w.getPathStr());
            }
            end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
