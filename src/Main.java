import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        System.out.println("Introduce el numero de ventanas que deseas abrir: ");
        int ventanas = scan.nextInt();

        ProcessBuilder pb = new ProcessBuilder();
        pb.command("cmd.exe", "/c", "Start Chrome");

        Process process = null;

        ArrayList<Process> listaProcesos = new ArrayList<Process>();
        boolean allDead = false;
        try {

            for (int i = 0; i < ventanas; i++) {
                process = pb.start();
                listaProcesos.add(process);
            }

            while (allDead == false) {
                for (int i = 0; i < listaProcesos.size(); i++) {
                    Process proceso = listaProcesos.get(i);

                    if (!proceso.isAlive()) {
                        listaProcesos.remove(i);
                    }

                    if (listaProcesos.size() == 0) {
                        process.destroy();
                        allDead = true;
                    }
                }
            }
            System.out.println(listaProcesos);

            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al abrir la aplicación");
            throw new RuntimeException(e);
        }
    }
}