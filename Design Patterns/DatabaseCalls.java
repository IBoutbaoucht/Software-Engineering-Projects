 public class DatabaseCalls {
    public static void main(String[] args) {
        Database database1 = Database.Database("db1") ;
        Database database2 = Database.Database("db2") ;

        database1.getConnected();
        database2.getConnected();

    }
    
}
