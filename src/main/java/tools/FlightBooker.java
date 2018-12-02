package tools;

import controllers.CustomerController;
import controllers.FlightController;
import models.Customer;
import models.Flight;
import models.Phone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlightBooker {

    static Scanner s = new Scanner(System.in);
    public static void main(String[] args){



        CustomerController customerController = CustomerController.getInstance();

        FlightController flightController = FlightController.getInstance();
        System.out.println("Guten Tag bei IntegratedAirlines. Sind Sie: \n 1 Neukunde \n 2 Bestandskunde");
        String choice = s.nextLine();
        Customer customer = null;
        if(choice.equals("1")){
            customer = registerNewCustomer();
        }
        while(customer == null)
        {
            System.out.println("Bitte gebene Sie Ihre Kundenummer ein:");
            customer = customerController.getCustomer(Integer.valueOf(s.nextLine()));
        }
        System.out.println("Willkommen " + customer.getC_NAME() + "! Wo möchten Sie gerne hinfliegen? (Zielflughafen)");
        String end = s.nextLine();
        System.out.println("Sehr schön, nun benötige ich Ihren Startort.");
        String start = s.nextLine();
        List<Flight> flights = flightController.findFlights(start, end);
        Flight bookedFlight;
        if(flights.size() == 0){
            System.out.println("Für diese Strecke sind leider keine Flüge mehr verfügbar. Hier die restlichen Flüge:");
            flights = flightController.getAllFlightsAsList();
        }
        System.out.println("Bitte wählen Sie einen Flug aus.");
        for(int i = 0; i<flights.size(); i++)
        {
            System.out.println(i + "\n " + flights.get(i));
        }
        bookedFlight = flights.get(s.nextInt());
        System.out.println("Sehr schön, ich buche diesen Flug für Sie!");
        customerController.bookFlight(bookedFlight, customer);
    }

    public static Customer registerNewCustomer(){
        System.out.println("Das ist kein Problem. Ich erstelle eben für Sie einen neuen Kunden. Dafür benötige ich zuerst Ihren vollen Namen.");
        String name = s.nextLine();
        System.out.println("Und nun Ihre Adresse");
        String address = s.nextLine();
        System.out.println("Sehr schön! Wenn Sie Telefonnummern besitzen, bitte tragen Sie diese nun ebenfalls ein. Sobald Sie damit fertig sind, oder keine mehr haben, drücken Sie einfach nochmals Enter.");
        String choice = s.nextLine();
        List<Phone> numbers = new ArrayList<>();
        while(!choice.equals(""))
        {
            Phone p = new Phone(choice);
            System.out.println("Für eine Mobile Nummer, geben Sie 'ja' ein, ansonsten bestätigen Sie einfach mit Enter.");
            if(s.nextLine().equals("")){
                p.setP_TYPE(Phone.PhoneType.Standard);
            }else{
                p.setP_TYPE(Phone.PhoneType.Mobile);
            }
                numbers.add(p);
            System.out.println("Geben Sie eine weitere Nummer ein, oder bestätigen Sie mit Enter.");
            choice = s.nextLine();
        }
        System.out.println("Super, ich erstelle für Sie einen neuen Nutzer.");
        Customer retval = CustomerController.getInstance().createCustomer(name, address, numbers);
        System.out.println("Es folgt nun Ihre eindeutige Kundennummer. Bitte vergessen Sie diese nicht, diese identifiziert Sie!");
        System.out.println(retval.getC_ID());
        return retval;
    }
}
