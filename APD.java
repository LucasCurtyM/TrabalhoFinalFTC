import java.util.*;

public class APD {
    private static class Transicao {
        String origem;
        String destino;
        char simboloEntrada;
        char simboloPilhaLido;
        char simboloPilhaEmpilhado;

        Transicao(String origem, String destino, char simboloEntrada, char simboloPilhaLido, char simboloPilhaEmpilhado) {
            this.origem = origem;
            this.destino = destino;
            this.simboloEntrada = simboloEntrada;
            this.simboloPilhaLido = simboloPilhaLido;
            this.simboloPilhaEmpilhado = simboloPilhaEmpilhado;
        }
    }

    private Set<String> estados = new HashSet<>();
    private Set<Character> alfabeto = new HashSet<>();
    private Set<Character> alfabetoPilha = new HashSet<>();
    private String estadoInicial;
    private Set<String> estadosFinais = new HashSet<>();
    private List<Transicao> transicoes = new ArrayList<>();
    private char simboloInicialPilha = 'Z';
    private Map<Character, Character> reacoes = new HashMap<>();

    public void setEstadoInicial(String estado) {
        this.estadoInicial = estado;
    }

    public void adicionarEstado(String estado) {
        estados.add(estado);
    }

    public void adicionarEstadoFinal(String estado) {
        estadosFinais.add(estado);
    }

    public void adicionarTransicao(String origem, String destino, char simboloEntrada, char simboloPilhaLido, char simboloPilhaEmpilhado) {
        transicoes.add(new Transicao(origem, destino, simboloEntrada, simboloPilhaLido, simboloPilhaEmpilhado));
        alfabeto.add(simboloEntrada);
        alfabetoPilha.add(simboloPilhaLido);
        alfabetoPilha.add(simboloPilhaEmpilhado);
    }

    public void setSimboloInicialPilha(char simbolo) {
        this.simboloInicialPilha = simbolo;
    }

    private static class Ingrediente {
        char simbolo;
        String nome;
        String propriedades;

        Ingrediente(char simbolo, String nome, String propriedades) {
            this.simbolo = simbolo;
            this.nome = nome;
            this.propriedades = propriedades;
        }
    }

    private Map<Character, Ingrediente> ingredientes = new HashMap<>();

    // Inicializa ingredientes padrão
    public void inicializarIngredientes() {
        ingredientes.put('a', new Ingrediente('a', "Água", "Líquido transparente, base neutra"));
        ingredientes.put('m', new Ingrediente('m', "Mel", "Substância doce, propriedades energizantes"));
        ingredientes.put('l', new Ingrediente('l', "Lágrima de ogro", "Líquido viscoso, propriedades mágicas"));
        ingredientes.put('s', new Ingrediente('s', "Sálvia", "Erva aromática, propriedades purificadoras"));
        ingredientes.put('f', new Ingrediente('f', "Folha de carvalho", "Folha robusta, aumenta resistência"));
        ingredientes.put('c', new Ingrediente('c', "Casca de árvore", "Casca envelhecida, estabilizante natural"));
    }

    // Exibe informações de um ingrediente
    public void exibirInformacaoIngrediente(char simbolo) {
        Ingrediente ing = ingredientes.get(simbolo);
        if (ing != null) {
            System.out.println("Ingrediente: " + ing.nome + " (" + ing.simbolo + ")");
            System.out.println("Propriedades: " + ing.propriedades);
        } else {
            System.out.println("Ingrediente não encontrado para o símbolo: " + simbolo);
        }
    }

    // Exibe todos os ingredientes
    public void exibirTodosIngredientes() {
        System.out.println("=== Ingredientes cadastrados ===");
        for (Ingrediente ing : ingredientes.values()) {
            System.out.println(ing.simbolo + " - " + ing.nome + ": " + ing.propriedades);
        }
    }

    public void inicializarReacoes() {
        reacoes.put('a', 'D'); // Água provoca Dissolução
        reacoes.put('m', 'A'); // Mel provoca Adocicação
        reacoes.put('l', 'M'); // Lágrima de ogro provoca Magia
        reacoes.put('s', 'S'); // Sal provoca Salgamento
        reacoes.put('f', 'R'); // Folha de carvalho provoca Resistência
        reacoes.put('c', 'E'); // Casca de árvore provoca Estabilidade
    }

    public boolean processarEntrada(String entrada) {
        Stack<Character> pilha = new Stack<>();
        pilha.push(simboloInicialPilha);
        String estadoAtual = estadoInicial;

        for (int i = 0; i < entrada.length(); i++) {
            char simbolo = entrada.charAt(i);
            char topoPilha = pilha.isEmpty() ? '\0' : pilha.peek();
            boolean transicaoEncontrada = false;

            for (Transicao t : transicoes) {
                if (t.origem.equals(estadoAtual) && t.simboloEntrada == simbolo && t.simboloPilhaLido == topoPilha) {
                    estadoAtual = t.destino;
                    pilha.pop();
                    if (t.simboloPilhaEmpilhado != 'E') { // 'E' representa "não empilhar nada"
                        pilha.push(t.simboloPilhaEmpilhado);
                    }
                    transicaoEncontrada = true;
                    break;
                }
            }
            if (!transicaoEncontrada) return false;
        }

        return estadosFinais.contains(estadoAtual);
    }

    // Métodos auxiliares
    public Set<String> getEstados() {
        return estados;
    }

    public Set<Character> getAlfabeto() {
        return alfabeto;
    }

    public Set<Character> getAlfabetoPilha() {
        return alfabetoPilha;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public Set<String> getEstadosFinais() {
        return estadosFinais;
    }

    public char getSimboloInicialPilha() {
        return simboloInicialPilha;
    }

    public List<Transicao> getTransicoes() {
        return transicoes;
    }

    // Exibe informações do APD
    public void exibirInformacoes() {
        System.out.println("\n===== AUTÔMATO DE PILHA DETERMINÍSTICO =====");
        System.out.println("Estados: " + estados);
        System.out.println("Alfabeto de entrada: " + alfabeto);
        System.out.println("Alfabeto da pilha: " + alfabetoPilha);
        System.out.println("Estado inicial: " + estadoInicial);
        System.out.println("Estados finais: " + estadosFinais);
        System.out.println("Símbolo inicial da pilha: " + simboloInicialPilha);
        System.out.println("============================================");
    }

    // Verifica se o APD está completo (todas as transições possíveis estão definidas)
   /*  public boolean verificarCompletude() {
        boolean completa = true;
        System.out.println("\nVerificando completude do APD...");
        for (String estado : estados) {
            for (char simbolo : alfabeto) {
                for (char pilha : alfabetoPilha) {
                    boolean existe = false;
                    for (Transicao t : transicoes) {
                        if (t.origem.equals(estado) && t.simboloEntrada == simbolo && t.simboloPilhaLido == pilha) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        System.out.printf("Falta transição para estado=%s, entrada=%s, pilha=%s\n", estado, simbolo, pilha);
                        completa = false;
                    }
                }
            }
        }
        if (completa) {
            System.out.println("O APD está completo.");
        } else {
            System.out.println("O APD NÃO está completo.");
        }
        return completa;
    }*/
}
