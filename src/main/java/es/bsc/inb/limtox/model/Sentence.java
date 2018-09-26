package es.bsc.inb.limtox.model;

import java.util.ArrayList;
import java.util.List;

public class Sentence implements LimtoxEntity {
	
	private Integer id;
	
	private String sentenceId;
	
	private Integer order;
	
	private String text;
	
	private Document document;
	
	private List<RelevantTopicInformation> relevantTopicsInformation = new ArrayList<RelevantTopicInformation>(); 
	
	private Section section;
	
	private List<EntityInstanceFound> entitiesInstanceFound = new  ArrayList<EntityInstanceFound>();
	
	/*
	private List<ChemicalCompoundSentence> chemicalCompoundSentences = new ArrayList<ChemicalCompoundSentence>();
	
	private List<HepatotoxicityTermSentence> hepatotoxicityTermSentences = new ArrayList<HepatotoxicityTermSentence>();
	
	private List<CytochromeSentence> cytochromeSentences = new ArrayList<CytochromeSentence>();
	
	private List<MarkerSentence> markerSentences = new ArrayList<MarkerSentence>();
	
	private List<ChemicalCompoundCytochromeSentence> chemicalCompoundCytochromeSentences = new ArrayList<ChemicalCompoundCytochromeSentence>();

	private List<HepatotoxicityTermChemicalCompoundSentence> hepatotoxicityTermChemicalCompoundSentences = new ArrayList<HepatotoxicityTermChemicalCompoundSentence>();
	
	private List<MarkerChemicalCompoundSentence> markerChemicalCompoundSentences = new ArrayList<MarkerChemicalCompoundSentence>();
	
	private List<TaxonomySentence> taxonomySentences = new ArrayList<TaxonomySentence>();
	*/
	
	public Sentence() {}

	public Sentence(String sentenceId, Integer order, String text) {
		this.sentenceId = sentenceId;
		this.order = order;
		this.text = text;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getSentenceId() {
		return sentenceId;
	}

	public void setSentenceId(String sentenceId) {
		this.sentenceId = sentenceId;
	}



	public Document getDocument() {
		return document;
	}



	public void setDocument(Document document) {
		this.document = document;
	}



	public List<RelevantTopicInformation> getRelevantTopicsInformation() {
		return relevantTopicsInformation;
	}



	public void setRelevantTopicsInformation(List<RelevantTopicInformation> relevantTopicsInformation) {
		this.relevantTopicsInformation = relevantTopicsInformation;
	}



	public Section getSection() {
		return section;
	}



	public void setSection(Section section) {
		this.section = section;
	}

	public void addRelevantTopicInformation(RelevantSentenceTopicInformation relevantSentenceTopicInformation) {
		this.relevantTopicsInformation.add(relevantSentenceTopicInformation);
	}

	public List<EntityInstanceFound> getEntitiesInstanceFound() {
		return entitiesInstanceFound;
	}

	public void setEntitiesInstanceFound(List<EntityInstanceFound> entitiesInstanceFound) {
		this.entitiesInstanceFound = entitiesInstanceFound;
	}

	public void addEntityInstanceFound(EntityInstanceFound entityInstanceFound) {
		entitiesInstanceFound.add(entityInstanceFound);
		
	}
	
	
	
	
	
}
