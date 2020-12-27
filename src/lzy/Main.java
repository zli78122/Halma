package lzy;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String gameType;
        String color;
        float time;
        char[][] map = new char[16][16];

        BufferedReader br = null;
        BufferedWriter bw = null;
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
            char c = (color.equals("BLACK") ? 'B' : 'W');
            float count = -60;
            for (int i = 0; i < 16; i++) {
                String row = br.readLine();
                for (int j = 0; j < 16; j++) {
                    map[i][j] = row.charAt(j);
                    if (row.charAt(j) == c) {
                        count += (i + j);
                    }
                }
            }
            if (color.equals("BLACK")) {
                Agent agent = new Agent();
                Black black = new Black(map, "BLACK");
                Black b = null;
                if (gameType.equals("SINGLE")) {
                    b = agent.randomPath(black);
                } else if (gameType.equals("GAME")) {
                    if (time < 150 * (1 - count / 450)) {
                        agent.setDEPTH(2);
                    }
                    b = agent.alphaBetaSearch(black);
                }
                bw.write(b.getPathStr());
            } else if (color.equals("WHITE")) {
                AgentWhite agentWhite = new AgentWhite();
                White white = new White(map, "WHITE");
                White w = null;
                if (gameType.equals("SINGLE")) {
                    w = agentWhite.randomPath(white);
                } else if (gameType.equals("GAME")) {
                    if (time < 150 * (count / 450)) {
                        agentWhite.setDEPTH(2);
                    }
                    w = agentWhite.alphaBetaSearch(white);
                }
                bw.write(w.getPathStr());
            }
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
