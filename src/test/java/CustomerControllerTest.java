import controllers.CustomerController;
import models.Airport;
import models.Customer;
import models.Phone;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerControllerTest {


    @Before
    public void setUp() throws Exception {


        CustomerController.deleteAllCustomers();

        

    }

    @After
    public void tearDown() throws Exception {




    }

  
    @Test
    public void read_Test() {

        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone());
        CustomerController.createCustomer("Harry", "Legusterweg 4", phones);
        System.out.println(CustomerController.getAllCustomerIDs());
        Customer cTest = CustomerController.getAllCustomersAsList().get(0);
        System.out.println(cTest.getC_NAME());
        assertEquals("Harry", cTest.getC_NAME());


    }

   
    @Test
    public void testC_getAllCustomerTest() {

        CustomerController.createRandomCustomers(100);;
        ArrayList<Customer> cList = (ArrayList<Customer>) CustomerController.getAllCustomersAsList();
        assertEquals(100, cList.size(), 0.0001);


    }

    @Test
    public void update_Test() {
        Customer custTest = null;

        CustomerController.createRandomCustomers(2);;
        List<Integer> cIDs = CustomerController.getAllCustomerIDs();
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
            
            CustomerController.updateCustomer(cTest);
        }
        
        //retrieve an updated customer from the datastore
        custTest = CustomerController.getCustomer(cIDs.get(0));
        
        assertEquals(99, custTest.getC_MILES_ALL(), 0.0001);


    }

    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {


        CustomerController.deleteCustomer(1);

        //fail("NullPointerException");

    }

  
    @Test 
    public void testH_bookFlightTest() {

        CustomerController.createRandomCustomers(1);;
    	Customer selectedCustomer = CustomerController.getAllCustomersAsList().get(0);
    	CustomerController.bookRandomFlight(selectedCustomer);
    	assertEquals(1, selectedCustomer.getC_FLIGHTS().size(), 0.0001);
    }
   
    @Test
    public void delete_Test() {


        CustomerController.deleteAllCustomers();


    }
    
    


}