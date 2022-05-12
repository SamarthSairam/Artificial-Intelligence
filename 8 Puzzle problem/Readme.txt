Assignment is implemented in Java.

Instructions for executing the program
1. Go to the sxs200378 folder inside which is src is present having 3 programs.
2. Execute command javac sxs200378*.java to compile the program
3. Execute command 
	java sxs200378/Main dfs to run DFS(Depth First Search)
	java sxs200378/Main ids to run IDS(Iterative Deepening Search)
	java sxs200378/Main Astar1 to run astar search with the heuristic number 1
	java sxs200378/Main Astar2 to run astar search with the heuristic number 2
4. After executing the above command enter the input 3*3 puzzle row by row separated by space in the command line.

	4 1 2
	6 3 *
	7 5 8

The input is taken in the above mentioned format.

5. The output shows the number of states enqueued and the number of moves required to reach the goal state along with the goal sequences to retrieve the output.


The other way to implement this is by using the Eclipse IDE.
Instructions to run using the Eclipse IDE:
1. Open the eclipse IDE and create a new java project.
2. Next create a package by any name create 3 classes called Action,Main and Node.
3. Inside the src folder of the sxs200378 folder retrieve the 3 respective programs and paste them in each of the 3 created classes.
4. Once pasted save the contract by clicking CTRL+S and then run the file using the run configurations option present on the top left corner of the IDE.
5.Once done, click on the arguments button and then type the name of the function required to be run.
6. Next, input the tile configuration row by row in the form of a 3x3 matrix.
7. The output is displayed wherein the total states, number of states enqueued and the path taken to reach the goal state is mentioned.


Comparative Analysis of 2 heuristics

We will be taking into consideration only Manhattan and Hamming for solving the 8 puzzle problem.
The  heuristic algorithms  take  into  consideration  two  pre-requisites:  to  find  some  distinction  criteria  which permit  finding  solution  with  less  resources  that  impose  uninformed  algorithms  and,  on  the  other  side,  to determine  correct  choices  in  order  to  reach  the final  state  using  optimal  path. 
Heuristic Algorithms use information from the domain of the problem to be solved.
The 2 used heuristics are:
1.Manhattan distance
2.Number of misplaced Tiles/Hamming distance

1.Manhattan distance:The most used and admissible heuristic used so far is called the Manhattan heuristic, which is as a matter of fact incorporated in solving the 8 puzzle problem. This is calculated as follows:

h(S)=Sum(ManhattanDistance(k)), where k belongs to 1,2,3,4...n

This represents the distance of k number position in state S towards its position to the goal state which is calculated using the following relation:

ManhattanDistance(k)=|x1-x2|+|y1-y2|

Where, x1 and y1 represent the coordinates of k number position in current state and x2,y2 represent the coordinates of k number position in goal state.

2. Number of misplaced tiles/Hamming distance: Assume that any tile can be moved to any position.
The tile needs simply to be moved to the final position. h(n) will therefore be the number of misplaced tiles. It is admissible as each misplaced tile needs to be moved.The Hamming distance is used to calculate the total number of misplaced tiles.