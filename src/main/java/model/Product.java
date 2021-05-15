package model;
import java.io.Serializable;

public class Product implements Serializable{
	//*************************************
	// メンバ変数の定義
	//*************************************
	private int id;
	private String name;
	private int price;
	private String updated;
	
	
	//*************************************
	// コンストラクタ
	//*************************************
	public Product(){}
	
	public Product(String name,int price,String updated){
		this.name=name;
		this.price=price;
		this.updated=updated;
	}
	
	public Product(int id,String name,int price,String updated){
		this(name,price,updated);
		this.id=id;
		
	}
	
	//*************************************
	// メソッドの定義
	//*************************************
	// ゲッターとセッター
	
	// IDを取得
	public int getId() {
		return id;
	}
	
	// IDを設定
	public void setId(int id) {
		this.id = id;
	}
	
	// 名前を取得
	public String getName() {
		return name;
	}
	
	// 名前を設定
	public void setName(String name) {
		this.name = name;
	}
	
	// 値段を取得
	public int getPrice() {
		return price;
	}
	
	// 値段を設定
	public void setPrice(int price) {
		this.price = price;
	}
	
	// 更新日を取得
	public String getUpdated() {
		return updated;
	}
	
	// 更新日を設定
	public void setUpdated(String updated) {
		this.updated = updated;
	}	

}
