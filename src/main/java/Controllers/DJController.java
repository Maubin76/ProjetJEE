package Controllers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Models.DJ;
import Models.StyleMusical;
import Queries.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dj-management")
public class DJController {

    private DJDAO djDao = new DJDAOImpl(); // Instance du DAO pour l'accès aux données des DJs

    // Endpoint pour récupérer tous les DJs
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDjs() {
        List<DJ> djs;
        djs = djDao.findByAll(); // Appel à la méthode du DAO pour récupérer tous les DJs
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(djs); // Conversion en JSON
        return json;
    }

    // Endpoint pour récupérer les DJs par nom de scène
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getDjs(@QueryParam("name") String nomScene) {
        List<DJ> djs;
        if (nomScene != null) {
            djs = djDao.findByNomDeScene(nomScene); // Appel à la méthode du DAO pour récupérer les DJs par nom de scène
        } else {
            djs = null;
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = gson.toJson(djs); // Conversion en JSON
        return json;
    }

    // Endpoint pour supprimer les DJs
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/supprimer")
    public void deleteDJs(@FormParam("nomDeSceneDJSupprimer") String nomScene) {
        List<DJ> djs;
        djs = djDao.findByNomDeScene(nomScene); // Récupération des DJs par nom de scène
        Iterator<DJ> iterateur = djs.iterator();
        while (iterateur.hasNext()) {
            DJ djactuel = iterateur.next();
            djDao.deleteFromDB(djactuel); // Suppression des DJs de la base de données
        }
    }

    // Endpoint pour ajouter un DJ
    @POST
    @Path("/ajout")
    @Consumes("application/x-www-form-urlencoded")
    public void addDJs(@FormParam("nom") String nom,
                       @FormParam("prenom") String prenom,
                       @FormParam("dateDeNaissance") String dateDeNaissance,
                       @FormParam("nomDeScene") String nomDeScene,
                       @FormParam("lieuDeResidence") String lieuDeResidence,
                       @FormParam("styleDeMusique") int styleDeMusique) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
        try {
            java.util.Date utilDate = dateFormat.parse(dateDeNaissance); // Conversion de la date de naissance
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            dateNaissance = sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StyleMusical styleMusical = null;
        // Conversion du style musical en enum StyleMusical
        switch (styleDeMusique) {
            case 1:
                styleMusical = StyleMusical.Electro;
                break;
            case 2:
                styleMusical = StyleMusical.House;
                break;
            case 3:
                styleMusical = StyleMusical.Hard_style;
                break;
            case 4:
                styleMusical = StyleMusical.EDM;
                break;
        }
        DJ dj = new DJ(nom, prenom, nomDeScene, dateNaissance, lieuDeResidence, styleMusical); // Création d'un objet DJ
        djDao.insertDJtoDB(dj); // Insertion du DJ dans la base de données
    }

    // Endpoint pour modifier les DJs
    @POST
    @Path("/modifier")
    @Consumes("application/x-www-form-urlencoded")
    public void modifyDJs(@FormParam("nomDeSceneDJ") String nomDeSceneDJ,
                          @FormParam("champ") String champ,
                          @FormParam("modification") String modification,
                          @FormParam("styleDeMusique") String style) {
        List<DJ> djs;
        djs = djDao.findByNomDeScene(nomDeSceneDJ); // Récupération des DJs par nom de scène
        Iterator<DJ> iterateur = djs.iterator();
        // Modification des champs des DJs
        if (champ.equals("styleMusical")) {
            while (iterateur.hasNext()) {
                DJ djactuel = iterateur.next();
                djDao.modifyDJ(djactuel, champ, style); // Appel à la méthode du DAO pour modifier le style musical
            }
        } else {
            while (iterateur.hasNext()) {
                DJ djactuel = iterateur.next();
                djDao.modifyDJ(djactuel, champ, modification); // Appel à la méthode du DAO pour modifier le champ spécifié
            }
        }
    }
}
