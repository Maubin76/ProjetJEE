package Queries;

import java.sql.Date;
import java.util.List;

import Models.Event;

public abstract class EventDAO {
	
	public abstract List<Event> findByAll();
	
	public abstract List<Event> EventListBetweenDates(Date dateMin, Date dateMax);
	
	public abstract void insertEventtoDB(Event event);
}

