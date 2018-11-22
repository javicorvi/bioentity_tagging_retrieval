package es.bsc.inb.limtox.model;

public class Reference {

	private String name;
	
	private String description;
	
	private EntityType entityType;
	
	
	public Reference(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
