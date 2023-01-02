import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    boolean showDecimal = false;
    boolean darkMode = true;
    boolean flipBinary = false;
    boolean flipDecimal = false;
    boolean clock12hr = false;
    private boolean pm = false;
    private BinaryClock clock = new BinaryClock();
    public Panel(){
        setBackground(darkMode ? Color.black : Color.white);
    }

    public void paint(Graphics g){
        //setup
        super.paintComponent(g);
        g.setColor(darkMode ? Color.white : Color.black);
        g.setFont(new Font("SF Pro Display", Font.PLAIN, 20));

        clock.tick(); // to remove the delay on the first tick
        if(showDecimal) { // draw the decimal clock
            g.setFont(new Font("JetBrains Mono NL", Font.PLAIN, 20));
            g.drawString(makeClock(), (flipDecimal ? getWidth() - (clock12hr ? (makeClock().length() == 11 ? 135 : 125) : 105) : 10) /* that was just to decide how much offset to put the clock on if it is right-aligned */, 20);
            g.setFont(new Font("SF Pro Display", Font.PLAIN, 20));
        }

        for (int i = -1; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                boolean[] line = (i == -1 ? clock.getHours() : (i == 0 ? clock.getMinutes() : clock.getSeconds())); // decides which line to draw
                int y = getHeight() / 2 + (100 * i) - 50;
                if(i == -1 && clock12hr && clock.getHour() > 12){ // am/pm fix
                    line = BinaryClock.toBinaryArray(BinaryClock.toBinary(clock.getHour()-12).toCharArray());
                    pm = true;
                }
                if(i!=2) { // draw the clock grid
                    if (line[(flipBinary ? 5 : 2*(j+3)) - (j + 3)]) g.fillOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    else g.drawOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    g.drawString((i == -1 ? "Hours" : (i == 0 ? "Minutes" : "Seconds")), getWidth() / 2 + 250, y+20); // draws the titles
                } else{ // add the line labels
                    g.drawString((int)Math.pow(2.0, (flipBinary ? 2*(j+3) : 5) - (j + 3)) + "", getWidth() / 2 + (100 * j) + 7, y);
                }
            }
        }
        if(clock12hr) { // add the AM/PM indicator
            if(pm) g.fillOval(getWidth() - 75, getHeight() - 40, 30, 30);
            else g.drawOval(getWidth() - 75, getHeight() - 40, 30, 30);
            g.drawString("PM", getWidth() - 40, getHeight() - 20);
        }
        repaint();
    }
    public String makeClock(){
        boolean pm = false;
        StringBuilder sb = new StringBuilder();
        int h = clock.getHour();
        int m = clock.getMinute();
        int s = clock.getSecond();

        if(clock12hr){
            if(h>12){
                h-=12;
                pm = true;
            }
            sb.append(h).append(":");
            if(m<10) sb.append("0");
            sb.append(m).append(":");
            if(s<10) sb.append("0");
            sb.append(s);
            if(pm) sb.append(" PM");
            else sb.append(" AM");
        } else {
            if(h<10) sb.append("0");
            sb.append(h).append(":");
            if(m<10) sb.append("0");
            sb.append(m).append(":");
            if(s<10) sb.append("0");
            sb.append(s);
        }
        return sb.toString();
    }
}