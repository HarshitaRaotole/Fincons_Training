public class Employee {
    int id;
    String name;
    Employee(int id, String name){
        this.id = id;
        this.name=name;
    }
    @Override
    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj==null || getClass() != obj.getClass()){
            return false;
        }
        Employee employee = (Employee) obj;
        return this.id == employee.id;
    }
    @Override
    public int hashCode(){
        return id;
    }
    @Override
    public String toString(){
        return "Employee{id = "+id+", name = "+name+"}";
    }
}
