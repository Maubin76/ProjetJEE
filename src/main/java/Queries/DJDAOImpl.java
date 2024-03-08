package Queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Models.DJ;
import Models.StyleMusical;

public class DJDAOImpl implements DJDAO {

	@Override
	public void insertDJtoDB(DJ dj) {
		
		String id = dj.getId().toString();
		String nom = dj.getNom();
		String prenom = dj.getPrenom();
		String nomDeScene = dj.getNomDeScene();
		Date dateDeNaissance = dj.getDateDeNaissance();
		String lieuDeResidence = dj.getLieuDeResidence();
		String styleMusical = dj.getStyleMusical().toString();
		
		Connection connection = DBManager.getInstance().getConnection();
		
		/* Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		String sql = "INSERT INTO `info_captainm_schema`.`DJ` "
					+ "(`id`, `nom`, `prenom`, `nomDeScene`, `dateDeNaissance`, `lieuDeResidence`, `styleMusical`)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)";
		
		try {
			// Permet d'éviter les injections SQL
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, nom);
			preparedStatement.setString(3, prenom);
			preparedStatement.setString(4, nomDeScene);
			preparedStatement.setDate(5, dateDeNaissance);
			preparedStatement.setString(6, lieuDeResidence);
			preparedStatement.setString(7, styleMusical);
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			preparedStatement.close();
			
			if(rowsAffected < 0) {
				System.err.println("Aucune colonne modifiée");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	public List<DJ> findByAll() {
		
		List<DJ> resultList = new ArrayList<DJ>();
		
		Connection connection = DBManager.getInstance().getConnection();
		/*
		Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
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
				Date dateDeNaissance = rs.getDate("dateDeNaissance");
				String lieuDeResidence = rs.getString("lieuDeResidence");
				StyleMusical style = StyleMusical.valueOf(rs.getString("styleMusical"));
				
				DJ dj = new DJ(id, nom, prenom, nomDeScene, dateDeNaissance, lieuDeResidence, style);
				
				resultList.add(dj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public List<DJ> findByNomDeScene(String _nomDeScene) {
		
		List<DJ> resultList = new ArrayList<DJ>();
		
		Connection connection = DBManager.getInstance().getConnection();
		
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM DJ WHERE nomDeScene = '" + _nomDeScene + "'");
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
				Date dateDeNaissance = rs.getDate("dateDeNaissance");
				String lieuDeResidence = rs.getString("lieuDeResidence");
				StyleMusical style = StyleMusical.valueOf(rs.getString("styleMusical"));
				
				DJ dj = new DJ(id, nom, prenom, nomDeScene, dateDeNaissance, lieuDeResidence, style);
				
				resultList.add(dj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public void deleteFromDB(DJ dj) {
	    UUID uuid = dj.getId();
	    String id = uuid.toString();

	    try (Connection connection = DBManager.getInstance().getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM DJ WHERE id = ?")) {

	        preparedStatement.setString(1, id);
	        preparedStatement.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void modifyDJ(DJ dj, String champs, String valeur) {
	    try (Connection connection = DBManager.getInstance().getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DJ SET " + champs + " = ? WHERE id = ?")) {

	        preparedStatement.setString(1, valeur);
	        preparedStatement.setString(2, dj.getId().toString());

	        int rowsUpdated = preparedStatement.executeUpdate();
	        System.out.println(rowsUpdated + " lignes mises à jour.");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	@Override
	public void modifyDJ(DJ dj, String champs, Date date) {
		try (Connection connection = DBManager.getInstance().getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DJ SET " + champs + " = ? WHERE id = ?")) {

		        preparedStatement.setDate(1, date);
		        preparedStatement.setString(2, dj.getId().toString());

		        int rowsUpdated = preparedStatement.executeUpdate();
		        System.out.println(rowsUpdated + " lignes mises à jour.");
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	@Override
	public void modifyDJ(DJ dj, String champs, StyleMusical style) {
		try (Connection connection = DBManager.getInstance().getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement("UPDATE DJ SET " + champs + " = ? WHERE id = ?")) {

		        preparedStatement.setString(1, style.toString());
		        preparedStatement.setString(2, dj.getId().toString());

		        int rowsUpdated = preparedStatement.executeUpdate();
		        System.out.println(rowsUpdated + " lignes mises à jour.");
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
	
	
	
	public static void main(String[] args) {	
		DJDAO dao = new DJDAOImpl();
		
		afficherDJ();
	}
	
	private static void afficherDJ() {
		DJDAO dao = new DJDAOImpl();
		
		// On affiche tous les noms de scène
		List<DJ> liste = dao.findByAll();
		for(int i=0; i<liste.size(); i++) {
			String id = liste.get(0).getId().toString();
			String prenom = liste.get(0).getPrenom();
			String nom = liste.get(0).getNom();
			String nomDeScene = liste.get(0).getNomDeScene();
			String dateDeNaissance = liste.get(0).getDateDeNaissance().toString();
			String lieuDeResidence = liste.get(0).getLieuDeResidence();
			String style = liste.get(0).getStyleMusical().toString();
			
			String djString = (i+1) + " " + id + " " + prenom + " " + nom + " " + 
					nomDeScene + " " + dateDeNaissance + " " + 
					lieuDeResidence + " " + style;
			System.out.println(djString);
		}
	}
}