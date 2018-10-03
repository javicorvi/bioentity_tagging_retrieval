package es.bsc.inb.limtox.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityInstance;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.Reference;
import es.bsc.inb.limtox.model.ReferenceValue;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;
@Service
public class SpecieServiceImpl extends EntityServiceImpl implements SpecieService{

	static final Logger log = Logger.getLogger("taggingLog");
	
	public void execute(Boolean retrieveSpecies, String speciesTaggedPathBlocks, String speciesTaggedPathSentences, File file_to_classify, Document document, Section section) {
		if(retrieveSpecies) {
			retrieveSpeciesTaggedInfoFromBlock(speciesTaggedPathBlocks, file_to_classify, document, section);
			retrieveSpeciesTaggedInfoFromSentences(speciesTaggedPathSentences, file_to_classify, section);
		}
	}

	private void retrieveSpeciesTaggedInfoFromSentences(String speciesTaggedPathSentences, File file_to_classify, Section section) {
		for (Sentence sentence : section.getSentences()) {
			int speciesQuantity=0;
			if (Files.isRegularFile(Paths.get(speciesTaggedPathSentences + File.separator + file_to_classify.getName() + "_tagged.txt"))) {
				for (String line : ObjectBank.getLineIterator(speciesTaggedPathSentences + File.separator + file_to_classify.getName() + "_tagged.txt", "utf-8")) {
					String[] data = line.split("\t");
					if(data[1]!=null && data[1].equals(sentence.getSentenceId())) {
			    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveSpecies(data); 
			    		if(entityInstanceFound!=null) {
			    			sentence.addEntityInstanceFound(entityInstanceFound);
			    			speciesQuantity++;
			    		}else {
			    			log.error("Error retrieving species tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + speciesTaggedPathSentences + File.separator + file_to_classify.getName());
			}
			sentence.setSpeciesQuantity(speciesQuantity);
		}
	}
	
	/**
	 * 
	 * @param speciesTaggedPathBlocks
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveSpeciesTaggedInfoFromBlock(String speciesTaggedPathBlocks, File file_to_classify, Document document, Section section) {
		int speciesQuantity=0;
		if (Files.isRegularFile(Paths.get(speciesTaggedPathBlocks + File.separator + file_to_classify.getName() + "_tagged.txt"))) {
			for (String line : ObjectBank.getLineIterator(speciesTaggedPathBlocks + File.separator + file_to_classify.getName() + "_tagged.txt", "utf-8")) {
				String[] data = line.split("\t");
		    	if(data[1]!=null && data[1].equals(document.getDocumentId())) {
		    		log.info("Document " + document.getDocumentId() + " \n " + line);
		    		EntityInstanceFound entityInstanceFound = retrieveSpecies(data); 
		    		if(entityInstanceFound!=null) {
		    			section.addEntityInstanceFound(entityInstanceFound);
		    			speciesQuantity++;
		    		}else {
		    			log.error("Error retrieving species tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
		    			log.error("The tagged line is" + data);
		    		}
		    	}
		    }
		} else {
			log.error("File not found " + speciesTaggedPathBlocks + File.separator + file_to_classify.getName());
		}
		section.setSpeciesQuantity(speciesQuantity);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	private EntityInstanceFound retrieveSpecies(String[] data) {
		try {
			String entity = data[0];
			String id = data[1];
			Integer start = new Integer(data[2]);
			Integer end = new Integer(data[3]);
			String text = data[4];
			String comment = "";
			if (data.length==6) {
				comment = data[5];
			}
			EntityType entityType = entityStructureService.getEntityType(Constants.SPECIES_ENTITY_TYPE);
			//Fix put comlumns into text file output
			ReferenceValue key_val = new ReferenceValue(entityType.getReferenceByName("species_ncbi").getName(), entity);
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			referenceValues.add(key_val);
			EntityInstance entityInstance = new EntityInstance(Constants.SPECIES_TAGGER, text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		}catch(Exception e) {
			log.error("Error reading species tagged line " + data ,e);
		}
		return null;
		
	}
	
	public void createEntityStructure(Float weightScore) {
		Reference name = new Reference("name", "Trivial");
		Reference database_relation_key = new Reference("species_ncbi", "NCBI Species");
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(database_relation_key);
		EntityType entityType = new EntityType(Constants.SPECIES_ENTITY_TYPE, refereces, weightScore );
		entityStructureService.putEntityType(Constants.SPECIES_ENTITY_TYPE, entityType);
	}
	
}
