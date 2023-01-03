import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    // preferences for how the clock is displayed
    private boolean showDecimal = PrefsSaver.getShowDecimal();
    private boolean darkMode = PrefsSaver.getDarkMode();
    private boolean flipBinary = PrefsSaver.getFlipBinary();
    private boolean flipDecimal = PrefsSaver.getFlipDecimal();
    private boolean clock12hr = PrefsSaver.getClock12hr();
    private boolean pm = false;

    /**
     * the clock
     */
    private BinaryClock clock = new BinaryClock();
    public Panel(){
        setBackground(darkMode ? Color.black : Color.white);
    }

    public void paint(Graphics g){
        //setup
        super.paintComponent(g);
        g.setColor(darkMode ? Color.white : Color.black);
        g.setFont(new Font("SF Pro Display", Font.PLAIN, 20));

        if(SwingUtilities.isDescendingFrom(this, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow()))
            clock.tick(); // update the clock only if the window is in focus to save memory (i dont think this actually works)
        if(showDecimal) { // draw the decimal clock (if applicable)
            // decides how much to offset the clock
            int amount = (flipDecimal ? getWidth() - (clock12hr ? (makeClock().length() == 11 ? 135 : 125) : 105) : 10);
            // draw the clock in a fixed-width font to save me from endless migraines
            g.setFont(new Font("JetBrains Mono NL", Font.PLAIN, 20));
            g.drawString(makeClock(), amount, 20);
            // reset the font
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
        // Update the screen
        if(SwingUtilities.isDescendingFrom(this, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow()))
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

        if(clock12hr){
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
    // toggles and getters
    public void toggleDecimal(){
        showDecimal = !showDecimal;
    }
    public void toggleDarkMode(){
        darkMode = !darkMode;
        setBackground(darkMode ? Color.black : Color.white);
    }
    public void toggleBinaryFlip(){
        flipBinary = !flipBinary;
    }
    public void toggleDecimalFlip(){
        flipDecimal = !flipDecimal;
    }
    public void toggleClock12hr(){
        clock12hr = !clock12hr;
    }

    public boolean isDecimalShown(){
        return showDecimal;
    }
    public boolean isDarkMode(){
        return darkMode;
    }
    public boolean isBinaryFlipped(){
        return flipBinary;
    }
    public boolean isDecimalFlipped(){
        return flipDecimal;
    }
    public boolean isClock12hr(){
        return clock12hr;
    }
}
