package Hrehova.Stedlova.PlanEat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReceptFxModel {
	private Long id;
	private StringProperty nazovReceptu = new SimpleStringProperty();
	private IntegerProperty pocetPorcii = new SimpleIntegerProperty();
	private StringProperty casPripravy = new SimpleStringProperty();
	private StringProperty postup = new SimpleStringProperty();
	private BooleanProperty oblubeny = new SimpleBooleanProperty();
	
	private List<Ingrediencia> ingrediencie = new ArrayList<Ingrediencia>();
	private Map<Ingrediencia, String> ingrediencie_mnozstvo = new HashMap<Ingrediencia, String>();

	public String getNazovReceptu() {
		return nazovReceptu.get();
	}

	public void setNazovReceptu(String nazovReceptu) {
		
		this.nazovReceptu.set(nazovReceptu);
	}

	public StringProperty nazovReceptuProperty() {
		return nazovReceptu;
	}

	public int getPocetPorcii() {
		return pocetPorcii.get();
	}

	public void setPocetPorcii(int pocetPorcii) {
		this.pocetPorcii.set(pocetPorcii);
	}

	public IntegerProperty pocetPorciiProperty() {
		return pocetPorcii;
	}

	public String getCasPripravy() {
		return casPripravy.get();
	}

	public void setCasPripravy(String casPripravy) {
		this.casPripravy.set(casPripravy);
	}

	public StringProperty casPripravyProperty() {
		return casPripravy;
	}

	public String getPostup() {
		return postup.get();
	}

	public void setPostup(String postup) {
		this.postup.set(postup);
	}

	public StringProperty postupProperty() {
		return postup;
	}

	public boolean jeOblubeny() {
		return oblubeny.get();
	}

	public void setOblubeny(boolean jeOblubeny) {
		this.oblubeny.set(jeOblubeny);
	}

	public BooleanProperty oblubenyProperty() {
		return oblubeny;
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

	public void setRecept(Recept recept) {
		this.id=recept.getId_receptu();
		setNazovReceptu(recept.getNazov_receptu());
		setCasPripravy(recept.getCas_pripravy());
		setPocetPorcii(recept.getPocet_porcii());
		setOblubeny(recept.isOblubeny_recept());
		setPostup(recept.getPostup());
		setIngrediencie(recept.getIngrediencie());	
		setIngrediencie_mnozstvo(recept.getIngrediencie_mnozstvo());
		
		
	}

	public Recept getRecept() {
		Recept recept = new Recept();
		recept.setNazov_receptu(getNazovReceptu());
		recept.setCas_pripravy(getCasPripravy());
		recept.setId_receptu(this.id);
		recept.setOblubeny_recept(jeOblubeny());
		recept.setPocet_porcii(getPocetPorcii());
		recept.setPostup(getPostup());
		recept.setIngrediencie(getIngrediencie());
		recept.setIngrediencie_mnozstvo(getIngrediencie_mnozstvo());

		return recept;

	}
}
