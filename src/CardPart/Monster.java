package CardPart;
import java.util.Random;

// Класс для действий монстра
class Action {
    String type;
    int value;

    public Action(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}

// Класс Monster
class Monster {
    int hp;
    String name;
    String description;
    Action[] actions;
    String miPath;

    public Monster(int hp, String name, String description, Action[] actions, String miPath) {
        this.hp = hp;
        this.name = name;
        this.description = description;
        this.actions = actions;
        this.miPath = miPath;
    }
}

// Класс для случайного монстра
class RandomMonster extends Monster {
    private static final Monster[] monsters = {
            new Monster(30, "New Cultist SN", "A new cultist with tentacles and hands.",
                    new Action[] { new Action("attack", 1), new Action("heal", 5), new Action("shuffle_curse", 90000900+1) },
                    "/CardPart/resources/monster001.png"),
            new Monster(41, "Regular Cultist SN", "A regular cultist with tentacles using a weapon.",
                    new Action[] { new Action("attack", 1), new Action("heal", 10), new Action("block", 20) },
                    "/CardPart/resources/monster002.png"),
            new Monster(60, "Old Cultist SN", "An old cultist with tentacles.",
                    new Action[] { new Action("attack", 2), new Action("heal", 15), new Action("shuffle_curse", 90000900+2) },//9 служат разделителем числа и замешиваемых карт
                    "/CardPart/resources/monster003.png")
    };

    public RandomMonster() {
        super(getRandomMonster().hp, getRandomMonster().name,
                getRandomMonster().description, getRandomMonster().actions,
                getRandomMonster().miPath);
    }

    private static Monster getRandomMonster() {
        return monsters[new Random().nextInt(monsters.length)];
    }
}

// Класс для босса
class Boss1 extends Monster {
    private static final String[] stages = {
            "Stage 1: Awakening",
            "Stage 2: Rising",
            "Stage 3: Ascension"
    };
    private int currentStage;

    public Boss1() {
        super(100, "Avatar SN", "A gigantic, ancient entity with a squid-like head and scaly, human-like body.",
                new Action[] { new Action("attack", 30), new Action("heal", 10), new Action("shuffle_curse", 90000900+2) },
                "/CardPart/resources/fibball.png");
        this.currentStage = 0;
    }

    public String getCurrentStage() {
        return stages[currentStage];
    }

    public void updateStage(int hp) {
        if (hp < 70 && currentStage == 0) {
            currentStage = 1;
            updateHealValue(15);
        } else if (hp < 40 && currentStage == 1) {
            currentStage = 2;
            updateHealValue(20);
        }
    }

    private void updateHealValue(int newHealValue) {
        for (Action action : actions) {
            if (action.getType().equals("heal")) {
                action.value = newHealValue;
            }
        }
    }


}
