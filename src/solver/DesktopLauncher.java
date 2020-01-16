package solver;

import javax.swing.*;

public class DesktopLauncher {

    public static void main(String[] args) {
        JFrame window = new JFrame("Rubik's Cube");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new CubePanel());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
