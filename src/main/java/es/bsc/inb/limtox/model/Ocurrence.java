package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Ocurrence {

	private Integer start;
	
	private Integer end;
	@JsonIgnore
	private HepatotoxicityTermSentence hepatotoxicityTermSentence;
	
	@JsonIgnore
	private ChemicalCompoundSentence chemicalCompoundSentence;
	
	public Ocurrence(Integer start, Integer end) {
		super();
		this.start = start;
		this.end = end;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public HepatotoxicityTermSentence getHepatotoxicityTermSentence() {
		return hepatotoxicityTermSentence;
	}

	public void setHepatotoxicityTermSentence(HepatotoxicityTermSentence hepatotoxicityTermSentence) {
		this.hepatotoxicityTermSentence = hepatotoxicityTermSentence;
	}

	public ChemicalCompoundSentence getChemicalCompoundSentence() {
		return chemicalCompoundSentence;
	}

	public void setChemicalCompoundSentence(ChemicalCompoundSentence chemicalCompoundSentence) {
		this.chemicalCompoundSentence = chemicalCompoundSentence;
	}
	
	
	
}
