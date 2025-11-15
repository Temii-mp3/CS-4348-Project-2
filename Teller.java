import java.util.Random;

public class Teller implements Runnable {
    private int id;
    private Random random = new Random();

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

                if (transaction.equals("WITHDRAW")) {
                    System.out.println("Teller " + id + " [Customer " + customerId + "]: going to manager");
                    BankSimulation.manager.acquire();
                    System.out.println("Teller " + id + " [Customer " + customerId + "]: with manager");

                    int managerTime = random.nextInt(26) + 5;
                    Thread.sleep(managerTime);

                    System.out.println("Teller " + id + " [Customer " + customerId + "]: done with manager");
                    BankSimulation.manager.release();
                }

                System.out.println("Teller " + id + " [Customer " + customerId + "]: going to safe");
                BankSimulation.safe.acquire();
                System.out.println("Teller " + id + " [Customer " + customerId + "]: in safe");

                int sleepTime = random.nextInt(41) + 10;
                Thread.sleep(sleepTime);

                System.out.println("Teller " + id + " [Customer " + customerId + "]: done with safe");
                BankSimulation.safe.release();

                System.out.println("Teller " + id + " [Customer " + customerId + "]: transaction complete");
                BankSimulation.transactionComplete[id].release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Teller " + id + " [Teller " + id + "]: going home");
    }
}
