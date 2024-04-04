package Queries;

import java.io.IOException;
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
import Models.Lieu;
import Models.StyleMusical;

public class ClubDAOImpl extends ClubDAO{

	@Override
	public Lieu findByName(String nom) {
		
		Lieu lieu = null;
		
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
			rs = statement.executeQuery("SELECT * FROM Clubs WHERE nom = '" + nom + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rs.next();

			String ville = rs.getString("ville");
			String pays = rs.getString("pays");
			String continent = rs.getString("continent");
			
			lieu = new Lieu(nom, ville, pays, continent);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lieu;
	}
	
	@Override
	public List<Lieu> findByAll() {
		
		List<Lieu> resultList = new ArrayList<Lieu>();
		
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
			rs = statement.executeQuery("SELECT * FROM Clubs ORDER BY nom");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String nom = rs.getString("nom");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				String continent = rs.getString("continent");
				
				Lieu lieu = new Lieu(nom, ville, pays, continent);
				
				resultList.add(lieu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<Lieu> findByVille(String _ville) {
		
		List<Lieu> resultList = new ArrayList<Lieu>();
		
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
			rs = statement.executeQuery("SELECT * FROM Clubs WHERE ville = '" + _ville + "'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String nom = rs.getString("nom");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				String continent = rs.getString("continent");
				
				Lieu lieu = new Lieu(nom, ville, pays, continent);
				
				resultList.add(lieu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	@Override
	public void insertClubtoDB(Lieu lieu) {
		
		String nom = lieu.getNomLieu().toString();
		String ville = lieu.getVille();
		String pays = lieu.getPays();
		String continent = lieu.getContinent();
		
		Connection connection = DBManager.getInstance().getConnection();
		
		/* Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		String sql = "INSERT INTO `info_captainm_schema`.`Clubs` "
					+ "(`nom`, `ville`, `pays`, `continent`)"
					+ "VALUES "
					+ "(?, ?, ?, ?)";
		
		try {
			// Permet d'éviter les injections SQL
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, nom);
			preparedStatement.setString(2, ville);
			preparedStatement.setString(3, pays);
			preparedStatement.setString(4, continent);
			
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
	
	// PERMET DE CHARGER UN CSV DANS LA BDD
	private static void csvToDB(String filePath) throws IOException {
		CSVFileIO io = new CSVFileIO();
		ArrayList<String[]> liste = io.readCSV(filePath);
		
		for(int i = 1; i<liste.size(); i++) {
			String nom = liste.get(i)[0];
			String ville = liste.get(i)[1];
			String pays = liste.get(i)[2];
			String continent = liste.get(i)[3];
			Lieu lieu = new Lieu(nom, ville, pays, continent);
			
			ClubDAO dao = new ClubDAOImpl();
			dao.insertClubtoDB(lieu);
			System.out.println(i + "Lieu ajouté : " + nom + " " + ville + " " + pays + " " + continent);
		}
	}
	
	
	private static void afficherClubs() {
		ClubDAO dao = new ClubDAOImpl();
		
		// On affiche tous les noms de scène
		List<Lieu> liste = dao.findByAll();
		for(int i=0; i<liste.size(); i++) {
			String nom = liste.get(i).getNomLieu();
			String ville = liste.get(i).getVille();
			String pays = liste.get(i).getPays();
			String continent = liste.get(i).getContinent();
			
			String djString = ((i+1) + " " + nom + " " + ville + " " + pays + " " + continent);
			System.out.println(djString);
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		//csvToDB("C:\\Users\\geoff\\OneDrive\\Documents\\JAVA_DEV\\workspace\\ProjetJEE\\src\\main\\java\\Queries\\liste.csv");
		afficherClubs();
	}
	
}
