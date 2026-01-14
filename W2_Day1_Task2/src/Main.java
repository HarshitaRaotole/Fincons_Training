import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Employee> employees = List.of(
                new Employee(1,"Harshita","IT",75000),
                new Employee(2,"Steve","CS",50000),
                new Employee(3,"Nancy","IT",40000),
                new Employee(4,"Avishkar","CS",60000)
        );

        List<Employee> salaryAbove50k =
                EmployeeService.filterEmployees(
                        employees,
                        e -> e.salary > 50000
                );
        List<Employee> itEmployees =
                EmployeeService.filterEmployees(
                        employees,
                        e-> e.department.equals("IT")
                );
        System.out.println("Employee with salary > 50000 : ");
        salaryAbove50k.forEach(System.out::println);

        System.out.println("Employees from IT Department : ");
        itEmployees.forEach(System.out::println);
    }
}
