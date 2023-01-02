import java.io.*;

/**
 * Simple utility class for saving the preferences of a {@link Panel} to a file and reading them back.
 */
public class PrefsSaver {
    private static final File PREFS_FILE_PATH = new File(System.getProperty("user.home") + File.separator + ".bc" + File.separator + "prefs.txt");
    private PrefsSaver() {}
    public static void writePrefs(Panel p){
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(PREFS_FILE_PATH, false));
        } catch (java.io.IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        }
        assert out != null;
        out.println(p.isClock12hr());
        out.println(p.isDarkMode());
        out.println(p.isDecimalShown());
        out.println(p.isDecimalFlipped());
        out.println(p.isBinaryFlipped());
        out.close();
    }
    private static boolean[] readPrefs(){
        boolean[] prefs = new boolean[6];
        try{
            BufferedReader in = new BufferedReader(new FileReader(PREFS_FILE_PATH));
            for(int i = 0; i < 6; i++){
                prefs[i] = Boolean.parseBoolean(in.readLine());
            }
            in.close();
        } catch (java.io.IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        }
        return prefs;
    }
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
}
