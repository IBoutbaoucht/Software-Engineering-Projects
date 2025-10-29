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