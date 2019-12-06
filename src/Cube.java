public class Cube {

    private int x, y, z;
    private CColor[] colors;

    private boolean isEdge = false;
    private boolean isCorner = false;

    public Cube(int x, int y, int z, CColor[] colors, char type) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public CColor getNotWhiteColor() {
        if(isWhite() && isEdge) {
            if(colors[0].getColor() == 'W') return colors[1];
            else return colors[0];
        }
        System.out.println("Это не белое ребро");
        return null;
    }

    public CColor getWhiteColor() {
        if(isEdge && isWhite()) {
            if(colors[0].getColor() == 'W') return colors[0];
            else return colors[1];
        }
        System.out.println("Это не белое ребро");
        return null;
    }

    public boolean isCorner() {
        return isCorner;
    }

    public boolean isCenter() {
        if(!isEdge && !isCorner) return true;
        else return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public CColor[] getColors() {
        return colors;
    }
}
