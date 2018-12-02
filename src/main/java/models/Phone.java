package models;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Indexed
@Table(name = "PHONES")
public class Phone implements Serializable {
    @Id
    private Integer P_ID;

    @Column
    Integer C_ID;

    @Column
    private String P_NUMBER;

    @Column
    private PhoneType P_TYPE;

    public Phone(String number) {
        P_NUMBER = number;
        P_ID = (int) (Math.random() * 100000);
    }

    public Phone() {
        P_NUMBER = "0";
        for (int i = 0; i < 10; i++) {
            P_NUMBER += (int) (Math.random() * 10);
        }
        P_ID = (int) (Math.random() * 100000);
    }

    public void setC_ID(Integer c_ID) {
        C_ID = c_ID;
    }

    public void setP_TYPE(PhoneType p_TYPE) {
        P_TYPE = p_TYPE;
    }

    public Integer getC_ID() {
        return C_ID;
    }

    public PhoneType getP_TYPE() {
        return P_TYPE;
    }

    public void setP_ID(Integer p_ID) {
        P_ID = p_ID;
    }

    public void setP_NUMBER(String p_NUMBER) {
        P_NUMBER = p_NUMBER;
    }

    public Integer getP_ID() {
        return P_ID;
    }

    public String getP_NUMBER() {
        return P_NUMBER;
    }

    public enum PhoneType {
        Mobile,
        Standard
    }
}
