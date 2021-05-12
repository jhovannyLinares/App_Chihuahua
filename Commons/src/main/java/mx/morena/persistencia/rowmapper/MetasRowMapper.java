package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Metas;

public class MetasRowMapper implements RowMapper<Metas> {

	@Override
	public Metas mapRow(ResultSet rs, int rowNum) throws SQLException {

		Metas metas = new Metas();

		metas.setListaNominal(rs.getLong("listado_nominal"));
		metas.setMetaCrg(rs.getLong("meta_crg"));
		metas.setMetaRg(rs.getLong("meta_rg"));
		metas.setMetaRc(rs.getLong("meta_rc"));
		metas.setMetaConvencidos(rs.getLong("meta_convencidos"));

		return metas;
	}

}
