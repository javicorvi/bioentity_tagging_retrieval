package es.bsc.inb.limtox.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.bsc.inb.limtox.model.EntityType;
@Service
public class EntityStructureServiceImpl implements EntityStructureService{

	public Map<String,EntityType> entitiesType = new HashMap<String,EntityType>();

	public EntityType getEntityType(String entityTypeName) {
		return this.getEntitiesType().get(entityTypeName);
	}
	
	public EntityType putEntityType(String entityTypeName, EntityType entityType) {
		return this.getEntitiesType().put(entityTypeName, entityType);
	}
	
	public Map<String, EntityType> getEntitiesType() {
		return entitiesType;
	}

	public void setEntitiesType(Map<String, EntityType> entitiesType) {
		this.entitiesType = entitiesType;
	}
	
	
}
