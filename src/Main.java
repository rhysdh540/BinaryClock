import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            if(System.getProperty("os.name").toLowerCase().contains("mac")) {
                // do macOS stuff to make the thing look better
                // note that this must be done BEFORE any swing code
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
        new GUI();
    }
}