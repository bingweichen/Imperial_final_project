package main;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.getjson;
import tools.word_seg;
import graph.myGraph;
import graph.myGraph.Vertex;
import tools.ChainCoherenceComparator;
import graph.myGraph.Chain;
import tools.coherence_calculator;
import tools.concurrency_construct_citation;
import tools.concurrency_construct_graph;
import tools.concurrency_monteCarlo;
import tools.concurrency_monteCarlo_async;
import graph.myGraph.Node;

public class storyline {
	/**
	 * 常量
	 */
	private static double threshold=0.2;
	private static int SUBCHAIN_LENGTH = 3;	
	private static String query="&q=computer%20science";
	
	private static PriorityQueue<Chain> pq;
	private static final int monteCarlo_counts=100;
	
	private static final String test_url="http://localhost:8983/solr/gettingstarted/select?fl=title,author,id,ref_title,date,url,paper_abstract,score&indent=on&q=Artificial%20neural%20network&wt=json&rows=100";
	private static String base_url="http://localhost:8983/solr/gettingstarted/select?fl=title,author,id,ref_title,date,url,paper_abstract,score&indent=on&wt=json&rows=100";
	/**
	 * 计算两次时间差值 毫秒
	 * @param time
	 * @param endTime
	 * @return
	 */
	private static double time(long time, long endTime) {  
        long costTime = endTime-time;  
        BigDecimal bd = new BigDecimal(costTime);  
        BigDecimal unit = new BigDecimal(1L);  
        BigDecimal s= bd.divide(unit,3);  
        return s.doubleValue();  
    } 
	/**
	 * 生成带节点的图
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static myGraph construct_graph(String query)throws JSONException, IOException {
        int n=4;//代表结点个数
        myGraph graph=new myGraph(n);       
        String _url=base_url+query;     
		JSONObject json=getjson.getJsonObject(_url);
        JSONObject docs = (JSONObject) json.get("response");
        JSONArray doc = (JSONArray) docs.get("docs");
        int i=0;
//        
        for(int i1=0;i1<doc.length();i1++){
            JSONObject doc_1= (JSONObject) doc.get(i1);
            graph.insertVertext(doc_1, i);//插入结点
            i++;
        }
//    	concurrency_monteCarlo_async calc = new concurrency_monteCarlo_async();  
//        concurrency_construct_graph calc = new concurrency_construct_graph();
//        Double i_sum = calc.sum(doc,graph);  
//        calc.close(); 
        
//        
//        System.out.println("num of node");
//        System.out.println(i_sum);
        
//      System.out.println("第一遍 :");
//      List<Vertex> vlist = graph.vertexs;
//      for(Vertex v:vlist){
////          JSONObject paper_i= (JSONObject) v.get_Value();
//          System.out.println(" "+v.get_vvalue()+":");
//          try{
////          System.out.println(paper_i.get("title").toString());
//          }catch(Exception e1){
//              System.out.print("no title");
//          }
//
//          LinkedList<Node> elist = v.elist;
//          for (Node node : elist) {
//              System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//          }
//      }
        
        return graph;
	}
	/**
	 * 生成citation图
	 * @param graph
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static myGraph construct_citation_graph(myGraph graph)throws JSONException, IOException {
		//加入cite label
		concurrency_construct_citation calc = new concurrency_construct_citation();
		Double i_sum = calc.sum(graph);  
		calc.close(); 
		
//        List<Vertex> vlist = graph.vertexs;
//        for(Vertex v:vlist){
//            JSONObject paper= (JSONObject) v.get_Value();
//            try{
//                JSONArray title_out= (JSONArray) paper.get("title");
//                JSONArray ref_titles=(JSONArray) paper.get("ref_title");             
//                for(int i2=0;i2<ref_titles.length();i2++){
//                    String ref_title=ref_titles.get(i2).toString();
//                    for(Vertex v1:vlist){
//                        JSONObject paper1= (JSONObject) v1.get_Value();
//                        try{
////                              System.out.println("title");
////                              System.out.println(paper1.get("title"));
//                            JSONArray title_in= (JSONArray) paper1.get("title");
//                            String title=title_in.get(0).toString();
////                            Object temp=paper1.get("title");
////                            System.out.println(title);
////                            String title=temp.toString().substring(2, temp.toString().length()-2);
//                            double compare_score=word_seg.compare(ref_title,title);
//                            if(compare_score>80){	
////                            	System.out.println("title out");
////                            	System.out.println(title_out);                                
////                                System.out.println(ref_title);
////                                System.out.println(title);
////                                System.out.println(compare_score);
////	                            System.out.println("score");
////                                double score_v = Double.parseDouble(paper.get("score").toString());
//                                double score_v1 = Double.parseDouble(paper1.get("score").toString());
////                                System.out.println(score_v+" "+score_v1);
//                                //v引用了v1,则v1指向v 
//                                graph.insertEdge(v1.get_vvalue(), v.get_vvalue(), score_v1, "cite");
//                            }
//                        }catch(Exception e3){
//                        }
//                    }
//                }
//            }
//            catch(Exception e1){
//            }
//        }  
//      //加入same author label
//        for(Vertex v:vlist){
//            JSONObject paper= (JSONObject) v.get_Value();
//            try{
//                JSONArray authors= (JSONArray) paper.get("author");
//                String author=authors.get(0).toString();
//                String id= paper.get("id").toString();
//                for(Vertex v1:vlist){
//                    JSONObject paper1= (JSONObject) v1.get_Value();
//                    JSONArray authors1= (JSONArray) paper1.get("author"); 
//                    String author1=authors1.get(0).toString();
//                    String id1= paper1.get("id").toString();
////same author and not same paper
//                    if(author.compareTo(author1)==0&&id.compareTo(id1)!=0){
////                	System.out.println("author");
////                    System.out.println(author);
////                    System.out.println(paper.get("id"));
////                    System.out.println(author1);
////                    System.out.println(paper1.get("id"));
//                        double score_v1 = Double.parseDouble(paper1.get("score").toString());
//                        graph.insertEdge(v1.get_vvalue(), v.get_vvalue(), score_v1, "same_author");
//                    }
//                }
//            }
//            catch(Exception e1){
////                System.out.println("no authors");
//            }
//        }
        return graph; 
	}
	/**
	 * 为citation图做normalize
	 * @param graph
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static myGraph citation_graph_nomalize(myGraph graph)throws JSONException, IOException {
		//计算各边weight   
	      //输出第二遍图
        List<Vertex> vlist = graph.vertexs;
//	        System.out.println("normalize图（v:v1->v) 和应为1 :");
	        for(Vertex v:vlist){
//	            System.out.println(" "+v.get_vvalue()+"************:");
	            double z=0;//weight 总和 所有指向x
	            int l=0;
	            //找出所有指向v的边 求其关于cite的weight总和 and same author篇数
	            for(Vertex v1:vlist){
	                Node node=graph.search(v1.get_vvalue(),v.get_vvalue(), "cite");
	                if(node!=null){
	                    if(node.getLabel().compareTo("cite")==0){
	                        z+=node.getWeight();
	                    }
	                }
	                //求得有多少篇same author    
	                Node node_same_author=graph.search(v1.get_vvalue(),v.get_vvalue(),"same_author");
	                if(node_same_author!=null){
	                	if(node_same_author.getLabel().compareTo("same_author")==0){
	                        l++;
	                    }   
	                }            
	            }
	            //找出所有指向v的边 求其关于same_author的weight总和
	            for(Vertex v1:vlist){
	                Node node=graph.search(v1.get_vvalue(),v.get_vvalue(),"same_author");
	                if(node!=null){
	                    if(node.getLabel().compareTo("same_author")==0){
	                        z+=(node.getWeight()/l);
	                    }
//	                    System.out.println(" "+v1.vvalue+":");
//	                    System.out.println("  "+node.evalue+"--"+node.weight+"--"+node.label);
	                }
	            }
//	            System.out.println("after normalize");
	            //找出关于cite和same_author所有指向v的边 修改其权重 influence=1/z*weight,influence=(1/l*z)*weight
	            for(Vertex v1:vlist){
	                Node node=graph.search(v1.get_vvalue(),v.get_vvalue(), "cite");
	                if(node!=null){
	                    if(node.getLabel().compareTo("cite")==0){
	                        double weight=node.getWeight()/z;
	                        node.setWeight(weight);
	                    }     
//	                    System.out.println(" "+v1.get_vvalue()+":");
//	                    System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
	                }
	                Node node_same_author=graph.search(v1.get_vvalue(),v.get_vvalue(), "same_author");
	                if(node_same_author!=null){
	                	if(node_same_author.getLabel().compareTo("same_author")==0){
	                        double weight=node_same_author.getWeight()/(z*l);
	                        node_same_author.setWeight(weight);
	                    }
//	                    System.out.println(" "+v1.get_vvalue()+":");
//	                    System.out.println("  "+node_same_author.getEvalue()+"--"+node_same_author.getWeight()+"--"+node_same_author.getLabel());
	                }
	            }
	        }
	        //输出第三遍图
//	        System.out.println("normalize图（v:v->v1) 和不一定 :");
//	        for(Vertex v:vlist){
//	            System.out.println(" "+v.get_vvalue()+":");
//	            LinkedList<Node> elist = v.elist;
//	            for (Node node : elist) {
//	                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//	            }
//	        }
	        return graph;
	}
    /**
     * GenerateRandomSubgraph(Gc) 用随机数获得新的图
     * @param Gc
     * @return
     */
    public static myGraph GenerateRandomSubgraph(myGraph Gc){
    	myGraph Grc=new myGraph(10);     
        List<Vertex> vlist = Gc.vertexs;
        //给新图插入vertex
    	for(Vertex v:vlist){
    		Grc.insertVertext(v.get_Value(),v.get_vvalue());
    	}
    	//给新图插入edge
    	for(Vertex v:vlist){
//            System.out.println(" "+v.vvalue+":");
            LinkedList<Node> elist = v.elist;
            for (Node node : elist) {
            	if(node.getWeight()>=Math.random()){
            		Grc.insertEdge(v.get_vvalue(), node.getEvalue(), node.getWeight(), node.getLabel());
            	}
//                System.out.println("  "+node.evalue+"--"+node.weight+"--"+node.label);
            }
        }
    	return Grc;
    }  
    
