import javax.swing.*;
import java.awt.*;
import java.lang.Thread;

public class GUI {
    @SuppressWarnings("FieldMayBeFinal")
    private Panel panel = new Panel();
    @SuppressWarnings("FieldMayBeFinal")
    private JFrame gooey = new JFrame("Binary Clock");

    public GUI() {
        Image icon = Toolkit.getDefaultToolkit().getImage("src/icon.png");
        try { // set image
            Taskbar.getTaskbar().setIconImage(icon);
        } catch (UnsupportedOperationException e) { // if not on macos, try the windows method
            gooey.setIconImage(icon);
        } catch (SecurityException e) {
            System.err.println("There was a security exception for: 'Taskbar.setIconImage'");
        }
        // create the menu bar toggles
        {
            JMenuBar mb = new JMenuBar(); // create the menu bar
            // create the menus
            JMenu clockMenu = new JMenu("Clock");
            JMenu appearanceMenu = new JMenu("Appearance");

            // create the buttons
            createJCheckBoxMenuItem("Show Decimal Clock", appearanceMenu);
            createJCheckBoxMenuItem("Dark Mode", appearanceMenu);
            createJCheckBoxMenuItem("Flip Binary Clock", appearanceMenu);
            createJCheckBoxMenuItem("Move Decimal Clock to Right Corner", appearanceMenu);
            createJCheckBoxMenuItem("12 Hour Clock", clockMenu);
            createJCheckBoxMenuItem("Use 0's and 1's", appearanceMenu);
            // add the menus to the main bar
            mb.add(appearanceMenu);
            mb.add(clockMenu);
            gooey.setJMenuBar(mb);
        }

        // stuff that must be done (or else)
        gooey.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension size = new Dimension(854, 480);
        gooey.setSize(size);
        gooey.setMinimumSize(size);
        gooey.getContentPane().add(panel);
        gooey.setVisible(true);
    }

    public void createJCheckBoxMenuItem(String name, JMenu menu) {
        if(panel.prefs.get(name) == null){
            System.err.println("Unrecognized Preference Name in File. This means your file might be corrupted!\nResetting the file to default preferences...");
            PrefsSaver.writePrefs(PrefsSaver.defaultSettings());
            System.out.println("Preferences Reset. Resetting Program. This may throw an exception.");
            new Thread(GUI::new).start();
            Thread.currentThread().interrupt();
            return;
        }
        JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem(name, panel.prefs.get(name));
        checkbox.addActionListener(e -> {
                    panel.prefs.put(name, !panel.prefs.get(name));
                    PrefsSaver.writePrefs(panel.prefs);
                }
        );
        menu.add(checkbox);
    }
}
