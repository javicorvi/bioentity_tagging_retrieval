package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Marker implements LimtoxEntity{

	private Integer id;
	
	private Integer keyId;
	
	private String marker_full_name;
	
	private String marker_namespace;
	
	private String marker_identifier;
	
	private String concept_namespace;
	
	private String marker_type_name;
	
		
	public String getMarker_full_name() {
		return marker_full_name;
	}

	public void setMarker_full_name(String marker_full_name) {
		this.marker_full_name = marker_full_name;
	}


	public String getMarker_namespace() {
		return marker_namespace;
	}


	public void setMarker_namespace(String marker_namespace) {
		this.marker_namespace = marker_namespace;
	}


	public String getMarker_identifier() {
		return marker_identifier;
	}


	public void setMarker_identifier(String marker_identifier) {
		this.marker_identifier = marker_identifier;
	}


	public String getConcept_namespace() {
		return concept_namespace;
	}


	public void setConcept_namespace(String concept_namespace) {
		this.concept_namespace = concept_namespace;
	}


	public String getMarker_type_name() {
		return marker_type_name;
	}


	public void setMarker_type_name(String marker_type_name) {
		this.marker_type_name = marker_type_name;
	}


	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void toLowerCase() {
		if(marker_full_name!=null && !marker_full_name.equals(marker_full_name.toLowerCase())) {
			marker_full_name=marker_full_name.toLowerCase();
		}
		
	}
	

	@Override
    public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }else if(marker_full_name==null || ((Marker)obj).marker_full_name==null) {
	    	return false;
	    }else {
	    	if(marker_full_name.equals(((Marker)obj).marker_full_name)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return marker_full_name.hashCode();
	}
	
	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
