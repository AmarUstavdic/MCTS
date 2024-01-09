import java.util.List;

/**
 *  Represents a state in a game or simulation within the context of Monte Carlo Tree Search (MCTS).
 *  A state encapsulates the current state of the system, provides methods to interact with it, and
 *  defines behaviors relevant to MCTS, such as obtaining available actions, performing actions,
 *  creating deep copies, checking for terminal states, getting simulation outcomes, and identifying
 *  whether it corresponds to an MCTS agent's turn.
 */
public interface State {

    /**
     *  Gets the list of available actions in the current state.
     *
     *  @return A list of available actions.
     */
    List<Action> getAvailableActions();

    /**
     *  Performs the specified action in the current state, updating it accordingly.
     *
     *  @param action The action to be performed.
     */
    void performAction(Action action);

    /**
     *  Performs a random action in the current state, updating it accordingly.
     */
    void performRandomAction();

    /**
     *  Creates a deep copy of the current state.
     *
     *  @return A deep copy of the current state.
     */
    State deepCopy();

    /**
     *  Checks whether the current state is terminal.
     *
     *  @return {@code true} if the state is terminal, {@code false} otherwise.
     */
    boolean isTerminal();

    /**
     *  Gets the simulation outcome value associated with the current state.
     *
     *  @return The simulation outcome value.
     */
    double getSimulationOutcome();

    /**
     *  Checks whether the current state corresponds to an MCTS agent's turn.
     *
     *  @return {@code true} if it is an MCTS agent's turn, {@code false} otherwise.
     */
    boolean isMCTSAgentState();

}
