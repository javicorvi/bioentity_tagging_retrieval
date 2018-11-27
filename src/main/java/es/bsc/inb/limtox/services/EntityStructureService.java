package es.bsc.inb.limtox.services;

import es.bsc.inb.limtox.model.EntityType;

public interface EntityStructureService {

	public EntityType getEntityType(String entityTypeName);
	
	public EntityType putEntityType(String entityTypeName, EntityType entityType);
	
	public void generateEntityStructureJSON(String outputFile);
}
