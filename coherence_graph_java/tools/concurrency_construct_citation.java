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

public class concurrency_construct_citation {
	 private ExecutorService exec;  
	    //这个地方，纯粹是“一厢情愿”，“并行执行”不受咱们控制，取决于操作系统的“态度”  
	    private int cpuCoreNumber;  
	    private List<Future<Double>> tasks = new ArrayList<Future<Double>>();  
	    class SumCalculator implements Callable<Double> {  
	        private myGraph graph;  
	        private int start;  
	        private int end;  
	 
	  
	        public SumCalculator(myGraph graph,int start, int end) {  
	            
	            this.graph = graph;  
	            this.start = start;  
	            this.end = end;  

	        }  
	  
	  
	        public Double call() throws Exception {  
	        	
	        	Double i=0.0;
	            List<Vertex> vlist = graph.vertexs;
	           
	            for(int i1=start;i1< end;i1++){
	            	Vertex v=vlist.get(i1);
	            	 JSONObject paper= (JSONObject) v.get_Value();
	                 try{
	                     JSONArray title_out= (JSONArray) paper.get("title");
	                     JSONArray ref_titles=(JSONArray) paper.get("ref_title");             
	                     for(int i2=0;i2<ref_titles.length();i2++){
	                         String ref_title=ref_titles.get(i2).toString();
	                         for(Vertex v1:vlist){
	                             JSONObject paper1= (JSONObject) v1.get_Value();
	                             try{
//	                                   System.out.println("title");
//	                                   System.out.println(paper1.get("title"));
	                                 JSONArray title_in= (JSONArray) paper1.get("title");
	                                 String title=title_in.get(0).toString();
//	                                 Object temp=paper1.get("title");
//	                                 System.out.println(title);
//	                                 String title=temp.toString().substring(2, temp.toString().length()-2);
	                                 double compare_score=word_seg.compare(ref_title,title);
	                                 if(compare_score>80){	
//	                                 	System.out.println("title out");
//	                                 	System.out.println(title_out);                                
//	                                     System.out.println(ref_title);
//	                                     System.out.println(title);
//	                                     System.out.println(compare_score);
//	     	                            System.out.println("score");
//	                                     double score_v = Double.parseDouble(paper.get("score").toString());
	                                     double score_v1 = Double.parseDouble(paper1.get("score").toString());
//	                                     System.out.println(score_v+" "+score_v1);
	                                     //v引用了v1,则v1指向v 
	                                     graph.insertEdge(v1.get_vvalue(), v.get_vvalue(), score_v1, "cite");
	                                 }
	                             }catch(Exception e3){
	                             }
	                         }
	                     }
	                 }
	                 catch(Exception e1){
	                 }
	            }
	            
	            for(int i1=start;i1< end;i1++){
	            	Vertex v=vlist.get(i1);
	                JSONObject paper= (JSONObject) v.get_Value();
	                try{
	                    JSONArray authors= (JSONArray) paper.get("author");
	                    String author=authors.get(0).toString();
	                    String id= paper.get("id").toString();
	                    for(Vertex v1:vlist){
	                        JSONObject paper1= (JSONObject) v1.get_Value();
	                        JSONArray authors1= (JSONArray) paper1.get("author"); 
	                        String author1=authors1.get(0).toString();
	                        String id1= paper1.get("id").toString();
	    //same author and not same paper
	                        if(author.compareTo(author1)==0&&id.compareTo(id1)!=0){
//	                    	System.out.println("author");
//	                        System.out.println(author);
//	                        System.out.println(paper.get("id"));
//	                        System.out.println(author1);
//	                        System.out.println(paper1.get("id"));
	                            double score_v1 = Double.parseDouble(paper1.get("score").toString());
	                            graph.insertEdge(v1.get_vvalue(), v.get_vvalue(), score_v1, "same_author");
	                        }
	                    }
	                }
	                catch(Exception e1){
//	                    System.out.println("no authors");
	                }
	            }
	            return i;
	        }  
	    }  
	    
	    public concurrency_construct_citation() {  
	        cpuCoreNumber = Runtime.getRuntime().availableProcessors();  
	        exec = Executors.newFixedThreadPool(cpuCoreNumber);  
	    }  
	    
	    public Double sum(myGraph graph) {  
	        // 根据CPU核心个数拆分任务，创建FutureTask并提交到Executor  
	        for (int i = 0; i < cpuCoreNumber; i++) {  
	            List<Vertex> vlist = graph.vertexs;

	            int increment = vlist.size() / cpuCoreNumber+1;  
	            int start = increment * i;  
	            int end = increment * i + increment;  
	            if (end > vlist.size())  
	                end = vlist.size();  

	            SumCalculator subCalc = new SumCalculator(graph,start, end);  
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
