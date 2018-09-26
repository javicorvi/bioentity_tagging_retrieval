package es.bsc.inb.limtox.model;

public class EntityInstanceFound {

	private Section section;
	
	private Integer start;
	
	private Integer end;
	
	private EntityInstance entityInstance;
	
	private String mentionType;
	
	private String mentionSource;
	
	
	
	
	
	public EntityInstanceFound(Integer start, Integer end, EntityInstance entityInstance,
			String mentionType, String mentionSource) {
		super();
		this.start = start;
		this.end = end;
		this.entityInstance = entityInstance;
		this.mentionType = mentionType;
		this.mentionSource = mentionSource;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public EntityInstance getEntityInstance() {
		return entityInstance;
	}

	public void setEntityInstance(EntityInstance entityInstance) {
		this.entityInstance = entityInstance;
	}

	public String getMentionType() {
		return mentionType;
	}

	public void setMentionType(String mentionType) {
		this.mentionType = mentionType;
	}

	public String getMentionSource() {
		return mentionSource;
	}

	public void setMentionSource(String mentionSource) {
		this.mentionSource = mentionSource;
	}
	
	
	
	
	
}
