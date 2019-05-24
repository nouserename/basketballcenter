/**  
* @Title: User.java  
* @Package entity  
* @Description: TODO(用一句话描述该文件做什么)  
* @author PC  
* @date 2019年5月10日  
* @version V1.0  
*/ 

package entity;


/**  
* @ClassName: User  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author PC  
* @date 2019年5月10日  
*    
*/
public class User {
	final public static int STUDENGT  = 1;
	
	final public static int TEACHER	= 2;
	
	final public static int ADMIN = 3;
	
	final public static int OFFCAMPUS = 4;
	
	
	
	
	private int id;
	private String password;
	private String nameString;
	private int state;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the nameString
	 */
	public String getNameString() {
		return nameString;
	}
	/**
	 * @param nameString the nameString to set
	 */
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	
	
	public Court reserveCourt()
	{
		Court court = null;
		String sqlString = "";
		
		
		return court;
	}

}
