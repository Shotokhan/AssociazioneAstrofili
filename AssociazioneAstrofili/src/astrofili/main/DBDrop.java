package astrofili.main;

import java.sql.SQLException;

import astrofili.database.DBManager;

public class DBDrop {

	public static void main(String[] args) {
		DBCreate.dropTables();
		try {
			DBManager.closeConnection();
		} catch (SQLException e) {
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
		}
	}

}
