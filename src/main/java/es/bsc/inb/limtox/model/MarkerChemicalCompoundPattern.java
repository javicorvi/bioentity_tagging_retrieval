package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Cytochrome Chemical Compound Inhibition Pattern 
 * @author jcorvi
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkerChemicalCompoundPattern implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String pattern_type;
	
	private String marker_pattern;
	
	private Integer pattern_length;
	
	private String pattern_trigger_term;

	private String marker_pattern_norm;

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

	public Integer getPattern_length() {
		return pattern_length;
	}

	public void setPattern_length(Integer pattern_length) {
		this.pattern_length = pattern_length;
	}

	public String getPattern_trigger_term() {
		return pattern_trigger_term;
	}

	public void setPattern_trigger_term(String pattern_trigger_term) {
		this.pattern_trigger_term = pattern_trigger_term;
	}

	public String getMarker_pattern_norm() {
		return marker_pattern_norm;
	}

	public void setMarker_pattern_norm(String marker_pattern_norm) {
		this.marker_pattern_norm = marker_pattern_norm;
	}

	
	
}
