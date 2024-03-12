package Models;

import java.sql.Date;
import java.time.LocalTime;

public class Event {

    private DJ dj;
    private Lieu lieu;
    private Date date;
    private LocalTime horaireDebut;
    private LocalTime horaireFin;
    
    
    public Event(DJ dj, Lieu lieu, Date date, LocalTime horaireDebut, LocalTime horaireFin) {
    	this.dj = dj;
    	this.lieu = lieu;
    	this.date = date;
    	this.horaireDebut = horaireDebut;
    	this.horaireFin = horaireFin;
    }

    public LocalTime getHoraireDebut() {
        return horaireDebut;
    }

    public void setHoraireDebut(LocalTime horaireDebut) {
        this.horaireDebut = horaireDebut;
    }
    
    public LocalTime getHoraireFin() {
        return horaireFin;
    }

    public void setHoraireFin(LocalTime horaireFin) {
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
