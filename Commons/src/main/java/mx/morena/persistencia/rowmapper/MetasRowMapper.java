package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Metas;

public class MetasRowMapper implements RowMapper<List<Metas>> {

	@Override
	public List<Metas> mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		List<Metas> lstMetas = new ArrayList<Metas>();
		Metas metas = null;
		
		do {
			metas = new Metas();
			
			metas.setListaNominal(rs.getLong("listado_nominal"));
			metas.setMetaCrg(rs.getLong("meta_crg"));
			metas.setMetaRg(rs.getLong("meta_rg"));
			metas.setMetaRc(rs.getLong("meta_rc"));
			metas.setMetaConvencidos(rs.getLong("meta_convencidos"));
			
			lstMetas.add(metas);
			
		}while (rs.next());
		
		return lstMetas;
	}

}
