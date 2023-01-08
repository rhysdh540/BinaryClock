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
     * The default preferences to use if the file doesn't exist or is corrupted.
     */
    public static final HashMap<String, Boolean> DEFAULT_PREFS = new HashMap<>(Map.of(
            "Show Decimal Clock",false,
            "Dark Mode"         ,true,
            "Flip Binary Clock" ,false,
            "Move Decimal Clock to Right Corner",false,
            "12 Hour Clock"     ,false,
            "Use 0's and 1's"   ,false
    ));

    /**
     * Don't let anyone instantiate this class.
     */
    private PrefsSaver(){
        throw new RuntimeException("It is illegal to instantiate this class.");
    }

    /**
     * Updates the file with a HashMap.
     * @param preferences the HashMap to write to the file
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writePrefs(HashMap<String,Boolean> preferences){
        FileWriter out;
        try { // try to create the writer
            out = new FileWriter(PREFS_FILE, false);
        } catch (FileNotFoundException e) { // if not found
            if(PREFS_FILE.isDirectory()){ // if there's a directory with the same name as the file
                System.err.println("The file is a directory. Attempting to delete it...");
                PREFS_FILE.delete();
            } else { // otherwise the file doesn't exist
                System.err.println("File Not Found. Attempting to fix...");
            }
            // create the file with default preferences
            makeFile();
            writePrefs(DEFAULT_PREFS);
            return;
        } catch (IOException e) { // if there's an IO exception, probably a permissions error
            System.err.println("\033[31mThere was an error creating the file. Please check your permissions for the directory and try again.\n\033[37m" + PREFS_FILE.getAbsolutePath() + "\033[0m");
            PREFS_FILE.setWritable(true);
            throw new RuntimeException(e);
        }
        try {
            for (Map.Entry<String, Boolean> mapElement : preferences.entrySet()) { // prints the preferences (name followed by value) to the file
                out.write(mapElement.getKey() + "\n" + mapElement.getValue() + "\n");
            }
            out.close();
        } catch(IOException e) { // something unexpected happened
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
            Scanner sc = new Scanner(new FileReader(PREFS_FILE));
                while(sc.hasNextLine()) {
                    // transform the lines into a HashMap
                    preferences.put(sc.nextLine(), Boolean.parseBoolean(sc.nextLine()));
                }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found. Implementing a Save File");
            makeFile();
            writePrefs(DEFAULT_PREFS);
        } catch (NoSuchElementException e) { // unexpected element
            System.err.println("File Corrupted! Using Default Preferences");
            preferences = DEFAULT_PREFS;
        }
        if(preferences.isEmpty()) {
            preferences = DEFAULT_PREFS;
        }
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
}
