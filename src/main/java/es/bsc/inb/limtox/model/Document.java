package es.bsc.inb.limtox.model;

import java.util.ArrayList;
import java.util.List;



public class Document implements LimtoxEntity {

	private Integer id;
	
	private String documentId;
	
	private String title;
	
	private DocumentSource documentSource;
	
	private List<RelevantTopicInformation> relevantTopicsInformation = new ArrayList<RelevantTopicInformation>(); 
	
	private List<Section> sections = new ArrayList<Section>();

	public Document() {
		super();
	}
	
	
	
	public Document(String documentId, DocumentSource documentSource, String title) {
		this.documentId = documentId;
		this.title = title;
		this.documentSource = documentSource;
	}



	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<RelevantTopicInformation> getRelevantTopicsInformation() {
		return relevantTopicsInformation;
	}

	public void setRelevantTopicsInformation(List<RelevantTopicInformation> relevantTopicsInformation) {
		this.relevantTopicsInformation = relevantTopicsInformation;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public DocumentSource getDocumentSource() {
		return documentSource;
	}

	public void setDocumentSource(DocumentSource documentSource) {
		this.documentSource = documentSource;
	}



	public void addRelevantTopicInformation(RelevantTopicInformation relevantTopicInformation) {
		relevantTopicsInformation.add(relevantTopicInformation);
		
	}



	public void addSection(Section section) {
		sections.add(section);
	}



	public Section findSectionByName(String sectionName) {
		if(sections!=null) {
			for (Section section : sections) {
				if(section.getName().equals(sectionName)) {
					return section;
				}
			}
		}
		return null;
	}
	
	
	
	
	
	
}
