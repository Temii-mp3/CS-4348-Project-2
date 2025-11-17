import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.Random;

public class Customer implements Runnable {
    private int id;
    private Random random = new Random();

    public Customer(int id) { this.id = id; }

    @Override
    public void run() {
        try {
            String transaction = random.nextBoolean() ? "DEPOSIT" : "WITHDRAW";

            BankSimulation.printMutex.acquire();
            System.out.println("Customer " + id + " [Customer " + id + "]: wants to make a " + transaction);
            BankSimulation.printMutex.release();
            Thread.sleep(random.nextInt(100));

            BankSimulation.door.acquire();
            BankSimulation.printMutex.acquire();
            System.out.println("Customer " + id + " [Customer " + id + "]: enters the bank");
            System.out.println("Customer " + id + " [Customer " + id + "]: getting in line");
            BankSimulation.printMutex.release();

            BankSimulation.availableTellers.acquire();

            int myTeller = -1;
            BankSimulation.mutex.acquire();
            List<Integer> readyTellers = new ArrayList<>();
            for (int i = 0; i < BankSimulation.NUM_TELLERS; i++) {
                if (BankSimulation.tellerReady[i]) readyTellers.add(i);
            }
            if (!readyTellers.isEmpty()) {
                myTeller = readyTellers.get(new Random().nextInt(readyTellers.size()));
                BankSimulation.tellerReady[myTeller] = false;
                BankSimulation.customerAtTeller[myTeller] = id;
            }

            BankSimulation.mutex.release();

            if (myTeller == -1) {
                BankSimulation.door.release();
                return;
            }

            BankSimulation.printMutex.acquire();
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: selects teller");
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: introduces self");
            BankSimulation.printMutex.release();

            BankSimulation.customerReady[myTeller].release();
            BankSimulation.tellerAsksTransaction[myTeller].acquire();

            BankSimulation.transactionType[myTeller] = transaction;

            BankSimulation.printMutex.acquire();
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: provides " + transaction + " transaction");
            BankSimulation.printMutex.release();

            BankSimulation.customerProvidesTransaction[myTeller].release();
            BankSimulation.transactionComplete[myTeller].acquire();

            BankSimulation.printMutex.acquire();
            System.out.println("Customer " + id + " [Teller " + myTeller + "]: thanks teller");
            System.out.println("Customer " + id + " [Customer " + id + "]: leaves the bank");
            BankSimulation.printMutex.release();

            BankSimulation.door.release();

            BankSimulation.mutex.acquire();
            BankSimulation.tellerReady[myTeller] = true;
            BankSimulation.customersServed++;
            BankSimulation.availableTellers.release();

            if (BankSimulation.customersServed == BankSimulation.NUM_CUSTOMERS) {
                for (int i = 0; i < BankSimulation.NUM_TELLERS; i++) {
                    BankSimulation.customerAtTeller[i] = -1;
                    BankSimulation.customerReady[i].release();
                }
            }

            BankSimulation.mutex.release();

        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
