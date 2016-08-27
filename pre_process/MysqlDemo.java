package bingwei_t1;



import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Statement;


public class MysqlDemo {
	
	
	public static String geturl(String ID) throws Exception {
		
		String paper_url = null;
		Connection con = null;
        Statement st = null;

        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/bingwei_search_db?useSSL=false";//javademo
        String user = "root";
        String password = "123456";
		
        
		
        try {
        	con = DriverManager.getConnection(url, user, password);
        	
//        	st = con.createStatement();
//        	String query = "INSERT INTO student(NO,name) VALUES('2012001','ccc')";
//            st.executeUpdate(query);
          
        	
            pst = con.prepareStatement("select URL from Paper WHERE ID="+ID);
            rs = pst.executeQuery();

//            System.out.print(rs.getString(1));
            //System.out.println("this url: ");

            while (rs.next()) {
                
                //System.out.println(rs.getString(1));
                paper_url=rs.getString(1);
//                System.out.print(": ");
//                System.out.println(rs.getString(2));
            }
        } catch (SQLException ex) {
            
            Logger lgr = Logger.getLogger(MysqlDemo.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

    } finally {

        try {
        
            if (rs != null) {
                rs.close();
            }
            
            if (pst != null) {
                pst.close();
            }
            
            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            
            Logger lgr = Logger.getLogger(MysqlDemo.class.getName());
            lgr.log(Level.WARNING, ex.getMessage(), ex);
        }
    }
		
		return paper_url;
		
		
	}

	public static void main(String[] args) throws Exception {
		String ID="'0a337cb4-7f51-3607-8577-7239d1e4c943'";
		String paperurl;
		paperurl=geturl(ID);
        System.out.print("paperurl: ");

        System.out.print(paperurl);

    }
}
