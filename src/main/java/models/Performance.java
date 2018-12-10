package models;

import org.hibernate.search.annotations.Indexed;
import tools.Config;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
@Table(name="PERFORMANCE")
public class Performance implements Serializable {
    @Id
    private String P_ID;

    @Column
    private long M_READ;

    @Column
    private long M_GET_ALL;

    @Column
    private long M_UPDATE;

    @Column
    private long M_DELETE;


    @Column
    private long M_BOOK;


    public Performance() {
        P_ID = Config.PERSISTENCE_UNIT_NAME;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public void setM_READ(long m_READ) {
        M_READ = m_READ;
    }

    public void setM_GET_ALL(long m_GET_ALL) {
        M_GET_ALL = m_GET_ALL;
    }

    public void setM_UPDATE(long m_UPDATE) {
        M_UPDATE = m_UPDATE;
    }

    public void setM_DELETE(long m_DELETE) {
        M_DELETE = m_DELETE;
    }


    public void setM_BOOK(long m_BOOK) {
        M_BOOK = m_BOOK;
    }


    public String getP_ID() {
        return P_ID;
    }

    public long getM_READ() {
        return M_READ;
    }

    public long getM_GET_ALL() {
        return M_GET_ALL;
    }

    public long getM_UPDATE() {
        return M_UPDATE;
    }

    public long getM_DELETE() {
        return M_DELETE;
    }


    public long getM_BOOK() {
        return M_BOOK;
    }

    public String toString()
    {
        return P_ID +"\n" + M_BOOK +"\n" + M_DELETE +"\n" + M_GET_ALL +"\n" + M_READ +"\n" + M_UPDATE;
    }
}
