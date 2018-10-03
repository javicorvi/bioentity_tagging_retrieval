package es.bsc.inb.limtox.services;

import java.io.File;

import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.Section;

public interface EntityService {

	public void execute (Boolean retrieveChemicalCompounds, String chemicalCompoundsTaggedPathBlocks, String chemicalCompoundsTaggedPathSentences,
			File file_to_classify, Document document, Section section);
	
	public void createEntityStructure(Float weightScore);	
	
}
