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

@Path("/club-management")
public class ClubController {
    private ClubDAO clubDao = new ClubDAOImpl(); // Instance du DAO pour la gestion des clubs (lieux)

    // Endpoint pour récupérer tous les clubs au format JSON
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/clubs")
    public String getClubss() {
        List<Lieu> clubs;
        clubs = clubDao.findByAll(); // Récupération de tous les clubs à partir du DAO
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(clubs); // Conversion en JSON
        return json;
    }
}
