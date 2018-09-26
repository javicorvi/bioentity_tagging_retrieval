package es.bsc.inb.limtox.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MeshChemicalCompound implements LimtoxEntity {
	
	private Integer id;
	
	@JsonProperty("Chemical_name")
	private String name;
	
	private String ui;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUi() {
		return ui;
	}

	public void setUi(String ui) {
		this.ui = ui;
	}


	
	
	
}
