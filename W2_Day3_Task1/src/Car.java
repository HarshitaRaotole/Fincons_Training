public class Car {
    private Engine engine;
    public Car(){
        engine = new Engine();
    }
    public void drive(){
        engine.start();
        System.out.println("Car is moving");
    }

}
