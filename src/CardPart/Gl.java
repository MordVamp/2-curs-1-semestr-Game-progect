package CardPart;

import java.util.Random;

public class Gl { // global variety for increse or less modifer of damga or other
    static int hpGL=20;
    static int energyglobal = 3; // player energy
    static int GlDW = 3; // number of draw card
    static int GlDM = 0; // player modifer of damage
    static int EGlDM = 0; // enemy modifer of damage
    static int DeckExampel = 7; // simple modifer of deck
    static boolean poison = false;
    static int infect = 0;
    static int Nstatus;
    static int insanity = 0;
    static int battlesWon = 0;
    static DisplayResolutionChecker checker = new DisplayResolutionChecker();
    static double GlDisMod = checker.getGlDisplaysizeMod();
   // static int GlDisWid=MathUtils.divideAndRound(1920, GlDisMod);
   // static int GlDisHei=MathUtils.divideAndRound(1080, GlDisMod);
//
//    static int turn = 1;
//
//    static String actionType="Sleep";

//    class Action
}
