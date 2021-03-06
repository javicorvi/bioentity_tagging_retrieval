package es.bsc.inb.limtox.model;

public class ReferenceValue {
	
	private String referenceName;
	
	private String value="";
	
	private EntityInstance entityInstance;

	private Reference reference;
	
//	public ReferenceValue(String value, Reference reference) {
//		super();
//		this.value = value;
//		//this.reference = reference;
//		this.referenceName=reference.getName();
//	}

	public ReferenceValue(String name, String value) {
		super();
		this.value = value;
		//this.reference = reference;
		this.referenceName=name;
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
