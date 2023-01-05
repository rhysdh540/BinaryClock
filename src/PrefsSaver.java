import java.io.*;

/**
 * Simple utility class for saving the preferences of a {@link Panel} to a file and reading them back.<br><br>
 * Since this program only uses one Panel, I didn't bother adding support for multiple files, and if multiple Panels are used, the one file will simply be overwritten.
 */
public class PrefsSaver {
    /**
     * The file to which the preferences are written and saved.
     */
    private static final File PREFS_FILE_PATH = new File(System.getProperty("user.home") + File.separator + ".rdhbinaryclock" + File.separator + "prefs.txt");

    /**
     * Don't let anyone instantiate this class.
     */
    private PrefsSaver(){
        throw new RuntimeException("It is illegal to instantiate this class.");
    }

    /**
     * Updates the file with the preferences for a panel.
     * @param p The panel to update preferences for
     */
    public static void writePrefs(Panel p){
        writePrefs(p.isClock12hr(), p.isDarkMode(), p.isDecimalShown(), p.isDecimalFlipped(), p.isBinaryFlipped(), p.isTextBin());
    }
    /**
     * Updates the file with raw booleans
     * @param clock12hr Whether or not the clocks use 12 hour time
     * @param darkMode Whether or not the panel is in dark mode
     * @param decimalShown Whether or not the decimal clock is shown
     * @param decimalFlipped Whether or not the decimal clock is moved to the right side of the panel
     * @param binaryFlipped Whether or not the binary clock is flipped
     * @param binText Whether or not the binary clock uses 0's and 1's
     */
    private static void writePrefs(boolean clock12hr, boolean darkMode, boolean decimalShown, boolean decimalFlipped, boolean binaryFlipped, boolean binText) {
//        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
//            System.err.println("PrefsSaver is not supported on Windows.");
//            return;
//        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(PREFS_FILE_PATH, false)));
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found. Attempting to fix...");
            try {
                makeFile();
                out = new PrintWriter(new BufferedWriter(new FileWriter(PREFS_FILE_PATH, false)));
            } catch (FileNotFoundException e1) {
                System.err.println("File Still Not Found:");
                e1.printStackTrace();
            } catch (IOException e1) {
                System.err.println("I/O Exception:");
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        }
        assert out != null;
        out.println(clock12hr);
        out.println(darkMode);
        out.println(decimalShown);
        out.println(decimalFlipped);
        out.println(binaryFlipped);
        out.println(binText);
        out.close();
    }
    /**
     * Reads the preferences from the file.
     * @return An array of booleans representing the preferences. The order is as follows:<br>
     *    <code>Clock 12 hour mode, Dark mode, Decimal Clock Shown, Decimal Clock Flipped, Binary Clock Flipped, Binary Clock uses 0's and 1's</code>
     */
    private static boolean[] readPrefs(){
//        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
//            System.err.println("PrefsSaver is not supported on Windows.");
//            return new boolean[]{false, true, false, false, false, false};
//        }
        boolean[] prefs = new boolean[6];
        try{
            BufferedReader in = new BufferedReader(new FileReader(PREFS_FILE_PATH));
            for(int i = 0; i < 6; i++){
                prefs[i] = Boolean.parseBoolean(in.readLine());
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found. Attempting to fix...");
            try{ // set default values (everything off except dark mode)
                prefs = new boolean[]{false, true, false, false, false, false};
                makeFile();
            } catch (FileNotFoundException e1) {
                System.err.println("File Still Not Found:");
                e1.printStackTrace();
            } catch (IOException e1) {
                System.err.println("I/O Exception:");
                e1.printStackTrace();
            }
        } catch (IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        } // save the prefs to the file immediately so if the user doesn't change anything it doesn't break
        writePrefs(prefs[0], prefs[1], prefs[2], prefs[3], prefs[4], prefs[5]);
        return prefs;
    }

    /**
     * Creates the file if it doesn't exist.
     * @throws IOException If the file cannot be created.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void makeFile() throws IOException {
        PREFS_FILE_PATH.getParentFile().mkdirs();
        PREFS_FILE_PATH.createNewFile();
    }
    // getters from the file
    public static boolean getClock12hr(){
        return readPrefs()[0];
    }
    public static boolean getDarkMode(){
        return readPrefs()[1];
    }
    public static boolean getShowDecimal(){
        return readPrefs()[2];
    }
    public static boolean getFlipDecimal(){
        return readPrefs()[3];
    }
    public static boolean getFlipBinary(){
        return readPrefs()[4];
    }
    public static boolean getBinText(){
        return readPrefs()[5];
    }
}
