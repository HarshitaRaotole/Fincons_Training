import java.util.*;


public class EmployeeManagementApp {
    private static Map<Integer,Employee> employeeMap = new HashMap<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        while(true){
            System.out.println("Employee Management System ");
            System.out.println("1. Add Employee");
            System.out.println("2. View all Employees");
            System.out.println("3. Find Employee by ID");
            System.out.println("4. Update Salary");
            System.out.println("5. Remove Employee");
            System.out.println("6. Exit");
            System.out.println("Enter choice");

            int choice = sc.nextInt();
            try{
                switch (choice){
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployee();
                        break;
                    case 3:
                        findEmployeeById();
                        break;
                    case 4:
                        updateSalary();
                        break;
                    case 5:
                        removeEmployee();
                        break;
                    case 6:
                        System.out.println("Exiting Application");
                        return;
                    default:t:
                        System.out.println("Invalid choice");
                }
            }catch (EmployeeNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void addEmployee(){
        System.out.println("Enter Id : ");
        int id = sc.nextInt();
        if(employeeMap.containsKey(id)){
            System.out.println("Duplicate Employee not allowed!");
            return;
        }
        sc.nextLine();
        System.out.println("Enter Name: ");
        String name = sc.nextLine();

        System.out.println("Enter Department: ");
        String dept = sc.nextLine();

        System.out.println("Enter Salary: ");
        double salary = sc.nextDouble();
        if(salary<0){
            throw new InvalidSalaryException("Salary cannot be negative.");
        }
        Employee emp = new Employee(id,name,dept,salary);
        employeeMap.put(id,emp);
        System.out.println("Employee added successfully");
    }
    private static void viewAllEmployee(){
        if(employeeMap.isEmpty()){
            System.out.println("No Employee found");
            return;
        }
        for(Employee emp : employeeMap.values()){
            System.out.println(emp);
        }
    }
    private static void findEmployeeById() throws EmployeeNotFoundException{
        System.out.println("Enter Employee Id: ");
        int id = sc.nextInt();
        Employee emp = employeeMap.get(id);
        if(emp == null){
            throw new EmployeeNotFoundException("Employee not found with Id : "+id);
        }
        System.out.println(emp);
    }
    private static void updateSalary() throws EmployeeNotFoundException{
        System.out.println("Enter Employee Id: ");
        int id = sc.nextInt();
        Employee emp = employeeMap.get(id);
        if(emp == null){
            throw new EmployeeNotFoundException("Employee not found with Id : "+id);
        }
        System.out.println("Enter new salary: ");
        double salary = sc.nextDouble();
        if(salary<0){
            throw new InvalidSalaryException("Salary cannot be negative");
        }
        emp.setSalary(salary);
        System.out.println("Salary updated successfully");

    }
    private static void removeEmployee() throws EmployeeNotFoundException{
        System.out.println("Enter Employee id: ");
        int id = sc.nextInt();
        if(!employeeMap.containsKey(id)){
            throw new EmployeeNotFoundException("Employee not found with employee Id "+id);
        }
        employeeMap.remove(id);
        System.out.println("Employee removed Successfully");
    }
}
