/**  
* @Title: administrator.java  
* @Package curriculum  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
* @author dell  
* @date 2019��5��10��  
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
	 *objective:����������Ҫ��֤�Ŀγ�
	 */
	public Curriculum[] searchVerifyCurriculums(int state) throws SQLException{
		String sql;
		if(state == -1)	//�������пγ�
			sql = "SELECT * FROM curriculum";
		else	//�����ض��γ�
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
	 *objective:����ѡ���Ŀγ���Ϊͨ����ˡ�
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
	 *objective:�����ض��Ŀγ̡�
	 */
	public void printCurriculum(HttpServletRequest request, HttpServletResponse response,int state) throws SQLException,IOException
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		Curriculum[] curriculums = this.searchVerifyCurriculums(state);
		if(curriculums == null)
			writer.println("�γ�Ϊ�գ�");
		else {
			writer.println("<table><tr><td>�γ�ID</td><td>�γ�����</td><td>�γ�״̬</td><td>��</td><td>��ʦID</td><td>�γ̼��</td><td>������ѧ������</td><td>�ܼ�</td><td>�γ�</td><td>ѡ��</td></tr>");
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
	 *objective:ȷ���ÿγ̵�״̬��
	 */
	public String confirmState(int state,int curriculum_id)
	{
		if(state == Curriculum.notPass || state == Curriculum.pass)
			return "��";
		else
			return "<button onclick=\"modifypassstate(" + curriculum_id + ")\">ͨ��</button><button onclick=\"modifynotstate(" + curriculum_id +")\">��ͨ��</button>";
	}
	
}
