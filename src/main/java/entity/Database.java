/**  
* @Title: Database.java  
* @Package entity  
* @Description: TODO(用一句话描述该文件做什么)  
* @author WenbinYu  
* @date 2019年3月22日  
* @version V1.0  
*/ 

package entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**  
* @ClassName: Database  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author WenbinYu  
* @date 2019年3月22日  
*    
*/
public class Database {
	//?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false
    private static final  String url = "jdbc:mysql://39.105.17.254:3306/basketball_court?useUnicode=true&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false";
private static final  String username = "root";
private static final  String password = "3FB4567eab7d";
private static Connection connection;
private static PreparedStatement statement;
private static java.sql.CallableStatement callableStatement;
    public static void getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        
        
    }
    public static void closeConnection(){
	try {
		
		
		if(connection!=null)connection.close();
		if (statement!=null) {
			statement.close();
		}
		if(callableStatement!=null)callableStatement.close();
	} catch (Exception e) {
		e.fillInStackTrace();
	}
}
    /**  
    * @Title: executeQuery  
    * @Description: 封装了jdbc的对应方法  
    * @param @param connection
    * @param @param sql
    * @param @return
    * @param @throws SQLException    参数  
    * @return ResultSet    返回类型  
    * @throws  
    */  
    private static ResultSet executeQuery(Connection connection,String sql) throws SQLException {
    	statement = connection.prepareStatement(sql);
    	ResultSet resultSet = statement.executeQuery();
    	return resultSet;
    }
    
    
    private static boolean callableExecute(Connection connection,String callName) throws SQLException {
    	callableStatement = connection.prepareCall(callName);
		return callableStatement.execute();
    }
    
    public static boolean callableExecute(String callName) throws SQLException {
		getConnection();
		return callableExecute(connection, callName);
	}
     
    private static int executeUpdate(Connection connection,String sql) throws SQLException {
    	statement = connection.prepareStatement(sql);
    	int influenceLine = statement.executeUpdate();
    	return influenceLine;
    }
    
    /**  
     * @Title: executeUpdate  
     * @Description: 封装了jdbc对应的方法  
     * @param @param connection
     * @param @param sql
     * @param @return
     * @param @throws SQLException    参数  
     * @return int    返回类型  
     * @throws  
     */ 
    public static int executeUpdate(String sql) throws SQLException {
    	getConnection();
    	return executeUpdate(connection, sql);
    }
    
    /**  
     * @Title: executeQuery  
     * @Description: 封装了jdbc的对应方法  
     * @param @param connection
     * @param @param sql
     * @param @return
     * @param @throws SQLException    参数  
     * @return ResultSet    返回类型  
     * @throws  
     */ 
    public static ResultSet executeQuery(String sql) throws SQLException {
    	getConnection();
    	return executeQuery(connection, sql);
    }

}


