package curriculum;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;

/**
 * Servlet implementation class StudentSelectCurriculum
 */
public class StudentSelectCurriculum extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Student student = new Student(2, "123456", "宋凯凯", User.STUDENGT);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentSelectCurriculum() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String state = request.getParameter("state");
		try {
			if(state.equals("all"))
			{
				student.printCurriculum(request, response, -1, -1);
			}else if (state.equals("part")) {
				student.printCurriculum(request, response, Integer.parseInt(request.getParameter("weekday")), Integer.parseInt(request.getParameter("whichClass")));
			}else if (state.equals("xuanke")) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				if(student.selectCurriculum(Integer.parseInt(request.getParameter("curriculumId"))))
					writer.println("修改成功！");
				else
					writer.println("修改失败！（请稍后再试）");
				writer.flush();
				writer.close();
			}else if (state.equals("yixuankecheng")) {
				student.printSelectedCurriculum(request, response);
			}else if (state.equals("shanchukecheng")) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = response.getWriter(); 
				if(student.deleteCurriculum(Integer.parseInt(request.getParameter("curriculumId"))))
					writer.println("修改成功！");
				else
					writer.println("修改失败！请稍后再试。");
				writer.flush();
				writer.close();
			}
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
		doGet(request, response);
	}

}
