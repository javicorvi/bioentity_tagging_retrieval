package es.bsc.inb.limtox.model;

import java.util.List;



public class HepatotoxicityTermSentence extends ReletavantTermSentence{
	
	private HepatotoxicityTerm hepatotoxicityTerm;
	
	public HepatotoxicityTermSentence() {}
	
	public HepatotoxicityTermSentence(HepatotoxicityTerm hepatotoxicityTerm, Float score, Integer quantity, List<Ocurrence> ocurrences, Sentence sentence) {
		super(score, quantity, sentence, ocurrences);
		this.hepatotoxicityTerm = hepatotoxicityTerm;
	}

	public HepatotoxicityTerm getHepatotoxicityTerm() {
		return hepatotoxicityTerm;
	}

	public void setHepatotoxicityTerm(HepatotoxicityTerm hepatotoxicityTerm) {
		this.hepatotoxicityTerm = hepatotoxicityTerm;
	}

}
