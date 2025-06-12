import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;

public class AFNMain {
    public static void main(String[] args) {
        
        Set<String> simbolosValidos = Set.of("a", "m", "l", "s", "f", "c");

        Scanner scanner = new Scanner(System.in);
        AFN afn = new AFN();

        System.out.print("Digite o nome do arquivo .txt com a definição do AFN: ");
        String nomeArquivo = scanner.nextLine().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();
                if (linha.startsWith("Q:")) {
                    String[] estados = linha.substring(2).trim().split("\\s+");
                    for (String estado : estados) afn.adicionarEstado(estado);
                } else if (linha.startsWith("I:")) {
                    afn.setEstadoInicial(linha.substring(2).trim());
                } else if (linha.startsWith("F:")) {
                    afn.adicionarEstadoFinal(linha.substring(2).trim());
                } else if (linha.equals("---")) {
                    break;
                } else if (linha.contains("->")) {
                    String[] partes = linha.split("->");
                    String origem = partes[0].trim();
                    String[] destinoESimbolos = partes[1].split("\\|");
                    String destino = destinoESimbolos[0].trim();
                    String[] simbolos = destinoESimbolos[1].trim().split("\\s+");

                    for (String s : simbolos) {
                        if (s.length() == 1) {
                            afn.adicionarTransicao(origem, destino, s.charAt(0));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            System.out.println("Encerrando Sistema");
            System.exit(0);
        }

        StringBuilder entrada = new StringBuilder();
        System.out.println("Insira o símbolo do primeiro ingrediente da receita:");

        while (true) {
            String simbolo = scanner.nextLine().trim();

            if ("none".equals(simbolo)) {
                System.out.println("Sem realizar transição!");
                break;
            } else if (!simbolosValidos.contains(simbolo)) {
                System.out.println("Entrada inválida. Digite um símbolo válido:");
                continue;
            }

            entrada.append(simbolo.charAt(0));

            System.out.print("Deseja inserir mais um ingrediente (s/n)? ");
            String resp = scanner.nextLine().trim().toLowerCase();

            while (!resp.equals("s") && !resp.equals("n")) {
                System.out.print("Entrada inválida. Digite 's' para sim ou 'n' para não: ");
                resp = scanner.nextLine().trim().toLowerCase();
            }

            if (!resp.equals("s")) break;
            System.out.print("Qual ingrediente será inserido: ");
        }


        boolean resultado = afn.processarEntrada(entrada.toString());

        if (resultado) {
            System.out.println("Poção criada com sucesso!");
        } else {
            System.out.println("Nenhuma Poção criada.");
            
        }
    }
}
