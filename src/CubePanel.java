import com.sun.deploy.util.BlackList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class CubePanel extends JPanel implements Runnable, KeyListener {

    private Thread thread;
    private boolean running;

    private static BufferedImage image;
    private static Graphics2D g;

    private int FPS = 30;
    private int targetTime = 1000 / FPS;

    private CubeModel cube;

    public CubePanel() {
        super();
        setPreferredSize(new Dimension(1080, 720));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    private void init() {
        running = true;

        image = new BufferedImage(1080, 720, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();

        cube = new CubeModel();
        cube.makeAlgorithm("F R U' R' U' R U R' F' R U R' U' R' F R F'");
    }


    public void update() {

    }

    public void draw() {
        int offset = 3, size = 50;
        char[] c = new char[] {'B', 'L', 'U', 'R', 'D', 'F'};
        Cube[][] layer = cube.getLayer(c[0]);
        layer = cube.layerRotate(layer, true, null, null);
        layer = cube.layerRotate(layer, true, null, null);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g.setColor(getRGBColor(layer[i][j].getColorByDir(c[0])));
                g.fillRect(size*3 + offset*3 + 50 + j*size + j*offset, i*50+i*offset + 50, size, size);
            }
        }
        for (int i = 1; i < 5; i++) {
            layer = cube.getLayer(c[i]);
            if(i == 4) {
                layer = cube.layerRotate(layer, true, null, null);
                layer = cube.layerRotate(layer, true, null, null);
            } else if(i == 1) layer = cube.layerRotate(layer, true, null, null);
              else if(i == 3) layer = cube.layerRotate(layer, false, null, null);
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    g.setColor(getRGBColor(layer[j][k].getColorByDir(c[i])));
                    g.fillRect(size*(i-1)*3 + offset*(i-1)*3 + 50 + k*size + k*offset, j*50+j*offset + 50 + 3*size + 3*offset, size, size);
                }
            }
        }
        layer = cube.getLayer(c[5]);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                g.setColor(getRGBColor(layer[i][j].getColorByDir(c[5])));
                g.fillRect(size*3 + offset*3 + 50 + j*size + j*offset, i*size+i*offset + 50 + size*6 + 6*offset, size, size);
            }
        }


    }

    public void drawToScreen() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void run() {
        init();

        long startTime;
        long urdTime;
        long waitTime;

        while(running) {

            startTime = System.nanoTime();
            //System.out.println("Before update "+System.currentTimeMillis());
            update();
            //System.out.println("After update "+System.currentTimeMillis());
            draw();
            //System.out.println("After draw "+System.currentTimeMillis());
            drawToScreen();
            //System.out.println("After draw to screen "+System.currentTimeMillis());

            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            if(waitTime < 0) waitTime = 0;
            try{
                Thread.sleep(waitTime);
            } catch(Exception e) {}
            //System.out.println("After wait"+System.currentTimeMillis());
        }
    }

    public Color getRGBColor(char color) {
        switch (color) {
            case 'Y': return Color.YELLOW;
            case 'W': return Color.WHITE;
            case 'R': return Color.RED;
            case 'O': return Color.ORANGE;
            case 'G': return Color.GREEN;
            case 'B': return Color.BLUE;
        }
        return Color.BLACK;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
