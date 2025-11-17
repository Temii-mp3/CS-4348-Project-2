import java.util.concurrent.Semaphore;
import java.util.Random;

public class Teller implements Runnable {
    private int id;
    private Random random = new Random();

    public Teller(int id) { this.id = id; }

    @Override
    public void run() {
        try {
            BankSimulation.printMutex.acquire();
            System.out.println("Teller " + id + ": arrives at work");
            BankSimulation.printMutex.release();

            BankSimulation.mutex.acquire();
            BankSimulation.tellerReady[id] = true;
            BankSimulation.availableTellers.release();
            System.out.println("Teller " + id + ": Ready to serve");
            BankSimulation.mutex.release();

            while (true) {
                BankSimulation.customerReady[id].acquire();

                BankSimulation.mutex.acquire();
                if (BankSimulation.customersServed >= BankSimulation.NUM_CUSTOMERS) {
                    BankSimulation.mutex.release();
                    break;
                }
                int customerId = BankSimulation.customerAtTeller[id];
                if (customerId == -1) { BankSimulation.mutex.release(); break; }
                BankSimulation.mutex.release();

                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: greets customer");
                System.out.println("Teller " + id + " [Customer " + customerId + "]: asks for transaction");
                BankSimulation.printMutex.release();

                BankSimulation.tellerAsksTransaction[id].release();
                BankSimulation.customerProvidesTransaction[id].acquire();
                String transaction = BankSimulation.transactionType[id];

                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: receives " + transaction + " transaction");
                BankSimulation.printMutex.release();

                if (transaction.equals("WITHDRAW")) {
                    BankSimulation.printMutex.acquire();
                    System.out.println("Teller " + id + " [Customer " + customerId + "]: going to Manger");
                    BankSimulation.printMutex.release();

                    BankSimulation.manager.acquire();
                    BankSimulation.printMutex.acquire();
                    System.out.println("Teller " + id + " [Customer " + customerId + "]: With Manager");
                    BankSimulation.printMutex.release();
                    Thread.sleep(random.nextInt(26) + 5);
                    BankSimulation.manager.release();
                    BankSimulation.printMutex.acquire();
                    System.out.println("Teller " + id + " [Customer " + customerId + "]: Got permission from Manager");
                    BankSimulation.printMutex.release();

                }

                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: Going to Safe");
                BankSimulation.printMutex.release();
                BankSimulation.safe.acquire();
                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: In Safe");
                BankSimulation.printMutex.release();
                Thread.sleep(random.nextInt(41) + 10);
                BankSimulation.safe.release();
                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: Done with Safe");
                BankSimulation.printMutex.release();

                BankSimulation.printMutex.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: Completes " + transaction + " transaction");
                BankSimulation.printMutex.release();
                BankSimulation.transactionComplete[id].release();
            }

            BankSimulation.printMutex.acquire();
            System.out.println("Teller " + id + " [Teller " + id + "]: going home");
            BankSimulation.printMutex.release();

        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
