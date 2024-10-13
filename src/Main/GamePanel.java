package Main;

import java.awt.*;
import  javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable{

// SCREEN SETTINGS

final int originalTileSize= 16; // 16x16 tile

final int scale=3;

final int tileSize= originalTileSize*scale; // 48x48 tile
final int maxScreenCol = 16;
final int maxScreenRow=12;
final int screenWidth = tileSize*maxScreenCol; // 768 pixels
final int screenHeight=tileSize*maxScreenRow; // 576 pixels
Thread gameThread;
KeyHandler KeyH= new KeyHandler();

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;




    public GamePanel() {
    this.setPreferredSize (new Dimension(screenWidth, screenHeight));
    this.setBackground (Color.black);
    this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);

        this.setFocusable(true);
}
    public void startGameThread(){
    gameThread=new Thread(this);
    gameThread.start();}


    @Override
    public void run() { while (gameThread !=null){
        // System.out.println("Run method called");
        double drawInterval =1000000000/60  ; // 0.01666 seconds 1000000000/60
        double nextDrawTime = System.nanoTime() + drawInterval;
        update();
        repaint();
            try {

                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                Thread.sleep((long) remainingTime);
                if (remainingTime<0){remainingTime=0;}

                nextDrawTime+=drawInterval;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
    }
    public void update(){
        if (KeyH.upPressed == true)
        { playerY -= playerSpeed;

        }

        else if (KeyH.downPressed == true)
        { playerY += playerSpeed;
        }

        else if (KeyH.leftPressed == true)
        { playerX -= playerSpeed;
        }

        else if (KeyH.rightPressed == true)
        { playerX += playerSpeed;
        }
    }
    public void paintComponent (Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();

    }

}
