package Hrehova.Stedlova.PlanEat;

import java.time.LocalDate;

public class Jedalnicek {
	private Long id;
	private String den;
	private LocalDate datum;
	private Recept recept;
	private String nazovReceptu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDen() {
		return den;
	}

	public void setDen(String den) {
		this.den = den;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate localDate) {
		this.datum = localDate;
	}

	public Recept getRecept() {
		return recept;
	}

	public void setRecept(Recept recept) {
		this.recept = recept;
	}

	public String getNazovReceptu() {
		return nazovReceptu;
	}

	public void setNazovReceptu(String nazovReceptu) {
		this.nazovReceptu = nazovReceptu;
	}

}
