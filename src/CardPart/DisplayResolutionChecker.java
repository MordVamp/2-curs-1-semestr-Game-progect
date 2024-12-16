package CardPart;

import java.awt.Dimension;
import java.awt.Toolkit;

public class DisplayResolutionChecker {

    public static void main(String[] args) { //test
        DisplayResolutionChecker checker = new DisplayResolutionChecker();
        double glDisplaysizeMod = checker.getGlDisplaysizeMod();
        System.out.println("GlDisplaysizeMod: " + glDisplaysizeMod);
    }

    public double getGlDisplaysizeMod() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();

        if (width >= 1920 && height >= 1080) {
            return  1.0;
        } else if (width >= 1280 && height >= 720) {
            return 1.55;
        } else {
            return 3.0;
        }
    }
}
