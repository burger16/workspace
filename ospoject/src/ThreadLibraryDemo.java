import java.util.concurrent.*;

// Shared Counter with Synchronization
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

// Worker Runnable for Thread Creation
class Worker implements Runnable {
    private final Counter counter;

    public Worker(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            counter.increment();
            System.out.println(Thread.currentThread().getName() + " incremented counter to: " + counter.getCount());
            try {
                Thread.sleep(500); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main Class
public class ThreadLibraryDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        // Thread Pool for Basic Scheduling
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Create and Start Threads
        for (int i = 1; i <= 3; i++) {
            executor.execute(new Worker(counter)); // Submit worker tasks to thread pool
        }

        executor.shutdown(); // Graceful shutdown after all tasks are completed
        executor.awaitTermination(10, TimeUnit.SECONDS);

        System.out.println("Final Counter Value: " + counter.getCount());
    }
}
