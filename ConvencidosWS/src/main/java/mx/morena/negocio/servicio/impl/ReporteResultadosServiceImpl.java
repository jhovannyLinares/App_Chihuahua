package mx.morena.negocio.servicio.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReportePMunicipalDTO;
import mx.morena.negocio.dto.ReporteResultadosActasDTO;
import mx.morena.negocio.exception.ConvencidosException;
import mx.morena.negocio.servicio.IReporteResultadosService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.Municipio;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IEnvioActasRepository;
import mx.morena.persistencia.repository.IMunicipioRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.persistencia.repository.IVotosPartidoAmbitoRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteResultadosServiceImpl extends MasterService implements IReporteResultadosService {
	
	@Autowired
	private ICasillaRepository casillasRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IDistritoFederalRepository distritoFederalRepository;
	
	@Autowired
	private IEnvioActasRepository envioActasRepository;
	
	@Autowired
	private IMunicipioRepository municipioRepository;
	
	@Autowired
	private IVotosPartidoAmbitoRepository votosRepository;
	
	@Override
	public List<ReporteResultadosActasDTO> getReporteResultados(Long idUsuario) throws ConvencidosException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		Long idEntidad = usuario.getEntidad();
		Long idDf = usuario.getFederal();
		
		List<ReporteResultadosActasDTO> resultados = new ArrayList<ReporteResultadosActasDTO>();
		ReporteResultadosActasDTO dto = null;
		
		ReporteResultadosActasDTO totales = new ReporteResultadosActasDTO();
		totales.setIdFederal("Totales:");
		totales.setCasillas(0L);
		totales.setActasGobernador(0L);
		totales.setPorcentajeActasGobernador(0.0);
		totales.setActasPresidenteMunicipal(0L);
		totales.setActasSindico(0L);
		totales.setActasDiputadoLocal(0L);
		totales.setActasDiputadoFederal(0L);
		
		if (perfil == PERFIL_ESTATAL) {
			List<DistritoFederal> lstDistritos = new ArrayList<DistritoFederal>();
			lstDistritos = distritoFederalRepository.findByEntidad(idEntidad);

			for (DistritoFederal df : lstDistritos) {
				dto = new ReporteResultadosActasDTO();
				dto = calcularReporteActas(idEntidad, df.getId(), df.getCabeceraFederal());
				
				resultados.add(dto);
				
				totales.setCasillas(totales.getCasillas()+dto.getCasillas());
				totales.setActasGobernador(totales.getActasGobernador()+dto.getActasGobernador());
				totales.setActasPresidenteMunicipal(totales.getActasPresidenteMunicipal()+dto.getActasPresidenteMunicipal());
				totales.setActasSindico(totales.getActasSindico()+dto.getActasSindico());
				totales.setActasDiputadoLocal(totales.getActasDiputadoLocal()+dto.getActasDiputadoLocal());
				totales.setActasDiputadoFederal(totales.getActasDiputadoFederal()+dto.getActasDiputadoFederal());
			}
			
			totales.setPorcentajeActasGobernador(dosDecimales((totales.getActasGobernador()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasDiputadoFederal(dosDecimales((totales.getActasDiputadoFederal()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasDiputadoLocal(dosDecimales((totales.getActasDiputadoLocal()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasSindico(dosDecimales((totales.getActasSindico()*100.00)/totales.getCasillas()).doubleValue());
			totales.setPorcentajeActasMunicipal(dosDecimales((totales.getActasPresidenteMunicipal()*100.00)/totales.getCasillas()).doubleValue());
			
			resultados.add(totales);
			
		} else if (perfil >= PERFIL_FEDERAL && perfil < PERFIL_RC) {
			DistritoFederal distrito = distritoFederalRepository.findById(idDf);
			dto = new ReporteResultadosActasDTO();
			dto = calcularReporteActas(idEntidad, distrito.getId(), distrito.getCabeceraFederal());
			
			resultados.add(dto);
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
		
		return resultados;
	}
	
	public ReporteResultadosActasDTO calcularReporteActas(Long idEntidad, Long idDistrito, String distrito) {
		
		Long countCasillas = casillasRepository.countByEntidadAndFederal(idEntidad, idDistrito);
		Long actasGob = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 1L);
		Long actasPreMun = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 2L);
		Long actasSindico = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 3L);
		Long actasDipLocal = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 4L);
		Long actasDipFed = envioActasRepository.countByTipoVotacionAndDistrito(idDistrito, 5L);
		
		ReporteResultadosActasDTO dto = new ReporteResultadosActasDTO();
		
		dto.setIdFederal(distrito);
		dto.setCasillas(countCasillas);
		dto.setActasGobernador(actasGob);
		double porcentajeActasGob = (actasGob * 100.0) / countCasillas ;
		dto.setPorcentajeActasGobernador(dosDecimales(porcentajeActasGob).doubleValue());
		dto.setActasPresidenteMunicipal(actasPreMun);
		double porcentajeActasMun = (actasPreMun * 100.0) / countCasillas ;
		dto.setPorcentajeActasMunicipal(dosDecimales(porcentajeActasMun).doubleValue());
		dto.setActasSindico(actasSindico);
		double porcentajeActasSindico = (actasSindico * 100.0) / countCasillas ;
		dto.setPorcentajeActasSindico(dosDecimales(porcentajeActasSindico).doubleValue());
		dto.setActasDiputadoLocal(actasDipLocal);
		double porcentajeActasLocal = (actasDipLocal * 100.0) / countCasillas ;
		dto.setPorcentajeActasDiputadoLocal(dosDecimales(porcentajeActasLocal).doubleValue());
		dto.setActasDiputadoFederal(actasDipFed);
		double porcentajeActasFederal = (actasDipFed * 100.0) / countCasillas ;
		dto.setPorcentajeActasDiputadoFederal(dosDecimales(porcentajeActasFederal).doubleValue());
		
		return dto;
	}

	@Override
	public void getReporteResultadosDownload(HttpServletResponse response, Long idUsuario, Long perfil) throws ConvencidosException, IOException {
		
		if (perfil >= PERFIL_ESTATAL && perfil < PERFIL_RC) {
			
			setNameFile(response, CSV_REPORTE_RESULTADOS_ACTAS);
			List<ReporteResultadosActasDTO> dto = getReporteResultados(idUsuario);
					
			String[] header = { "idFederal", "casillas", "actasGobernador", "porcentajeActasGobernador", "actasPresidenteMunicipal", "porcentajeActasMunicipal",
					"actasSindico", "porcentajeActasSindico", "actasDiputadoLocal", "porcentajeActasDiputadoLocal", "actasDiputadoFederal", "porcentajeActasDiputadoFederal" };
	
			setWriterFile(response, dto, header);
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
	
	public BigDecimal dosDecimales(double numero) {
		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;
	}
	
	@Override
	public List<ReportePMunicipalDTO> getReportePresidenteMunicipal(Long idUsuario, Long idEleccion) throws ConvencidosException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		//Long perfil = usuario.getPerfil();
		Long idEntidad = usuario.getEntidad();
		//Long idDf = usuario.getFederal();
		
		List<ReportePMunicipalDTO> resultados = new ArrayList<ReportePMunicipalDTO>();
		ReportePMunicipalDTO dto = null;
		
		ReportePMunicipalDTO totales = new ReportePMunicipalDTO();
		totales.setIdFederal(null);
		totales.setMunicipio("Total");
		totales.setListaNominal(0L);
		totales.setPAN(0L);
		totales.setPorcentajePAN(0.0);
		totales.setPRI(0L);
		totales.setPorcentajePRI(0.0);
		totales.setPRD(0L);
		totales.setPorcentajePRD(0.0);
		totales.setPVEM(0L);
		totales.setPorcentajePVEM(0.0);
		totales.setPT(0L);
		totales.setPorcentajePT(0.0);
		totales.setMC(0L);
		totales.setPorcentajeMC(0.0);
		totales.setMORENA(0L);
		totales.setPorcentajeMORENA(0.0);
		totales.setPES(0L);
		totales.setPorcentajePES(0.0);
		totales.setRSP(0L);
		totales.setPorcentajeRSP(0.0);
		totales.setFUERZAMEXICO(0L);
		totales.setPorcentajeFUERZAMEXICO(0.0);
		totales.setPANAL(0L);
		totales.setPorcentajePANAL(0.0);
		totales.setNulos(0L);
		totales.setPorcentajeNulos(0.0);
		totales.setCrg(0L);
		totales.setPorcentajeCrg(0.0);
		totales.setTotal(0L);
		totales.setPorcentajeTotal(0.0);
		totales.setCandidato1(0L);
		totales.setPorcentajeCandidato1(0.0);
		totales.setCandidato2(0L);
		totales.setPorcentajeCandidato2(0.0);
		
		List<Municipio> municipios = municipioRepository.getByEstado(idEntidad);
		
		for (Municipio municipio : municipios) {
			dto = new ReportePMunicipalDTO();
			dto = calcularReporteResultados(idEntidad, municipio.getFederalId(), municipio.getId(), municipio.getDescripcion(), idEleccion);
			
			resultados.add(dto);
			
			totales.setPAN(totales.getPAN() + dto.getPAN());
			totales.setPRI(totales.getPRI() + dto.getPRI());
			totales.setPRD(totales.getPRD() + dto.getPRD());
			totales.setPVEM(totales.getPVEM() + dto.getPVEM());
			totales.setPT(totales.getPT() + dto.getPT());
			totales.setMC(totales.getMC() + dto.getMC());
			totales.setMORENA(totales.getMORENA() + dto.getMORENA());
			totales.setPES(totales.getPES() + dto.getPES());
			totales.setRSP(totales.getRSP() + dto.getRSP());
			totales.setFUERZAMEXICO(totales.getFUERZAMEXICO() + dto.getFUERZAMEXICO());
			totales.setPANAL(totales.getPANAL() + dto.getPANAL());
			totales.setNulos(totales.getNulos() + dto.getNulos());
			totales.setCrg(totales.getCrg() + dto.getCrg());
			totales.setTotal(totales.getTotal() + dto.getTotal());
			totales.setCandidato1(totales.getCandidato1() + dto.getCandidato1());
			totales.setCandidato2(totales.getCandidato2() + dto.getCandidato2());
			totales.setListaNominal(totales.getListaNominal() + dto.getListaNominal());
		}
		//falta total porcentajes
		totales.setPorcentajePAN(dosDecimales((totales.getPAN()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePRI(dosDecimales((totales.getPRI()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePRD(dosDecimales((totales.getPRD()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePVEM(dosDecimales((totales.getPVEM()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePT(dosDecimales((totales.getPT()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeMC(dosDecimales((totales.getMC()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeMORENA(dosDecimales((totales.getMORENA()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePES(dosDecimales((totales.getPES()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeRSP(dosDecimales((totales.getRSP()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeFUERZAMEXICO(dosDecimales((totales.getFUERZAMEXICO()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajePANAL(dosDecimales((totales.getPANAL()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeNulos(dosDecimales((totales.getNulos()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeCrg(dosDecimales((totales.getCrg()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeTotal(dosDecimales((totales.getTotal()*100.00)/totales.getListaNominal()).doubleValue());
		totales.setPorcentajeCandidato1(dosDecimales((totales.getCandidato1()*100.00)/totales.getTotal()).doubleValue());
		totales.setPorcentajeCandidato2(dosDecimales((totales.getCandidato2()*100.00)/totales.getTotal()).doubleValue());
		
		resultados.add(totales);
		return resultados;
	}
	
	public ReportePMunicipalDTO calcularReporteResultados(Long idEntidad, Long idDistrito, Long idMunicipio, String municipio, Long idEleccion) {
		Long listaNominal = 1000L;
		Long pan = votosRepository.getVotosByDistritoAndMunicipioAndPartido(idEntidad, idDistrito, idMunicipio, idEleccion, 1L);
		Long pri = votosRepository.getVotosByDistritoAndMunicipioAndPartido(idEntidad, idDistrito, idMunicipio, idEleccion, 2L);;
		Long prd = 50L;
		Long pvem = 42L;
		Long pt = 35L;
		Long mc = 53L;
		Long morena = 35L;
		Long pes = 53L;
		Long rsp = 35L;
		Long fuerzaM = 35L;
		Long panal = 35L;
		Long nulos = 53L;
		Long crg = 50L;
		Long candidato1 = 20L;
		Long candidato2 = 60L;
		
		Long total = pan + pri + prd + pvem + pt + mc + morena + pes + rsp + fuerzaM + panal;
		
		ReportePMunicipalDTO dto = new ReportePMunicipalDTO();
		
		dto.setIdFederal(idDistrito);
		dto.setMunicipio(municipio);
		dto.setListaNominal(listaNominal);
		dto.setTotal(total);
		double porcentajeTotal = (total * 100.0) / total ;
		dto.setPorcentajeTotal(dosDecimales(porcentajeTotal).doubleValue());
		dto.setPAN(pan);
		double porcentajePartido1 = (pan * 100.0) / total ;
		dto.setPorcentajePAN(dosDecimales(porcentajePartido1).doubleValue());
		dto.setPRI(pri);
		double porcentajePartido2 = (pri * 100.0) / total ;
		dto.setPorcentajePRI(dosDecimales(porcentajePartido2).doubleValue());
		dto.setPRD(prd);
		double porcentajePartido3 = (prd * 100.0) / total ;
		dto.setPorcentajePRD(dosDecimales(porcentajePartido3).doubleValue());
		dto.setPVEM(pvem);
		double porcentajePartido4 = (pvem * 100.0) / total ;
		dto.setPorcentajePVEM(dosDecimales(porcentajePartido4).doubleValue());
		dto.setPT(pt);
		double porcentajePartido5 = (pt * 100.0) / total ;
		dto.setPorcentajePT(dosDecimales(porcentajePartido5).doubleValue());
		dto.setMC(mc);
		double porcentajePartido6 = (mc * 100.0) / total ;
		dto.setPorcentajeMC(dosDecimales(porcentajePartido6).doubleValue());
		dto.setMORENA(morena);
		double porcentajePartido7 = (morena * 100.0) / total ;
		dto.setPorcentajeMORENA(dosDecimales(porcentajePartido7).doubleValue());
		dto.setPES(pes);
		double porcentajePartido8 = (pes * 100.0) / total ;
		dto.setPorcentajePES(dosDecimales(porcentajePartido8).doubleValue());
		dto.setRSP(rsp);
		double porcentajePartido9 = (rsp * 100.0) / total ;
		dto.setPorcentajeRSP(dosDecimales(porcentajePartido9).doubleValue());
		dto.setFUERZAMEXICO(fuerzaM);
		double porcentajePartido10 = (fuerzaM * 100.0) / total ;
		dto.setPorcentajeFUERZAMEXICO(dosDecimales(porcentajePartido10).doubleValue());
		dto.setPANAL(panal);
		double porcentajePartido11 = (panal * 100.0) / total ;
		dto.setPorcentajePANAL(dosDecimales(porcentajePartido11).doubleValue());
		dto.setNulos(nulos);
		double porcentajeNull = (nulos * 100.0) / total ;
		dto.setPorcentajeNulos(dosDecimales(porcentajeNull).doubleValue());
		dto.setCrg(crg);
		double porcentajeCrg = (crg * 100.0) / total ;
		dto.setPorcentajeCrg(dosDecimales(porcentajeCrg).doubleValue());
		dto.setCandidato1(candidato1);
		double porcentajeCandidato1 = (candidato1 * 100.0) / total ;
		dto.setPorcentajeCandidato1(dosDecimales(porcentajeCandidato1).doubleValue());
		dto.setCandidato2(candidato2);
		double porcentajeCandidato2 = (candidato2 * 100.0) / total ;
		dto.setPorcentajeCandidato2(dosDecimales(porcentajeCandidato2).doubleValue());
				
		return dto;
	}

	@Override
	public void getReportePresidenteMunicipalDownload(HttpServletResponse response, Long idUsuario, Long perfil, Long idEleccion) throws ConvencidosException, IOException {
		if (perfil >= PERFIL_ESTATAL && perfil < PERFIL_RC) {
			
			setNameFile(response, CSV_REPORTE_RESULTADOS_PMUNICIPAL);
			List<ReportePMunicipalDTO> dto = getReportePresidenteMunicipal(idUsuario, idEleccion);
					
			String[] header = { "idFederal", "municipio", "listaNominal", "PAN", "porcentajePAN", "PRI", "porcentajePRI", "PRD", "porcentajePRD", "PVEM", "porcentajePVEM", "PT",
					"porcentajePT", "MC", "porcentajeMC", "MORENA", "porcentajeMORENA", "PES", "porcentajePES", "RSP", "porcentajeRSP", "FUERZAMEXICO", "porcentajeFUERZAMEXICO",
					"PANAL", "porcentajePANAL", "nulos", "porcentajeNulos", "crg", "porcentajeCrg", "total", "porcentajeTotal", "candidato1", "porcentajeCandidato1", "candidato2",
					"porcentajeCandidato2" };
	
			setWriterFile(response, dto, header);
		} else {
			throw new ConvencidosException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}
}
