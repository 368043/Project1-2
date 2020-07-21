import java.time.Duration;
import java.time.Instant;


public class Timer {

    private int minutesLimit;
    private Instant startTime;

    public Timer(int minuteLimit){
        this.minutesLimit = minuteLimit - 1;

    }

    public void start(){
        this.startTime = Instant.now();
    }

    private int getPastMinutes(){
        Duration elapsed = Duration.between(startTime, Instant.now());
        return elapsed.toMinutesPart();
    }

    private int getPastSeconds(){
        Duration elapsed = Duration.between(startTime, Instant.now());
        return elapsed.toSecondsPart();
    }

    public boolean check(){
        if (getPastMinutes() >= minutesLimit){
            return true;
        }else {
            return false;
        }
    }

    public String getLeftTime(){
        String result = "Time left: " + (minutesLimit - getPastMinutes()) + ":" + (60 - getPastSeconds());
        return result;
    }

}
