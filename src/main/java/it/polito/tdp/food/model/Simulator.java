package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.food.model.Food.StatoPreparazione;

public class Simulator {
	// coda degli eventi
	public PriorityQueue<Event> coda;
	
	//modello del mondo
	List<Stazione> stazioni; //la lista deve essere sempre di size=k, 
	                    //ogni oggetto della lista è associato ad una stazione
	
	//parametri di input
	int k; //un cibo per volta ogni stazione
	Graph<Food,DefaultWeightedEdge>grafo;
	List<Food>preparato;
	private Model model ;
	private List<Food>cibi;
	
	//parametri di output
		private Double tempoPreparazione ;
		private int cibiPreparati ;
	
	
	public Simulator(Graph<Food, DefaultWeightedEdge> graph, Model model, int k) 
	{
		this.grafo = graph ;
		this.model = model ;
		this.k=k;
	}
	
		public void init(Food partenza) {
		 stazioni=new ArrayList<Stazione>();
		 for(int i=0;i<k;i++) {
			 stazioni.add(new Stazione(null,true));
		 }
		 cibi= new ArrayList<Food>(grafo.vertexSet());
		 for(Food cibo: this.cibi)
				cibo.setPrep(StatoPreparazione.DA_PREPARARE);
		 
		this.tempoPreparazione=0.0;
		this.cibiPreparati=0;
		
		coda= new PriorityQueue<Event>();
		List<FoodCalories>vicini= model.calorie5(partenza);
		Collections.sort(vicini);
		for(int i=0; i<this.k && i<vicini.size(); i++) {
			this.stazioni.get(i).setLibera(false);
			this.stazioni.get(i).setFood(vicini.get(i).getF());
			vicini.get(i).getF().setPrep(StatoPreparazione.IN_CORSO);
			
			Event e= new Event(vicini.get(i).getCalorie(),vicini.get(i).getF(),EventType.FINE_PREP,this.stazioni.get(i));
		   coda.add(e);
		   }
		}
	
	public void simula() {
		while(!coda.isEmpty()) {
			Event e=coda.poll();
			this.processEvent(e);
			
		}
	}
	
	private void processEvent(Event e) {
		switch(e.type) {
		
		case INIZIO_PREP:
		List<FoodCalories> vicini=model.calorie5(e.getFood());
		Collections.sort(vicini);
		FoodCalories fc=null;
		for(FoodCalories f: vicini) {
			if(f.getF().getPrep()==StatoPreparazione.DA_PREPARARE) {
				fc=f;
				break;
			}
		}
		
		if(fc!=null) {
			fc.getF().setPrep(StatoPreparazione.IN_CORSO);
			e.getStazione().setLibera(false);
			coda.add(new Event(e.getTime()+fc.getCalorie(),fc.getF(),EventType.FINE_PREP,e.getStazione()));
		}
			break;
			
		case FINE_PREP:
			this.cibiPreparati++;
			e.getStazione().setLibera(true);
			e.getFood().setPrep(StatoPreparazione.PREPARATO);
			this.tempoPreparazione=e.getTime();
			coda.add(new Event(e.getTime(),e.getFood(),EventType.INIZIO_PREP,e.getStazione()));
		
			break;
		}
		
		
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

	public int getCibiPreparati() {
		return cibiPreparati;
	}
	
	

}
