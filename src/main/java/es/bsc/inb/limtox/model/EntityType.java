package es.bsc.inb.limtox.model;

import java.util.List;

public class EntityType {

	private String name="";
	
	private List<Reference> references;

	private Float weightScore;
	
	public EntityType(String name, List<Reference> references, Float weightScore) {
		super();
		this.name = name;
		this.references = references;
		this.weightScore =  weightScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public Reference getReferenceByName(String name) {
		for (Reference reference : references) {
			if(reference.getName().equals(name)) {
				return reference;
			}
		}
		return null;
	}

	public Float getWeightScore() {
		return weightScore;
	}

	public void setWeightScore(Float weightScore) {
		this.weightScore = weightScore;
	}
	
	
}
