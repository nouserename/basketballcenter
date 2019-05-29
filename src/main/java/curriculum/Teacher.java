/**  
* @Title: teacher.java  
* @Package curriculum  
* @Description: TODO(��һ�仰�������ļ���ʲô)  
* @author dell  
* @date 2019��5��10��  
* @version V1.0  
*/ 
package curriculum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.Database;
import entity.User;

/**
 * @author dell
 *
 */

public class Teacher extends User{
	
	
	
	public Teacher(int id,String password,String nameString,int state)
	{
		this.setId(id);
		this.setPassword(password);
		this.setNameString(nameString);
		this.setState(state);
	}
	
	/**
	 * @author skk
	 *objective:ͨ��ǰ̨��ȡ�Ŀγ����ƣ��γ�ʱ�䣬���ÿγ̲���γ̱����ʦ�����γ̱��γ̵�״̬Ϊwaitfor_verify��
	 *
	 */
	public boolean applyCurriculum(int weekday, int whichclass,int teacher_id,String curriculum_name,String curriculum_overview,int accomodateNumber,int curriculum_state) throws SQLException{
			String sql1 = "INSERT INTO curriculum (curriculum_name,curriculum_state,teacher_id,curriculum_overview,accomodatenumber,weekday,whichclass) VALUES('"
						+ curriculum_name + "','" + curriculum_state + "','" + teacher_id + "','" + curriculum_overview + "','" 
						+ accomodateNumber + "','" + weekday + "','" + whichclass  +"')";
		
			int r = Database.executeUpdate(sql1);
			if(r != 0)
			{
				String sql2 = "SELECT curriculum_id FROM curriculum WHERE curriculum_name='"+ curriculum_name + "'";
				ResultSet resultSet = Database.executeQuery(sql2);
				if(resultSet.next()) {
					String sql3 = "INSERT INTO teacher_curriculum (teacher_id,curriculum_id) VALUES(" + this.getId() + "," + Integer.parseInt(resultSet.getString("curriculum_id")) + ")";
					System.out.println(sql1);
					System.out.println(sql3);
					int k = Database.executeUpdate(sql3);
					if(k!=0)
					{
						Database.closeConnection();
						return true;
					}
				}
			}
		
		Database.closeConnection();
		return false;
	}
	
	
	/**
	 * @author dell
	 *objective:��ȡ��ʦ������ѡ��Ŀγ̡�������ʦ�Լ���id���ҡ�
	 */
	public Curriculum[] searchCurriculums() throws SQLException{
		String sql = "SELECT * FROM teacher_curriculum LEFT JOIN curriculum ON teacher_curriculum.curriculum_id=curriculum.curriculum_id WHERE teacher_curriculum.teacher_id=" + this.getId();
		System.out.println("��2����֤");
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
		for(int i=0;i<curriculumsList.size();i++)
		{
			curriculums[i] = curriculumsList.get(i);
		}
		
		return curriculums;
	}
	
	
	
}
