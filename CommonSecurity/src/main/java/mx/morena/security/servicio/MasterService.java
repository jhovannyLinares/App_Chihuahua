package mx.morena.security.servicio;

import org.apache.log4j.Logger;

public class MasterService {
	
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected static final Integer PERFIL_ESTATAL = 1;
	protected static final Integer PERFIL_FEDERAL = 2;
	protected static final Integer PERFIL_LOCAL = 3;
	protected static final Integer PERFIL_MUNICIPAL = 4;
	protected static final Integer PERFIL_COTS = 5;
	protected static final Integer PERFIL_SECCION_ELECTORAL = 6;
	protected static final Integer PERFIL_CRG = 7;
	protected static final Integer PERFIL_RG = 8;
	protected static final Integer PERFIL_RC = 9;

}
