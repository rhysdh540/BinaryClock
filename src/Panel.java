import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    BinaryClock clock = new BinaryClock();
    public Panel(){
        setBackground(Color.white);

    }

    public void paint(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.black);
        for(int i = -1; i < 2; i++){
            for(int j = -3; j < 3; j++) {

                g.drawOval(100+(100*j), getHeight()/2+(100*i), 30, 30);
            }
        }
    }

}

