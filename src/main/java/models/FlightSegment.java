package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "FLIGHT_SEGMENT")
public class FlightSegment implements Serializable {
    @Id
    private Integer FS_ID;

    @Column
    private Integer FS_START_ID;

    @Column
    private Integer FS_END_ID;

    @ManyToMany(mappedBy = "F_FS")
    private List<Flight> F_FLIGHTS;

    public FlightSegment(){

    }

    public String toString()
    {
        return "Not implemented Yet!";
    }

    public FlightSegment(Integer start, Integer end) {
        FS_ID = (int) (Math.random() * 100000);
        FS_START_ID = start;
        FS_END_ID = end;
    }

    public void setFS_ID(Integer FS_ID) {
        this.FS_ID = FS_ID;
    }

    public void setFS_START_ID(Integer FS_START_ID) {
        this.FS_START_ID = FS_START_ID;
    }

    public void setFS_END_ID(Integer FS_END_ID) {
        this.FS_END_ID = FS_END_ID;
    }

    public void setF_FLIGHTS(List<Flight> f_FLIGHTS) {
        F_FLIGHTS = f_FLIGHTS;
    }

    public Integer getFS_ID() {
        return FS_ID;
    }

    public Integer getFS_START_ID() {
        return FS_START_ID;
    }

    public Integer getFS_END_ID() {
        return FS_END_ID;
    }

    public List<Flight> getF_FLIGHTS() {
        return F_FLIGHTS;
    }
}
