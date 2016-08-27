package tools;


import java.util.Comparator;
import graph.myGraph.Chain;
/*
 * Default class ChainCoherenceComparator, used in CoherenceGraph class.
 */
public class ChainCoherenceComparator implements Comparator<Chain> {
    public int compare(Chain o1, Chain o2) {
    	if (o1.getCoherence() < o2.getCoherence()) {
    		return -1;
    	}
    	if (o1.getCoherence() > o2.getCoherence()) {
    		return 1;
    	}
    	return 0;
    }
}
