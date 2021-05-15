package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Product;

// Data Access Object（DAO）
public class ProductDAO {
	//*************************************
	// メンバ変数の定義
	//*************************************
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	//*************************************
	// メソッドの定義
	//*************************************
	// MySQLとの接続処理
	private void getConnection() throws NamingException, SQLException{	
			Context context=new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/jsp");
			this.db=ds.getConnection();	
	}
	
	// MySQLとの切断処理
	private void disconnect(){
		try{
			if(rs != null){rs.close();}
			if(ps != null){ps.close();}
			if(db != null){db.close();}
		}catch(SQLException e){
			e.printStackTrace();
		}	
	}
	
	// priductsテーブルのデータを全て取得
	public List<Product> findAll(){
		
		List<Product> productList=new ArrayList<>();
		try {
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM products ORDER BY id DESC");
			rs=ps.executeQuery();
			while(rs.next()){
				int id=rs.getInt("id");
				String name=rs.getString("name");
				int price=rs.getInt("price");
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String updated=sdf.format(rs.getTimestamp("updated"));
				Product product=new Product(id,name,price,updated);
				productList.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return productList;
	}
	
	// 引数でしたProductデータを新規追加
	public boolean insertOne(Product product){
		try {
			this.getConnection();
			ps=db.prepareStatement("INSERT INTO products(name,price,updated) VALUES(?,?,?)");
			ps.setString(1,product.getName());
			ps.setInt(2,product.getPrice());
			ps.setString(3, product.getUpdated());
			int result=ps.executeUpdate();
			if(result != 1){
				return false;
			}
		} catch (SQLException e) {	
			e.printStackTrace();
		} catch (NamingException e) {	
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return true;
	}
	
	// 引数でしたIDのデータを1件検索
	public Product findOne(int id){
		Product product=null;
		try{
			this.getConnection();
			ps=db.prepareStatement("SELECT * FROM products WHERE id=?");
			ps.setInt(1, id);
			rs=ps.executeQuery();
			if(rs.next()){
				String name=rs.getString("name");
				int price=rs.getInt("price");
				String updated=rs.getString("updated");
				product=new Product(id,name,price,updated);
			}
			
		}catch (SQLException e) {	
			e.printStackTrace();
		} catch (NamingException e) {	
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return product;
	}
	
	// 引数でしたProductデータを更新
	public boolean updateOne(Product product){
		try{
			this.getConnection();
			ps=db.prepareStatement("UPDATE products SET name=?,price=?,updated=? WHERE id=?");
			ps.setString(1, product.getName());
			ps.setInt(2, product.getPrice());
			ps.setString(3, product.getUpdated());
			ps.setInt(4, product.getId());
			int result=ps.executeUpdate();
			if(result != 1){
				return false;
			}
		}catch (SQLException e) {	
			e.printStackTrace();
		} catch (NamingException e) {	
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return true;
	}
	
	// 引数でしたIDのデータを削除
	public boolean deleteOne(int id){
		
		try{
			this.getConnection();
			ps=db.prepareStatement("DELETE FROM products WHERE id=?");
			ps.setInt(1, id);
			int result=ps.executeUpdate();
			if(result != 1){
				return false;
			}
		}catch (SQLException e) {	
			e.printStackTrace();
		} catch (NamingException e) {	
			e.printStackTrace();
		}finally{
			this.disconnect();
		}
		return true;
	}
	
}
