import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Panel extends JPanel{
    // preferences for how the clock is displayed
    HashMap<String,Boolean> prefs;
    private boolean pm = false;
    private static final Font SFPRO = new Font("SF Pro", Font.PLAIN, 20);
    private static final Font MONO = new Font("JetBrains Mono NL", Font.PLAIN, 20);
    private static final Font SFPROBIG = new Font("SF Pro", Font.PLAIN, 40);


    /**
     * the clock
     */
    @SuppressWarnings("FieldMayBeFinal") // shut up
    private BinaryClock clock = new BinaryClock();
    public Panel(){
        prefs = PrefsSaver.readPrefs();
    }

    public void paint(Graphics g){
        //setup
        super.paintComponent(g);
        setBackground(prefs.get("Dark Mode") ? Color.black : Color.white);
        g.setColor(prefs.get("Dark Mode") ? Color.white : Color.black);
        g.setFont(MONO);

        if(SwingUtilities.isDescendingFrom(this, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow()))
            clock.tick(); // update the clock only if the window is in focus to save memory (i dont think this actually works)
        if(prefs.get("Show Decimal Clock")) { // draw the decimal clock (if applicable)
            // decides how much to offset the clock
            int amount = (prefs.get("Flip Decimal Clock") ? getWidth() - (prefs.get("12 Hour Clock") ? (makeClock().length() == 11 ? 135 : 125) : 105) : 10);
            g.drawString(makeClock(), amount, 20);
        }

        // there's a lot of weird math in this part, but its just to make sure the clock is centered (and other parts are right/left-aligned)
        for (int i = -1; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                boolean[] line = (i == -1 ? clock.getHours() : (i == 0 ? clock.getMinutes() : clock.getSeconds())); // decides which line to draw
                int y = getHeight() / 2 + (100 * i) - 50;
                if(i == -1 && prefs.get("12 Hour Clock") && clock.getHour() > 12){ // am/pm fix
                    line = BinaryClock.toBinaryArray(Integer.toBinaryString(clock.getHour()-12));
                    pm = true;
                }
                if(i!=2) { // draw the clock grid
                    g.setFont(SFPROBIG);
                    if (line[(prefs.get("Flip Binary Clock") ? 5 : 2*(j+3)) - (j + 3)]) {
                        if(prefs.get("Use 0's and 1's"))
                            g.drawString("1", getWidth() / 2 + (100 * j)+5, y+25);
                        else
                            g.fillOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    } else {
                        if(prefs.get("Use 0's and 1's"))
                            g.drawString("0", getWidth() / 2 + (100 * j)+5, y+25);
                        else
                            g.drawOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    }
                    g.setFont(SFPRO);
                    g.drawString((i == -1 ? "Hours" : (i == 0 ? "Minutes" : "Seconds")), getWidth() / 2 + 250, y+20); // draws the titles
                } else{ // add the line labels
                    g.setFont(SFPRO);
                    g.drawString((int)Math.pow(2.0, (prefs.get("Flip Binary Clock") ? 2*(j+3) : 5) - (j + 3)) + "", getWidth() / 2 + (100 * j) + 7, y);
                }
            }
        }
        if(prefs.get("12 Hour Clock")) { // add the AM/PM indicator
            g.setFont(SFPROBIG);
            if(pm) {
                if(prefs.get("Use 0's and 1's"))
                    g.drawString("1", getWidth() - 75, getHeight() - 15);
                else
                    g.fillOval(getWidth() - 75, getHeight() - 43, 30, 30);
            } else {
                if(prefs.get("Use 0's and 1's"))
                    g.drawString("0", getWidth() - 75, getHeight() - 15);
                else
                    g.drawOval(getWidth() - 75, getHeight() - 43, 30, 30);
            }
            g.setFont(SFPRO);
            g.drawString("PM", getWidth() - 40, getHeight() - 20);
        }
        // Update the screen
        repaint();
    }

    /**
     * Returns a String representation of the current time.
     * @return a String representation of the current time.
     */
    public String makeClock(){
        StringBuilder sb = new StringBuilder();
        int h = clock.getHour();
        int m = clock.getMinute();
        int s = clock.getSecond();

        if(prefs.get("12 Hour Clock")){
            if(pm) h -= 12;
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
