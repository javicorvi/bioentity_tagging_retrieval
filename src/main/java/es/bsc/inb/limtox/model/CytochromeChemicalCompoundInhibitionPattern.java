package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cytochrome Chemical Compound Inhibition Pattern 
 * @author jcorvi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CytochromeChemicalCompoundInhibitionPattern extends CytochromeChemicalCompoundPattern implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String cyp_inhibition_pattern_norm;
	
	private String cyp_inhibition_pattern_id;
	
	private Integer pattern_length;
	
	private String cyp_inhibition_pattern;

	

	public String getCyp_inhibition_pattern_norm() {
		return cyp_inhibition_pattern_norm;
	}



	public void setCyp_inhibition_pattern_norm(String cyp_inhibition_pattern_norm) {
		this.cyp_inhibition_pattern_norm = cyp_inhibition_pattern_norm;
	}



	public String getCyp_inhibition_pattern_id() {
		return cyp_inhibition_pattern_id;
	}



	public void setCyp_inhibition_pattern_id(String cyp_inhibition_pattern_id) {
		this.cyp_inhibition_pattern_id = cyp_inhibition_pattern_id;
	}



	public Integer getPattern_length() {
		return pattern_length;
	}



	public void setPattern_length(Integer pattern_length) {
		this.pattern_length = pattern_length;
	}



	public String getCyp_inhibition_pattern() {
		return cyp_inhibition_pattern;
	}



	public void setCyp_inhibition_pattern(String cyp_inhibition_pattern) {
		this.cyp_inhibition_pattern = cyp_inhibition_pattern;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getKeyId() {
		return keyId;
	}



	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	@Override
    public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }else if(cyp_inhibition_pattern==null || ((CytochromeChemicalCompoundInhibitionPattern)obj).cyp_inhibition_pattern==null) {
	    	return false;
	    }else {
	    	if(cyp_inhibition_pattern.equals(((CytochromeChemicalCompoundInhibitionPattern)obj).cyp_inhibition_pattern)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return cyp_inhibition_pattern.hashCode();
	}
	
	public void toLowerCase() {
		if(cyp_inhibition_pattern!=null && !cyp_inhibition_pattern.equals(cyp_inhibition_pattern.toLowerCase())) {
			cyp_inhibition_pattern=cyp_inhibition_pattern.toLowerCase();
		}
		
	}
	
	
	
}
