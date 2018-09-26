package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MarkerChemicalCompoundSentence {
	private Integer id;
	
	private ChemicalCompound chemicalCompound;
	
	private Marker marker;
	
	private RelationRule relationRule;
	
	private Float score;
	
	private Integer quantity;
	
	@JsonIgnore
	private Sentence sentence;
		
	public MarkerChemicalCompoundSentence() {}
	
	public MarkerChemicalCompoundSentence(ChemicalCompound chemicalCompound, Marker marker,Float score, Integer quantity, Sentence sentence, RelationRule relationRule) {
		this.chemicalCompound = chemicalCompound;
		this.marker = marker;
		this.score = score;
		this.quantity = quantity;
		this.sentence = sentence;
		this.relationRule = relationRule;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ChemicalCompound getChemicalCompound() {
		return chemicalCompound;
	}

	public void setChemicalCompound(ChemicalCompound chemicalCompound) {
		this.chemicalCompound = chemicalCompound;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Sentence getSentence() {
		return sentence;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public RelationRule getRelationRule() {
		return relationRule;
	}

	public void setRelationRule(RelationRule relationRule) {
		this.relationRule = relationRule;
	}

	
	
}
