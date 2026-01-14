public class MyTask implements Runnable{
    @Override
    public void run(){
        Thread currentThread= Thread.currentThread();
        long processId = ProcessHandle.current().pid();

        System.out.println("ThreadName : "+ currentThread.getName());
        System.out.println("ThreadId : "+ currentThread.getName()+" "+currentThread.getId());
        System.out.println("ProcessId : "+ processId);

    }

}
