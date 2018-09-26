package es.bsc.inb.limtox.model;

public class ReferenceValue {
	
	private String value="";
	
	private Reference reference;
	
	private String referenceName;
	
	private EntityInstance entityInstance;

	
	
	public ReferenceValue(String value, Reference reference) {
		super();
		this.value = value;
		//this.reference = reference;
		this.referenceName=reference.getName();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public EntityInstance getEntityInstance() {
		return entityInstance;
	}

	public void setEntityInstance(EntityInstance entityInstance) {
		this.entityInstance = entityInstance;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}
	
	
}
