package Controllers;

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
}
