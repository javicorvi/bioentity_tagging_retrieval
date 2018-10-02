package es.bsc.inb.limtox.services;

import java.io.File;
import java.util.Map;

import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.Section;

public interface GeneService {

	public void execute(Boolean retrieveGenes, String geneTaggedPathBlocks, String geneTaggedPathSentences, File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType);
	
}
