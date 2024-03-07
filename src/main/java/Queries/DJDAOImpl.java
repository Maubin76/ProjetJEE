package Queries;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Models.DJ;
import Models.StyleMusical;

public class DJDAOImpl implements DJDAO {

	/*
	public void insertDJtoDB(DJ dj) {
		
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		// INSERT INTO `info_captainm_schema`.`DJ` (`id`, `nom`, `prenom`, `nomDeScene`, `dateDeNaissance`, `lieuDeResidence`, `styleMusical`) 
		// VALUES ('0e30b4a1-1577-4c70-9bcf-092140d888b8', 'Montel', 'Theo', 'Xx_miniZgEg_xX', '2003-10-05', 'zeubi', 'Hard_style');
		
	}
	*/
	
	
	@Override
	public List<DJ> findByAll() {
		
		List<DJ> resultList = new ArrayList<DJ>();
			
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM DJ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				UUID id = UUID.fromString(rs.getString("id"));
				String prenom = rs.getString("prenom");
				String nom = rs.getString("nom");
				String nomDeScene = rs.getString("nomDeScene");
				LocalDate dateDeNaissance = rs.getDate("dateDeNaissance").toLocalDate();
				String lieuDeResidence = rs.getString("lieuDeResidence");
				StyleMusical style = StyleMusical.valueOf(rs.getString("styleMusical"));
				
				DJ dj = new DJ(id, prenom, nom, nomDeScene, dateDeNaissance, lieuDeResidence, style);
				
				resultList.add(dj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}
	
}
