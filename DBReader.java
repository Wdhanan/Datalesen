import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Scanner;

public class DBReader {
	

	public static void main(String[] args) {
		try {
		      

		      try (Connection cn = new ConnectDB().connect();) {

		        System.out.println("Verbindung ok!");
		        //DBReader fur 4) d.h alle Daten werden ausgegeben
		        try(Statement statment=cn.createStatement();
				        ResultSet rs=statment.executeQuery("select * from Lieferant");){
				        while(rs.next())
				        	System.out.println(rs.getInt("LNr")+";"+rs.getString("Name")+";"+rs.getString("PLZ"));
				        
				        } 
		        //DBReader fur 5) dh Sie geben einen Bereich ein(Bei mir ist min 0 und max 6 moeglich)
		        //dann werden Elemente nur in dem Bereich ausgegeben
		        Scanner ein=new Scanner(System.in); 
		        
		        System.out.println("Geben Sie bitte Ihren minimum Lieferantnummer ein");
		        int min=ein.nextInt();
		        assert(min>=0&&min<=6);//da ich nur 7 Daten habe(von 0 bis 6)
		        System.out.println("Geben Sie bitte Ihren maximum Lieferantnummer ein");
		        int max=ein.nextInt();
		        assert(min>=0&&min<=6&&min<max);////da ich nur 7 Daten habe(von 0 bis 6)und min muss kleiner als max sein
		       
		        ein.close();
		        String sql="select * from Lieferant where LNr between?and?";
		        PreparedStatement statment=cn.prepareStatement(sql);
		        		statment.setInt(1, min);
		        		statment.setInt(2, max);
               ResultSet rs=statment.executeQuery();
		        while(rs.next())
		        	System.out.println(rs.getInt("LNr")+";"+rs.getString("Name")+";"+rs.getString("PLZ"));
		        
		        
		      }
		    } catch (Exception e) {
		      System.err.println("connect ERROR :\n" + e.toString());
		    }

	}

}
