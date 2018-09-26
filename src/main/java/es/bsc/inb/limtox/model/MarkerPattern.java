package es.bsc.inb.limtox.model;
/**
 * Cytochrome Chemical Compound Induction Pattern 
 * @author jcorvi
 *
 */
public class MarkerPattern implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String pattern_type;
	
	private String marker_pattern;
	
	private Integer pattern_trigger_term;
	
	private String marker_pattern_norm;

	private String marker_pattern_id;
	
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

	public String getPattern_type() {
		return pattern_type;
	}

	public void setPattern_type(String pattern_type) {
		this.pattern_type = pattern_type;
	}

	public String getMarker_pattern() {
		return marker_pattern;
	}

	public void setMarker_pattern(String marker_pattern) {
		this.marker_pattern = marker_pattern;
	}

	public Integer getPattern_trigger_term() {
		return pattern_trigger_term;
	}

	public void setPattern_trigger_term(Integer pattern_trigger_term) {
		this.pattern_trigger_term = pattern_trigger_term;
	}

	public String getMarker_pattern_norm() {
		return marker_pattern_norm;
	}

	public void setMarker_pattern_norm(String marker_pattern_norm) {
		this.marker_pattern_norm = marker_pattern_norm;
	}

	public String getMarker_pattern_id() {
		return marker_pattern_id;
	}

	public void setMarker_pattern_id(String marker_pattern_id) {
		this.marker_pattern_id = marker_pattern_id;
	}

	@Override
    public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }else if(marker_pattern==null || ((MarkerPattern)obj).marker_pattern==null) {
	    	return false;
	    }else {
	    	if(marker_pattern.equals(((MarkerPattern)obj).marker_pattern)) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	}
	
	@Override
	public int hashCode() {
	    return marker_pattern.hashCode();
	}
	
	public void toLowerCase() {
		if(marker_pattern!=null && !marker_pattern.equals(marker_pattern.toLowerCase())) {
			marker_pattern=marker_pattern.toLowerCase();
		}
		
	}
	
	
}
