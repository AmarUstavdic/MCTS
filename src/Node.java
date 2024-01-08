import java.util.ArrayList;
import java.util.List;

public class Node {

    private final State state;
    private int visits;
    private double value;
    private Node parent;
    private final List<Node> children;

    public Node(State state, Node parent) {
        this.state = state;
        this.parent = parent;
        this.children = new ArrayList<>();
    }




    public void expand() {
        List<Action> availableActions = state.getAvailableActions();
        for (Action action : availableActions) {
            State clonedState = state.deepCopy();
            clonedState.performAction(action);
            children.add(new Node(clonedState, this));
        }
    }


    private double calculateUct() {
        if (visits == 0) {
            return Double.MAX_VALUE;
        } else {
            double exploitation = value / visits;
            double exploration = Math.sqrt(Math.log(parent.visits) / visits);
            return exploitation + Math.sqrt(2) * exploration;
        }
    }


    public Node getBestChild() {
        Node bestChild = null;
        double bestUct = Double.NEGATIVE_INFINITY;
        for (Node child : children) {
            double childUct = child.calculateUct();
            if (bestUct < childUct) {
                bestChild = child;
                bestUct = childUct;
            }
        }
        return bestChild;
    }


    public Node backpropagate(double simulationResult) {
        visits++;
        // TODO: Figure out if this is true?!
        // this value we need to alternate since each node represents state after different player
        value += simulationResult;
        return parent;
    }


    public State getState() {
        return state;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getFirstChild() {
        return children.getFirst();
    }

    public List<Node> getChildren() {
        return children;
    }

    /**
     *  Tells us if this node has been simulated at least once.
     */
    public boolean isSimulated() {
        return visits > 0;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

}
