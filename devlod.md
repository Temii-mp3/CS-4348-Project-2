Nov 8 3:40 AM
I took a look at the project and have a basice idea about what i need to do.
The premise of the project is to use semaphores to ensure proper communcation between threads that use a shared resource.
For this project i will be using java because i use c++ in the last project and would like to get experience with java as well.
In order to properly start, i need to plan where semaphores will be needed.
Iwill create a basic program first to test out the syntax of java and have a basic idea of what im supposed to do.
The goal for this session is to have all files uploaded to github and a have a basic understanding of the project.

Nov 10 4:09 PM
I did not complete what i wanted to do in the last session so i plan to complete it in this session instead
Okay so i have been able to create a basic program with which i will add complexity as i progress.
Right now i have three classes, the BankSimulation class and the Teller and Customer classes.
The Customer class creates Customers. We have an ID that differentiates between the customers.
When we run the program, 5 customers are created and they all sleep for a random amount of time before waiting for a teller to be available.
If there is a teller available, then the customer thread acquires that teller and the teller is marked as busy.
Once a customer is assigned to a teller the customer introduces itself to the teller and sleeps for 300 ms before leaving the bank.
When the customer leaves the bank, the teller is marked as available for other customers again.
The Teller class creates tellers, we have a teller id

Nov 13 7:36 PM
I was able to complete the goal on the 13th but i wasnt not able to push the devlog and my changes, but i want to use this session to add more complexiity to the project since i already have the threads communicating and synchronzing with one another. Im not sure how to add the complexity but i will figure it out but for now i will continue explaining how the basic code that i have works. The teller class creates tellers, we have a teller id to not which teller is doing what. The fist step is to let all the customers know that a teller is ready. Since this is a basic program the tellers will just serve the customers but the next step would be to wait for the customer to approach the teller before serving. The plan of this session is to move into the next stage of complexity which included adding more complex interactions between the customer and teller
