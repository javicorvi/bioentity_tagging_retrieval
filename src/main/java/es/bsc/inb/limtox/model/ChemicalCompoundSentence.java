package es.bsc.inb.limtox.model;

import java.util.List;

public class ChemicalCompoundSentence extends ReletavantTermSentence{
	
	private ChemicalCompound chemicalCompound;
	
	private String chemicalCompoundValueTypeFounded;
	
	public ChemicalCompoundSentence() {}
	
	public ChemicalCompoundSentence(ChemicalCompound chemicalCompound, String chemicalCompoundValueTypeFounded, Float score, Integer quantity, List<Ocurrence> ocurrences, Sentence sentence) {
		super(score, quantity, sentence, ocurrences);
		this.chemicalCompound = chemicalCompound;
		this.chemicalCompoundValueTypeFounded = chemicalCompoundValueTypeFounded;
	}

	public ChemicalCompound getChemicalCompound() {
		return chemicalCompound;
	}

	public void setChemicalCompound(ChemicalCompound chemicalCompound) {
		this.chemicalCompound = chemicalCompound;
	}

	public String getChemicalCompoundValueTypeFounded() {
		return chemicalCompoundValueTypeFounded;
	}

	public void setChemicalCompoundValueTypeFounded(String chemicalCompoundValueTypeFounded) {
		this.chemicalCompoundValueTypeFounded = chemicalCompoundValueTypeFounded;
	}

	
	
}
