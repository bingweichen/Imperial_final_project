package bingwei_t1;


import javax.xml.parsers.DocumentBuilderFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
//import org.json.simple.parser.*;
public class process_xml {

    private static ArrayList<String> file_path= new ArrayList<String>() ;
    private static ArrayList<String> file_name= new ArrayList<String>() ;

    //put all file into file path
    private static void get_all_file(String path) {
        File f = new File(path);
        File[] fs = f.listFiles();

        if (fs == null) {
            return;
        }

        for (File file : fs) {
            if (file.isFile()) {
                //System.out.println(file.getName().substring(0,file.getName().lastIndexOf('.')));
                if(file.getPath().endsWith(".xml")){
                    file_path.add(file.getPath());
                    file_name.add(file.getName().substring(0,file.getName().lastIndexOf('.')));

                }
            } else {
                get_all_file(file.getPath());
            }
        }
    }

    public static void transfer_xml_json() throws IOException {
        String xml_path="/Users/chen/Documents/individulal_project/data/testlrec/";
        String json_path="/Users/chen/Documents/individulal_project/data/rlrec/";
        get_all_file(xml_path);

        //for 1 xml file, get the useful information
        for(int i=0;i<file_path.size();i++) {
            String xml = file_path.get(i);
            String name = file_name.get(i);
//            System.out.println(xml);
//            System.out.println(name);
            String title = "";
            String content = "";
            String author = "";
            String id = name;
            String paper_abstract="null";

            ArrayList<String> ref_title= new ArrayList<String>() ;
            ArrayList<String> ref_author= new ArrayList<String>() ;
            ArrayList<String> ref_rawString= new ArrayList<String>() ;
            ArrayList<String> ref_date= new ArrayList<String>() ;
            boolean wether_title=false;
            boolean wether_author=false;
            boolean wether_abstract=false;

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(xml);

                Element root = doc.getDocumentElement();
//                System.out.println(root.getNodeName());

                NodeList nodeList = root.getChildNodes();
                if (null != root) {
                    //SectLabel
                    Node SectLabel = nodeList.item(1);
//                    System.out.println(SectLabel);
//                    if (SectLabel.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println(SectLabel.getAttributes().getNamedItem(
//                                "name").getNodeValue());
//                    }
                    NodeList variants = SectLabel.getChildNodes();
                    Node variant = variants.item(1);
//                    System.out.println(variant);
//                    if (variant.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println(variant.getAttributes().getNamedItem(
//                                "confidence").getNodeValue());
//                    }
                    for (Node node = variant.getFirstChild(); node != null; node = node
                            .getNextSibling()) {
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("title".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());
                                if(!wether_title) {
                                    title = node.getFirstChild()
                                            .getNodeValue();
                                    wether_title=true;
                                }
                            }
                        }
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("subsectionHeader".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());
                                content += node.getFirstChild()
                                        .getNodeValue();
                            }
                        }
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("bodyText".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());
                                content += node.getFirstChild()
                                        .getNodeValue();
                            }
                        }
                    }
                    //ParsHed
                    Node ParsHed = nodeList.item(3);
//                    System.out.println(ParsHed);
//                    if (ParsHed.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println(ParsHed.getAttributes().getNamedItem(
//                                "name").getNodeValue());
//                    }
                    NodeList variantsH = ParsHed.getChildNodes();
                    Node variantH = variantsH.item(1);
//                    System.out.println(variantH);
                    for (Node node = variantH.getFirstChild(); node != null; node = node
                            .getNextSibling()) {
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("author".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());

                                if(!wether_author) {
                                    author = node.getFirstChild()
                                            .getNodeValue();
                                    wether_author=true;
                                }
                            }
                        }

                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("title".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());

                                if(!wether_title) {
                                    title = node.getFirstChild()
                                            .getNodeValue();
                                    wether_title=true;
                                }
                            }
                        }
                        
                        //abstract
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            if ("abstract".equals(node.getNodeName())) {
//                              System.out.println(node.getFirstChild()
//                                     .getNodeValue());

                                if(!wether_abstract) {
                                	paper_abstract = node.getFirstChild()
                                            .getNodeValue();
                                    wether_abstract=true;
                                }
                            }
                        }
                        

                    }

                    //ParsCit
                    Node ParsCit = nodeList.item(5);
