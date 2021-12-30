import java.io.BufferedReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



public class FileDBInput {

	
   static String file="C:\\Users\\HANAN WANDJI\\Documents\\Eclipse-Workspace GDI2\\DatenBanken\\DB_Abgaben\\src\\praktikum1\\Elementen.csv";
	public static void main(String[] args) throws IOException, SQLException {

     
		liesLieferanten();
	


	}


	
	public static void liesLieferanten() throws IOException, SQLException {
		String line="";
		List<String>li= new ArrayList<>();//eine Liste von String ,um alle Zeilen aufzunehmen
		try 
			(BufferedReader br=new BufferedReader(new FileReader(file));){
			while((line=br.readLine())!=null) {
				 li.add(line);
				 
				}
			
			if(li.size()>=5) {//nur wenn mindestens 5 Lieferanten vorhanden sind, wird weitergemacht bzw eingelesen
				try {

				      
					try ( Connection cn = new ConnectDB().connect()) {

				        System.out.println("Verbindung ok!");
				        String sql = "INSERT INTO Lieferant(LNr, Name, PLZ)"
				        		+ " VALUES (?, ?, ?)";
				        String sql1="Delete from Lieferant";//furs Loeschen
				        Statement stmt1=cn.createStatement();
				        ResultSet rs=stmt1.executeQuery(sql1);//tabelle wird erst geleert,falls etwas vorhanden ist
				        stmt1.close();
				        rs.close();
				        PreparedStatement pstmt=cn.prepareStatement(sql);//Dann werden die Elemente nacheinander hinzugefuegt
				        for(int j=0;j<li.size();j++) {
				            String[] value=li.get(j).split(",");
			  	        	int x=Integer.parseInt(value[0]);
			  	        	pstmt.setInt(1, x);
			  	        	pstmt.setString(2, value[1]);
			  	        	pstmt.setString(3, value[2]);
			  	        	int erg=pstmt.executeUpdate();
				      }
				        
				       pstmt.close(); 
				       
				      }
				    } catch (Exception e) {
				      System.err.println("connect ERROR :\n" + e.toString());
				    }
				
			}

  		      
			else {//weniger als 5 Lieferanten
				System.out.println("Es gibt weniger als 5 Lieferanten in Ihrer Datei");
			}
			
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} 
			
		
		
			
		}
	}
	


