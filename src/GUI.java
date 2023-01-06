import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.Thread;

public class GUI {
    @SuppressWarnings("FieldMayBeFinal")
    private Panel panel = new Panel();
    public GUI() {
        JFrame gooey = new JFrame("Binary Clock");
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
            JCheckBoxMenuItem item = createJCheckBoxMenuItem("Move Decimal Clock to Right Corner");
            appearanceMenu.add(createJCheckBoxMenuItem("Show Decimal Clock", e -> {
                panel.prefs.put("Show Decimal Clock", !panel.prefs.get("Show Decimal Clock"));
                PrefsSaver.writePrefs(panel.prefs);
                item.setEnabled(panel.prefs.get("Show Decimal Clock"));
            }));
            createJCheckBoxMenuItem("Dark Mode", appearanceMenu);
            createJCheckBoxMenuItem("Flip Binary Clock", appearanceMenu);
            appearanceMenu.add(item);
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
        JCheckBoxMenuItem item = createJCheckBoxMenuItem(name, e -> {
            panel.prefs.put(name, !panel.prefs.get(name));
            PrefsSaver.writePrefs(panel.prefs);
        });
        menu.add(item);
    }
    public JCheckBoxMenuItem createJCheckBoxMenuItem(String name, ActionListener l) {
        if(panel.prefs.get(name) == null){
            System.err.println("Unrecognized Preference Name in File. This means your file might be corrupted!\nResetting the file to default preferences...");
            PrefsSaver.writePrefs(PrefsSaver.defaultSettings());
            System.out.println("Preferences Reset. Resetting Program. This may throw an exception.");
            //starts a new thread with a new program and ends the current one
            new Thread(GUI::new).start();
            Thread.currentThread().interrupt();
            return null;
        }
        JCheckBoxMenuItem checkbox = new JCheckBoxMenuItem(name, panel.prefs.get(name));
        checkbox.addActionListener(l);
        return checkbox;
    }
    public JCheckBoxMenuItem createJCheckBoxMenuItem(String name) {
        return createJCheckBoxMenuItem(name, e -> {
            panel.prefs.put(name, !panel.prefs.get(name));
            PrefsSaver.writePrefs(panel.prefs);
        });
    }
}
