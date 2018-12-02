package controllers;

import models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {


    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("OGM_MONGODB");
    public static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();

    private static CustomerController instance = null;

    private CustomerController(){}
    public static CustomerController getInstance(){
        if(instance == null)
        {
            instance = new CustomerController();
        }
        return instance;
    }

    public Customer createCustomer(String name, String address, List<Phone> phones)
    {
        Customer cust = new Customer(name, address);
        cust.setC_PHONES(phones);
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            for (Phone phone : phones) {
                em.persist(phone);
            }
            em.persist(cust);

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
        return cust;
    }
    public Customer getCustomer(Integer C_ID) {
        Customer retval = null;
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            retval = em.find(Customer.class, C_ID);
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
    public List<Integer> getAllCustomerIDs()
    {
        List<Integer> retval = new ArrayList<>();
        for(Customer c : getAllCustomersAsList())
        {
            retval.add(c.getC_ID());
        }
        return retval;
    }

    public List<Customer> getAllCustomersAsList()
    {
        List<Customer> customers = new ArrayList<>();
        try {
            EntityManager em = emf.createEntityManager();
            Query q = em.createQuery("SELECT c FROM Customer c");
            tm.setTransactionTimeout(3000);
            tm.begin();

            customers = q.getResultList();

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
        return customers;
    }

    public void updateCustomer(Customer customer) {
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            em.merge(customer);
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


    public void deleteCustomer(Integer CustomerID) throws NullPointerException
    {
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            Customer cust = em.find(Customer.class, CustomerID);
            if(cust == null)
            {
                throw new NullPointerException("Customer with ID " + CustomerID + " couldn't be found.");
            }else{
                em.remove(cust);
            }
            em.flush();
            em.close();
            tm.commit();
        }catch (NotSupportedException e) {
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

    public void deleteAllCustomers()
    {
        for(Integer id : getAllCustomerIDs()){
            deleteCustomer(id);
        }
    }


    private void bookRandomFlight(Integer customerID)
    {
        bookRandomFlight(getCustomer(customerID));
    }

    public void bookFlight(Flight flight, Customer customer)
    {
        customer.getC_FLIGHTS().add(flight);
        flight.getC_ID().add(customer);
        flight.setF_SEATS_E(flight.getF_SEATS_F());
        updateCustomer(customer);
        FlightController.getInstance().updateFlight(flight);
    }

    public void bookRandomFlight(Customer customer)
    {
        List<Flight> currentFlights = customer.getC_FLIGHTS();
        Flight sampleFlight = new Flight();
        List<FlightSegment> fs = new ArrayList<FlightSegment>();
        Airport start = new Airport("Mid London", "London");
        Airport end =new Airport("BER", "Berlin");
        FlightSegment seg = new FlightSegment(start.getA_ID(), end.getA_ID());
        fs.add(seg);
        sampleFlight.setF_FS(fs);
        currentFlights.add(sampleFlight);
        customer.setC_FLIGHTS(currentFlights);

        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();

            em.persist(start);
            em.persist(end);
            em.persist(seg);
            em.persist(sampleFlight);

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

        updateCustomer(customer);

    }

    public void createRandomCustomers(int num)
    {
        ArrayList<Customer> customers = new ArrayList<>();
        for(int i = 0; i<num; i++)
        {
            List<Phone> phones = new ArrayList<>();
            for(int j = 0; j<Math.random()*5; j++)
            {
                Phone p = new Phone();
                if(Math.random()*2 > 1)
                {
                    p.setP_TYPE(Phone.PhoneType.Mobile);
                }else{
                    p.setP_TYPE(Phone.PhoneType.Standard);
                }
                phones.add(p);
            }
            Customer c = new Customer(getRandomAString(8), getRandomAString(20));
            c.setC_PHONES(phones);
            customers.add(c);
        }
        PersistCustomers(customers);
    }

    private void PersistCustomers(List<Customer> customers)
    {
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            tm.begin();
            for(Customer cust : customers)
            {
                for (Phone phone : cust.getC_PHONES()) {
                    em.persist(phone);
                }
                em.persist(cust);
            }
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
    private String getRandomAString(int length) {
        String newstring = new String();
        int i;
        final char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@', '#',
                '$', '%', '^', '&', '*', '(', ')', '_', '-', '=', '+',
                '{', '}', '[', ']', '|', ':', ';', ',', '.', '?', '/',
                '~', ' '}; //79 characters
        for (i = 0; i < length; i++) {
            char c = chars[(int) Math.floor(Math.random() * 79)];
            newstring = newstring.concat(String.valueOf(c));
        }
        return newstring;
    }
}
