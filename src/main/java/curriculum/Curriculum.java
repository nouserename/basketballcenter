/**  
* @Title: curriculum.java  
* @Package curriculum  
* @Description: TODO(用一句话描述该文件做什么)  
* @author dell  
* @date 2019年5月10日  
* @version V1.0  
*/ 
package curriculum;

/**
 * @author dell
 *
 */
public class Curriculum {
	final static int waitfor_verify = 1;
	final static int notPass = 2;
	final static int pass = 3;
	
	final static String curriculum1 = "8:00--9:35";
	final static String curriculum2 = "9:55--11:30";
	final static String curriculum3 = "1:30--3:05";
	final static String curriculum4 = "3:25--5:00";
	
	private int id;
	private String name;
	private int state;
	private int whichCourt;
	private int teacher_id;
	private String overview;
	private int accomodatenumber;
	private int weekday;
	private int whichClass;
	
	
	
	/**
	 * @param id
	 * @param name
	 * @param state
	 * @param whichCourt
	 * @param time
	 */
	public Curriculum(int id, String name, int state, int whichCourt,int teacher_id, String overview,int accomodatenumber,int weekday,int whichClass) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.whichCourt = whichCourt;
		this.teacher_id = teacher_id;
		this.overview = overview;
		this.accomodatenumber = accomodatenumber;
		this.weekday = weekday;
		this.whichClass = whichClass;
	}
	
	
	
	/**
	 * @return the teacher_id
	 */
	public int getTeacher_id() {
		return teacher_id;
	}
	/**
	 * @param teacher_id the teacher_id to set
	 */
	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}
	/**
	 * @return the overview
	 */
	public String getOverview() {
		return overview;
	}
	/**
	 * @param overview the overview to set
	 */
	public void setOverview(String overview) {
		this.overview = overview;
	}
	/**
	 * @return the accomodatenumber
	 */
	public int getAccomodatenumber() {
		return accomodatenumber;
	}
	/**
	 * @param accomodatenumber the accomodatenumber to set
	 */
	public void setAccomodatenumber(int accomodatenumber) {
		this.accomodatenumber = accomodatenumber;
	}
	/**
	 * @return the weekday
	 */
	public int getWeekday() {
		return weekday;
	}
	/**
	 * @param weekday the weekday to set
	 */
	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}
	/**
	 * @return the whichClass
	 */
	public int getWhichClass() {
		return whichClass;
	}
	/**
	 * @param whichClass the whichClass to set
	 */
	public void setWhichClass(int whichClass) {
		this.whichClass = whichClass;
	}

	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	/**
	 * @return the whichCourt
	 */
	public int getWhichCourt() {
		return whichCourt;
	}
	/**
	 * @param whichCourt the whichCourt to set
	 */
	public void setWhichCourt(int whichCourt) {
		this.whichCourt = whichCourt;
	}
	
	/**
	 * @author dell
	 *objective:获取老师所有已选择的课程。根据老师自己的id查找。
	 */
	public static String modifyState(int state) {
		switch (state) {
		case Curriculum.notPass:
			return "未通过";
		case Curriculum.pass:
			return "已通过";
		case Curriculum.waitfor_verify:
			return "等待审核";
		default:
			return "未知状态";
		}
	}
	
	
	
}
