package Controllers;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import Models.DJ;
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
		 
		/*public String getDjs(@QueryParam("nomScene") String nomScene) {
			List<DJ> djs;
			if (nomScene!=null) {
				djs = djDao.findByNomScene(nomScene);
			}else {
				djs = djDao.findByAll();
			}
			GsonBuilder builder = new GsonBuilder();
			Gson gson = builder.create();
			String json=gson.toJson(djs);
			return json;	
		}*/
}
