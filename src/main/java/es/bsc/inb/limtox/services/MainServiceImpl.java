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
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.StemAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.bsc.inb.limtox.model.CustomEntityNameTagger;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.DocumentSource;
import es.bsc.inb.limtox.model.EntityAssociation;
import es.bsc.inb.limtox.model.EntityAssociationSentence;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.PatternAssociation;
import es.bsc.inb.limtox.model.RelevantDocumentTopicInformation;
import es.bsc.inb.limtox.model.RelevantSectionTopicInformation;
import es.bsc.inb.limtox.model.RelevantSentenceTopicInformation;
import es.bsc.inb.limtox.model.RelevantTopicInformation;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;

@Service
class MainServiceImpl implements MainService {

	protected Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	CustomTaggerService customTaggerService;
	
	@Autowired
	ChemicalCompoundService chemicalCompoundService;
	
	@Autowired
	SpecieService specieService;
	
	@Autowired
	DiseaseService diseaseService;
	
	@Autowired
	GeneService geneService;
	
	@Autowired
	protected EntityStructureService entityStructureService;
	
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
			String outputEntityStructureFilePath = propertiesParameters.getProperty("outputEntityStructureFilePath");
			
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
			Float chemicalCompoundsWeightScore = 0f;
			if(retrieveChemicalCompounds) {
				chemicalCompoundsTaggedPathBlocks = propertiesParameters.getProperty("chemicalCompoundsTaggedPathBlocks");
				chemicalCompoundsTaggedPathSentences = propertiesParameters.getProperty("chemicalCompoundsTaggedPathSentences");
				chemicalCompoundsWeightScore = new Float(propertiesParameters.get("chemical_compunds.weight.score").toString());
				chemicalCompoundService.createEntityStructure(chemicalCompoundsWeightScore);
			}
			
			Boolean retrieveDiseases = propertiesParameters.getProperty("retrieveDiseases")!=null & 
					propertiesParameters.getProperty("retrieveDiseases").equals("true");
			String diseasesTaggedPathBlocks = "";
			String diseasesTaggedPathSentences = "";
			Float diseasesWeightScore = 0f;
			if(retrieveDiseases) {
				diseasesTaggedPathBlocks = propertiesParameters.getProperty("diseasesTaggedPathBlocks");
				diseasesTaggedPathSentences = propertiesParameters.getProperty("diseasesTaggedPathSentences");
				diseasesWeightScore = new Float(propertiesParameters.get("diseases.weight.score").toString());
				diseaseService.createEntityStructure(diseasesWeightScore);
			}
			
			Boolean retrieveGenes = propertiesParameters.getProperty("retrieveGenes")!=null & 
					propertiesParameters.getProperty("retrieveGenes").equals("true");
			String genesTaggedPathBlocks = "";
			String genesTaggedPathSentences = "";
			Float genesWeightScore = 0f;
			if(retrieveGenes) {
				genesTaggedPathBlocks = propertiesParameters.getProperty("genesTaggedPathBlocks");
				genesTaggedPathSentences = propertiesParameters.getProperty("genesTaggedPathSentences");
				genesWeightScore = new Float(propertiesParameters.get("genes.weight.score").toString());
				geneService.createEntityStructure(genesWeightScore);
			}
			
			Boolean retrieveSpecies = propertiesParameters.getProperty("retrieveSpecies")!=null & 
					propertiesParameters.getProperty("retrieveSpecies").equals("true");
			String speciesTaggedPathBlocks = "";
			String speciesTaggedPathSentences = "";
			Float speciesWeightScore = 0f;
			if(retrieveSpecies) {
				speciesTaggedPathBlocks = propertiesParameters.getProperty("speciesTaggedPathBlocks");
				speciesTaggedPathSentences = propertiesParameters.getProperty("speciesTaggedPathSentences");
				speciesWeightScore = new Float(propertiesParameters.get("species.weight.score").toString());
				specieService.createEntityStructure(speciesWeightScore);
			}
			
			Boolean enableLTKB = propertiesParameters.getProperty("enableLTKB")!=null & 
					propertiesParameters.getProperty("enableLTKB").equals("true");
			
			customTaggerService.readCustomTaggedEntitiesProperties(propertiesParameters);
			
			entityStructureService.generateEntityStructureJSON(outputEntityStructureFilePath);
			
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
				    	
