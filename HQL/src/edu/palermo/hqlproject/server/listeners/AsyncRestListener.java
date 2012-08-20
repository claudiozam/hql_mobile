package edu.palermo.hqlproject.server.listeners;

public interface AsyncRestListener<T> {
	public void restCallDidFinishSucessfuly(T result);
	public void restCallDidFinishWithErrors();
}
