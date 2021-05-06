package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.Partido;

public interface IPartidosRepository {

	public List<Partido> getGobernador(Long entidad);

	public List<Partido> getMunicipal(Long municipio);

	public List<Partido> getSindico(Long municipio);

	public List<Partido> getDiputadoLocal(Long federal);

	public List<Partido> getDiputadoFederal(Long federal);

	public List<Partido> getGobernador();

	public List<Partido> getMunicipal();

	public List<Partido> getSindico();

	public List<Partido> getDiputadoLocal();

	public List<Partido> getDiputadoFederal();

}