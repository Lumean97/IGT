package controllers;

import models.Customer;
import models.Flight;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.TransactionManager;
import java.util.ArrayList;
import java.util.List;

public class FlightController {

    public EntityManagerFactory emf = Persistence.createEntityManagerFactory("OGM_MONGODB");
    public TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    private static FlightController instance = null;

    public static FlightController getInstance()
    {
        if(instance == null){
            instance = new FlightController();
        }
        return instance;
    }
    private FlightController(){}

    public Flight findFlight(String start, String end)
    {
        FlightSegmentController.getInstance();
        return null;
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
    public List<Integer> getAllFlightIDs()
    {
        List<Integer> retval = new ArrayList<>();
        for(Flight f : getAllFlightsAsList())
        {
            retval.add(f.getF_ID());
        }
        return retval;
    }

    public List<Flight> getAllFlightsAsList()
    {
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

}
