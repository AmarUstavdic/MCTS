import java.util.List;

public class MonteCarloTreeSearch {

    private Node root;



    public State search(State state, int time) {

        long start = System.currentTimeMillis();
        int simulations = 0;

        // here we need to handle root properly, we can use previously discovered info
        // we can use the information that tree has previously discovered
        // therefore we can simply find the successor of root and replace it...
        root = replace(state);

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


    // Here we made an assumption that it is general simple turn based game
    private Node replace(State state) {
        // root should have children if I have not made mistake somewhere
        if (root == null) return new Node(state, null);
        List<Node> children = root.getChildren();
        for (Node child : children) {
            if (child.hasChildren()) {
                for (Node grandChild : child.getChildren()) {
                    if (grandChild.getState().equals(state)) {
                        grandChild.setParent(null);
                        return grandChild;
                    }
                }
            }
        }
        return new Node(state, null);
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
        return clonedState.getValue();
    }

    private void backpropagation(Node node, double simulationResult) {

        // TODO: Test if backpropagation works correctly!

        while (node != null) {
            node.incVisits();
            node.addValue(simulationResult);
            node = node.getParent();
        }
    }

}
