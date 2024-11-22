package CardPart;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

class Card {
    int GlDM;
    int EGlDM;
    int damage;
    int heal;
    int energycard;
    String name;
    String type;
    String status;
    String imagePath; // Path to the image
    int count;

    public Card(String name, int damage, int heal, int energycard, String type, int GlDM, int EGlDM, String status, String imagePath) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.heal = heal;
        this.energycard = energycard;
        this.GlDM = GlDM;
        this.EGlDM = EGlDM;
        this.status = status;
        this.imagePath = imagePath;
        this.count = count;
    }
}

class Player {
    int hp;
    int energy;
    List<Card> deck;
    List<Card> hand;

    public Player() {
        this.hp = 19;
        this.energy = Gl.energyglobal;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        loadCardsFromJson();
    }

    private void loadCardsFromJson() {
        Gson gson = new Gson();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CardPart/cards.json");
             InputStreamReader reader = new InputStreamReader(inputStream)){
            Type listType = new TypeToken<ArrayList<Card>>() {}.getType();
            List<Card> cards = gson.fromJson(reader, listType);
            for (Card card : cards) {
                int count = card.count; // Get the count of the card
                for (int i = 0; i < count; i++) {
                    deck.add(card);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawCard() {
        if (!deck.isEmpty() && hand.size() < 8) {
            Random rand = new Random();
            Card card = deck.remove(rand.nextInt(deck.size()));
            hand.add(card);
        }
    }

    public void playCard(Card card, Monster enemy) {
        if (card.energycard <= energy) {
            energy -= card.energycard;
            if (card.type.equals("attack")) {
                enemy.hp -= card.damage + Gl.GlDM;
            }
            hp += card.heal;
            Gl.GlDM += card.GlDM;
            if (card.status.equals(""))
            {}// optimization
            else if (card.status.equals("infect")) {
                Gl.infect = Gl.infect + 3;
                Gl.Nstatus++;
            } else if (card.status.equals("insanity")) {
                Gl.insanity = Gl.insanity + 3;
                Gl.Nstatus++;}
            hand.remove(card);
        }
    }
}
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    static class MouseTrackerPanel extends JPanel {
        private Point mousePoint = new Point(0, 0);
        private Image eyeImage;
        private int circleRadius = 50; // Radius of the transparent circular area

        public MouseTrackerPanel() {
            eyeImage = new ImageIcon(getClass().getResource("/CardPart/resources/eye.png")).getImage();
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    mousePoint = e.getPoint();
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the eye image in the center
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int eyeWidth = 200; // Width of the eye image
            int eyeHeight = 200; // Height of the eye image
            g.drawImage(eyeImage, centerX - eyeWidth / 2, centerY - eyeHeight / 2, eyeWidth, eyeHeight, this);

            // Draw the transparent circular area
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setColor(new Color(255, 255, 255, 128)); // Semi-transparent white
            g2d.fillOval(centerX - circleRadius, centerY - circleRadius, circleRadius * 2, circleRadius * 2);

            // Draw the red dot within the circular area
            int dotRadius = 5;
            int dotX = Math.max(centerX - circleRadius + dotRadius, Math.min(centerX + circleRadius - dotRadius, mousePoint.x));
            int dotY = Math.max(centerY - circleRadius + dotRadius, Math.min(centerY + circleRadius - dotRadius, mousePoint.y));
            g.setColor(Color.RED);
            g.fillOval(dotX - dotRadius, dotY - dotRadius, dotRadius * 2, dotRadius * 2);
        }
    }
}

public class CardPart extends JFrame implements ActionListener {
    private Player player;private JLabel playerEnergy;private JLabel playerInfo;
    private Monster monster;private JPanel monsterPanel;  private JLabel monsterInfo;
    private JLabel monsterActionIcon; // New label for monster action

    private JButton endturn;private JPanel cardPanel; private Random random = new Random();
    private JButton pentagramButton;
    private JPanel actionPanel;
    private boolean actionsVisible = false;

    public CardPart() {
        player = new Player();
        monster = new RandomMonster();

        setTitle("Lafcraft Card Game");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon originalHpIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Heart.png"));
        ImageIcon originalEnergyIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Heart.png"));

        Image hpImage = originalHpIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        Image energyImage = originalEnergyIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

        ImageIcon hpIcon = new ImageIcon(hpImage);
        ImageIcon energyIcon = new ImageIcon(energyImage);

        //playerInfo = createIconLabel(hpIcon, String.valueOf(player.hp));
        monsterInfo = new JLabel("Monster HP: " + monster.hp);
        //playerEnergy = createIconLabel(energyIcon, String.valueOf(player.energy));
        playerInfo = new JLabel(hpIcon);
        playerEnergy  = new JLabel(energyIcon);
        monsterActionIcon = new JLabel();
        endturn = new JButton("End Turn");
        endturn.addActionListener(this);
        cardPanel = new JPanel(new FlowLayout());
        monsterPanel = new JPanel(new BorderLayout());
        monsterPanel.setOpaque(false);

        ImageIcon monsterIcon = new ImageIcon(getClass().getResource(monster.miPath));
        Image img = monsterIcon.getImage();
        Image scaledImg = img.getScaledInstance(240, 240, Image.SCALE_SMOOTH);
        ImageIcon scaledMonsterIcon = new ImageIcon(scaledImg);
        JLabel monsterImageLabel = new JLabel(scaledMonsterIcon);
        monsterPanel.add(monsterImageLabel, BorderLayout.CENTER);

        JPanel monsterInfoPanel = new JPanel(new FlowLayout());
        monsterInfoPanel.add(monsterInfo);
        monsterInfoPanel.add(monsterActionIcon);
        monsterPanel.add(monsterInfoPanel, BorderLayout.SOUTH);
        cardPanel.setOpaque(false);
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setOpaque(false);
        Color darkRed = new Color(202, 41, 41);
        playerInfo.setForeground(darkRed);
        playerEnergy.setForeground(darkRed);
        monsterInfo.setForeground(darkRed);
        endturn.setForeground(darkRed);
        topPanel.add(playerEnergy);
        topPanel.add(playerInfo);
        topPanel.add(endturn);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/CardPart/resources/Fon.png");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(cardPanel, BorderLayout.SOUTH);
        backgroundPanel.add(monsterPanel, BorderLayout.EAST);

        BackgroundPanel.MouseTrackerPanel mouseTrackerPanel = new BackgroundPanel.MouseTrackerPanel();
        mouseTrackerPanel.setPreferredSize(new Dimension(200, 200));
        mouseTrackerPanel.setOpaque(false);
        backgroundPanel.add(mouseTrackerPanel, BorderLayout.CENTER);

        // Add pentagram button
        ImageIcon pentagramIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/BloodRitual/BloodRitual.png"));
        Image pentagramImg = pentagramIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        pentagramButton = new JButton(new ImageIcon(pentagramImg));
        pentagramButton.setPreferredSize(new Dimension(50, 50));
        pentagramButton.setContentAreaFilled(false);
        pentagramButton.setBorderPainted(false);
        pentagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleActions();
            }
        });
        topPanel.add(pentagramButton, BorderLayout.WEST);

