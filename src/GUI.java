import javax.swing.*;
import java.awt.*;

public class GUI {
    public GUI(){
        JFrame gooey = new JFrame("Binary Clock");
        Image icon = Toolkit.getDefaultToolkit().getImage("src/icon.png");
        try { // set image
            Taskbar.getTaskbar().setIconImage(icon);
        } catch (UnsupportedOperationException e) { // if not on macos, try the windows method
            gooey.setIconImage(icon);
        } catch (SecurityException e) {
            System.err.println("There was a security exception for: 'taskbar.setIconImage'");
        }
        Panel panel = new Panel();

        // this is all for adding the menu bar toggles
        {
            JMenuBar mb = new JMenuBar(); // create the menu bar

            JCheckBoxMenuItem toggleDecimalClock = new JCheckBoxMenuItem("Show Decimal Clock", panel.isDecimalShown());
            toggleDecimalClock.addActionListener(e -> {
                panel.toggleDecimal();
                PrefsSaver.writePrefs(panel);
            });

            JCheckBoxMenuItem lightdark = new JCheckBoxMenuItem("Dark Mode", panel.isDarkMode());
            lightdark.addActionListener(e -> {
                panel.toggleDarkMode();
                PrefsSaver.writePrefs(panel);
            });

            JCheckBoxMenuItem flipBinary = new JCheckBoxMenuItem("Flip Binary Clock", panel.isBinaryFlipped());
            flipBinary.addActionListener(e -> {
                panel.toggleBinaryFlip();
                PrefsSaver.writePrefs(panel);
            });

            JCheckBoxMenuItem flipDecimal = new JCheckBoxMenuItem("Flip Decimal Clock", panel.isDecimalFlipped());
            flipDecimal.addActionListener(e -> {
                panel.toggleDecimalFlip();
                PrefsSaver.writePrefs(panel);
            });

            JCheckBoxMenuItem clock12hr = new JCheckBoxMenuItem("12 Hour Clock", panel.isClock12hr());
            clock12hr.addActionListener(e -> {
                panel.toggleClock12hr();
                PrefsSaver.writePrefs(panel);
            });

            // create the menus
            JMenu clockMenu = new JMenu("Clock");
            JMenu appearanceMenu = new JMenu("Appearance");

            // add the buttons to the menus
            appearanceMenu.add(toggleDecimalClock);
            appearanceMenu.add(lightdark);
            appearanceMenu.add(flipBinary);
            appearanceMenu.add(flipDecimal);
            clockMenu.add(clock12hr);

            // add the menus to the main bar
            mb.add(appearanceMenu);
            mb.add(clockMenu);
            gooey.setJMenuBar(mb);
        }

        // stuff that must be done (or else)
        gooey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(854, 480); // dont ask why this is so specific
        gooey.setSize(size);
        gooey.setMinimumSize(size);
        gooey.getContentPane().add(panel);
        gooey.setVisible(true);
    }
}
