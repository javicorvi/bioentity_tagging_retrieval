package es.bsc.inb.limtox.model;

import java.util.List;

public class EntityInstance {

	private String value="";
	
	private EntityType entityType;
	
	private List<ReferenceValue> referenceValues;
	
	private String entityTypeName;
	
	public EntityInstance(String value, EntityType entityType, List<ReferenceValue> referenceValues) {
		super();
		this.value = value;
		//this.entityType = entityType;
		this.entityTypeName = entityType.getName();
		this.referenceValues = referenceValues;
	}

	public EntityInstance(String value, String entityType, List<ReferenceValue> referenceValues) {
		super();
		this.value = value;
		//this.entityType = entityType;
		this.entityTypeName = entityType;
		this.referenceValues = referenceValues;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public List<ReferenceValue> getReferenceValues() {
		return referenceValues;
	}

	public void setReferenceValues(List<ReferenceValue> referenceValues) {
		this.referenceValues = referenceValues;
	}

	public String getEntityTypeName() {
		return entityTypeName;
	}

	public void setEntityTypeName(String entityTypeName) {
		this.entityTypeName = entityTypeName;
	}
	
	
	
}
