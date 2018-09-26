package es.bsc.inb.limtox.model;

import java.util.List;

public class CytochromeSentence extends ReletavantTermSentence{
	
	private Cytochrome cytochrome;
	
	public CytochromeSentence() {}
	
	public CytochromeSentence(Cytochrome cytochrome, Float score, Integer quantity, List<Ocurrence> ocurrences, Sentence sentence) {
		super(score, quantity, sentence, ocurrences);
		this.cytochrome = cytochrome;
	}

	
	public Cytochrome getCytochrome() {
		return cytochrome;
	}

	public void setCytochrome(Cytochrome cytochrome) {
		this.cytochrome = cytochrome;
	}

	

}
