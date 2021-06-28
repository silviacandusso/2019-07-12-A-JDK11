package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	public enum EventType{
		INIZIO_PREP,
		FINE_PREP;			
	}
	private Double time;
	Food food;
	EventType type;
	Stazione stazione;
	
	public Event(Double time, Food food, EventType type, Stazione stazione) {
		this.time=time;
		this.food=food;
		this.type=type;
		this.stazione=stazione;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Stazione getStazione() {
		return stazione;
	}

	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}
	  

}
