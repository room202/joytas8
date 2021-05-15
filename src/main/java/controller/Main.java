package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProductDAO;
import model.Product;

@WebServlet("/main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// **********************************************
	// GETで来た場合
	// **********************************************
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 自作クラスのProductDAOをインスタンス化
		ProductDAO dao=new ProductDAO();
		
		// actionパラメーター取得
		String action=request.getParameter("action");
		
		// パラメーター判定
		if(action != null && action.equals("delete")){
			// deleteの場合
			// 対象データを削除
			dao.deleteOne(Integer.parseInt(request.getParameter("id")));
			
			// 応答データ作成
			request.setAttribute("msg", "1件削除しました。");
			
		}else if(action != null && action.equals("update")){
			// updateの場合
			// 対象データを更新
			// 対象データを検索
			Product product = dao.findOne(Integer.parseInt(request.getParameter("id")));
			
			// 応答データ作成
			request.setAttribute("product", product);
			request.setAttribute("title", "項目を編集してください。");			
		}
		List<Product> list=dao.findAll();
		request.setAttribute("list", list);
		RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/view/main.jsp");
		rd.forward(request, response);
	}

	// **********************************************
	// POSTで来た場合
	// **********************************************
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 文字コード設定
		request.setCharacterEncoding("UTF-8");
		
		// nameパラメーター取得
		String name=request.getParameter("name");
		
		// priceパラメーター取得
		String price=request.getParameter("price");
		
		// 入力値チェック
		if(name.isEmpty() || price.isEmpty()){
			request.setAttribute("err","未記入の項目があります！");
		}else{
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String updated=sdf.format(date);
			ProductDAO dao=new ProductDAO();
			String id=request.getParameter("id");
			if(id != null){
				// 既に登録済みデータの場合は更新
				dao.updateOne(new Product(Integer.parseInt(id),name,Integer.parseInt(price),updated));
				request.setAttribute("msg","1件更新しました。");
			}else{
				// 未登録の場合は、新規追加
				dao.insertOne(new Product(name,Integer.parseInt(price),updated));
				request.setAttribute("msg","1件登録しました。");
			}	
		}		
		doGet(request,response);		
	}
}
