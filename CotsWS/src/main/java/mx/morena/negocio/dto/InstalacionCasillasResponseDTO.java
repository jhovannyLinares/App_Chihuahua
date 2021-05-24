package mx.morena.negocio.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class InstalacionCasillasResponseDTO {
	
	private Time horaInstalacion;

	private String llegaronFuncionarios;

	private String funcionariosFila;

	private String paqueteCompleto;

	private String llegoRg;

	private String desayuno;

	private Time inicioVotacion;

	private String llegoRc;

	/////////// nuevos campos

	private String instaloCasilla;

	private String instaloLugarDistinto;

	private String CausaLugarDistinto;

	private Long BoletasGobernador;

	private Long FolioIniGobernador;

	private Long FolioFinGobernador;

	private Long BoletasDipLocal;

	private Long FolioIniDipLocal;

	private Long FolioFinDipLocal;

	private Long BoletasPresidenteMunicipal;

	private Long FolioIniPresidenteMunicipal;

	private Long FolioFinPresidenteMunicipal;

	private Long BoletasSindico;

	private Long FolioIniSindico;

	private Long FolioFinSindico;

	private Long BoletasDipFederal;

	private Long FolioIniDipFederal;

	private Long FolioFinDipFederal;
	
	private String sellaronBoletas;
	
	private Long idPartidoSello;
	
	private String nombrePresidente;
	
	private String nombreSecretario;
	
	private String nombreEscrutador1;
	
	private String nombreEscrutador2;
	
	private String nombreEscrutador3;
	
	private String presentaIncidencias;
	
	private List<IncidenciasResponseDTO> incidencia;

	public String getLlegaronFuncionarios() {
		return llegaronFuncionarios;
	}

	public void setLlegaronFuncionarios(String llegaronFuncionarios) {
		this.llegaronFuncionarios = llegaronFuncionarios;
	}

	public String getFuncionariosFila() {
		return funcionariosFila;
	}

	public void setFuncionariosFila(String funcionariosFila) {
		this.funcionariosFila = funcionariosFila;
	}

	public String getPaqueteCompleto() {
		return paqueteCompleto;
	}

	public void setPaqueteCompleto(String paqueteCompleto) {
		this.paqueteCompleto = paqueteCompleto;
	}

	public String getLlegoRg() {
		return llegoRg;
	}

	public void setLlegoRg(String llegoRg) {
		this.llegoRg = llegoRg;
	}

	public String getDesayuno() {
		return desayuno;
	}

	public void setDesayuno(String desayuno) {
		this.desayuno = desayuno;
	}

	public String getLlegoRc() {
		return llegoRc;
	}

	public void setLlegoRc(String llegoRc) {
		this.llegoRc = llegoRc;
	}

	public String getInstaloCasilla() {
		return instaloCasilla;
	}

	public void setInstaloCasilla(String instaloCasilla) {
		this.instaloCasilla = instaloCasilla;
	}

	public String getInstaloLugarDistinto() {
		return instaloLugarDistinto;
	}

	public void setInstaloLugarDistinto(String instaloLugarDistinto) {
		this.instaloLugarDistinto = instaloLugarDistinto;
	}

	public String getCausaLugarDistinto() {
		return CausaLugarDistinto;
	}

	public void setCausaLugarDistinto(String causaLugarDistinto) {
		CausaLugarDistinto = causaLugarDistinto;
	}

	public Long getBoletasGobernador() {
		return BoletasGobernador;
	}

	public void setBoletasGobernador(Long boletasGobernador) {
		BoletasGobernador = boletasGobernador;
	}

	public Long getFolioIniGobernador() {
		return FolioIniGobernador;
	}

	public void setFolioIniGobernador(Long folioIniGobernador) {
		FolioIniGobernador = folioIniGobernador;
	}

	public Long getFolioFinGobernador() {
		return FolioFinGobernador;
	}

	public void setFolioFinGobernador(Long folioFinGobernador) {
		FolioFinGobernador = folioFinGobernador;
	}

	public Long getBoletasDipLocal() {
		return BoletasDipLocal;
	}

	public void setBoletasDipLocal(Long boletasDipLocal) {
		BoletasDipLocal = boletasDipLocal;
	}

	public Long getFolioIniDipLocal() {
		return FolioIniDipLocal;
	}

	public void setFolioIniDipLocal(Long folioIniDipLocal) {
		FolioIniDipLocal = folioIniDipLocal;
	}

	public Long getFolioFinDipLocal() {
		return FolioFinDipLocal;
	}

	public void setFolioFinDipLocal(Long folioFinDipLocal) {
		FolioFinDipLocal = folioFinDipLocal;
	}

	public Long getBoletasPresidenteMunicipal() {
		return BoletasPresidenteMunicipal;
	}

	public void setBoletasPresidenteMunicipal(Long boletasPresidenteMunicipal) {
		BoletasPresidenteMunicipal = boletasPresidenteMunicipal;
	}

	public Long getFolioIniPresidenteMunicipal() {
		return FolioIniPresidenteMunicipal;
	}

	public void setFolioIniPresidenteMunicipal(Long folioIniPresidenteMunicipal) {
		FolioIniPresidenteMunicipal = folioIniPresidenteMunicipal;
	}

	public Long getFolioFinPresidenteMunicipal() {
		return FolioFinPresidenteMunicipal;
	}

	public void setFolioFinPresidenteMunicipal(Long folioFinPresidenteMunicipal) {
		FolioFinPresidenteMunicipal = folioFinPresidenteMunicipal;
	}

	public Long getBoletasSindico() {
		return BoletasSindico;
	}

	public void setBoletasSindico(Long boletasSindico) {
		BoletasSindico = boletasSindico;
	}

	public Long getFolioIniSindico() {
		return FolioIniSindico;
	}

	public void setFolioIniSindico(Long folioIniSindico) {
		FolioIniSindico = folioIniSindico;
	}

	public Long getFolioFinSindico() {
		return FolioFinSindico;
	}

	public void setFolioFinSindico(Long folioFinSindico) {
		FolioFinSindico = folioFinSindico;
	}

	public Long getBoletasDipFederal() {
		return BoletasDipFederal;
	}

	public void setBoletasDipFederal(Long boletasDipFederal) {
		BoletasDipFederal = boletasDipFederal;
	}

	public Long getFolioIniDipFederal() {
		return FolioIniDipFederal;
	}

	public void setFolioIniDipFederal(Long folioIniDipFederal) {
		FolioIniDipFederal = folioIniDipFederal;
	}

	public Long getFolioFinDipFederal() {
		return FolioFinDipFederal;
	}

	public void setFolioFinDipFederal(Long folioFinDipFederal) {
		FolioFinDipFederal = folioFinDipFederal;
	}

	public String getSellaronBoletas() {
		return sellaronBoletas;
	}

	public void setSellaronBoletas(String sellaronBoletas) {
		this.sellaronBoletas = sellaronBoletas;
	}

	public Long getIdPartidoSello() {
		return idPartidoSello;
	}

	public void setIdPartidoSello(Long idPartidoSello) {
		this.idPartidoSello = idPartidoSello;
	}

	public String getNombrePresidente() {
		return nombrePresidente;
	}

	public void setNombrePresidente(String nombrePresidente) {
		this.nombrePresidente = nombrePresidente;
	}

	public String getNombreSecretario() {
		return nombreSecretario;
	}

	public void setNombreSecretario(String nombreSecretario) {
		this.nombreSecretario = nombreSecretario;
	}

	public String getNombreEscrutador1() {
		return nombreEscrutador1;
	}

	public void setNombreEscrutador1(String nombreEscrutador1) {
		this.nombreEscrutador1 = nombreEscrutador1;
	}

	public String getNombreEscrutador2() {
		return nombreEscrutador2;
	}

	public void setNombreEscrutador2(String nombreEscrutador2) {
		this.nombreEscrutador2 = nombreEscrutador2;
	}

	public String getNombreEscrutador3() {
		return nombreEscrutador3;
	}

	public void setNombreEscrutador3(String nombreEscrutador3) {
		this.nombreEscrutador3 = nombreEscrutador3;
	}

	public String getPresentaIncidencias() {
		return presentaIncidencias;
	}

	public void setPresentaIncidencias(String presentaIncidencias) {
		this.presentaIncidencias = presentaIncidencias;
	}

	public List<IncidenciasResponseDTO> getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(List<IncidenciasResponseDTO> incidencia) {
		this.incidencia = incidencia;
	}

	public Time getHoraInstalacion() {
		return horaInstalacion;
	}

	public void setHoraInstalacion(Time horaInstalacion) {
		this.horaInstalacion = horaInstalacion;
	}

	public Time getInicioVotacion() {
		return inicioVotacion;
	}

	public void setInicioVotacion(Time inicioVotacion) {
		this.inicioVotacion = inicioVotacion;
	}

}
