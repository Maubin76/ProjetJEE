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
				String lieuDeNaissance = rs.getString("lieuDeNaissance");
				StyleMusical style = StyleMusical.valueOf(rs.getString("styleMusical"));
				
				DJ dj = new DJ(id, prenom, nom, nomDeScene, dateDeNaissance, lieuDeNaissance, style);
				
				resultList.add(dj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

	
	
}
