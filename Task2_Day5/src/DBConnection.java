public class DBConnection {
    public void open(){
        System.out.println("Database connection Opened");
    }
    public void executeQuery() throws QueryExecutionException{
        System.out.println("Executing Query...");
        throw new QueryExecutionException("Error while executing query");
    }
    public void close(){
        System.out.println("Database connection closed");
    }
}
