package solver;

public class CubeModel {

    public Cube [][][] cubes = new Cube[3][3][3];
    public Tools tools;

    public CubeModel() {
        init();
        tools = new Tools(this);
    }
    
    public String makeAlgorithm(String s) {
        String [] moves = s.split(" ");
        for (int i = 0; i < moves.length; i++) {
            s += makeMove(moves[i]);
        }
        return s;
    }

    public boolean isUCornersDone() {
        boolean b = true;
        for (int i = 0; i < 4; i++) {
            if(cubes[0][0][0].getColorByDir('F') != cubes[2][0][0].getColorByDir('F')) b = false;
            makeMove("U");
        }
        return b;
    }

    public boolean isCubeDone() {
        boolean b = false;
        int rotates = 0;
        for (int i = 0; i < 4; i++) {
            if(cubes[1][0][1].getColorByDir('F') != 'G') { makeMove("y"); rotates++; }
        }
        char [] dirs = new char[]{'F', 'R', 'B', 'L', 'U', 'D'};
        char [] colors = new char[]{'G', 'O', 'B', 'R', 'Y', 'W'};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < cubes[i][j][k].getColors().length; l++) {
                        CColor color = cubes[i][j][k].getColors()[l];
                        switch (color.getDirection()) {
                            case 'F': if(color.getColor() != 'G') b = true; break;
                            case 'R': if(color.getColor() != 'O') b = true; break;
                            case 'B': if(color.getColor() != 'B') b = true; break;
                            case 'L': if(color.getColor() != 'R') b = true; break;
                            case 'U': if(color.getColor() != 'Y') b = true; break;
                            case 'D': if(color.getColor() != 'W') b = true; break;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4 - rotates; i++) makeMove("y");

        return !b;
    }

