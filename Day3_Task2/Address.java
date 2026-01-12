public class Address {
    private String city;
    private String state;
    private int pincode;

     public Address(String city, String state, int pincode) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }


    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public int getPinCode(){
        return pincode;
    }
}
