package es.bsc.inb.limtox.services;

import java.io.File;
import java.util.Map;

import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.Section;

public interface DiseaseService {

	public void execute(Boolean retrieveDiseases, String diseasesTaggedPathBlocks, String diseasesTaggedPathSentences,File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType);
	
}
