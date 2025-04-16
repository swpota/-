import java.util.*;

public class DFA {
    private Set<Integer> states;
    private Set<Character> alphabet;
    private Map<Integer, Map<Character, Integer>> transitions;
    private int startState;
    private Set<Integer> acceptStates;

    public DFA(Set<Integer> states, Set<Character> alphabet, Map<Integer, Map<Character, Integer>> transitions, int startState, Set<Integer> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.acceptStates = acceptStates;
    }

    // 可视化DFA
    public void visualize() {
        System.out.println("DFA Visualization:");
        for (Map.Entry<Integer, Map<Character, Integer>> entry : transitions.entrySet()) {
            int state = entry.getKey();
            for (Map.Entry<Character, Integer> trans : entry.getValue().entrySet()) {
                char symbol = trans.getKey();
                int nextState = trans.getValue();
                System.out.println(state + " --" + symbol + "--> " + nextState);
            }
        }
    }

    // 扫描字符串
    public boolean scan(String input) {
        int currentState = startState;
        for (char symbol : input.toCharArray()) {
            if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(symbol)) {
                currentState = transitions.get(currentState).get(symbol);
            } else {
                return false;
            }
        }
        return acceptStates.contains(currentState);
    }
}