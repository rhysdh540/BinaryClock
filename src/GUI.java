import javax.swing.*;
import java.awt.*;

public class GUI {
    Panel panel = new Panel();

    public GUI() {
        JFrame gooey = new JFrame("Binary Clock");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/icon.png");
        try { // set image
            Taskbar.getTaskbar().setIconImage(icon);
        } catch (UnsupportedOperationException e) { // if not on macos, try the windows method
            gooey.setIconImage(icon);
        } catch (SecurityException e) {
            System.err.println("There was a security exception for: 'taskbar.setIconImage'");
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
        gooey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(854, 480); // dont ask why this is so specific // why is it so specific
        gooey.setSize(size);
        gooey.setMinimumSize(size);
        gooey.getContentPane().add(panel);
        gooey.setVisible(true);
    }

    public void createJCheckBoxMenuItem(String name, JMenu menu) {
        if(panel.prefs.get(name) == null){
            System.err.println("Unrecognized Preference Name in File. Skipping menu box");
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
