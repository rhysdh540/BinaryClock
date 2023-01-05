import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Simple utility class for saving the preferences of a {@link Panel} to a file and reading them back.<br><br>
 * Since this program only uses one Panel, I didn't bother adding support for multiple files, and if multiple Panels are used, the one file will simply be overwritten.
 */
public class PrefsSaver {
    /**
     * The file to which the preferences are written and saved.
     */
    private static final File PREFS_FILE = new File(System.getProperty("user.home") + File.separator + ".rdhbinaryclock" + File.separator + "prefs.txt");

    /**
     * Don't let anyone instantiate this class.
     */
    private PrefsSaver(){
        throw new RuntimeException("It is illegal to instantiate this class.");
    }

    /**
     * Updates the file with a HashMap
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writePrefs(HashMap<String,Boolean> preferences){
        FileWriter out = null;
        try {
            out = new FileWriter(PREFS_FILE, false);
        } catch (FileNotFoundException e) { // TODO: this always runs even though the file very clearly exists
            System.err.println("File Not Found. Attempting to fix...");
            makeFile();
            return;
        } catch (IOException e) {
            if(PREFS_FILE.isDirectory()) {
                System.err.println("There was a directory with the file's name! Attempting to fix...");
                PREFS_FILE.delete();
                makeFile();
            } if(!PREFS_FILE.canWrite()){
                System.err.println("The file is not writable! Attempting to fix...");
                PREFS_FILE.setWritable(true);
                System.err.println("File now should be writable. Check just in case, though!\nFile: " + PREFS_FILE.getAbsolutePath());
                return;
            } else {
                System.err.println("There was an IOException. Please report this to the developer.");
                e.printStackTrace();
                System.exit(1);
            }
        }
        assert out != null; // just in case it somehow slipped through my mess of try-catches back there
        try {
            for (Map.Entry<String,Boolean> mapElement : preferences.entrySet()) { // prints the preferences (name followed by value) to the file
                out.write(mapElement.getKey() + "\n" + mapElement.getValue() + "\n");
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
            Scanner scanner = new Scanner(new FileReader(PREFS_FILE));
            String key,value;
                while(scanner.hasNextLine()) {
                    key = scanner.nextLine();
                    value = scanner.nextLine();
                    preferences.put(key, Boolean.parseBoolean(value));
                }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found. Implementing a Save File");
            makeFile();
        } catch (NoSuchElementException e) {
            System.err.println("File Corrupted! Using Default Preferences");
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
        PREFS_FILE.getParentFile().mkdirs();
        try {
            PREFS_FILE.createNewFile();
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
        prefs.put("Move Decimal Clock to Right Corner",false);
        prefs.put("12 Hour Clock",false);
        prefs.put("Use 0's and 1's",false);
        return prefs;
    }
}
