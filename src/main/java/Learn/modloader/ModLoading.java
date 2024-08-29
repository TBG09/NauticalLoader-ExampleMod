package Learn.modloader;

import Learn.Core.Main;
import Learn.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ModLoading {
// yoo gpt thank you here, well I mean, I understood most of it, except URLClassLoader
    public static void main(String[] args) throws InterruptedException {
        File modsDir = new File("mods");
        if (!modsDir.isDirectory()) {
            Logger.error("Learn.Core.modloader.ModLoading","Mods directory does not exist or is not a directory.");
            System.exit(1);
        }

        File[] jarFiles = modsDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null || jarFiles.length == 0) {
            Logger.info("Learn.Core.modloader.ModLoading","No JAR files found in the mods directory, skipping modloading.");
        } else {
            for (File jarFile : jarFiles) {
                try {
                    Logger.info("Learn.Core.modloader.ModLoading","Loading JAR file: " + jarFile.getName());
                    URL jarUrl = jarFile.toURI().toURL();

                    try (URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl})) {
                        // Example: Load the ModMain class from the JAR
                        Class<?> modMainClass = Class.forName("ModMain", true, classLoader);
                        Object modMainInstance = modMainClass.getDeclaredConstructor().newInstance();

                        // Example: Call the main method of ModMain
                        modMainClass.getMethod("main", String[].class).invoke(modMainInstance, (Object) new String[]{});
                    }

                } catch (Exception e) {
                    Logger.fatal("Learn.Core.modloader.ModLoading", "Failed to load JAR file: " + jarFile.getName() + " with error: " + e.getMessage());
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }

        // Continue execution regardless of whether JAR files were found
        Main mainStaticOverride = new Main();
        mainStaticOverride.main(args);
    }
}
