package es.bsc.inb.limtox.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ReletavantTermSentence {
	
	private Integer id;
	
	private Float score;
	
	private Integer quantity;
	
	@JsonIgnore
	private Sentence sentence;
		
	private List<Ocurrence> ocurrences;

	public ReletavantTermSentence() {}

	public ReletavantTermSentence(Float score, Integer quantity, Sentence sentence,	List<Ocurrence> ocurrences) {
		super();
		this.score = score;
		this.quantity = quantity;
		this.sentence = sentence;
		this.ocurrences = ocurrences;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Ocurrence> getOcurrences() {
		return ocurrences;
	}

	public void setOcurrences(List<Ocurrence> ocurrences) {
		this.ocurrences = ocurrences;
	}
	
	
}