        // Add action panel
        actionPanel = new JPanel(new GridLayout(0, 1));
        actionPanel.setOpaque(false);
        backgroundPanel.add(actionPanel, BorderLayout.WEST);

        add(backgroundPanel);
        player.drawCard();
        updateDisplay();
    }

    private void toggleActions() {
        if (actionsVisible) {
            actionPanel.removeAll();
            actionsVisible = false;
        } else {
            // Add action buttons
            addActionButton("/CardPart/resources/HUD/BloodRitual/mediumattacksacrifice.png", "Action 1", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.hp -= 1;
                    monster.hp -= 3;
                    updateDisplay();
                }
            });
            addActionButton("/CardPart/resources/HUD/BloodRitual/mediumattacksacrifice.png", "Action 2", new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player.hp -= 3;
                    monster.hp -= 6;
                    updateDisplay();
                }
            });
            actionsVisible = true;
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void addActionButton(String imagePath, String tooltip, ActionListener listener) {
        ImageIcon actionIcon = new ImageIcon(getClass().getResource(imagePath));
        Image actionImg = actionIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JButton actionButton = new JButton(new ImageIcon(actionImg));
        actionButton.setPreferredSize(new Dimension(70, 70));
        actionButton.setContentAreaFilled(false);
        actionButton.setBorderPainted(false);
        actionButton.setToolTipText(tooltip);
        actionButton.addActionListener(listener);
        actionPanel.add(actionButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == endturn) {
            for (int i = 0; i < Gl.GlDW; i++) {
                player.drawCard();
            }
            player.hp = player.hp - Gl.infect;
            if (Gl.infect > 0) {
                Gl.infect--;
            }
            player.energy = Gl.energyglobal;
            monsterAttack();
            updateDisplay();
            if (Gl.insanity > 0) {
                applyInsanityMechanic();
            }
        }
    }

    private void updateDisplay() {
        if (player.hp>9&&player.hp<20) { ImageIcon originalHpIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Heart/Heart"+player.hp+".png"));
            Image hpImage = originalHpIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            playerInfo.setIcon(new ImageIcon(hpImage));}
        else {ImageIcon originalHpIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Heart"+".png"));
            Image hpImage = originalHpIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            playerInfo.setIcon(new ImageIcon(hpImage));};

        //ImageIcon originalEnergyIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Heart/Heart"+player.energy+".png"));
        if (player.energy>0&&player.energy<4)  {
            ImageIcon originalEnergyIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Stamina/Stamina"+player.energy+".png"));
            Image hpImage = originalEnergyIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            playerEnergy.setIcon(new ImageIcon(hpImage));}
        else { ImageIcon originalEnergyIcon = new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Stamina"+".png"));

            Image energyImage = originalEnergyIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);

            //playerEnergy.setIcon(createIconLabel(new ImageIcon(getClass().getResource("/CardPart/resources/HUD/Stamina.png")), String.valueOf(player.energy)).getIcon());
            playerEnergy.setIcon(new ImageIcon(energyImage));
        };

        monsterInfo.setText("Monster HP: " + monster.hp);
        cardPanel.removeAll();

        for (Card card : player.hand) {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(card.imagePath));
            Image img = originalIcon.getImage();
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            JButton cardButton = new JButton(scaledIcon);
            cardButton.setPreferredSize(new Dimension(200, 200));
            Color darkRed = new Color(202, 41, 41);
            LineBorder customBorder = new LineBorder(darkRed, 2);
            cardButton.setBorder(customBorder);
            cardButton.setContentAreaFilled(false);
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

