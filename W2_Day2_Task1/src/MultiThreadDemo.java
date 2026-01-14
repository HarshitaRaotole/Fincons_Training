public class MultiThreadDemo {
    public static void main(String[] args){
        long processId = ProcessHandle.current().pid();
        System.out.println("Main Process ID : "+ processId);

        MyTask task1 = new MyTask();
        MyTask task2 = new MyTask();
        MyTask task3 = new MyTask();

        Thread t1 = new Thread(task1,"Thread-1");
        Thread t2 = new Thread(task2,"Thread-2");
        Thread t3 = new Thread(task3,"Thread-3");

        t1.start();
        t2.start();
        t3.start();


    }
}
