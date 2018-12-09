package models;

import controllers.FlightController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "FLIGHT")
public class Flight implements Serializable {
    @Id
    private Integer F_ID;

    @Column
    private Date F_START_TIME;

    @Column
    private Date F_LANDING_TIME;

    @Column
    private Double F_PRICE_E;

    @Column
    private Double F_PRICE_F;

    @Column
    private String F_PLANE_TYPE;

    @Column
    private Integer F_SEATS_E;

    @Column
    private Integer F_SEATS_F;



    @ManyToMany(mappedBy = "C_FLIGHTS", fetch = FetchType.EAGER)
    private List<Customer> C_ID;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FLIGHT_SEGMENT_FLIGHT",
            joinColumns = {@JoinColumn(name = "F_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FS_ID")}
    )
    private List<FlightSegment> F_FS;

    public Flight() {
        F_ID = (int) (Math.random() * 100000);
        F_START_TIME = new Date();
        F_LANDING_TIME = new Date();
        F_PRICE_E = 100D;
        F_PRICE_F = 5000D;
        F_PLANE_TYPE = "BOEING";
        F_SEATS_E = 150;
        F_SEATS_F = 25;
        C_ID = new ArrayList<>();
        F_FS = new ArrayList<>();
    }

    public void setF_START_TIME(Date f_START_TIME) {
        F_START_TIME = f_START_TIME;
    }

    public void setF_LANDING_TIME(Date f_LANDING_TIME) {
        F_LANDING_TIME = f_LANDING_TIME;
    }

    public void setF_PRICE_E(Double f_PRICE_E) {
        F_PRICE_E = f_PRICE_E;
    }

    public void setF_PRICE_F(Double f_PRICE_F) {
        F_PRICE_F = f_PRICE_F;
    }

    public void setF_PLANE_TYPE(String f_PLANE_TYPE) {
        F_PLANE_TYPE = f_PLANE_TYPE;
    }

    public void setF_SEATS_E(Integer f_SEATS_E) {
        F_SEATS_E = f_SEATS_E;
    }

    public void setF_SEATS_F(Integer f_SEATS_F) {
        F_SEATS_F = f_SEATS_F;
    }

    public void setC_ID(List<Customer> c_ID) {
        C_ID = c_ID;
    }

    public void setF_FS(List<FlightSegment> f_FS) {
        F_FS = f_FS;
    }

    public void setF_ID(Integer f_ID) {
        F_ID = f_ID;
    }

    public Integer getF_ID() {
        return F_ID;
    }

    public Date getF_START_TIME() {
        return F_START_TIME;
    }

    public Date getF_LANDING_TIME() {
        return F_LANDING_TIME;
    }

    public Double getF_PRICE_E() {
        return F_PRICE_E;
    }

    public Double getF_PRICE_F() {
        return F_PRICE_F;
    }

    public String getF_PLANE_TYPE() {
        return F_PLANE_TYPE;
    }

    public Integer getF_SEATS_E() {
        return F_SEATS_E;
    }

    public Integer getF_SEATS_F() {
        return F_SEATS_F;
    }

    public List<Customer> getC_ID() {
        return C_ID;
    }

    public List<FlightSegment> getF_FS() {
        return F_FS;
    }
}
