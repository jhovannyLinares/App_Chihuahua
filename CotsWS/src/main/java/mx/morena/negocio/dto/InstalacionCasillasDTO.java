package mx.morena.negocio.dto;

import java.sql.Timestamp;

public class InstalacionCasillasDTO {
	
	private Long idCasilla;
	
	private Timestamp horaInstalacion;
	
	private String llegaronFuncionarios;
	
	private String funcionariosFila;
	
	private String paqueteCompleto;
	
	private String llegoRg;
	
	private String desayuno;
	
	private Timestamp inicioVotacion;

	public Long getIdCasilla() {
		return idCasilla;
	}

	public void setIdCasilla(Long idCasilla) {
		this.idCasilla = idCasilla;
	}

	public Timestamp getHoraInstalacion() {
		return horaInstalacion;
	}

	public void setHoraInstalacion(Timestamp horaInstalacion) {
		this.horaInstalacion = horaInstalacion;
	}

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

	public Timestamp getInicioVotacion() {
		return inicioVotacion;
	}

	public void setInicioVotacion(Timestamp inicioVotacion) {
		this.inicioVotacion = inicioVotacion;
	}

}
