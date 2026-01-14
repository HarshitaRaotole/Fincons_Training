import java.sql.SQLOutput;

public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 5; i++) {
            System.out.println(getName() + ": " + i);
        }
    }
}

