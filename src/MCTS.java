import java.util.LinkedList;
import java.util.Queue;

public class MCTS {

    private Node root;



    public State search(State state, int time) {

        long start = System.currentTimeMillis();
        root = new Node(state, null);

        while (System.currentTimeMillis() - start < time) {
            Node selectedNode = selection();
            Node expandedNode = expansion(selectedNode);
            double simulationResult = simulation(expandedNode);
            backpropagation(expandedNode, simulationResult);
        }
        System.out.println("Number of simulations: " + root.getVisits());
        return root.getBestMove();
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



    private void printByLevel() {
        if (root == null) return;

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        int level = 0;

        while (!q.isEmpty()) {
            int nodesAtCurrentLevel = q.size();
            System.out.print("LEVEL " + level + ": ");

            for (int i = 0; i < nodesAtCurrentLevel; i++) {
                Node n = q.remove();
                n.printStats();
                System.out.print("   ");
                q.addAll(n.getChildren());
            }
            level++;
            System.out.println();
        }
        System.out.println();
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


    /*
        if state has not been simulated --> return that state immediately
        otherwise, for all legal moves, add children, and then pick a random child, and return it..
     */
    private Node expansion(Node node) {
        if (node.isSimulated() && !node.getState().isTerminal()) {
            node.expand();
            return node.getFirstChild();
        }
        return node;
    }

    /*
        At this step we can do light simulation or heavy simulation.
        light simulation --> moves are chose randomly
        heavy simulation --> we can use better heuristics, for choosing moves
        (by doing heavy simulation, we should get better performing MCTS...)
        ---------------------------------------------------------------------
        at this step we can also play with ANN, to teach them and use them instead of simulating game
     */
    private double simulation(Node node) {
        State clonedState = node.getState().deepCopy();

        while (!clonedState.isTerminal()) {
            clonedState.performRandomAction();
        }
        return clonedState.getSimulationOutcome();
    }

    private void backpropagation(Node node, double simulationResult) {
        while (node != null) {
            node.incVisits();
            if (node.getState().getCurrentAgent() == node.getState().getMCTSAgent()) {
                node.addValue(simulationResult * -1);
            } else {
                node.addValue(simulationResult);
            }
            node = node.getParent();
        }
    }

}
