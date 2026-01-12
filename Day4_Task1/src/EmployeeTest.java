import java.util.*;
public class EmployeeTest {
    public static void main(String[] args){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Harshita"));
        employees.add(new Employee(2, "Steve"));
        employees.add(new Employee(1,"Nancy"));
        employees.add(new Employee(3,"Mike"));
        employees.add(new Employee(3,"Eleven"));

        Set<Employee> uniqueEmployees = new LinkedHashSet<>(employees);
        for(Employee e : uniqueEmployees){
            System.out.println(e);
        }
        }
    }


