public class ThreadComparisonDemo {
    public static void main(String[] args){
        MyThread t1 = new MyThread("Thread-Extend");
        Thread t2 = new Thread(new MyRunnable(),"Thread-Runnable");
        t1.start();
        t2.start();

    }
}
