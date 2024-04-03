package Controllers;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;

import Models.Event;
import Models.DJ;
import Queries.DJDAO;
import Queries.DJDAOImpl;
import Queries.EventDAO;
import Queries.EventDAOImpl;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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

    // Méthode pour ajouter un certain nombre de jours à une date
    public static Date addDays(Date date, int days) {
        long millisecondsInDay = 24 * 60 * 60 * 1000L;
        long addedMilliseconds = date.getTime() + days * millisecondsInDay;
        return new Date(addedMilliseconds);
    }

    // Endpoint pour récupérer les événements au format JSON
    @GET
    @Path("/events")
    @Produces(MediaType.APPLICATION_JSON)
    public String getEvents() {
        Date dateMin = new Date(System.currentTimeMillis()); // Date d'aujourd'hui
        Date dateMax = addDays(dateMin, 30); // Date dans 30 jours
        List<Event> eventList = new ArrayList<>();
        List<EventJSON> eventListJSON = new ArrayList<>();
        eventList = eventDao.eventListBetweenDates(dateMin, dateMax); // Récupération des événements entre les dates spécifiées
        // Conversion des événements en format JSON
        for (Event event : eventList) {
            eventListJSON.add(new EventJSON(event.getNom(), event.getDj().getNomDeScene(), event.getLieu().getNomLieu(), event.getDate(), event.getHoraireDebut(), event.getHoraireFin()));
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(eventListJSON); // Conversion en JSON
        return json;
    }

    // Endpoint pour ajouter un DJ à un événement
    @POST
    @Path("/ajoute")
    @Consumes("application/x-www-form-urlencoded")
    public void addDjs(@FormParam("evenement") String nomEvenement, @FormParam("dj") String nomSceneDJ) {
        DJ dj = djDao.findByNomDeScene(nomSceneDJ).get(0); // Récupération du DJ par son nom de scène
        Event evenement = eventDao.findByNom(nomEvenement).get(0); // Récupération de l'événement par son nom
        DJ djAssigne = evenement.getDj(); // Récupération du DJ déjà assigné à l'événement
        // Vérification si le DJ et l'événement existent et si aucun DJ n'est déjà assigné à l'événement
        if (dj != null && evenement != null && djAssigne == null) {
            eventDao.addDjtoEvent(dj, evenement); // Ajout du DJ à l'événement dans la base de données
            dj.ajoutEvent(); // Met à jour le nombre d'événement du DJ
        }
    }
}

