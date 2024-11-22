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

// Базовый класс Monster
class Monster {
    int hp;
    String name;
    String description;
    Action[] actions;
    String miPath;

    public Monster(int hp, String name, String description, Action[] actions,String miPath) {
        this.hp = hp;
        this.name = name;
        this.description = description;
        this.actions = actions;
        this.miPath=miPath;
    }


}

// Класс для случайного монстра
class RandomMonster extends Monster {
    private static final Monster[] monsters = {
            new Monster(20, "Deep One", "A fish-like humanoid creature from the depths of the ocean.",
                    new Action[] { new Action("attack", 1), new Action("heal", 5) },"/CardPart/resources/monster001.png"),
            new Monster(40, "Shoggoth", "A massive, amorphous creature made of black slime.",
                    new Action[] { new Action("attack", 2), new Action("heal", 10) },"/CardPart/resources/monster002.png"),
            new Monster(60, "Mi-Go", "A fungoid, crustacean-like creature from the planet Yuggoth.",
                    new Action[] { new Action("attack", 2), new Action("heal", 15) },"/CardPart/resources/monster003.png")
    };

    public RandomMonster() {
        super(getRandomMonster().hp, getRandomMonster().name, getRandomMonster().description, getRandomMonster().actions, getRandomMonster().miPath);
    }

    private static Monster getRandomMonster() {
        return monsters[new Random().nextInt(monsters.length)];
    }
}

// Класс для босса
class Boss1 extends Monster {
    public Boss1() {
        super(100, "Cthulhu", "A gigantic, ancient entity with a squid-like head and scaly, human-like body.",
                new Action[] { new Action("attack", 30), new Action("heal", 20) },"/CardPart/resources/fireball.png");
    }
}





