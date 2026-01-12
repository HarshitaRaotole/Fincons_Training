public class DBTest {
    public static void main(String[] args){
        DBConnection db = new DBConnection();
        try{
            db.open();

            db.executeQuery();
        }
        catch(QueryExecutionException e){
            System.out.println("Exception caught : "+ e.getMessage());
        }
        finally{
            db.close();
        }
    }

}
