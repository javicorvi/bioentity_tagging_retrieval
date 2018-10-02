package es.bsc.inb.limtox.services;

import java.io.File;
import java.util.Map;

import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.Section;

public interface ChemicalCompoundService {
	
	public void execute(Boolean retrieveChemicalCompounds, String chemicalCompoundsTaggedPathBlocks, String chemicalCompoundsTaggedPathSentences, 
			File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType);
	
}
