package es.bsc.inb.limtox.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.model.CustomEntityNameTagger;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityAssociation;
import es.bsc.inb.limtox.model.EntityInstance;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.PatternAssociation;
import es.bsc.inb.limtox.model.Reference;
import es.bsc.inb.limtox.model.ReferenceValue;
import es.bsc.inb.limtox.model.RelevantTopicInformation;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;
@Service
public class CustomTaggerServiceImpl implements CustomTaggerService {
	
	List<CustomEntityNameTagger> customsTaggers = new ArrayList<CustomEntityNameTagger>();
	
	@Autowired
	protected EntityStructureService entityStructureService;
	
	static final Logger log = Logger.getLogger("taggingLog");
	
	
	
	public void execute(File file_to_classify, Document document, Section section) {
		for (CustomEntityNameTagger customEntityNameTagger : customsTaggers) {
			retrieveTaggerInfo(customEntityNameTagger, file_to_classify, document, section);
		}
		
	}
	
	
	
	/**
	 * Retrieve the information of the tagger
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveTaggerInfo(CustomEntityNameTagger customEntityNameTagger, File file_to_classify, Document document, Section section) {
		retrieveTaggerInfoFromSection(customEntityNameTagger, file_to_classify, document, section);
		retrieveTaggerInfoFromSentence(customEntityNameTagger, file_to_classify, section);
	}
	
	/**
	 * Retrieve the tagger information from the sentences
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param section
	 */
	private void retrieveTaggerInfoFromSentence(CustomEntityNameTagger customEntityNameTagger, File file_to_classify, Section section) {
		if (Files.isRegularFile(Paths.get(customEntityNameTagger.getTaggerSentencePath() + File.separator + file_to_classify.getName()))) {
			for (Sentence sentence : section.getSentences()) {
				int numbersOfTerms = 0;
				String[] columnNames=null;
				boolean column=true;
				for (String line : ObjectBank.getLineIterator(customEntityNameTagger.getTaggerSentencePath() + File.separator + file_to_classify.getName(), "utf-8")) {
					if(column) {
						columnNames = line.split("\t");
						column=false;
					}else {
						String[] data = line.split("\t");
						if(data[0]!=null && data[0].equals(sentence.getSentenceId())) {
				    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
				    		EntityInstanceFound entityInstanceFound = retrieveTag(customEntityNameTagger.getTaggerName(), data, columnNames); 
				    		if(entityInstanceFound!=null) {
				    			numbersOfTerms++;
				    			sentence.addEntityInstanceFound(entityInstanceFound);
				    		}else {
				    			log.error("Error retrieving diseases tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
				    			log.error("The tagged line is" + data);
				    		}
				    	}
					}
				}
				//if the tagger is a relevant topip we set the term numer score for the sentence
				RelevantTopicInformation relevantTopipInformation = sentence.getRelevantTopicsInformationByName(customEntityNameTagger.getTaggerName());
				if(relevantTopipInformation!=null) {
					relevantTopipInformation.setNumberOfTermsScore(numbersOfTerms);
					relevantTopipInformation.setCustomWeightScore(
							numbersOfTerms * customEntityNameTagger.getWeightScore() + 
							sentence.getDiseasesQuantity() * entityStructureService.getEntityType(Constants.DISEASES_ENTITY_TYPE).getWeightScore() + 
							sentence.getSpeciesQuantity() * entityStructureService.getEntityType(Constants.SPECIES_ENTITY_TYPE).getWeightScore() + 
							sentence.getChemicalCompoundsQuantity() * entityStructureService.getEntityType(Constants.CHEMICAL_ENTITY_TYPE).getWeightScore() + 
							sentence.getGenesQuantity() * entityStructureService.getEntityType(Constants.GENES_ENTITY_TYPE).getWeightScore());
				}
			}
				
		} else {
			log.error("File not found " + customEntityNameTagger.getTaggerSentencePath() + File.separator + file_to_classify.getName());
		}
	}
	/**
	 * Retrieve tagger information from section
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveTaggerInfoFromSection(CustomEntityNameTagger customEntityNameTagger, File file_to_classify, Document document, Section section) {
		if (Files.isRegularFile(Paths.get(customEntityNameTagger.getTaggerBlocksPath() + File.separator + file_to_classify.getName()))) {
			int numbersOfTerms = 0;
			String[] columnNames=null;
			boolean column=true;
			for (String line : ObjectBank.getLineIterator(customEntityNameTagger.getTaggerBlocksPath() + File.separator + file_to_classify.getName(), "utf-8")) {
				if(column) {
					columnNames = line.split("\t");
					column=false;
				}else {
					String[] data = line.split("\t");
			    	if(data[0]!=null && data[0].equals(document.getDocumentId())) {
			    		log.info("Document " + document.getDocumentId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveTag(customEntityNameTagger.getTaggerName(),data, columnNames); 
			    		if(entityInstanceFound!=null) {
			    			numbersOfTerms++;
			    			section.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving tagger info for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    	}
				}
			}
			//if the tagger is a relevant topic we set the term number score for the section
			RelevantTopicInformation relevantTopipInformation = section.getRelevantTopicsInformationByName(customEntityNameTagger.getTaggerName());
			if(relevantTopipInformation!=null) {
				relevantTopipInformation.setNumberOfTermsScore(numbersOfTerms);
				relevantTopipInformation.setCustomWeightScore(
						numbersOfTerms * customEntityNameTagger.getWeightScore() + 
						section.getDiseasesQuantity() * entityStructureService.getEntityType(Constants.DISEASES_ENTITY_TYPE).getWeightScore() + 
						section.getSpeciesQuantity() * entityStructureService.getEntityType(Constants.SPECIES_ENTITY_TYPE).getWeightScore() + 
						section.getChemicalCompoundsQuantity() * entityStructureService.getEntityType(Constants.CHEMICAL_ENTITY_TYPE).getWeightScore() + 
						section.getGenesQuantity() * entityStructureService.getEntityType(Constants.GENES_ENTITY_TYPE).getWeightScore());
			}
		} else {
			log.error("File not found " + customEntityNameTagger.getTaggerBlocksPath() + File.separator + file_to_classify.getName());
		}
	}
	
	private void createCustomEntityType(CustomEntityNameTagger customEntityNameTagger) {
		List<Reference> references = new ArrayList<Reference>();
		if(customEntityNameTagger.getReferences()!=null) {
			String[] columnNames = customEntityNameTagger.getReferences().split(",");
			if(columnNames!=null) {
				for (int i = 0; i < columnNames.length; i++) {
					Reference reference = new Reference(columnNames[i], "");
					references.add(reference);
				}
			}
		}
		EntityType entityType = new EntityType(customEntityNameTagger.getTaggerName(), references, customEntityNameTagger.getWeightScore());
		entityStructureService.putEntityType(customEntityNameTagger.getTaggerName(), entityType);
	}

	/**
	 * Retrieve tag information form tagger line
	 * @param data
	 * @param columnNames
	 * @return
	 */
	private EntityInstanceFound retrieveTag(String taggerName, String[] data, String[] columnNames) {
		try {
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			String entityType = data[4];
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			for (int i = 5; i < columnNames.length; i++) {
				String name = columnNames[i];
				try {
					String value = data[i];
					if(value!=null && !value.trim().equals("null")) {
						if(taggerName.equals("cyps")) {
							log.error("Cyps");
						}
						ReferenceValue key_val = new ReferenceValue(name, value);
						referenceValues.add(key_val);
					}
					//no data for that column, do not forget to put null and complete the information in the tagger.
				}catch(ArrayIndexOutOfBoundsException e) {
					
				}
			}
			EntityInstance entityInstance = new EntityInstance(taggerName, text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		} catch(Exception e) {
			log.error("Error reading custom tag tagged line " + data ,e);
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param properties
	 */
	public void readCustomTaggedEntitiesProperties(Properties properties) {
		for (int i = 1; i < 50; i++) {
			String name = properties.getProperty("customtag."+i+".taggerName");
			String blocksPath = properties.getProperty("customtag."+i+".taggedTermsPathBlocks");
			String sentencesPath = properties.getProperty("customtag."+i+".taggedTermsPathSentences");
			String weigthScore = properties.getProperty("customtag."+i+".weight.score");
			String references = properties.getProperty("customtag."+i+".references");
			if(name!=null && blocksPath!=null && sentencesPath!=null && weigthScore!=null) {
				Float weigthScore_f = new Float(weigthScore);
				CustomEntityNameTagger customEntityNameTagger= new CustomEntityNameTagger(name, blocksPath, sentencesPath, weigthScore_f, references);
				for (int j = 1; j < 50; j++) {
					String entity =  properties.getProperty("customtag."+i+".relation."+j+".entity");
					String relation_name =  properties.getProperty("customtag."+i+".relation."+j+".name");
					String key_lemma_list =  properties.getProperty("customtag."+i+".relation."+j+".pattern");
					if(entity!=null) {
						EntityAssociation entityAssociation = new EntityAssociation(entity);
						if(relation_name!=null && key_lemma_list!=null) {
							String[] keys = key_lemma_list.split(",");
							PatternAssociation patternAssociation = new PatternAssociation(relation_name, keys);
							entityAssociation.addPatternAssociation(patternAssociation);
						}
						customEntityNameTagger.addEntityAssociation(entityAssociation);
					}
				}
				customsTaggers.add(customEntityNameTagger);
			}
		}
		this.createCustomTaggersEntityTypes();
	}

	
	@Override
	public void createCustomTaggersEntityTypes() {
		for (CustomEntityNameTagger customEntityNameTagger : customsTaggers) {
			createCustomEntityType(customEntityNameTagger);
		}
		
	}
	
	public List<CustomEntityNameTagger> getCustomsTaggers() {
		return customsTaggers;
	}

	public void setCustomsTaggers(List<CustomEntityNameTagger> customsTaggers) {
		this.customsTaggers = customsTaggers;
	}

	
	
	
	
}
