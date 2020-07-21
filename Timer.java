import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Timer {

    private int minutesLimit;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mmss");
    private int maxTime;

    public Timer(int minuteLimit){
        this.minutesLimit = minuteLimit*100;

    }

    private int currentTimeInt(){
        return Integer.parseInt(LocalTime.now().format(formatter));
    }

    public void start(){
        this.maxTime = currentTimeInt() + minutesLimit;
    }

    public boolean isRanOut(){
        if (getLeftTime() <= 0){
            return true;
        }else {
            return false;
        }
    }

    private int getLeftTime(){
        return maxTime - currentTimeInt();
    }

    public String getLeftTimeString(){
        String result = Integer.toString(getLeftTime());

        return "Time left: " + result;
    }

}
