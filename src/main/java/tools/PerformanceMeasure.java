package tools;

public class PerformanceMeasure {

    private static long startTime;

    public static void StartMeasureMent(){
        startTime = System.currentTimeMillis();
    }

    public static long GetTimeStamp(){
        return System.currentTimeMillis() - startTime;
    }
}
