import java.util.ArrayList;
import java.util.Random;

public class BattleArena {
    public static void main(String[] args) {
        BattleManager battleManager = BattleManager.getInstance();

        if (args.length < 2) {
            System.out.println("Usage: java BattleArena <Fighter1> <Fighter2>");
            return;
        }

        Fighter fighter1 = FighterFactory.create(args[0]);
        Fighter fighter2 = FighterFactory.create(args[1]);

        if (fighter1 == null || fighter2 == null) {
            System.out.println("Invalid fighter type(s). Please choose from: knight, archer, mage.");
            return;
        }

        battleManager.addFighter(fighter1);
        battleManager.addFighter(fighter2);

        battleManager.startBattle();
    }
}

class BattleManager {
    private static BattleManager instance;
    private Random random = new Random();
    private ArrayList<Fighter> fighters = new ArrayList<>();

    private BattleManager() {}

    public static BattleManager getInstance() {
        if (instance == null) {
            instance = new BattleManager();
        }
        return instance;
    }

    public void addFighter(Fighter fighter) {
        fighters.add(fighter);
    }

    public void startBattle() {
        System.out.println("Welcome to the Battle Arena");

        while (fighters.get(0).getHealth() > 0 && fighters.get(1).getHealth() > 0) {
            System.out.println("\n-------------------------------------------------");
            System.out.printf("%-12s HP: %4d | %-12s HP: %4d\n",
                    fighters.get(0).getName(), fighters.get(0).getHealth(),
                    fighters.get(1).getName(), fighters.get(1).getHealth());
            System.out.println("-------------------------------------------------\n");

            int attackerIndex = random.nextInt(2);
            Fighter attacker = fighters.get(attackerIndex);
            Fighter defender = fighters.get(1 - attackerIndex);

            attacker.attack(defender);
        }

        Fighter winner = (fighters.get(0).getHealth() > 0) ? fighters.get(0) : fighters.get(1);
        System.out.println("\n==================== RESULT ====================");
        System.out.printf("The winner is: %s with %d HP remaining\n", winner.getName(), winner.getHealth());
    }
}

abstract class Fighter {
    protected String name;
    protected int attackPower;
    protected int healthPoints;

    public abstract void attack(Fighter target);
    public abstract void takeDamage(int damage);

    public int getHealth() {
        return healthPoints;
    }

    public String getName() {
        return name;
    }
}

class Knight extends Fighter {
    Knight() {
        this.name = "Arthur";
        this.attackPower = 500;
        this.healthPoints = 4000;
    }

    public void attack(Fighter target) {
        System.out.printf("%s strikes %s for %d damage\n", name, target.getName(), attackPower);
        target.takeDamage(attackPower);
    }

    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints <= 0) {
            healthPoints = 0;
            System.out.println(name + " has fallen");
        }
    }
}

class Archer extends Fighter {
    Archer() {
        this.name = "Robin";
        this.attackPower = 700;
        this.healthPoints = 2800;
    }

    public void attack(Fighter target) {
        System.out.printf("%s shoots an arrow at %s for %d damage\n", name, target.getName(), attackPower);
        target.takeDamage(attackPower);
    }

    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints <= 0) {
            healthPoints = 0;
            System.out.println(name + " has fallen");
        }
    }
}

class Mage extends Fighter {
    Mage() {
        this.name = "Merlin";
        this.attackPower = 1200;
        this.healthPoints = 1500;
    }

    public void attack(Fighter target) {
        System.out.printf("%s casts a spell on %s for %d damage\n", name, target.getName(), attackPower);
        target.takeDamage(attackPower);
    }

    public void takeDamage(int damage) {
        healthPoints -= damage;
        if (healthPoints <= 0) {
            healthPoints = 0;
            System.out.println(name + " has fallen");
        }
    }
}

class FighterFactory {
    public static Fighter create(String type) {
        switch (type.toLowerCase()) {
            case "knight":
                return new Knight();
            case "archer":
                return new Archer();
            case "mage":
                return new Mage();
            default:
                return null;
        }
    }
}
