package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Casilla;

public interface ICasillaRepository {

	public List<Casilla> getCasillas(Long entidad);

	public List<Casilla> getCasillas(Long entidad, Long federal);

	public List<Casilla> getCasillas(Long entidad, Long federal, Long municipio);

}
