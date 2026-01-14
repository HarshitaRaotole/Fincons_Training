import java.util.*;
import java.util.stream.Collectors;

public class EmployeeStreamExample {
    public static void main(String[] args){
        List<Employee> employees = List.of(
                new Employee(1,"Harshita", 50000),
                new Employee(2,"Avishkar", 45000),
                new Employee(2,"Steve", 30000),
                new Employee(2,"Nancy", 25000)
        );
        List<String> result = employees.stream()
                .filter(e -> e.getSalary() > 40000)
                .map(e ->e.getName().toUpperCase())
                .collect(Collectors.toList());
        System.out.println(result);
    }



}
