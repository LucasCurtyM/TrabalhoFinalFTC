import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MooreMain {
    public static void main(String[] args) {
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c", "o");

        Scanner scanner = new Scanner(System.in);
        MaquinaMoore moore = new MaquinaMoore();

        // Exibe cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   LABORATÓRIO DE POÇÕES                  ║");
        System.out.println("║                Máquina de Moore (Saída por Estado)       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.print("\nDigite o nome do arquivo .txt com a definição da Máquina de Moore: ");
        String nomeArquivo = scanner.nextLine().trim();

        // Carregamento do arquivo de definição da Máquina de Moore
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Q:")) {
                    // Exemplo: Q: S0 S1 S2 F erro
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) {
                        moore.adicionarEstado(estado, ""); // saída será definida depois
                    }
                } else if (linha.startsWith("I:")) {
                    moore.setEstadoInicial(linha.substring(2).trim());
                }  else if (linha.startsWith("O:")) {
                    // Exemplo: O: S0=A S1=B S2=C F=OK erro=X
                    String[] pares = linha.substring(2).trim().split("\\s+");
                    for (String par : pares) {
                        String[] partes = par.split("=");
                        if (partes.length == 2) {
                            moore.setSaida(partes[0], partes[1]); // Alterado aqui
                        }
                    }
                }
                else if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                } else if (linha.contains("->")) {
                    // Exemplo: S0 -> S1 | a
                    String[] partes = linha.split("->");
                    String origem = partes[0].trim();
                    String[] direita = partes[1].split("\\|");
                    String destino = direita[0].trim();
                    String[] simbolos = direita[1].trim().split("\\s+");
                    for (String simbolo : simbolos) {
                        if (!simbolo.isEmpty()) {
                            moore.adicionarTransicao(origem, simbolo.charAt(0), destino);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            System.out.println("Encerrando Sistema");
            System.exit(0);
        }

        // Exibe informações da máquina carregada
        System.out.println("\nDefinição da Máquina de Moore carregada com sucesso!");

        // Entrada interativa da receita
        StringBuilder entrada = new StringBuilder();
        System.out.println("\nInsira o símbolo do primeiro ingrediente da receita:");

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

            System.out.print("\nDeseja inserir mais um ingrediente (s/n)? ");
            String resp = scanner.nextLine().trim().toLowerCase();

            while (!resp.equals("s") && !resp.equals("n")) {
                System.out.print("Entrada inválida. Digite 's' para sim ou 'n' para não: ");
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

            moore.processar(entrada.toString());

            System.out.println("\n" + "=".repeat(50));
            System.out.println("RESULTADO: Veja a sequência de saídas acima.");
            System.out.println("=".repeat(50));
        } else {
            System.out.println("\nNenhum ingrediente foi inserido. Processo encerrado.");
        }

        System.out.println("\nObrigado por usar o Sistema de Produção de Poções!");
        scanner.close();        
    }
}