package Models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class Event {

    private DJ dj;
    private Lieu lieu;
    private Date date;
    private Time horaireDebut;
    private Time horaireFin;
    
    public Event(DJ dj, Lieu lieu, Date date, Time horaireDebut, Time horaireFin) {
    	this.dj = dj;
    	this.lieu = lieu;
    	this.date = date;
    	this.horaireDebut = horaireDebut;
    	this.horaireFin = horaireFin;
    }

    public Time getHoraireDebut() {
        return horaireDebut;
    }

    public void setHoraireDebut(Time horaireDebut) {
        this.horaireDebut = horaireDebut;
    }
    
    public Time getHoraireFin() {
        return horaireFin;
    }

    public void setHoraireFin(Time horaireFin) {
        this.horaireFin = horaireFin;
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
