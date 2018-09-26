package es.bsc.inb.limtox.model;

/**
 * Entity that describes a RelevantTop of a Text Entity.
 * @author jcorvi
 *
 */
public class RelevantTopicInformation {
	
	private String topicName;
	
	private Double score;

	public RelevantTopicInformation(String topicName, Double score) {
		this.topicName=topicName;
		this.score=score;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	
}
