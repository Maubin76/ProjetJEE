package Controllers;

import java.util.*;
import java.sql.Date;

import Models.Event;
import Models.Lieu;
import Models.DJ;
import Models.StyleMusical;
//import Queries.EventDAO;

public class EventController {

	public void ShowNextMonthEvents() {
		Date dateMin = new Date(System.currentTimeMillis()); // Date d'aujourd'hui
		Date dateMax = addDays(dateMin, 30);
		// List<Event> eventList = EventDAO.EventListBetweenDates(dateMin, dateMin);
		
		// Echantillon tampon pour les tests
		DJ dj = new DJ("nomDJ", "prenomDJ", "nomDeSceneDJ", addDays(dateMin, -10000), "residenceDJ", StyleMusical.Electro);
		Lieu lieu = new Lieu("nomLieu", "villeLieu", "paysLieu", "continentLieu");
		List<Event> eventList = new ArrayList<>();
		eventList.add(new Event(dj, lieu, addDays(dateMin, -10)));
		eventList.add(new Event(dj, lieu, addDays(dateMin, -100)));
		
	}
	
    public static Date addDays(Date date, int days) {
        long millisecondsInDay = 24 * 60 * 60 * 1000L;
        long addedMilliseconds = date.getTime() + days * millisecondsInDay;
        return new Date(addedMilliseconds);
    }
}
