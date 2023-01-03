import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {
            // do macos stuff to make the thing look better
            // note that this must be done BEFORE any swing code
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "BinaryClock");
            System.setProperty("apple.awt.application.appearance", "system");
        }
        // uncomment to print the installed L&Fs
        //for(UIManager.LookAndFeelInfo l : UIManager.getInstalledLookAndFeels())
        //    System.out.println(l.getClassName());
        try {
            if(System.getProperty("os.name").toLowerCase().contains("windows")) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            } else
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(Exception e) {
            e.printStackTrace(System.err);
        }
        new GUI();
    }
}