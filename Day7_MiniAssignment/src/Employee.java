public class Employee {
    private final int id;
    private String name;
    private String department;
    private double salary;

    Employee(int id, String name, String department, double salary){
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDepartmnet(){
        return department;
    }
    public double getSalry(){
        return salary;
    }
    public void setSalary(double salary){
        this.salary=salary;
    }
    @Override
    public String toString(){
        return "Employee [ID = "+id+
                ", Name = "+ name +
                ", Department = "+department +
                ", Salary = "+salary + "]";
    }
}
