import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Simple utility class for saving the preferences of a {@link Panel} to a file and reading them back.<br><br>
 * Since this program only uses one Panel, I didn't bother adding support for multiple files, and if multiple Panels are used, the one file will simply be overwritten.
 */
public class PrefsSaver {
    /**
     * The file to which the preferences are written and saved.
     */
    private static final File PREFS_FILE_PATH = new File(System.getProperty("user.home") + File.separator + ".bc" + File.separator + "prefs.txt");

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
        PrintWriter out = null;
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter(PREFS_FILE_PATH, false)));
        } catch (IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
            try{
                Files.createDirectory(Paths.get(File.separator + System.getProperty("user.home") + File.separator + ".bc"));
                PREFS_FILE_PATH.createNewFile();
                out = new PrintWriter(new BufferedWriter(new FileWriter(PREFS_FILE_PATH, false)));
            } catch (IOException e1) {
                System.err.println("I/O Exception:");
                e1.printStackTrace();
            }
        }
        assert out != null;
        out.println(p.isClock12hr());
        out.println(p.isDarkMode());
        out.println(p.isDecimalShown());
        out.println(p.isDecimalFlipped());
        out.println(p.isBinaryFlipped());
        out.println(p.getBinText());
        out.close();
    }
    /**
     * Reads the preferences from the file.
     * @return An array of booleans representing the preferences. The order is as follows:<br>
     *    <code>Clock 12 hour mode, Dark mode, Decimal Clock Shown, Decimal Clock Flipped, Binary Clock Flipped, Binary Clock uses 0's and 1's</code>
     */
    private static boolean[] readPrefs(){
        boolean[] prefs = new boolean[6];
        try{
            BufferedReader in = new BufferedReader(new FileReader(PREFS_FILE_PATH));
            for(int i = 0; i < 6; i++){
                prefs[i] = Boolean.parseBoolean(in.readLine());
            }
            in.close();
        } catch (IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        }
        return prefs;
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
