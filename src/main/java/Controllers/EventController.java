package Controllers;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import Models.Event;
import Models.Lieu;
import Models.DJ;
import Models.StyleMusical;
import Queries.DJDAO;
import Queries.DJDAOImpl;
import Queries.EventDAO;
import Queries.EventDAOImpl;
import jakarta.servlet.RequestDispatcher;
//import Queries.EventDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;

@Path("/event-management")
public class EventController {
	public class EventJSON {
		private String nom;
		private String nomDJ;
		private String nomLieu;
		private Date date;
	    private Time horaireDebut;
	    private Time horaireFin;
	    
	    public EventJSON(String nom, String nomDJ, String nomLieu, Date date, Time horaireDebut,Time horaireFin) {
	    	this.nom = nom;
	    	this.nomDJ = nomDJ;
	    	this.nomLieu = nomLieu;
	    	this.date = date;
	    	this.horaireDebut = horaireDebut;
	    	this.horaireFin = horaireFin;
	    }
	}
	private EventDAO eventDao = new EventDAOImpl();
	
	/*@GET
    @Path("list")
	public void ShowNextMonthEvents(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException{
		

		
        //req.setAttribute("eventsList", json); // Ajoutez la liste des événements à l'objet HttpServletRequest
        HttpSession session = req.getSession(); // Obtenez la session
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/nextMonthEvent.html");
		dispatcher.forward(req, resp);
	}*/
    public static Date addDays(Date date, int days) {
        long millisecondsInDay = 24 * 60 * 60 * 1000L;
        long addedMilliseconds = date.getTime() + days * millisecondsInDay;
        return new Date(addedMilliseconds);
    }
    
    @GET
    @Path("/events")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvents() {
    	Date dateMin = new Date(System.currentTimeMillis()); // Date d'aujourd'hui
		Date dateMax = addDays(dateMin, 30);
		// List<Event> eventList = EventDAO.EventListBetweenDates(dateMin, dateMin);
		
		// Echantillon tampon pour les tests
		DJ dj = new DJ("nomDJ", "prenomDJ", "nomDeSceneDJ", addDays(dateMin, -10000), "residenceDJ", StyleMusical.Electro);
		Lieu lieu = new Lieu("nomLieu", "villeLieu", "paysLieu", "continentLieu");
		List<Event> eventList = new ArrayList<>();
		Time time=new Time(0);
		eventList.add(new Event("nom", dj, lieu, dateMax, time, time));
		List<EventJSON> eventListJSON=new ArrayList<>();
		//eventList = eventDao.findByAll();
		for(Event event:eventList) {
			eventListJSON.add(new EventJSON(event.getNom(), event.getDj().getNomDeScene(),event.getLieu().getNomLieu(),event.getDate(),event.getHoraireDebut(),event.getHoraireFin()));
		}
    	GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json=gson.toJson(eventListJSON);
		return json;
    }
    
    @POST
    @Path("/ajoute")
    @Consumes("application/x-www-form-urlencoded")
    public void addDjs(@FormParam("evenement") String evenement,@FormParam("dj") String dj) {
    	System.out.println(evenement+" "+dj);
    }
}
