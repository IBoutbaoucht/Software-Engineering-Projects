# Design Patterns TP Report

## Exercise 1

Code:

```java
// Database.java
// Implementing a database that has attribute name , and can only have one instance : singleton design pattern .
public class Database {

    private static Database Instance ;
    String name ;

    private Database(String name){
        this.name = name ;
    }
    public static Database Database(String name) {
        if (Instance == null) {
            Instance = new Database(name) ;
        }
        return Instance ;
    }

    public void getConnected(){
        System.out.println("You are connected to the database " + name);
    }   
}

// DatabaseCalls.java

 public class DatabaseCalls {
    public static void main(String[] args) {
        Database database1 = Database.Database("db1") ;
        Database database2 = Database.Database("db2") ;

        database1.getConnected();
        database2.getConnected();

    }
    
}

```

### Output:

```java
    You are connected to the database db1 
    You are connected to the database db1
```

## Exercise 2

### Part 1 : Naive method

Code:

![1761734514084](TP-Design-Patterns/Client-first-code.png)


![1761734514084](TP-Design-Patterns/Program2-code.png)

> Problem: This code is not well structured and is difficult to maintain and make changes !

### Part 2: Applying design patterns


Class Diagram:

![alt text](TP-Design-Patterns/Factory-Class-Diagram.png)

Code:

```java

interface DoesGo {
    public void go() ;
}


// adding Program4 :
/* add a class Program4
 * add a switch case
 * Done !!
 */

class ProgramFactory {

    public static DoesGo getProgram(int index){
        switch (index) {
            case 1:
                return new Program1() ;
            case 2 :
                return new Program2() ;
            case 3 :
                return new Program3() ;
            default:
                return new Program1(); // default
        }
    }
}

public class Client {

    public static void main(String args[]) {
        int index = Integer.parseInt(args[0]) ;
        
        DoesGo program = ProgramFactory.getProgram(index) ;
        program.go();
    }

}

public class Program1 implements DoesGo {
    
    public Program1(){
        //
    }

    public void go(){
        System.out.println("Program 1 :Goooooooooo is running") ;
    }
}

```

Now adding the `Program4` is a piece of cake, we just need to create it and add 2 lines to the factory:

```java
class Program4 implements DoesGo{
    public Program4() {
    // The constructor does nothing .
    }
    public void go() {
        System.out.println("Program 4 :Goooooooooo is running");
    }
}


class ProgramFactory {

    public static DoesGo getProgram(int index){
        switch (index) {
            case 1:
                return new Program1() ;
            case 2 :
                return new Program2() ;
            case 3 :
                return new Program3() ;
            case 4: // <-- Added !!
                return new Program4();
            default:
                return new Program1(); // default
        }
    }
}

```

## Exercise 3

Code for all classes:
  
```java
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

```

### Example Usage : 

```java
javac BattleArena.java
java BattleArena knight mage
```
  
### Output:

```java

Welcome to the Battle Arena

-------------------------------------------------
Arthur       HP: 4000 | Merlin       HP: 1500
-------------------------------------------------

Merlin casts a spell on Arthur for 1200 damage
Arthur strikes Merlin for 500 damage
...

==================== RESULT ====================
The winner is: Arthur with 500 HP remaining

```