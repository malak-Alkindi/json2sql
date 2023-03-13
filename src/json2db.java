import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
public class json2db {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		 try {
			 
			 System.out.println("enter the database name");
			 String url = "jdbc:sqlserver://localhost:1433;" +
			 "databaseName="+scan.nextLine()+";" +
			 "encrypt=true;" + "trustServerCertificate=true";
			 String username = "sa";
			 String password = "root";
			 Connection conn = DriverManager.getConnection(url, username, password);
			 Statement st = conn.createStatement();
			 
			 if(!tableExists(conn,"universities")){
			 String universityTable = "CREATE TABLE universities ("
			 + " id INTEGER IDENTITY(1,1) Primary key ,"
			 + " name varchar(1000) , "
			 + " country varchar(1000) ) " ;
			 st.executeUpdate(universityTable);}
				else {System.out.println("Table universities Exists");}
			 BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\eclipse-workspace\\Json2javaObjjDB\\jsonfile.txt"));
			 StringBuilder jsonString = new StringBuilder();
			 String line;
			 while ((line = reader.readLine()) != null) {
			 jsonString.append(line);
			 }
			 reader.close();
			 JSONArray universities = new JSONArray(jsonString.toString());
			 for (int i = 0; i < universities.length(); i++) {
			 JSONObject university = universities.getJSONObject(i);
			 //int id = university.getInt("id");
			 String name = university.getString("name");
			 String country = university.getString("country");
	
			 String unviQERY = "insert into universities values ('"+name+"','"+country+"')";
			 st.execute(unviQERY);
			 }
			 
			 System.out.println("enter the country name ");
			 String searchCon = "select * from universities where country like '" +  scan.nextLine()+"'";
		
		
			 showUnviSet(conn,searchCon);
			
			System.out.println(" list of countries suggestions to the user for ");
			String sug = "select * from universities WHERE Country  IN ('us', 'France', 'UK') ";
			
			 showUnviSet(conn,sug);
			 } catch (Exception e) {
			 System.err.println("Error: " + e.getMessage());
			 }
			 }
	
	static int getSize(Connection con, String tableName) throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("select * from " + tableName);
		int i = 0;
		while (rs.next()) {
			i++;
		}
		return i;
	};
	
	 static boolean tableExists(Connection connection, String tableName) throws SQLException {
		    DatabaseMetaData meta = connection.getMetaData();
		    ResultSet resultSet = meta.getTables(null, null, tableName, new String[] {"TABLE"});

		    return resultSet.next();
		}
	 

		static void showUnviSet(Connection con ,String str) throws SQLException {

			Statement st = con.createStatement();

			 ResultSet resultSet = st.executeQuery(str);
				if(resultSet.next()) {
				 while (resultSet.next()) {
				 System.out.println("\nid: " + resultSet.getInt("id")
				 + " |country: " + resultSet.getString("country")
				 + " |name: " + resultSet.getString("name")
		
				 + "\n\n");
				 }
		}
				
		else {
			System.out.println("no data found ");
		}
		}}


