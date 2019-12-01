public class CubeModel {

    public Cube [][][] cubes = new Cube[3][3][3];

    public CubeModel() {
        init();
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

    public void makeMove(String move) {
        Cube [][] layer;
        if(move.equals("R") || move == "R'") {
            layer = getLayer('R');
            if(move.equals("R")) {
                layer = layerRotate(layer, true, new char[]{'U', 'B', 'D', 'F'}, new char[]{'B', 'D', 'F', 'U'});
            } else {
                 layer = layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            }
            setLayer(layer, 'R');
        } else if(move == "L" || move == "L'") {

        }
    }


    private void init() {
        cubes[0][0][0] = new Cube(0,0,0,
                new CColor[]{ new CColor('Y','U') , new CColor('R','L'), new CColor('G','F')}, 'C');
        cubes[1][0][0] = new Cube(1,0,0,
                new CColor[]{ new CColor('Y','U') , new CColor('G','F')}, 'E');
        cubes[2][0][0] = new Cube(2,0,0,
                new CColor[]{ new CColor('Y','U') , new CColor('G','F'), new CColor('O','R')}, 'C');
        cubes[0][0][1] = new Cube(0,0,1,
                new CColor[]{ new CColor('R','L'), new CColor('G','F')}, 'E');
        cubes[1][0][1] = new Cube(1,0,1,
                new CColor[]{ new CColor('G','F')}, 'Q');
        cubes[2][0][1] = new Cube(2,0,1,
                new CColor[]{ new CColor('G','F'), new CColor('O','R')}, 'E');
        cubes[0][0][2] = new Cube(0,0,2,
                new CColor[]{ new CColor('W','D') , new CColor('R','L'), new CColor('G','F')}, 'C');
        cubes[1][0][2] = new Cube(1,0,2,
                new CColor[]{ new CColor('W','D') , new CColor('G','F')}, 'E');
        cubes[2][0][2] = new Cube(2,0,2,
                new CColor[]{ new CColor('W','D') , new CColor('G','F'), new CColor('O','R')}, 'C');
        cubes[0][1][0] = new Cube(0,1,0,
                new CColor[]{ new CColor('R','L'), new CColor('Y','U')}, 'E');
        cubes[1][1][0] = new Cube(1,1,0,
                new CColor[]{ new CColor('Y','U')}, 'Q');
        cubes[2][1][0] = new Cube(2,1,0,
                new CColor[]{ new CColor('Y','U'), new CColor('O','R')}, 'E');
        cubes[0][1][1] = new Cube(0,1,1,
                new CColor[]{ new CColor('R','L')}, 'Q');
        cubes[1][1][1] = new Cube(1,1,1,
                new CColor[]{ new CColor('Q','Q')}, 'Q');
        cubes[2][1][1] = new Cube(2,1,1,
                new CColor[]{ new CColor('O','R')}, 'Q');
        cubes[0][1][2] = new Cube(0,1,2,
                new CColor[]{ new CColor('R','L'), new CColor('W','D')}, 'E');
        cubes[1][1][2] = new Cube(1,1,2,
                new CColor[]{ new CColor('W','D')}, 'Q');
        cubes[2][1][2] = new Cube(2,1,2,
                new CColor[]{ new CColor('W','D'), new CColor('O','R')}, 'E');
        cubes[0][2][0] = new Cube(0,2,0,
                new CColor[]{ new CColor('Y','U') , new CColor('R','L'), new CColor('B','B')}, 'C');
        cubes[1][2][0] = new Cube(1,2,0,
                new CColor[]{ new CColor('Y','U') , new CColor('B','B')}, 'E');
        cubes[2][2][0] = new Cube(2,2,0,
                new CColor[]{ new CColor('Y','U') , new CColor('B','B'), new CColor('O','R')}, 'C');
        cubes[0][2][1] = new Cube(0,2,1,
                new CColor[]{ new CColor('R','L'), new CColor('B','B')}, 'E');
        cubes[1][2][1] = new Cube(1,2,1,
                new CColor[]{ new CColor('B','B')}, 'Q');
        cubes[2][2][1] = new Cube(2,2,1,
                new CColor[]{ new CColor('B','B'), new CColor('O','R')}, 'E');
        cubes[0][2][2] = new Cube(0,2,2,
                new CColor[]{ new CColor('W','D') , new CColor('R','L'), new CColor('B','B')}, 'C');
        cubes[1][2][2] = new Cube(1,2,2,
                new CColor[]{ new CColor('W','D') , new CColor('B','B')}, 'E');
        cubes[2][2][2] = new Cube(2,2,2,
                new CColor[]{ new CColor('W','D') , new CColor('B','B'), new CColor('O','R')}, 'C');

    }
}
