public class Employee{
    private int id;
    private String name;
    private double salary;
    private Department department;
    private Address address;


    public Employee(int id, String name, double salary, Department department, Address address){
        this.id = id;
        this.name = name;
        this.department = department;
        this.address = address;
        if(salary > 0){
            this.salary = salary;
        }else{
            this.salary = 0;
        }
        
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public double getSalary(){
        return salary;
    }
    public Department getDepartment(){
        return department;
    }
    public Address getAddress(){
        return address;
    }
}