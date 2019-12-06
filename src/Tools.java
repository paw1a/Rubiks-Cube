public class Tools {
    private Cube [][][] cubes;
    public Tools(Cube [][][] cubes) {
        this.cubes = cubes;
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
