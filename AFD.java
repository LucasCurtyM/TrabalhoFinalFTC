import java.util.*;

public class AFD {
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
    
    // Classe para representar ingredientes e suas propriedades
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
    
    // Classe para representar poções e suas receitas
    private static class Pocao {
        String nome;
        String descricao;
        
        Pocao(String nome, String descricao) {
            this.nome = nome;
            this.descricao = descricao;
        }
    }
    
    private Set<String> estados = new HashSet<>();
    private Set<Character> alfabeto = new HashSet<>();
    private String estadoInicial;
    private Set<String> estadosFinais = new HashSet<>();
    private String estadoErro;
    private List<Transicao> transicoes = new ArrayList<>();
    private Map<Character, Ingrediente> ingredientes = new HashMap<>();
    private Map<String, Pocao> receitas = new HashMap<>();
    private Map<String, String> caracteristicasEstados = new HashMap<>();
    
    public AFD() {
        inicializarIngredientes();
        inicializarReceitas();
    }
    
    private void inicializarIngredientes() {
        ingredientes.put('a', new Ingrediente('a', "Água", "Líquido transparente, base neutra"));
        ingredientes.put('m', new Ingrediente('m', "Mel", "Substância doce, propriedades energizantes"));
        ingredientes.put('l', new Ingrediente('l', "Lágrima de ogro", "Líquido viscoso, propriedades mágicas"));
        ingredientes.put('s', new Ingrediente('s', "Sálvia", "Erva aromática, propriedades purificadoras"));
        ingredientes.put('f', new Ingrediente('f', "Folha de carvalho", "Folha robusta, aumenta resistência"));
        ingredientes.put('c', new Ingrediente('c', "Casca de árvore", "Casca envelhecida, estabilizante natural"));
    }
    
    private void inicializarReceitas() {
        receitas.put("F", new Pocao("Poção de Cura Básica", "Uma poção simples com propriedades restauradoras"));
        receitas.put("final1", new Pocao("Poção de Força", "Aumenta temporariamente a força física"));
        receitas.put("final2", new Pocao("Poção de Sabedoria", "Clareia a mente e melhora o raciocínio"));
    }
    
    public void inicializarCaracteristicasEstados() {
        caracteristicasEstados.put("I", "🧪 Caldeirão vazio, pronto para receber ingredientes");
        caracteristicasEstados.put("estado1", "🌊 Base líquida estabelecida, mistura transparente");
        caracteristicasEstados.put("estado2", "✨ Primeira reação química, pequenas bolhas se formam");
        caracteristicasEstados.put("estado3", "🌿 Aroma herbáceo se desenvolve, cor esverdeada");
        caracteristicasEstados.put("F", "🎉 Poção finalizada! Brilho dourado e aroma agradável");
        caracteristicasEstados.put("final1", "💪 Poção com tonalidade vermelha, energia pulsante");
        caracteristicasEstados.put("final2", "🧠 Poção azulada, emanando sabedoria antiga");
        caracteristicasEstados.put("erro", "💀 Mistura estragou! Fumaça tóxica, ingredientes incompatíveis");
    }
    
    public void adicionarEstado(String estado) {
        estados.add(estado);
        if (estado.equals("erro")) {
            setEstadoErro(estado);
        }
    }
    
    public void setEstadoInicial(String estado) {
        this.estadoInicial = estado;
    }
    
    public void adicionarEstadoFinal(String estado) {
        estadosFinais.add(estado);
    }
    
    public void setEstadoErro(String estado) {
        this.estadoErro = estado;
    }
    
    public void adicionarTransicao(String origem, String destino, char simbolo) {
        transicoes.add(new Transicao(origem, destino, simbolo));
        alfabeto.add(simbolo);
    }
    
    public void exibirInformacoes() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           🧙‍♀️ SISTEMA DE PRODUÇÃO DE POÇÕES 🧙‍♂️");
        System.out.println("=".repeat(60));
        
        System.out.println("\n📋 INGREDIENTES DISPONÍVEIS:");
        System.out.println("┌─────┬─────────────────────┬──────────────────────────────┐");
        System.out.println("│ Cód │ Nome                │ Propriedades                 │");
        System.out.println("├─────┼─────────────────────┼──────────────────────────────┤");
        for (Ingrediente ing : ingredientes.values()) {
            System.out.printf("│  %c  │ %-19s │ %-28s │%n", 
                ing.simbolo, ing.nome, ing.propriedades);
        }
        System.out.println("└─────┴─────────────────────┴──────────────────────────────┘");
        
