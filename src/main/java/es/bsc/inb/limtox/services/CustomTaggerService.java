package es.bsc.inb.limtox.services;

import java.io.File;
import java.util.List;

import es.bsc.inb.limtox.model.CustomEntityNameTagger;
import es.bsc.inb.limtox.model.Document;
import es.bsc.inb.limtox.model.Section;

public interface CustomTaggerService {
	
	public void execute(List<CustomEntityNameTagger> customsTaggers, File file_to_classify, Document document, Section section);
	
}
