package CardPart;

import javax.swing.*;
import java.awt.*;

public class IUtils {//IUtils ImageUtils

    public  Image CSI(String path, int width, int height) { //createScaledImageLabel
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
        Image img = originalIcon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);
        return scaledImg;
    }
}
