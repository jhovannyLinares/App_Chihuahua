package mx.morena.persistencia.repository.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.morena.persistencia.entidad.Modulo;
import mx.morena.persistencia.entidad.Perfil;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.persistencia.rowmapper.ModulosRowMapper;
import mx.morena.persistencia.rowmapper.PerfilRowMapper;
import mx.morena.persistencia.rowmapper.StringRowMapper;
import mx.morena.persistencia.rowmapper.UsuarioRowMapper;

@Repository
public class UsuarioRepository implements IUsuarioRepository {
	
	Logger logger = LoggerFactory.getLogger(UsuarioRepository.class);

	@Autowired
	private JdbcTemplate template;

	@Override
	public Usuario findByUsuario(String usuario) {

		String sql = "SELECT * FROM app_usuario WHERE usuario = ?";
		try {
			return template.queryForObject(sql, new Object[] { usuario }, new int[] { 1 }, new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public Usuario findById(long idUsuario) {

		String sql = "SELECT * FROM app_usuario WHERE id = ?::INTEGER";
		try {
			return template.queryForObject(sql, new Object[] { idUsuario }, new int[] { 1 }, new UsuarioRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public boolean update(Usuario usuario) {

		String sql = "update app_usuario set email = ?, password= ? where id = ?::INTEGER";

		int status = template.update(sql, usuario.getEmail(), usuario.getPassword(), usuario.getId());

		if (status != 0) {

			return true;
		} else {

			return false;
		}

	}

	@Override
	public Perfil findPerfilById(long id) {

		String sql = "SELECT * FROM app_perfil WHERE id = ?::INTEGER";
		try {
			return template.queryForObject(sql, new Long[] { id }, new int[] { 1 }, new PerfilRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;
		}
	}

	@Override
	public List<Modulo> findModulosByPerfil(Long id) {

		String sql = "select * from app_perfil_modulo apm inner join app_modulo am on apm.id_modulo = am.id where id_perfil = ?::INTEGER";
		try {
			return template.queryForObject(sql, new Long[] { id }, new int[] { 1 }, new ModulosRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return new ArrayList<Modulo>();
		}

	}

	@Override
	public String getClaveElector(Long usuario) {
		String sql = "select clave_elector from app_representantes ar "
				+ "inner join app_usuario au "
				+ "on ar.id = au.id_representante "
				+ "where au.id = ?";
		
		logger.debug(sql);
		try {
			return template.queryForObject(sql, new Object[] { usuario }, new int[] { Types.NUMERIC },
					new StringRowMapper());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
