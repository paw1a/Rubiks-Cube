import java.util.ArrayList;

public class Cube {

    private CColor[] colors;

    private boolean isEdge = false;
    private boolean isCorner = false;

    public Cube(CColor[] colors, char type) {
        this.colors = colors;
        switch (type) {
            case 'E': isEdge = true; break;
            case 'C': isCorner = true; break;
        }
    }

    public char getDirByColor(char color) {
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].getColor() == color) return colors[i].getDirection();
        }
        return 'Q';
    }

    public char getColorByDir(char direction) {
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].getDirection() == direction) return colors[i].getColor();
        }
        return 'Q';
    }

    public void setColors(CColor[] colors) {
        this.colors = colors;
    }

    public boolean isEdge() {
        return isEdge;
    }

    public boolean isWhite() {
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].getColor() == 'W') return true;
        }
        return false;
    }

    public boolean isYellow() {
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].getColor() == 'Y') return true;
        }
        return false;
    }

    public CColor getNotWhiteColor() {
        if(isWhite() && isEdge) {
            if(colors[0].getColor() == 'W') return colors[1];
            else return colors[0];
        }
        System.out.println("Это не белое ребро");
        return null;
    }

    public ArrayList<CColor> getNotWhiteColors() {
        ArrayList<CColor> col = new ArrayList<>();
        if(isWhite() && isCorner) {
            for (int i = 0; i < 3; i++) {
                if(colors[i].getColor() != 'W') col.add(colors[i]);
            }
        }
        return col;
    }

    public CColor getWhiteColor() {
        for (int i = 0; i < colors.length; i++) {
            if(colors[i].getColor() == 'W') return colors[i];
        }
        System.out.println("Это не белое ребро/угол");
        return null;
    }

    public boolean isCorner() {
        return isCorner;
    }

    public CColor[] getColors() {
        return colors;
    }
}
