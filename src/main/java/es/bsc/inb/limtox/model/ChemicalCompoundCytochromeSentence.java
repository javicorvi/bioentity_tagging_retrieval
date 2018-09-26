package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChemicalCompoundCytochromeSentence {
	
	private Integer id;
	
	private ChemicalCompound chemicalCompound;
	
	private Cytochrome cytochrome;
	
	private RelationRule relationRule;
	
	private Float score;
	
	private Integer quantity;
	@JsonIgnore
	private Sentence sentence;
	
	private CytochromeChemicalCompoundPattern pattern;
	
	public ChemicalCompoundCytochromeSentence() {}
	
	public ChemicalCompoundCytochromeSentence(ChemicalCompound chemicalCompound, Cytochrome cytochrome,Float score, Integer quantity, Sentence sentence) {
		this.chemicalCompound = chemicalCompound;
		this.cytochrome = cytochrome;
		this.score = score;
		this.quantity = quantity;
		this.sentence = sentence;
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

	public Cytochrome getCytochrome() {
		return cytochrome;
	}

	public void setCytochrome(Cytochrome cytochrome) {
		this.cytochrome = cytochrome;
	}

	public RelationRule getRelationRule() {
		return relationRule;
	}

	public void setRelationRule(RelationRule relationRule) {
		this.relationRule = relationRule;
	}

	public CytochromeChemicalCompoundPattern getPattern() {
		return pattern;
	}

	public void setPattern(CytochromeChemicalCompoundPattern pattern) {
		this.pattern = pattern;
	}
	
	

	

	
	
}
