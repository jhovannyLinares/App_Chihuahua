package mx.morena.persistencia.repository;

import mx.morena.persistencia.entidad.InstalacionCasilla;

public interface IInstalacionCasillasRepository {

	void save(InstalacionCasilla ic);

	Long getMaxId();

}
