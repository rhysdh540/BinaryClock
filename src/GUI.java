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


        JMenuItem toggleDecimalClock = new JMenuItem("Show/Hide Decimal Clock");
        toggleDecimalClock.addActionListener(e -> panel.showDecimal = !panel.showDecimal);

        JMenuItem lightdark = new JMenuItem("Light/Dark Mode");
        lightdark.addActionListener(e -> {
            panel.darkMode = !panel.darkMode;
            panel.setBackground(panel.darkMode ? Color.black : Color.white);
        });

        JMenuItem flipBinary = new JMenuItem("Flip Binary Clock");
        flipBinary.addActionListener(e -> panel.flipBinary = !panel.flipBinary);

        JMenuItem flipDecimal = new JMenuItem("Flip Decimal Clock");
        flipDecimal.addActionListener(e -> panel.flipDecimal = !panel.flipDecimal);

        JMenuItem clock12hr = new JMenuItem("12/24 Hour Clock");
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
