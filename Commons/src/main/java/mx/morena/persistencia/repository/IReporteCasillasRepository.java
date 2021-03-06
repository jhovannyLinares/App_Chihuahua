package mx.morena.persistencia.repository;

import java.util.List;

import mx.morena.persistencia.entidad.ActasVotos;
import mx.morena.persistencia.entidad.AfluenciaVotos;
import mx.morena.persistencia.entidad.EstadoVotacion;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.entidad.VotacionPorHora;

public interface IReporteCasillasRepository {

	int save(ReporteCasilla rc);

	int updateHoraCierre(ReporteCasilla rc);

	List<ReporteCasilla> getReporteByIdCasilla(Long idCasilla);

	VotacionPorHora getCountByDistritoAndTipoVotacion(Long idFederal, Long idReporte, String once);
	
	List<EstadoVotacion> getEstadoByIdCasilla(Long idCasilla);
	
	int updateEstadoVotacion(EstadoVotacion ev);
	
	int insertEstadoVotacion(EstadoVotacion ev);
	
	List<AfluenciaVotos> getAfluenciaByIdCasilla(Long isCasilla);
	
	List<ActasVotos> getActasByIdCasilla(Long idCasilla);
	
	int updateAfluenciaVotacion(AfluenciaVotos ev);
	
	int updateActasVotacion(ActasVotos ev);
	
	List<ReporteCasilla> getRegistrosByIdRc(Long idRc, Long idCasilla, Integer tipoReporte);
	
	List<ReporteCasilla> getReporteByIdCasillaAndTipoRep(Long idCasilla, Integer tipoReporte);
	
	List<ReporteCasilla> getCierreByIdCasilla(Long idCasilla);
	
	List<ReporteCasilla> getCierreByIdCasillaAndIsCerrada(Long idCasilla, boolean isCerrada);
}
