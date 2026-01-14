public class Employee {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id,String name, String department,double salary){
        this.id=id;
        this.name=name;
        this.department=department;
        this.salary=salary;
    }
    @Override
    public String toString(){
        return id+" "+ name+ " "+department+" "+salary;
    }



}
