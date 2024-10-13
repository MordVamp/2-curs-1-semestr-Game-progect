package Main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window= new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);


        window.setTitle("2D Adventure");
        GamePanel gamePanel=new GamePanel();
        window.pack();
        window.add(gamePanel);
        window.setLocationRelativeTo (null);
        gamePanel.startGameThread();
        window.setVisible(true);}}

