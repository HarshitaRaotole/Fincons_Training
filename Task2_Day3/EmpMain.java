public class EmpMain {
    public static void main(String args[]){
        Department dept = new Department(101, "IT");
        Address addr = new Address("Mumbai", "Maharashtra", 400071);
        Employee E1 = new Employee(1, "Harshita", 50000, dept, addr);
        Employee E2 = new Employee(102,"Swarnika",50000,dept, addr);



            printEmployee(E1, addr);
            printEmployee(E2, addr);
    }
    
    public static void printEmployee(Employee emp, Address addr){
        System.out.println("Employee Id : "+ emp.getId());
        System.out.println("Employee Name : "+ emp.getName());
        System.out.println("Employee Salary : "+ emp.getSalary());
        System.out.println("Employee Department Id : "+ emp.getDepartment().getDeptId());
        System.out.println("Employee Department : "+ emp.getDepartment().getDeptName());
        System.out.println("Employee Department : "+ addr.getCity());
        System.out.println("Employee Department : "+ addr.getState());
        System.out.println("Employee Department : "+ addr.getPinCode());
        
    }
}   
