package solver;

public class Tools {
    private Cube [][][] cubes;
    private CubeModel model;

    public Tools(CubeModel model) {
        this.model = model;
        this.cubes = model.cubes;
    }

    public boolean isYellowU(int x, int y) {
        if(cubes[x][y][0].getColorByDir('U') == 'Y') return true;
        return false;
    }

    public Cube[][] getLayer(char name) {
        Cube[][] layer = new Cube[3][3];
        switch (name) {
            case 'U':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[j][2-i][0];
                    }
                }
                break;
            case 'R':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[2][j][i];
                    }
                }
                break;
            case 'L':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[0][2-j][i];
                    }
                }
                break;
            case 'F':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[j][0][i];
                    }
                }
                break;
            case 'D':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[j][i][2];
                    }
                }
                break;
            case 'B':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[2-j][2][i];
                    }
                }
                break;
            case 'E':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[j][2-i][1];
                    }
                }
                break;
            case 'S':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[j][1][i];
                    }
                }
                break;
            case 'M':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        layer[i][j] = cubes[1][j][i];
                    }
                }
                break;
        }
        return layer;
    }

    public void setLayer(Cube[][] layer, char name) {
        switch (name) {
            case 'U':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[j][2-i][0] = layer[i][j];
                    }
                }
                break;
            case 'R':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[2][j][i] = layer[i][j];
                    }
                }
                break;
            case 'L':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[0][2-j][i] = layer[i][j];
                    }
                }
                break;
            case 'F':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[j][0][i] = layer[i][j];
                    }
                }
                break;
            case 'D':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[j][i][2] = layer[i][j];
                    }
                }
                break;
            case 'B':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[2-j][2][i] = layer[i][j];
                    }
                }
                break;
            case 'E':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[j][2-i][1] = layer[i][j];
                    }
                }
                break;
            case 'M':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[1][j][i] = layer[i][j];
                    }
                }
                break;
            case 'S':
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        cubes[j][1][i] = layer[i][j];
                    }
                }
                break;
        }
    }

    public Cube[][] layerRotate(Cube[][] layer, boolean clock, char [] before, char [] after) {
        Cube[][] newLayer = new Cube[3][3];
        if(clock) {
            newLayer[0][0] = layer[2][0];
            newLayer[0][1] = layer[1][0];
            newLayer[0][2] = layer[0][0];
            newLayer[1][0] = layer[2][1];
            newLayer[1][1] = layer[1][1];
            newLayer[1][2] = layer[0][1];
            newLayer[2][0] = layer[2][2];
            newLayer[2][1] = layer[1][2];
            newLayer[2][2] = layer[0][2];
        } else {
            newLayer[0][0] = layer[0][2];
            newLayer[0][1] = layer[1][2];
            newLayer[0][2] = layer[2][2];
            newLayer[1][0] = layer[0][1];
            newLayer[1][1] = layer[1][1];
            newLayer[1][2] = layer[2][1];
            newLayer[2][0] = layer[0][0];
            newLayer[2][1] = layer[1][0];
            newLayer[2][2] = layer[2][0];
        }
        if(before != null && after != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    CColor[] colors = newLayer[i][j].getColors();
                    for (int k = 0; k < colors.length; k++) {
                        int temp = -1;
                        for (int l = 0; l < before.length; l++) {
                            if (colors[k].getDirection() == before[l]) temp = l;
                        }
                        if(temp >= 0) colors[k].setDirection(after[temp]);
                    }
                    newLayer[i][j].setColors(colors);
                }
            }
        }
        return newLayer;
    }

    public String makeCornerEmpty() {
        String moves = "";
        while(true) {
            if(cubes[2][0][0].isCorner() && cubes[2][0][0].isWhite()) {
                moves += model.makeMove("U");
            }
            else break;
        }
        return moves;
    }

    public String makeEdgeEmpty(char c) {
        int x = 0, y = 0;
        switch (c) {
            case 'R': x = 2; y = 1; break;
            case 'B': x = 1; y = 2; break;
            case 'L': x = 0; y = 1; break;
            case 'F': x = 1; y = 0; break;
        }
        String moves = "";
        while(true) {
            if(cubes[x][y][0].isEdge() && cubes[x][y][0].isWhite() && cubes[x][y][0].getWhiteColor().getDirection() == 'U') {
                moves += model.makeMove("U");
            }
            else break;
        }
        return moves;
    }

    public static String makeRandomScramble() {
        String scr = "";
        char [] moves = new char[] {'D', 'R', 'L', 'U', 'B', 'F'};
        int prev = 'R';
        for (int i = 0; i < 25; i++) {
            int move;
            while (true) {
                move = (int) (Math.random()*5);
                if(move != prev) break;
            }
            prev = move;
            scr += moves[move];
            int option = (int) (Math.random()*3);
            switch (option) {
                case 0: scr += "2"; break;
                case 1: scr += "'"; break;
                case 2: break;
            }
            scr += " ";
        }
        return scr;
    }


}
