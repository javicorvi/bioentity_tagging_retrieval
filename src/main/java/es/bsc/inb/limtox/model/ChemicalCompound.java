package es.bsc.inb.limtox.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChemicalCompound implements LimtoxEntity {
	
	private Integer id;
	
	private Integer keyId;
	
	@JsonProperty("Chemical_name")
	private String name;
	
	private Integer nameToStruct;
	
	@JsonProperty("ChemIDplus_id")
	private String chemPlusId;
	
	@JsonProperty("ChEBI_id")
	private String chebi;
	
	@JsonProperty("CAS_registry")
	private String casRegistryNumber;
	
	@JsonProperty("PubChem_compound_id")
	private String pubChemCompundId;
	
	@JsonProperty("PubChem_substance_id")
	private String pubChemSubstance;
	
	@JsonProperty("IUPAC_International_Chemical_id")
	private String inchi;
	
	@JsonProperty("Drugback_id")
	private String drugBankId;
	
	@JsonProperty("Human_metabolome_id")
	private String humanMetabolome;
	
	@JsonProperty("KEGG_compound_id")
	private String keggCompoundId; 
	
	@JsonProperty("MeSH_substance_id")
	private String meshSubstanceId; 
	
	@JsonProperty("nrDBIds")
	private String nrDBIds;
	
	@JsonProperty("SMILES")
	private String smile;
	
	
	public boolean equals(Object word_token) {
		if(word_token==null) {
			return false;
		}else {
			if(word_token.equals(this.name)) {
				return true;
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getNameToStruct() {
		return nameToStruct;
	}


	public void setNameToStruct(Integer nameToStruct) {
		if(nameToStruct!=null && nameToStruct.equals("NaN")) {
			this.nameToStruct = null;
		}else {
			this.nameToStruct = nameToStruct;
		}
		
	}


	public String getChemPlusId() {
		return chemPlusId;
	}


	public void setChemPlusId(String chemPlusId) {
		if(chemPlusId!=null && chemPlusId.equals("NaN")) {
			this.chemPlusId = null;
		}else {
			this.chemPlusId = chemPlusId;
		}
	}


	public String getChebi() {
		return chebi;
	}


	public void setChebi(String chebi) {
		if(chebi!=null && chebi.equals("NaN")) {
			this.chebi = null;
		}else {
			this.chebi = chebi;
		}
	}


	public String getCasRegistryNumber() {
		return casRegistryNumber;
	}


	public void setCasRegistryNumber(String casRegistryNumber) {
		if(casRegistryNumber!=null && casRegistryNumber.equals("NaN")) {
			this.casRegistryNumber = null;
		}else {
			this.casRegistryNumber = casRegistryNumber;
		}
	}


	public String getPubChemCompundId() {
		return pubChemCompundId;
	}


	public void setPubChemCompundId(String pubChemCompundId) {
		if(pubChemCompundId!=null && pubChemCompundId.equals("NaN")) {
			this.pubChemCompundId = null;
		}else {
			this.pubChemCompundId = pubChemCompundId;
		}
	}


	public String getPubChemSubstance() {
		return pubChemSubstance;
	}


	public void setPubChemSubstance(String pubChemSubstance) {
		if(pubChemSubstance!=null && pubChemSubstance.equals("NaN")) {
			this.pubChemSubstance = null;
		}else {
			this.pubChemSubstance = pubChemSubstance;
		}
	}


	public String getInchi() {
		return inchi;
	}


	public void setInchi(String inchi) {
		if(inchi!=null && inchi.equals("NaN")) {
			this.inchi = null;
		}else {
			this.inchi = inchi;
		}
	}


	public String getDrugBankId() {
		return drugBankId;
	}


	public void setDrugBankId(String drugBankId) {
		if(drugBankId!=null && drugBankId.equals("NaN")) {
			this.drugBankId = null;
		}else {
			this.drugBankId = drugBankId;
		}
	}


	public String getHumanMetabolome() {
		return humanMetabolome;
	}


	public void setHumanMetabolome(String humanMetabolome) {
		if(humanMetabolome!=null && humanMetabolome.equals("NaN")) {
			this.humanMetabolome = null;
		}else {
			this.humanMetabolome = humanMetabolome;
		}
	}


	public String getKeggCompoundId() {
		return keggCompoundId;
	}


	public void setKeggCompoundId(String keggCompoundId) {
		if(keggCompoundId!=null && keggCompoundId.equals("NaN")) {
			this.keggCompoundId = null;
		}else {
			this.keggCompoundId = keggCompoundId;
		}
	}


	public String getMeshSubstanceId() {
		return meshSubstanceId;
	}


	public void setMeshSubstanceId(String meshSubstanceId) {
		if(meshSubstanceId!=null && meshSubstanceId.equals("NaN")) {
			this.meshSubstanceId = null;
		}else {
			this.meshSubstanceId = meshSubstanceId;
		}
	}


	public String getNrDBIds() {
		return nrDBIds;
	}


	public void setNrDBIds(String nrDBIds) {
		if(nrDBIds!=null && nrDBIds.equals("NaN")) {
			this.nrDBIds = null;
		}else {
			this.nrDBIds = nrDBIds;
		}
	}


	public String getSmile() {
		return smile;
	}


	public void setSmile(String smile) {
		if(smile!=null && smile.equals("NaN")) {
			this.smile = null;
		}else {
			this.smile = smile;
		}
	}


	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public Integer getId() {
		return id;
	}


	public Integer getKeyId() {
		return keyId;
	}


	public void setKeyId(Integer keyId) {
		this.keyId = keyId;
	}


	public void toLowerCase() {
		if(name!=null && !name.equals(name.toLowerCase())) {
			name=name.toLowerCase();
		}
	}
	
	@Override
	public int hashCode() {
	    return name.hashCode();
	}
	
	
}
