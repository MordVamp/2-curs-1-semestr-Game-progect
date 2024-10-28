package CardPart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Gl {//global variety for increse or less modifer of damga or other
    static int energyglobal=3;//player energy
    static int GlDW=3;// number of draw card
    static int GlDM=0;//player modifer of damage
    static int EGlDM=0;//enemy modifer of damage
    static int DeckExampel=3;//simple modifer of deck
    static boolean poison=false;
    static int infect=0;
    static int Nstatus;


}
class Card {
    int GlDM;
    int EGlDM;
    int damage;
    int heal;
    int energycard;
    String name;
    String type;
    String status;
    public Card(String name, int damage, int heal,int energycard,String type,int GlDM,int EGlDM, String status) {
        this.name = name;
        this.type=type;
        this.damage = damage;
        this.heal = heal;
        this.energycard=energycard;
        this.GlDM=GlDM;
        this.EGlDM=EGlDM;
        this.status= status;
    }
}

class Player {
    int hp;
    int energy;

    List<Card> deck;
    List<Card> hand;

    public Player() {
        this.hp = 50;
        this.energy=3;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        for (int i = 0 ; i < Gl.DeckExampel; i++) {
            deck.add(new Card("Удар ", 5, 0, 1, "attack",0,0,""));
            deck.add(new Card("Перевязка", 0, 10, 1, "heal",0,0,""));
            deck.add(new Card("Fireball", 15, 0, 2, "attack",0,0,""));
            deck.add(new Card("заточка посоха и меча +1", 0, 0, 2, "attack",1,0,""));
            deck.add(new Card("опасная перевязка", 0, 10, 0, "heal",0,1,"infect"));
        }
    }


    public void drawCard() {
        if (!deck.isEmpty()) {
            Random rand = new Random();
            Card card = deck.remove(rand.nextInt(deck.size()));
            hand.add(card);

        }
    }

    public void playCard(Card card, Monster enemy) {
        if (card.energycard <=  energy )
        {energy-=card.energycard;
            if (card.type== "attack") {enemy.hp -= card.damage+Gl.GlDM;}
            hp += card.heal;
            Gl.GlDM+= card.GlDM;
            if (card.status== ""){;}//дабы норм карты меньше ресов проца ели
            else {if (card.status=="infect") {Gl.infect=Gl.infect+3;Gl.Nstatus++;}}

            hand.remove(card);}

    }
}

class Monster {
    int hp;

    public Monster() {
        this.hp = 30;
    }
}

public class CardPart extends JFrame implements ActionListener {
    private Player player;
    private Monster monster;
    private JLabel playerEnergy ;
    private JLabel playerInfo;
    private JLabel monsterInfo;
    private JButton endturn;
    private JPanel cardPanel;

    public CardPart() {
        player = new Player();
        monster = new Monster();

        setTitle("Simple Card Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        playerInfo = new JLabel("Player HP: " + player.hp);
        playerEnergy = new JLabel("Player HP: " + player.energy);
        monsterInfo = new JLabel("Monster HP: " + monster.hp);
        endturn = new JButton("endturn");
        endturn.addActionListener(this);
        cardPanel = new JPanel();
        add(playerEnergy);
        add(playerInfo);
        add(monsterInfo);
        add(endturn);
        add(cardPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endturn) {
            for (int i = 0 ; i < Gl.GlDW; i++) {
                player.drawCard();}
            player.hp=player.hp-Gl.infect;
            if(Gl.infect>0) {Gl.infect--;}
            //Gl.energyglobal++;
            player.energy=Gl.energyglobal;
            monsterAttack();
            updateDisplay();

        }
    }

    private void updateDisplay() {
        playerInfo.setText("Player HP: " + player.hp);
        playerEnergy.setText("Player Energy"+player.energy);
        monsterInfo.setText("Monster HP: " + monster.hp);
        cardPanel.removeAll();

        for (Card card : player.hand) {
            JButton cardButton = new JButton(card.name);
            cardButton.addActionListener(new CardActionListener(card));
            cardPanel.add(cardButton);
        }

        if (player.hp <= 0) {
            JOptionPane.showMessageDialog(this, "You have been defeated!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else if (monster.hp <= 0) {
            JOptionPane.showMessageDialog(this, "You defeated the monster!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private class CardActionListener implements ActionListener {
        private final Card card;

        public CardActionListener(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            player.playCard(card, monster);
            //monsterAttack();
            updateDisplay();
        }
    }

    private void monsterAttack() {
        if (monster.hp > 0) {
            player.hp -= 15+Gl.EGlDM; //  damage
        }
    }


        public static void main() {
            SwingUtilities.invokeLater(() -> {
                CardPart game = new CardPart();
                game.setVisible(true);
            });
        }
    }

