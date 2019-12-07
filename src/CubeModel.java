
public class CubeModel {

    public Cube [][][] cubes = new Cube[3][3][3];
    public String whiteCross = "";
    private Tools tools;

    public CubeModel() {
        init();
        tools = new Tools(this);
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

    public String makeAlgorithm(String s) {
        String [] moves = s.split(" ");
        for (int i = 0; i < moves.length; i++) {
            makeMove(moves[i]);
        }
        return s + " ";
    }

    public String solveYellowCross() {
        String moves = "";

        if(cubes[1][0][0].getColorByDir('U') == 'Y' && cubes[2][1][0].getColorByDir('U') == 'Y'
            && cubes[1][2][0].getColorByDir('U') == 'Y' && cubes[0][1][0].getColorByDir('U') == 'Y')
            return moves;

        for (int i = 0; i < 2; i++) {
            if(cubes[0][1][0].getColorByDir('U') == 'Y' && cubes[2][1][0].getColorByDir('U') == 'Y') {
                moves += makeAlgorithm("F R U R' U' F'");
                return moves;
            } else moves += makeMove("U");
        }

        for (int i = 0; i < 4; i++) {
            if(cubes[1][2][0].getColorByDir('U') == 'Y' && cubes[0][1][0].getColorByDir('U') == 'Y') {
                moves += makeAlgorithm("F R U R' U' R U R' U' F'");
                return moves;
            } else moves += makeMove("U");
        }
        moves += makeAlgorithm("F R U R' U' F' U2 F R U R' U' R U R' U' F'");
        return moves;
    }

    public String solveF2L() {
        String moves = "";

        for (int i = 0; i < 16; i++) {
            Cube cube = cubes[1][0][0];
            if(!cube.isYellow()) {
                for (int j = 0; j < 4; j++) {
                    if(cube.getColorByDir('F') == cubes[1][0][1].getColors()[0].getColor()) {
                        if(cube.getColorByDir('U') == cubes[2][1][1].getColors()[0].getColor())
                            moves += makeAlgorithm("U R U' R' U' F' U F");
                        else moves += makeAlgorithm("U' L' U L U F U' F'");
                        break;
                    } else moves += makeAlgorithm("y U'");
                }
            } else moves += makeMove("U");
        }

        boolean restart = false;
        for (int i = 0; i < 4; i++) {
            Cube cube = cubes[2][0][1];
            if(cube.getColorByDir('F') != cubes[1][0][1].getColors()[0].getColor()
                || cube.getColorByDir('R') != cubes[2][1][1].getColors()[0].getColor()) {
                moves += makeAlgorithm("U R U' R' U' F' U F");
                restart = true;
            }
            makeMove("y");
        }

        if(restart) moves += solveF2L();

        return moves;
    }

    public boolean checkF2L() {
        for (int i = 0; i < 4; i++) {
            Cube cube = cubes[2][0][1];
            if(cube.getColorByDir('F') != cubes[1][0][1].getColorByDir('F')
                    || cube.getColorByDir('R') != cubes[2][1][1].getColorByDir('R'))
                throw new RuntimeException("sfgsdfgsd");
        }
        return true;
    }

    public String solveWhiteCorners() {
        String moves = "";

        for (int i = 0; i < 4; i++) {
            Cube cube = cubes[2][0][0];
            if(cube.isWhite()) {
                for (int j = 0; j < 4; j++) {
                    if(     (cube.getNotWhiteColors().get(0).getColor() == cubes[1][0][1].getColors()[0].getColor()
                                && cube.getNotWhiteColors().get(1).getColor() == cubes[2][1][1].getColors()[0].getColor())
                                        ||
                            (cube.getNotWhiteColors().get(1).getColor() == cubes[1][0][1].getColors()[0].getColor()
                                    && cube.getNotWhiteColors().get(0).getColor() == cubes[2][1][1].getColors()[0].getColor())) {

                        switch (cube.getWhiteColor().getDirection()) {
                            case 'U': moves += makeAlgorithm("R U2 R' U' R U R' U'"); break;
                            case 'R': moves += makeAlgorithm("R U R' U'"); break;
                            case 'F': moves += makeAlgorithm("R' F R F'"); break;
                        }
                        break;
                    } else {
                        moves += makeMove("y");
                        moves += makeMove("U'");
                    }
                }
            } else moves += makeMove("U");
        }

        for (int i = 0; i < 3; i+=2) {
            for (int j = 0; j < 3; j+=2) {
                if(!cubes[i][j][2].isWhite()) moves += solveWhiteCorners();
            }
        }

        return moves;
    }

    public String prepareWhiteCorners() {
        String moves = "";
        for (int i = 0; i < 4; i++) {
            Cube cube = cubes[2][0][2];
            if(cube.isWhite()) {
                if(cube.getWhiteColor().getDirection() == 'D') {
                    if(!cube.getNotWhiteColors().contains(cubes[1][0][2].getNotWhiteColor())
                        && !cube.getNotWhiteColors().contains(cubes[2][1][2].getNotWhiteColor())) {
                        moves += tools.makeCornerEmpty();
                        moves += makeAlgorithm("R U R' U'");
                    }
                } else {
                    moves += tools.makeCornerEmpty();
                    moves += makeAlgorithm("R U R' U'");
                }
            }
            moves += makeMove("y");
        }
        for (int i = 0; i < 3; i+=2) {
            for (int j = 0; j < 3; j+=2) {
                if(!cubes[i][j][0].isWhite()) moves += prepareWhiteCorners();
            }
        }
        return moves;
    }

    public String solveWhiteCross() {
        Cube[][] layer;

        if(!isWhiteCrossDone()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(cubes[i][j][1].isEdge() && cubes[i][j][1].isWhite()) {
                        Cube edge = cubes[i][j][1];
                        if(i == 0 && j == 0) {
                            if(edge.getWhiteColor().getDirection() == 'F') whiteCross += tools.makeEdgeEmpty('L') + makeMove("L'");
                            else whiteCross += tools.makeEdgeEmpty('F') + makeMove("F");
                        } else if(i==0 && j==2) {
                            if(edge.getWhiteColor().getDirection() == 'B') whiteCross += tools.makeEdgeEmpty('L') + makeMove("L");
                            else whiteCross += tools.makeEdgeEmpty('B') + makeMove("B'");
                        } else if(i==2 && j==2) {
                            if(edge.getWhiteColor().getDirection() == 'B') whiteCross += tools.makeEdgeEmpty('R') + makeMove("R'");
                            else whiteCross += tools.makeEdgeEmpty('B') + makeMove("B");
                        } else {
                            if(edge.getWhiteColor().getDirection() == 'F') whiteCross += tools.makeEdgeEmpty('R') + makeMove("R");
                            else whiteCross += tools.makeEdgeEmpty('F') + makeMove("F'");
                        }
                    }
                }
            }
        }

