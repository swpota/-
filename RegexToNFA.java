import java.util.*;

public class RegexToNFA {
    private static class State {
        int id;
        Map<Character, List<State>> transitions = new HashMap<>();

        State(int id) {
            this.id = id;
        }

        void addTransition(char symbol, State state) {
            transitions.computeIfAbsent(symbol, k -> new ArrayList<>()).add(state);
        }
    }

    private State startState;
    private State acceptState;
    private int stateCount = 0;

    public RegexToNFA(String regex) {
        startState = new State(stateCount++);
        acceptState = constructNFA(startState, regex);
    }

    private State constructNFA(State start, String regex) {
        State currentState = start;
        State prevState = null;
        char prevChar = '\0';

        for (int i = 0; i < regex.length(); i++) {
            char c = regex.charAt(i);

            if (c == '*') {
                if (prevState == null) {
                    throw new IllegalArgumentException("Invalid regex: '*' cannot be the first character");
                }
                // Add a self-loop to the previous state's outgoing transitions
                prevState.addTransition(prevChar, prevState);
                // Add an epsilon transition to allow skipping the repeated character
                prevState.addTransition('\0', currentState);
                continue;
            }

            // Create new state and add transition
            State newState = new State(stateCount++);
            currentState.addTransition(c, newState);

            // Update previous state and character
            prevState = currentState;
            currentState = newState;
            prevChar = c;
        }

        return currentState;
    }

    private boolean matchHelper(State currentState, String input, int index) {
        if (index == input.length()) {
            return currentState == acceptState;
        }

        char symbol = input.charAt(index);

        // Check epsilon transitions
        if (currentState.transitions.containsKey('\0')) {
            for (State nextState : currentState.transitions.get('\0')) {
                if (matchHelper(nextState, input, index)) {
                    return true;
                }
            }
        }

        // Check transitions for the current symbol
        if (currentState.transitions.containsKey(symbol)) {
            for (State nextState : currentState.transitions.get(symbol)) {
                if (matchHelper(nextState, input, index + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean match(String input) {
        return matchHelper(startState, input, 0);
    }

    public void visualize() {
        Set<State> visited = new HashSet<>();
        visualizeHelper(startState, visited);
    }

    private void visualizeHelper(State state, Set<State> visited) {
        if (visited.contains(state)) {
            return;
        }
        visited.add(state);
        for (Map.Entry<Character, List<State>> entry : state.transitions.entrySet()) {
            for (State nextState : entry.getValue()) {
                System.out.println("State " + state.id + " --" + entry.getKey() + "--> State " + nextState.id);
                visualizeHelper(nextState, visited);
            }
        }
    }
}
