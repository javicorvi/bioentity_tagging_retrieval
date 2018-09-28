package es.bsc.inb.limtox.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.objectbank.ObjectBank;
import es.bsc.inb.limtox.model.CustomEntityNameTagger;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.EntityInstance;
import es.bsc.inb.limtox.model.EntityInstanceFound;
import es.bsc.inb.limtox.model.EntityType;
import es.bsc.inb.limtox.model.ReferenceValue;
import es.bsc.inb.limtox.model.Section;
import es.bsc.inb.limtox.model.Sentence;
import es.bsc.inb.limtox.util.Constants;
@Service
public class CustomTaggerServiceImpl implements CustomTaggerService {
	
	static final Logger log = Logger.getLogger("taggingLog");
	
	public void execute(List<CustomEntityNameTagger> customsTaggers, File file_to_classify, Document document, Section section) {
		for (CustomEntityNameTagger customEntityNameTagger : customsTaggers) {
			retrieveTaggerInfo(customEntityNameTagger, file_to_classify, document, section);
		}
		
	}
	
	private void retrieveTaggerInfo(CustomEntityNameTagger customEntityNameTagger, File file_to_classify, Document document, Section section) {
		if (Files.isRegularFile(Paths.get(customEntityNameTagger.getTaggerBlocksPath() + File.separator + file_to_classify.getName()))) {
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
			    		EntityInstanceFound entityInstanceFound = retrieveTag(data, columnNames); 
			    		if(entityInstanceFound!=null) {
			    			section.addEntityInstanceFound(entityInstanceFound);
			    		}else {
			    			log.error("Error retrieving tagger info for document " + document.getDocumentId() + " in file: " + file_to_classify.getName() );
			    			log.error("The tagged line is" + data);
			    		}
			    		
			    	}
				}
				
		    }
		} else {
			log.error("File not found " + customEntityNameTagger.getTaggerBlocksPath() + File.separator + file_to_classify.getName());
		}
		
		//sentences
		for (Sentence sentence : section.getSentences()) {
			if (Files.isRegularFile(Paths.get(customEntityNameTagger.getTaggerSentencePath() + File.separator + file_to_classify.getName()))) {
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
				    		EntityInstanceFound entityInstanceFound = retrieveTag(data, columnNames); 
				    		if(entityInstanceFound!=null) {
				    			sentence.addEntityInstanceFound(entityInstanceFound);
				    		}else {
				    			log.error("Error retrieving diseases tagged for sentence " + sentence.getSentenceId() + " in file: " + file_to_classify.getName() );
				    			log.error("The tagged line is" + data);
				    		}
				    	}
					}
				}
			} else {
				log.error("File not found " + customEntityNameTagger.getTaggerSentencePath() + File.separator + file_to_classify.getName());
			}
		}
		
	}

	private EntityInstanceFound retrieveTag(String[] data, String[] columnNames) {
		try {
			String id = data[0];
			Integer start = new Integer(data[1]);
			Integer end = new Integer(data[2]);
			String text = data[3];
			String entityType = data[4];
			List<ReferenceValue> referenceValues = new ArrayList<ReferenceValue>();
			for (int i = 5; i < columnNames.length; i++) {
				String name = columnNames[i];
				String value = data[i];
				if(value!=null && !value.trim().equals("null")) {
					ReferenceValue key_val = new ReferenceValue(name, value);
					referenceValues.add(key_val);
				}
			}
			EntityInstance entityInstance = new EntityInstance(text ,entityType, referenceValues);
			EntityInstanceFound found = new EntityInstanceFound(start, end, entityInstance, "trivial", "trivial");
			return found;
		} catch(Exception e) {
			log.error("Error reading custom tag tagged line " + data ,e);
		}
		return null;
	}
	
}
