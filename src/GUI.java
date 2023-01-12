import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.Thread;

public class GUI {
    @SuppressWarnings("FieldMayBeFinal")
    private Panel panel = new Panel();

    @SuppressWarnings("UnusedLabel")
    public GUI() {
        JFrame gooey = new JFrame("Binary Clock");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/resources/icon.png");
        try { // set image
            Taskbar.getTaskbar().setIconImage(icon);
        } catch (UnsupportedOperationException e) { // if not on macos, try the windows method
            gooey.setIconImage(icon);
        } catch (SecurityException e) {
            System.err.println("There was a security exception for: 'Taskbar.setIconImage'");
        }
        // create the menu bar toggles
        createMB:{
            JMenuBar mb = new JMenuBar(); // create the menu bar
            // create the menus
            JMenu clockMenu = new JMenu("Clock");
            JMenu appearanceMenu = new JMenu("Appearance");

            // create the buttons
            JCheckBoxMenuItem item = createJCheckBoxMenuItem("Move Decimal Clock to Right Corner");
            item.setEnabled(panel.prefs.get("Show Decimal Clock"));
            // this is so we can disable the "move decimal clock" button if the decimal clock itself is disabled
            appearanceMenu.add(createJCheckBoxMenuItem("Show Decimal Clock", e -> {
                panel.prefs.put("Show Decimal Clock", !panel.prefs.get("Show Decimal Clock"));
                PrefsSaver.writePrefs(panel.prefs);
                item.setEnabled(panel.prefs.get("Show Decimal Clock"));
            }));
            createJCheckBoxMenuItem("Dark Mode", appearanceMenu);
            createJCheckBoxMenuItem("Flip Binary Clock", appearanceMenu);
            // add the move decimal clock button in the right place
            appearanceMenu.add(item);
            createJCheckBoxMenuItem("12 Hour Clock", clockMenu);
            createJCheckBoxMenuItem("Use 0's and 1's", appearanceMenu);
            createJCheckBoxMenuItem("RAVE MODE", appearanceMenu);
            // add the menus to the main bar
            mb.add(appearanceMenu);
            mb.add(clockMenu);
            gooey.setJMenuBar(mb);
        }

        // stuff that must be done (or else)
        gooey.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // dispose on close superiority
        Dimension size = new Dimension(854, 480);
        gooey.setSize(size);
        gooey.setMinimumSize(size);
        gooey.getContentPane().add(panel);
        gooey.setVisible(true);
    }

    /**
     * Creates a {@code JCheckBoxMenuItem} with the given text and adds it to the given menu.
     * @param name the text of the {@code JCheckBoxMenuItem} (and therefore also the name of the preference that it changes)
     * @param menu the {@code JMenu} to add the {@code JCheckBoxMenuItem} to
     * @throws NullPointerException if the given menu is null or if the given preference name doesn't exist
     */
    public void createJCheckBoxMenuItem(String name, JMenu menu) {
        menu.add(createJCheckBoxMenuItem(name));
    }

    /**
     * Creates a {@code JCheckBoxMenuItem} with the given text and action.
     * @param name the text of the {@code JCheckBoxMenuItem}
     * @param l the action that the button performs
     * @return the {@code JCheckBoxMenuItem} that was created (or null if the preference wasn't found)
     */
    @SuppressWarnings("deprecation")
    public JCheckBoxMenuItem createJCheckBoxMenuItem(String name, ActionListener l) {
        if(panel.prefs.get(name) == null){
            System.err.println("Unrecognized Preference Name in File. This means your file might be corrupted!\nResetting the file to default preferences...");
            PrefsSaver.writePrefs(PrefsSaver.DEFAULT_PREFS);
            System.out.println("Preferences Reset. Restarting Program...");
            // starts a new thread with a new program and ends the current one
            new Thread(GUI::new).start();
            Thread.currentThread().stop();
            // I know that Thread.stop is very unsafe and can cause problems and such but I don't think that my program is so complex that I will have multiple threads accessing the same objects, so it's fine
            // Why not use Thread.interrupt? because then the code below would still run and throw a NullPointerException
        }
        JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem(name, panel.prefs.get(name));
        checkbox.addActionListener(l);
        return checkbox;
    }

    /**
     * Creates a {@code JCheckBoxMenuItem} with the given text.
     * @param name the text of the {@code JCheckBoxMenuItem} (and the name of the preference that it changes)
     * @return the {@code JCheckBoxMenuItem} that was created (or null if the preference wasn't found)
     */
    public JCheckBoxMenuItem createJCheckBoxMenuItem(String name) {
        return createJCheckBoxMenuItem(name, e -> {
            panel.prefs.put(name, !panel.prefs.get(name));
            PrefsSaver.writePrefs(panel.prefs);
        });
    }
}
