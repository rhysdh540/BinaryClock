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
        seconds = toBinaryArray(second);
        minutes = toBinaryArray(minute);
        hours = toBinaryArray(hour);
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
     * Converts an {@code int} of in base 10 to a {@code boolean[]} representation of it in base 2 (binary).<br><br>
     * {@code String.format("%6s", Integer.toBinaryString(i)).replace(' ', '0')} converts the {@code int} to a {@code String} in base 2 and pad the front with '0's.
     * <br><br>
     * @param i the {@code int} to convert
     * @return a {@code boolean[]} representation of the {@code int}
     */
    public static boolean[] toBinaryArray(int i){
        boolean[] arr = new boolean[6];
        for(int x = 0; x < 6; x++)
            arr[x] = String.format("%6s", Integer.toBinaryString(i)).replace(' ', '0').charAt(x) == '1';
        return arr;
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
