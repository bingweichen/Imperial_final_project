package tools;

import java.util.Arrays;

//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.ansj.domain.Result;
//import org.ansj.splitWord.analysis.ToAnalysis;
//import org.json.JSONException;

public class word_seg {
	
//	public static String fenci(String string){
//		  string = convert(string);
//		  var output = [];
//		  output = string.replace(/\n/g,'').split(re);
//		  var output_1 = [];
//		  for (var i = 0; i < output.length; i++) {
//		    if (output[i]!==',' && output[i]!==';' && output[i]!=='.')
//		      output_1.push(output[i]);
//		  }
//		  return output_1;
//		}
//	public static String convert(String propertyName)
//	{
//	  public static String upperToLower(String match)
//	  {
//	    return match.toLowerCase();
//	  }
//	  return propertyName.replace(re2, upperToLower);
//	}
	
	public static double compare(String a,String b) {
		  String[] x =wordSplit(a);
		  String[] y =wordSplit(b);
		
		  String[] x1 = Arrays.copyOf(x,x.length);
		  String[] y1 = Arrays.copyOf(y,y.length);

		  Arrays.sort(x1);
		  Arrays.sort(y1);

		double s=x1.length + y1.length;
		double z=0;
		int i;
		for( i=0; i<Math.min(x1.length,y1.length);i++)
		{
			if(x1[i].compareTo(y1[i])==0){
				z++;
//				  System.out.println("z");
//				  System.out.println(z);
			}
		}
		return z/s*200;
		}
	
		public static String[] wordSplit(String s){
				  s=s.toLowerCase();
				  s=s.replace(".", "").replace(",", "").replace("?", "").replace("!","").replaceAll("[\\t\\n\\r]", "");
				  String[] words = s.split("\\s+");
				  return words;
		}
	
	  public static void main(String[] args)  {

		  String s = "tt Generalizing biomedical event extraction.";
		  String s1 = "\nGeneralizing Biomedical Event Extraction tssss \n";
		  double socre =compare(s,s1);
		  System.out.println(s);
		  System.out.println(socre);


	  }	
	
}

/*
* 
* 
//		  //String str = "欢迎使用ansj_seg,(ansj中文分词)在这里如果你遇到什么问题都可以联系我.我一定尽我所能.帮助大家.ansj_seg更快,更准,更自由!" ;
//		  String string1 = "Generalizing biomedical event extraction.";
//		  
//		  System.out.println(ToAnalysis.parse(string1));
//	      Result al =ToAnalysis.parse(string1);
//	      
//		  System.out.println("al.get(0).getClass().getName()");
//		  System.out.println(al.get(0).getClass().getName());
//
//		  
//		  String string2 = "\nGeneralizing Biomedical Event Extraction\n";
//	      Result al1 =ToAnalysis.parse(string2);
////		  if(al1.get(0)=="/n"){
////			  System.out.println("al1.get(0)");
////		  }
//		  System.out.println(al1.get(0));
//
//		  System.out.println(ToAnalysis.parse(string2));
*/