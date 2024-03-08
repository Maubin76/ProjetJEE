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
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/dj-management")
public class DJController {

	@GET 
	@Produces(MediaType.TEXT_PLAIN) 
	@Path("/hello") 
	public String hello() { 
		return "Hello World!"; 
	}
	private DJDAO djDao = new DJDAOImpl();
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/djs")
		public String getDjs() {
			List<DJ> djs;
			djs = djDao.findByAll();
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String json=gson.toJson(djs);
			return json;	
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/modification")
		public String getDjs(@QueryParam("name") String nomScene) {
			List<DJ> djs;
			if (nomScene!=null) {
				djs = djDao.findByNomDeScene(nomScene);
			}else {
				djs = null;
			}
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String json=gson.toJson(djs);
			return json;	
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/supprimer")
		public void deleteDJs(@QueryParam("nom") String nomScene) {
			List<DJ> djs;
			djs = djDao.findByNomDeScene(nomScene);
			Iterator<DJ> iterateur = djs.iterator();
			while (iterateur.hasNext()){
				DJ djactuel= iterateur.next();
				djDao.deleteFromDB(djactuel);
			}
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/ajout")
		public void addDJs(@QueryParam("nom") String nom,
				@QueryParam("prenom") String prenom,
				@QueryParam("dateDeNaissance") String dateDeNaissance,
				@QueryParam("nomDeScene") String nomDeScene,
				@QueryParam("lieuDeResidence") String lieuDeResidence,
				@QueryParam("styleDeMusique") int styleDeMusique) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateNaissance = null;
			try {
				java.util.Date utilDate = dateFormat.parse(dateDeNaissance);
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				dateNaissance = sqlDate;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
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
		
}
