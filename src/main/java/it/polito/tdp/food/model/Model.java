package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	FoodDao dao;
	Graph <Food, DefaultWeightedEdge>grafo;
	List<Food>vertici;
	Map<Integer,Food> idMap;
	//simulazione
	Double tempoTot;
	int cibiPreparati;
	
	
	public Model() {
		dao= new FoodDao();
		idMap= new HashMap<Integer,Food>();
		
	}
	
	public void creaGrafo(int porz) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//aggiungi i vertici
		vertici= dao.listAllFoods(porz,idMap);
		Graphs.addAllVertices(grafo,vertici);
		//aggiungo archi
		
		for(Adiacenza a: dao.getAdiacenze(idMap)) {
			Graphs.addEdgeWithVertices(grafo, a.getF1(), a.getF2(),a.getPeso());
			
		}
		
	}
	public int getVertici() {
		return this.grafo.vertexSet().size();
	}
	public int getArchi() {
		return this.grafo.edgeSet().size();
	}
	public List<Food> getVerticiv(){
		return vertici;
	}

	public List<FoodCalories> calorie5(Food f) {
		List<FoodCalories> result= new ArrayList<FoodCalories>();
		List<Food>vicini=Graphs.neighborListOf(grafo,f);
		
		for(Food food: vicini) {
			Double calorie= grafo.getEdgeWeight(grafo.getEdge(food, f));
			result.add(new FoodCalories(food,calorie));
		}
		Collections.sort(result);
		return result;
	
	}
	
	public void simula(int k, Food partenza) {
		Simulator sim= new Simulator(grafo,this,k);
		sim.init(partenza);
		sim.simula();
		tempoTot=sim.getTempoPreparazione();
		cibiPreparati=sim.getCibiPreparati();
		
	}

	public Double getTempoTot() {
		return tempoTot;
	}

	public int getCibiPreparati() {
		return cibiPreparati;
	}
	
	

}
