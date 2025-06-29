import java.util.*;

public class MaquinaTuring {
    private static class Transicao {
        String origem;
        String destino;
        char lido;
        char escreve;
        char move; // 'R' = direita, 'L' = esquerda, 'S' = stay

        Transicao(String origem, String destino, char lido, char escreve, char move) {
            this.origem = origem;
            this.destino = destino;
            this.lido = lido;
            this.escreve = escreve;
            this.move = move;
        }
    }

    private Set<String> estados = new HashSet<>();
    private String estadoInicial;
    private Set<String> estadosFinais = new HashSet<>();
    private String estadoErro = "erro";
    private List<Transicao> transicoes = new ArrayList<>();

    public void setEstadoInicial(String estado) {
        this.estadoInicial = estado;
    }

    public void adicionarEstado(String estado) {
        estados.add(estado);
    }

    public void adicionarEstadoFinal(String estado) {
        estadosFinais.add(estado);
    }

    public void adicionarTransicao(String origem, String destino, char lido, char escreve, char move) {
        transicoes.add(new Transicao(origem, destino, lido, escreve, move));
    }

    public boolean processar(String entrada) {
        // Substitui '_' por espaço e cria uma fita com margem extra
        entrada = entrada.replace('_', ' ');
        char[] fita = new char[entrada.length() + 20]; // margem extra
        Arrays.fill(fita, ' ');
        System.arraycopy(entrada.toCharArray(), 0, fita, 0, entrada.length());

        int cabeca = 0;
        String estadoAtual = estadoInicial;

        while (true) {
            char simboloLido = fita[cabeca];
            boolean encontrou = false;

            for (Transicao t : transicoes) {
                if (t.origem.equals(estadoAtual) && t.lido == simboloLido) {
                    // Transição encontrada
                    fita[cabeca] = t.escreve;
                    estadoAtual = t.destino;

                    if (t.move == 'R') cabeca++;
                    else if (t.move == 'L') cabeca--;
                    // 'S' = Stay (não move a cabeça)

                    encontrou = true;
                    break;
                }
            }

            if (!encontrou) {
                System.out.println("Erro: símbolo '" + simboloLido + "' não reconhecido no estado " + estadoAtual);
                estadoAtual = estadoErro;
            }

            if (estadoAtual.equals(estadoErro)) {
                System.out.println("Fita final: " + new String(fita).trim());
                return false;
            }

            if (estadosFinais.contains(estadoAtual)) {
                System.out.println("Fita final: " + new String(fita).trim());
                return true;
            }
        }
    }

  
}
