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

    public boolean isCorner() {
        return isCorner;
    }

    public boolean isCenter() {
        if(!isEdge && !isCorner) return true;
        else return false;
    }

    public CColor[] getColors() {
        return colors;
    }
}