    /**
     * monteCarloSimulation(Gc,pi,pj,N) 循环citation图里的任意两个点，返回其ancestral influence的可能性
     * @param Gc 原先citation图
     * @param pi 节点vertex
     * @param pj 节点vertex
     * @param N 砸点次数
     * @return ancestral influence的可能性，0到1之间
     */
    public static double monteCarloSimulation(myGraph Gc,Vertex pi,Vertex pj,int N){
//    	concurrency_monteCarlo_async calc = new concurrency_monteCarlo_async();  
    	concurrency_monteCarlo calc = new concurrency_monteCarlo();
        Double sum = calc.sum(Gc,pi,pj,N);  
        calc.close(); 
        return sum;
//        LinkedList<Node> elist_i = pi.elist;
//        for (Node node : elist_i) {
//        	if (node.getEvalue()==pj.get_vvalue()){
//        		return node.getWeight();
//        	}
//        }
//        LinkedList<Node> elist_j = pj.elist;
//        for (Node node : elist_j) {
//        	if (node.getEvalue()==pi.get_vvalue()){
//        		return node.getWeight();
//        	}
//        }        
//        int count=0;
//        int k=0;
//        while (k<N){
//        	myGraph Grc=new myGraph(10);
//        	Grc=GenerateRandomSubgraph(Gc);
//        	//在新图中重新获取pi,pj的边
//            LinkedList<Node> new_elist_i = Grc.vertexs.get(pi.get_vvalue()).elist;
//            LinkedList<Node> new_elist_j = Grc.vertexs.get(pj.get_vvalue()).elist;
//        	int ancestor=0;
//        		for (Node node_i : new_elist_i) {
//        			for (Node node_j : new_elist_j) {
//	        			if(node_i.getEvalue()==node_j.getEvalue()){
//	        				ancestor=1;
//	        				break;
//	        			}
//        			}
//        		}
//        	if(ancestor==1){
//        		count++;
//        	}
//        	k++;       		        	
//        }    
//        double result=(double) count/N;
//    	return result;
    }
  
