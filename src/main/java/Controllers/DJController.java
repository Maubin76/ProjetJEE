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
        djs = djDao.findByAll();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json=gson.toJson(djs);
        return json;    
    }
    
    // Endpoint pour récupérer les DJ par leur nom de scène en utilisant une requête avec paramètre Query
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public String getDjs(@QueryParam("name") String nomScene) {
        List<DJ> djs;
        if (nomScene!=null) {
            djs = djDao.findByNomDeScene(nomScene);
        } else {
            djs = null;
        }
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json=gson.toJson(djs);
        return json;    
    }
    
    // Endpoint pour supprimer un DJ en utilisant une méthode POST avec des paramètres formulaire
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("/supprimer")
    public void deleteDJs(@FormParam("nomDeSceneDJSupprimer") String nomScene) {
        List<DJ> djs;
        djs = djDao.findByNomDeScene(nomScene);
        Iterator<DJ> iterateur = djs.iterator();
        while (iterateur.hasNext()){
            DJ djactuel= iterateur.next();
            djDao.deleteFromDB(djactuel);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNaissance = null;
        try {
            java.util.Date utilDate = dateFormat.parse(dateDeNaissance);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            dateNaissance = sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        DJ dj= new DJ(nom,prenom,nomDeScene,dateNaissance,lieuDeResidence,styleMusical);
        djDao.insertDJtoDB(dj);
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
        djs = djDao.findByNomDeScene(nomDeSceneDJ);
        Iterator<DJ> iterateur = djs.iterator();
        if(champ.equals("styleMusical")) {
            while (iterateur.hasNext()){
                DJ djactuel= iterateur.next();
                djDao.modifyDJ(djactuel,champ,style);
            }
        } else {
            while (iterateur.hasNext()){
                DJ djactuel= iterateur.next();
                djDao.modifyDJ(djactuel,champ,modification);
            }
        }
    }
}

