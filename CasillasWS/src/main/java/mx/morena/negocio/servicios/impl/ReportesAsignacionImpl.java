package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.morena.negocio.dto.ReporteAsignacionDistritalDTO;
import mx.morena.negocio.dto.ReporteCrgDTO;
import mx.morena.negocio.dto.ReporteAsignacionEstatalDTO;
import mx.morena.negocio.dto.ReporteAsignacionRgDTO;
import mx.morena.negocio.dto.ReporteCrgAsignadoDTO;
import mx.morena.negocio.exception.RepresentanteException;
import mx.morena.negocio.dto.ReporteRCDTO;
import mx.morena.negocio.dto.ReporteRgDTO;
import mx.morena.negocio.servicios.IReportesAsignacionService;
import mx.morena.persistencia.entidad.DistritoFederal;
import mx.morena.persistencia.entidad.ReporteAsignacionRg;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IDistritoFederalRepository;
import mx.morena.persistencia.repository.IRepresentanteRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReportesAsignacionImpl extends MasterService implements IReportesAsignacionService{
	
	@Autowired
	private IRepresentanteRepository representanteRepository;

	@Autowired
	private IDistritoFederalRepository distritoRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public List<ReporteAsignacionDistritalDTO> getRepAsignacionDistrital(long idUsuario) throws RepresentanteException {
		
		List<ReporteAsignacionDistritalDTO> lstDto = new ArrayList<ReporteAsignacionDistritalDTO>();
		ReporteAsignacionDistritalDTO dto = null;
		ReporteAsignacionDistritalDTO total = new ReporteAsignacionDistritalDTO();
		
		total.setMetaCrg(0L);
		total.setAvanceCapturadoCrg(0L);
		total.setAvanceAsignadoCrg(0L);
		total.setPorcentajeAvanceCrg(0.0);
		total.setMetaRg(0L);
		total.setAvanceCapturadoRg(0L);
		total.setAvanceAsignadoRg(0L);
		total.setPorcentajeAvanceRg(0.0);
		total.setMetaRc(0L);
		total.setAvanceCapturadoRc(0L);
		total.setAvanceAsignadoRc(0L);
		total.setPorcentajeAvanceRc(0.0);

		Usuario usuario = usuarioRepository.findById(idUsuario);
		long idDistrito = usuario.getFederal();
		long perfilUsuario = usuario.getPerfil();
		
		System.out.println("distrito " + idDistrito);
		System.out.println("perfil " + perfilUsuario);
		
		
		dto = new ReporteAsignacionDistritalDTO();
		
		Long crgCapturado = representanteRepository.getByTipo(PERFIL_CRG, idDistrito, perfilUsuario);
		Long rgCapturado = representanteRepository.getByTipo(PERFIL_RG, idDistrito, perfilUsuario);
		Long rcCapturado = representanteRepository.getByTipo(PERFIL_RC, idDistrito, perfilUsuario);

		Long crgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_CRG, idDistrito, perfilUsuario);
		Long rgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG, idDistrito, perfilUsuario);
		Long rcAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RC, idDistrito, perfilUsuario);

		// CRG
		dto.setMetaCrg(50L);
		dto.setAvanceCapturadoCrg(crgCapturado); // 7
		dto.setAvanceAsignadoCrg(crgAsignado);
		double avanceCrg = (crgAsignado * 100.0) / dto.getMetaCrg();
		dto.setPorcentajeAvanceCrg(dosDecimales(avanceCrg).doubleValue());
		// RG
		dto.setMetaRg(50L);
		dto.setAvanceCapturadoRg(rgCapturado); // 8
		dto.setAvanceAsignadoRg(rgAsignado);
		double avanceRg = (rgAsignado * 100.0) / dto.getMetaRg();
		dto.setPorcentajeAvanceRg(dosDecimales(avanceRg).doubleValue());
		// RC
		dto.setMetaRc(50L);
		dto.setAvanceCapturadoRc(rcCapturado); // 9
		dto.setAvanceAsignadoRc(rcAsignado);
		double avanceRc = (rcAsignado * 100.0) / dto.getMetaRc();
		dto.setPorcentajeAvanceRc(dosDecimales(avanceRc).doubleValue());

		lstDto.add(dto);

		return lstDto;
	}

	@Override
	public void getReporteDistritalDownload(HttpServletResponse response, long perfil)
			throws RepresentanteException, IOException {

		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_ASIGN_DISTRITAL);

		List<ReporteAsignacionDistritalDTO> asignacionDTOs = getRepAsignacionDistrital(perfil);

		// Nombre y orden de los encabezados en el excel
		String[] header = {"metaCrg", "avanceCapturadoCrg", "avanceAsignadoCrg", "porcentajeAvanceCrg", "metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg", "metaRc", "avanceCapturadoRc",
				"avanceAsignadoRc", "porcentajeAvanceRc"};

		setWriterFile(response, asignacionDTOs, header);

	}

	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

	@Override
	public List<ReporteRCDTO> getReporteRc(Long perfil, Long idUsuario) throws RepresentanteException {
if(perfil == PERFIL_ESTATAL) {
			
			List<ReporteRCDTO> lstRc = new ArrayList<ReporteRCDTO>();
			ReporteRCDTO dto = null;
			
			Usuario usuario = usuarioRepository.findById(idUsuario);
			long idDistrito = usuario.getFederal();
			long perfilUsuario = usuario.getPerfil();
			
			
			Long countRcCapturados = representanteRepository.getByTipo(PERFIL_RC, idDistrito, perfilUsuario);
			Long countRcAsigndos = representanteRepository.getRepAsignadoByTipo(PERFIL_RC, idDistrito, perfilUsuario);
			
				dto = new ReporteRCDTO();
				
				dto.setMetaRc(45L);;
				dto.setAvanceCapturadoRc(countRcCapturados);
				dto.setAvanceAsignadoRc(countRcAsigndos);
				double sub1 = (dto.getAvanceAsignadoRc() * 100.00)/ dto.getMetaRc();
				dto.setPorcentajeAvanceRc(dosDecimales(sub1).doubleValue());
				
				lstRc.add(dto);

			return lstRc;
		}else {
			throw new RepresentanteException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	@Override
	public void getReporteRcDownload(HttpServletResponse response, Long perfil, Long idUsuario)
			throws RepresentanteException, IOException {
		if(perfil == PERFIL_ESTATAL ) {
			// Asignacion de nombre al archivo CSV
			setNameFile(response, CSV_ASIGN_RC);

			List<ReporteRCDTO> rcDTOs = getReporteRc(perfil, idUsuario);

			//Nombre y orden de los encabezados en el excel
			String[] header = { "metaRc", "avanceCapturadoRc", "avanceAsignadoRc", "porcentajeAvanceRc"};

			setWriterFile(response, rcDTOs, header);
			
		} else {
			throw new RepresentanteException("No cuenta con permisos suficientes para descargar el reporte", 401);
		}
		
	}

	@Override
	public List<ReporteCrgDTO> getReporteCrgDv(Long idUsuario, Long perfil) throws RepresentanteException {
		if(perfil == PERFIL_ESTATAL || perfil == PERFIL_FEDERAL || perfil == PERFIL_CRG ) {
			List<ReporteCrgDTO> lstDto = new ArrayList<ReporteCrgDTO>();
			ReporteCrgDTO dto = new ReporteCrgDTO();
			
			Usuario usuario = usuarioRepository.findById(idUsuario);
			long idDistrito = usuario.getFederal();
			long perfilUsuario = usuario.getPerfil();
	
			Long countRepRgCapturado = representanteRepository.getByTipo(PERFIL_RG, idDistrito, perfilUsuario);
			Long countRepRgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG, idDistrito, perfilUsuario);
			Long countRepRcCapturado = representanteRepository.getByTipo(PERFIL_RC, idDistrito, perfilUsuario);
			Long countRepRcAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RC, idDistrito, perfilUsuario);
	
			dto.setMetaRg(63L);
			dto.setAvanceCapturadoRg(countRepRgCapturado);
			dto.setAvanceAsignadoRg(countRepRgAsignado);
			double porAvanceRg = (double) dto.getAvanceAsignadoRg() / dto.getMetaRg() * 100.00;
			dto.setPorcentajeAvanceRg(dosDecimales(porAvanceRg).doubleValue());
	
			dto.setMetaRc(30L);
			dto.setAvanceCapturadoRc(countRepRcCapturado);
			dto.setAvanceAsignadoRc(countRepRcAsignado);
			double porAvanceRc = (double) dto.getAvanceAsignadoRc() / dto.getMetaRc() * 100.00;
			dto.setPorcentajeAvanceRc(dosDecimales(porAvanceRc).doubleValue());
	
			lstDto.add(dto);
	
			return lstDto;
		} else {
			throw new RepresentanteException("No cuenta con permisos suficientes", 401);
		}
	}

	@Override
	public void getReporteCrgDownload(HttpServletResponse response, long perfil, long idUsuario) throws RepresentanteException, IOException {
		setNameFile(response, CSV_ASIGN_CRG);

		List<ReporteCrgDTO> crgDTOs = getReporteCrgDv(idUsuario, perfil);

		String[] header = { "metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg", "metaRc",
							"avanceCapturadoRc", "avanceAsignadoRc", "porcentajeAvanceRc" };

		setWriterFile(response, crgDTOs, header);
	}

	@Override
	public List<ReporteAsignacionEstatalDTO> getReporteAsignacionEstatal(long idUsuario)
			throws RepresentanteException, IOException {

		List<ReporteAsignacionEstatalDTO> lstDto = new ArrayList<ReporteAsignacionEstatalDTO>();
		List<DistritoFederal> lstSeccion = null;
		ReporteAsignacionEstatalDTO dto = null;
		ReporteAsignacionEstatalDTO total = new ReporteAsignacionEstatalDTO();

		total.setNumero("TOTAL: ");
		total.setIdDistrito(null);
		total.setMetaRFederal(0L);
		total.setAvanceCapturadoRFederal(0L);
		total.setAvanceAsignadoRfederal(0L);
		total.setPorcentajeAvanceRFederal(0.0);
		total.setMetaCrg(0L);
		total.setAvanceCapturadoCrg(0L);
		total.setAvanceAsignadoCrg(0L);
		total.setPorcentajeAvanceCrg(0.0);
		total.setMetaRg(0L);
		total.setAvanceCapturadoRg(0L);
		total.setAvanceAsignadoRg(0L);
		total.setPorcentajeAvanceRg(0.0);
		total.setMetaRc(0L);
		total.setAvanceCapturadoRc(0L);
		total.setAvanceAsignadoRc(0L);
		total.setPorcentajeAvanceRc(0.0);
		
		Usuario usuario = usuarioRepository.findById(idUsuario);

		lstSeccion = distritoRepository.findByEntidad(usuario.getEntidad());

		for (DistritoFederal df : lstSeccion) {
			dto = new ReporteAsignacionEstatalDTO();
			Long fedCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_FEDERAL);
			Long crgCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_CRG);
			Long rgCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_RG);
			Long rcCapturado = representanteRepository.getByDistritoAndTipo(df.getId(), PERFIL_RC);

			Long fedAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_FEDERAL);
			Long crgAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_CRG);
			Long rgAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_RG);
			Long rcAsignado = representanteRepository.getRepAsignadoByDistrito(df.getId(), PERFIL_RC);

			dto.setNumero(df.getId().toString());
			dto.setIdDistrito(df.getId());

			// FEDERAL
			dto.setMetaRFederal(50L);
			dto.setAvanceCapturadoRFederal(fedCapturado);
			dto.setAvanceAsignadoRfederal(fedAsignado);
			double avanceFederal = (fedAsignado * 100.0) / dto.getMetaRFederal();
			dto.setPorcentajeAvanceRFederal(dosDecimales(avanceFederal).doubleValue());
			// CRG
			dto.setMetaCrg(50L);
			dto.setAvanceCapturadoCrg(crgCapturado); // 7
			dto.setAvanceAsignadoCrg(crgAsignado);
			double avanceCrg = (crgAsignado * 100.0) / dto.getMetaCrg();
			dto.setPorcentajeAvanceCrg(dosDecimales(avanceCrg).doubleValue());
			// RG
			dto.setMetaRg(50L);
			dto.setAvanceCapturadoRg(rgCapturado); // 8
			dto.setAvanceAsignadoRg(rgAsignado);
			double avanceRg = (rgAsignado * 100.0) / dto.getMetaRg();
			dto.setPorcentajeAvanceRg(dosDecimales(avanceRg).doubleValue());
			// RC
			dto.setMetaRc(50L);
			dto.setAvanceCapturadoRc(rcCapturado); // 9
			dto.setAvanceAsignadoRc(rcAsignado);
			double avanceRc = (rcAsignado * 100.0) / dto.getMetaRc();
			dto.setPorcentajeAvanceRc(dosDecimales(avanceRc).doubleValue());

			lstDto.add(dto);

			// TOTALES
			total.setMetaRFederal(total.getMetaRFederal() + dto.getMetaRFederal());
			total.setAvanceCapturadoRFederal(total.getAvanceCapturadoRFederal() + fedCapturado);
			total.setAvanceAsignadoRfederal(total.getAvanceAsignadoRfederal() + fedAsignado);

			total.setMetaCrg(total.getMetaCrg() + dto.getMetaCrg());
			total.setAvanceCapturadoCrg(total.getAvanceCapturadoCrg() + crgCapturado);
			total.setAvanceAsignadoCrg(total.getAvanceAsignadoCrg() + crgAsignado);

			total.setMetaRg(total.getMetaRg() + dto.getMetaRg());
			total.setAvanceCapturadoRg(total.getAvanceCapturadoRg() + rgCapturado);
			total.setAvanceAsignadoRg(total.getAvanceCapturadoRg() + rgAsignado);

			total.setMetaRc(total.getMetaRc() + dto.getMetaRc());
			total.setAvanceCapturadoRc(total.getAvanceCapturadoRc() + rcCapturado);
			total.setAvanceAsignadoRc(total.getAvanceAsignadoRc() + rcAsignado);

		}
		total.setPorcentajeAvanceRFederal(
				dosDecimales((total.getAvanceAsignadoRfederal() * 100.0) / total.getMetaRFederal()).doubleValue());
		total.setPorcentajeAvanceCrg(
				dosDecimales((total.getAvanceAsignadoCrg() * 100.0) / total.getMetaCrg()).doubleValue());
		total.setPorcentajeAvanceRg(
				dosDecimales((total.getAvanceAsignadoRg() * 100.0) / total.getMetaRg()).doubleValue());
		total.setPorcentajeAvanceRc(
				dosDecimales((total.getAvanceAsignadoRc() * 100.0) / total.getMetaRc()).doubleValue());

		lstDto.add(total);

		return lstDto;
	}

	@Override
	public void getReporteEstatalDownload(HttpServletResponse response, long perfil)
			throws RepresentanteException, IOException {
		// Asignacion de nombre al archivo CSV
		setNameFile(response, CSV_ASIGN_ESTATAL);

		List<ReporteAsignacionEstatalDTO> asignacionDTOs = getReporteAsignacionEstatal(perfil); 

		// Nombre y orden de los encabezados en el excel
		String[] header = {"numero", "idDistrito", "metaRFederal", "avanceCapturadoRFederal", "avanceAsignadoRfederal", "porcentajeAvanceRFederal", 
				"metaCrg", "avanceCapturadoCrg", "avanceAsignadoCrg", "porcentajeAvanceCrg", 
				"metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg",
				"metaRc", "avanceCapturadoRc", "avanceAsignadoRc", "porcentajeAvanceRc" };

		setWriterFile(response, asignacionDTOs, header);
	}

	@Override
	public List<ReporteRgDTO> getReporteRg(Long perfil, Long idUsuario) throws RepresentanteException {
		List<ReporteRgDTO> lstRgDto = new ArrayList<ReporteRgDTO>();
		ReporteRgDTO dto = new ReporteRgDTO();
		
		Usuario usuario = usuarioRepository.findById(idUsuario);
		long idDistrito = usuario.getFederal();
		long perfilUsuario = usuario.getPerfil();

		Long countRgCapturado = representanteRepository.getByTipo(PERFIL_RG, idDistrito, perfilUsuario);
		Long countRgAsignado = representanteRepository.getRepAsignadoByTipo(PERFIL_RG, idDistrito, perfilUsuario);
		
		dto = new ReporteRgDTO();
		
		dto.setMetaRg(45L);;
		dto.setAvanceCapturadoRg(countRgCapturado);
		dto.setAvanceAsignadoRg(countRgAsignado);
		double sub1 = (dto.getAvanceAsignadoRg() * 100.00)/ dto.getMetaRg();
		dto.setPorcentajeAvanceRg(dosDecimales(sub1).doubleValue());
		
		lstRgDto.add(dto);

	return lstRgDto;
	
	}

	@Override
	public void getReporteRgDownload(HttpServletResponse response, Long perfil, Long idUsuario)
			throws RepresentanteException, IOException {
		setNameFile(response, CSV_RG);
		List<ReporteCrgDTO> crgDTOs = getReporteCrgDv(perfil, idUsuario);

		String[] header = { "metaRg", "avanceCapturadoRg", "avanceAsignadoRg", "porcentajeAvanceRg"};

		setWriterFile(response, crgDTOs, header);
		
	}

	@Override
	public List<ReporteAsignacionRgDTO> getReporteRgAsignado(Long perfil, Long idUsuario) throws RepresentanteException {
		List<ReporteAsignacionRgDTO> lstAsigRgDto = new ArrayList<ReporteAsignacionRgDTO>();
		List<ReporteAsignacionRg> lstAsigRg = null;
		ReporteAsignacionRgDTO dto = new ReporteAsignacionRgDTO();
		
		Usuario usuario = usuarioRepository.findById(idUsuario);
		long idDistrito = usuario.getFederal();
//		long perfilUsuario = usuario.getPerfil();

		if(perfil == PERFIL_ESTATAL) {
			lstAsigRg = representanteRepository.getReporteRgEstatal();
		}else if(perfil != PERFIL_RC) {
			lstAsigRg = representanteRepository.getReporteRg(idDistrito);
		}else {
			throw new RepresentanteException("No cuenta con permisos suficientes para consultar el reporte", 401);
		}
		
		for (ReporteAsignacionRg rg : lstAsigRg) {
			
			Long propuesto1 = representanteRepository.getCargo(rg.getIdDistrito(), 1L);
			Long suplente1 = representanteRepository.getCargo(rg.getIdDistrito(), 2L);
			Long propuesto2 = representanteRepository.getCargo(rg.getIdDistrito(), 3L);
			Long suplente2 = representanteRepository.getCargo(rg.getIdDistrito(), 4L);
		
			dto = new ReporteAsignacionRgDTO();
			
			dto.setIdDistrito(rg.getIdDistrito());
			dto.setIdLocal(rg.getIdLocal());
			dto.setMunicipio(rg.getMunicipio());
			dto.setIdCasilla(rg.getIdCasilla());
			dto.setIdRuta(rg.getIdRuta());
			dto.setPropietarioUno(propuesto1);
			dto.setPropietarioDos(propuesto2);
			dto.setSuplenteUno(suplente1);
			dto.setSuplenteDos(suplente2);
			
			lstAsigRgDto.add(dto);
		}

	return lstAsigRgDto;
	}

	@Override
	public void getReporteRgAsignadoDownload(HttpServletResponse response, Long perfil, Long idUsuario)
			throws RepresentanteException, IOException {
		if(perfil != PERFIL_RC) {
			setNameFile(response, CSV_ASIGN_RG);
			List<ReporteAsignacionRgDTO> rgDTOs = getReporteRgAsignado(perfil, idUsuario);
	
			String[] header = { "idDistrito", "idLocal", "municipio", "idRuta", "idCasilla", "propietarioUno", "propietarioDos", "suplenteUno", "suplenteDos"};
	
			setWriterFile(response, rgDTOs, header);
		}else {
			throw new RepresentanteException("No cuenta con permisos suficientes para descargar el reporte", 401);
		}
		
	}
	
	@Override
	public List<ReporteCrgAsignadoDTO> getReporteCrgAsignado(Long idUsuario) throws RepresentanteException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long idEntidad = usuario.getEntidad();
		Long idDistritoF = usuario.getFederal();
		Long idMunicipio = usuario.getMunicipio();
		Long idDistritoLocal = usuario.getDistritoLocal();
		
		Long perfil = usuario.getPerfil();
		List<ReporteCrgAsignadoDTO> lstDto = new ArrayList<ReporteCrgAsignadoDTO>();
		List <ReporteAsignacionRg> datos = new ArrayList<ReporteAsignacionRg>();
		
		if (perfil == PERFIL_ESTATAL) {
			datos = representanteRepository.getDistritosAndMunicipios(idEntidad, null, null, null);
			
		} else if (perfil == PERFIL_FEDERAL) {
			datos = representanteRepository.getDistritosAndMunicipios(idEntidad, idDistritoF, null, null);

		} else if (perfil == PERFIL_LOCAL) {
			datos = representanteRepository.getDistritosAndMunicipios(idEntidad, idDistritoF, idDistritoLocal, null);
			
		} else if (perfil == PERFIL_MUNICIPAL || perfil == PERFIL_CRG || perfil == PERFIL_RG) {
			datos = representanteRepository.getDistritosAndMunicipios(idEntidad, idDistritoF, idDistritoLocal, idMunicipio);
			
		} else {
			throw new RepresentanteException("No cuenta con permisos suficientes", 401);
		}
		
		ReporteCrgAsignadoDTO reporteDto = null;
		
		if (datos != null) {
			for (ReporteAsignacionRg dato : datos) {
				reporteDto = new ReporteCrgAsignadoDTO();
				//IdRuta trae el valor de idMunicipio
				reporteDto = calcularReporteCrgAsignado(idEntidad, dato.getIdDistrito(), dato.getIdLocal(),
							dato.getIdRuta(), dato.getMunicipio(), dato.getIdCasilla());
				
				lstDto.add(reporteDto);
			}
		} else {
			throw new RepresentanteException("No se encontraron datos", 404);
		}
		
		return lstDto;
	}
	
	public ReporteCrgAsignadoDTO calcularReporteCrgAsignado(Long idEntidad, Long idDisitritoF, Long idDistritoL, Long idMunicipio, String municipio, Long casilla) {
		ReporteCrgAsignadoDTO dto = new ReporteCrgAsignadoDTO();
		
		Long crg = representanteRepository.getRepCrgAsignadoByDistrito(idEntidad, idDisitritoF, idMunicipio, PERFIL_CRG);
		List<Long> rutas = representanteRepository.getRutasByCrgAsignadoAndDistrito(idEntidad, idDisitritoF, idDistritoL, idMunicipio, PERFIL_CRG);
		Long propietario1 = representanteRepository.getCargoByDistritoAndMunicipio(idDisitritoF, idDistritoL, idMunicipio, 1L);
		Long suplente1 = representanteRepository.getCargoByDistritoAndMunicipio(idDisitritoF, idDistritoL, idMunicipio, 2L);
		Long propietario2 = representanteRepository.getCargoByDistritoAndMunicipio(idDisitritoF, idDistritoL, idMunicipio, 3L);
		Long suplente2 = representanteRepository.getCargoByDistritoAndMunicipio(idDisitritoF, idDistritoL, idMunicipio, 4L);
		
		if (rutas == null) {
			rutas = (Arrays.asList(0L));
		}
		
		dto.setIdDistrito(idDisitritoF);
		dto.setIdLocal(idDistritoL);
		dto.setMunicipio(municipio);
		dto.setRutas(rutas);
		dto.setCrg(crg);
		dto.setCasillas(casilla);
		dto.setPropietarioUno(propietario1);
		dto.setPropietarioDos(propietario2);
		dto.setSuplenteUno(suplente1);
		dto.setSuplenteDos(suplente2);
		
		return dto;
	}

	@Override
	public void getReporteCrgAsignadoDownload(HttpServletResponse response, long perfil, long idUsuario) throws RepresentanteException, IOException {
		setNameFile(response, CSV_CRG_ASIGNADO);
		List<ReporteCrgAsignadoDTO> crgDTOs = getReporteCrgAsignado(idUsuario);

		String[] header = { "idDistrito", "idLocal", "municipio", "crg", "rutas", "casillas", "propietarioUno", "propietarioDos", "suplenteUno", "suplenteDos"};

		setWriterFile(response, crgDTOs, header);
	}

}
