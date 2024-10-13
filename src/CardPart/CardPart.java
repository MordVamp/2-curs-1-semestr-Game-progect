package CardPart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



    class Card {
        String name;
        int damage;
        int heal;

        public Card(String name, int damage, int heal) {
            this.name = name;
            this.damage = damage;
            this.heal = heal;
        }
    }

    class Player {
        int hp;
        List<Card> deck;
        List<Card> hand;

        public Player() {
            this.hp = 50;
            this.deck = new ArrayList<>();
            this.hand = new ArrayList<>();
            deck.add(new Card("Strike", 10, 0));
            deck.add(new Card("Defend", 0, 10));
            deck.add(new Card("Fireball", 15, 0));
            deck.add(new Card("Defend", 0, 10));
            deck.add(new Card("Fireball", 15, 0));
        }

        public void drawCard() {
            if (!deck.isEmpty()) {
                Random rand = new Random();
                Card card = deck.remove(rand.nextInt(deck.size()));
                hand.add(card);
            }
        }

        public void playCard(Card card, Monster enemy) {
            if (card.damage > 0) {
                enemy.hp -= card.damage;
            }
            if (card.heal > 0) {
                hp += card.heal;
            }
            hand.remove(card);
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

        private JLabel playerInfo;
        private JLabel monsterInfo;
        private JButton drawButton;
        private JPanel cardPanel;

        public CardPart() {
            player = new Player();
            monster = new Monster();

            setTitle("Simple Card Game");
            setSize(400, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new FlowLayout());

            playerInfo = new JLabel("Player HP: " + player.hp);
            monsterInfo = new JLabel("Monster HP: " + monster.hp);
            drawButton = new JButton("Draw Card");
            drawButton.addActionListener(this);
            cardPanel = new JPanel();

            add(playerInfo);
            add(monsterInfo);
            add(drawButton);
            add(cardPanel);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == drawButton) {
                player.drawCard();
                monsterAttack();
                updateDisplay();
            }
        }

        private void updateDisplay() {
            playerInfo.setText("Player HP: " + player.hp);
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
                player.hp -= 5; // Simple fixed damage
            }
        }

        public static void main() {
            SwingUtilities.invokeLater(() -> {
                CardPart game = new CardPart();
                game.setVisible(true);
            });
        }
    }

