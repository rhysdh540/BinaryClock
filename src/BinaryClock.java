import java.time.LocalTime;

/**
 * A fairly basic binary clock class, contains the time as ints, the time in binary, and methods to update them.
 */
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
        seconds = toBinaryArray(toBinaryString(second));
        minutes = toBinaryArray(toBinaryString(minute));
        hours = toBinaryArray(toBinaryString(hour));
    }
    /**
     * Updates the clock to the current time based on the current time in New York.
     */
    public void tick(){
        LocalTime time = LocalTime.now(java.time.ZoneId.of("America/New_York"));
        second = time.getSecond();
        minute = time.getMinute();
        hour = time.getHour();
        update();
    }

    /**
     * Converts a {@code String} of 0's and 1's to a {@code boolean[]}.
     * @param str the {@code String} to convert
     * @return a {@code boolean[]} representation of the {@code String}
     */
    public static boolean[] toBinaryArray(String str){
        StringBuilder sb = new StringBuilder(str);
        while(sb.length() < 6) {
            sb.insert(0, "0");
        }
        str = sb.toString();
        boolean[] arr = new boolean[6];
        for(int i = 0; i < 6; i++)
            arr[i] = str.charAt(i) == '1';
        return arr;
    }

    /**
     * Returns a string representation of the integer argument in base&nbsp;2
     * @param i an integer to be converted to a string.
     * @return the string representation of the unsigned integer value represented by the argument in binary (base&nbsp;2).
     */
    public static String toBinaryString(int i){
        if(i == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while(i != 0){
            sb.insert(0, i % 2);
            i /= 2;
        }
        return sb.toString();
    }
    // getters (no setters because update method already does that)
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