//                    System.out.println(ParsCit);
//                    if (ParsCit.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println(ParsCit.getAttributes().getNamedItem(
//                                "name").getNodeValue());
//                    }

                    NodeList citationLists = ParsCit.getChildNodes();
                    Node citationList = citationLists.item(1);
//                    System.out.println(citationList);


                    for (Node node = citationList.getFirstChild(); node != null; node = node
                            .getNextSibling()) {

                        for (Node node_in = node.getFirstChild(); node_in != null; node_in = node_in
                                .getNextSibling()) {

                            if (node_in.getNodeType() == Node.ELEMENT_NODE) {
                                if ("title".equals(node_in.getNodeName())) {
//                                     System.out.println(node_in.getFirstChild()
//                                             .getNodeValue());
                                    ref_title.add(node_in.getFirstChild()
                                            .getNodeValue());
                                }
                            }
//                            if (node_in.getNodeType() == Node.ELEMENT_NODE) {
//                                if ("date".equals(node_in.getNodeName())) {
////                                     System.out.println(node_in.getFirstChild()
////                                             .getNodeValue());
//                                	try{
//                                		ref_date.add(node_in.getFirstChild()
//                                                .getNodeValue());
//                                	}
//                                	catch(Exception e) {
//                                        //e.printStackTrace();
//                                        ref_date.add("null");
//                                    }
//                                }
//                            }
//                            if (node_in.getNodeType() == Node.ELEMENT_NODE) {
//                                if ("rawString".equals(node_in.getNodeName())) {
////                                     System.out.println(node_in.getFirstChild()
////                                             .getNodeValue());
//                                    ref_rawString.add(node_in.getFirstChild()
//                                            .getNodeValue());
//                                }
//                            }
                            
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


/*
            //add to json object
            */

            String ID="'"+id+"'";
            String paperurl = null;
            System.out.println("ID");
            System.out.println(ID);

            try {
                paperurl=MysqlDemo.geturl(ID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            

            System.out.println();
            //            System.out.println("title");
//            System.out.println(title);
//            System.out.println("content");
//            System.out.println(content);
//            System.out.println("author");
//            System.out.println(author);
//            System.out.println("ref_rawString");
//            System.out.println(ref_rawString);
//            System.out.println("ref_date");
//            System.out.println(ref_date);
            System.out.println("Paperurl: ");
            System.out.println(paperurl);
            //"url":"http:\/\/www.aclweb.org\/anthology\/W\/W13\/W13-4056.pdf"
            //http://www.lrec-conf.org/proceedings/lrec2000/pdf/92.pdf

            String datestr;
            int date=0;
            //get date form url
            if(paperurl!=null){
                
            	if(paperurl.substring(11,15).equals("lrec"))
            	{
                    System.out.println("paperurl.substring(11,15)");
                    System.out.println(paperurl.substring(11,15));
                    
                    paperurl=paperurl.trim();
                    String str2="";
                    if(paperurl != null && !"".equals(paperurl)){
                        for(int i1=0;i1<paperurl.length();i1++){
                            if(paperurl.charAt(i1)>=48 && paperurl.charAt(i1)<=57){
                                str2+=paperurl.charAt(i1);
                            }
                        }
                    }
//                    if(str2.length()<8){
//                        System.out.println("str2: "+str2);
//                    	String str3=str2.substring(0,4);
//                    		for (int i8=0;i8<8-str2.length();i8++){
//                    			str3=str3+"0";
//                    		}
//                    			str3=str3+str2.substring(4,str2.length());
//                        System.out.println("str3: "+str3);
//
//                    	
//                    }
                    System.out.println("str2: "+str2);
                	String str3=str2.substring(0,4);
                		for (int i8=0;i8<8-str2.length();i8++){
                			str3=str3+"0";
                		}
                			str3=str3+str2.substring(4,str2.length());
                    System.out.println("str3: "+str3);
                    date=Integer.parseInt(str3);
            	}
            	else {
                    datestr = paperurl.substring(paperurl.lastIndexOf('.') - 7, paperurl.lastIndexOf('.'));
                    String yearstr = datestr.substring(0, 2);
                    int year = Integer.parseInt(yearstr);
                    if (year > 16) {
                        year = 1900 + year;
                    } else {
                        year = 2000 + year;
                    }
                    String countstr = datestr.substring(3, 7);
                    int count = Integer.parseInt(countstr);
                    date = year * 10000 + count;
                }
            }
            else{
                date=0;
            }

            System.out.println("title: ");
            System.out.println(title);
//            System.out.println("author: ");
//            System.out.println(author);


            JSONObject paper = new JSONObject();
            paper.put("id", id);

            if(title.equals("null")){
                continue;
            }
            if(title.equals("")){
                continue;
            }
            paper.put("title", title);
            
            paper.put("author", author);
            
            paper.put("date", date);
            
            paper.put("url", paperurl);
            
            paper.put("ref_title", ref_title);

            paper.put("paper_abstract", paper_abstract);

            
//            paper.put("ref_rawString", ref_rawString);
//            paper.put("ref_date", ref_date);
            paper.put("content", content);


            //System.out.print(paper);
            FileWriter file = null;
            try {
                file = new FileWriter(json_path+name+".json");
                file.write("[" + paper.toJSONString() + "]");

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } finally {
                file.flush();
                file.close();
            }

        }
    }

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        transfer_xml_json();
    }

}
/*

//            NodeList algorithmlist = test.getElementsByTagName("algorithm");
//            System.out.println("共有" + algorithmlist.getLength() + "个algorithm节点");
//            for (int i = 0; i < algorithmlist.getLength(); i++)
//            {
//                Node  algorithm= algorithmlist.item(i);
//                Element elem = (Element) algorithm;
//                System.out.println("name:" + elem.getAttribute("name"));
//                System.out.println("name:" + algorithm);
//                System.out.println("name:" + elem);



//                Node var = elem.getFirstChild();
//                System.out.println("name:" + var);
//
//                Element elem2 = (Element) var;
//
//                System.out.println("name:" + elem1.getAttribute("no"));

//                for (Node node = dog.getFirstChild(); node != null; node = node.getNextSibling())
//                {
//                    if (node.getNodeType() == Node.ELEMENT_NODE)
//                    {
//                        String name = node.getNodeName();
//                        String value = node.getFirstChild().getNodeValue();
//                        System.out.print(name + ":" + value + "\t");
//                    }
//                }
//                System.out.println();


	 NodeList dogList = doc.getElementsByTagName("penguins");
//            System.out.println("共有" + dogList.getLength() + "个dog节点");
//            for (int i = 0; i < dogList.getLength(); i++)
//            {
//                Node dog = dogList.item(i);
//                Element elem = (Element) dog;
//                System.out.println("id:" + elem.getAttribute("id"));
//                for (Node node = dog.getFirstChild(); node != null; node = node.getNextSibling())
//                {
//                    if (node.getNodeType() == Node.ELEMENT_NODE)
//                    {
//                        String name = node.getNodeName();
//                        String value = node.getFirstChild().getNodeValue();
//                        System.out.print(name + ":" + value + "\t");
//                    }
//                }
//                System.out.println();
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }

	 //            NodeList nList = root.getElementsByTagName("algorithm");
//            Node nNode = nList.item(0);
//            System.out.println(nNode.getNodeName());
//


//            NodeList nList1 = nNode.getElementsByTagName("variant");





//            Node nl=root.getFirstChild();
//            System.out.print(nl.getNodeName());
//            NodeList nList = doc.getElementsByTagName("variant");
//            Node nNode = nList.item(0);
            //System.out.println("\nCurrent Element :"+ nNode.getNodeName());
//
//            Element eElement = (Element) nNode;
//            System.out.println("Student roll no : "
//               + eElement.getAttribute("no"));
//
//            System.out.println("First Name : "
//                    + eElement
//                    .getElementsByTagName("title")
//                    .item(0)
//                    .getTextContent());


	JSONObject obj = new JSONObject();

		JSONObject t1 = new JSONObject();
		t1.put("test", "helo");
		t1.put("2", "helo");
		JSONObject t2 = new JSONObject();
		t2.put("test", "helo1");
		t2.put("2", "helo2");

		JSONArray list = new JSONArray();
		list.add(t1);
		list.add(t2);


		JSONObject t3 = new JSONObject();

	      obj.put("name", "foo");
	      obj.put("num", new Integer(100));
	      obj.put("balance", new Double(1000.21));
	      obj.put("is_vip", new Boolean(true));

	      obj.put("notis_vip", list);


	      System.out.print(obj);
/Users/chen/Documents/individulal_project/data/testxml2/3b656c28-4a3f-39dc-9ea3-bb2ec28a57f9/00d7e3ab-c7c4-3fee-99f6-e1111ced26aa.xml
	 */


