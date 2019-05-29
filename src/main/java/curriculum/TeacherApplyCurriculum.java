package curriculum;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;

/**
 * Servlet implementation class TeacherApplyCurriculum
 */
public class TeacherApplyCurriculum extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Teacher teacher = new Teacher(2, "123456", "宋凯凯", User.TEACHER);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeacherApplyCurriculum() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		System.out.println("第1处验证");
		try {
			Curriculum[] selectedCurriculums = teacher.searchCurriculums();
			System.out.println(selectedCurriculums.length);
			if(selectedCurriculums.length == 0)
				writer.println("没有课程申请！");
			else {
				writer.println("<table><tr><td>课程ID</td><td>课程名称</td><td>课程状态</td><td>球场</td><td>老师ID</td><td>课程简介</td><td>可容纳学生数量</td><td>周几</td><td>课程</td></tr>");
				for (int i = 0; i < selectedCurriculums.length; i++) {
					writer.println("<tr>"
								+ "<td>" + selectedCurriculums[i].getId() + "</td>"
								+ "<td>" + selectedCurriculums[i].getName() + "</td>"
								+ "<td>" + Curriculum.modifyState(selectedCurriculums[i].getState()) + "</td>"
								+ "<td>" + selectedCurriculums[i].getWhichCourt() + "</td>"
								+ "<td>" + selectedCurriculums[i].getTeacher_id() + "</td>"
								+ "<td>" + selectedCurriculums[i].getOverview() + "</td>"
								+ "<td>" + selectedCurriculums[i].getAccomodatenumber() + "</td>"
								+ "<td>" + selectedCurriculums[i].getWeekday() + "</td>"
								+ "<td>" + selectedCurriculums[i].getWhichClass() + "</td>"
								+ "</tr>"
								);
				}
				writer.println("</table>");
			}
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		request.setCharacterEncoding("utf-8");
		String weekday = request.getParameter("weekday");
		String whichClass = request.getParameter("whichClass");
		String teacherName = request.getParameter("teacherName");
		String curriculumName = request.getParameter("curriculumName");
		String curriculumOverview = request.getParameter("curriculumOverview");
		String accomodateNumber = request.getParameter("accomodateNumber");
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			boolean bool = teacher.applyCurriculum(Integer.parseInt(weekday), Integer.parseInt(whichClass), 
														teacher.getId(), curriculumName, curriculumOverview, 
														Integer.parseInt(accomodateNumber), Curriculum.waitfor_verify);
			if(bool == true)
				writer.println("申请成功!");
			else
				writer.println("申请失败!");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
