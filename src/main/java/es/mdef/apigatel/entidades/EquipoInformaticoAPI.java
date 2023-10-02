package es.mdef.apigatel.entidades;

import es.mde.acing.gatel.EquipoInformatico;

public class EquipoInformaticoAPI extends ModeloConId implements EquipoInformatico{

	private String sistemaOperativo;
	private Integer discoDuro;
	private Integer memoria;
	private Integer pulgadas;
	private String tipoEquipoInformatico;
	
	
	@Override
	public Integer getMemoria() {
		return memoria;
	}

	public void setMemoria(Integer memoria) {
		this.memoria = memoria;
	}

	@Override
	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public void setSistemaOperativo(String sistemaOperativo) {
		this.sistemaOperativo = sistemaOperativo;
	}

	@Override
	public Integer getDiscoDuro() {
		return discoDuro;
	}

	public void setDiscoDuro(Integer discoDuro) {
		this.discoDuro = discoDuro;
	}

	@Override
	public Integer getPulgadas() {
		return pulgadas;
	}

	public void setPulgadas(Integer pulgadas) {
		this.pulgadas = pulgadas;
	}
	
	@Override
	public String getTipoEquipoInformatico() {
		return tipoEquipoInformatico;
	}

	public void setTipoEquipoInformatico(String tipoEquipoInf) {
		this.tipoEquipoInformatico = tipoEquipoInf;
	}

	@Override
	public TipoModelo getTipoModelo() {
		return TipoModelo.EQUIPO_INFORMATICO;
	}

	@Override
	public String toString() {
		return "EquipoInformaticoAPI [sistemaOperativo=" + sistemaOperativo + ", discoDuro=" + discoDuro + ", memoria="
				+ memoria + ", pulgadas=" + pulgadas + "]";
	}

}
