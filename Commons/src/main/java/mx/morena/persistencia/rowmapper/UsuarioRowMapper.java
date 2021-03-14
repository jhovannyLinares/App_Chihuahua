package mx.morena.persistencia.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import mx.morena.persistencia.entidad.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

		Usuario usuario = new Usuario();

		usuario.setId(rs.getLong("id"));
		usuario.setUsuario(rs.getString("usuario"));
		usuario.setPassword(rs.getString("password"));
		usuario.setPerfil(rs.getLong("perfil_id"));
		usuario.setEntidad(rs.getString("entidad_id"));
		usuario.setFederal(rs.getString("federal_id"));
		usuario.setMunicipio(rs.getString("municipio_id"));

		return usuario;
	}

}
