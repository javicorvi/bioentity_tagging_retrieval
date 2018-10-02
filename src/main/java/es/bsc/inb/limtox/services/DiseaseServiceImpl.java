package es.bsc.inb.limtox.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityInstance;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.ReferenceValue;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;
@Service
public class DiseaseServiceImpl implements DiseaseService{
	
	static final Logger log = Logger.getLogger("taggingLog");
	
	public void execute(Boolean retrieveDiseases, String diseasesTaggedPathBlocks, String diseasesTaggedPathSentences,File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType) {
		if(retrieveDiseases) {
			retrieveDiseaseTaggedInfoFromSection(diseasesTaggedPathBlocks, file_to_classify, document, section, entitiesType);
			retrieveDiseaseTaggedInfoFromSentences(diseasesTaggedPathSentences, file_to_classify, section, entitiesType);
		}
	}
	/**
	 * 
	 * @param diseasesTaggedPathSentences
	 * @param file_to_classify
	 * @param section
	 * @param entitiesType
	 */
	private void retrieveDiseaseTaggedInfoFromSentences(String diseasesTaggedPathSentences, File file_to_classify,
			Section section, Map<String,EntityType> entitiesType) {
		for (Sentence sentence : section.getSentences()) {
			if (Files.isRegularFile(Paths.get(diseasesTaggedPathSentences + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(diseasesTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data = line.split("\t");
					if(data[0]!=null && data[0].equals(sentence.getSentenceId())) {
			    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveDisease(data, entitiesType); 
			    		if(entityInstanceFound!=null) {
			    			sentence.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving diseases tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + diseasesTaggedPathSentences + File.separator + file_to_classify.getName());
			}
		}
	}
	/**
	 * 
	 * @param diseasesTaggedPathBlocks
	 * @param file_to_classify
	 * @param document
	 * @param section
	 * @param entitiesType
	 */
	private void retrieveDiseaseTaggedInfoFromSection(String diseasesTaggedPathBlocks, File file_to_classify,
			Document document, Section section, Map<String,EntityType> entitiesType) {
		if (Files.isRegularFile(Paths.get(diseasesTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
			for (String line : ObjectBank.getLineIterator(diseasesTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
				String[] data = line.split("\t");
		    	if(data[0]!=null && data[0].equals(document.getDocumentId())) {
		    		log.info("Document " + document.getDocumentId() + " \n " + line);
		    		EntityInstanceFound entityInstanceFound = retrieveDisease(data, entitiesType); 
		    		if(entityInstanceFound!=null) {
		    			section.addEntityInstanceFound(entityInstanceFound);
		    		}else {
		    			log.error("Error retrieving diseases tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
		    			log.error("The tagged line is" + data);
		    		}
		    		
		    	}
		    }
		} else {
			log.error("File not found " + diseasesTaggedPathBlocks + File.separator + file_to_classify.getName());
		}
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	private EntityInstanceFound retrieveDisease(String[] data, Map<String,EntityType> entitiesType) {
		try {
			EntityType entityType = entitiesType.get(Constants.DISEASES_ENTITY_TYPE);
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			if (data.length==5) {
				//could be mesh or omim
				ReferenceValue key_val = new ReferenceValue(entityType.getReferenceByName("database_relation_key").getName(),data[4]);
				referenceValues.add(key_val);
			}
			EntityInstance entityInstance = new EntityInstance(Constants.DISEASES_TAGGER,text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		} catch(Exception e) {
			log.error("Error reading diseases tagged line " + data ,e);
		}
		return null;
	}
	
}
