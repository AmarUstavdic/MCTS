import java.util.List;

public interface State {


    List<Action> getAvailableActions();

    void performAction(Action action);

    void performRandomAction();

    State deepCopy();

    boolean isTerminal();

    double getValue();

    boolean equals(State state);

}
