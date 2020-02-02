package astrofili.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import astrofili.entity.Pianeta;
import astrofili.entity.SatelliteNaturale;

public class SatelliteNaturaleDAO {
	
	private Connection conn;
	
	public SatelliteNaturale create(SatelliteNaturale satellite) {
		// TODO
		return null;
	}
	
	public List<SatelliteNaturale> read(Pianeta pianeta) throws SQLException, DAOException {
		// TODO
		return null;
	}
	
	public SatelliteNaturale read(int idSatellite) throws SQLException, DAOException{
		// TODO
		return null;
	}
	
	public SatelliteNaturale update(SatelliteNaturale satellite) {
		// TODO
		return null;
	}
	
	public Boolean delete(SatelliteNaturale satellite) {
		// TODO
		return null;
	}
}
