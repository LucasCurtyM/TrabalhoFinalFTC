import java.util.*;

public class MaquinaMoore {
    private static class Transicao {
        String origem;
        char simbolo;
        String destino;

        Transicao(String origem, char simbolo, String destino) {
            this.origem = origem;
            this.simbolo = simbolo;
            this.destino = destino;
        }
    }

    private Set<String> estados = new HashSet<>();
    private Set<Character> alfabeto = new HashSet<>();
    private String estadoInicial;
    private Map<String, String> saidas = new HashMap<>();
    private List<Transicao> transicoes = new ArrayList<>();

    public void adicionarEstado(String estado, String saida) {
        estados.add(estado);
        saidas.put(estado, saida);
    }

    public void setEstadoInicial(String estado) {
        this.estadoInicial = estado;
    }
    // Adicione na classe MaquinaMoore
    public void setSaida(String estado, String saida) {
        saidas.put(estado, saida);
    }

    public void adicionarTransicao(String origem, char simbolo, String destino) {
        transicoes.add(new Transicao(origem, simbolo, destino));
        alfabeto.add(simbolo);
    }

    // Nova função: só adiciona transição se não existir
    public void adicionarTransicaoSeAusente(String origem, char simbolo, String destino) {
        for (Transicao t : transicoes) {
            if (t.origem.equals(origem) && t.simbolo == simbolo) return; // já existe
        }
        adicionarTransicao(origem, simbolo, destino);
    }

    public void processar(String entrada) {
        String estadoAtual = estadoInicial;
        System.out.println("\nProcessando entrada: " + entrada);
        System.out.print("Saída: [" + estadoAtual + "] " + saidas.get(estadoAtual));

        for (char simbolo : entrada.toCharArray()) {
            boolean encontrou = false;

            for (Transicao t : transicoes) {
                if (t.origem.equals(estadoAtual) && t.simbolo == simbolo) {
                    estadoAtual = t.destino;
                    System.out.print(" [" + estadoAtual + "] " + saidas.get(estadoAtual));
                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                System.out.print(" [erro: símbolo '" + simbolo + "' inválido para o estado " + estadoAtual + "]");
                break;
            }
        }

        System.out.println(); // nova linha final
    }

}