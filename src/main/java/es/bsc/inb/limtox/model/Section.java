package es.bsc.inb.limtox.model;

import java.util.ArrayList;
import java.util.List;

public class Section implements LimtoxEntity {
	
	private Integer id;
	
	private String name;
	
	private String internalName;
	
	private String text;
	
	private Integer speciesQuantity;
	
	private Integer diseasesQuantity;
	
	private Integer genesQuantity;
	
	private Integer chemicalCompoundsQuantity;
	
	private List<RelevantSectionTopicInformation> relevantTopicsInformation = new ArrayList<RelevantSectionTopicInformation>();
	
	private List<EntityInstanceFound> entitiesInstanceFound = new  ArrayList<EntityInstanceFound>();
	
	private List<Sentence> sentences = new ArrayList<Sentence>();
		
	public Section(String name, String text) {
		super();
		this.name = name;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
	public List<RelevantSectionTopicInformation> getRelevantTopicsInformation() {
		return relevantTopicsInformation;
	}

	public void setRelevantTopicsInformation(List<RelevantSectionTopicInformation> relevantTopicsInformation) {
		this.relevantTopicsInformation = relevantTopicsInformation;
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}

	public void addRelevantTopicInformation(RelevantSectionTopicInformation relevantSectionTopicInformation) {
		relevantTopicsInformation.add(relevantSectionTopicInformation);
		
	}

	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
		
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
	

	public void removeEntityInstanceFound(EntityInstanceFound entityInstanceFound) {
		entitiesInstanceFound.remove(entityInstanceFound);
	}
	
	public RelevantTopicInformation getRelevantTopicsInformationByName(String topicName) {
		for (RelevantTopicInformation relevantTopicInformation : relevantTopicsInformation) {
			if(relevantTopicInformation.getTopicName().equals(topicName)) {
				return relevantTopicInformation;
			}
		}
		return null;
	}

	public Integer getSpeciesQuantity() {
		return speciesQuantity;
	}

	public void setSpeciesQuantity(Integer speciesQuantity) {
		this.speciesQuantity = speciesQuantity;
	}

	public Integer getDiseasesQuantity() {
		return diseasesQuantity;
	}

	public void setDiseasesQuantity(Integer diseasesQuantity) {
		this.diseasesQuantity = diseasesQuantity;
	}

	public Integer getGenesQuantity() {
		return genesQuantity;
	}

	public void setGenesQuantity(Integer genesQuantity) {
		this.genesQuantity = genesQuantity;
	}

	public Integer getChemicalCompoundsQuantity() {
		return chemicalCompoundsQuantity;
	}

	public void setChemicalCompoundsQuantity(Integer chemicalCompoundsQuantity) {
		this.chemicalCompoundsQuantity = chemicalCompoundsQuantity;
	}
	
	/**
	 * Find entityInstance By Id
	 * @param entityInstanceId
	 * @return
	 */
	public EntityInstanceFound findEntityInstanceFoundById(String entityInstanceId) {
		for (EntityInstanceFound entityInstanceFound : entitiesInstanceFound) {
			if(entityInstanceFound.getEntityInstanceId().equals(entityInstanceId)) {
				return entityInstanceFound;
			}
		}
		return null;
	}
	
	
	
}
