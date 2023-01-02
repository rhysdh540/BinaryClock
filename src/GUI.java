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
        JMenuBar mb = new JMenuBar();

        JCheckBoxMenuItem toggleDecimalClock = new JCheckBoxMenuItem("Show Decimal Clock", panel.showDecimal);
        toggleDecimalClock.addActionListener(e -> panel.showDecimal = !panel.showDecimal);

        JCheckBoxMenuItem lightdark = new JCheckBoxMenuItem("Dark Mode", panel.darkMode);
        lightdark.addActionListener(e -> {
            panel.darkMode = !panel.darkMode;
            panel.setBackground(panel.darkMode ? Color.BLACK : Color.WHITE);
        });

        JCheckBoxMenuItem flipBinary = new JCheckBoxMenuItem("Flip Binary Clock", panel.flipBinary);
        flipBinary.addActionListener(e -> panel.flipBinary = !panel.flipBinary);

        JCheckBoxMenuItem flipDecimal = new JCheckBoxMenuItem("Flip Decimal Clock", panel.flipDecimal);
        flipDecimal.addActionListener(e -> panel.flipDecimal = !panel.flipDecimal);

        JCheckBoxMenuItem clock12hr = new JCheckBoxMenuItem("12 Hour Clock", panel.clock12hr);
        clock12hr.addActionListener(e -> panel.clock12hr = !panel.clock12hr);

        JMenu clockMenu = new JMenu("Clock");
        JMenu appearanceMenu = new JMenu("Appearance");

        appearanceMenu.add(toggleDecimalClock);
        appearanceMenu.add(lightdark);
        appearanceMenu.add(flipBinary);
        appearanceMenu.add(flipDecimal);
        clockMenu.add(clock12hr);

        mb.add(appearanceMenu);
        mb.add(clockMenu);
        gooey.setJMenuBar(mb);

        // stuff that must be done (or else)
        gooey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(854, 480); // dont ask why this is so specific
        gooey.setSize(size);
        gooey.setMinimumSize(size);
        Container pane = gooey.getContentPane();
        pane.add(panel);
        gooey.setVisible(true);
    }
}
