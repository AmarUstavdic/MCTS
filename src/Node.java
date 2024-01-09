import java.util.*;

public class Node {

    private final State state;
    private int visits;
    private double value;
    private Node parent;
    private final List<Node> children;

    public Node(State state, Node parent) {
        this.state = state;
        this.visits = 0;
        this.value = 0;
        this.parent = parent;
        this.children = new ArrayList<>();
    }



    // only for stats
    public void printStats() {
        System.out.print("|n=" + visits + ", v=" + value + "|");
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
        if (this.visits == 0) {
            return Double.MAX_VALUE;
        } else {
            double exploitation = this.value / this.visits;
            double exploration = Math.sqrt(Math.log(parent.visits) / this.visits);
            return exploitation + Math.sqrt(2) * exploration;
        }
    }


    // cannot return null, that is checked where this function is called
    public Node getBestChild() {
        return children.stream().max(Comparator.comparingDouble(Node::calculateUct)).orElse(null);
    }



    public State getBestMove() {
        Node best = this.children.stream().max(Comparator.comparingInt(Node::getVisits)).orElse(null);
        if (best == null) {
            throw new RuntimeException("Unable to get best state. The root has no children!");
        }
        System.out.println("Value: " + best.value);
        System.out.println("Visits: " + best.visits);
        return best.getState();
    }



    public void incVisits() {
        this.visits++;
    }

    public void addValue(double value) {
        this.value += value;
    }

    public State getState() {
        return state;
    }

    public int getVisits() {
        return visits;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getFirstChild() {
        Collections.shuffle(children);
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
