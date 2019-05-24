/**  
* @Title: modifyPasswd.java  
* @Package onlineshop  
* @Description: TODO(用一句话描述该文件做什么)  
* @author dell  
* @date 2019年5月3日  
* @version V1.0  
*/ 
package onlineshop;

import java.awt.desktop.PrintFilesEvent;
import java.sql.ResultSet;

import entity.Database;
import tool.Security;

/**
 * @author dell
 *
 */
public class modifyPasswd {

	/**  
	* @Title: main  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param args    参数  
	* @return void    返回类型  
	* @throws  
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sqlString2 = "SELECT * FROM orders LEFT JOIN product on orders.product_id = product.product_id WHERE customer_id = '15011329606'";
		try {
			ResultSet resultSet = Database.executeQuery(sqlString2);
			
			while (resultSet.next()) {
				System.out.println("找到了！");
				System.out.println(resultSet.getString("order_id"));
				System.out.println(resultSet.getString("image1"));
				System.out.println(resultSet.getString("price"));
				
			}
			
			System.out.println("结束！");
			Database.closeConnection();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
