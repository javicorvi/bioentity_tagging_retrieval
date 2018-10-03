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
public class ChemicalCompoundServiceImpl extends EntityServiceImpl implements ChemicalCompoundService {
	
	static final Logger log = Logger.getLogger("taggingLog");
	
	public void execute (Boolean retrieveChemicalCompounds, String chemicalCompoundsTaggedPathBlocks, String chemicalCompoundsTaggedPathSentences,
			File file_to_classify, Document document, Section section) {
		if(retrieveChemicalCompounds) {
			retrieveTaggerInfoFromSection(chemicalCompoundsTaggedPathBlocks, file_to_classify, document, section);
			retrieveTaggerInfoFromSentences(chemicalCompoundsTaggedPathSentences, file_to_classify, section);
		}
	}
	/**
	 * Retrieve the tagger information from the sentences
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param section
	 */
	private void retrieveTaggerInfoFromSentences(String chemicalCompoundsTaggedPathSentences, File file_to_classify, Section section) {
		for (Sentence sentence : section.getSentences()) {
			int chemicalCompoundsQuantity = 0;
			if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName()))) {
				for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName(), "utf-8")) {
					String[] data_chemical_compound = line.split("\t");
			    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(sentence.getSentenceId())) {
			    		log.info("Sentence " + sentence.getSentenceId() + " \n " + line);
			    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound); 
			    		if(entityInstanceFound!=null) {
			    			sentence.addEntityInstanceFound(entityInstanceFound);
			    			chemicalCompoundsQuantity++;
			    		}else {
			    			log.error("Error retrieving chemical compound tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data_chemical_compound);
			    		}
			    	}
			    }
			} else {
				log.error("File not found " + chemicalCompoundsTaggedPathSentences + File.separator + file_to_classify.getName());
			}
			sentence.setChemicalCompoundsQuantity(chemicalCompoundsQuantity);
		}
	}
	/**
	 * Retrieve tagger information from section
	 * @param customEntityNameTagger
	 * @param file_to_classify
	 * @param document
	 * @param section
	 */
	private void retrieveTaggerInfoFromSection(String chemicalCompoundsTaggedPathBlocks, File file_to_classify, Document document, Section section) {
		int chemicalCompoundsQuantity = 0;
		if (Files.isRegularFile(Paths.get(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName()))) {
			for (String line : ObjectBank.getLineIterator(chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName(), "utf-8")) {
				String[] data_chemical_compound = line.split("\t");
		    	if(data_chemical_compound[0]!=null && data_chemical_compound[0].equals(document.getDocumentId())) {
		    		log.info("Document " + document.getDocumentId() + " \n " + line);
		    		EntityInstanceFound entityInstanceFound = retrieveChemicalCompound(data_chemical_compound); 
		    		if(entityInstanceFound!=null) {
		    			section.addEntityInstanceFound(entityInstanceFound);
		    			chemicalCompoundsQuantity ++;
		    		}else {
		    			log.error("Error retrieving chemical compound tagged for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
		    			log.error("The tagged line is" + data_chemical_compound);
		    		}
		    	}
		    }
		} else {
			log.error("File not found " + chemicalCompoundsTaggedPathBlocks + File.separator + file_to_classify.getName());
		}
		section.setChemicalCompoundsQuantity(chemicalCompoundsQuantity);	
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
			
			EntityType entityType = entityStructureService.getEntityType(Constants.CHEMICAL_ENTITY_TYPE);
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

	/**
	 * 
	 */
	public void createEntityStructure(Float weightScore) {
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
		
		EntityType entityType = new EntityType(Constants.CHEMICAL_ENTITY_TYPE, refereces, weightScore);
		entityStructureService.putEntityType(Constants.CHEMICAL_ENTITY_TYPE, entityType);
	}
	
	
}
