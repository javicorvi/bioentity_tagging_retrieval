package es.bsc.inb.limtox.model;

import java.util.List;

public class TaxonomySentence extends ReletavantTermSentence{
	
	
	private Taxonomy taxonomy;
	
	public TaxonomySentence() {}
	
	public TaxonomySentence(Taxonomy taxonomy, Float score, Integer quantity, List<Ocurrence> ocurrences, Sentence sentence) {
		super(score, quantity, sentence, ocurrences);
		this.taxonomy = taxonomy;
	}

	public Taxonomy getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(Taxonomy taxonomy) {
		this.taxonomy = taxonomy;
	}


}
