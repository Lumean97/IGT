package models;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Indexed
@Table(name = "CUSTOMER")
public class Customer implements Serializable {
    @Id
    private Integer C_ID;

    @Column
    private CustomerRank C_STATE = CustomerRank.NONE;

    @Column
    private Integer C_MILES_ALL = 0;

    @Column
    private Integer C_MILES_YEAR = 0;

    @Column
    private String C_ADDR;

    @Column
    private String C_NAME;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "C_ID")
    private List<Phone> C_PHONES;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CUSTOMER_FLIGHT",
            joinColumns = { @JoinColumn(name = "C_ID")},
            inverseJoinColumns = { @JoinColumn(name = "F_ID")}
    )
    private List<Flight> C_FLIGHTS;

    public Customer(){

    }

    // Getter and Setter

    public Customer(String name, String address) {
        C_ID = (int) (Math.random() * 100000);
        C_NAME = name;
        C_ADDR = address;
    }

    public void setC_FLIGHTS(List<Flight> c_FLIGHTS) {
        C_FLIGHTS = c_FLIGHTS;
    }

    public List<Flight> getC_FLIGHTS() {
        return C_FLIGHTS;
    }

    public void setC_PHONES(List<Phone> c_PHONES) {
        C_PHONES = c_PHONES;
    }

    public List<Phone> getC_PHONES() {
        return C_PHONES;
    }

    public Integer getC_ID() {
        return C_ID;
    }

    public String getC_NAME() {
        return C_NAME;
    }

    public void setC_ID(Integer c_ID) {
        C_ID = c_ID;
    }

    public void setC_NAME(String c_NAME) {
        C_NAME = c_NAME;
    }

    public void setC_STATE(CustomerRank c_STATE) {
        C_STATE = c_STATE;
    }

    public void setC_MILES_ALL(Integer c_MILES_ALL) {
        C_MILES_ALL = c_MILES_ALL;
    }

    public void setC_MILES_YEAR(Integer c_MILES_YEAR) {
        C_MILES_YEAR = c_MILES_YEAR;
    }

    public void setC_ADDR(String c_ADDR) {
        C_ADDR = c_ADDR;
    }

    public CustomerRank getC_STATE() {
        return C_STATE;
    }

    public Integer getC_MILES_ALL() {
        return C_MILES_ALL;
    }

    public Integer getC_MILES_YEAR() {
        return C_MILES_YEAR;
    }

    public String getC_ADDR() {
        return C_ADDR;
    }

    public String toString(){
        String retval = "Der Kunde " + C_NAME + ", wohnhaft in " + C_ADDR + " ist bisher " + C_MILES_ALL + " Meilen geflogen, davon " + C_MILES_YEAR + " alleine dieses Jahr. Er hat den Rang " + C_STATE + " und folgende Telefonnummern: ";
        for (Phone phone : C_PHONES) {
            retval += "\n " + phone.getP_NUMBER();
        }
        retval += "\n Hier seine Flugdaten:";
        for(Flight flight : C_FLIGHTS)
        {
            retval += "\n" + flight;
        }
        return retval;
    }

    public enum CustomerRank {
        NONE,
        SILVER,
        GOLD,
        PLATINUM,
        SPECIAL_PLATINUM,
        WHITE_GOLD
    }
}
