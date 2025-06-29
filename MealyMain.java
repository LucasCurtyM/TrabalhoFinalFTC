import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MealyMain {
    public static void main(String[] args) {
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c", "o");

        Scanner scanner = new Scanner(System.in);
        Mealy mealy = new Mealy();

        // Exibe cabeçalho do sistema
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   LABORATÓRIO DE POÇÕES                  ║");
        System.out.println("║                Máquina de Mealy (Saída por Transição)    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        System.out.print("\nDigite o nome do arquivo .txt com a definição da Máquina de Mealy: ");
        String nomeArquivo = scanner.nextLine().trim();

        // Carregamento do arquivo de definição da Máquina de Mealy
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.startsWith("Q:")) {
                    // Exemplo: Q: S0 S1 S2 F erro
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) {
                        mealy.addState(estado);
                    }
                } else if (linha.startsWith("I:")) {
                    mealy.setInitialState(linha.substring(2).trim());
                } else if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                } else if (linha.contains("->")) {
                    // Exemplo: S0 -> S1 | a/B
                    String[] partes = linha.split("->");
                    String origem = partes[0].trim();
                    String[] direita = partes[1].split("\\|");
                    String destino = direita[0].trim();
                    String[] transicoes = direita[1].trim().split("\\s+");
                    for (String t : transicoes) {
                        // Exemplo de t: a/B
                        String[] inOut = t.split("/");
                        if (inOut.length == 2) {
                            String input = inOut[0].trim();
                            String output = inOut[1].trim();
                            mealy.addTransition(origem, input, destino, output);
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
        System.out.println("\nDefinição da Máquina de Mealy carregada com sucesso!");

        // Entrada interativa da receita
        System.out.println("\nInsira o símbolo do primeiro ingrediente da receita:");

       mealy.setInitialState(mealy.getInitialState());
       
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

            mealy.processSingleInput(simbolo);

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

        System.out.println("\nObrigado por usar o Sistema de Produção de Poções!");
    }
}