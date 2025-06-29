import java.util.*;

public class Mealy {
    private String initialState;
    private String currentState;
    private Set<String> states = new HashSet<>();
    private Map<String, Map<String, Transition>> transitions = new HashMap<>();

    // Define o estado inicial e o estado atual
    public void setInitialState(String state) {
        initialState = state;
        currentState = state;
    }

    // Adiciona um estado
    public void addState(String state) {
        states.add(state);
        transitions.putIfAbsent(state, new HashMap<>());
    }
    public String getInitialState() {
    return initialState;
}
    // Adiciona uma transição
    public void addTransition(String from, String input, String to, String output) {
        transitions.get(from).put(input, new Transition(to, output));
    }

    // Processa uma entrada de cada vez (modo interativo)
    public void processSingleInput(String input) {
        Map<String, Transition> stateTransitions = transitions.get(currentState);
        if (stateTransitions == null || !stateTransitions.containsKey(input)) {
            System.out.println("Transição inválida: " + currentState + " com entrada '" + input + "'");
            return;
        }
        Transition t = stateTransitions.get(input);
        System.out.println("Entrada: " + input +
                           " | Estado atual: " + currentState +
                           " -> Próximo: " + t.nextState +
                           " | Saída: " + t.output);
        currentState = t.nextState;
    }

    // Classe interna para representar uma transição
    private static class Transition {
        String nextState;
        String output;

        Transition(String nextState, String output) {
            this.nextState = nextState;
            this.output = output;
        }
    }
}
