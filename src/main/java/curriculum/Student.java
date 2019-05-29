/**  
* @Title: Student.java  
* @Package curriculum  
* @Description: TODO(用一句话描述该文件做什么)  
* @author dell  
* @date 2019年5月10日  
* @version V1.0  
*/ 
package curriculum;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Database;
import entity.User;





public class Student extends User{
	
	public Student(int id,String password,String nameString,int state)
	{
		this.setId(id);
		this.setPassword(password);
		this.setNameString(nameString);
		this.setState(state);
	}
	
	/**
	 * @author skk
	 * objective:如果星期和课堂都为-1，则是查找所有课程，否则为查找特定课程。
	 *
	 */
	public Curriculum[] searchOptionalCurriculums(int weekday,int whichClass) throws SQLException{
		String sql;
		if(weekday == -1 && whichClass == -1)
			sql = "SELECT * FROM curriculum WHERE curriculum_state=" + Curriculum.pass;
		else {
			sql = "SELECT * FROM curriculum WHERE curriculum_state=" + Curriculum.pass + " AND weekday=" + weekday + " AND whichclass=" + whichClass;
		}
		List<Curriculum> curriculumsList = new LinkedList<Curriculum>();
		ResultSet resultSet = Database.executeQuery(sql);
		while (resultSet.next()) {
			curriculumsList.add(new Curriculum(Integer.parseInt(resultSet.getString("curriculum_id")), 
					resultSet.getString("curriculum_name"), 
					Integer.parseInt(resultSet.getString("curriculum_state")), 
					Integer.parseInt(resultSet.getString("whichCourt")), 
					Integer.parseInt(resultSet.getString("teacher_id")),
					resultSet.getString("curriculum_overview"), 
					Integer.parseInt(resultSet.getString("accomodatenumber")),
					Integer.parseInt(resultSet.getString("personnumber")),
					Integer.parseInt(resultSet.getString("weekday")), Integer.parseInt(resultSet.getString("whichclass"))));
		}
		
		Database.closeConnection();
		if(curriculumsList.isEmpty())
			return null;
		
		Curriculum[] curriculums = new Curriculum[curriculumsList.size()];
		for(int i=0;i<curriculumsList.size();i++)
		{
			curriculums[i] = curriculumsList.get(i);
		}
		return curriculums;
	}
	
	/**
	 * @author skk
	 *objective:根据自身的id查找自己所有已选的课程。
	 */
	public Curriculum[] searchselectedCurriculums() throws SQLException{
		System.out.println("查询已选课程！");
		String sql = "SELECT * FROM student_curriculum LEFT JOIN  curriculum ON student_curriculum.curriculum_id=curriculum.curriculum_id WHERE student_id=" + this.getId();
		ResultSet resultSet = Database.executeQuery(sql);
		List<Curriculum> curriculumsList = new LinkedList<Curriculum>();
		while (resultSet.next()) {
			curriculumsList.add(new Curriculum(Integer.parseInt(resultSet.getString("curriculum_id")), 
					resultSet.getString("curriculum_name"), 
					Integer.parseInt(resultSet.getString("curriculum_state")), 
					Integer.parseInt(resultSet.getString("whichCourt")), 
					Integer.parseInt(resultSet.getString("teacher_id")),
					resultSet.getString("curriculum_overview"), 
					Integer.parseInt(resultSet.getString("accomodatenumber")),
					Integer.parseInt(resultSet.getString("personnumber")),
					Integer.parseInt(resultSet.getString("weekday")), Integer.parseInt(resultSet.getString("whichclass"))));
		}
		
		Database.closeConnection();
		if(curriculumsList.isEmpty())
			return null;
		
		Curriculum[] curriculums = new Curriculum[curriculumsList.size()];
		for(int i = 0;i<curriculumsList.size();i++)
		{
			curriculums[i] = curriculumsList.get(i);
		}
		
		return curriculums;
	}
	
	
	
	/**
	 * @author skk
	 *objective:选择特定课程,将该课程插入学生课程表。
	 */
	public boolean selectCurriculum(int curriculumId) throws SQLException{
		String sql = "INSERT INTO student_curriculum(student_id,curriculum_id) VALUES(" + this.getId() + "," + curriculumId + ")";
		String sqlString = "UPDATE curriculum SET personnumber=(personnumber+1) WHERE curriculum_id=" + curriculumId;
		int r = Database.executeUpdate(sql);
		int q = Database.executeUpdate(sqlString);
		Database.closeConnection();
		if(r==0 || q==0)
			return false;
		return true;
	}
	
