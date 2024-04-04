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
import Queries.DJDAO;
import Queries.DJDAOImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dj-management")
public class DJController {

    // Déclaration de l'interface DJDAO et de son implémentation DJDAOImpl
    private DJDAO djDao = new DJDAOImpl();
    
    // Endpoint pour récupérer tous les DJ
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDjs() {
        List<DJ> djs;
        djs = djDao.findByAll();// Récupération des données de la base sous forme de liste
        // Création du JSON qui va accueillir la réponse de la base
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json=gson.toJson(djs);
        return json; // Renvoie des données à la vue
    }
    
    // Endpoint pour récupérer les DJ par leur nom de scène en utilisant une requête avec paramètre Query
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getDjs(@QueryParam("name") String nomScene) {
        List<DJ> djs;
        if (nomScene!=null) {
            djs = djDao.findByNomDeScene(nomScene); // Récupération des données de la base sous forme de liste
        } else {
            djs = null;
        }
        // Création du JSON qui va accueillir la réponse de la base
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json=gson.toJson(djs);
        return json; // Renvoie des données à la vue
    }
    
    // Endpoint pour supprimer un DJ en utilisant une méthode POST avec des paramètres formulaire
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/supprimer")
    public void deleteDJs(@FormParam("nomDeSceneDJSupprimer") String nomScene) {
        List<DJ> djs;
        djs = djDao.findByNomDeScene(nomScene); // Récupération des données de la base sous forme de liste
        Iterator<DJ> iterateur = djs.iterator(); // Itérateur de parcours de la liste
        while (iterateur.hasNext()){
            DJ djactuel= iterateur.next();
            djDao.deleteFromDB(djactuel); // Suppression du DJ choisi
        }
    }
    
    // Endpoint pour ajouter un DJ en utilisant une méthode POST avec des paramètres formulaire
    @POST
    @Path("/ajout")
    @Consumes("application/x-www-form-urlencoded")
    public void addDJs(@FormParam("nom") String nom,
            @FormParam("prenom") String prenom,
            @FormParam("dateDeNaissance") String dateDeNaissance,
            @FormParam("nomDeScene") String nomDeScene,
            @FormParam("lieuDeResidence") String lieuDeResidence,
            @FormParam("styleDeMusique") int styleDeMusique) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Format de la date utilisé
        Date dateNaissance = null;
        try {
        	// Conversion du format de la date pour l'enregistrement dans la base de données
            java.util.Date utilDate = dateFormat.parse(dateDeNaissance);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            dateNaissance = sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Conversion du style musical String-Enum
        StyleMusical styleMusical = null;
        switch (styleDeMusique) {
            case 1:
                styleMusical=StyleMusical.Electro;
                break;
            case 2:
                styleMusical=StyleMusical.House;
                break;
            case 3:
                styleMusical=StyleMusical.Hard_style;
                break;
            case 4:
                styleMusical=StyleMusical.EDM;
                break;
        }
        DJ dj= new DJ(nom,prenom,nomDeScene,dateNaissance,lieuDeResidence,styleMusical); // Instanciation du DJ
        djDao.insertDJtoDB(dj); // Ajout du DJ dans la base
    }

    // Endpoint pour modifier un DJ en utilisant une méthode POST avec des paramètres formulaire
    @POST
    @Path("/modifier")
    @Consumes("application/x-www-form-urlencoded")
    public void modifyDJs(@FormParam("nomDeSceneDJ") String nomDeSceneDJ,
            @FormParam("champ") String champ,
            @FormParam("modification") String modification,
            @FormParam("styleDeMusique") String style) {
        List<DJ> djs;
        djs = djDao.findByNomDeScene(nomDeSceneDJ); // Récupération du DJ associé au nom de scène saisi
        Iterator<DJ> iterateur = djs.iterator();
        if(champ.equals("styleMusical")) { // Modification dans le cas du champ musical
            while (iterateur.hasNext()){
                DJ djactuel= iterateur.next();
                djDao.modifyDJ(djactuel,champ,style);
            }
        } else { // Modification pour les autres champs
            while (iterateur.hasNext()){
                DJ djactuel= iterateur.next();
                djDao.modifyDJ(djactuel,champ,modification);
            }
        }
    }
}

