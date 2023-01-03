import java.time.LocalTime;

public class BinaryClock {
    private boolean[] seconds = new boolean[6];
    private boolean[] minutes = new boolean[6];
    private boolean[] hours = new boolean[6];
    private int second;
    private int minute;
    private int hour;

    public BinaryClock(){
        for(int i = 0; i < 6; i++){
            seconds[i] = false;
            minutes[i] = false;
            hours[i] = false;
        }
    }

    /**
     * Updates the {@code boolean[]} variables to use the values provided by the {@code int} variables.
     */
    private void update(){
        seconds = toBinaryArray(toBinary(second).toCharArray());
        minutes = toBinaryArray(toBinary(minute).toCharArray());
        hours = toBinaryArray(toBinary(hour).toCharArray());
    }

    /**
     * Converts a number into a String of 1's and 0's of length 6.
     * @param n the number to convert
     * @return a String representation of the number in binary
     */
    public static String toBinary(int n){
        StringBuilder s = new StringBuilder();
        while(n > 0){
            s.insert(0, n % 2);
            n /= 2;
        }
        while(s.length()<6)
            s.insert(0, "0");
        return s.toString();
    }

    /**
     * Gets the current time in New York.
     * @return the current time in New York.
     */
    private static LocalTime getTime(){
        return LocalTime.now(java.time.ZoneId.of("America/New_York"));
    }

    /**
     * Updates the clock to the current time based on the {@link BinaryClock#getTime()} method.
     */
    public void tick(){
        LocalTime time = getTime();
        second = time.getSecond();
        minute = time.getMinute();
        hour = time.getHour();
        update();
    }

    /**
     * Converts a {@code char[]} of 0's and 1's to a {@code boolean[]}.
     * @param n the {@code char[]} to convert
     * @return a {@code boolean[]} representation of the {@code char[]}
     */
    public static boolean[] toBinaryArray(char[] n){
        boolean[] b = new boolean[n.length];
        for(int i = 0; i < n.length; i++)
            b[i] = n[i] == '1';

        return b;
    }
    // getters
    public int getHour(){
        return hour;
    }
    public int getMinute(){
        return minute;
    }
    public int getSecond(){
        return second;
    }
    public boolean[] getSeconds(){
        return seconds;
    }
    public boolean[] getMinutes(){
        return minutes;
    }
    public boolean[] getHours(){
        return hours;
    }
}
