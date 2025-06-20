import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class AFDMain {
    public static void main(String[] args) {
        
        // Símbolos válidos conforme especificação do trabalho
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c");

        Scanner scanner = new Scanner(System.in);
        AFD afd = new AFD();

        // Exibe cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                🧙‍♀️ LABORATÓRIO DE POÇÕES 🧙‍♂️                ║");
        System.out.println("║                Autômato Finito Determinístico              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.print("\nDigite o nome do arquivo .txt com a definição do AFD: ");
        String nomeArquivo = scanner.nextLine().trim();

        // Carregamento do arquivo de definição do AFD
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                
                if (linha.startsWith("Q:")) {
                    // Processa estados
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) {
                        afd.adicionarEstado(estado);
                    }
                } else if (linha.startsWith("I:")) {
                    // Processa estado inicial
                    afd.setEstadoInicial(linha.substring(2).trim());
                } else if (linha.startsWith("F:")) {
                    // Processa estado final
                    afd.adicionarEstadoFinal(linha.substring(2).trim());
                } else if (linha.equals("---")) {
                    // Fim da definição da máquina
                    break;
                } else if (linha.contains("->")) {
                    // Processa transições
                    String[] partes = linha.split("->");
                    String origem = partes[0].trim();
                    String[] destinoESimbolos = partes[1].split("\\|");
                    String destino = destinoESimbolos[0].trim();
                    String[] simbolos = destinoESimbolos[1].trim().split("\\s+");

                    for (String s : simbolos) {
                        if (s.length() == 1) {
                            afd.adicionarTransicao(origem, destino, s.charAt(0));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erro ao ler o arquivo: " + e.getMessage());
            System.out.println("🔚 Encerrando Sistema");
            System.exit(0);
        }

        // Inicializa características dos estados após carregar a máquina
        afd.inicializarCaracteristicasEstados();
        
        // Exibe informações da máquina carregada
        afd.exibirInformacoes();
        
        // Verifica se a máquina está completa
        afd.verificarCompletude();

        // Processamento interativo da receita
        StringBuilder entrada = new StringBuilder();
        System.out.println("\n🧪 INICIANDO PROCESSO DE CRIAÇÃO DE POÇÃO...");
        System.out.println("Insira o símbolo do primeiro ingrediente da receita:");

        while (true) {
            System.out.print("➤ ");
            String simbolo = scanner.nextLine().trim();

            // Comando especial para sair sem processar
            if ("none".equals(simbolo)) {
                System.out.println("🚫 Processo cancelado pelo usuário!");
                break;
            } 
            
            // Validação de entrada
            if (simbolo.isEmpty()) {
                System.out.println("⚠️ Por favor, digite um símbolo válido.");
                continue;
            }
            
            if (!simbolosValidos.contains(simbolo)) {
                System.out.println("❌ Entrada inválida. Os símbolos válidos são: " + simbolosValidos);
                System.out.println("Digite um símbolo válido:");
                continue;
            }

            // Adiciona símbolo à entrada
            entrada.append(simbolo.charAt(0));

            // Pergunta se deseja continuar
            System.out.print("\nDeseja inserir mais um ingrediente (s/n)? ");
            String resp = scanner.nextLine().trim().toLowerCase();

            // Validação da resposta
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
            
            boolean resultado = afd.processarEntrada(entrada.toString());

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
        scanner.close();
    }
}
