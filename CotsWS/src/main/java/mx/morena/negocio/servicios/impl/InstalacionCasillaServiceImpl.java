package mx.morena.negocio.servicios.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.morena.negocio.dto.IncidenciasCasillasDTO;
import mx.morena.negocio.dto.InstalacionCasillasDTO;
import mx.morena.negocio.dto.listIncidenciasDTO;
import mx.morena.negocio.exception.CotException;
import mx.morena.negocio.servicios.IInstalacionCasillaService;
import mx.morena.persistencia.entidad.IncidenciasCasillas;
import mx.morena.persistencia.entidad.InstalacionCasilla;
import mx.morena.persistencia.entidad.ReporteCasilla;
import mx.morena.persistencia.entidad.Usuario;
import mx.morena.persistencia.repository.IIncidenciasCasillasRepository;
import mx.morena.persistencia.repository.IInstalacionCasillasRepository;
import mx.morena.persistencia.repository.IReporteCasillas;
import mx.morena.persistencia.repository.IUsuarioRepository;
import mx.morena.security.servicio.MasterService;

@Service
public class InstalacionCasillaServiceImpl extends MasterService implements IInstalacionCasillaService {
	
	@Autowired
	private IInstalacionCasillasRepository  casillasRepository;
	
	@Autowired
	private IIncidenciasCasillasRepository incidenciasRepository;
	
	@Autowired
	private IReporteCasillas reporteRepository;
	
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	@Transactional(rollbackFor={CotException.class})
	public Long saveInstalacionCasilla(InstalacionCasillasDTO dto, long perfil, long usuario)
			throws CotException, IOException {
		
		if(perfil == PERFIL_RG || perfil == PERFIL_RC ) {
			
			InstalacionCasilla ic = new InstalacionCasilla();
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			
			ic.setIdCasilla(dto.getIdCasilla());
			ic.setHoraInstalacion(dto.getHoraInstalacion());
//			ic.setHoraInstalacion(df.format(dto.getHoraInstalacion()));
			ic.setLlegaronFuncionarios(dto.getLlegaronFuncionarios());
			ic.setFuncionariosFila(dto.getFuncionariosFila());
			ic.setPaqueteCompleto(dto.getPaqueteCompleto());
			ic.setLlegoRg(dto.getLlegoRg());
			ic.setDesayuno(dto.getDesayuno());
			ic.setInicioVotacion(dto.getInicioVotacion());
//			ic.setInicioVotacion(df.format(dto.getInicioVotacion()));
			  
			if(casillasRepository.save(ic) ==0) {
				throw new CotException("No se guardo la informacion con éxito.", 409);
			}
		}else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		
		return casillasRepository.getMaxId();
	}

	@Override
	@Transactional(rollbackFor = { CotException.class })
	public Long saveIncidenciasCasilla(IncidenciasCasillasDTO dto, long perfil, long usuario) throws CotException {

		if (perfil == PERFIL_RG) {

			List<listIncidenciasDTO> lstIn = dto.getIncidencia();
			System.out.println(" numero incidencias " + lstIn.size());
			IncidenciasCasillas ic = null;

			Usuario usr = usuarioRepository.findById(usuario);

			System.out.println("SSSS " + dto.getPresentaIncidencias());
			if (dto.getPresentaIncidencias().equals("si")) {
				for (listIncidenciasDTO incidencia : lstIn) {
					ic = new IncidenciasCasillas();

					ic.setIdCasilla(dto.getIdCasilla());
					ic.setIdIncidencia(incidencia.getId());

					if (incidenciasRepository.save(ic) == 0) {
						throw new CotException("No se guardo la incidencia con éxito.", 409);
					}
				}

			} if (dto.getPresentaIncidencias().equals("no")) {

				ReporteCasilla rc = new ReporteCasilla();

				rc.setIdCasilla(dto.getIdCasilla());
				rc.setHoraReporte(dto.getHoraReporte());
				rc.setIdRg(usr.getId());
				rc.setNumeroVotos(dto.getNumero());
				
				if (reporteRepository.save(rc) == 0) {
					throw new CotException("No se guardo el reporte con éxito.", 409);
				};

			}

		} else {
			throw new CotException("No cuenta con los permisos suficientes para realizar la operacion.", 401);
		}
		return dto.getIdCasilla();
	}

}
