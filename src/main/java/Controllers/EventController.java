package Controllers;

import java.util.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalTime;

import Models.Event;
import Models.Lieu;
import Models.DJ;
import Models.StyleMusical;
import jakarta.servlet.RequestDispatcher;
//import Queries.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/event-management")
public class EventController {

	@GET
    @Produces(MediaType.TEXT_HTML)
    @Path("list")
	public void ShowNextMonthEvents(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException{ 
		Date dateMin = new Date(System.currentTimeMillis()); // Date d'aujourd'hui
		Date dateMax = addDays(dateMin, 30);
		// List<Event> eventList = EventDAO.EventListBetweenDates(dateMin, dateMin);
		
		// Echantillon tampon pour les tests
		DJ dj = new DJ("nomDJ", "prenomDJ", "nomDeSceneDJ", addDays(dateMin, -10000), "residenceDJ", StyleMusical.Electro);
		Lieu lieu = new Lieu("nomLieu", "villeLieu", "paysLieu", "continentLieu");
		List<Event> eventList = new ArrayList<>();

		//eventList.add(new Event(dj, lieu, addDays(dateMin, -10)));
		//eventList.add(new Event(dj, lieu, addDays(dateMin, -100)));
		
        req.setAttribute("eventsList", eventList); // Ajoutez la liste des événements à l'objet HttpServletRequest
        HttpSession session = req.getSession(); // Obtenez la session
        session.setAttribute("eventsList", eventList); // Ajoutez la liste des événements à la session (optionnel)
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/nextMonthEvent.html");
        dispatcher.forward(req, resp);
	}
	
    public static Date addDays(Date date, int days) {
        long millisecondsInDay = 24 * 60 * 60 * 1000L;
        long addedMilliseconds = date.getTime() + days * millisecondsInDay;
        return new Date(addedMilliseconds);
    }
}
