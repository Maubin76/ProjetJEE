package Controllers;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Models.DJ;
import Models.Lieu;
import Queries.ClubDAO;
import Queries.ClubDAOImpl;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Définition du chemin de base pour toutes les méthodes dans cette classe
@Path("/club-management")
public class ClubController {
    private ClubDAO clubDao = new ClubDAOImpl(); // Initialisation de l'objet ClubDAO avec l'implémentation ClubDAOImpl

    // Endpoint pour récupérer la liste des clubs au format JSON
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getClubss() {
        List<Lieu> clubs; // Déclaration d'une liste de Lieu pour stocker les clubs
        clubs = clubDao.findByAll(); // Récupération de tous les clubs depuis la base de données
        GsonBuilder builder = new GsonBuilder(); // Création d'un constructeur GsonBuilder
        Gson gson = builder.create(); // Création d'un objet Gson à partir du constructeur
        String json = gson.toJson(clubs); // Conversion de la liste des clubs en JSON
        return json; // Retourne la représentation JSON des clubs
    }
}
