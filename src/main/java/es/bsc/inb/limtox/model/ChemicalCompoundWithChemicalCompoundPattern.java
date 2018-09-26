package es.bsc.inb.limtox.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChemicalCompoundWithChemicalCompoundPattern implements LimtoxEntity{

	private Integer keyId;
	
	private Integer id;
	
	private String adverse_pattern;
	
	private String adverse_pattern_id;
	
	private Integer pattern_length;
	
	private String adverse_pattern_norm;

	
	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdverse_pattern() {
		return adverse_pattern;
	}



	public void setAdverse_pattern(String adverse_pattern) {
		this.adverse_pattern = adverse_pattern;
	}



	public String getAdverse_pattern_id() {
		return adverse_pattern_id;
	}



	public void setAdverse_pattern_id(String adverse_pattern_id) {
		this.adverse_pattern_id = adverse_pattern_id;
	}



	public Integer getPattern_length() {
		return pattern_length;
	}



	public void setPattern_length(Integer pattern_length) {
		this.pattern_length = pattern_length;
	}



	public String getAdverse_pattern_norm() {
		return adverse_pattern_norm;
	}



	public void setAdverse_pattern_norm(String adverse_pattern_norm) {
		this.adverse_pattern_norm = adverse_pattern_norm;
	}


	public void toLowerCase() {
		if(adverse_pattern!=null && !adverse_pattern.equals(adverse_pattern.toLowerCase())) {
			adverse_pattern=adverse_pattern.toLowerCase();
		}
		
	}
	

	@Override
    public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }else if(adverse_pattern==null || ((ChemicalCompoundWithChemicalCompoundPattern)obj).adverse_pattern==null) {
	    	return false;
	    }else {
	    	if(adverse_pattern.equals(((ChemicalCompoundWithChemicalCompoundPattern)obj).adverse_pattern)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return adverse_pattern.hashCode();
	}
	
	
	
	
}
