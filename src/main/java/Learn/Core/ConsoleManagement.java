package Learn.Core;

import Learn.Logger;

import java.io.IOException;

public class ConsoleManagement {

    public static void clearConsole() {
        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                // Unix-like (Linux, macOS)
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else {
                // Unsupported OS
                Logger.warn("Learn.Core.ConsoleManagement","Unable to determine OS. Cannot clear console.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void main(String[] args) {

        clearConsole();
        Logger.info("Learn.Core.ConsoleManagement","Cleared Console!");
    }
}