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
When we run the program, 5 customers are created, and they all sleep for a random amount of time before waiting for a teller to be available.
If there is a teller available, then the customer thread acquires that teller and the teller is marked as busy.
Once a customer is assigned to a teller the customer introduces itself to the teller and sleeps for 300 ms before leaving the bank.
When the customer leaves the bank, the teller is marked as available for other customers again.
The Teller class creates tellers, we have a teller id

Nov 13 7:36 PM
I was able to complete the goal on the 13th but i wasn't able to push the devlog and my changes, but i want to use this session to add more complexiity to the project since i already have the threads communicating and synchronzing with one another. Im not sure how to add the complexity but i will figure it out but for now i will continue explaining how the basic code that i have works. The teller class creates tellers, we have a teller id to not which teller is doing what. The fist step is to let all the customers know that a teller is ready. Since this is a basic program the tellers will just serve the customers but the next step would be to wait for the customer to approach the teller before serving. The plan of this session is to move into the next stage of complexity which included adding more complex interactions between the customer and teller
so i have moved on the next stage of complexity which includes adding new semapores that signals that the customer is ready and have approached a teller, signals that the teller is requesting the transaction type from the customer, signals that the customer has provided transaction type, and a signal that the teller uses to signify the end of a transaction. Now the customer randomly picks between withdrawing or depositing money and waits for the teller to ask for transaction type before providing transaction type, the customer waits for the customer to send the transaction complete signal before thanking the teller and leaving the bank

Nov 14 1:35 PM
I had completed what i wanted to do for the last session but i did not push my changes. The goal of this session is to push the changes. I will push the changes now.
Nov 14 1:58 PM
I have time to work on the project so the plan this sessiion is to continue building on what we have already so far we have the cutsomers and the tellers being in sync (1 customer per teller) and we have the deposit/withdraw finctionality working, the next thing i want to add is the safe, so there are only 2 tellers allowed to enter the safe, i will mirror this using java code. I have implemented the safe feature now so the safe is limited to only 2 tellers at a time. The next step will be to add the manager.
Nov 14 6:35 PM
I have added the manager semaphore to the program so now there is a manager that the tellers have to meet before going to the safe. The next step is to implement the door.  
I have implemented the door, so now only two customers are allowed into the bank. This is possible using a semaphore with 2 permits. in the next step, i will polish up the program and increase the count to 50 customers but that will be for another session.
In this session, I:
completed last session goals
added deposit/withdraw functionality
added safe functionality
added manager functionality
added door functionality
The project is basically complete but i will go through and not any logical errors i might have in the code.

Nov 16 9:00 AM
Okay so the program is basically complete, i just have to look through for any logical errors and to make sure i followed the specifications provided in the pdf file. The goal of this session is to complete the project and add anything missing.
Upon running and taking a closer look at the program i noticed that the output isn't up to the project specifications. All the threads are printing to the console as soon as they get the chance to but it shouldnt be this way. I will have to implement a mutex for the output so that only on thread can print to output at a time.
I have successfully added the mutex so now when a thread performs an action, other threads are blocked until the thread completes the action and prints it to the console. It has been 4 hours since ive been trying to debug this program so im taking a break

Nov 16 4:00 PM
I took a look at the program again and noticed that only Tellers 1 and 0 are being used, i think this is because of the teller picking algorithm that i setup, instead i will have customers pick from the random pool of available tellers
I have implemented the teller picking algorithm and teller 2 is now being used by the customers. I compared the output to the one provided and it matches what i have so i have completed the project
In this session, I:
Fixed Teller 2 not being selected
Implemented new teller picking algorithm
Used mutexes to prevent threads from printing prematurely
Completed Project 2
This project allowed me to have a solid grasp on java and using semaphores in java.
