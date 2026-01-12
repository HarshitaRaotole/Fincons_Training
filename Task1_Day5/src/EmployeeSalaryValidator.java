import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

class NegativeSalaryException extends RuntimeException{
    NegativeSalaryException(String message){
        super(message);
    }
}

public class EmployeeSalaryValidator {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        FileReader reader = null;
        try {
            System.out.println("Enter File Path: ");
            String path = sc.nextLine();
            reader = new FileReader(path);
            Scanner fileScanner = new Scanner(reader);

            while (fileScanner.hasNextLine()) {
                int salary = Integer.parseInt(fileScanner.nextLine());
                if (salary < 0) {
                    throw new NegativeSalaryException("Salary cannot be negative : " + salary);
                }
                System.out.println("Valid Salary : " + salary);
            }
            fileScanner.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error : File not found. Please check the path");
        }
        catch (IOException e){
            System.out.println("Error while reading the file.");
        }
        catch(NegativeSalaryException e){
            System.out.println("Validation Error : "+e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println("Invalid salary format in file");
        }
        finally {
            try{
                if(reader != null) {
                    reader.close();
                }
            }
            catch (IOException e){
                System.out.println("Error closing file");
            }
            sc.close();
        }
    }
}
