package es.bsc.inb.limtox.model;

/**
 * Entity that describes a RelevantTop of a Text Entity.
 * @author jcorvi
 *
 */
public class RelevantTopicInformation {
	
	private String topicName;
	
	private Double classifierScore;
	
	private Integer numberOfTermsScore;
	
	private Integer coOcurrenceScore;

	private Integer patternScore;
	
	private Float customWeightScore;
	
	public RelevantTopicInformation(String topicName, Double classifierScore) {
		this.topicName=topicName;
		this.classifierScore=classifierScore;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Double getClassifierScore() {
		return classifierScore;
	}

	public void setClassifierScore(Double classifierScore) {
		this.classifierScore = classifierScore;
	}

	

	public Integer getNumberOfTermsScore() {
		return numberOfTermsScore;
	}

	public void setNumberOfTermsScore(Integer numberOfTermsScore) {
		this.numberOfTermsScore = numberOfTermsScore;
	}

	public Integer getCoOcurrenceScore() {
		return coOcurrenceScore;
	}

	public void setCoOcurrenceScore(Integer coOcurrenceScore) {
		this.coOcurrenceScore = coOcurrenceScore;
	}

	public Integer getPatternScore() {
		return patternScore;
	}

	public void setPatternScore(Integer patternScore) {
		this.patternScore = patternScore;
	}

	public Float getCustomWeightScore() {
		return customWeightScore;
	}

	public void setCustomWeightScore(Float customWeightScore) {
		this.customWeightScore = customWeightScore;
	}

	
}
