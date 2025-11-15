import java.util.concurrent.Semaphore;

public class BankSimulation {
    public static final int NUM_TELLERS = 3;
    public static final int NUM_CUSTOMERS = 5;

    public static Semaphore availableTellers = new Semaphore(0);
    public static boolean[] tellerReady = new boolean[NUM_TELLERS];
    public static Semaphore mutex = new Semaphore(1);

    public static Semaphore[] customerReady = new Semaphore[NUM_TELLERS];
    public static Semaphore[] tellerAsksTransaction = new Semaphore[NUM_TELLERS];
    public static Semaphore[] customerProvidesTransaction = new Semaphore[NUM_TELLERS];
    public static Semaphore[] transactionComplete = new Semaphore[NUM_TELLERS];

    public static String[] transactionType = new String[NUM_TELLERS];
    public static int[] customerAtTeller = new int[NUM_TELLERS];

    public static int customersServed = 0;

    public static Semaphore safe = new Semaphore(2);
    public static Semaphore manager = new Semaphore(1);

    static {
        for (int i = 0; i < NUM_TELLERS; i++) {
            customerReady[i] = new Semaphore(0);
            tellerAsksTransaction[i] = new Semaphore(0);
            customerProvidesTransaction[i] = new Semaphore(0);
            transactionComplete[i] = new Semaphore(0);
            customerAtTeller[i] = -1;
        }
    }

    public static void main(String[] args) {
        System.out.println("Bank is opening...\n");

        Thread[] tellers = new Thread[NUM_TELLERS];
        for (int i = 0; i < NUM_TELLERS; i++) {
            tellers[i] = new Thread(new Teller(i));
            tellers[i].start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Bank is now open!\n");

        Thread[] customers = new Thread[NUM_CUSTOMERS];
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            customers[i] = new Thread(new Customer(i));
            customers[i].start();
        }

        for (int i = 0; i < NUM_CUSTOMERS; i++) {
            try {
                customers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nAll customers served. Bank is closing.");
    }

}