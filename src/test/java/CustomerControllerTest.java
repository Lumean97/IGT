import controllers.CustomerController;
import models.Customer;
import models.Phone;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import tools.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerTest {

    CustomerController customerController;

    private boolean setupIsDone = false;

    @Before
    public void setUp() throws Exception {

        if (!setupIsDone) {
            customerController = CustomerController.getInstance();
        }
        customerController.deleteAllCustomers();
    }



    @Ignore
    @Test
    public void read_Test() {

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone());
        customerController.createCustomer("Harry", "Legusterweg 4", phones);
        System.out.println(customerController.getAllCustomerIDs());
        Customer cTest = customerController.getAllCustomersAsList().get(0);
        System.out.println(cTest.getC_NAME());
        assertEquals("Harry", cTest.getC_NAME());


    }


    @Ignore
    @Test
    public void testC_getAllCustomerTest() {

        customerController.createRandomCustomers(Config.NUMBER_OF_CUSTOMERS);
        ;
        ArrayList<Customer> cList = (ArrayList<Customer>) customerController.getAllCustomersAsList();
        assertEquals(Config.NUMBER_OF_CUSTOMERS, cList.size(), 0.0001);


    }

    @Ignore
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


    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {


        customerController.deleteCustomer(1);

        //fail("NullPointerException");

    }


    @Ignore
    @Test
    public void testH_bookFlightTest() {

        customerController.createRandomCustomers(1);
        ;
        Customer selectedCustomer = customerController.getAllCustomersAsList().get(0);
        customerController.bookRandomFlight(selectedCustomer);
        assertEquals(1, selectedCustomer.getC_FLIGHTS().size(), 0.0001);
    }

    @Ignore
    @Test
    public void delete_Test() {


        customerController.deleteAllCustomers();


    }


}