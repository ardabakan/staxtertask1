
# Task Solution for Staxter

![Staxter](https://images.prismic.io/staxter/05d507f2-e0b1-4e12-9040-8d9fec7363a0_get-a-staxter-mastercard.svg?auto=compress,format)

This project is my solution for the hiring task of Staxter. I've implemented it using a good old non blocking IO communication of Java, NIO.  

The Player class has two elements :

> PlayerClient -> able to call another player
> PlayerServer -> listens to other players

The two players in the solution, The Initiator and The Responder has different natures. The Initiator conducts the flow of the game by sending messages and in the end shutting down the Responder, and himself in the end. Both games, same PID and different PID run the same way, and print exactly the same output. 


##  Prerequisites

- Java 1.8
- Maven
- Internet connection


##  Task Details

The use case for this task is as below:
1. create 2 players
2. one of the players should send a message to second player (let's call this player "initiator")
3. when a player receives a message should send back a new message that contains the received message concatenated with the
message counter that this player sent.
4. finalize the program (gracefully) after the initiator sent 10 messages and received back 10 messages (stop condition)
5. both players should run in the same java process (strong requirement)
6. document for every class the responsibilities it has.
7. additional challenge (nice to have) opposite to 5: have every player in a separate JAVA process.
Constrains:
* Please use pure Java as much as possible (no additional frameworks like spring, etc)
* Please deliver a maven project with the source code only (no jars).
* Please provide a shell script to start the program.
* Everything what is not clearly specified is to be decided by developer. Everything what is specified is a hard requirement.
* Please focus on design and not on technology, the technology should be the simplest possible that is achieving the target.
* The focus of the exercise is to deliver the cleanest and clearest design that you can achieve (and the system has to be functional).
###  How to run the solution
####  1) Build the project
> **How to?** 
> Run the **build_the_project.bat**

### # 2) Try the solution class for same PID 

> **How to?** 
>  **Step 1** Run the **main_question_samepid_run_me_alone.bat**

####  3) Try the solution classes for bonus question with different PIDs 
> **How to?** 
> **Step 1** First, run the **bonus_question_different_pid_run_me_first.bat**
> This will launch the Responder Player. This player will listen to echo with counter.  When it receives a poison pill from the initiator, it will eventually shut down its server and exit.
> **Step 2** Then, run the **bonus_question_different_pid_run_me_second.bat**
> This will launch the Initiator Player. This player will call the Responder Player and get answers from it.
> After GAME_ROUNDS, 10 rounds, will send a poison pill to the Responder and make it stop, and then it will quit.

##  Unit Tests

The unit test method launches 2 players, 1 responder and 1 initiator. We control the responses of responder to be exactly same as we would expect it to be, according to the counter incremented. 

