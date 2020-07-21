import java.time.Duration;
import java.time.Instant;


public class Timer {

    private int minutesLimit;
    private Instant startTime;

    /**
     * Constructs Timer
     * @param minuteLimit amount of minutes to count down
     */
    public Timer(int minuteLimit){
        this.minutesLimit = minuteLimit - 1;

    }

    /**
     * Start the timer count
     */
    public void start(){
        this.startTime = Instant.now();
    }

    /**
     * return past minutes
     * @return int of past minutes
     */
    private int getPastMinutes(){
        Duration elapsed = Duration.between(startTime, Instant.now());
        return elapsed.toMinutesPart();
    }

    /**
     * return past seconds
     * @return int of past seconds
     */
    private int getPastSeconds(){
        Duration elapsed = Duration.between(startTime, Instant.now());
        return elapsed.toSecondsPart();
    }

    /**
     * Check if count is passed
     * @return true or false
     */
    public boolean check(){
        if (getPastMinutes() > minutesLimit){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Returns a String of left over time
     * @return String of time left
     */
    public String getTimeString(){
        String minutes = Integer.toString(minutesLimit-getPastMinutes());
        String seconds = Integer.toString(60 - getPastSeconds());

        if (minutes.length() == 1){
            minutes = "0"+minutes;
        }

        if (seconds.length() == 1){
            seconds = "0"+seconds;
        }

        return minutes + ":" + seconds;
    }

}
