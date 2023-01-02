import javax.swing.*;
import javax.swing.event.*;
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

        // this is all for adding the menu bar toggle for the decimal clock
        JMenuBar mb = new JMenuBar();

        JMenuItem toggleDecimalClock = new JMenuItem("Show/Hide Decimal Clock");
        toggleDecimalClock.addActionListener(e -> panel.showDecimal = !panel.showDecimal);

        JMenu appearance = new JMenu("Light/Dark Mode");
        appearance.addMenuListener(new MenuListener() {
            @Override public void menuSelected(MenuEvent e) {
                panel.darkMode = !panel.darkMode;
                panel.setBackground(panel.darkMode ? Color.black : Color.white);
            }
            @Override public void menuDeselected(MenuEvent e) {}
            @Override public void menuCanceled(MenuEvent e) {}
        });

        JMenuItem flipBinary = new JMenuItem("Flip Binary Clock");
        flipBinary.addActionListener(e -> panel.flipBinary = !panel.flipBinary);

        JMenuItem flipDecimal = new JMenuItem("Flip Decimal Clock");
        flipDecimal.addActionListener(e -> panel.flipDecimal = !panel.flipDecimal);

        JMenu clockMenu = new JMenu("Clock");
        clockMenu.add(toggleDecimalClock);
        clockMenu.add(flipBinary);
        clockMenu.add(flipDecimal);

        mb.add(clockMenu);
        mb.add(appearance);
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
