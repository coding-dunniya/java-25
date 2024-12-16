import java.util.concurrent.*;

public class ExecutorServiceDemo {

    public static void main(String[] args) throws Exception {
        // create executor service object
        ExecutorService service = Executors.newFixedThreadPool(3);
        RunnableTask task1 = new RunnableTask();
        RunnableTask task2 = new RunnableTask();
        RunnableTask task3 = new RunnableTask();

        Future<?> f1 = service.submit(task1);
        Future<?> f2 = service.submit(task2);
        Future<?> f3 = service.submit(task3);

        System.out.println("First task complete ==> " + f1.isDone());
        System.out.println("Second task complete ==> " + f2.isDone());
        System.out.println("Third task complete ==> " + f3.isDone());

        f1.get();
        f2.get();
        f3.get();

        CallableTask callableTask = new CallableTask();
        Future<String> cf = service.submit(callableTask);

        String threadName = cf.get();
        System.out.println("callable task is executed in thread ==> " + threadName);

        // shutdown process
        service.shutdown();

        boolean flag = service.awaitTermination(1, TimeUnit.SECONDS);
        if (!flag) {
            System.out.println("Shutting down executor service forcefully");
            service.shutdownNow();
        }
    }
}

class RunnableTask implements Runnable {

    @Override
    public void run() {
        Thread t1 = Thread.currentThread();
        String name = t1.getName();
        System.out.println("Hello world from runnable task with thread name ==> " + name);
    }
}

class CallableTask implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread t1 = Thread.currentThread();
        String name = t1.getName();
        System.out.println("Hello world from callable task with thread name ==> " + name);
        return name;
    }
}