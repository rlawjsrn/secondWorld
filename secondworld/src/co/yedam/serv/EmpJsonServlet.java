package co.yedam.serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.yedam.common.EmpDAO;
import co.yedam.common.Employee;

@WebServlet("/empJsonServlet.json")
public class EmpJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmpJsonServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath()).append("Hong");
		PrintWriter out = response.getWriter();  //출력메서드
		EmpDAO dao = new EmpDAO();  //초기화된 EmpDAO 클래스를 가져와 사용하겠다. 여기서는 EmpDAO 클래스를 dao로 부르겠다. 
		
		// 1. db에서 데이터를 가져오기
		List<Employee> list = dao.getEmpList(); //dao에 있는 getEmpList메서드를 가져온 뒤, list에 담겠다.
		// {"name":"Hongkildong", "age":15, "score": 80}
		//out.println("{\"name\":\"Hongkildong\", \"age\":15, \"score\": 80}");
		// {"empId":"?","lname":"?","email":"?","hireDate":"?","job":"?"}
		int cnt = 0; 
		int lastCnt = list.size(); //list 배열의 길이 = 5
		
		// 2. db에서 가져온 데이터를 json 형식으로 만들기
		out.print("[");
		for (Employee emp : list) { //for(배열 안 요소의 값을 지칭하는애 : 돌리고자 하는 배열) 배열을 for문 돌리는 방식
			out.print("{\"empId\":\""+emp.getEmployeeId()
					+"\",\"lname\":\""+emp.getLastName()
					+"\",\"email\":\""+emp.getEmail()
					+"\",\"hireDate\":\""+emp.getHireDate()
					+"\",\"job\":\""+emp.getJobId()+"\"}");
			// , 마지막 건(,)
			cnt++; //cnt=1, 2...5
			if(cnt != lastCnt) {
				out.print(",");
			}
		}
		out.println("]");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
