package Queries;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/* Classe permettant de récupérer les informations de 
 * config.properties afin d'éviter d'écrire les logins
 * en brut dans le projet */
public class DBConfig {
	
	// Voir doc java.util.Properties;
    private static final Properties properties = new Properties();

    // Exécuté à la création d'une isntanc
    static {
        try {
        	// Load les id de config.properties dans properties
            FileInputStream input = new FileInputStream("./src/main/java/config.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getURL() {
    	return properties.getProperty("JDBC_URL");
    }
    
    public static String getLogin() {
        return properties.getProperty("DB_LOGIN");
    }

    public static String getPassword() {
        return properties.getProperty("DB_PASSWORD");
    }
    
    public static String getDriver() {
        return properties.getProperty("DB_DRIVER");
    }
}
