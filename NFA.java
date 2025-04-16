import java.util.*;

public class NFA {
    private Set<Integer> states;
    private Set<Character> alphabet;
    private Map<Integer, Map<Character, Set<Integer>>> transitions;
    private int startState;
    private Set<Integer> acceptStates;

    public NFA(Set<Integer> states, Set<Character> alphabet, Map<Integer, Map<Character, Set<Integer>>> transitions, int startState, Set<Integer> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;
        this.startState = startState;
        this.acceptStates = acceptStates;
    }

    // 可视化NFA
    public void visualize() {
        System.out.println("NFA Visualization:");
        for (Map.Entry<Integer, Map<Character, Set<Integer>>> entry : transitions.entrySet()) {
            int state = entry.getKey();
            for (Map.Entry<Character, Set<Integer>> trans : entry.getValue().entrySet()) {
                char symbol = trans.getKey();
                for (int nextState : trans.getValue()) {
                    System.out.println(state + " --" + symbol + "--> " + nextState);
                }
            }
        }
    }

    // 扫描字符串
    public boolean scan(String input) {
        Set<Integer> currentStates = new HashSet<>();
        currentStates.add(startState);
        for (char symbol : input.toCharArray()) {
            Set<Integer> nextStates = new HashSet<>();
            for (int state : currentStates) {
                if (transitions.containsKey(state) && transitions.get(state).containsKey(symbol)) {
                    nextStates.addAll(transitions.get(state).get(symbol));
                }
            }
            currentStates = nextStates;
        }
        for (int state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }
        return false;
    }
}