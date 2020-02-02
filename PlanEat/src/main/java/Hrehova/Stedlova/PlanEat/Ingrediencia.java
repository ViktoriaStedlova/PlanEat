package Hrehova.Stedlova.PlanEat;

public class Ingrediencia {
	private Long id_ingrediencie;
	private String nazov_ingrediencie;
	private String alergen;
	private String mnozstvo;

	public String getAlergen() {
		return alergen;
	}

	public void setAlergen(String alergen) {
		this.alergen = alergen;
	}

	public Long getId_ingrediencie() {
		return id_ingrediencie;
	}

	public void setId_ingrediencie(Long id_ingrediencie) {
		this.id_ingrediencie = id_ingrediencie;
	}

	public String getNazov_ingrediencie() {
		return nazov_ingrediencie;
	}

	public void setNazov_ingrediencie(String nazov_ingrediencie) {
		this.nazov_ingrediencie = nazov_ingrediencie;
	}

	public String getMnozstvo() {
		return mnozstvo;
	}

	public void setMnozstvo(String mnozstvo) {
		this.mnozstvo = mnozstvo;
	}

	//ingrediencie sa porovnavaju v ReceptController
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediencia other = (Ingrediencia) obj;
		if (id_ingrediencie == null) {
			if (other.id_ingrediencie != null)
				return false;
		} else if (!id_ingrediencie.equals(other.id_ingrediencie))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nazov_ingrediencie;
	}

}