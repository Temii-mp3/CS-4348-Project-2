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
            BankSimulation.mutex.acquire(); // Lock
            BankSimulation.tellerReady[id] = true;
            System.out.println("Teller " + id + " [Teller " + id + "]: ready to serve");
            BankSimulation.availableTellers.release(); // Signal that this teller is available
            BankSimulation.mutex.release(); // Unlock
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < BankSimulation.NUM_CUSTOMERS / BankSimulation.NUM_TELLERS + 1; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}