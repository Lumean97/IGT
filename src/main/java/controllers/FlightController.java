package controllers;

import models.Airport;
import models.Customer;
import models.Flight;
import models.FlightSegment;
import tools.Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

    public EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);
    public TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    private static FlightController instance = null;

    public static FlightController getInstance() {
        if (instance == null) {
            instance = new FlightController();
        }
        return instance;
    }

    private FlightController() {
    }

    public Flight findFlight(String start, String end) {
        FlightSegmentController.getInstance();
        AirportController controller = AirportController.getInstance();
        List<Flight> flights = getAllFlightsAsList();
        for (Flight flight : flights) {
            List<FlightSegment> fs = flight.getF_FS();
            if (controller.getAirportByID(fs.get(0).getFS_START_ID()).getA_PLACE().equals(start)
                    && controller.getAirportByID(fs.get(fs.size() - 1).getFS_END_ID()).getA_PLACE().equals(end)
            ) {
                return flight;
            }
        }
        return null;
    }

    public List<Flight> findFlights(String start, String end) {
        AirportController controller = AirportController.getInstance();
        List<Flight> flights = getAllFlightsAsList();
        List<Flight> retval = new ArrayList<>();
        for (Flight flight : flights) {
            List<FlightSegment> fs = flight.getF_FS();
            if (getStartAirport(flight).getA_PLACE().equals(start) &&
                    getEndAirport(flight).getA_PLACE().equals(end)
            ) {
                retval.add(flight);
            }
        }
        return retval;
    }

    public List<Flight> findAvialableFlights(String start, String end)
    {
        List<Flight> allFlights = findFlights(start, end);
        for(Flight flight : allFlights)
        {
            if(flight.getF_SEATS_E() == 0 && flight.getF_SEATS_F() == 0)
            {
                allFlights.remove(flight);
            }
        }
        return allFlights;
    }

    public void updateFlight(Flight flight) {
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            em.merge(flight);
            em.flush();
            em.close();
            tm.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }
    
    public Airport getStartAirport(Flight flight) {
        return AirportController.getInstance().getAirportByID(flight.getF_FS().get(0).getFS_START_ID());
    }

    public Airport getEndAirport(Flight flight) {
        return AirportController.getInstance().getAirportByID(flight.getF_FS().get(flight.getF_FS().size() - 1).getFS_END_ID());
    }

    public Flight getFlight(Integer F_ID) {
        Flight retval = null;
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            retval = em.find(Flight.class, F_ID);
            em.flush();
            em.close();
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retval;
    }

    public List<Integer> getAllFlightIDs() {
        List<Integer> retval = new ArrayList<>();
        for (Flight f : getAllFlightsAsList()) {
            retval.add(f.getF_ID());
        }
        return retval;
    }

    public List<Flight> getAllFlightsAsList() {
        List<Flight> flights = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createQuery("SELECT f FROM Flight f");
            tm.setTransactionTimeout(3000);
            tm.begin();

            flights = q.getResultList();

            em.flush();
            em.close();
            tm.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flights;
    }

    public String getFlightData(Flight flight){
        System.out.println("To String called");
        String retval = "Flug Nummer " + flight.getF_ID() + " von " + FlightController.getInstance().getStartAirport(flight) + " nach " + FlightController.getInstance().getEndAirport(flight) + " mit dem Flieger " + flight.getF_PLANE_TYPE() + ".\n";
        retval += "Der Flug besitzt " + flight.getF_SEATS_E() + " Sitze in der Economy Class, und " + flight.getF_SEATS_F() + " Sitze in der ersten Klasse.\n";
        retval += "In der Economy Class kostet ein Sitz " + flight.getF_PRICE_E() + "€. In der ersten Klasse " + flight.getF_PRICE_F() + ".\n";
        retval += "Der Flug wird voraussichtlich " + flight.getF_START_TIME() + " starten, und " + flight.getF_LANDING_TIME() + " landen.";
        return retval;
    }

}
