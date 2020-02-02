package Hrehova.Stedlova.PlanEat;
import java.util.List;
import java.util.Map;

public class Recept {
	private Long id_receptu;
	private String nazov_receptu;
	private int pocet_porcii;
	private String cas_pripravy;
	private boolean oblubeny_recept;
	private String postup;
	
	private List<Ingrediencia> ingrediencie;
	private Map<Ingrediencia, String> ingrediencie_mnozstvo;


	public Long getId_receptu() { 
		return id_receptu;
	}

	public void setId_receptu(Long id_receptu) {
		this.id_receptu = id_receptu;
	}

	public void setNazov_receptu(String nazov_receptu) {
		this.nazov_receptu = nazov_receptu;
	}

	public String getNazov_receptu() {
		return nazov_receptu;
	}

	public int getPocet_porcii() {
		return pocet_porcii;
	}

	public void setPocet_porcii(int pocet_porcii) {
		this.pocet_porcii = pocet_porcii;
	}

	public String getCas_pripravy() {
		return cas_pripravy;
	}

	public void setCas_pripravy(String cas_pripravy) {
		this.cas_pripravy = cas_pripravy;
	}

	public boolean isOblubeny_recept() { 
		return oblubeny_recept;
	}

	public void setOblubeny_recept(boolean oblubeny_recept) {
		this.oblubeny_recept = oblubeny_recept;
	}

	public String getPostup() {
		return postup;
	}

	public void setPostup(String postup) {
		this.postup = postup;
	}

	public List<Ingrediencia> getIngrediencie() {
		return ingrediencie;
	}

	public void setIngrediencie(List<Ingrediencia> ingrediencie) {
		this.ingrediencie = ingrediencie;
	}

	public Map<Ingrediencia, String> getIngrediencie_mnozstvo() {
		return ingrediencie_mnozstvo;
	}

	public void setIngrediencie_mnozstvo(Map<Ingrediencia, String> ingrediencie_mnozstvo) {
		this.ingrediencie_mnozstvo = ingrediencie_mnozstvo;
	}

	@Override
	public String toString() {
		return  nazov_receptu ;
	}

	
	

}  
