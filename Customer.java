import java.util.concurrent.Semaphore;
import java.util.Random;

public class Customer implements Runnable {
    private int id;
    private Random random = new Random();

    public Customer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            String transaction = random.nextBoolean() ? "DEPOSIT" : "WITHDRAW";
            System.out.println("Customer " + id + " [Customer " + id + "]: wants to make a " + transaction);

            Thread.sleep((int)(Math.random() * 100));

            System.out.println("Customer " + id + " [Customer " + id + "]: enters the bank");

            BankSimulation.availableTellers.acquire();

            int myTeller = -1;
            BankSimulation.mutex.acquire();
            for (int i = 0; i < BankSimulation.NUM_TELLERS; i++) {
                if (BankSimulation.tellerReady[i]) {
                    myTeller = i;
                    BankSimulation.tellerReady[i] = false;
                    BankSimulation.customerAtTeller[i] = id;
                    break;
                }
            }
            BankSimulation.mutex.release();

            if (myTeller == -1) {
                System.out.println("ERROR: Customer " + id + " couldn't find teller!");
                return;
            }

            System.out.println("Customer " + id + " [Teller " + myTeller + "]: selects teller");
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: introduces self");
            BankSimulation.customerReady[myTeller].release();

            BankSimulation.tellerAsksTransaction[myTeller].acquire();

            BankSimulation.transactionType[myTeller] = transaction;
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: provides " + transaction + " transaction");
            BankSimulation.customerProvidesTransaction[myTeller].release();

            BankSimulation.transactionComplete[myTeller].acquire();
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: thanks teller");

            System.out.println("Customer " + id + " [Customer " + id + "]: leaves the bank");

            BankSimulation.mutex.acquire();
            BankSimulation.tellerReady[myTeller] = true;
            BankSimulation.customersServed++;
            BankSimulation.availableTellers.release();
            BankSimulation.mutex.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
