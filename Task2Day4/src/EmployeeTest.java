import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class EmployeeTest {
    public static void main(String[] args){
        Map<Integer,Employee> employeeMap = new HashMap<>();
        employeeMap.put(1, new Employee(1,"Harshita"));
        employeeMap.put(2, new Employee(2,"Steve"));
        employeeMap.put(3,new Employee(3,"Nancy"));
        employeeMap.put(4, new Employee(4,"Mike"));
        employeeMap.put(5, new Employee(5,"Eleven"));

        int searchId =10;
        Employee employee = employeeMap.get(searchId);
        if(employee != null){
            System.out.println(employee);
        }
        else{
            System.out.println("Employee not found");
        }
    }
}
