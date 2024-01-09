import java.util.List;

public class MonteCarloTreeSearch {

    private Node root;



    public State search(State state, int time) {

        long start = System.currentTimeMillis();
        int simulations = 0;

        // here we need to handle root properly, we can use previously discovered info
        // we can use the information that tree has previously discovered
        // therefore we can simply find the successor of root and replace it...
        //root = replace(state); // --> this might be why it performs badly

        root = new Node(state, null);


        while (System.currentTimeMillis() - start < time) {
            Node selectedNode = selection();
            Node expandedNode = expansion(selectedNode);
            double simulationResult = simulation(expandedNode);
            backpropagation(expandedNode, simulationResult);

            // for statistics purposes
            simulations++;
        }

        System.out.println("Number of simulations: " + simulations);

        return root.getBestMove();
    }


    // -------------------------------------------------------------
    // Doing this has it potential problems, bot might perform badly
    // -------------------------------------------------------------
    private Node replace(State state) {
        // root should have children if I have not made mistake somewhere
        if (root == null) return new Node(state, null);
        List<Node> children = root.getChildren();
        for (Node child : children) {
            if (child.hasChildren()) {
                for (Node grandChild : child.getChildren()) {
                    if (grandChild.getState().equals(state)) {

                        System.out.println("Root replaced with grandchild:");
                        TicTacToeGameState s = (TicTacToeGameState) grandChild.getState();
                        s.printBoard();
                        System.out.println("-------------------------------");

                        grandChild.setParent(null);
                        return grandChild;
                    }
                }
            }
        }
        return new Node(state, null);
    }


    public int getDepth() {
        return depth(root);
    }

    private int depth(Node node) {
        if (node == null) return 0;
        if (node.getChildren().isEmpty()) return 1;

        int maxChildDepth = 0;
        for (Node child : node.getChildren()) {
            int childDepth = depth(child);
            maxChildDepth = Math.max(maxChildDepth, childDepth);
        }
        return 1 + maxChildDepth;
    }


    // -----------------------------------
    //      FOUR MAIN PHASES OF MCTS
    // -----------------------------------

    private Node selection() {
        Node node = root;
        while (node.hasChildren()) {
            node = node.getBestChild();
        }
        return node;
    }

    private Node expansion(Node node) {

        // TODO: Check if this condition after && is okay to have here!

        if (node.isSimulated() && !node.getState().isTerminal()) {
            node.expand();
            return node.getFirstChild();
        }
        return node;
    }

    private double simulation(Node node) {
        State clonedState = node.getState().deepCopy();
        while (!clonedState.isTerminal()) {
            clonedState.performRandomAction();
        }
        return clonedState.getSimulationOutcome();
    }

    private void backpropagation(Node node, double simulationResult) {

        // TODO: Here we might need to alternate the values.
        while (node != null) {
            node.incVisits();
            node.addValue(simulationResult);
            node = node.getParent();
        }
    }

}
