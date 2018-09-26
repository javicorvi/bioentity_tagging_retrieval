package es.bsc.inb.limtox.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Cytochrome implements LimtoxEntity{
	
	private Integer id;
	
	private Integer keyId;
	
	private String entityId;
	
	private String name;
	
	private String type;
	
	private String cytochrome_canonical_root;
	
	private String cytocrome_subfamily_id;
	
	private String cytocrome_protein_id;
	
	private String cytochrome_canonical_symbol;
	
	private String cytocrome_family_id;

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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCytochrome_canonical_root() {
		return cytochrome_canonical_root;
	}

	public void setCytochrome_canonical_root(String cytochrome_canonical_root) {
		this.cytochrome_canonical_root = cytochrome_canonical_root;
	}

	public String getCytocrome_subfamily_id() {
		return cytocrome_subfamily_id;
	}

	public void setCytocrome_subfamily_id(String cytocrome_subfamily_id) {
		this.cytocrome_subfamily_id = cytocrome_subfamily_id;
	}

	public String getCytocrome_protein_id() {
		return cytocrome_protein_id;
	}

	public void setCytocrome_protein_id(String cytocrome_protein_id) {
		this.cytocrome_protein_id = cytocrome_protein_id;
	}

	public String getCytochrome_canonical_symbol() {
		return cytochrome_canonical_symbol;
	}

	public void setCytochrome_canonical_symbol(String cytochrome_canonical_symbol) {
		this.cytochrome_canonical_symbol = cytochrome_canonical_symbol;
	}

	public String getCytocrome_family_id() {
		return cytocrome_family_id;
	}

	public void setCytocrome_family_id(String cytocrome_family_id) {
		this.cytocrome_family_id = cytocrome_family_id;
	}

	
	
	
	
	
}
