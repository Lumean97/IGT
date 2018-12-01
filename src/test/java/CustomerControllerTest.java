import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import controllers.CustomerController;
import models.Customer;
import tools.Config;

public class CustomerControllerTest {


    @Before
    public void setUp() throws Exception {

        CustomerController custController = new CustomerController();

        CustomerController.deleteAllCustomers();

        CustomerController.createRandomCustomers(100);;
        

    }

    @After
    public void tearDown() throws Exception {

        CustomerController custController = new CustomerController();

        CustomerController.deleteAllCustomers();


    }

  
    @Test
    public void read_Test() {

        CustomerController custController = new CustomerController();
        System.out.println(CustomerController.getAllCustomerIDs());
        Customer cTest = CustomerController.getAllCustomersAsList().get(1);
        assertEquals(1, cTest.getC_ID(), 0.0001);


    }

   
    @Test
    public void testC_getAllCustomerTest() {
        CustomerController custController = new CustomerController();

        ArrayList<Customer> cList = (ArrayList<Customer>) CustomerController.getAllCustomersAsList();
   
        assertEquals(100, cList.size(), 0.0001);


    }

    @Ignore
    @Test
    public void update_Test() {
        CustomerController custController = new CustomerController();
        Customer custTest = null;

        List<Integer> cIDs = CustomerController.getAllCustomerIDs();
        List<Customer> customersToUpdate = new ArrayList<Customer>();

        for (Integer id : cIDs) {
            //find a customer to update
            //Customer cTest = custController.getCustomer(id);
            Customer cTest = new Customer();

            cTest.setC_ID(1);
            cTest.setC_MILES_ALL(99);
            cTest.setC_MILES_YEAR(30);
            cTest.setC_NAME("Thomas");
            cTest.setC_ADDR("Linguster Weg 4");
            
            CustomerController.updateCustomer(cTest);
        }
        
        //retrieve an updated customer from the datastore
        custTest = CustomerController.getCustomer(1);
        
        assertEquals(99, custTest.getC_MILES_ALL(), 0.0001);


    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void testF_deleteCustomerTest() {

        CustomerController custController = new CustomerController();


        Customer cTest = CustomerController.getCustomer(1);

        CustomerController.deleteCustomer(1);

        cTest = CustomerController.getCustomer(1);

        cTest.getC_ID();

        //fail("NullPointerException");

    }

  
    @Test 
    public void testH_bookFlightTest() {
    	CustomerController custController = new CustomerController();
    	List<Integer> cIDs = CustomerController.getAllCustomerIDs();
    	for (Integer id : cIDs) {
            Customer cTest = new Customer();

            cTest.setC_ID(1);
            CustomerController.updateCustomer(cTest);
           
        }
    	Customer selectedCustomer = CustomerController.getCustomer(1);
    	CustomerController.bookRandomFlight(selectedCustomer);
    	assertEquals(1, selectedCustomer.getC_FLIGHTS().size(), 0.0001);
    }
   
    @Test
    public void delete_Test() {

        CustomerController custController = new CustomerController();

        CustomerController.deleteAllCustomers();


    }
    
    


}