	/**
	 * @author skk
	 *objective:将所选课程从学生——课程表中删除。
	 */
	public boolean deleteCurriculum(int curriculumId) throws SQLException{
		String sql = "DELETE FROM student_curriculum WHERE curriculum_id=" + curriculumId + " AND student_id=" + this.getId();
		String sqlString = "UPDATE curriculum SET personnumber=(personnumber-1) WHERE curriculum_id=" + curriculumId;
		int r = Database.executeUpdate(sql);
		int q = Database.executeUpdate(sqlString);
		if(r==0 || q == 0)
			return false;
		return true;
	}
	

	
	/**
	 * @author skk
	 *objective: 该学生已选课程的map。
	 */
	public List<Integer> selectedCurriclum() throws SQLException
	{
		List<Integer> list = new ArrayList<Integer>();
		String sql = "SELECT * FROM student_curriculum WHERE student_id=" + this.getId();
		ResultSet resultSet = Database.executeQuery(sql);
		while (resultSet.next()) {
			list.add(Integer.parseInt(resultSet.getString("curriculum_id")));
		}
		
		return list;
	}
	
	/**
	 * @author skk
	 *objective: 该学生已选课程的map。
	 */
	public String option(int curriculum_id,List<Integer> list)
	{
		if(list.contains(curriculum_id))
			return "已选";
		else
			return "<button onclick=\"tiaoxuankecheng(" + curriculum_id + ")\">选择</button>";
	}
	
	
	/**
	 * @author skk
	 *objective: 打印课程。
	 */
	public void printCurriculum(HttpServletRequest request, HttpServletResponse response,int weekday,int whichClass) throws IOException,SQLException
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Curriculum[] curriculums = searchOptionalCurriculums(weekday, whichClass);
		List<Integer> list = selectedCurriclum();	//该学生所有已选课程
		if(curriculums == null)
			writer.println("没有课程！");
		else {
			writer.println("<table><tr><th>课程ID</th><th>课程名称</th><th>球场</th><th>老师ID</th><th>课程简介</th><th>可容纳学生数量</th><th>已有人数</th><th>周几</th><th>课程</th><th>选项</th></tr>");
			for(int i = 0;i<curriculums.length;i++)
			{
				writer.println("<tr>"
						+ "<td>" + curriculums[i].getId() + "</td>"
						+ "<td>" + curriculums[i].getName() + "</td>"
						+ "<td>" + curriculums[i].getWhichCourt() + "</td>"
						+ "<td>" + curriculums[i].getTeacher_id() + "</td>"
						+ "<td>" + curriculums[i].getOverview() + "</td>"
						+ "<td>" + curriculums[i].getAccomodatenumber() + "</td>"
						+ "<td>" + curriculums[i].getPersonNubmber() + "</td>"
						+ "<td>" + curriculums[i].getWeekday() + "</td>"
						+ "<td>" + curriculums[i].getWhichClass() + "</td>"
						+ "<td>" + option(curriculums[i].getId(), list) +"</td>"
						+ "</tr>"
						);
			}
			writer.println("</table>");			
		}
		writer.flush();
		writer.close();
	}
	
	
	
	
	/**
	 * @author skk
	 *objective: 打印已选课程。
	 */
	public void printSelectedCurriculum(HttpServletRequest request, HttpServletResponse response) throws IOException,SQLException
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Curriculum[] curriculums = searchselectedCurriculums();
		if(curriculums == null)
		{
			writer.println("没有课程！");
			System.out.println("没有课程");
		}
		else {
			writer.println("<table><tr><th>课程ID</th><th>课程名称</th><th>球场</th><th>老师ID</th><th>课程简介</th><th>可容纳学生数量</th><th>已有人数</th><th>周几</th><th>课程</th><th>选项</th></tr>");
			for(int i = 0;i<curriculums.length;i++)
			{
				System.out.println(curriculums[i].getId());
				writer.println("<tr>"
						+ "<td>" + curriculums[i].getId() + "</td>"
						+ "<td>" + curriculums[i].getName() + "</td>"
						+ "<td>" + curriculums[i].getWhichCourt() + "</td>"
						+ "<td>" + curriculums[i].getTeacher_id() + "</td>"
						+ "<td>" + curriculums[i].getOverview() + "</td>"
						+ "<td>" + curriculums[i].getAccomodatenumber() + "</td>"
						+ "<td>" + curriculums[i].getPersonNubmber() + "</td>"
						+ "<td>" + curriculums[i].getWeekday() + "</td>"
						+ "<td>" + curriculums[i].getWhichClass() + "</td>"
						+ "<td><button onclick=\"shanchukecheng("+ curriculums[i].getId() +")\">删除课程</button></td>"
						+ "</tr>"
						);
			}
			writer.println("</table>");
		}
		writer.flush();
		writer.close();
		
	}

	
	
	
}