        if(!isWhiteCrossDone()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(cubes[i][j][2].isEdge() && cubes[i][j][2].isWhite()) {
                        if(cubes[i][j][2].getWhiteColor().getDirection() == 'D') {
                            whiteCross += tools.makeEdgeEmpty(cubes[i][j][2].getNotWhiteColor().getDirection())
                                    + makeMove(String.valueOf(cubes[i][j][2].getNotWhiteColor().getDirection()));
                        }
                    }
                }
            }
        }

        if(!isWhiteCrossDone()) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k+=2) {
                        Cube cube = cubes[i][j][k];
                        if(cube.isEdge() && cube.isWhite()
                                && cube.getWhiteColor().getDirection() != 'U'
                                && cube.getWhiteColor().getDirection() != 'D') {
                            if(k == 0) {
                                whiteCross += makeMove(cube.getWhiteColor().getDirection()+"");
                                whiteCross += makeMove("U'");
                                whiteCross += makeMove(cube.getNotWhiteColor().getDirection()+"");
                            } else {
                                whiteCross += tools.makeEdgeEmpty(cube.getNotWhiteColor().getDirection())
                                        + makeMove(cube.getWhiteColor().getDirection()+"'");
                                whiteCross += makeMove("U'");
                                whiteCross += makeMove(cube.getNotWhiteColor().getDirection()+"");
                            }
                        }
                    }
                }
            }
        }

        if(!isWhiteCrossDone()) solveWhiteCross();
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (cubes[i][j][0].isEdge()) {
                        while(true) {
                            if (cubes[i][j][0].isWhite()) {
                                if(cubes[i][j][0].getNotWhiteColor().getColor() == cubes[i][j][1].getColors()[0].getColor()) {
                                    whiteCross += makeMove(cubes[i][j][0].getNotWhiteColor().getDirection() + "2");
                                    break;
                                } else {
                                    whiteCross += makeMove("U");
                                }
                            } else {
                                whiteCross += makeMove("U");
                            }
                        }
                    }
                }
            }
        }
        return whiteCross;
    }

    public boolean isWhiteCrossDone() {
        int count = 0;
        Cube[][] layer = getLayer('U');
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(layer[i][j].isEdge() && layer[i][j].getColorByDir('U') == 'W') count++;
            }
        }
        return count == 4;
    }

    public String makeMove(String move) {
        Cube [][] layer;
        if(move.contains("2")) {
            makeMove(String.valueOf(move.charAt(0)));
            makeMove(String.valueOf(move.charAt(0)));
            return move + " ";
        }
        if(move.contains("R")) {
            layer = getLayer('R');
            if(move.equals("R")) {
                layer = layerRotate(layer, true, new char[]{'U', 'B', 'D', 'F'}, new char[]{'B', 'D', 'F', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            }
            setLayer(layer, 'R');
        } else if(move.contains("L")) {
            layer = getLayer('L');
            if(move.equals("L")) {
                layer = layerRotate(layer, true, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'B', 'U', 'F', 'D'});
            }
            setLayer(layer, 'L');
        } else if(move.equals("U") || move.equals("U'")) {
            layer = getLayer('U');
            if(move.equals("U")) {
                layer = layerRotate(layer, true, new char[]{'R', 'F', 'L', 'B'}, new char[]{'F', 'L', 'B', 'R'});
            } else {
                layer = layerRotate(layer, false, new char[]{'R', 'F', 'L', 'B'}, new char[]{'B', 'R', 'F', 'L'});
            }
            setLayer(layer, 'U');
        } else if(move.equals("F") || move.equals("F'")) {
            layer = getLayer('F');
            if(move.equals("F")) {
                layer = layerRotate(layer, true, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            }
            setLayer(layer, 'F');
        } else if(move.equals("D") || move.equals("D'")) {
            layer = getLayer('D');
            if(move.equals("D")) {
                layer = layerRotate(layer, true, new char[]{'F', 'R', 'B', 'L'}, new char[]{'R', 'B', 'L', 'F'});
            } else {
                layer = layerRotate(layer, false, new char[]{'F', 'L', 'B', 'R'}, new char[]{'L', 'B', 'R', 'F'});
            }
            setLayer(layer, 'D');
        } else if(move.equals("B") || move.equals("B'")) {
            layer = getLayer('B');
            if(move.equals("B")) {
                layer = layerRotate(layer, true, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            }
            setLayer(layer, 'B');
        } else if(move.equals("E") || move.equals("E'")) {
            layer = getLayer('E');
            if(move.equals("E")) {
                layer = layerRotate(layer, true, new char[]{'R', 'F', 'L', 'B'}, new char[]{'F', 'L', 'B', 'R'});
            } else {
                layer = layerRotate(layer, false, new char[]{'R', 'F', 'L', 'B'}, new char[]{'B', 'R', 'F', 'L'});
            }
            setLayer(layer, 'E');
        } else if(move.equals("M") || move.equals("M'")) {
            layer = getLayer('M');
            if(move.equals("M")) {
                layer = layerRotate(layer, true, new char[]{'U', 'B', 'D', 'F'}, new char[]{'B', 'D', 'F', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            }
            setLayer(layer, 'M');
        } else if(move.equals("S") || move.equals("S'")) {
            layer = getLayer('S');
            if(move.equals("S")) {
                layer = layerRotate(layer, true, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            } else {
                layer = layerRotate(layer, false, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            }
            setLayer(layer, 'S');
        } else if(move.contains("y")) {
            if(move.equals("y")) makeAlgorithm("U E D'");
            else makeAlgorithm("U' E' D");
        } else if(move.contains("x")) {
            if(move.equals("x")) makeAlgorithm("R M L'");
            else makeAlgorithm("R' M' L");
        } else if(move.contains("z")) {
            if(move.equals("z")) makeAlgorithm("F S B'");
            else makeAlgorithm("F' S' B");
        }
        return move + " ";
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
