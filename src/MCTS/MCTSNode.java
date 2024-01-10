package MCTS;

import java.util.*;

public class MCTSNode {


    /**
     *  The exploration constant, originally Math.sqrt(2), is chosen empirically to tune
     *  the balance between exploitation and exploration based on the characteristics
     *  of the problem being solved.
     */
    private final double C = Math.sqrt(2);

    private final State state;
    private int visits;
    private double value;
    private final MCTSNode parent;
    private final List<MCTSNode> children;


    public MCTSNode(State state, MCTSNode parent) {
        this.state = state;
        this.visits = 0;
        this.value = 0;
        this.parent = parent;
        this.children = new ArrayList<>();
    }


    /**
     *  Expands the current node by adding child nodes for all available actions in the state.
     *  For each available action, a new child node is created with a cloned state resulting from
     *  performing that action. These child nodes represent potential future states in the search tree.
     *  The new nodes are added to the list of children for the current node.
     */
    public void expand() {
        state.getAvailableActions().stream().map(action -> {
                    State clonedState = state.deepCopy();
                    clonedState.performAction(action);
                    return new MCTSNode(clonedState, this);
        }).forEach(children::add);
    }

    /**
     *  Calculates the Upper Confidence Bound for Trees (UCT) value for the current node.
     *  The UCT formula balances exploitation (favoring nodes with high average values) and
     *  exploration (favoring nodes with low visit counts). If the node has not been visited
     *  yet (visits == 0), the UCT value is set to {@link Double#MAX_VALUE} to encourage
     *  exploration of unvisited nodes.
     *
     *  @return The calculated UCT value for the current node.
     */
    private double calculateUct() {
        return visits == 0 ? Double.MAX_VALUE : (value / visits) + C * Math.sqrt(Math.log(parent.visits) / visits);
    }

    /**
     *  Returns the child node with the highest Upper Confidence Bound for Trees (UCT) value.
     *  The UCT values are calculated for each child using the {@link #calculateUct()} method,
     *  and the node with the maximum UCT value is considered the best child.
     *
     *  @return The child node with the highest UCT value, or {@code null} if the list of children is empty.
     */
    public MCTSNode getBestChild() {
        return children.stream().max(Comparator.comparingDouble(MCTSNode::calculateUct)).orElse(null);
    }

    /**
     *  Returns the state associated with the child node that has the highest number of visits.
     *  The best child node is determined by comparing the visit counts of all children.
     *  If no children are present (e.g., at the root), a {@link RuntimeException} is thrown
     *  indicating that the best state cannot be obtained because the node has no children.
     *
     *  @return The state associated with the child node that has the highest number of visits.
     *  @throws RuntimeException If no children are present, indicating an inability to determine the best state.
     */
    public State getBestMove() {
        MCTSNode best = this.children.stream().max(Comparator.comparingInt(MCTSNode::getVisits)).orElse(null);
        if (best == null) {
            throw new RuntimeException("Unable to get best state. The root has no children!");
        }
        return best.getState();
    }

    public void incVisits() {
        this.visits++;
    }

    /**
     *  Adds a value to the cumulative value of the node based on the type of state.
     *  If the state corresponds to a Monte Carlo Tree Search Agent (MCTS.MCTS) state, the value is added.
     *  Otherwise, the value is subtracted.
     *
     *  @param value The value to be added or subtracted based on the state type.
     */
    public void addValue(double value) {
        this.value += this.state.isMCTSAgentState() ? value : -value;
    }

    public State getState() {
        return state;
    }

    public int getVisits() {
        return visits;
    }

    public MCTSNode getParent() {
        return parent;
    }

    public MCTSNode getRandomChild() {
        Collections.shuffle(children);
        return children.getFirst();
    }

    public List<MCTSNode> getChildren() {
        return children;
    }

    public boolean isSimulated() {
        return visits > 0;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

}
