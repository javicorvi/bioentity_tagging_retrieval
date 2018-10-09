package es.bsc.inb.limtox.model;

import java.util.ArrayList;
import java.util.List;

public class CustomEntityNameTagger {

	private String taggerName;
	
	private Float weightScore;
	
	private String taggerBlocksPath;
	
	private String taggerSentencePath;

	private List<EntityAssociation> associations = new ArrayList<EntityAssociation>();
	
	public CustomEntityNameTagger(String taggerName, String taggerBlocksPath, String taggerSentencePath, Float weightScore) {
		this.taggerName = taggerName;
		this.weightScore = weightScore;
		this.taggerBlocksPath = taggerBlocksPath;
		this.taggerSentencePath = taggerSentencePath;
	}

	public String getTaggerName() {
		return taggerName;
	}

	public void setTaggerName(String taggerName) {
		this.taggerName = taggerName;
	}

	public String getTaggerBlocksPath() {
		return taggerBlocksPath;
	}

	public void setTaggerBlocksPath(String taggerBlocksPath) {
		this.taggerBlocksPath = taggerBlocksPath;
	}

	public String getTaggerSentencePath() {
		return taggerSentencePath;
	}

	public void setTaggerSentencePath(String taggerSentencePath) {
		this.taggerSentencePath = taggerSentencePath;
	}

	public List<EntityAssociation> getAssociations() {
		return associations;
	}

	public void setAssociations(List<EntityAssociation> associations) {
		this.associations = associations;
	}

	public void addEntityAssociation(EntityAssociation entityAssociation) {
		associations.add(entityAssociation);
	}

	public Float getWeightScore() {
		return weightScore;
	}

	public void setWeightScore(Float weightScore) {
		this.weightScore = weightScore;
	}
	
	
	
	
}
