package es.bsc.inb.limtox.services;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.DocumentSource;
import es.bsc.inb.limtox.model.EntityInstance;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.Reference;
import es.bsc.inb.limtox.model.ReferenceValue;
import es.bsc.inb.limtox.model.RelevantDocumentTopicInformation;
import es.bsc.inb.limtox.model.RelevantSectionTopicInformation;
import es.bsc.inb.limtox.model.RelevantSentenceTopicInformation;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;

@Service
class MainServiceImpl implements MainService {

	static final Logger log = Logger.getLogger("taggingLog");
	
	Map<String,EntityType> entitiesType = new HashMap<String,EntityType>();
	
	public void execute(String propertiesParametersPath) {
		try {
			
			log.info("Retrieve Bioentities Tagging information with properties :  " +  propertiesParametersPath);
			Properties propertiesParameters = this.loadPropertiesParameters(propertiesParametersPath);
			
			log.info("The values of the properties file are: ");
			Enumeration e = propertiesParameters.propertyNames();
			while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      log.info(key + " -- " + propertiesParameters.getProperty(key) );
		    }
			
			String inputArticlesDirectoryPath = propertiesParameters.getProperty("inputArticlesDirectory");
			String inputSentencesDirectoryPath = propertiesParameters.getProperty("inputSentencesDirectory");
			String outputDirectoryPath = propertiesParameters.getProperty("outputDirectory");
			
			File inputArticlesDirectory = new File(inputArticlesDirectoryPath);
		    if(!inputArticlesDirectory.exists()) {
		    	return ;
		    }
		    if (!Files.isDirectory(Paths.get(inputArticlesDirectoryPath))) {
		    	return ;
		    }
		    
		    File inputDirectorySentences = new File(inputSentencesDirectoryPath);
		    if(!inputDirectorySentences.exists()) {
		    	return ;
		    }
		    if (!Files.isDirectory(Paths.get(inputSentencesDirectoryPath))) {
		    	return ;
		    }
			
		    File outputDirectory = new File(outputDirectoryPath);
		    if(!outputDirectory.exists())
		    	outputDirectory.mkdirs();
		    
			Boolean retrieveChemicalCompounds = propertiesParameters.getProperty("retrieveChemicalCompounds")!=null & 
					propertiesParameters.getProperty("retrieveChemicalCompounds").equals("true");
			String chemicalCompoundsTaggedPathBlocks = "";
			String chemicalCompoundsTaggedPathSentences = "";
			
			if(retrieveChemicalCompounds) {
				chemicalCompoundsTaggedPathBlocks = propertiesParameters.getProperty("chemicalCompoundsTaggedPathBlocks");
				chemicalCompoundsTaggedPathSentences = propertiesParameters.getProperty("chemicalCompoundsTaggedPathSentences");
				createChemicalCompoundEntityType();
			}
			
			Boolean retrieveDiseases = propertiesParameters.getProperty("retrieveDiseases")!=null & 
					propertiesParameters.getProperty("retrieveDiseases").equals("true");
			String diseasesTaggedPathBlocks = "";
			String diseasesTaggedPathSentences = "";
			if(retrieveDiseases) {
				diseasesTaggedPathBlocks = propertiesParameters.getProperty("diseasesTaggedPathBlocks");
				diseasesTaggedPathSentences = propertiesParameters.getProperty("diseasesTaggedPathSentences");
				createDiseasesEntityType();
			}
			
			Boolean retrieveGenes = propertiesParameters.getProperty("retrieveGenes")!=null & 
					propertiesParameters.getProperty("retrieveGenes").equals("true");
			String genesTaggedPathBlocks = "";
			String genesTaggedPathSentences = "";
			
			if(retrieveGenes) {
				genesTaggedPathBlocks = propertiesParameters.getProperty("genesTaggedPathBlocks");
				genesTaggedPathSentences = propertiesParameters.getProperty("genesTaggedPathSentences");
				createGenesEntityType();
			}
			
			Boolean retrieveSpecies = propertiesParameters.getProperty("retrieveSpecies")!=null & 
					propertiesParameters.getProperty("retrieveSpecies").equals("true");
			String speciesTaggedPathBlocks = "";
			String speciesTaggedPathSentences = "";
			if(retrieveSpecies) {
				speciesTaggedPathBlocks = propertiesParameters.getProperty("speciesTaggedPathBlocks");
				speciesTaggedPathSentences = propertiesParameters.getProperty("speciesTaggedPathSentences");
				createSpeciesEntityType();
			}
			
			Boolean enableLTKB = propertiesParameters.getProperty("enableLTKB")!=null & 
					propertiesParameters.getProperty("enableLTKB").equals("true");
			
			
			List<String> filesProcessed = readFilesProcessed(outputDirectoryPath); 
		    BufferedWriter filesPrecessedWriter = new BufferedWriter(new FileWriter(outputDirectoryPath + File.separator + "list_files_processed.dat", true));
		    
		    File[] files =  inputArticlesDirectory.listFiles();
			for (File file_to_classify : files) {
				if(file_to_classify.getName().endsWith(".txt") && filesProcessed!=null && !filesProcessed.contains(file_to_classify.getName())){
					log.info("Processing file  : " + file_to_classify.getName());
					String fileName = file_to_classify.getName();
					String internalOutputPath = outputDirectory + File.separator + file_to_classify.getName() + File.separator;
					File internalOutput = new File(internalOutputPath);
				    if(!internalOutput.exists())
				    	internalOutput.mkdirs();
					
				    Map<String, Document> documents_map = this.loadDocuments(file_to_classify);
				    if (Files.isRegularFile(Paths.get(inputDirectorySentences + File.separator + fileName))) {
						File inputFileSentences = new File(inputDirectorySentences + File.separator + fileName);
						this.loadSentences(documents_map, inputFileSentences);
				    }
				    
				    for (Document document : documents_map.values()) {
				    	Section section = document.getSections().get(0);
				    	
				    	chemicalCompounds(retrieveChemicalCompounds, chemicalCompoundsTaggedPathBlocks, chemicalCompoundsTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	species(retrieveSpecies, speciesTaggedPathBlocks, speciesTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	diseases(retrieveDiseases, diseasesTaggedPathBlocks, diseasesTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	genes(retrieveGenes, genesTaggedPathBlocks, genesTaggedPathSentences, file_to_classify, document, section);
				    	
				    	generateJSONFile(document, internalOutputPath + document.getDocumentId() + ".json");	
				   
				    }
				    /*for (String line : ObjectBank.getLineIterator(file_to_classify.getAbsolutePath(), "utf-8")) {
						try {
							String[] data_document = line.split("\t");
							Double score = new Double(data_document[0]);
							String relevant = data_document[1];
							String id = data_document[2];
							String title = data_document[3];
							String text_block = data_document[4];
							log.info(line);
							//process(id, text, outPutFile, file_to_classify.getName());
						}  catch (Exception e2) {
							log.error("Error processing the document line " + line + " belongs to the file: " +  fileName,e2);
						}
					}*/
				    
				    log.info("Processed file " + file_to_classify.getName());
				    
				    filesPrecessedWriter.write(file_to_classify.getName()+"\n");
					filesPrecessedWriter.flush();
				}
			}
			filesPrecessedWriter.close();
		}  catch (Exception e) {
			log.error("Generic error in the classification step",e);
		}
	}


	private void diseases(Boolean retrieveDiseases, String diseasesTaggedPathBlocks, String diseasesTaggedPathSentences,File file_to_classify,
			Document document, Section section) {
		if(retrieveDiseases) {
			if (Files.isRegularFile(Paths.get(diseasesTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(diseasesTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data = line.split("\t");
			    	if(data[0]!=null && data[0].equals(document.getDocumentId())) {
			    		log.info("Document " + document.getDocumentId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveDisease(data); 
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
			
			//sentences
			for (Sentence sentence : section.getSentences()) {
				if (Files.isRegularFile(Paths.get(diseasesTaggedPathSentences + File.separator + file_to_classify.getName()))) {
					for (String line : ObjectBank.getLineIterator(diseasesTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
						String[] data = line.split("\t");
						if(data[0]!=null && data[0].equals(sentence.getSentenceId())) {
				    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
				    		EntityInstanceFound entityInstanceFound = retrieveDisease(data); 
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
	}

	private void genes(Boolean retrieveGenes, String geneTaggedPathBlocks, String geneTaggedPathSentences, File file_to_classify,
			Document document, Section section) {
		if(retrieveGenes) {
			if (Files.isRegularFile(Paths.get(geneTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(geneTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data = line.split("\t");
			    	if(data.length>1 && data[0]!=null && data[0].equals(document.getDocumentId())) {
			    		log.info("Document " + document.getDocumentId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveGenes(data); 
			    		if(entityInstanceFound!=null) {
			    			section.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving genes tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + geneTaggedPathBlocks + File.separator + file_to_classify.getName());
			}
			
			//sentences
			for (Sentence sentence : section.getSentences()) {
				if (Files.isRegularFile(Paths.get(geneTaggedPathSentences + File.separator + file_to_classify.getName()))) {
					for (String line : ObjectBank.getLineIterator(geneTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
						String[] data = line.split("\t");
						if(data.length>1 && data[0]!=null && data[0].equals(sentence.getSentenceId())) {
				    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
				    		EntityInstanceFound entityInstanceFound = retrieveGenes(data); 
				    		if(entityInstanceFound!=null) {
				    			sentence.addEntityInstanceFound(entityInstanceFound);
				    		}else {
				    			log.error("Error retrieving genes tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
				    			log.error("The tagged line is" + data);
				    		}
				    	}
				    }
				} else {
					log.error("File not found " + geneTaggedPathSentences + File.separator + file_to_classify.getName());
				}
			}
			
		}
	}
	

	private void species(Boolean retrieveSpecies, String speciesTaggedPathBlocks, String speciesTaggedPathSentences, File file_to_classify,
			Document document, Section section) {
		if(retrieveSpecies) {
			if (Files.isRegularFile(Paths.get(speciesTaggedPathBlocks + File.separator + file_to_classify.getName() + "_tagged.txt"))) {
				for (String line : ObjectBank.getLineIterator(speciesTaggedPathBlocks + File.separator + file_to_classify.getName() + "_tagged.txt", "utf-8")) {
					String[] data = line.split("\t");
			    	if(data[1]!=null && data[1].equals(document.getDocumentId())) {
			    		log.info("Document " + document.getDocumentId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveSpecies(data); 
			    		if(entityInstanceFound!=null) {
			    			section.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving species tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + speciesTaggedPathBlocks + File.separator + file_to_classify.getName());
			}
			
			//sentences
			for (Sentence sentence : section.getSentences()) {
				if (Files.isRegularFile(Paths.get(speciesTaggedPathSentences + File.separator + file_to_classify.getName() + "_tagged.txt"))) {
					for (String line : ObjectBank.getLineIterator(speciesTaggedPathSentences + File.separator + file_to_classify.getName() + "_tagged.txt", "utf-8")) {
						String[] data = line.split("\t");
						if(data[1]!=null && data[1].equals(sentence.getSentenceId())) {
				    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
				    		EntityInstanceFound entityInstanceFound = retrieveSpecies(data); 
				    		if(entityInstanceFound!=null) {
				    			sentence.addEntityInstanceFound(entityInstanceFound);
				    		}else {
				    			log.error("Error retrieving species tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
				    			log.error("The tagged line is" + data);
				    		}
				    	}
				    }
				} else {
					log.error("File not found " + speciesTaggedPathSentences + File.separator + file_to_classify.getName());
				}
			}
			
		}
	}


	private void chemicalCompounds(Boolean retrieveChemicalCompounds, String chemicalCompoundsTaggedPathBlocks, String chemicalCompoundsTaggedPathSentences,
			File file_to_classify, Document document, Section section) {
		if(retrieveChemicalCompounds) {
			
			//blocks
			if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data_chemical_compound = line.split("\t");
			    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(document.getDocumentId())) {
			    		log.info("Document " + document.getDocumentId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound); 
			    		if(entityInstanceFound!=null) {
			    			section.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving chemical compound tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data_chemical_compound);
			    		}
			    		
			    	}
			    }
			} else {
				log.error("File not found " + chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName());
			}
			
			//sentences
			for (Sentence sentence : section.getSentences()) {
				if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName()))) {
					for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
						String[] data_chemical_compound = line.split("\t");
				    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(sentence.getSentenceId())) {
				    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
				    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound); 
				    		if(entityInstanceFound!=null) {
				    			sentence.addEntityInstanceFound(entityInstanceFound);
				    		}else {
				    			log.error("Error retrieving chemical compound tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
				    			log.error("The tagged line is" + data_chemical_compound);
				    		}
				    	}
				    }
				} else {
					log.error("File not found " + chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName());
				}
			}
		}
	}
	
	private EntityInstanceFound retrieveGenes(String[] data) {
		try {
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			String type = data[4];
			String ncbi_id = data[5];
			
			EntityType entityType = entitiesType.get(Constants.GENES_ENTITY_TYPE);
			//Fix put comlumns into text file output
			ReferenceValue key_val = new ReferenceValue(ncbi_id, entityType.getReferenceByName("ncbi"));
			ReferenceValue type_val = new ReferenceValue(type, entityType.getReferenceByName("type"));
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			referenceValues.add(key_val);
			referenceValues.add(type_val);
			EntityInstance entityInstance = new EntityInstance(text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		}catch(Exception e) {
			log.error("Error reading species tagged line " + data ,e);
		}
		return null;
		
	}
	
	
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
			EntityType entityType = entitiesType.get(Constants.SPECIES_ENTITY_TYPE);
			//Fix put comlumns into text file output
			ReferenceValue key_val = new ReferenceValue(entity, entityType.getReferenceByName("species_ncbi"));
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			referenceValues.add(key_val);
			EntityInstance entityInstance = new EntityInstance(text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		}catch(Exception e) {
			log.error("Error reading species tagged line " + data ,e);
		}
		return null;
		
	}
	
	
	private EntityInstanceFound retrieveDisease(String[] data) {
		try {
			EntityType entityType = entitiesType.get(Constants.DISEASES_ENTITY_TYPE);
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			if (data.length==5) {
				//could be mesh or omim
				ReferenceValue key_val = new ReferenceValue(data[4], entityType.getReferenceByName("database_relation_key"));
				referenceValues.add(key_val);
			}
			EntityInstance entityInstance = new EntityInstance(text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		} catch(Exception e) {
			log.error("Error reading diseases tagged line " + data ,e);
		}
		return null;
	}

	/**
	 * 
	 */
	private void createChemicalCompoundEntityType() {
		Reference name = new Reference("name", "Trivial");
		Reference chid = new Reference("chid", "Chem Id Plus");
		Reference cheb = new Reference("cheb", "Chebi. Chemical Entities of Biological Interest ");
		Reference cas = new Reference("cas", "CAS registry number. American Chemical Society");
		Reference pubc = new Reference("pubc", "PubChem compound");
		Reference pubs = new Reference("pubs", "PubChem Substance");
		Reference inch = new Reference("inch", "International Chemical Identifier");
		Reference drug = new Reference("drug", "The DrugBank database is a unique bioinformatics and cheminformatics resource that combines detailed drug data with comprehensive drug target information");
		Reference hmbd = new Reference("hmbd","The Human Metabolome Database");
		Reference kegg = new Reference("kegg","KEGG COMPOUND Database");
		Reference kegd = new Reference("kegd","KEGG DRUG Database");
		Reference mesh = new Reference("mesh","MeSH (Medical Subject Headings) is the NLM controlled vocabulary thesaurus used for indexing articles for PubMed.");
		
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(chid);
		refereces.add(cheb);
		refereces.add(cas);
		refereces.add(pubc);
		refereces.add(pubs);
		refereces.add(inch);
		refereces.add(drug);
		refereces.add(hmbd);
		refereces.add(kegg);
		refereces.add(kegd);
		refereces.add(mesh);
		
		EntityType entityType = new EntityType(Constants.CHEMICAL_ENTITY_TYPE, refereces );
		entitiesType.put(Constants.CHEMICAL_ENTITY_TYPE, entityType);
	}

	private void createDiseasesEntityType() {
		Reference name = new Reference("name", "Trivial");
		Reference database_relation_key = new Reference("database_relation_key", "MeSH or OMIM key");
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(database_relation_key);
		EntityType entityType = new EntityType(Constants.DISEASES_ENTITY_TYPE, refereces );
		entitiesType.put(Constants.DISEASES_ENTITY_TYPE, entityType);
	}
	
	private void createSpeciesEntityType() {
		Reference name = new Reference("name", "Trivial");
		Reference database_relation_key = new Reference("species_ncbi", "NCBI Species");
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(database_relation_key);
		EntityType entityType = new EntityType(Constants.SPECIES_ENTITY_TYPE, refereces );
		entitiesType.put(Constants.SPECIES_ENTITY_TYPE, entityType);
	}
	
	private void createGenesEntityType() {
		Reference name = new Reference("name", "Trivial");
		Reference type = new Reference("type", "Type of Found");
		Reference ncbi_id = new Reference("ncbi", "NCBI id");
		List<Reference> refereces = new ArrayList<Reference>();
		refereces.add(name);
		refereces.add(type);
		refereces.add(ncbi_id);
		EntityType entityType = new EntityType(Constants.GENES_ENTITY_TYPE, refereces );
		entitiesType.put(Constants.GENES_ENTITY_TYPE, entityType);
	}
	
	/**
	 * 
	 * @param data_chemical_compound
	 */
	private EntityInstanceFound retrieveChemicalCompound(String[] data_chemical_compound) {
		try {
			String id = data_chemical_compound[0];
			Integer start = new Integer(data_chemical_compound[1]);
			Integer end = new Integer(data_chemical_compound[2]);
			String text = data_chemical_compound[3];
			String mentionType = data_chemical_compound[4];
			String mentionSource = data_chemical_compound[5];
			String chid = data_chemical_compound[6];
			String cheb = data_chemical_compound[7];
			String cas = data_chemical_compound[8];
			String pubc = data_chemical_compound[9];
			String pubs = data_chemical_compound[10];
			String inch = data_chemical_compound[11];
			String drug = data_chemical_compound[12];
			String hmbd = data_chemical_compound[13];
			String kegg = data_chemical_compound[14];
			String kegd = data_chemical_compound[15];
			String mesh = data_chemical_compound[16];
			
			EntityType entityType = entitiesType.get(Constants.CHEMICAL_ENTITY_TYPE);
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			//Fix put comlumns into text file output
			if(!(chid==null || (chid!=null && (chid.trim().equals("null")|| chid.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(chid, entityType.getReferenceByName("chid"));
				referenceValues.add(v);
			}
			
			if(!(cheb==null || (cheb!=null && (cheb.trim().equals("null")|| cheb.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(cheb, entityType.getReferenceByName("cheb"));
				referenceValues.add(v);
			}
			
			if(!(cas==null || (cas!=null && (cas.trim().equals("null")|| cas.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(cas, entityType.getReferenceByName("cas"));
				referenceValues.add(v);
			}
			
			if(!(pubc==null || (pubc!=null && (pubc.trim().equals("null")|| pubc.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(pubc, entityType.getReferenceByName("pubc"));
				referenceValues.add(v);
			}
			
			if(!(pubs==null || (pubs!=null && (pubs.trim().equals("null")|| pubs.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(pubs, entityType.getReferenceByName("pubs"));
				referenceValues.add(v);
			}
			
			if(!(inch==null || (inch!=null && (inch.trim().equals("null")|| inch.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(inch, entityType.getReferenceByName("inch"));
				referenceValues.add(v);
			}
			
			if(!(drug==null || (drug!=null && (drug.trim().equals("null")|| drug.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(drug, entityType.getReferenceByName("drug"));
				referenceValues.add(v);
			}
			
			if(!(hmbd==null || (hmbd!=null && (hmbd.trim().equals("null")|| hmbd.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(hmbd, entityType.getReferenceByName("hmbd"));
				referenceValues.add(v);
			}
			
			if(!(kegg==null || (kegg!=null && (kegg.trim().equals("null")|| kegg.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(kegg, entityType.getReferenceByName("kegg"));
				referenceValues.add(v);
			}
			
			if(!(kegd==null || (kegd!=null && (kegd.trim().equals("null")|| kegd.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(kegd, entityType.getReferenceByName("kegd"));
				referenceValues.add(v);
			}
			
			if(!(mesh==null || (mesh!=null && (mesh.trim().equals("null")|| mesh.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(mesh, entityType.getReferenceByName("mesh"));
				referenceValues.add(v);
			}
			
			EntityInstance entityInstance = new EntityInstance(text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, mentionType, mentionSource);
			return found;
		}catch(Exception e) {
			log.error("Error reading chemical compound tagged line " + data_chemical_compound,e );
		}
		return null;
	}
	
	
//	public void execute(String propertiesParametersPath) {
//		try {
//			
//			log.info("Retrieve Bioentities Tagging information with properties :  " +  propertiesParametersPath);
//			Properties propertiesParameters = this.loadPropertiesParameters(propertiesParametersPath);
//			
//			log.info("The values of the properties file are: ");
//			Enumeration e = propertiesParameters.propertyNames();
//			while (e.hasMoreElements()) {
//		      String key = (String) e.nextElement();
//		      log.info(key + " -- " + propertiesParameters.getProperty(key) );
//		    }
//			
//			String inputArticlesDirectoryPath = propertiesParameters.getProperty("inputArticlesDirectory");
//			String inputSentencesDirectoryPath = propertiesParameters.getProperty("inputSentencesDirectory");
//			String outputDirectoryPath = propertiesParameters.getProperty("outputDirectory");
//			
//			File inputArticlesDirectory = new File(inputArticlesDirectoryPath);
//		    if(!inputArticlesDirectory.exists()) {
//		    	return ;
//		    }
//		    if (!Files.isDirectory(Paths.get(inputArticlesDirectoryPath))) {
//		    	return ;
//		    }
//		    
//		    File inputDirectorySentences = new File(inputSentencesDirectoryPath);
//		    if(!inputDirectorySentences.exists()) {
//		    	return ;
//		    }
//		    if (!Files.isDirectory(Paths.get(inputSentencesDirectoryPath))) {
//		    	return ;
//		    }
//			
//		    File outputDirectory = new File(outputDirectoryPath);
//		    if(!outputDirectory.exists())
//		    	outputDirectory.mkdirs();
//		    
//			Boolean retrieveChemicalCompounds = propertiesParameters.getProperty("retrieveChemicalCompounds")!=null & 
//					propertiesParameters.getProperty("retrieveChemicalCompounds").equals("true");
//			String chemicalCompoundsTaggedPath = "";
//			if(retrieveChemicalCompounds) {
//				chemicalCompoundsTaggedPath = propertiesParameters.getProperty("chemicalCompoundsTaggedPath");
//			}
//			
//			Boolean retrieveDiseases = propertiesParameters.getProperty("retrieveDiseases")!=null & 
//					propertiesParameters.getProperty("retrieveDiseases").equals("true");
//			String diseasesTaggedPath = "";
//			if(retrieveDiseases) {
//				diseasesTaggedPath = propertiesParameters.getProperty("diseasesTaggedPath");
//			}
//			
//			Boolean retrieveGenes = propertiesParameters.getProperty("retrieveGenes")!=null & 
//					propertiesParameters.getProperty("retrieveGenes").equals("true");
//			String genesTaggedPath = "";
//			if(retrieveGenes) {
//				genesTaggedPath = propertiesParameters.getProperty("genesTaggedPath");
//			}
//			
//			Boolean retrieveSpecies = propertiesParameters.getProperty("retrieveSpecies")!=null & 
//					propertiesParameters.getProperty("retrieveSpecies").equals("true");
//			String speciesTaggedPath = "";
//			if(retrieveSpecies) {
//				speciesTaggedPath = propertiesParameters.getProperty("speciesTaggedPath");
//			}
//			
//			Boolean enableLTKB = propertiesParameters.getProperty("enableLTKB")!=null & 
//					propertiesParameters.getProperty("enableLTKB").equals("true");
//			
//			
//			List<String> filesProcessed = readFilesProcessed(outputDirectoryPath); 
//		    BufferedWriter filesPrecessedWriter = new BufferedWriter(new FileWriter(outputDirectoryPath + File.separator + "list_files_processed.dat", true));
//		    
//		    File[] files =  inputArticlesDirectory.listFiles();
//			for (File file_to_classify : files) {
//				if(file_to_classify.getName().endsWith(".txt") && filesProcessed!=null && !filesProcessed.contains(file_to_classify.getName())){
//					log.info("Processing file  : " + file_to_classify.getName());
//					String fileName = file_to_classify.getName();
//					String internalOutputPath = outputDirectory + File.separator + file_to_classify.getName() + File.separator;
//					File internalOutput = new File(internalOutputPath);
//				    if(!internalOutput.exists())
//				    	internalOutput.mkdirs();
//					
//				    Map<String, Document> documents_map = this.loadDocuments(file_to_classify);
//				    if (Files.isRegularFile(Paths.get(inputDirectorySentences + File.separator + fileName))) {
//						File inputFileSentences = new File(inputDirectorySentences + File.separator + fileName);
//						this.loadSentences(documents_map, inputFileSentences);
//				    }
//				    
//				    if(retrieveChemicalCompounds) {
//				    	
//				    	
//				    	
//				    }
//				    
//				    for (Document document : documents_map.values()) {
//				    	
//				    	
//				    	
//				    	generateJSONFile(document, internalOutputPath + document.getDocumentId() + ".json");
//					}
//				    /*for (String line : ObjectBank.getLineIterator(file_to_classify.getAbsolutePath(), "utf-8")) {
//						try {
//							String[] data_document = line.split("\t");
//							Double score = new Double(data_document[0]);
//							String relevant = data_document[1];
//							String id = data_document[2];
//							String title = data_document[3];
//							String text_block = data_document[4];
//							log.info(line);
//							//process(id, text, outPutFile, file_to_classify.getName());
//						}  catch (Exception e2) {
//							log.error("Error processing the document line " + line + " belongs to the file: " +  fileName,e2);
//						}
//					}*/
//				    
//				    log.info("Processed file " + file_to_classify.getName());
//				    
//				    filesPrecessedWriter.write(file_to_classify.getName()+"\n");
//					filesPrecessedWriter.flush();
//				}
//			}
//			filesPrecessedWriter.close();
//		}  catch (Exception e) {
//			log.error("Generic error in the classification step",e);
//		}
//	}
	
	/**
	 * 
	 * @param file_to_classify
	 * @return
	 */
	private Map<String, Document> loadDocuments(File file_to_classify) {
		Map<String, Document> documents = new HashMap<String, Document>();
		log.info(" Load relevant documents in file " + file_to_classify.getAbsolutePath());
		try {
			for (String line : ObjectBank.getLineIterator(file_to_classify.getAbsolutePath(), "utf-8")) {
				try {
					String[] data_document = line.split("\t");
					String relevantTopic = data_document[0];
					Double score = new Double(data_document[1]);
					String id = data_document[2];
					String source = data_document[3];
					String section = data_document[4];
					String title = data_document[5];
					String text = data_document[6];
					RelevantDocumentTopicInformation relevantDocumentTopicInformation = new RelevantDocumentTopicInformation(relevantTopic, score);
					DocumentSource documentSource = new DocumentSource(source);
					Section section_model = new Section(section, text);
					RelevantSectionTopicInformation relevantSectionTopicInformation = new RelevantSectionTopicInformation(relevantTopic, score);
					section_model.addRelevantTopicInformation(relevantSectionTopicInformation);
					Document document = new Document(id, documentSource, title);
					document.addRelevantTopicInformation(relevantDocumentTopicInformation);
					document.addSection(section_model);
					documents.put(id, document);
				}catch (Exception e) {
					log.error(" Error loadinding document line " + line + " from file " + file_to_classify.getAbsolutePath(), e);
				}
				
			}
		} catch (Exception e) {
			log.error(" General Exception " + file_to_classify, e);
		}
		return documents;
	}
	
	/**
	 * 
	 * @param file_to_classify
	 * @return
	 */
	private void loadSentences(Map<String,Document> documents, File file_to_classify) {
		log.info(" Load relevant sentences in file " + file_to_classify.getAbsolutePath());
		try {
			for (String line : ObjectBank.getLineIterator(file_to_classify.getAbsolutePath(), "utf-8")) {
				try {
					String[] data = line.split("\t");
					String relevantTopic = data[0];
					Double score = new Double(data[1]);
					String id_sentence =  data[2];
					String source =  data[3];
					String section =  data[4];
					String text =  data[5];
					
					String[] id = id_sentence.split("_");
					//the doc id and the order
					String docId = id[0];
					Integer order = new Integer(id[1]);
					
					RelevantSentenceTopicInformation relevantSentenceTopicInformation = new RelevantSentenceTopicInformation(relevantTopic, score);
					Document document =  documents.get(docId);
					if(document!=null) {
						Sentence sentence = new Sentence(id_sentence, order, text);
						document.getSections().get(0).addSentence(sentence);
						sentence.addRelevantTopicInformation(relevantSentenceTopicInformation);
						//document.getSentences().add(sentence);
					}else {
						log.info(" The sentence do not correspond to a paragraph id:  " + id_sentence );
					}
				}catch (Exception e) {
					log.error(" Error loadinding sentence line " + line + " from file " + file_to_classify.getAbsolutePath(), e);
				}
			}
		}catch (Exception e) {
			log.error(" General Exception " + file_to_classify, e);
		}
		
	}	
	
	
	private List<String> readFilesProcessed(String outputDirectoryPath) {
		try {
			List<String> files_processed = new ArrayList<String>();
			if(Files.isRegularFile(Paths.get(outputDirectoryPath + File.separator + "list_files_processed.dat"))) {
				FileReader fr = new FileReader(outputDirectoryPath + File.separator + "list_files_processed.dat");
			    BufferedReader br = new BufferedReader(fr);
			    
			    String sCurrentLine;
			    while ((sCurrentLine = br.readLine()) != null) {
			    	files_processed.add(sCurrentLine);
				}
			    br.close();
			    fr.close();
			}
			return files_processed;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	  * Load Properties
	  * @param properitesParametersPath
	  */
	 public Properties loadPropertiesParameters(String properitesParametersPath) {
		 Properties prop = new Properties();
		 InputStream input = null;
		 try {
			 input = new FileInputStream(properitesParametersPath);
			 // load a properties file
			 prop.load(input);
			 return prop;
		 } catch (IOException ex) {
			 ex.printStackTrace();
		 } finally {
			 if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		}
		return null;
	 }	
	 
	 
	 @SuppressWarnings("unused")
	private void generateJSONFile(Document document, String outputPath)  {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.NON_NULL);
			objectMapper.setSerializationInclusion(Include.NON_EMPTY);
			try {
				objectMapper.writeValue(new File(outputPath), document);
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
