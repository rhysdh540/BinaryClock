import java.io.*;
import java.util.*;

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
     * Updates the file with a HashMap
     */
    public static void writePrefs(HashMap<String,Boolean> preferences){
        FileWriter out = null;
        try {
            out = new FileWriter(PREFS_FILE_PATH, false);
        } catch (FileNotFoundException e) {
            System.err.println("File Not Found. Attempting to fix...");
            try {
                makeFile();
                out = new FileWriter(PREFS_FILE_PATH, false);
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
        try {
            for (Map.Entry<String,Boolean> mapElement : preferences.entrySet()) {
                String key = mapElement.getKey();
                boolean value = mapElement.getValue();
                out.write(key+"\n"+value+"\n");
            }
            out.close();
        } catch(IOException e) {
            System.err.println("Saving Preferences Failed! :(");
            e.printStackTrace();
        }
    }
    /**
     * Reads the preferences from the file.
     * @return A HashMap representing the preferences. The order is as follows:<br>
     *    <code>Clock 12 hour mode, Dark mode, Decimal Clock Shown, Decimal Clock Flipped, Binary Clock Flipped, Binary Clock uses 0's and 1's</code>
     */
    public static HashMap<String,Boolean> readPrefs(){
        HashMap<String,Boolean> preferences = new HashMap<>();
        try{
            FileReader in = new FileReader(PREFS_FILE_PATH);
            Scanner scanner = new Scanner(in);
            String key,value;
                while(scanner.hasNextLine()) {
                    key = scanner.nextLine();
                    System.out.println(key);
                    value = scanner.nextLine();
                    preferences.put(key, Boolean.parseBoolean(value));
                }
            in.close();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found. Implementing a Save File");
            makeFile();
        } catch (IOException e) {
            System.err.println("I/O Exception:");
            e.printStackTrace();
        } catch(NoSuchElementException e) {
        System.err.println("File Corrupted! Using Default Preferences ");
        preferences = defaultSettings();
    }
        if(preferences.size() == 0) {
            preferences = defaultSettings();
        }
        writePrefs(preferences);
        return preferences;
    }

    /**
     * Creates the file if it doesn't exist.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void makeFile() {
        PREFS_FILE_PATH.getParentFile().mkdirs();
        try {
            PREFS_FILE_PATH.createNewFile();
        } catch (IOException e) {
            System.err.println("File Failed to be Created");
        }
    }

    /**
     * Returns a hash map with the default settings
     * @return a hash map with the default settings
     */
    public static HashMap<String,Boolean> defaultSettings(){
        HashMap<String,Boolean> prefs = new HashMap<>();
        prefs.put("Show Decimal Clock",false);
        prefs.put("Dark Mode",true);
        prefs.put("Flip Binary Clock",false);
        prefs.put("Flip Decimal Clock",false);
        prefs.put("12 Hour Clock",false);
        prefs.put("Use 0's and 1's",false);
        return prefs;
    }
}
