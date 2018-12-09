package controllers;

import models.Airport;
import tools.Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;

public class AirportController {

    private static AirportController instance;
    public EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);
    public TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    private AirportController(){}
    public static AirportController getInstance(){
        if(instance == null)
        {
            instance = new AirportController();
        }
        return instance;
    }

    public Airport getAirportByID(Integer AirportID){
        Airport retval = null;
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            retval = em.find(Airport.class, AirportID);
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
        return retval;
    }
}
