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
    private void update(){
        seconds = toBinaryArray(toBinary(second).toCharArray());
        minutes = toBinaryArray(toBinary(minute).toCharArray());
        hours = toBinaryArray(toBinary(hour).toCharArray());
    }
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
    private static LocalTime getTime(){
        return LocalTime.now(java.time.ZoneId.of("America/New_York"));
    }
    public void tick(){
        LocalTime time = getTime();
        second = time.getSecond();
        minute = time.getMinute();
        hour = time.getHour();
        update();
    }
    public static boolean[] toBinaryArray(char[] n){
        boolean[] b = new boolean[n.length];
        for(int i = 0; i < n.length; i++)
            b[i] = n[i] == '1';

        return b;
    }
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
