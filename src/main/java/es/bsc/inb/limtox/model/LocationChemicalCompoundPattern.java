package es.bsc.inb.limtox.model;

public class LocationChemicalCompoundPattern implements LimtoxEntity{

	private String location_pattern_norm;
	
	private String substrate_pattern_id;
	
	private Integer pattern_length;
	
	private String location_pattern;

	public String getLocation_pattern_norm() {
		return location_pattern_norm;
	}

	public void setLocation_pattern_norm(String location_pattern_norm) {
		this.location_pattern_norm = location_pattern_norm;
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

	public String getLocation_pattern() {
		return location_pattern;
	}

	public void setLocation_pattern(String location_pattern) {
		this.location_pattern = location_pattern;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}