        System.out.println("\n🔧 ESTADOS DA MÁQUINA:");
        for (String estado : estados) {
            String tipo = "";
            if (estado.equals(estadoInicial)) tipo += "[INICIAL] ";
            if (estadosFinais.contains(estado)) tipo += "[FINAL] ";
            if (estado.equals(estadoErro)) tipo += "[ERRO] ";
            System.out.printf("  %-10s %s\n", estado, tipo);
        }
    }
    
    public String processarSimbolo(String estadoAtual, char simbolo) {
        for (Transicao t : transicoes) {
            if (t.origem.equals(estadoAtual) && t.simbolo == simbolo) {
                return t.destino;
            }
        }
        return estadoErro; // Se não encontrou transição válida
    }
    
    public void exibirCaracteristicasEstado(String estado) {
        if (caracteristicasEstados.containsKey(estado)) {
            System.out.println("Estado atual: " + estado);
            System.out.println("Características: " + caracteristicasEstados.get(estado));
        }
    }
    
    public void exibirInformacaoIngrediente(char simbolo) {
        if (ingredientes.containsKey(simbolo)) {
            Ingrediente ing = ingredientes.get(simbolo);
            System.out.println("✅ Ingrediente adicionado: " + ing.nome);
            System.out.println("   Propriedades: " + ing.propriedades);
        } else {
            System.out.println("❓ Ingrediente desconhecido: " + simbolo);
        }
    }
    
    public boolean processarEntrada(String entrada) {
        String estadoAtual = estadoInicial;
        System.out.println("\n🧪 PROCESSANDO RECEITA...");
        exibirCaracteristicasEstado(estadoAtual);
        
        for (int i = 0; i < entrada.length(); i++) {
            char simbolo = entrada.charAt(i);
            System.out.println("\n➤ Processando ingrediente: " + simbolo);
            exibirInformacaoIngrediente(simbolo);
            
            String proximoEstado = processarSimbolo(estadoAtual, simbolo);
            
            if (proximoEstado.equals(estadoErro)) {
                System.out.println("❌ ERRO: Transição inválida!");
                exibirCaracteristicasEstado(proximoEstado);
                return false;
            }
            
            estadoAtual = proximoEstado;
            exibirCaracteristicasEstado(estadoAtual);
        }
        
        // Verifica se está em estado final
        if (estadosFinais.contains(estadoAtual)) {
            System.out.println("\n🎉 SUCESSO!");
            if (receitas.containsKey(estadoAtual)) {
                Pocao pocao = receitas.get(estadoAtual);
                System.out.println("Poção criada: " + pocao.nome);
                System.out.println("Descrição: " + pocao.descricao);
            }
            return true;
        } else {
            System.out.println("\n⚠️ RECEITA INCOMPLETA!");
            System.out.println("A sequência não terminou em um estado final.");
            return false;
        }
    }
    
    // Getters
    public String getEstadoInicial() {
        return estadoInicial;
    }
    
    public Set<String> getEstadosFinais() {
        return estadosFinais;
    }
    
    public String getEstadoErro() {
        return estadoErro;
    }
    
    public Set<Character> getAlfabeto() {
        return alfabeto;
    }
    
    public Set<String> getEstados() {
        return estados;
    }
    
    // Método para verificar se a máquina está completa
    public boolean verificarCompletude() {
        boolean completa = true;
        System.out.println("\n🔍 VERIFICANDO COMPLETUDE DA MÁQUINA...");
        
        for (String estado : estados) {
            if (estado.equals(estadoErro)) continue;
            
            for (char simbolo : alfabeto) {
                boolean temTransicao = false;
                for (Transicao t : transicoes) {
                    if (t.origem.equals(estado) && t.simbolo == simbolo) {
                        temTransicao = true;
                        break;
                    }
                }
                if (!temTransicao) {
                    System.out.println("⚠️ AVISO: Transição faltante de " + estado + " com símbolo " + simbolo);
                    completa = false;
                }
            }
        }
        
        if (completa) {
            System.out.println("✅ Máquina AFD está completa!");
        } else {
            System.out.println("❌ Máquina AFD não está completa!");
        }
        
        return completa;
    }
}