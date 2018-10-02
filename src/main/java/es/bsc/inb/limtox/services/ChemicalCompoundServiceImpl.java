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
public class ChemicalCompoundServiceImpl implements ChemicalCompoundService {
	
	static final Logger log = Logger.getLogger("taggingLog");
	
	public void execute (Boolean retrieveChemicalCompounds, String chemicalCompoundsTaggedPathBlocks, String chemicalCompoundsTaggedPathSentences,
			File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType) {
		if(retrieveChemicalCompounds) {
			retrieveTaggerInfoFromSection(chemicalCompoundsTaggedPathBlocks, file_to_classify, document, section, entitiesType);
			retrieveTaggerInfoFromSentences(chemicalCompoundsTaggedPathSentences, file_to_classify, section, entitiesType);
		}
	}
	/**
	 * Retrieve the tagger information from the sentences
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param section
	 */
	private void retrieveTaggerInfoFromSentences(String chemicalCompoundsTaggedPathSentences, File file_to_classify, Section section, Map<String,EntityType> entitiesType) {
		for (Sentence sentence : section.getSentences()) {
			if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data_chemical_compound = line.split("\t");
			    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(sentence.getSentenceId())) {
			    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound, entitiesType); 
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
	/**
	 * Retrieve tagger information from section
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveTaggerInfoFromSection(String chemicalCompoundsTaggedPathBlocks, File file_to_classify, Document document, Section section, Map<String,EntityType> entitiesType) {
		//blocks
		if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
			for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
				String[] data_chemical_compound = line.split("\t");
		    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(document.getDocumentId())) {
		    		log.info("Document " + document.getDocumentId() + " \n " + line);
		    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound, entitiesType); 
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
	}
	
	/**
	 * 
	 * @param data_chemical_compound
	 */
	private EntityInstanceFound retrieveChemicalCompound(String[] data_chemical_compound, Map<String,EntityType> entitiesType) {
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
				ReferenceValue v = new ReferenceValue(chid, entityType.getReferenceByName("chid").getName());
				referenceValues.add(v);
			}
			
			if(!(cheb==null || (cheb!=null && (cheb.trim().equals("null")|| cheb.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_CHEB, cheb);
				referenceValues.add(v);
			}
			
			if(!(cas==null || (cas!=null && (cas.trim().equals("null")|| cas.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_CAS, cas);
				referenceValues.add(v);
			}
			
			if(!(pubc==null || (pubc!=null && (pubc.trim().equals("null")|| pubc.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_PUBC, pubc);
				referenceValues.add(v);
			}
			
			if(!(pubs==null || (pubs!=null && (pubs.trim().equals("null")|| pubs.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_PUBS, pubs);
				referenceValues.add(v);
			}
			
			if(!(inch==null || (inch!=null && (inch.trim().equals("null")|| inch.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_INCH, inch);
				referenceValues.add(v);
			}
			
			if(!(drug==null || (drug!=null && (drug.trim().equals("null")|| drug.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_DRUG, drug);
				referenceValues.add(v);
			}
			
			if(!(hmbd==null || (hmbd!=null && (hmbd.trim().equals("null")|| hmbd.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_HMBD, hmbd);
				referenceValues.add(v);
			}
			
			if(!(kegg==null || (kegg!=null && (kegg.trim().equals("null")|| kegg.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_KEGG, kegg);
				referenceValues.add(v);
			}
			
			if(!(kegd==null || (kegd!=null && (kegd.trim().equals("null")|| kegd.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_KEGD, kegd);
				referenceValues.add(v);
			}
			
			if(!(mesh==null || (mesh!=null && (mesh.trim().equals("null")|| mesh.trim().equals(""))))) {
				ReferenceValue v = new ReferenceValue(Constants.CHEMICAL_MESH, mesh);
				referenceValues.add(v);
			}
			
			EntityInstance entityInstance = new EntityInstance(Constants.CHEMICALCOMPOUND_TAGGER, text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, mentionType, mentionSource);
			return found;
		}catch(Exception e) {
			log.error("Error reading chemical compound tagged line " + data_chemical_compound,e );
		}
		return null;
	}

}
