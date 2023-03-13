import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
public class json2db {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		 try {
			 String url = "jdbc:sqlserver://localhost:1433;" +
			 "databaseName=json2sql;" +
			 "encrypt=true;" + "trustServerCertificate=true";
			 String username = "sa";
			 String password = "root";
			 Connection conn = DriverManager.getConnection(url, username, password);
			 Statement st = conn.createStatement();
			 String universityTable = "CREATE TABLE universities ("
			 + " id INTEGER IDENTITY(1,1) Primary key ,"
			 + " name TEXT , "
			 + " country TEXT ) " ;
			// st.executeUpdate(universityTable);
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
	
//			 String unviQERY = "insert into universities values ('"+name+"','"+country+"')";
//			 st.execute(unviQERY);
			 }
			 
			 System.out.println("enter the country name ");
			 String searchCon = "select * from universities where country like '" +  scan.nextLine()+"'";
		
			 ResultSet resultSet = st.executeQuery(searchCon);
			if(resultSet.next()) {
			
			 System.out.println("\nid: " + resultSet.getInt("id")
			 + " |country: " + resultSet.getString("country")
			 + " |name: " + resultSet.getString("name")
	
			 + "\n\n");
			 
			}else {
				System.out.println("no database realted to this country found");
			}
			 } catch (Exception e) {
			 System.err.println("Error: " + e.getMessage());
			 }
			 }
			}
