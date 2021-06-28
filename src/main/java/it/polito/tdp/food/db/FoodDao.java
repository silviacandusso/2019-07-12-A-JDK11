package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(int porz, Map<Integer,Food> idMap){
		String sql = "SELECT f.food_code,f.display_name, COUNT(*) AS porzioni "
				+ "FROM food f, `portion`AS  p "
				+ "WHERE f.food_code=p.food_code "
				+ "GROUP BY f.food_code,f.display_name "
				+ "HAVING porzioni<=? "
				+ "ORDER BY f.display_name ASC" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, porz);
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					Food f=new Food(res.getInt("food_code"),res.getString("display_name"));
					list.add(f);
					idMap.put(res.getInt("food_code"), f);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer,Food>idMap){
		
			String sql = "SELECT fc1.food_code,fc2.food_code, AVG(c.condiment_calories) as peso "
					+ "FROM food_condiment fc1, food_condiment fc2, condiment c "
					+ "WHERE fc1.condiment_code=fc2.condiment_code AND fc1.food_code>fc2.food_code "
					+ "AND c.condiment_code=fc1.condiment_code AND c.condiment_code=fc2.condiment_code "
					+ "GROUP BY fc1.food_code,fc2.food_code" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				List<Adiacenza> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
						Food f1=idMap.get(res.getInt("fc1.food_code"));
						Food f2=idMap.get(res.getInt("fc2.food_code"));
						if(f1!= null && f2!=null && !f1.equals(f2)) {
							list.add(new Adiacenza(f1,f2,res.getInt("peso")));
						}
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				e.printStackTrace();
				return null ;
			}
	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
}
