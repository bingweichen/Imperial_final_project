package graph;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.json.JSONObject;

import graph.myGraph.Chain;
import graph.myGraph.Node;
import graph.myGraph.Vertex;



public class myGraph {
	public class Vertex{
        private Object value;//顶点的名称
        private int vvalue;//定点的额值
        public LinkedList<Node> elist;//链表 用来存放边的信息

        public Vertex(Object value,int vvalue){
            this.setValue(value);
            this.vvalue=vvalue;
            elist=new LinkedList<Node>();
        }
        public int get_vvalue(){
        	return vvalue;
        }
        
		public void setValue(Object value) {
			this.value = value;
		}
		public Object get_Value() {
			// TODO Auto-generated method stub
			return value;
		}
    }

    public class Node{
        private int evalue;//边的节点的值
        private double weight;//权重
        
        private String label;//标签

        public Node(int evalue,double weight,String label){
            this.evalue=evalue;
            this.weight=weight;
            this.setLabel(label);

        }
        public int getEvalue() {
            return evalue;
        }

        public double getWeight() {
            return weight;
        }
        
        public void setWeight(double weight){
            this.weight=weight;
        }
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}

    }
    /**
     * chain obj
     * @author chen
     *
     */
    public class Chain{
    	private ArrayList<Vertex> chain;
        private double coherence;
        
        public Chain(ArrayList<Vertex> chain,double coherence){
        	this.chain = chain;
        	this.coherence = coherence;

        }
        //还没用过
        public ArrayList<Vertex> getList() {
        	return chain;
        }
        
        public double getCoherence() {
        	return coherence;
        }
        
        public boolean equals(Object obj) {
           	if (obj == this) return true;
        	if (obj == null) return false;
        	if (obj.getClass() != this.getClass()) return false;
        	Chain that = (Chain) obj;
        	if (!that.chain.equals(this.chain)) return false;
        	if (that.coherence != this.coherence) return false;
        	return true;
        }

    }

    public List<Vertex> vertexs;//存储节点
    private int numOfEdges;//边的条数

    public myGraph(int n){
        //初始化邻接表
        vertexs=new ArrayList<Vertex>(n);
        numOfEdges=0;
    }

    public List<Vertex> getVertexs() {
        return vertexs;
    }

    //得到节点个数
    public int getNumOfVertex(){
        return vertexs.size();
    }

    //得到边的个数
    public int getNumOfEdges(){
        return numOfEdges;
    }

    //返回节点i的值
    public Object get(int i){
        Vertex vertex = vertexs.get(i);
        if(vertex!=null){
            return vertex.get_Value();
        }
        return null;
    }
    //返回节点i
    public Vertex get_vertex(int i){
        Vertex vertex = vertexs.get(i);
        if(vertex!=null){
            return vertex;
        }
        return null;
    }

    //返回v1,v2节点之间边的权值
    public double getWeight(int v1,int v2, String label){
        Node search = search(v1, v2, label);
        if(search!=null) return search.weight;
        else return -1;
    }

    //搜索边节点
    //无label
    public Node search(int v1,int v2){
        Iterator<Node> it = vertexs.get(v1).elist.iterator();
        while(it.hasNext()){
            Node node = (Node) it.next();
            if(node.evalue==v2){
                return node;
            }    
        }
        return null;
    }
    //有label
    public Node search(int v1,int v2, String label){
        Iterator<Node> it = vertexs.get(v1).elist.iterator();
        while(it.hasNext()){
            Node node = (Node) it.next();
            if(node.evalue==v2&& node.getLabel().equals(label)){
                return node;
            }    
        }
        return null;
    }

    //插入节点
    public void insertVertext(Object value,int vvalue){
        Vertex vertex=new Vertex(value,vvalue);
        vertexs.add(vertex);        
    }

    //插入边节点
    public void insertEdge(int v1,int v2,double weight,String label){
        LinkedList<Node> elist = vertexs.get(v1).elist;
        Node node = new Node(v2, weight,label);
        elist.add(node);
        numOfEdges++;
    }

    //删除节点
    public void deleteVertex(int vvalue){
        vertexs.remove(vvalue);
    }

    //删除边节点
    public void deleteEdge(int v1,int v2, String label){
        LinkedList<Node> elist = vertexs.get(v1).elist;
        Node search = search(v1, v2, label);
        if(search!=null) elist.remove(search);
        numOfEdges--;
    }

    //得到节点的第一个边节点的下标
    public int getFirstneighbor(int i){
        LinkedList<Node> elist = vertexs.get(i).elist;
        if(elist.size()==0) return -1;
        Node node =  elist.get(0);
        if(node!=null)
            return node.evalue;
        return -1;
    }

    //根据前一个边节点的下标来取得下一个边节点
    public int getNextNeighbor(int v1,int v2){
        LinkedList<Node> elist = vertexs.get(v1).elist;
        Iterator<Node> iterator = elist.iterator();
        while(iterator.hasNext()){
            Node next = iterator.next();
            if (next.evalue==v2) {
                try {
                    return iterator.next().evalue;
                } catch (Exception e) {
                    System.out.println("没有下一个");
                }
            }
        }
        return -1;
    }
	
}
