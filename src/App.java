package src;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        int broadheight = 640;
        int broadwidth = 360;
        JFrame frame = new JFrame("Flappy Bird");
        frame.setSize(broadwidth,broadheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        frame.requestFocus();
        frame.setVisible(true);

    }
}
