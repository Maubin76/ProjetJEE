package Controllers;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Models.Event;
import Models.Lieu;
import Models.DJ;
import Queries.DJDAO;
import Queries.DJDAOImpl;
import Queries.EventDAO;
import Queries.EventDAOImpl;
import Queries.ClubDAO;
import Queries.ClubDAOImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/event-management")
public class EventController {

    // Classe interne pour représenter les événements au format JSON
    public class EventJSON {
        private String nom;
        private String nomDJ;
        private String nomLieu;
        private Date date;
        private Time horaireDebut;
        private Time horaireFin;

        public EventJSON(String nom, String nomDJ, String nomLieu, Date date, Time horaireDebut, Time horaireFin) {
            this.nom = nom;
            this.nomDJ = nomDJ;
            this.nomLieu = nomLieu;
            this.date = date;
            this.horaireDebut = horaireDebut;
            this.horaireFin = horaireFin;
        }
    }

    private EventDAO eventDao = new EventDAOImpl(); // Instance du DAO pour la gestion des événements
    private DJDAO djDao = new DJDAOImpl(); // Instance du DAO pour la gestion des DJs
    private ClubDAO clubDao = new ClubDAOImpl(); // Instance du DAO pour la gestion des lieus


    // Méthode pour ajouter un certain nombre de jours à une date
    public static Date addDays(Date date, int days) {
        long millisecondsInDay = 24 * 60 * 60 * 1000L;
        long addedMilliseconds = date.getTime() + days * millisecondsInDay;
        return new Date(addedMilliseconds);
    }

    // Endpoint d'affichage des events du mois actuel
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvents() {
    	Date dateMin = new Date(System.currentTimeMillis()); // Date d'aujourd'hui
		return getEventsOfTheMonth(dateMin);
    }
    
