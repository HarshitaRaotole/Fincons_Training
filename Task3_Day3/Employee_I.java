class Employee_I extends Address_I {
    private int id;
    private String name;

    public Employee_I(int id, String name, String city, String state, int pincode) {
        super(city, state, pincode);
        this.id = id;
        this.name = name;
    }

    public void printDetails() {
        System.out.println("Employee ID   : " + id);
        System.out.println("Employee Name : " + name);
        System.out.println("City          : " + city);
        System.out.println("State         : " + state);
        System.out.println("Pincode       : " + pincode);
    }
}
