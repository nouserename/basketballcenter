/**  
* @Title: administrator.java  
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
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Database;
import entity.User;




public class Administrator extends User{
	
	
	/**
	 * @author skk
	 *objective:查找所有需要验证的课程
	 */
	public Curriculum[] searchVerifyCurriculums(int state) throws SQLException{
		String sql;
		if(state == -1)	//返回所有课程
			sql = "SELECT * FROM curriculum";
		else	//返回特定课程
			sql = "SELECT * FROM curriculum WHERE curriculum_state=" + state;
		ResultSet resultSet = Database.executeQuery(sql);
		List<Curriculum> curriculumsList = new LinkedList<Curriculum>();
		
		while (resultSet.next()) {
			curriculumsList.add(new Curriculum(Integer.parseInt(resultSet.getString("curriculum_id")), resultSet.getString("curriculum_name"), 
					Integer.parseInt(resultSet.getString("curriculum_state")), Integer.parseInt(resultSet.getString("whichCourt")),
					Integer.parseInt(resultSet.getString("teacher_id")),resultSet.getString("curriculum_overview"),
					Integer.parseInt(resultSet.getString("accomodatenumber")),Integer.parseInt(resultSet.getString("weekday")),
					Integer.parseInt(resultSet.getString("whichclass"))));
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
	 *objective:将所选定的课程设为通过审核。
	 */
	public boolean whetherPassCurriculum(int curriculumId, int state) throws SQLException{
		String sql = "UPDATE curriculum SET curriculum_state=" + state + " WHERE curriculum_id=" + curriculumId;
		int r = Database.executeUpdate(sql);
		Database.closeConnection();
		if(r == 0)
			return false;
		return true;
	}
	
	
	
	/**
	 * @author skk
	 *objective:返回特定的课程。
	 */
	public void printCurriculum(HttpServletRequest request, HttpServletResponse response,int state) throws SQLException,IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Curriculum[] curriculums = this.searchVerifyCurriculums(state);
		if(curriculums == null)
			writer.println("课程为空！");
		else {
			writer.println("<table><tr><td>课程ID</td><td>课程名称</td><td>课程状态</td><td>球场</td><td>老师ID</td><td>课程简介</td><td>可容纳学生数量</td><td>周几</td><td>课程</td><td>选项</td></tr>");
			for(int i = 0;i < curriculums.length;i++)
			{
				System.out.println(curriculums[i].getId());
				writer.println("<tr>"
						+ "<td>" + curriculums[i].getId() + "</td>"
						+ "<td>" + curriculums[i].getName() + "</td>"
						+ "<td>" + Curriculum.modifyState(curriculums[i].getState()) + "</td>"
						+ "<td>" + curriculums[i].getWhichCourt() + "</td>"
						+ "<td>" + curriculums[i].getTeacher_id() + "</td>"
						+ "<td>" + curriculums[i].getOverview() + "</td>"
						+ "<td>" + curriculums[i].getAccomodatenumber() + "</td>"
						+ "<td>" + curriculums[i].getWeekday() + "</td>"
						+ "<td>" + curriculums[i].getWhichClass() + "</td>"
						+ "<td>" + confirmState(curriculums[i].getState(), curriculums[i].getId()) + "</td>"
 						+ "</tr>");
			}
			writer.println("</table>");
		}
		writer.flush();
		writer.close();
	}
	
	/**
	 * @author skk
	 *objective:确定该课程的状态。
	 */
	public String confirmState(int state,int curriculum_id)
	{
		if(state == Curriculum.notPass || state == Curriculum.pass)
			return "无";
		else
			return "<button onclick=\"modifypassstate(" + curriculum_id + ")\">通过</button><button onclick=\"modifynotstate(" + curriculum_id +")\">不通过</button>";
	}
	
}
