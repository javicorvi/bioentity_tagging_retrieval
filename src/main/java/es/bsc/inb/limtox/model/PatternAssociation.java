package es.bsc.inb.limtox.model;

public class PatternAssociation {

	private String patternName;
	
	private String[] lemmaKeywords;

	public PatternAssociation(String patternName, String[] lemmaKeywords) {
		super();
		this.patternName = patternName;
		this.lemmaKeywords = lemmaKeywords;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	public String[] getLemmaKeywords() {
		return lemmaKeywords;
	}

	public void setLemmaKeywords(String[] lemmaKeywords) {
		this.lemmaKeywords = lemmaKeywords;
	}

}
