# LukasiewiczLogic

This program evaluates functions that are written in Łukasiewicz notation (parenthesis-free notation).

## Description

This project implements an application that can read and evaluate functions written in Łukasiewicz notation. Jan Łukasiewicz was a famous Polish logician and philosopher who first developed Łukasiewicz logic in 1924. Łukasiewicz logic belongs to two classes: t-norm fuzzy logics and substructural logics. Instead of using parenthesis to indicate operator priority, each operator applies on the operands that come next in the expression, according to the operator's arity. In this way, the following standard boolean expression:

  (x ⋁ y) ⋁ z
  
becomes:

  A A x y z

where A signifies the boolean OR operator (V). 

In this program, K signifies the AND operator (Ʌ), I signifies implication, D signifies the NAND operator, N signifies NOT and O signifies false. See [here](http://www.cs.uwm.edu/classes/cs315/Bacon/Lecture/HTML/ch13s02.html) for more information.

![Image showing Łukasiewicz logic](https://www.researchgate.net/publication/329668761/figure/fig1/AS:703944753963011@1544844916406/Bochenskis-presentation-of-the-laws-of-implication-in-two-notations-with-labels-in_Q320.jpg)

## Getting Started

### Dependencies

* Java 8 is the only requirement. 

### Installing and using

* Simply clone the code from this repository. If using Eclipe, IntelliJ or another IDE, main.java can be run.
* If running on terminal, src code needs to be compiled before running Main. This can be done by navigating into the src directory of the cloned the repository. Then run: ```javac -d ./../bin -sourcepath . Main.java ```. Then the application can be run from the bin directory with the following command: ```java Main```
* The following command arguments are expected: 3 lines to represent the Tic Tac Toe board. Each "cell" of the board can contain an X, an O or a - to signify empty. See below for an example:
  ```
  >> java Main
  >> X-O
  >> OXO 
  >> ---
  ```
* After a few milliseconds or seconds, the resultant board will be displayed. The result depends on the decision made by the algorithm and will differ since the algorithm is probabilistic. Below is an example board:  
  ```
  X-O
  OXO 
  --X
  
  Player 1 has won!
  ```
* The time that the algorithm has to "think" is a variable that can be changed in the Main.java function (line 27).
