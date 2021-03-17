package mx.morena.security.servicio;

import org.apache.log4j.Logger;

public class MasterService {
	
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected static final Long PERFIL_ESTATAL = 1L;
	protected static final Long PERFIL_FEDERAL = 2L;
	protected static final Long PERFIL_LOCAL = 3L;
	protected static final Long PERFIL_MUNICIPAL = 4L;
	protected static final Long PERFIL_COTS = 5L;
	protected static final Long PERFIL_SECCION_ELECTORAL = 6L;
	protected static final Long PERFIL_CRG = 7L;
	protected static final Long PERFIL_RG = 8L;
	protected static final Long PERFIL_RC = 9L;
	
	protected static final Long CONVENCIDO = 1L;
	protected static final Long COT = 2L;
	
	protected static final char ESTATUS_ALTA = 'A';
	protected static final char ESTATUS_SUSPENDIDO = 'S';
	
	

}