    /**
     * 通过蒙特卡洛和随机数 生成带有ancestral influence的图
     * 循环所有的两个不同的点
     * @param graph
     * @return 带有ancestral influence的citation图
     * @throws JSONException
     * @throws IOException
     */
    public static myGraph citation_graph_ancestral(myGraph graph)throws JSONException, IOException {
    	
    	
        List<Vertex> vlist = graph.vertexs;     
        for(Vertex vi:vlist){
            JSONObject paper_i= (JSONObject) vi.get_Value();
            String id_i= paper_i.get("id").toString();
        	 for(Vertex vj:vlist){
        		 JSONObject paper_j= (JSONObject) vj.get_Value();
                 String id_j= paper_j.get("id").toString();
                 if(id_i.equals(id_j)==false){
                	 double weight_ance=monteCarloSimulation(graph,vi,vj,monteCarlo_counts);
                	 if(weight_ance>0){
//                	        System.out.println("weight_ance="+weight_ance); 

                		 graph.insertEdge(vi.get_vvalue(), vj.get_vvalue(), weight_ance, "ancestral");
                	 }
                 }
        	 }
        }
      //输出第五遍图 （有ancestral influence）
//        System.out.println("Gc图（v:v->v1) 有ancestral influence :");
//        for(Vertex v:vlist){
//            JSONObject paper_i= (JSONObject) v.get_Value();
//            System.out.println(" "+v.get_vvalue()+":");
//            try{
////            System.out.println(paper_i.get("title").toString());
//            }catch(Exception e1){
//                System.out.print("no title");
//            }
//
//            LinkedList<Node> elist = v.elist;
//            for (Node node : elist) {
//                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//            }
//        }
        return graph; 	
    }
    /**
     * 生成coherence graph
     * @param graph
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static myGraph construct_coherence_graph(myGraph graph)throws JSONException, IOException {
    	/**
         * 开始生成coherence graph
         * 1 从所有vertexs挑出任意两个点 vertex_i<vertex_j 
         * 2 建立一个新的class chain,可以包含 vertex,  edge?是否需要
         */
        System.out.println("开始生成coherence graph："); 
        Comparator<Chain> comparator = new ChainCoherenceComparator();
		Comparator<Chain> reverseComparator = Collections.reverseOrder(comparator); // reverse comparator        
    	pq=new PriorityQueue<Chain>(reverseComparator);
    	
