package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Taxonomy implements LimtoxEntity {
	
	private Integer keyId;
	
	private Integer tax_id;
	
	private String name;
	
	private String className;
	
	private String uniqueName;

	public Taxonomy(Integer tax_id, String name, String uniqueName,String className) {
		super();
		this.tax_id = tax_id;
		this.name = name;
		this.uniqueName = uniqueName;
		this.className = className;
	}

	public Taxonomy() {}
	
	public Integer getKeyId() {
		return keyId;
	}

	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}

	public Integer getTax_id() {
		return tax_id;
	}

	public void setTax_id(Integer tax_id) {
		this.tax_id = tax_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
