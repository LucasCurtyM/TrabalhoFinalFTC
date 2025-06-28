import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class APDMain {
    public static void main(String[] args) {
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c");

        Scanner scanner = new Scanner(System.in);
        APD apd = new APD();

        // Exibe cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                🧙‍♀️ LABORATÓRIO DE POÇÕES 🧙‍♂️                ║");
        System.out.println("║           Autômato de Pilha Determinístico (APD)           ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.print("\nDigite o nome do arquivo .txt com a definição do APD: ");
        String nomeArquivo = scanner.nextLine().trim();

        // Carregamento do arquivo de definição do APD
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Q:")) {
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) {
                        apd.adicionarEstado(estado);
                    }
                } else if (linha.startsWith("I:")) {
                    apd.setEstadoInicial(linha.substring(2).trim());
                } else if (linha.startsWith("F:")) {
                    apd.adicionarEstadoFinal(linha.substring(2).trim());
                } else if (linha.startsWith("P:")) {
                    apd.setSimboloInicialPilha(linha.substring(2).trim().charAt(0));
                } else if (linha.equals("---")) {
                    break;
                } else if (linha.contains("->")) {
                    // Exemplo de linha: q0, a, Z -> q1, X
                    String[] partes = linha.split("->");
                    String[] esquerda = partes[0].split(",");
                    String origem = esquerda[0].trim();
                    char simboloEntrada = esquerda[1].trim().charAt(0);
                    char simboloPilhaLido = esquerda[2].trim().charAt(0);

                    String[] direita = partes[1].split(",");
                    String destino = direita[0].trim();
                    char simboloPilhaEmpilhado = direita[1].trim().charAt(0);

                    apd.adicionarTransicao(origem, destino, simboloEntrada, simboloPilhaLido, simboloPilhaEmpilhado);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler o arquivo: " + e.getMessage());
            System.out.println("🔚 Encerrando Sistema");
            System.exit(0);
        }

        // Inicializa ingredientes padrão
        apd.inicializarIngredientes();

        // Exibe informações da máquina carregada
        apd.exibirInformacoes();
        
        // Inicializa as reações
        apd.inicializarReacoes();

        // Processamento interativo da receita
        StringBuilder entrada = new StringBuilder();
        System.out.println("\n🧪 INICIANDO PROCESSO DE CRIAÇÃO DE POÇÃO...");
        System.out.println("Insira o símbolo do primeiro ingrediente da receita:");

        while (true) {
            System.out.print("➤ ");
            String simbolo = scanner.nextLine().trim();

            if ("none".equals(simbolo)) {
                System.out.println("🚫 Processo cancelado pelo usuário!");
                break;
            }

            if (simbolo.isEmpty()) {
                System.out.println("⚠️ Por favor, digite um símbolo válido.");
                continue;
            }

            if (!simbolosValidos.contains(simbolo)) {
                System.out.println("❌ Entrada inválida. Os símbolos válidos são: " + simbolosValidos);
                System.out.println("Digite um símbolo válido:");
                continue;
            }

            entrada.append(simbolo.charAt(0));

            System.out.print("\nDeseja inserir mais um ingrediente (s/n)? ");
            String resp = scanner.nextLine().trim().toLowerCase();

            while (!resp.equals("s") && !resp.equals("n")) {
                System.out.print("⚠️ Entrada inválida. Digite 's' para sim ou 'n' para não: ");
                resp = scanner.nextLine().trim().toLowerCase();
            }

            if (!resp.equals("s")) {
                break;
            }

            System.out.print("Qual ingrediente será inserido: ");
        }

        // Processa a entrada final se houver símbolos
        if (entrada.length() > 0) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PROCESSANDO RECEITA: " + entrada.toString());
            System.out.println("=".repeat(50));

            boolean resultado = apd.processarEntrada(entrada.toString());

            System.out.println("\n" + "=".repeat(50));
            if (resultado) {
                System.out.println("🎉 RESULTADO: Poção criada com sucesso!");
                System.out.println("✨ A receita foi executada perfeitamente!");
            } else {
                System.out.println("❌ RESULTADO: Nenhuma poção foi criada.");
                System.out.println("💔 A receita não pôde ser completada.");
            }
            System.out.println("=".repeat(50));
        } else {
            System.out.println("\n🚫 Nenhum ingrediente foi inserido. Processo encerrado.");
        }

        System.out.println("\n🔚 Obrigado por usar o Sistema de Produção de Poções!");
    }
}
