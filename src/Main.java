public class Main {
    public static void main(String[] args) {
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {
            // do macos stuff to make the thing look better
            // note that this must be done BEFORE any swing code
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "BinaryClock");
            System.setProperty("apple.awt.application.appearance", "system");
        }
        new GUI();
    }
}