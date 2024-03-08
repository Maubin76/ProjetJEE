package Queries;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import Models.DJ;
import Models.StyleMusical;

public class DJDAOMockImpl implements DJDAO {
	
	@Override
	public List<DJ> findByAll() {
		List<DJ> Djs = new ArrayList<DJ>();
		Date ajd =new Date(0);
		DJ livre1= new DJ("p","n","ns",ajd,"l",StyleMusical.Electro);
		Djs.add(livre1);
		return Djs;
	}
	

	@Override
	public void insertDJtoDB(DJ dj) {
		
		
	}	
	
}
