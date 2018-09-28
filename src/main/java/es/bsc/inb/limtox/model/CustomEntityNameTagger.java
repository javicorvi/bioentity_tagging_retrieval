package es.bsc.inb.limtox.model;

public class CustomEntityNameTagger {

	private String taggerName;
	
	private String taggerBlocksPath;
	
	private String taggerSentencePath;

	
	
	public CustomEntityNameTagger(String taggerName, String taggerBlocksPath, String taggerSentencePath) {
		super();
		this.taggerName = taggerName;
		this.taggerBlocksPath = taggerBlocksPath;
		this.taggerSentencePath = taggerSentencePath;
	}

	public String getTaggerName() {
		return taggerName;
	}

	public void setTaggerName(String taggerName) {
		this.taggerName = taggerName;
	}

	public String getTaggerBlocksPath() {
		return taggerBlocksPath;
	}

	public void setTaggerBlocksPath(String taggerBlocksPath) {
		this.taggerBlocksPath = taggerBlocksPath;
	}

	public String getTaggerSentencePath() {
		return taggerSentencePath;
	}

	public void setTaggerSentencePath(String taggerSentencePath) {
		this.taggerSentencePath = taggerSentencePath;
	}
	
	
	
}
