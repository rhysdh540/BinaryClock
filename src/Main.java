import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        new GUI();
    }

    /*
     * This code block executes before the main method and sets the swing "look and feel" of the program to the operating system's default (or at least something that looks nice on the os)
     */
    static {
        try {
            if(System.getProperty("os.name").toLowerCase().contains("mac")) { // On macOS, we can take advantage of the fact that the system has its own menu bar and use that instead of one in our window.
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("apple.awt.application.name", "BinaryClock");
                System.setProperty("apple.awt.application.appearance", "system");
            }
            else if(System.getProperty("os.name").toLowerCase().contains("windows"))
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // windows default L&F
            else
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); // acceptable alternative (doesn't matter in macOS since we're using the system menubar there)
        } catch (Exception e) {
            System.err.println("Error with Loading Custom UI!");
            e.printStackTrace(System.err);
        }
    }
}