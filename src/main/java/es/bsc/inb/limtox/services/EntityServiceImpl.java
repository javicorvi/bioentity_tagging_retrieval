package es.bsc.inb.limtox.services;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class EntityServiceImpl implements EntityService{
	
	@Autowired
	protected EntityStructureService entityStructureService;
	
}
