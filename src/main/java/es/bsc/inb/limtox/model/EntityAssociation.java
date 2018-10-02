package es.bsc.inb.limtox.model;

import java.util.ArrayList;
import java.util.List;

public class EntityAssociation {

	private String topicName;
	
	private List<PatternAssociation> patternAssociations = new ArrayList<PatternAssociation>();

	
	public EntityAssociation(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public List<PatternAssociation> getPatternAssociations() {
		return patternAssociations;
	}

	public void setPatternAssociations(List<PatternAssociation> patternAssociations) {
		this.patternAssociations = patternAssociations;
	}

	public void addPatternAssociation(PatternAssociation patternAssociation) {
		patternAssociations.add(patternAssociation);
		
	}
	
}
