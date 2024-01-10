# Monte Carlo Tree Search (MCTS.MCTS) Algorithm

This repository contains a Java implementation of the Monte Carlo Tree Search (MCTS.MCTS) algorithm, 
a popular technique used in decision-making processes for games and simulations.

## Overview

The MCTS.MCTS algorithm is used to find the best move in a game or simulation within a specified time
limit. It leverages the Upper Confidence Bound for Trees (UTC) value to guide its search through 
possible moves. The algorithm consists of four main phases:

1. **Selection Phase**: Starting from the root node (initial state), the algorithm navigates through child nodes until reaching a leaf node without further children. The best child is selected based on the UTC value.

2. **Expansion Phase**: If the state associated with the selected node has been simulated and is not terminal, the node is expanded by adding children representing legal moves. A random child is then selected for further exploration.

3. **Simulation Phase**: The outcome of the selected node is simulated. This can be a light simulation, where moves are chosen randomly, or a heavy simulation using better heuristics for move selection. The simulation outcome provides a reward for the selected state.

4. **Backpropagation Phase**: The visit count and value statistics of nodes along the path from the selected node to the root are updated based on the simulation result. This phase ensures that the algorithm learns from the simulated outcomes.


## Additional Feature: Tic-Tac-Toe Environment

In addition to the MCTS.MCTS algorithm implementation, this repository features a simple implementation of the Tic Tac Toe game. The Tic Tac Toe environment is utilized as a testing ground for the MCTS.MCTS algorithm. This allows testing and evaluating the algorithm's performance in a familiar and well-defined game setting.



## Getting Started

To use the MCTS.MCTS algorithm in your project:

1. Clone this repository:

```bash
https://github.com/AmarUstavdic/MCTS.MCTS.git
```

2. Integrate the `MCTS.MCTS` class and the associated `MCTS.MCTSNode` and `MCTS.State` and `MCTS.Action` interfaces into your project. If you want to get some stats of MCTS.MCTS performance you can also include `MCTS.MCTSUtils` class in your project.

3. Use the `search` method of the `MCTS.MCTS` class to find the best move within a specified time limit.


## Example Usage

```java
import MCTS.MCTS;
import MCTS.State;// Create an initial game state
State initialState = new YourGameStateClass(/* pass relevant parameters */);

// Create an instance of the MCTS.MCTS algorithm
MCTS mcts = new MCTS();

// Perform the MCTS.MCTS search for a specified time
int searchTimeMillis = 5000; // 5 seconds
State bestNextState = mcts.search(initialState, searchTimeMillis);
```

> Note that you have to implement, how you would like to retrieve the actions, from the next state that has been found by the MCTS.MCTS algorithm.

## Contributions and Feedback
Feel free to contribute to this project, report any issues, or suggest improvements. Your input is valuable, and together we can make this project even better. Happy coding!
