package solver;

public class AlgorithmController {
    public String shortAlgorithm(String str) {
        if(str.equals("")) return "";
        String [] moves = str.split(" ");
        char prev = moves[0].charAt(0);
        String res = "";
        int repeat;
        if(moves[0].contains("'")) repeat = -1;
        else if(moves[0].contains("2")) repeat = 2;
        else repeat = 1;

        for (int i = 1; i < moves.length; i++) {
            if(moves[i].charAt(0) == prev) {
                if(moves[i].contains("'")) repeat--;
                else if(moves[i].contains("2")) repeat += 2;
                else repeat++;
            } else {
                res += getMove(prev, repeat);
                prev = moves[i].charAt(0);
                if(moves[i].contains("'")) repeat = -1;
                else if(moves[i].contains("2")) repeat = 2;
                else repeat = 1;
            }
        }
        res += getMove(prev, repeat);
        return res;
    }

    private String getMove(char c, int n) {
        String s = "";
        n = n % 4;

        if(n == 0) return "";
        else if(n == 1 || n == -3) return c+" ";
        else if(n == 3 || n == -1) return c+"' ";
        else return c+"2 ";
    }
}
