package solver;

public class CColor {

    private char direction;
    private char color;

    public CColor(char c, char d) {
        this.direction = d;
        this.color = c;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public char getDirection() {
        return direction;
    }

    public char getColor() {
        return color;
    }
}
