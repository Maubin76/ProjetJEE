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
	private ClubDAO clubDao = new ClubDAOImpl();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getClubss() {
		List<Lieu> clubs;
		clubs = clubDao.findByAll();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String json=gson.toJson(clubs);
		return json;	
	}
}