    // Fonction qui récupère les événements au format JSON
    private String getEventsOfTheMonth(Date dateMin) {
    	Date dateMax = addDays(dateMin, 30); // Durée dateMin-dateMax de 1 mois
		List<Event> eventList = new ArrayList<>();
		List<EventJSON> eventListJSON=new ArrayList<>(); // Liste qui va accueillir les events à afficher
		eventList = eventDao.eventListBetweenDates(dateMin,dateMax); // Récupération des events dans une liste
		for (Event event : eventList) {
        	if(event.getDj()==null) {
        		eventListJSON.add(new EventJSON(event.getNom(), "Pas de DJ pour cet event", event.getLieu().getNomLieu(), event.getDate(), event.getHoraireDebut(), event.getHoraireFin()));
        	}else {
        		eventListJSON.add(new EventJSON(event.getNom(), event.getDj().getNomDeScene(), event.getLieu().getNomLieu(), event.getDate(), event.getHoraireDebut(), event.getHoraireFin()));
        	}
        }
    	GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json=gson.toJson(eventListJSON);
		return json;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ShowEvents")
    public String ShowEvents(@QueryParam("dateString") String dateString) {
        java.sql.Date sqlDate = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        try {
            // Parsing de la chaîne en java.util.Date
            java.util.Date parsedDate = dateFormat.parse(dateString);

            // Conversion de java.util.Date en java.sql.Date
            sqlDate = new java.sql.Date(parsedDate.getTime());

            // Affichage de la date SQL
            System.out.println("Date SQL : " + sqlDate);
        } catch (ParseException e) {
            // Gestion des erreurs de parsing
            e.printStackTrace();
        }
        return getEventsOfTheMonth(sqlDate);
    }
    
    // Endpoint pour ajouter un DJ à un événement
    @POST
    @Path("/ajoutDjEvent")
    @Consumes("application/x-www-form-urlencoded")
    public void addDjs(@FormParam("evenement") String nomEvenement, @FormParam("nomDeSceneDJ") String nomSceneDJ) {
        DJ dj = djDao.findByNomDeScene(nomSceneDJ).get(0); // Récupération du DJ par son nom de scène
        Event evenement = eventDao.findByNom(nomEvenement).get(0); // Récupération de l'événement par son nom
        DJ djAssigne = evenement.getDj(); // Récupération du DJ déjà assigné à l'événement
        // Vérification si le DJ et l'événement existent et si aucun DJ n'est déjà assigné à l'événement
        if (dj != null && evenement != null && djAssigne == null) {
            eventDao.addDjtoEvent(dj, evenement); // Ajout du DJ à l'événement dans la base de données
        }
        
    }
    
  //Endpoint pour ajouter un événement
    @POST
    @Path("/ajoutEvent")
    @Consumes("application/x-www-form-urlencoded")
    public void addEvent(@FormParam("nomEvent") String nomEvent, 
    		@FormParam("nomLieu") String nomLieu,
    		@FormParam("dateEvent") String dateEvent,
    		@FormParam("heureDebut") String heureDebut,
    		@FormParam("heureFin") String heureFin) {
    	
    	
    	Lieu lieu = clubDao.findByName(nomLieu); // Récupération des lieux de la base
    	// Format de la date
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateEvenement = null;
        Time heureDebutObjet = null;
        Time heureFinObjet = null;

        try {
        	// Conversions entre sql.date et util.date
            java.util.Date utilDate = dateFormat.parse(dateEvent);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            dateEvenement = sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Définir le format de l'heure
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
        	// Convertir la Date en objet Time
            heureDebutObjet = new Time(sdf.parse(heureDebut).getTime());
            heureFinObjet = new Time(sdf.parse(heureFin).getTime());
           } catch (ParseException e) {
            e.printStackTrace();
        }
        Event event = new Event(nomEvent, null,lieu,dateEvenement,heureDebutObjet,heureFinObjet); // Instanciation de l'event
        eventDao.insertEventtoDB(event); // Ajout dans la base
    }
    
    @GET
    @Path("/eventDispo")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEventsDispo(@QueryParam("name") String nom) {
    	// On récupère le dj dans la base de données
    	DJ dj =djDao.findByNomDeScene(nom).get(0);
    	// On récupère la liste de tous les events sans DJ et de tous les events assigné au dj recherché
    	List<Event> eventPossible=new ArrayList<>();// Liste qui accueillera les events où le DJ choisi peut s'assigner
    	List<Event> eventLibre = new ArrayList<>(); // Liste qui accueillera les events pour lesquels aucun DJ n'est assigné
		eventLibre = eventDao.findByDJ(null); // Récupération des events sans DJ
		List<Event> eventDJ = new ArrayList<>();
		eventDJ=eventDao.findByDJ(dj);
		List<EventJSON> eventListJSON=new ArrayList<>();
		for (Event eventsLibre : eventLibre) {
			Boolean disponible=true;
			//Pour chaque event possible on vérifie que cela correspond bien avec les contraintes de temps
			for (Event eventsDJ : eventDJ) {
				if (eventsDJ.getLieu().getContinent().equals(eventsLibre.getLieu().getContinent())) {
					if(addDays(eventsDJ.getDate(),2).compareTo(eventsLibre.getDate())>0) { // Contrainte de déplacement sur le même continent (2jours)
						disponible=false;
					}
					
				}else {
					if(addDays(eventsDJ.getDate(),3).compareTo(eventsLibre.getDate())>0) { // Contrainte de déplacement sur un continent différent (3jours)
						disponible=false;
					}
				}
			}
			if(disponible) {
				eventPossible.add(eventsLibre); // Ajout à la liste des event auxquels le DJ peut être assigné
			}
		}
		// On ajoute ensuite tous les events possible dans un format pour le json
		for (Event event : eventPossible) {
			// Gestion de l'affichage des events assignables
        	if(event.getDj()==null) {
        		eventListJSON.add(new EventJSON(event.getNom(), "Pas de DJ pour cet event", event.getLieu().getNomLieu(), event.getDate(), event.getHoraireDebut(), event.getHoraireFin()));
        	}else {
        		eventListJSON.add(new EventJSON(event.getNom(), event.getDj().getNomDeScene(), event.getLieu().getNomLieu(), event.getDate(), event.getHoraireDebut(), event.getHoraireFin()));
        	}
        }
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json=gson.toJson(eventListJSON);
		// On retourne le json à la vue
		return json; 

    }

}

