package es.bsc.inb.limtox.model;

import java.util.List;

public class MarkerSentence extends ReletavantTermSentence {
	
	private Marker marker;
	
	
	public MarkerSentence() {}
	
	public MarkerSentence(Marker marker, Float score, Integer quantity, List<Ocurrence> ocurrences, Sentence sentence) {
		super(score, quantity, sentence, ocurrences);
		this.marker = marker;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

}
