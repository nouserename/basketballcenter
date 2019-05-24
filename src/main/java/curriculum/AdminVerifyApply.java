package curriculum;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminVerifyApply
 */
public class AdminVerifyApply extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Administrator admin = new Administrator();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminVerifyApply() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String event = request.getParameter("event");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		try {
			if(event.equals("all"))	//所有课程
			{
				admin.printCurriculum(request, response, -1);
			}else if (event.equals("wait")) {	//等待审核的课程
				admin.printCurriculum(request, response, Curriculum.waitfor_verify);
			}else if (event.equals("pass")) {	//审核通过的课程
				admin.printCurriculum(request, response, Curriculum.pass);
			}else {	//审核未通过的课程
				admin.printCurriculum(request, response, Curriculum.notPass);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String event = request.getParameter("event");
		String curriculum_Id = request.getParameter("curriculumId");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			if(event.equals("pass"))
			{
				if(admin.whetherPassCurriculum(Integer.parseInt(curriculum_Id), Curriculum.pass))
					writer.println("课程通过！（修改成功）");
				else
					writer.println("修改失败，请稍后再试！");
			}else {
				if(admin.whetherPassCurriculum(Integer.parseInt(curriculum_Id), Curriculum.notPass))
					writer.println("课程不通过！（修改成功）");
				else
					writer.println("修改失败，请稍后再试！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		writer.flush();
		writer.close();
		
	}

}
