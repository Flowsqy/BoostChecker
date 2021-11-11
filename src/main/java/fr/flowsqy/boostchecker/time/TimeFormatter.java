package fr.flowsqy.boostchecker.time;

public class TimeFormatter {

    private final String none;
    private final String one;
    private final String multiple;

    public TimeFormatter(String none, String one, String multiple) {
        this.none = none;
        this.one = one;
        this.multiple = multiple;
    }

    public String format(long time){
        if(time > 1L){
            return multiple;
        }
        if(time == 1){
            return one;
        }
        return none;
    }

}
