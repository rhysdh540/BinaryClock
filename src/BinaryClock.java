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
        seconds = toBinaryArray(Integer.toBinaryString(second));
        minutes = toBinaryArray(Integer.toBinaryString(minute));
        hours = toBinaryArray(Integer.toBinaryString(hour));
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