    public boolean isWhiteCrossDone() {
        int count = 0;
        Cube[][] layer = tools.getLayer('U');
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(layer[i][j].isEdge() && layer[i][j].getColorByDir('U') == 'W') count++;
            }
        }
        return count == 4;
    }

    public String solvePLLEdges() {
        String moves = "";

        if(isCubeDone()) return moves;

        for (int i = 0; i < 4; i++) {
            if(cubes[0][0][0].getColorByDir('F') == cubes[1][0][0].getColorByDir('F')
                    && cubes[1][0][0].getColorByDir('F') == cubes[1][0][1].getColorByDir('F')) {
                if(cubes[2][1][0].getColorByDir('R') == cubes[0][1][1].getColorByDir('L'))
                    return moves + makeAlgorithm("R' U R' U' R' U' R' U R U R2");
                else return moves + makeAlgorithm("R2 U' R' U' R U R U R U' R");
            } else moves += makeMove("y");
        }

        for (int i = 0; i < 4; i++) {
            if(cubes[1][0][0].getColorByDir('F') == cubes[1][2][1].getColorByDir('B')
                    && cubes[1][2][0].getColorByDir('B') == cubes[1][0][1].getColorByDir('F'))
                return moves + makeAlgorithm("L R U2 L' R' F' B' U2 F B");
        }

        if(cubes[1][0][0].getColorByDir('F') == cubes[0][1][1].getColorByDir('L'))
            return moves + makeAlgorithm("R' U' R U' R U R U' R' U R U R2 U' R' U2");
        else return moves + makeAlgorithm("y R' U' R U' R U R U' R' U R U R2 U' R' U2");
    }

    public String solvePLLCorners() {
        String moves = "";

        if(!isUCornersDone()) {
            boolean b = false;
            for (int i = 0; i < 4; i++) {
                if (cubes[2][0][0].getColorByDir('R') == cubes[2][2][0].getColorByDir('R')) {
                    moves += makeAlgorithm("R2 B2 R F R' B2 R F' R");
                    b = true;
                    break;
                }
                moves += makeMove("U");
            }
            if (!b) moves += makeAlgorithm("F R U' R' U' R U R' F' R U R' U' R' F R F'");
        }

        for (int i = 0; i < 4; i++) {
            if(cubes[0][0][0].getColorByDir('F') != cubes[0][0][1].getColorByDir('F'))
                moves += makeMove("U");
            else break;
        }

        return moves;
    }

    public String solveOLL() {
        String alg = "", moves = "";
        boolean b = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(cubes[i][j][0].getColorByDir('U') != 'Y') b = true;
            }
        }
        if(b) {
            for (int i = 0; i < 4; i++) {
                if (tools.isYellowU(0, 0) && tools.isYellowU(2, 0)
                        && cubes[0][2][0].getDirByColor('Y') == 'B' && cubes[2][2][0].getDirByColor('Y') == 'B')
                {alg = "R2 D' R U2 R' D R U2 R"; break;}
                else if (tools.isYellowU(0, 0) && tools.isYellowU(2, 0)
                        && cubes[0][2][0].getDirByColor('Y') == 'L' && cubes[2][2][0].getDirByColor('Y') == 'R')
                {alg = "R' F' R U R' U' R' F R U R"; break;}
                else if (tools.isYellowU(0, 2) && tools.isYellowU(2, 0)
                        && cubes[0][0][0].getDirByColor('Y') == 'L' && cubes[2][2][0].getDirByColor('Y') == 'B')
                {alg = "R2 D' R U' R' D R U R"; break;}
                else if (tools.isYellowU(2, 2) && cubes[2][0][0].getDirByColor('Y') == 'R'
                        && cubes[0][0][0].getDirByColor('Y') == 'F')
                {alg = "R U2 R' U' R U' R'"; break; }
                else if (tools.isYellowU(0, 0) && cubes[2][0][0].getDirByColor('Y') == 'F'
                        && cubes[2][2][0].getDirByColor('Y') == 'R')
                {alg = "R U R' U R U2 R'"; break; }
                else if (cubes[0][0][0].getDirByColor('Y') == 'L' && cubes[0][2][0].getDirByColor('Y') == 'L'
                        && !tools.isYellowU(2, 0) && !tools.isYellowU(2, 2)) {
                    if (cubes[2][0][0].getDirByColor('Y') == 'R' && cubes[2][2][0].getDirByColor('Y') == 'R')
                    {alg = "R U R' U R U' R' U R U2 R'"; break; }
                    else {alg = "R U2 R2 U' R2 U' R2 U2 R"; break; }
                }
                moves += makeMove("U");
            }
        }
        moves += makeAlgorithm(alg);

        return moves;
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
        String whiteCross = "";
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

        if(!isWhiteCrossDone()) whiteCross += solveWhiteCross();
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

    public String makeMove(String move) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Cube [][] layer;
        if(move.contains("2")) {
            makeMove(String.valueOf(move.charAt(0)));
            makeMove(String.valueOf(move.charAt(0)));
            return move + " ";
        }
        if(move.contains("R")) {
            layer = tools.getLayer('R');
            if(move.equals("R")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'B', 'D', 'F'}, new char[]{'B', 'D', 'F', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            }
            tools.setLayer(layer, 'R');
        } else if(move.contains("L")) {
            layer = tools.getLayer('L');
            if(move.equals("L")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'B', 'U', 'F', 'D'});
            }
            tools.setLayer(layer, 'L');
        } else if(move.equals("U") || move.equals("U'")) {
            layer = tools.getLayer('U');
            if(move.equals("U")) {
                layer = tools.layerRotate(layer, true, new char[]{'R', 'F', 'L', 'B'}, new char[]{'F', 'L', 'B', 'R'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'R', 'F', 'L', 'B'}, new char[]{'B', 'R', 'F', 'L'});
            }
            tools.setLayer(layer, 'U');
        } else if(move.equals("F") || move.equals("F'")) {
            layer = tools.getLayer('F');
            if(move.equals("F")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            }
            tools.setLayer(layer, 'F');
        } else if(move.equals("D") || move.equals("D'")) {
            layer = tools.getLayer('D');
            if(move.equals("D")) {
                layer = tools.layerRotate(layer, true, new char[]{'F', 'R', 'B', 'L'}, new char[]{'R', 'B', 'L', 'F'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'F', 'L', 'B', 'R'}, new char[]{'L', 'B', 'R', 'F'});
            }
            tools.setLayer(layer, 'D');
        } else if(move.equals("B") || move.equals("B'")) {
            layer = tools.getLayer('B');
            if(move.equals("B")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            }
            tools.setLayer(layer, 'B');
        } else if(move.equals("E") || move.equals("E'")) {
            layer = tools.getLayer('E');
            if(move.equals("E")) {
                layer = tools.layerRotate(layer, true, new char[]{'R', 'F', 'L', 'B'}, new char[]{'F', 'L', 'B', 'R'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'R', 'F', 'L', 'B'}, new char[]{'B', 'R', 'F', 'L'});
            }
            tools.setLayer(layer, 'E');
        } else if(move.equals("M") || move.equals("M'")) {
            layer = tools.getLayer('M');
            if(move.equals("M")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'B', 'D', 'F'}, new char[]{'B', 'D', 'F', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'F', 'D', 'B'}, new char[]{'F', 'D', 'B', 'U'});
            }
            tools.setLayer(layer, 'M');
        } else if(move.equals("S") || move.equals("S'")) {
            layer = tools.getLayer('S');
            if(move.equals("S")) {
                layer = tools.layerRotate(layer, true, new char[]{'U', 'R', 'D', 'L'}, new char[]{'R', 'D', 'L', 'U'});
            } else {
                layer = tools.layerRotate(layer, false, new char[]{'U', 'L', 'D', 'R'}, new char[]{'L', 'D', 'R', 'U'});
            }
            tools.setLayer(layer, 'S');
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
        cubes[0][0][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('R','L'), new CColor('G','F')}, 'C');
        cubes[1][0][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('G','F')}, 'E');
        cubes[2][0][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('G','F'), new CColor('O','R')}, 'C');
        cubes[0][0][1] = new Cube(new CColor[]{ new CColor('R','L'), new CColor('G','F')}, 'E');
        cubes[1][0][1] = new Cube(new CColor[]{ new CColor('G','F')}, 'Q');
        cubes[2][0][1] = new Cube(new CColor[]{ new CColor('G','F'), new CColor('O','R')}, 'E');
        cubes[0][0][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('R','L'), new CColor('G','F')}, 'C');
        cubes[1][0][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('G','F')}, 'E');
        cubes[2][0][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('G','F'), new CColor('O','R')}, 'C');
        cubes[0][1][0] = new Cube(new CColor[]{ new CColor('R','L'), new CColor('Y','U')}, 'E');
        cubes[1][1][0] = new Cube(new CColor[]{ new CColor('Y','U')}, 'Q');
        cubes[2][1][0] = new Cube(new CColor[]{ new CColor('Y','U'), new CColor('O','R')}, 'E');
        cubes[0][1][1] = new Cube(new CColor[]{ new CColor('R','L')}, 'Q');
        cubes[1][1][1] = new Cube(new CColor[]{ new CColor('Q','Q')}, 'Q');
        cubes[2][1][1] = new Cube(new CColor[]{ new CColor('O','R')}, 'Q');
        cubes[0][1][2] = new Cube(new CColor[]{ new CColor('R','L'), new CColor('W','D')}, 'E');
        cubes[1][1][2] = new Cube(new CColor[]{ new CColor('W','D')}, 'Q');
        cubes[2][1][2] = new Cube(new CColor[]{ new CColor('W','D'), new CColor('O','R')}, 'E');
        cubes[0][2][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('R','L'), new CColor('B','B')}, 'C');
        cubes[1][2][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('B','B')}, 'E');
        cubes[2][2][0] = new Cube(new CColor[]{ new CColor('Y','U') , new CColor('B','B'), new CColor('O','R')}, 'C');
        cubes[0][2][1] = new Cube(new CColor[]{ new CColor('R','L'), new CColor('B','B')}, 'E');
        cubes[1][2][1] = new Cube(new CColor[]{ new CColor('B','B')}, 'Q');
        cubes[2][2][1] = new Cube(new CColor[]{ new CColor('B','B'), new CColor('O','R')}, 'E');
        cubes[0][2][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('R','L'), new CColor('B','B')}, 'C');
        cubes[1][2][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('B','B')}, 'E');
        cubes[2][2][2] = new Cube(new CColor[]{ new CColor('W','D') , new CColor('B','B'), new CColor('O','R')}, 'C');
    }
}
