
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MTmain {
    public static void main(String[] args) {
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c", "o", "_");

        Scanner scanner = new Scanner(System.in);
        MaquinaTuring mt = new MaquinaTuring();

        // Exibe cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   LABORATÓRIO DE POÇÕES                    ║");
        System.out.println("║                  Máquina de Turing (MT)                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.print("\nDigite o nome do arquivo .txt com a definição da MT: ");
        String nomeArquivo = scanner.nextLine().trim();

        // Carregamento do arquivo de definição da MT
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Q:")) {
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) {
                        mt.adicionarEstado(estado);
                    }
                } else if (linha.startsWith("I:")) {
                    mt.setEstadoInicial(linha.substring(2).trim());
                } else if (linha.startsWith("F:")) {
                    mt.adicionarEstadoFinal(linha.substring(2).trim());
                } else if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                } else if (linha.contains("->")) {
                    // Exemplo: q1 -> q2 | a, X, R
                    String[] partes = linha.split("->");
                    String origem = partes[0].trim();
                    String[] direita = partes[1].split("\\|");
                    String destino = direita[0].trim();
                    String[] transicoes = direita[1].trim().split("\\s+");
                    for (String t : transicoes) {
                        String[] params = t.split(",");
                        if (params.length < 3) continue;
                        char lido = params[0].trim().charAt(0);
                        char escreve = params[1].trim().charAt(0);
                        char move = params[2].trim().charAt(0);
                        mt.adicionarTransicao(origem, destino, lido, escreve, move);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            System.out.println("Encerrando Sistema");
            System.exit(0);
        }

        // Exibe informações da máquina carregada
        System.out.println("\nDefinição da Máquina de Turing carregada com sucesso!");

        // Entrada interativa da fita
        StringBuilder entrada = new StringBuilder();
        System.out.println("\nInsira o símbolo do primeiro ingrediente da fita (use _ para branco):");

        while (true) {
            System.out.print("> ");
            String simbolo = scanner.nextLine().trim();

            if ("none".equals(simbolo)) {
                System.out.println("Processo cancelado pelo usuário!");
                break;
            }

            if (simbolo.isEmpty()) {
                System.out.println("Por favor, digite um símbolo válido.");
                continue;
            }

            if (!simbolosValidos.contains(simbolo)) {
                System.out.println("Entrada inválida. Os símbolos válidos são: " + simbolosValidos);
                System.out.println("Digite um símbolo válido:");
                continue;
            }

            entrada.append(simbolo.charAt(0));

            System.out.print("\nDeseja inserir mais um símbolo na fita (s/n)? ");
            String resp = scanner.nextLine().trim().toLowerCase();

            while (!resp.equals("s") && !resp.equals("n")) {
                System.out.print("Entrada inválida. Digite 's' para sim ou 'n' para não: ");
                resp = scanner.nextLine().trim().toLowerCase();
            }

            if (!resp.equals("s")) {
                break;
            }

            System.out.print("Qual símbolo será inserido: ");
        }

        // Processa a entrada final se houver símbolos
        if (entrada.length() > 0) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PROCESSANDO FITA: " + entrada.toString());
            System.out.println("=".repeat(50));

            boolean resultado = mt.processar(entrada.toString());

            System.out.println("\n" + "=".repeat(50));
            if (resultado) {
                System.out.println("\u001B[32m" + "RESULTADO: Poção criada com sucesso (Aceita)!" + "\u001B[0m");
            } else {
                System.out.println("\u001B[31m" + "RESULTADO: Nenhuma poção foi criada (Rejeita)." + "\u001B[0m");
            }
            System.out.println("=".repeat(50));
        } else {
            System.out.println("\nNenhum símbolo foi inserido. Processo encerrado.");
        }

        System.out.println("\nObrigado por usar o Sistema de Produção de Poções!");
    }

}
