/**  
* @Title: Student.java  
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





public class Student extends User{
	
	/**
	 * @author skk
	 * objective:�������п�ѡ�γ̡�
	 *
	 */
	public Curriculum[] searOptionalCurriculums() throws SQLException{
		String sql = "SELECT * FROM curriculum WHERE curriculum_state=" + Curriculum.pass;
		List<Curriculum> curriculumsList = new LinkedList<Curriculum>();
		ResultSet resultSet = Database.executeQuery(sql);
		while (resultSet.next()) {
			curriculumsList.add(new Curriculum(Integer.parseInt(resultSet.getString("curriculum_id")), resultSet.getString("curriculum_name"), 
					Integer.parseInt(resultSet.getString("state")),Integer.parseInt("whichcourt"),
					Integer.parseInt(resultSet.getString("curriculum_state")), Integer.parseInt(resultSet.getString("whichCourt")),
					resultSet.getString("curriculum_time")));
		}
		
		Database.closeConnection();
		if(curriculumsList.size() == 0)
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
	 *objective:���������id�����Լ�������ѡ�Ŀγ̡�
	 */
	public Curriculum[] searchselectedCurriculums() throws SQLException{
		String sql = "SELECT * FROM student_curriculum LEFT JOIN ON curriculum WHERE student_id=" + this.getId();
		ResultSet resultSet = Database.executeQuery(sql);
		List<Curriculum> curriculumsList = new LinkedList<Curriculum>();
		while (resultSet.next()) {
			curriculumsList.add(new Curriculum(Integer.parseInt(resultSet.getString("curriculum_id")), resultSet.getString("curriculum_name"), 
					Integer.parseInt(resultSet.getString("curriculum_state")), Integer.parseInt(resultSet.getString("whichCourt")),
					resultSet.getString("curriculum_time")));
		}
		
		Database.closeConnection();
		if(curriculumsList.size() == 0)
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
	 *objective:ѡ���ض��γ�,���ÿγ̲���ѧ���γ̱�
	 */
	public boolean selectCurriculum(int curriculumId) throws SQLException{
		String sql = "INSERT INTO student_curriculum(student_id,curriculum_id) VALUES(" + this.getId() + "," + curriculumId + ")";
		int r = Database.executeUpdate(sql);
		Database.closeConnection();
		if(r==0)
			return false;
		return true;
	}
	
	/**
	 * @author skk
	 *objective:����ѡ�γ̴�ѧ�������γ̱���ɾ����
	 */
	public boolean deleteCurriculum(int curriculumId) throws SQLException{
		String sql = "DELETE FROM student_curriculum WHERE curriculum_id=" + curriculumId + " AND student_id=" + this.getId();
		int r = Database.executeUpdate(sql);
		if(r==0)
			return false;
		return true;
	}
	

}
