package astrofili.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import astrofili.database.DBManager;

public class DBCreate {

	public static void main(String[] args) {
		
		String createAstronomoTable = "CREATE TABLE ASTRONOMO("
				+ "IDASTRONOMO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "NOME VARCHAR(50) NOT NULL, "
				+ "COGNOME VARCHAR(50) NOT NULL, "
				+ "DATANASCITA DATE NOT NULL, "
				+ "EMAIL VARCHAR(100) NOT NULL, "
				+ "DESCRIZIONEPERSONALE VARCHAR(255) NOT NULL, "
				+ "TITOLO VARCHAR(100) NOT NULL, "
				+ "PROFESSIONE VARCHAR(50) NOT NULL);";
		String createOggettoCelesteTable = "CREATE TABLE OGGETTOCELESTE("
				+ "IDOGGETTO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "TIPOOGGETTOCELESTE VARCHAR(18) NOT NULL, "
				+ "NOME VARCHAR(50) NOT NULL, "
				+ "GALASSIADIAPPARTENENZA INT, "
				+ "SISTEMAPLANETARIODIAPPARTENENZA INT, "
				+ "CENTROSTELLARE INT, "
				+ "FOREIGN KEY (GALASSIADIAPPARTENENZA) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL, "
				+ "FOREIGN KEY (SISTEMAPLANETARIODIAPPARTENENZA) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL, "
				+ "FOREIGN KEY (CENTROSTELLARE) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE SET NULL);";
		String createSatelliteNaturaleTable = "CREATE TABLE SATELLITENATURALE("
				+ "IDSATELLITE INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "NOME VARCHAR(70) NOT NULL, "
				+ "PIANETA INT NOT NULL, "
				+ "FOREIGN KEY (PIANETA) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE CASCADE);";
		String createArticoloTable = "CREATE TABLE ARTICOLO("
				+ "IDARTICOLO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "TITOLO VARCHAR(30) NOT NULL, "
				+ "OGGETTOARTICOLO VARCHAR(50) NOT NULL, "
				+ "CORPO VARCHAR(255) NOT NULL, "
				+ "DATAINSERIMENTO DATE NOT NULL, "
				+ "NUMEROLIKE INT NOT NULL, "
				+ "NUMERODISLIKE INT NOT NULL, "
				+ "OGGETTOCELESTE INT NOT NULL, "
				+ "AUTORE INT NOT NULL, "
				+ "FOREIGN KEY (OGGETTOCELESTE) REFERENCES OGGETTOCELESTE "
				+ "ON DELETE CASCADE, "
				+ "FOREIGN KEY (AUTORE) REFERENCES ASTRONOMO "
				+ "ON DELETE CASCADE);";
		String createCommentoTable = "CREATE TABLE COMMENTO("
				+ "IDCOMMENTO INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "TITOLO VARCHAR(50) NOT NULL, "
				+ "CORPO VARCHAR(255) NOT NULL, "
				+ "ARTICOLO INT NOT NULL, "
				+ "AUTORE INT, "
				+ "FOREIGN KEY (ARTICOLO) REFERENCES ARTICOLO "
				+ "ON DELETE CASCADE, "
				+ "FOREIGN KEY (AUTORE) REFERENCES ASTRONOMO "
				+ "ON DELETE SET NULL);";
		
		try {
			Connection conn = DBManager.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(createAstronomoTable);
			stmt.execute(createOggettoCelesteTable);
			stmt.execute(createSatelliteNaturaleTable);
			stmt.execute(createArticoloTable);
			stmt.execute(createCommentoTable);
			DBManager.closeConnection();
			
		} catch(SQLException e) {
			dropTables();
		}
	}
	
	public static void dropTables() {
		
		String dropCommento = "DROP TABLE IF EXISTS COMMENTO;";
		String dropArticolo = "DROP TABLE IF EXISTS ARTICOLO;";
		String dropSatelliteNaturale = "DROP TABLE IF EXISTS SATELLITENATURALE;";
		String dropOggettoCeleste = "DROP TABLE IF EXISTS OGGETTOCELESTE;";
		String dropAstronomo = "DROP TABLE IF EXISTS ASTRONOMO;";
		
		try {
			Connection conn = DBManager.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute(dropCommento);
			stmt.execute(dropArticolo);
			stmt.execute(dropSatelliteNaturale);
			stmt.execute(dropOggettoCeleste);
			stmt.execute(dropAstronomo);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