				    	chemicalCompoundService.execute(retrieveChemicalCompounds, chemicalCompoundsTaggedPathBlocks, chemicalCompoundsTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	specieService.execute(retrieveSpecies, speciesTaggedPathBlocks, speciesTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	diseaseService.execute(retrieveDiseases, diseasesTaggedPathBlocks, diseasesTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	geneService.execute(retrieveGenes, genesTaggedPathBlocks, genesTaggedPathSentences, file_to_classify, document, section);	
				    	
				    	customTaggerService.execute(file_to_classify, document, section);
				    	
				    	entityAssociations(customTaggerService.getCustomsTaggers(), document, section);
				    	
				    	generateJSONFile(document, internalOutputPath + document.getDocumentId() + ".json");	
				   
				    }
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

	/**
	 * 
	 * @param document
	 * @param section
	 */
	private void entityAssociations(List<CustomEntityNameTagger> customsTaggers, Document document, Section section) {
		log.debug("Analize the entityAssociations for document id : " + document.getDocumentId());
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		//props.put("regexner.mapping", rulesPathOutput);
		props.put("regexner.posmatchtype", "MATCH_ALL_TOKENS");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    //for every custom tag
		for (CustomEntityNameTagger customEntityNameTagger : customsTaggers) {
			log.debug("Custom entity tagger to process : " + customEntityNameTagger.getTaggerName());
			for (Sentence sentence : section.getSentences()) {
				log.debug("Sentence Id: " + sentence.getSentenceId());
				log.debug("Sentence Text : " + sentence.getText());
				RelevantTopicInformation relevantTopiInformation = sentence.getRelevantTopicsInformationByName(customEntityNameTagger.getTaggerName());
				
				if(relevantTopiInformation!=null) {
					List<EntityInstanceFound> entitiesHepatotoxicityInstanceFound = sentence.findEntitiesInstanceFoundByType(customEntityNameTagger.getTaggerName());
					Integer co_ocurrences_score = 0;
					Integer patterns_score = 0;
					for (EntityAssociation entityAssociation : customEntityNameTagger.getAssociations()) {
						log.debug(" Entity Association with : " + entityAssociation.getTopicName());
						List<EntityInstanceFound> entitiesAssociatinoInstanceFound = sentence.findEntitiesInstanceFoundByType(entityAssociation.getTopicName());
				    	for (EntityInstanceFound entityAssociationInstance : entitiesAssociatinoInstanceFound) {
							for (EntityInstanceFound endPointFound : entitiesHepatotoxicityInstanceFound) {
								try {
									String text_between_relation = "";
									if(entityAssociationInstance.getStart()<endPointFound.getStart()) {
										text_between_relation = sentence.getText().substring(entityAssociationInstance.getStart(), endPointFound.getEnd());
									}else {
										text_between_relation = sentence.getText().substring(endPointFound.getStart(), entityAssociationInstance.getEnd());
									}
									String[] words_between = text_between_relation.split(" ");
									Annotation sentence_annotated= new Annotation(sentence.getText());
									pipeline.annotate(sentence_annotated);
									List<CoreLabel> tokens= sentence_annotated.get(TokensAnnotation.class);
									Boolean patter_presente = false;
									for (CoreLabel token: tokens){
										String word = token.get(TextAnnotation.class);
										String pos = token.get(PartOfSpeechAnnotation.class);
										String ner = token.get(NamedEntityTagAnnotation.class);
										String lemma = token.get(LemmaAnnotation.class);
										//String stem = token.get(StemAnnotation.class);
										//generate patterns relations
										for (PatternAssociation patternAssociation : entityAssociation.getPatternAssociations()) {
											if(Stream.of(patternAssociation.getLemmaKeywords()).anyMatch(x -> x.equals(lemma))) { 
												patterns_score ++;
												EntityAssociationSentence entityAssociationSentence = new EntityAssociationSentence (endPointFound.getEntityInstanceId(), endPointFound.getEntityInstance().getEntityTypeName(), entityAssociationInstance.getEntityInstanceId(), entityAssociationInstance.getEntityInstance().getEntityTypeName(),lemma, patternAssociation.getPatternName());
												sentence.addEntityAssociationInstanceFound(entityAssociationSentence);
												patter_presente=true;
											}
										}
									}
									if(!patter_presente) {
										EntityAssociationSentence entityAssociationSentence = new EntityAssociationSentence (endPointFound.getEntityInstanceId(), endPointFound.getEntityInstance().getEntityTypeName(), entityAssociationInstance.getEntityInstanceId(), entityAssociationInstance.getEntityInstance().getEntityTypeName(),"", Constants.DEFAULT_ASSOCIATION_RULE);
										sentence.addEntityAssociationInstanceFound(entityAssociationSentence);
										co_ocurrences_score ++;
									}
								}catch(Exception e) {
									log.error("Error with relation generation " ,e );
								}
							}	
						}
				    	relevantTopiInformation.setCoOcurrenceScore(co_ocurrences_score);
				    	relevantTopiInformation.setPatternScore(patterns_score);
						log.debug(sentence.getText());
						log.debug("End Point : " + relevantTopiInformation.getTopicName());
						log.debug("Classifier score : " + relevantTopiInformation.getClassifierScore());
						log.debug("End Point Terms : " +relevantTopiInformation.getNumberOfTermsScore());
						log.debug("End Point Terms co-ocurrences : " + relevantTopiInformation.getCoOcurrenceScore());
						//log.debug("End Point patterns : " + relevantTopiInformation.getCoOcurrenceScore());
				    }
				}else {
					log.warn("The relevant topic information is not present : " + customEntityNameTagger.getTaggerName());
				}
			}
		}
	}
	
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
					
					Sentence sentence = new Sentence(id_sentence, order, text);
					sentence.addRelevantTopicInformation(relevantSentenceTopicInformation);
					
					if(document!=null) {
						//Sentence sentence = new Sentence(id_sentence, order, text);
						Section section_model = document.findSectionByName(section);
						if(section_model!=null) {
							section_model.addSentence(sentence);
						}else {
							log.error(" There is no section named : " + section  + " in the document " + docId + " in the file : " + file_to_classify);
						}
					}else {
						//create document and section this is for sentences that are alone, meaning that his section was not classified as relevant.
						DocumentSource documentSource = new DocumentSource(source);
						document = new Document(docId, documentSource, "");
						Section section_model = new Section(section, "");
						document.addSection(section_model);
						section_model.addSentence(sentence);
						documents.put(docId, document);
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
