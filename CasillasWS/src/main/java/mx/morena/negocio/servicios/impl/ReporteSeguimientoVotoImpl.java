package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.morena.negocio.dto.ReporteSeguimintoVotoDTO;
import mx.morena.negocio.dto.SeguimientoVotoDTO;
import mx.morena.negocio.exception.SeguimientoVotoException;
import mx.morena.negocio.servicios.IReporteSeguimientoVotoService;
import mx.morena.persistencia.entidad.Convencidos;
import mx.morena.persistencia.entidad.SeccionElectoral;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.ICasillaRepository;
import mx.morena.persistencia.repository.IConvencidosRepository;
import mx.morena.persistencia.repository.ISeccionElectoralRepository;
import mx.morena.persistencia.repository.ISeguimientoVotoRepository;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class ReporteSeguimientoVotoImpl extends MasterService implements IReporteSeguimientoVotoService {

	@Autowired
	private ISeguimientoVotoRepository seguimientoRepository;

	@Autowired
	private ISeccionElectoralRepository seccionRepository;

	@Autowired
	private ICasillaRepository casillasRepository;

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Autowired
	private IConvencidosRepository convencidoRepository;

	@Override
	public List<ReporteSeguimintoVotoDTO> getSeguimeitoVoto(Long perfil, Long usuario) throws SeguimientoVotoException {
		if (perfil != PERFIL_RG) {

			List<ReporteSeguimintoVotoDTO> lstvoto = new ArrayList<ReporteSeguimintoVotoDTO>();
			List<SeccionElectoral> lstSeccion = null;
			ReporteSeguimintoVotoDTO dto = null;
			ReporteSeguimintoVotoDTO totales = new ReporteSeguimintoVotoDTO();
			totales.setIdDistrito(0L);
			totales.setDistrito("Totales");
			totales.setSecciones(0L);
			totales.setUrbanas(0L);
			totales.setNoUrbanas(0L);
			totales.setMetaConvencidos(0L);
			totales.setTotalConvencidos(0L);
			totales.setPorcentajeAvanceConvencidos(0.00);
			totales.setNotificado(0L);
			totales.setPorcentajeAvanceNotificado(0.00);

			lstSeccion = seccionRepository.getDistritos();

			for (SeccionElectoral seccion : lstSeccion) {

				Long countSecciones = seccionRepository.getSecciones(seccion.getDistritoId());
				Long urbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), URBANAS);
				Long noUrbanas = casillasRepository.countByDistritoAndTipologia(seccion.getDistritoId(), NO_URBANAS);
				Long convencidos = seguimientoRepository.countByLocalAndTipo(seccion.getDistritoId(), CONVENCIDO);
				Long metaCon = 45L;
				Long notificados = seguimientoRepository.countNotificados(seccion.getDistritoId());
				dto = new ReporteSeguimintoVotoDTO();

				dto.setIdDistrito(seccion.getDistritoId());
				dto.setDistrito(seccion.getDistritoId() + "-" + seccion.getNombreDistrito());
				dto.setSecciones(countSecciones);
				dto.setUrbanas(urbanas);
				dto.setNoUrbanas(noUrbanas);
				dto.setMetaConvencidos(metaCon);
				dto.setTotalConvencidos(convencidos);
				double sub2 = (convencidos * 100.00) / dto.getMetaConvencidos();
				dto.setPorcentajeAvanceConvencidos(dosDecimales(sub2).doubleValue());
				dto.setNotificado(notificados);
				double sub1 = (notificados * 100.00) / dto.getTotalConvencidos();
				dto.setPorcentajeAvanceNotificado(dosDecimales(sub1).doubleValue());

				totales.setSecciones(totales.getSecciones() + countSecciones);
				totales.setUrbanas(totales.getUrbanas() + urbanas);
				totales.setNoUrbanas(totales.getNoUrbanas() + noUrbanas);
				totales.setMetaConvencidos(totales.getMetaConvencidos() + dto.getMetaConvencidos());
				totales.setTotalConvencidos(totales.getTotalConvencidos() + convencidos);
				totales.setNotificado(totales.getNotificado() + dto.getNotificado());

				lstvoto.add(dto);
			}

			totales.setPorcentajeAvanceConvencidos(
					dosDecimales((totales.getTotalConvencidos() * 100.00) / totales.getMetaConvencidos())
							.doubleValue());
			totales.setPorcentajeAvanceNotificado(
					dosDecimales((totales.getTotalConvencidos() * 100.00) / totales.getMetaConvencidos())
							.doubleValue());

			lstvoto.add(totales);

			return lstvoto;
		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para consultar el reporte", 401);
		}
	}

	public BigDecimal dosDecimales(double numero) {

		BigDecimal bd = new BigDecimal(numero);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd;

	}

	@Override
	public List<SeguimientoVotoDTO> getCasillaBySeccion(long idPerfil, Long idSeccion) throws SeguimientoVotoException {

		if (idPerfil == PERFIL_BRIGADISTA) {
			List<Convencidos> conven = new ArrayList<Convencidos>();

			List<SeguimientoVotoDTO> reporteDto = new ArrayList<SeguimientoVotoDTO>();
			SeguimientoVotoDTO dto = null;
			SeguimientoVotoDTO totales = new SeguimientoVotoDTO();

			totales.setNombre("");
			totales.setApellidoPaterno("");
			totales.setApellidoMaterno("");
			totales.setColonia("");
			totales.setReferencia("");
			totales.setIsNotificado(false);

			if (idSeccion != null) {

				conven = seguimientoRepository.getConvencidos(idSeccion);
				reporteDto = conven.stream().map(this::convertirADto).collect(Collectors.toList());
				
				return reporteDto;

			} else {
				throw new SeguimientoVotoException("Favor de seleccionar una seccion", 401);
			}

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes", 401);
		}

		
	}

	public SeguimientoVotoDTO convertirADto(Convencidos con) {
		SeguimientoVotoDTO convencidosDto = new SeguimientoVotoDTO();

		convencidosDto.setNombre(con.getNombre());
		convencidosDto.setApellidoPaterno(con.getApellidoPaterno());
		convencidosDto.setApellidoMaterno(con.getApellidoMaterno());
		convencidosDto.setColonia(con.getColonia_casilla());
		convencidosDto.setReferencia(con.getReferencia_casilla());
		convencidosDto.setIsNotificado(con.getIsNotificado());

		return convencidosDto;
	}

	@Override
	public void getReporteSeguimientoVotoDownload(HttpServletResponse response, Long perfil, Long usuario)
			throws SeguimientoVotoException, IOException {
		if (perfil != PERFIL_RG) {
			setNameFile(response, CSV_SEGUIMIENTOVOTO);

			List<ReporteSeguimintoVotoDTO> seguimientoDTOs = getSeguimeitoVoto(perfil, usuario);

			String[] header = { "idDistrito", "distrito", "secciones", "urbanas", "noUrbanas", "metaConvencidos",
					"totalConvencidos", "porcentajeAvanceConvencidos", "notificado", "porcentajeAvanceNotificado" };

			setWriterFile(response, seguimientoDTOs, header);

		} else {
			throw new SeguimientoVotoException("No cuenta con los permisos suficientes para descargr el reporte", 401);
		}
	}

	@Override
	public String marcarConvencido(Long idUsuario, Long idConvencido, Boolean isNotificado)
			throws SeguimientoVotoException {
		Usuario usuario = usuarioRepository.findById(idUsuario);
		Long perfil = usuario.getPerfil();
		if (perfil == PERFIL_BRIGADISTA) {
			Convencidos convencido = convencidoRepository.getByIdAndTipoAndIsNotificado(idConvencido, CONVENCIDO,
					isNotificado);
			if (convencido != null) {
				System.out.println(convencido.getId());
				System.out.println(convencido.getIsNotificado());
			}
		}

		return null;
	}
}
