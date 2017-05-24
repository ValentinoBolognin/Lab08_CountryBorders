package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode,StateAbb,StateNme " + "FROM country " + "ORDER BY StateAbb ";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			List<Country> nazioni = new ArrayList<Country>();
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				nazioni.add(c);
			}

			conn.close();
			return nazioni;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		}
	}

	public List<Border> getCountryPairs(int anno) {
		
		String sql = "SELECT state1no, state2no, year FROM contiguity WHERE year<=? AND conttype = 1 ";
		
		List<Country> nazioni = this.loadAllCountries();
		List<Border> confini = new ArrayList<Border>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
			
				int ccode1= rs.getInt("state1no");
				int ccode2= rs.getInt("state2no");
				int year = rs.getInt("year");
				
				Country c1 = null;
				Country c2 = null;
				Border b = null;
				
				for(Country c : nazioni) {
					if(c.getCcode()==ccode1)
						c1 = c;
					if(c.getCcode()==ccode2)
						c2 = c;
				}
				
				if(c1 != null && c2 != null)
					b= new Border(c1, c2, year);
				
				confini.add(b);
			}

			conn.close();
			return confini;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- getCountryPairs");
			throw new RuntimeException("Database Error");
		}	
	}
}
