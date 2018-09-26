package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cytochrome Chemical Compound Induction Pattern 
 * @author jcorvi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CytochromeChemicalCompoundInductionPattern extends CytochromeChemicalCompoundPattern implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String cyp_induction_pattern_norm;
	
	private String cyp_induction_pattern_id;
	
	private Integer pattern_length;
	
	private String cyp_induction_pattern;

	public String getCyp_induction_pattern_norm() {
		return cyp_induction_pattern_norm;
	}

	public void setCyp_induction_pattern_norm(String cyp_induction_pattern_norm) {
		this.cyp_induction_pattern_norm = cyp_induction_pattern_norm;
	}

	public String getCyp_induction_pattern_id() {
		return cyp_induction_pattern_id;
	}

	public void setCyp_induction_pattern_id(String cyp_induction_pattern_id) {
		this.cyp_induction_pattern_id = cyp_induction_pattern_id;
	}

	public Integer getPattern_length() {
		return pattern_length;
	}

	public void setPattern_length(Integer pattern_length) {
		this.pattern_length = pattern_length;
	}

	public String getCyp_induction_pattern() {
		return cyp_induction_pattern;
	}



	public void setCyp_induction_pattern(String cyp_induction_pattern) {
		this.cyp_induction_pattern = cyp_induction_pattern;
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
	    }else if(cyp_induction_pattern==null || ((CytochromeChemicalCompoundInductionPattern)obj).cyp_induction_pattern==null) {
	    	return false;
	    }else {
	    	if(cyp_induction_pattern.equals(((CytochromeChemicalCompoundInductionPattern)obj).cyp_induction_pattern)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return cyp_induction_pattern.hashCode();
	}
	
	public void toLowerCase() {
		if(cyp_induction_pattern!=null && !cyp_induction_pattern.equals(cyp_induction_pattern.toLowerCase())) {
			cyp_induction_pattern=cyp_induction_pattern.toLowerCase();
		}
		
	}
	
	
}
