import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel{
    // preferences for how the clock is displayed
    private boolean showDecimal;
    private boolean darkMode;
    private boolean flipBinary;
    private boolean flipDecimal;
    private boolean clock12hr;
    private boolean binText;
    private boolean pm = false;
    private static final Font sfpro = new Font("SF Pro", Font.PLAIN, 20);
    private static final Font mono = new Font("JetBrains Mono NL", Font.PLAIN, 20);
    private static final Font sfprobig = new Font("SF Pro", Font.PLAIN, 40);

    /**
     * the clock
     */
    private BinaryClock clock = new BinaryClock();
    public Panel(){
        showDecimal = PrefsSaver.getShowDecimal();
        darkMode = PrefsSaver.getDarkMode();
        flipBinary = PrefsSaver.getFlipBinary();
        flipDecimal = PrefsSaver.getFlipDecimal();
        clock12hr = PrefsSaver.getClock12hr();
        binText = PrefsSaver.getBinText();
        setBackground(darkMode ? Color.black : Color.white);
    }

    public void paint(Graphics g){
        //setup
        super.paintComponent(g);
        g.setColor(darkMode ? Color.white : Color.black);
        g.setFont(mono);

        if(SwingUtilities.isDescendingFrom(this, KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow()))
            clock.tick(); // update the clock only if the window is in focus to save memory (i dont think this actually works)
        if(showDecimal) { // draw the decimal clock (if applicable)
            // decides how much to offset the clock
            int amount = (flipDecimal ? getWidth() - (clock12hr ? (makeClock().length() == 11 ? 135 : 125) : 105) : 10);
            g.drawString(makeClock(), amount, 20);
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
                    g.setFont(sfprobig);
                    if (line[(flipBinary ? 5 : 2*(j+3)) - (j + 3)]) {
                        if(binText)
                            g.drawString("1", getWidth() / 2 + (100 * j)+5, y+25);
                        else
                            g.fillOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    } else {
                        if(binText)
                            g.drawString("0", getWidth() / 2 + (100 * j)+5, y+25);
                        else
                            g.drawOval(getWidth() / 2 + (100 * j), y, 30, 30);
                    }
                    g.setFont(sfpro);
                    g.drawString((i == -1 ? "Hours" : (i == 0 ? "Minutes" : "Seconds")), getWidth() / 2 + 250, y+20); // draws the titles
                } else{ // add the line labels
                    g.setFont(sfpro);
                    g.drawString((int)Math.pow(2.0, (flipBinary ? 2*(j+3) : 5) - (j + 3)) + "", getWidth() / 2 + (100 * j) + 7, y);
                }
            }
        }
        if(clock12hr) { // add the AM/PM indicator
            g.setFont(sfprobig);
            if(pm) {
                if(binText)
                    g.drawString("1", getWidth() - 75, getHeight() - 15);
                else
                    g.fillOval(getWidth() - 75, getHeight() - 43, 30, 30);
            } else {
                if(binText)
                    g.drawString("0", getWidth() - 75, getHeight() - 15);
                else
                    g.drawOval(getWidth() - 75, getHeight() - 43, 30, 30);
            }
            g.setFont(sfpro);
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

    // toggles
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
    public void toggleBinText(){
        binText = !binText;
    }

    // getters
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
    public boolean getBinText(){
        return binText;
    }
}
