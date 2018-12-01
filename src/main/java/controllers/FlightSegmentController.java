package controllers;

public class FlightSegmentController {

    private static FlightSegmentController instance;

    public static FlightSegmentController getInstance()
    {
        if(instance == null)
        {
            instance = new FlightSegmentController();
        }
        return instance;
    }

    private FlightSegmentController(){}


}
