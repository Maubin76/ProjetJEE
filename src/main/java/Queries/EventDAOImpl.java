package Queries;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Models.DJ;
import Models.Event;
import Models.Lieu;

public class EventDAOImpl extends EventDAO {
	
	@Override
	public void insertEventtoDB(Event event) {
		
		// Attributs de l'event
		DJ dj = event.getDj();
		Lieu lieu = event.getLieu();
		Date date = event.getDate();
		LocalTime horaireDebut = event.getHoraireDebut();
		LocalTime horaireFin = event.getHoraireFin();
		
		// Convertion utilisable SQL
		String djID = dj.getId().toString();
		String lieuNom = lieu.getNomLieu();
		Time horaireDebutSQL = Time.valueOf(horaireDebut);
		Time horaireFinSQL = Time.valueOf(horaireFin);
		
		Connection connection = DBManager.getInstance().getConnection();
		
		/* Connection connection = null;
		try {
			connection = DBConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		String sql = "INSERT INTO `info_captainm_schema`.`Events` "
					+ "(`dj`, `lieu`, `date`, `horaireDebut`, `horaireFin`)"
					+ "VALUES "
					+ "(?, ?, ?, ?, ?)";
		
		try {
			// Permet d'éviter les injections SQL
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, djID);
			preparedStatement.setString(2, lieuNom);
			preparedStatement.setDate(3, date);
			preparedStatement.setTime(4, horaireDebutSQL);
			preparedStatement.setTime(5, horaireFinSQL);
			
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
	public List<Event> findByAll() {
		
		List<Event> resultList = new ArrayList<Event>();
		
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
			rs = statement.executeQuery("SELECT * FROM Events");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String djID = rs.getString("dj");
				String lieuNom = rs.getString("lieu");
				Date date = rs.getDate("date");
				LocalTime horaireDebut = rs.getTime("horaireDebut").toLocalTime();
				LocalTime horaireFin = rs.getTime("horaireFin").toLocalTime();
				
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				DJ dj = djdao.findByID(UUID.fromString(djID));
				// On crée un ClubDAO pour aller chercher le club via le nom
				ClubDAO clubdao = new ClubDAOImpl();
				Lieu lieu = clubdao.findByName(lieuNom);
				
				Event event = new Event(dj, lieu, date, horaireDebut, horaireFin);
				
				resultList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}

	@Override
	public List<Event> EventListBetweenDates(Date dateMin, Date dateMax) {
List<Event> resultList = new ArrayList<Event>();
		
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
		
		ResultSet rs = null;
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Events WHERE date BETWEEN ? AND ?");
			statement.setDate(1, dateMin);
			statement.setDate(2, dateMax);
			rs = statement.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()) {
				String djID = rs.getString("dj");
				String lieuNom = rs.getString("lieu");
				Date date = rs.getDate("date");
				LocalTime horaireDebut = rs.getTime("horaireDebut").toLocalTime();
				LocalTime horaireFin = rs.getTime("horaireFin").toLocalTime();
				
				// On crée un DJDAO pour aller chercher le DJ via son id
				DJDAO djdao = new DJDAOImpl();
				DJ dj = djdao.findByID(UUID.fromString(djID));
				// On crée un ClubDAO pour aller chercher le club via le nom
				ClubDAO clubdao = new ClubDAOImpl();
				Lieu lieu = clubdao.findByName(lieuNom);
				
				Event event = new Event(dj, lieu, date, horaireDebut, horaireFin);
				
				resultList.add(event);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultList;
	}
	
	public static void main(String[] args) {

	}

}