        List<Vertex> vlist = graph.vertexs;     
        myGraph coherence_graph=new myGraph(10);

    	//1 get all pair
        for(Vertex vi:vlist){        	
            JSONObject paper_i= (JSONObject) vi.get_Value();
            String id_i= paper_i.get("id").toString();
            String date_i= paper_i.get("date").toString();

        	 for(Vertex vj:vlist){
        		 JSONObject paper_j= (JSONObject) vj.get_Value();
                 String id_j= paper_j.get("id").toString();
                 String date_j= paper_j.get("date").toString();

                 if(id_i.equals(id_j)==false && date_i.compareTo(date_j)<0){
 					ArrayList<Vertex> pair = new ArrayList<Vertex>();
					pair.add(graph.get_vertex(vi.get_vvalue()));
					pair.add(graph.get_vertex(vj.get_vvalue()));
					
					double coherence = coherence_calculator.calculateCoherence(pair, graph);

					if (coherence >= threshold) {
						Chain chain=graph.new Chain(pair,coherence);
						pq.add(chain);
					}
                 }
        	 }
        }	
        //2 generateCoherenceGraph
        int i=0;
        while (!pq.isEmpty()) {
			Chain highestChain = pq.poll();
			ArrayList<Vertex> list = highestChain.getList();
			
			if (list.size() == SUBCHAIN_LENGTH) {
				coherence_graph.insertVertext(list, i);	
				i++;
			}
			else if (list.size() < SUBCHAIN_LENGTH) {
				generateExtensions(list,graph); // it will alter priority queue
			}
			else {
				System.out.println("subchain length is larger than m error");
			}
		}
   
//        List<Vertex> vlist_c = coherence_graph.vertexs;
//        System.out.println("coherence 图（v:v->v1) 和不一定 :");
//        for(Vertex v:vlist_c){
//            System.out.println(" "+v.get_vvalue()+":");
//			Object list1 = v.get_Value();
//			ArrayList<Vertex> list2 =(ArrayList<Vertex>) list1;
//			for(Vertex v1 : list2){
//				System.out.println(v1.get_vvalue());
//			}
//
//            LinkedList<Node> elist = v.elist;
//            for (Node node : elist) {
//                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//            }
//        }
       //为m-length图加入edge 
        List<Vertex> vlist_coherence_out = coherence_graph.vertexs;
        for(Vertex v:vlist_coherence_out){
    		Object list1 = v.get_Value();
			ArrayList<Vertex> list_out =(ArrayList<Vertex>) list1;
        	if(list_out.size()!=SUBCHAIN_LENGTH){
				System.out.println("subchain length error.");
        	}
            List<Vertex> vlist_coherence_in = coherence_graph.vertexs;
            for(Vertex v_in:vlist_coherence_in){
            	Object list2 = v_in.get_Value();
    			ArrayList<Vertex> list_in =(ArrayList<Vertex>) list2;
            	if(v.get_vvalue()==v_in.get_vvalue()){
            		continue;
            	}
            	else{
            		if(list_out.subList(1, SUBCHAIN_LENGTH).equals(list_in.subList(0, SUBCHAIN_LENGTH - 1))){
            			coherence_graph.insertEdge(v.get_vvalue(), v_in.get_vvalue(), 1, "connect");
            		}
            	}
            }
        }
        return coherence_graph;	
    }
    
