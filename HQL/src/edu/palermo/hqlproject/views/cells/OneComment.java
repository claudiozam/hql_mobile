package edu.palermo.hqlproject.views.cells;

public class OneComment {
	private boolean left;
	private String comment;

	public OneComment(boolean left, String comment) {
		super();
		this.left = left;
		this.comment = comment;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}