import static spark.Spark.*;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.storyline;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

public class Main {
	
	
	private static final HashMap<String, String> corsHeaders = new HashMap<String, String>();
	static {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        corsHeaders.put("Access-Control-Allow-Credentials", "true");
    }
	public final static void apply() {
        Filter filter = new Filter() {
            @Override
            public void handle(Request request, Response response) throws Exception {
                corsHeaders.forEach((key, value) -> {
                    response.header(key, value);
                });
            }

        };
        Spark.after(filter);
    }
	
	// Enables CORS on requests. This method is an initialization method and should be called once.
//	private static void enableCORS(final String origin, final String methods, final String headers) {
//
//	    options("/*", (request, response) -> {
//
//	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
//	        if (accessControlRequestHeaders != null) {
//	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
//	        }
//
//	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
//	        if (accessControlRequestMethod != null) {
//	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
//	        }
//
//	        return "OK";
//	    });
//
//	    before((request, response) -> {
//	        response.header("Access-Control-Allow-Origin", origin);
//	        response.header("Access-Control-Request-Method", methods);
//	        response.header("Access-Control-Allow-Headers", headers);
//	        // Note: this may or may not be necessary in your particular application
//	        response.type("application/json");
//	    });
//	}
	
    public static void main(String[] args)throws JSONException, IOException {
    	
    	apply();
//        get("/hello", (req, res) -> "Heallo World");        
        get("/hello/:query", (request, response) -> {
        String query="&q="+request.params(":query");
        query=query.replaceAll(" ", "%20");  
        System.out.println(query);
        JSONObject coherenct_graph=storyline.get_coherence_graph(query,0.5,3);
        return coherenct_graph;
        
//        
//        System.out.println("query");
//
//        
//        JSONObject EDGE = new JSONObject();
//		EDGE.put("source","123");
//        //storyline.main(args);
//  		JSONArray NODES = new JSONArray();
//  		NODES.put(EDGE);
//		
//        
//            return NODES;
        
        });
    }
}