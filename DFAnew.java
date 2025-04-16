import java.util.*;

public class DFA {
    public static class State {
        int id;
        Map<Character, State> transitions = new HashMap<>();

        State(int id) {
            this.id = id;
        }

        void addTransition(char symbol, State state) {
            transitions.put(symbol, state);
        }
    }

    private State startState;

    public DFA(State startState) {
        this.startState = startState;
    }

    public boolean match(String input) {
        State currentState = startState;
        for (char symbol : input.toCharArray()) {
            currentState = currentState.transitions.get(symbol);
            if (currentState == null) {
                return false;
            }
        }
        return true;
    }

    // 可视化DFA
    public void visualize() {
        Set<State> visited = new HashSet<>();
        visualizeHelper(startState, visited);
    }

    private void visualizeHelper(State state, Set<State> visited) {
        if (visited.contains(state)) {
            return;
        }
        visited.add(state);
        for (Map.Entry<Character, State> entry : state.transitions.entrySet()) {
            System.out.println("State " + state.id + " --" + entry.getKey() + "--> State " + entry.getValue().id);
            visualizeHelper(entry.getValue(), visited);
        }
    }
}