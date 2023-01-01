import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    boolean showDecimal = false;
    boolean darkMode = false;
    private BinaryClock clock = new BinaryClock();
    public Panel(){
        setBackground(darkMode ? Color.black : Color.white);
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        g.setColor(darkMode ? Color.white : Color.black);
        g.setFont(new Font("SF Pro Display", Font.PLAIN, 20));

        clock.tick();
        if(showDecimal)
            g.drawString(clock.getHour() + ":" + clock.getMinute() + ":" + clock.getSecond(), 10, 20);

        for (int i = -1; i < 3; i++) { // draw the clock grid (as well as the labels)
            for (int j = -3; j < 3; j++) {
                boolean[] line = (i == -1 ? clock.getHours() : (i == 0 ? clock.getMinutes() : clock.getSeconds())); // decides which line to draw
                int y = getHeight() / 2 + (100 * i) - 50;
                if(i!=2) {
                    if (line[5 - (j + 3)]) g.fillOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    else g.drawOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    g.drawString((i == -1 ? "Hours" : (i == 0 ? "Minutes" : "Seconds")), getWidth() / 2 + 250, y+20); // draws the titles
                } else{
                    g.drawString((int)Math.pow(2.0, j+3) + "", getWidth() / 2 + (100 * j) + 7, y);
                }
            }
        }
        repaint();
    }
}

