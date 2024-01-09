import java.util.List;

public interface State {


    List<Action> getAvailableActions();

    void performAction(Action action);

    void performRandomAction();

    State deepCopy();

    boolean isTerminal();

    boolean equals(State state);

    char getCurrentAgent();

    char getMCTSAgent();

    double getSimulationOutcome();

}
