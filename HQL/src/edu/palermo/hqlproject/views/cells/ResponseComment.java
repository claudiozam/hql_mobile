package edu.palermo.hqlproject.views.cells;

import edu.palermo.hqlproject.server.models.Analize;

public class ResponseComment extends OneComment {

	private Analize data;
	
	public ResponseComment(boolean left, Analize data) {
		super(left, data.getResponseData().getSimpleText());
		this.data = data;
	}

	public Analize getData() {
		return data;
	}

	public void setData(Analize data) {
		this.data = data;
	}

}
