import javax.swing.*;
import java.awt.*;
public class GUI {
    public GUI(){
        JFrame gooey = new JFrame();
        gooey.setTitle("Binary Clock");
        gooey.setSize(854, 480);

        Panel panel = new Panel();
        Container pane = gooey.getContentPane();
        pane.add(panel);
        gooey.setVisible(true);
    }
}
