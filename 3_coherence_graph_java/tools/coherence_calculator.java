package tools;

import java.util.ArrayList;

import graph.myGraph;
import graph.myGraph.Vertex;

public class coherence_calculator {

	public static double calculateCoherence(ArrayList<Vertex> chain, myGraph graph) {
		
		if (chain == null || chain.size() < 2 ) {
			return 0.0;
		}
		double coherence = slove(chain, graph);	
		return coherence;
	}
	
	private static Double slove(ArrayList<Vertex> chain, myGraph graph){	
		int numPapers = chain.size();
		double coherence=0.0;
		for (int p = 0; p < numPapers - 1; p++) {
			Vertex p_current = chain.get(p);
			Vertex p_after = chain.get(p + 1);
			double coherence_temp=graph.getWeight(p_current.get_vvalue(),p_after.get_vvalue(),"ancestral");
			if(coherence<coherence_temp){
				coherence=coherence_temp;
			}
		}	
		return coherence;
	}
}