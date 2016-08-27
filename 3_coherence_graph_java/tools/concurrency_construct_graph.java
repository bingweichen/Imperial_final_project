package tools;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;  
import java.util.concurrent.Callable;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.Future;  
import java.util.concurrent.FutureTask;

import org.json.JSONArray;
import org.json.JSONObject;

import graph.myGraph;
import graph.myGraph.Node;
import graph.myGraph.Vertex;
import main.storyline;
import tools.concurrency_monteCarlo.SumCalculator;

public class concurrency_construct_graph {
	 private ExecutorService exec;  
	    //这个地方，纯粹是“一厢情愿”，“并行执行”不受咱们控制，取决于操作系统的“态度”  
	    private int cpuCoreNumber;  
	    private List<Future<Double>> tasks = new ArrayList<Future<Double>>();  
	    class SumCalculator implements Callable<Double> {  
	        private JSONArray doc;
	        private myGraph graph;  
	        private int start;  
	        private int end;  
	 
	  
	        public SumCalculator(JSONArray doc,myGraph graph,int start, int end) {  
	            
	            this.doc = doc;  
	            this.graph = graph;  
	            this.start = start;  
	            this.end = end;  

	        }  
	  
	  
	        public Double call() throws Exception {  
	        	Double i=0.0;
	            for(int i1=start;i1< end;i1++){
	            	JSONObject doc_1= (JSONObject) doc.get(i1);
	            	graph.insertVertext(doc_1, i1);//插入结点
	            	i++;
	            }
	            return i;
	        }  
	    }  
	    
	    public concurrency_construct_graph() {  
	        cpuCoreNumber = Runtime.getRuntime().availableProcessors();  
	        exec = Executors.newFixedThreadPool(cpuCoreNumber);  
	    }  
	    
	    public Double sum(JSONArray doc,myGraph graph) {  
	        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor  
	        for (int i = 0; i < cpuCoreNumber; i++) {  

	            int increment = doc.length() / cpuCoreNumber;  
	            int start = increment * i;  
	            int end = increment * i + increment;  
	            if (end > doc.length())  
	                end = doc.length();  

	            SumCalculator subCalc = new SumCalculator(doc,graph,start, end);  
	            FutureTask<Double> task = new FutureTask<Double>(subCalc);  
	            tasks.add(task);  
	            if (!exec.isShutdown()) {  
	                exec.submit(task);  
	            }  
	        }  
	        return getResult();  
	    }  
	    /** 
	     * 迭代每个只任务，获得部分和，相加返回 
	     */  
	    public Double getResult() {  
	    	Double result = 0.0;  
	        for (Future<Double> task : tasks) {  
	            try {  
	                // 如果计算未完成则阻塞  
	            	Double subSum = task.get();  
	                result += subSum;  
	            } catch (InterruptedException e) {  
	                e.printStackTrace();  
	            } catch (ExecutionException e) {  
	                e.printStackTrace();  
	            }  
	        }  
//	        if (result>0){
//         System.out.println("result= "+result);  
//	        }

	        return result;  
	    }  
	  
	  
	    public void close() {  
	        exec.shutdown();  
	    } 
}
