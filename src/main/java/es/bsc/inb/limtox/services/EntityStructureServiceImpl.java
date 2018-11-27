package es.bsc.inb.limtox.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public void generateEntityStructureJSON(String outputFile) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		try {
			objectMapper.writeValue(new File(outputFile), entitiesType);
		} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
}
