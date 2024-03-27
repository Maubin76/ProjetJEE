package Queries;

import java.sql.Date;
import java.util.List;

import Models.DJ;
import Models.Event;

public abstract class EventDAO {
	
	public abstract List<Event> findByAll();
	
	public abstract List<Event> EventListBetweenDates(Date dateMin, Date dateMax);
	
	public abstract void insertEventtoDB(Event event);
	
	public abstract List<Event> findByNom(String nom);
	
	public abstract void addDjtoEvent(DJ dj, Event event);
}