/**
 * 为小于m-length的chain增加长度
 * @param highestList
 * @param graph
 */
    private static void generateExtensions(ArrayList<Vertex> highestList,myGraph graph) {
		ArrayList<Chain> extensions = new ArrayList<Chain>();
		Iterator<Chain> it_pq = pq.iterator();
		while (it_pq.hasNext()) {
			Chain currentChain = it_pq.next();
			ArrayList<Vertex> currentList = currentChain.getList();
		    int shorterLength = highestList.size();
		    if (currentList.size() < shorterLength) {
		    	shorterLength = currentList.size();
		    }
		    for (int k = 1; k < shorterLength; k++) {
		    	List<Vertex> headHighest = highestList.subList(0, k); // first k 
		    	List<Vertex> tailHighest = highestList.subList(highestList.size() - k, highestList.size()); // last k
		    	List<Vertex> headCurrent = currentList.subList(0, k);
		    	List<Vertex> tailCurrent = currentList.subList(currentList.size() - k, currentList.size());
		    	if (headHighest.equals(tailCurrent)) {
		    		ArrayList<Vertex> extension = new ArrayList<Vertex>();
		    		extension.addAll(currentList);
		    		extension.addAll(highestList.subList(k, highestList.size()));
		    		if (extension.size() > SUBCHAIN_LENGTH) {
		    			continue; // extension cannot longer than m.
		    		}
		    		else {
		    			double coherence = coherence_calculator.calculateCoherence(extension,graph);
		    			if (coherence >= threshold) {
		    			    Chain extendedChain = graph.new Chain(extension, coherence);
		    			    extensions.add(extendedChain);
		    			}
		    		}
		    		// break;
		    	}
		    	else if (headCurrent.equals(tailHighest)) {
		    		ArrayList<Vertex> extension = new ArrayList<Vertex>();
		    		extension.addAll(highestList);
		    		extension.addAll(currentList.subList(k, currentList.size()));
		    		if (extension.size() > SUBCHAIN_LENGTH) {
		    			continue;
		    		}
		    		else {
		    			double coherence = coherence_calculator.calculateCoherence(extension,graph);
		    			if (coherence >= threshold) {
		    			    Chain extendedChain = graph.new Chain(extension, coherence);
		    			    extensions.add(extendedChain);
		    			}
		    		}
		    		// break;
		    	}
		    	else {
		    		continue;
		    	}
		    }
		}
		pq.addAll(extensions);
	}

	
    private static boolean idexists(JSONArray jsonArray, String object){
        return jsonArray.toString().contains("\"id\":\""+object+"\"");
    }

    public static void print_graph(myGraph graph,String name){
    	List<Vertex> vlist = graph.vertexs;
        System.out.println("****************************");
        System.out.println(name+"图");
        for(Vertex v:vlist){
            System.out.println(" "+v.get_vvalue()+":");
            LinkedList<Node> elist = v.elist;
            for (Node node : elist) {
                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
            }
        }
    }
    
     public static JSONObject get_coherence_graph(String query,double threshold1,int SUBCHAIN_LENGTH1 ) throws JSONException, IOException{
    	threshold=threshold1;
    	SUBCHAIN_LENGTH=SUBCHAIN_LENGTH1;
    	//开始时间
        long start_time = System.currentTimeMillis();  
        System.out.println("start_time: "+start_time);

        //1. 生成graph, vertex是solr库中的点
        myGraph graph=construct_graph(query);
        
        long construct_graph_time=System.currentTimeMillis();  
        System.out.println("开始到construct_graph_time："+time(start_time,construct_graph_time));  

        //2. 加入cite label
        graph=construct_citation_graph(graph);
        
        long citation_time=System.currentTimeMillis();  
        System.out.println("construct_graph_time到citation_time："+time(construct_graph_time,citation_time));  
        
        //3. citation nomalize
        graph=citation_graph_nomalize(graph);
        
        long normalize_time=System.currentTimeMillis();  
        System.out.println("citation_time到normalize_time："+time(citation_time,normalize_time));  
        
//        List<Vertex> vlist = graph.vertexs;
        
        //4. 生成ancestral 通过蒙特卡洛和随机数
        graph=citation_graph_ancestral(graph);
        
        print_graph(graph,"ancestral");
        
        
        long ancestral_time=System.currentTimeMillis();  
        System.out.println("normalize_time到ancestral_time："+time(normalize_time,ancestral_time)); 
        
        //5. 生成coherence graph m=3 
        myGraph coherence_graph=construct_coherence_graph(graph);
       
        long coherence_graph_time=System.currentTimeMillis();  
        System.out.println("ancestral_time到coherence_graph_time："+time(ancestral_time,coherence_graph_time)); 
        
        
        System.out.println("coherence 123图（v:v->v1) 和不一定 :");
        List<Vertex> vlist_co = coherence_graph.vertexs;
        
      //将nodes,edges存成json file
      		JSONArray NODES = new JSONArray();
      		JSONArray EDGES = new JSONArray();

        for(Vertex v:vlist_co){
            System.out.println(" "+v.get_vvalue()+":");
            
			Object m_list = v.get_Value();
			ArrayList<Vertex> list2 =(ArrayList<Vertex>) m_list;
			//取得edge
            for(int x=0;x<SUBCHAIN_LENGTH-1;x++){
	            JSONObject paper_from= (JSONObject) list2.get(x).get_Value();
	            JSONObject paper_to= (JSONObject) list2.get(x+1).get_Value();
	    		JSONObject EDGE = new JSONObject();
	    		EDGE.put("source",paper_from.get("id"));
//	    		EDGE.put("source_vv",list2.get(x).get_vvalue());

	    		EDGE.put("target",paper_to.get("id")); 
//	    		EDGE.put("target_vv",list2.get(x+1).get_vvalue());
	    		EDGES.put(EDGE);


            }
			for(Vertex v1 : list2){
				try{
		            JSONObject paper= (JSONObject) v1.get_Value();
		            //如果id已包含在NODES则不加
		            
		            if(idexists(NODES,paper.get("id").toString())!=true){
		    		JSONObject NODE = new JSONObject();
		    		NODE.put("id",paper.get("id"));
		    		NODE.put("title",paper.get("title"));
		    		NODE.put("date",paper.get("date"));
		    		NODE.put("vvalue",v1.get_vvalue());
		    		NODE.put("url",paper.get("url"));
		    		NODE.put("paper_abstract",paper.get("paper_abstract"));

		    		NODES.put(NODE);
		    		}
		    		
		            }catch(Exception e1){}
				System.out.println(v1.get_vvalue());
			}

            LinkedList<Node> elist = v.elist;
            for (Node node : elist) {
                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
            }
        }
        
        JSONObject COHERENCE_GRAPH = new JSONObject();
        COHERENCE_GRAPH.put("nodes",NODES);
        COHERENCE_GRAPH.put("edges",EDGES);     
        return COHERENCE_GRAPH;
    }
 
    
	public static void main(String args[]) throws JSONException, IOException {
		//Term Recognition
      
      JSONObject cohe= new JSONObject();
      cohe=get_coherence_graph(query,threshold,SUBCHAIN_LENGTH);
      System.out.println(cohe);
      

		
		
    	//开始时间
//        long start_time = System.currentTimeMillis(); 
//
//        System.out.println("start_time: "+start_time);
//
//        //1. 生成graph, vertex是solr库中的点
//        String query="&q=Artificial%20neural%20network";
//
//        myGraph graph=construct_graph(query);
//        
//        long construct_graph_time=System.currentTimeMillis();  
//        System.out.println("开始到construct_graph_time："+time(start_time,construct_graph_time));  
//
//        //2. 加入cite label
//        graph=construct_citation_graph(graph);
//        
//        long citation_time=System.currentTimeMillis();  
//        System.out.println("construct_graph_time到citation_time："+time(construct_graph_time,citation_time));  
//        
//        //3. citation nomalize
//        graph=citation_graph_nomalize(graph);
//        
//        long normalize_time=System.currentTimeMillis();  
//        System.out.println("citation_time到normalize_time："+time(citation_time,normalize_time));  
//        
////        List<Vertex> vlist = graph.vertexs;
//        
//        //4. 生成ancestral 通过蒙特卡洛和随机数
//        graph=citation_graph_ancestral(graph);
//        
//        long ancestral_time=System.currentTimeMillis();  
//        System.out.println("normalize_time到ancestral_time："+time(normalize_time,ancestral_time)); 
//        
//        //5. 生成coherence graph m=3 
//        myGraph coherence_graph=construct_coherence_graph(graph);
//       
//        long coherence_graph_time=System.currentTimeMillis();  
//        System.out.println("ancestral_time到coherence_graph_time："+time(ancestral_time,coherence_graph_time)); 
//        
//
////      System.out.println("normalize图（v:v->v1) 和为1:");
////      for(Vertex v:vlist){
////          System.out.println(" "+v.get_vvalue()+":");
////          LinkedList<Node> elist = v.elist;
////          for (Node node : elist) {
////              System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
////          }
////      }
////        System.out.println("第一遍 :");
////      List<Vertex> vlist = graph.vertexs;
////      for(Vertex v:vlist){
//////          JSONObject paper_i= (JSONObject) v.get_Value();
////          System.out.println(" "+v.get_vvalue()+":");
////          try{
//////          System.out.println(paper_i.get("title").toString());
////          }catch(Exception e1){
////              System.out.print("no title");
////          }
////
////          LinkedList<Node> elist = v.elist;
////          for (Node node : elist) {
////              System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
////          }
////      }
//        
//        
//	
//        
//        
//        
//        
//        System.out.println("coherence 123图（v:v->v1) 和不一定 :");
//        List<Vertex> vlist_co = coherence_graph.vertexs;
//        
//      //将nodes,edges存成json file
//      		JSONArray NODES = new JSONArray();
//      		JSONArray EDGES = new JSONArray();
//
//        for(Vertex v:vlist_co){
//            System.out.println(" "+v.get_vvalue()+":");
//            
//			Object m_list = v.get_Value();
//			ArrayList<Vertex> list2 =(ArrayList<Vertex>) m_list;
//			//取得edge
//            for(int x=0;x<SUBCHAIN_LENGTH-1;x++){
//	            JSONObject paper_from= (JSONObject) list2.get(x).get_Value();
//	            JSONObject paper_to= (JSONObject) list2.get(x+1).get_Value();
//	    		JSONObject EDGE = new JSONObject();
//	    		EDGE.put("source",paper_from.get("id"));
////	    		EDGE.put("source_vv",list2.get(x).get_vvalue());
//
//	    		EDGE.put("target",paper_to.get("id")); 
////	    		EDGE.put("target_vv",list2.get(x+1).get_vvalue());
//	    		EDGES.put(EDGE);
//
//
//            }
//			for(Vertex v1 : list2){
//				try{
//		            JSONObject paper= (JSONObject) v1.get_Value();
//		            //如果id已包含在NODES则不加
//		            
//		            if(idexists(NODES,paper.get("id").toString())!=true){
//		    		JSONObject NODE = new JSONObject();
//		    		NODE.put("id",paper.get("id"));
//		    		NODE.put("title",paper.get("title"));
//		    		NODE.put("date",paper.get("date"));
//		    		NODE.put("vvalue",v1.get_vvalue());
//		    		NODE.put("url",paper.get("url"));
//		    		NODE.put("paper_abstract",paper.get("paper_abstract"));
//
//		    		NODES.put(NODE);
//		    		}
//		    		
//		            }catch(Exception e1){}
//				System.out.println(v1.get_vvalue());
//			}
//
//            LinkedList<Node> elist = v.elist;
//            for (Node node : elist) {
//                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//            }
//        }
//        
//        JSONObject COHERENCE_GRAPH = new JSONObject();
//        COHERENCE_GRAPH.put("nodes",NODES);
//        COHERENCE_GRAPH.put("edges",EDGES);
//        
//        
//        try (FileWriter file = new FileWriter("/Users/chen/project_run/test/json/file3.json")) {
//    		file.write(COHERENCE_GRAPH.toString());
//    		System.out.println("Successfully Copied JSON Object to File...");
//    		System.out.println("\nJSON Object: " + COHERENCE_GRAPH);
//    	}
//        
//
//        
//        
//        try (FileWriter file = new FileWriter("/Users/chen/project_run/test/json/file1.json")) {
//    		file.write(NODES.toString());
//    		System.out.println("Successfully Copied JSON Object to File...");
//    		System.out.println("\nJSON Object: " + NODES);
//    	}
//        try (FileWriter file = new FileWriter("/Users/chen/project_run/test/json/file2.json")) {
//    		file.write(EDGES.toString());
//    		System.out.println("Successfully Copied JSON Object to File...");
//    		System.out.println("\nJSON Object: " + EDGES);
//    	}
//                
//        
       
	}
	
}


/*
 * //输出第一遍图
        List<Vertex> vlist = graph.vertexs;
        System.out.println("normalize图（v:v->v1) 和不一定 :");
        for(Vertex v:vlist){
            System.out.println(" "+v.get_vvalue()+":");
            LinkedList<Node> elist = v.elist;
            for (Node node : elist) {
                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
            }
        }
        
        //        //生成新的Gcr
//        myGraph Grc=new myGraph(10);
//    	Grc=GenerateRandomSubgraph(graph);
//    	List<Vertex> vlist_grc = Grc.vertexs;
//        System.out.println("Grc 随机图（v:v->v1) 和不一定 :");
//        for(Vertex v:vlist_grc){
//            System.out.println(" "+v.get_vvalue()+":");
//            LinkedList<Node> elist = v.elist;
//            for (Node node : elist) {
//                System.out.println("  "+node.getEvalue()+"--"+node.getWeight()+"--"+node.getLabel());
//            }
//        }
        
 */

