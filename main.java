import java.util.Scanner;

public class main{

    public static void main(String[] args) {
        int escolha;
        int continuar;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=".repeat(70));
        System.out.println(" ".repeat(20) + "Selecione qual tipo de Autômato: ");
        System.out.println("=".repeat(70));

        System.out.println("1- Autômato Finito (AF): ");
        System.out.println("2- Autômato Pilha (AP): ");
        System.out.println("0- Sair ");
        escolha = scanner.nextInt();
        
        switch (escolha){
            case 0:
                break;
            case 1:
                do{
                AFNMain.main(args);

                System.out.println("Deseja continuar fazendo poções com AFN)?");
                System.out.println("1- Sim");
                System.out.println("0- Encerrar");
                continuar = scanner.nextInt();
                while(!(continuar == 0 || continuar == 1)){
                    System.out.println("Informe uma opção válida");
                    continuar = scanner.nextInt();
                }
                }
                while((continuar == 1));
                break;
            case 2:
                do {
                    APDMain.main(args);

                    System.out.println("Deseja continuar fazendo poções com APD?");
                    System.out.println("1- Sim");
                    System.out.println("0- Encerrar");
                    continuar = scanner.nextInt();
                    while (!(continuar == 0 || continuar == 1)) {
                        System.out.println("Informe uma opção válida");
                        continuar = scanner.nextInt();
                    }
                } while (continuar == 1);
                break;
        }
    }
}