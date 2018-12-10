import controllers.CustomerController;
import models.Customer;
import models.Performance;
import models.Phone;
import org.junit.*;
import tools.Config;
import tools.PerformanceMeasure;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerTest {

    CustomerController customerController;
    public static Performance performanceModell = new Performance();

    private boolean setupIsDone = false;

    @Before
    public void setUp() throws Exception {

        customerController = CustomerController.getInstance();
        customerController.deleteAllCustomers();
        PerformanceMeasure.StartMeasureMent();
    }


    @Test
    public void read_Test() {

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone());
        customerController.createCustomer("Harry", "Legusterweg 4", phones);
        Customer cTest = customerController.getAllCustomersAsList().get(0);
        assertEquals("Harry", cTest.getC_NAME());
        performanceModell.setM_READ(PerformanceMeasure.GetTimeStamp());
    }


    @Test
    public void testC_getAllCustomerTest() {

        customerController.createRandomCustomers(Config.NUMBER_OF_CUSTOMERS);
        ArrayList<Customer> cList = (ArrayList<Customer>) customerController.getAllCustomersAsList();
        assertEquals(Config.NUMBER_OF_CUSTOMERS, cList.size(), 0.0001);
        performanceModell.setM_GET_ALL(PerformanceMeasure.GetTimeStamp());


    }

    @Test
    public void update_Test() {
        Customer custTest = null;

        customerController.createRandomCustomers(2);
        List<Integer> cIDs = customerController.getAllCustomerIDs();
        List<Customer> customersToUpdate = new ArrayList<Customer>();
        for (Integer id : cIDs) {
            //find a customer to update
            //Customer cTest = custController.getCustomer(id);
            Customer cTest = new Customer();

            cTest.setC_ID(id);
            cTest.setC_MILES_ALL(99);
            cTest.setC_MILES_YEAR(30);
            cTest.setC_NAME("Thomas");
            cTest.setC_ADDR("Linguster Weg 4");

            customerController.updateCustomer(cTest);
        }

        //retrieve an updated customer from the datastore
        custTest = customerController.getCustomer(cIDs.get(0));

        assertEquals(99, custTest.getC_MILES_ALL(), 0.0001);
        performanceModell.setM_UPDATE(PerformanceMeasure.GetTimeStamp());


    }

    @Test
    public void delete_Test() {


        customerController.deleteAllCustomers();
        performanceModell.setM_DELETE(PerformanceMeasure.GetTimeStamp());


    }


    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {


        customerController.deleteCustomer(1);


        Assert.fail("Deletion shouldn't be possible");
    }


    @Test
    public void testH_bookFlightTest() {

        customerController.createRandomCustomers(1);
        ;
        Customer selectedCustomer = customerController.getAllCustomersAsList().get(0);
        customerController.bookRandomFlight(selectedCustomer);
        assertEquals(1, selectedCustomer.getC_FLIGHTS().size(), 0.0001);
        performanceModell.setM_BOOK(PerformanceMeasure.GetTimeStamp());
    }


    @Ignore
    @Test(expected = javax.transaction.NotSupportedException.class)
    public void TestNestedTransaction() throws javax.transaction.SystemException, javax.transaction.NotSupportedException {
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        tm.setTransactionTimeout(1000);
        tm.begin();
        tm.begin();
        Assert.fail("Nested transactions should fail!");
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Tear Down");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OGM_MONGODB");
        TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
        try {
            EntityManager em = emf.createEntityManager();
            tm.setTransactionTimeout(3000);
            em.merge(performanceModell);

            em.flush();
            em.close();
            tm.commit();
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


}