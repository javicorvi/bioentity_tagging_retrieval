package es.bsc.inb.limtox.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Cytochrome Chemical Compound Metabolism Pattern 
 * @author jcorvi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CytochromeChemicalCompoundMetabolismPattern extends CytochromeChemicalCompoundPattern implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String substrate_pattern_norm;
	
	private String substrate_pattern_id;
	
	private Integer pattern_length;
	
	private String substrate_pattern;

	public String getSubstrate_pattern_norm() {
		return substrate_pattern_norm;
	}

	public void setSubstrate_pattern_norm(String substrate_pattern_norm) {
		this.substrate_pattern_norm = substrate_pattern_norm;
	}

	public String getSubstrate_pattern_id() {
		return substrate_pattern_id;
	}

	public void setSubstrate_pattern_id(String substrate_pattern_id) {
		this.substrate_pattern_id = substrate_pattern_id;
	}

	public Integer getPattern_length() {
		return pattern_length;
	}

	public void setPattern_length(Integer pattern_length) {
		this.pattern_length = pattern_length;
	}

	public String getSubstrate_pattern() {
		return substrate_pattern;
	}

	public void setSubstrate_pattern(String substrate_pattern) {
		this.substrate_pattern = substrate_pattern;
	}

	
	
	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public void toLowerCase() {
		if(substrate_pattern!=null && !substrate_pattern.equals(substrate_pattern.toLowerCase())) {
			substrate_pattern=substrate_pattern.toLowerCase();
		}
		
	}
	

	@Override
    public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }else if(substrate_pattern==null || ((CytochromeChemicalCompoundMetabolismPattern)obj).substrate_pattern==null) {
	    	return false;
	    }else {
	    	if(substrate_pattern.equals(((CytochromeChemicalCompoundMetabolismPattern)obj).substrate_pattern)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return substrate_pattern.hashCode();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
}
