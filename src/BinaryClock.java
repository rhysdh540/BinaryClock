import java.time.LocalTime;

import lombok.Getter;

public class BinaryClock {
    @Getter
    private boolean[] seconds = new boolean[6];
    @Getter
    private boolean[] minutes = new boolean[6];
    @Getter
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
        for(int i = 0; i < 6; i++){
            seconds[i] = toBinary(second).toCharArray()[i] == '1';
            minutes[i] = toBinary(minute).toCharArray()[i] == '1';
            hours[i] = toBinary(hour).toCharArray()[i] == '1';
        }
    }
    private static String toBinary(int n){
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
    public String getHour(){
        return Integer.toString(hour).length()==1 ? "0" + hour : Integer.toString(hour);
    }
    public String getMinute(){
        return Integer.toString(minute).length()==1 ? "0" + minute : Integer.toString(minute);
    }
    public String getSecond(){
        return Integer.toString(second).length()==1 ? "0" + second : Integer.toString(second);
    }

}
