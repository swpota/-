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
        for (char c : regex.toCharArray()) {
            State newState = new State(stateCount++);
            currentState.addTransition(c, newState);
            currentState = newState;
        }
        return currentState;
    }

    public boolean match(String input) {
        return matchHelper(startState, input, 0);
    }

    private boolean matchHelper(State currentState, String input, int index) {
        if (index == input.length()) {
            return currentState == acceptState;
        }
        char symbol = input.charAt(index);
        if (currentState.transitions.containsKey(symbol)) {
            for (State nextState : currentState.transitions.get(symbol)) {
                if (matchHelper(nextState, input, index + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 可视化NFA
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

    public static void main(String[] args) {
        RegexToNFA nfa = new RegexToNFA("ab*c");
        nfa.visualize();
        System.out.println(nfa.match("abc")); // true
        System.out.println(nfa.match("ac")); // true
        System.out.println(nfa.match("abbbc")); // true
        System.out.println(nfa.match("abb")); // false
    }
}