//    private JLabel createIconLabel(ImageIcon icon, String text) {
//        JLabel label = new JLabel(icon);
//        label.setHorizontalTextPosition(JLabel.CENTER);
//        label.setVerticalTextPosition(JLabel.CENTER);
//        label.setText(text);
//        label.setForeground(Color.WHITE);
//        label.setFont(new Font("Arial", Font.BOLD, 20));
//        return label;
//    }

    private class CardActionListener implements ActionListener {
        private final Card card;

        public CardActionListener(Card card) {
            this.card = card;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            player.playCard(card, monster);
            updateDisplay();
        }
    }

    private void monsterAttack() {
        if (monster.hp > 0) {
            Action action = monster.actions[new Random().nextInt(monster.actions.length)];
            String actionType = action.getType();
            String iconPath = "/CardPart/resources/m" + actionType + ".png"; // Path to the action icon
            ImageIcon actionIcon = new ImageIcon(getClass().getResource(iconPath));
            Image img = actionIcon.getImage();
            Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Scale the icon
            ImageIcon scaledActionIcon = new ImageIcon(scaledImg);
            monsterActionIcon.setIcon(scaledActionIcon); // Update the monster action icon label
            if (actionType.equals("attack")) {
                player.hp -= action.getValue() + Gl.EGlDM; // damage
            } else if (actionType.equals("heal")) {
                monster.hp += action.getValue(); // heal
            }
        }
    }

    private void applyInsanityMechanic() {
        for (Card card : player.hand) {
            if (random.nextBoolean() && !card.name.equals("Fireball")) { // Example exception
                String[] possibleImages = {
                        "/CardPart/resources/insanity1.png",
                        "/CardPart/resources/insanity2.png",
                        "/CardPart/resources/insanity3.png"
                };
                card.imagePath = possibleImages[random.nextInt(possibleImages.length)];
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CardPart game = new CardPart();
            game.setVisible(true);
        });
    }
}
