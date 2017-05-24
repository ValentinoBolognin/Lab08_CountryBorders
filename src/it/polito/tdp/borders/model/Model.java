package it.polito.tdp.borders.model;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	BordersDAO bordersDAO;
	private UndirectedGraph<Country, DefaultEdge> graph ;

	public Model() {
		this.bordersDAO = new BordersDAO();
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;
	}

	public void creaGrafo(int anno){

		Graphs.addAllVertices(graph, bordersDAO.loadAllCountries()) ;
		
		for(Border b: bordersDAO.getCountryPairs(anno)) {
			graph.addEdge(b.getC1(), b.getC2()) ;
		}
		
	}

	public List<Country> trovaStati(){
		return bordersDAO.loadAllCountries();
	}
	
	public int statiConfinanti(Country stato) {
		int grado = 0;
		
		for(DefaultEdge e : graph.edgesOf(stato) )
			grado = graph.degreeOf(stato);
		
		return grado;
	}
	
	public int componentiConnesse() {
		int componentiConnesse = 0;
		
		ConnectivityInspector ci = new ConnectivityInspector(graph);
		
		componentiConnesse = ci.connectedSets().size();
		
		return componentiConnesse;
	}

}
