package Models;

import java.sql.Date;

public class Event {

    private DJ dj;
    private Lieu lieu;
    private Date date;
    
    public Event(DJ dj, Lieu lieu, Date date) {
    	this.dj = dj;
    	this.lieu = lieu;
    	this.date = date;
    }

    public DJ getDj() {
        return dj;
    }

    public void setDj(DJ dj) {
        this.dj = dj;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
