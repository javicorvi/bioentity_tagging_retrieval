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
public class GeneServiceImpl extends EntityServiceImpl implements GeneService{

	static final Logger log = Logger.getLogger("taggingLog");

	public void execute(Boolean retrieveGenes, String geneTaggedPathBlocks, String geneTaggedPathSentences, File file_to_classify, Document document, Section section) {
		if(retrieveGenes) {
			retrieveGenesTaggedInfoFormSection(geneTaggedPathBlocks, file_to_classify, document, section);
			retrieveGenesTaggedInfoFromSentences(geneTaggedPathSentences, file_to_classify, section);
		}
	}

	/**
	 * 
	 * @param geneTaggedPathSentences
	 * @param file_to_classify
	 * @param section
	 */
	private void retrieveGenesTaggedInfoFromSentences(String geneTaggedPathSentences, File file_to_classify, Section section) {
		
		for (Sentence sentence : section.getSentences()) {
			int genesQuantity = 0;
			//int speciesQuantity = 0;
			if (Files.isRegularFile(Paths.get(geneTaggedPathSentences + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(geneTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data = line.split("\t");
					if(data.length>1 && data[0]!=null && data[0].equals(sentence.getSentenceId())) {
			    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveGenes(data);
			    		if(entityInstanceFound!=null) {
			    			sentence.addEntityInstanceFound(entityInstanceFound);
			    			//if(entityInstanceFound.getEntityInstance().getEntityTypeName().equals(Constants.GENES_ENTITY_TYPE)) {
			    				genesQuantity++;
			    			/*}else if(entityInstanceFound.getEntityInstance().getEntityTypeName().equals(Constants.SPECIES_ENTITY_TYPE)) {
			    				speciesQuantity++;
			    			}*/
			    		}else {
			    			//log.error("Error retrieving genes tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
			    			//log.error("The tagged line is" + data);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + geneTaggedPathSentences + File.separator + file_to_classify.getName());
			}
			sentence.setGenesQuantity(genesQuantity);
		}
	}

	/**
	 * 
	 * @param geneTaggedPathBlocks
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveGenesTaggedInfoFormSection(String geneTaggedPathBlocks, File file_to_classify, Document document, Section section) {
		int genesQuantity = 0;
		if (Files.isRegularFile(Paths.get(geneTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
			for (String line : ObjectBank.getLineIterator(geneTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
				String[] data = line.split("\t");
		    	if(data.length>1 && data[0]!=null && data[0].equals(document.getDocumentId())) {
		    		log.info("Document " + document.getDocumentId() + " \n " + line);
		    		EntityInstanceFound entityInstanceFound = retrieveGenes(data);
		    		if(entityInstanceFound!=null) {
		    			section.addEntityInstanceFound(entityInstanceFound);
		    			genesQuantity ++;
		    		}else {
		    			log.warn("warn retrieving genes tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
		    			log.warn("The tagged line is" + data);
		    		}
		    	}
		    }
		} else {
			log.error("File not found " + geneTaggedPathBlocks + File.separator + file_to_classify.getName());
		}
		section.setGenesQuantity(genesQuantity);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	private EntityInstanceFound retrieveGenes(String[] data) {
		try {
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			String type = data[4];
			String ncbi_id = data[5];
			
			//Fix put comlumns into text file output
			ReferenceValue key_val = new ReferenceValue("ncbi" , ncbi_id);
			ReferenceValue type_val = new ReferenceValue("type", type);
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			referenceValues.add(key_val);
			referenceValues.add(type_val);
			
			EntityType entityType = null;
			if(type!=null && type.equals("Gene")) {
				entityType = entityStructureService.getEntityType(Constants.GENES_ENTITY_TYPE);
			}else if(type!=null && type.equals("Species")){
				//for now the species will be tagged only for linneas 
				//entityType = entityStructureService.getEntityType(Constants.SPECIES_ENTITY_TYPE);
				return null;
			}else {
				log.error("Error reading genes tagger " + data + " - type : " + type);
			}
			if(entityType!=null) {
				EntityInstance entityInstance = new EntityInstance(Constants.GENES_TAGGER, text ,entityType, referenceValues);
				EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
				return found;
			}
			
		}catch(Exception e) {
			log.error("Error reading species tagged line " + data ,e);
		}
		return null;
		
	}
	
	public void createEntityStructure(Float weightScore) {
		Reference name = new Reference("name", "Trivial");
		Reference type = new Reference("type", "Type of Found");
		Reference ncbi_id = new Reference("ncbi", "NCBI id");
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(type);
		refereces.add(ncbi_id);
		EntityType entityType = new EntityType(Constants.GENES_ENTITY_TYPE, refereces, weightScore );
		entityStructureService.putEntityType(Constants.GENES_ENTITY_TYPE, entityType);
	}
}
