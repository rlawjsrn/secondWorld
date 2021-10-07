package co.yedam.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpDAO extends DAO {
	
	   public boolean checkId(String id) {
		      
		      connect();
		      String sql = "SELECT * FROM empl_demo where employee_id = ?";
		      try {
		         psmt = conn.prepareStatement(sql);
		         psmt.setString(1, id);
		         rs = psmt.executeQuery();
		         
		         if ( rs.next()) {
		            return false;
		         } else {
		            return true;
		         }
		         
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
		         disconnect();
		      }
		      return false;
		   }
	    //메소드의 반환값 타입 - 메소드 이름()
   public List<Employee> getEmpList() { //db에서 가져온 데이터를 list화 시키기
      connect();
      
      List<Employee> list = new ArrayList<>(); //배열 틀을 만들겠다.

      String sql = "select * from empl_demo order by 1 desc";
      
      try {
         stmt = conn.createStatement();
         rs = stmt.executeQuery(sql); //executeQuery = sql 수행 메소드
         while (rs.next()) { // query결과값 있는만큼 돌린다.
            Employee emp = new Employee();
            emp.setEmployeeId(rs.getInt("employee_id"));
            emp.setLastName(rs.getString("last_name"));
            emp.setEmail(rs.getString("email"));
            emp.setHireDate(rs.getString("hire_date").substring(0,10));
            emp.setJobId(rs.getString("job_id"));

            list.add(emp);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         disconnect();
      }
      return list; //return '값'을 반환해라. 이 메소드를 호출한 애한테 값을 준다.
   }

   public boolean insertEmp(Employee emp) {
      connect();
      String sql = "insert into empl_demo (employee_id, last_name, email, job_id, hire_date)\r\n"
            + "values(?, ?, ?, ?, ?)";
      try {
         psmt = conn.prepareStatement(sql);
         psmt.setInt(1, emp.getEmployeeId());
         psmt.setString(2, emp.getLastName());
         psmt.setString(3, emp.getEmail());
         psmt.setString(4, emp.getJobId());
         psmt.setString(5, emp.getHireDate());
         int r = psmt.executeUpdate();
         System.out.println(r + "입력됨.");
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         disconnect();
      }
   }

   public void updateEmp(String id, String phone, String salary) {
      connect();
      String sql = "update empl_demo set phone_number=?, salary=? where employee_id=?";
      try {
         psmt = conn.prepareStatement(sql);
         psmt.setString(1, phone);
         psmt.setString(2, salary);
         psmt.setString(3, id);
         int r = psmt.executeUpdate();
         System.out.println(r + "건 수정됨.");

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         disconnect();
      }
   }

   public void deleteEmp(int empId) {
      connect(); // conn=dbconnecton.Connection (DB연결)
      String sql = "delete from empl_demo where employee_id=" + empId;
      try {
         stmt = conn.createStatement(); // Employee emp = new Employee();
         int r = stmt.executeUpdate(sql);
         System.out.println(r + "건 삭제됨.");
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         disconnect();
      }

   }
   
   public int deleteEmployee(String empId) {
		connect();
		String sql = "delete from empl_demo where employee_id = " + empId;
		try {
			stmt = conn.createStatement();
			int r = stmt.executeUpdate(sql);
			System.out.println(r + "건 삭제됨.");
			return Integer.parseInt(empId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return -1;
	}
   

}