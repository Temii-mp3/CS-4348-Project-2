import java.util.concurrent.Semaphore;

public class Customer implements Runnable {
    private int id;

    public Customer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            Thread.sleep((int)(Math.random() * 100));

            System.out.println("Customer " + id + " [Customer " + id + "]: enters the bank");


            BankSimulation.availableTellers.acquire();


            int myTeller = -1;
            BankSimulation.mutex.acquire();
            for (int i = 0; i < BankSimulation.NUM_TELLERS; i++) {
                if (BankSimulation.tellerReady[i]) {
                    myTeller = i;
                    BankSimulation.tellerReady[i] = false;
                    break;
                }
            }
            BankSimulation.mutex.release();


            if (myTeller != -1) {
                System.out.println("Customer " + id + " [Teller " + myTeller + "]: selects teller");
                System.out.println("Customer " + id + " [Teller " + myTeller + "]: introduces self");
            }


            Thread.sleep(300);


            System.out.println("Customer " + id + " [Customer " + id + "]: leaves the bank");

            
            BankSimulation.mutex.acquire();
            if (myTeller != -1) {
                BankSimulation.tellerReady[myTeller] = true;
                BankSimulation.availableTellers.release();
            }
            BankSimulation.mutex.release();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}