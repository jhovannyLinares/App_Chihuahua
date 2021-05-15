package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.RepresentacionPartidos;

public interface IPartidoVotacionRepository {

	List<RepresentacionPartidos> getPartidos();
}
