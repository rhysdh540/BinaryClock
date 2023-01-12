import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Panel extends JPanel{
    // preferences for how the clock is displayed
    HashMap<String,Boolean> prefs = PrefsSaver.readPrefs();
    // whether or not the time is after noon
    private boolean pm = false;
    private static final Font SFPRO = createFont("SFPro", 20);
    private static final Font MONO = createFont("JetBrainsMono", 20);
    private static final Font SFPROBIG = createFont("SFPro", 40);
    private static Color rave = new Color(0);
    /**
     * the clock
     */
    @SuppressWarnings("FieldMayBeFinal")
    private BinaryClock clock = new BinaryClock();

    public void paint(Graphics g){
        //setup
        if(prefs.get("RAVE MODE"))
            updateColor();
        setBackground(prefs.get("RAVE MODE") ? rave : (prefs.get("Dark Mode") ? Color.black : Color.white));
        g.setColor(prefs.get("Dark Mode") && !prefs.get("RAVE MODE") ? Color.white : Color.black);
        g.setFont(MONO);
        super.paintComponent(g);

        if(SwingUtilities.isDescendingFrom(this, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow()))
            clock.tick(); // update the clock only if the window is in focus to save memory (this barely does anything since the screen still constantly updates anyway)

        if(prefs.get("Show Decimal Clock")) { // draw the decimal clock (if applicable)
            // decides how much to offset the clock
            int amount = (prefs.get("Move Decimal Clock to Right Corner") ? getWidth() - (prefs.get("12 Hour Clock") ? (makeClock().length() == 11 ? 135 : 125) : 105) : 10);
            g.drawString(makeClock(), amount, 20);
        }

        // there's a lot of weird math in this part, but its just to make sure the clock is centered (and other parts are right/left-aligned)
        for (int i = -1; i < 3; i++) {
            for (int j = -3; j < 3; j++) {
                boolean[] line = new boolean[][]{clock.getHours(), clock.getMinutes(), clock.getSeconds(), null}[i + 1]; // decides which line to draw (i==-1: hours, i==0: minutes, i==1: seconds, i==2; not drawing so we don't care)
                int y = getHeight() / 2 + (100 * i) - 50, x = getWidth() / 2 + (100 * j);
                if(i == -1 && prefs.get("12 Hour Clock") && clock.getHour() >= 12){ // am/pm fix
                    line = BinaryClock.toBinaryArray(clock.getHour()-12);
                    pm = true;
                }
                if(line != null) { // draw the clock grid
                    g.setFont(SFPROBIG);
                    if (line[(prefs.get("Flip Binary Clock") ? 5 : 2*(j+3)) - (j + 3)]) { // if the bit is on, fill a circle/draw 1
                        if(prefs.get("Use 0's and 1's"))
                            g.drawString("1", x+5, y+25);
                        else
                            g.fillOval(x, y, 30, 30);
                    } else { // draw circle/draw 0
                        if(prefs.get("Use 0's and 1's"))
                            g.drawString("0", x+5, y+25);
                        else
                            g.drawOval(x, y, 30, 30);
                    }
                    g.setFont(SFPRO);
                    g.drawString((i == -1 ? "Hours" : (i == 0 ? "Minutes" : "Seconds")), getWidth() / 2 + 250, y+20); // draws the titles
                } else { // add the line labels
                    g.setFont(SFPRO);
                    g.drawString((int)Math.pow(2.0, (prefs.get("Flip Binary Clock") ? 2*(j+3) : 5) - (j + 3)) + "", x + 7, y);
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
    // guess what this method does (hint: it returns a String representation of the current time)
    /**
     * Returns a String representation of the current time.
     * @return a String representation of the current time.
     */
    public String makeClock(){
        StringBuilder sb = new StringBuilder();
        int h = clock.getHour(), m = clock.getMinute(), s = clock.getSecond();

        if(prefs.get("12 Hour Clock")){
            h = (pm && h != 12 ? h-12 : (h == 0 ? 12 : h)); // pm fix
            sb.append(h).append(":");
            if(m<10) sb.append("0");
            sb.append(m).append(":");
            if(s<10) sb.append("0");
            sb.append(s);
            sb.append(pm ? " PM" : " AM");
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

    /**
     * Changes the values of {@link #rave} to random values between 0 and 255.
     */
    private void updateColor(){
        java.util.function.Supplier<Integer> randColor = () -> (int)(Math.random()*255);
        rave = new Color(randColor.get(), randColor.get(), randColor.get());
    }
    private static Font createFont(String filename, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new java.io.File("src/resources/" + filename + ".ttf")).deriveFont(Font.PLAIN, size);
        } catch (Exception e) {
            System.err.println("Failed to initialize font!");
            throw new RuntimeException(e);
        }
    }
}
