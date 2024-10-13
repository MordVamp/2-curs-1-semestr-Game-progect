package Main;
import java.awt.event. KeyEvent;
import java.awt.event. KeyListener;
import CardPart.CardPart;
public class KeyHandler implements KeyListener{
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped (KeyEvent e) {
    }
    @Override

    public void keyPressed (KeyEvent e) {

        int code = e.getKeyCode();

        if (code== KeyEvent.VK_W) {
            upPressed=true;
            System.out.println("s");
        }
        if (code== KeyEvent.VK_C) {
            upPressed=true;
            CardPart.main();
        }

        if (code ==KeyEvent. VK_S) {
            downPressed=true;
        }

        if (code ==KeyEvent.VK_A) {
            leftPressed=true;
        }

        if (code ==KeyEvent. VK_D) {
            rightPressed=true;
        }

        }
@Override
public void keyReleased (KeyEvent e) {

    int code = e.getKeyCode();
    if (code == KeyEvent.VK_W)
    { upPressed = false;}
    if (code == KeyEvent. VK_S)
    { downPressed = false;}
    if (code == KeyEvent. VK_A)
    { leftPressed = false;
        System.out.println("a");}
    if (code == KeyEvent. VK_D)
    { rightPressed = false;}
}}