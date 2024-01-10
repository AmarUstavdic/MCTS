package MCTS;

public class MCTS {

    private MCTSNode root;


    /**
     *  Performs the Monte Carlo Tree Search (MCTS.MCTS) algorithm to find the best move within a
     *  specified time limit.
     *
     *  @param state The initial state from which the MCTS.MCTS algorithm starts its search.
     *  @param time The maximum time, in milliseconds, allowed for the search.
     *  @return The best move determined by the MCTS.MCTS algorithm.
     */
    public State searchWithTimeLimit(State state, int time) {
        long start = System.currentTimeMillis();
        root = new MCTSNode(state, null);
        while (System.currentTimeMillis() - start < time) {
            MCTSNode selectedMCTSNode = selection();
            MCTSNode expandedMCTSNode = expansion(selectedMCTSNode);
            double simulationResult = simulation(expandedMCTSNode);
            backpropagation(expandedMCTSNode, simulationResult);
        }
        return root.getBestMove();
    }

    /**
     *  Performs the Monte Carlo Tree Search (MCTS.MCTS) algorithm to find the best move within a
     *  specified time limit.
     *
     *  @param state The initial state from which the MCTS.MCTS algorithm starts its search.
     *  @param maxIterations The maximum number of iterations, allowed for the search.
     *  @return The best move determined by the MCTS.MCTS algorithm.
     */
    public State searchWithIterationsLimit(State state, int maxIterations) {
        root = new MCTSNode(state, null);
        for (int i = 0; i < maxIterations; i++) {
            MCTSNode selectedMCTSNode = selection();
            MCTSNode expandedMCTSNode = expansion(selectedMCTSNode);
            double simulationResult = simulation(expandedMCTSNode);
            backpropagation(expandedMCTSNode, simulationResult);
        }
        return root.getBestMove();
    }

    /**
     *  Selects the best child node in the Monte Carlo Tree Search (MCTS.MCTS) process based on the Upper
     *  Confidence Bound for Trees (UTC) value. Starting from the root node (initial state), iteratively
     *  navigates through child nodes until reaching a leaf node without further children. This is the first
     *  phase of MCTS.MCTS algorithm.
     *
     *  @return The most promising leaf node based on UTC formula.
     */
    private MCTSNode selection() {
        MCTSNode MCTSNode = root;
        while (MCTSNode.hasChildren()) {
            MCTSNode = MCTSNode.getBestChild();
        }
        return MCTSNode;
    }

    /**
     *  Expands a given Monte Carlo Tree Search (MCTS.MCTS) node during the Expansion Phase.
     *  If the state associated with the node has not been simulated yet, expansion phase
     *  is skipped, and it just returns the node. Otherwise, if the state has been simulated
     *  at least once, and it is not terminal we expand it by adding children representing
     *  legal moves. A random child is then selected and returned.
     *
     *  @param  MCTSNode The node to be expanded.
     *  @return A randomly selected child node after expansion, or the original node if
     *          it has never been simulated before or its state is terminal.
     */
    private MCTSNode expansion(MCTSNode MCTSNode) {
        if (MCTSNode.isSimulated() && !MCTSNode.getState().isTerminal()) {
            MCTSNode.expand();
            return MCTSNode.getRandomChild();
        }
        return MCTSNode;
    }

    /**
     *  Simulates the outcome of a Monte Carlo Tree Search (MCTS.MCTS) node during the Simulation Phase.
     *  The simulation can be either a light simulation, where moves are chosen randomly, or a
     *  heavy simulation, where better heuristics are used for move selection. Heavy simulation
     *  typically results in better MCTS.MCTS performance. Additionally, in simulation phase we could
     *  employ Artificial Neural Networks (ANN), that can be used for prediction, if so we would
     *  not have to simulate all the way to the terminal state or att all.
     *
     *  @param MCTSNode The node for which state the simulation is about to be performed.
     *  @return The simulated outcome (reward) of the state associated with the node.
     */
    private double simulation(MCTSNode MCTSNode) {
        State clonedState = MCTSNode.getState().deepCopy();
        while (!clonedState.isTerminal()) clonedState.performRandomAction();
        return clonedState.getSimulationOutcome();
    }

    /**
     *  Performs the backpropagation phase in the Monte Carlo Tree Search (MCTS.MCTS) algorithm.
     *  Starting from the given MCTS.MCTS node, it increments the visit count and updates the value
     *  statistics based on the simulation result. The backpropagation continues towards the
     *  root of the tree until reaching the root node. In games with alternating turns, such
     *  as Tic Tac Toe, it is crucial to alternate the simulation result up the tree based on
     *  the player associated with each state/node.
     *
     *  @param MCTSNode The MCTS.MCTS node from which the backpropagation begins (the node whose state was simulated).
     *  @param simulationResult The simulated outcome of the state associated with the node.
     */
    private void backpropagation(MCTSNode MCTSNode, double simulationResult) {
        while (MCTSNode != null) {
            MCTSNode.incVisits();
            MCTSNode.addValue(simulationResult);
            MCTSNode = MCTSNode.getParent();
        }
    }

    public MCTSNode getRoot() {
        return root;
    }
}
