public class Tools {
    private Cube [][][] cubes;
    private CubeModel model;

    public Tools(CubeModel model) {
        this.model = model;
        this.cubes = model.cubes;
    }

    public String makeCornerEmpty() {
        int times = 0;
        while(true) {
            if(cubes[2][0][0].isCorner() && cubes[2][0][0].isWhite()) {
                model.makeMove("U");
                times++;
            }
            else break;
        }
        if(times == 3) return "U' ";
        else if(times == 2) return "U2 ";
        else if(times == 1) return "U ";
        else return "";
    }

    public String makeEdgeEmpty(char c) {
        int x = 0, y = 0;
        switch (c) {
            case 'R': x = 2; y = 1; break;
            case 'B': x = 1; y = 2; break;
            case 'L': x = 0; y = 1; break;
            case 'F': x = 1; y = 0; break;
        }
        int times = 0;
        while(true) {
            if(cubes[x][y][0].isEdge() && cubes[x][y][0].isWhite() && cubes[x][y][0].getWhiteColor().getDirection() == 'U') {
                model.makeMove("U");
                times++;
            }
            else break;
        }
        if(times == 3) return "U' ";
        else if(times == 2) return "U2 ";
        else if(times == 1) return "U ";
        else return "";
    }

    public static String makeRandomScramble() {
        String scr = "";
        char [] moves = new char[] {'D', 'R', 'L', 'U', 'B', 'F'};
        for (int i = 0; i < 25; i++) {
            int move = (int) (Math.random()*5);
            scr += moves[move];
            int option = (int) (Math.random()*2);
            switch (option) {
                case 0: scr += "2"; break;
                case 1: scr += "'"; break;
                case 3: break;
            }
            scr += " ";
        }
        return scr;
    }


}
