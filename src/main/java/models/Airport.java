package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="AIRPORT")
public class Airport  implements Serializable {
    @Id
    private Integer A_ID;

    @Column
    private String A_NAME;

    @Column
    private String A_PLACE;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "FS_START_ID")
    private List<FlightSegment> A_FS_START;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "FS_END_ID")
    private List<FlightSegment> A_FS_END;

    public Airport()
    {
        A_FS_START = new ArrayList<>();
        A_FS_END = new ArrayList<>();
    }

    public Airport(String name, String place)
    {
        A_ID = (int) (Math.random() * 100000);
        A_NAME = name;
        A_PLACE = place;
        A_FS_START = new ArrayList<>();
        A_FS_END = new ArrayList<>();
    }

    public void setA_ID(Integer a_ID) {
        A_ID = a_ID;
    }

    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }

    public void setA_PLACE(String a_PLACE) {
        A_PLACE = a_PLACE;
    }

    public void setA_FS_START(List<FlightSegment> a_FS_START) {
        A_FS_START = a_FS_START;
    }

    public void setA_FS_END(List<FlightSegment> a_FS_END) {
        A_FS_END = a_FS_END;
    }

    public Integer getA_ID() {
        return A_ID;
    }

    public String getA_NAME() {
        return A_NAME;
    }

    public String getA_PLACE() {
        return A_PLACE;
    }

    public List<FlightSegment> getA_FS_START() {
        return A_FS_START;
    }

    public List<FlightSegment> getA_FS_END() {
        return A_FS_END;
    }


    public String toString()
    {
        return A_PLACE + ", Flughafen " + A_NAME;
    }
}
