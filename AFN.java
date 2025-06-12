import java.util.*;

public class AFN {
    private static class Transicao {
        String origem;
        String destino;
        char simbolo;

        Transicao(String origem, String destino, char simbolo) {
            this.origem = origem;
            this.destino = destino;
            this.simbolo = simbolo;
        }
    }

    private Set<String> estados = new HashSet<>();
    private Set<Character> alfabeto = new HashSet<>();
    private String estadoInicial;
    private Set<String> estadosFinais = new HashSet<>();
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

    public void adicionarTransicao(String origem, String destino, char simbolo) {
        transicoes.add(new Transicao(origem, destino, simbolo));
        alfabeto.add(simbolo);
    }

    public boolean processarEntrada(String entrada) {
        Set<String> estadosAtuais = new HashSet<>();
        estadosAtuais.add(estadoInicial);

        for (char simbolo : entrada.toCharArray()) {
            Set<String> proximosEstados = new HashSet<>();
            for (String estado : estadosAtuais) {
                for (Transicao transicao : transicoes) {
                    if (transicao.origem.equals(estado) && transicao.simbolo == simbolo) {
                        proximosEstados.add(transicao.destino);
                    }
                }
            }
            estadosAtuais = proximosEstados;
            if (estadosAtuais.isEmpty()) break;
        }

        for (String estado : estadosAtuais) {
            if (estadosFinais.contains(estado)) return true;
        }
        return false;
    }
}
