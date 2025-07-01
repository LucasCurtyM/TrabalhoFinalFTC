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
        System.out.println("3- Máquina de Turing (MT): ");
        System.out.println("4- Máquina de Moore (MM): ");
        System.out.println("5- Máquina de Mealy (Me): ");
        System.out.println("0- Sair ");
        System.out.print("Opção: ");
        escolha = scanner.nextInt();
        
        switch (escolha){
            case 0:
                break;
            case 1:
                do{
                AFNMain.main(args);
                System.out.println("\n\n");
                System.out.println("=".repeat(70));
                System.out.println("Deseja continuar fazendo poções com AFN)?");
                System.out.println("=".repeat(70));
                System.out.println("1- Sim");
                System.out.println("0- Encerrar");
                System.out.print("Opção: ");
                continuar = scanner.nextInt();
                while(!(continuar == 0 || continuar == 1)){
                    System.out.println("Informe uma opção válida");
                    System.out.print("Opção: ");
                    continuar = scanner.nextInt();
                }
                }
                while((continuar == 1));
                break;
            case 2:
                do {
                    APDMain.main(args);
                    System.out.println("\n\n");
                    System.out.println("=".repeat(70));
                    System.out.println("Deseja continuar fazendo poções com APD?");
                    System.out.println("1- Sim");
                    System.out.println("0- Encerrar");
                    System.out.print("Opção: ");
                    continuar = scanner.nextInt();
                    while (!(continuar == 0 || continuar == 1)) {
                        System.out.println("Informe uma opção válida");
                        System.out.print("Opção: ");
                        continuar = scanner.nextInt();
                    }
                } while (continuar == 1);
                break;
              case 3:
                do {
                    MTmain.main(args);
                    System.out.println("\n\n");
                    System.out.println("=".repeat(70));
                    System.out.println("Deseja continuar fazendo poções com Máquina de Turing?");
                    System.out.println("1- Sim");
                    System.out.println("0- Encerrar");
                    System.out.print("Opção: ");
                    continuar = scanner.nextInt();
                    while (!(continuar == 0 || continuar == 1)) {
                        System.out.println("Informe uma opção válida");
                        System.out.print("Opção: ");
                        continuar = scanner.nextInt();
                    }
                } while (continuar == 1);
                break;
                 case 4:
                do {
                    MooreMain.main(args);
                    System.out.println("\n\n");
                    System.out.println("=".repeat(70));
                    System.out.println("Deseja continuar fazendo poções com Máquina de Moore?");
                    System.out.println("1- Sim");
                    System.out.println("0- Encerrar");
                    System.out.print("Opção: ");
                    continuar = scanner.nextInt();
                    while (!(continuar == 0 || continuar == 1)) {
                        System.out.println("Informe uma opção válida");
                        System.out.print("Opção: ");
                        continuar = scanner.nextInt();
                    }
                } while (continuar == 1);
                break;
                 case 5:
                do {
                    MealyMain.main(args);
                    System.out.println("\n\n");
                    System.out.println("=".repeat(70));
                    System.out.println("Deseja continuar fazendo poções com Máquina de Mealy?");
                    System.out.println("1- Sim");
                    System.out.println("0- Encerrar");
                    System.out.print("Opção: ");
                    continuar = scanner.nextInt();
                    while (!(continuar == 0 || continuar == 1)) {
                        System.out.println("Informe uma opção válida");
                        System.out.print("Opção: ");
                        continuar = scanner.nextInt();
                    }
                } while (continuar == 1);
                break;
        }
        
    }
}