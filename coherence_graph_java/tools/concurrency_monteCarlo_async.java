package tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;  
import java.util.concurrent.CompletionService;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ExecutorCompletionService;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;

import graph.myGraph;
import graph.myGraph.Node;
import graph.myGraph.Vertex;
import main.storyline;

public class concurrency_monteCarlo_async {

	 	private ExecutorService exec;  
	    //这个地方，纯粹是“一厢情愿”，“并行执行”不受咱们控制，取决于操作系统的“态度”  
	    private int cpuCoreNumber;  
	    private CompletionService<Double> completionService;  

	    
//	    private List<Future<Double>> tasks = new ArrayList<Future<Double>>();  
	    class SumCalculator implements Callable<Double> {  
	        private myGraph Gc;  
	        private Vertex pi;  
	        private Vertex pj;
	        private int N;
	  
	  
	        public SumCalculator(myGraph Gc,Vertex pi,Vertex pj,int N) {  
	            this.Gc = Gc;  
	            this.pi = pi;  
	            this.pj = pj; 
	            this.N = N;  

	        }  
	  
	  
	        public Double call() throws Exception {  
	            double sum = 0;  
	            LinkedList<Node> elist_i = pi.elist;
	            for (Node node : elist_i) {
	            	if (node.getEvalue()==pj.get_vvalue()){
	            		return node.getWeight();
	            	}
	            }
	            LinkedList<Node> elist_j = pj.elist;
	            for (Node node : elist_j) {
	            	if (node.getEvalue()==pi.get_vvalue()){
	            		return node.getWeight();
	            	}
	            }        
	            int count=0;
	            int k=0;
	            while (k<N){
	            	myGraph Grc=new myGraph(10);
	            	Grc=storyline.GenerateRandomSubgraph(Gc);
	            	//在新图中重新获取pi,pj的边
	                LinkedList<Node> new_elist_i = Grc.vertexs.get(pi.get_vvalue()).elist;
	                LinkedList<Node> new_elist_j = Grc.vertexs.get(pj.get_vvalue()).elist;
	            	int ancestor=0;
	            		for (Node node_i : new_elist_i) {
	            			for (Node node_j : new_elist_j) {
	    	        			if(node_i.getEvalue()==node_j.getEvalue()){
	    	        				ancestor=1;
	    	        				break;
	    	        			}
	            			}
	            		}
	            	if(ancestor==1){
	            		count++;
	            	}
	            	k++;       		        	
	            }   
	            
		          double re1=(double) count/N;

		          Double re= (double) re1;
	        	return  re;
	           // return sum;  
	        }  
	    }  
	    
	    public concurrency_monteCarlo_async() {  
	        cpuCoreNumber = Runtime.getRuntime().availableProcessors();  
	        exec = Executors.newFixedThreadPool(cpuCoreNumber);
	        completionService = new ExecutorCompletionService<Double>(exec);  

	    }  
	    public Double sum(myGraph Gc,Vertex pi,Vertex pj,int N) {  
	        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor  
	        for (int i = 0; i < cpuCoreNumber; i++) {  
	            int increment = N/cpuCoreNumber; 
	            
	      
	            SumCalculator subCalc = new SumCalculator(Gc,pi,pj,increment);  
	            if (!exec.isShutdown()) {  
	                completionService.submit(subCalc);  
	            }  
	              
	        }  
	        return getResult();  
	    }  
//	    public Double sum(myGraph Gc,Vertex pi,Vertex pj,int N) {  
//	        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor  
//	        for (int i = 0; i < cpuCoreNumber; i++) {  
//	            int increment = N/cpuCoreNumber;  
////	            int start = increment * i;  
////	            int end = increment * i + increment;  
////	            if (end > numbers.length)  
////	                end = numbers.length;  
//	            System.out.println("increment:  "+increment);
//
//	            SumCalculator subCalc = new SumCalculator(Gc,pi,pj,increment);  
//	            FutureTask<Double> task = new FutureTask<Double>(subCalc);  
//	            tasks.add(task);  
//	            if (!exec.isShutdown()) {  
//	                exec.submit(task);  
//	            }  
//	        }  
//	        return getResult();  
//	    }  
	    /** 
	     * 迭代每个只任务，获得部分和，相加返回 
	     */  
//	    public Double getResult() {  
//	    	Double result = 0.0;  
//	        for (Future<Double> task : tasks) {  
//	            try {  
//	                // 如果计算未完成则阻塞  
//	            	Double subSum = task.get();  
//	                result += subSum;  
//	            } catch (InterruptedException e) {  
//	                e.printStackTrace();  
//	            } catch (ExecutionException e) {  
//	                e.printStackTrace();  
//	            }  
//	        }  
//	        return result/cpuCoreNumber;  
//	    }  
	    public Double getResult() {  
	    	Double result = 0.0;  
	        for (int i = 0; i < cpuCoreNumber; i++) {              
	            try {  
	            	Double subSum = completionService.take().get();  
	                result += subSum;  
//	                if(result>0){
//	                System.out.println("subSum="+subSum+",result="+result);  
//	                }
	            } catch (InterruptedException e) {  
	                e.printStackTrace();  
	            } catch (ExecutionException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result/cpuCoreNumber;  
	    }  
	    
	    
	  
	  
	    public void close() {  
	        exec.shutdown();  
	    } 
}
