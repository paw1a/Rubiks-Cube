package solver;

public class AlgorithmController {

    public String shortSideChanges(String alg) {
        String s = "";
        String [] moves = alg.split(" ");
        int y = 0;

        for (int i = 0; i < moves.length; i++) {
            if(moves[i].contains("y")) { y++; continue;}
            if(y >= 4) y = y % 4;

            if(y == 1) {
                switch (moves[i].charAt(0)) {
                    case 'F': s += 'R'; break;
                    case 'L': s += 'F'; break;
                    case 'B': s += 'L'; break;
                    case 'R': s += 'B'; break;
                    case 'U': s += 'U'; break;
                    case 'D': s += 'D'; break;
                }
            } else if(y == 2) {
                switch (moves[i].charAt(0)) {
                    case 'F': s += 'B'; break;
                    case 'L': s += 'R'; break;
                    case 'B': s += 'F'; break;
                    case 'R': s += 'L'; break;
                    case 'U': s += 'U'; break;
                    case 'D': s += 'D'; break;
                }
            } else if(y == 3) {
                switch (moves[i].charAt(0)) {
                    case 'F': s += 'L'; break;
                    case 'L': s += 'B'; break;
                    case 'B': s += 'R'; break;
                    case 'R': s += 'F'; break;
                    case 'U': s += 'U'; break;
                    case 'D': s += 'D'; break;
                }
            } else if(y == 0){
                switch (moves[i].charAt(0)) {
                    case 'F': s += 'F'; break;
                    case 'L': s += 'L'; break;
                    case 'B': s += 'B'; break;
                    case 'R': s += 'R'; break;
                    case 'U': s += 'U'; break;
                    case 'D': s += 'D'; break;
                }
            }
            if(moves[i].length() == 2) s += moves[i].charAt(1);
            s += " ";
        }

        return s;
    }
}
