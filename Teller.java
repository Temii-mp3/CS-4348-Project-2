import java.util.concurrent.Semaphore;

public class Teller implements Runnable {
    private int id;

    public Teller(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Teller " + id + " [Teller " + id + "]: arrives at work");

        try {
            BankSimulation.mutex.acquire();
            BankSimulation.tellerReady[id] = true;
            System.out.println("Teller " + id + " [Teller " + id + "]: ready to serve");
            BankSimulation.availableTellers.release();
            BankSimulation.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                BankSimulation.mutex.acquire();
                if (BankSimulation.customersServed >= BankSimulation.NUM_CUSTOMERS) {
                    BankSimulation.mutex.release();
                    break;
                }
                BankSimulation.mutex.release();

                BankSimulation.customerReady[id].acquire();

                int customerId = BankSimulation.customerAtTeller[id];
                System.out.println("Teller " + id + " [Customer " + customerId + "]: greets customer");

                System.out.println("Teller " + id + " [Customer " + customerId + "]: asks for transaction");
                BankSimulation.tellerAsksTransaction[id].release();

                BankSimulation.customerProvidesTransaction[id].acquire();
                String transaction = BankSimulation.transactionType[id];
                System.out.println("Teller " + id + " [Customer " + customerId + "]: receives " + transaction + " transaction");

                System.out.println("Teller " + id + " [Customer " + customerId + "]: processes transaction");
                Thread.sleep(200);

                System.out.println("Teller " + id + " [Customer " + customerId + "]: transaction complete");
                BankSimulation.transactionComplete[id].release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Teller " + id + " [Teller " + id + "]: going home");
    }
}
