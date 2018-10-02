package es.bsc.inb.limtox.model;

public class EntityAssociationSentence {

	private String entityInstanceFoundId;
	
	private String entityInstanceName;
	
	private String associationEntityInstanceFoundId;
	
	private String associationEntityInstanceFoundName;
	
	private String keyword;
	
	private String patternName;

	public EntityAssociationSentence(String entityInstanceFoundId, String entityInstanceName, String associationEntityInstanceFoundId,  String associationEntityInstanceFoundName, String keyword, String patternName) {
		this.entityInstanceFoundId = entityInstanceFoundId;
		this.entityInstanceName = entityInstanceName;
		
		this.associationEntityInstanceFoundId = associationEntityInstanceFoundId;
		this.associationEntityInstanceFoundName = associationEntityInstanceFoundName;
		
		this.keyword = keyword;
		this.patternName = patternName;
	}

	

	public String getEntityInstanceFoundId() {
		return entityInstanceFoundId;
	}



	public void setEntityInstanceFoundId(String entityInstanceFoundId) {
		this.entityInstanceFoundId = entityInstanceFoundId;
	}



	public String getAssociationEntityInstanceFoundId() {
		return associationEntityInstanceFoundId;
	}



	public void setAssociationEntityInstanceFoundId(String associationEntityInstanceFoundId) {
		this.associationEntityInstanceFoundId = associationEntityInstanceFoundId;
	}



	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPatternName() {
		return patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}



	public String getEntityInstanceName() {
		return entityInstanceName;
	}



	public void setEntityInstanceName(String entityInstanceName) {
		this.entityInstanceName = entityInstanceName;
	}



	public String getAssociationEntityInstanceFoundName() {
		return associationEntityInstanceFoundName;
	}



	public void setAssociationEntityInstanceFoundName(String associationEntityInstanceFoundName) {
		this.associationEntityInstanceFoundName = associationEntityInstanceFoundName;
	}
	
	
	
	

	
	
}
