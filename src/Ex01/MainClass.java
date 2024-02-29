package Ex01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

class DB_con{
	private Connection con;//DB와 연결하는 역할 Connection con 변수 생성
	private PreparedStatement ps;//DB로 명령어를 전송하는 역할
	private ResultSet rs; // 모든 데이터를 저장하는 객체라고 생각하시면 돼요
	public DB_con() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("오라클 기능 사용가능(드라이브 로드)");

			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "c##abcd";
			String pwd = "1234";//oracle url -> 위치 ,oracle id,pwd 연결

			con = DriverManager.getConnection(url,id,pwd);//오라클에 연동
			System.out.println("db연결 성공 : "+ con);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public ArrayList<MemberDTO> select() {
		String sql = "select * from member_test";
		ArrayList<MemberDTO> arr = new ArrayList<>();// 정보가 많아 arraylist에 저장

		try {
			ps = con.prepareStatement(sql); //오라클 전송 객채 생성
			rs = ps.executeQuery(); // 명령어를 rs라는 객체에 저장, 명령어를 전송


			//System.out.println(rs.next());
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				arr.add(dto); // 모든 정보가 arraylist로 저장
				/*
			System.out.println(rs.getString("id"));
			System.out.println(rs.getString("pwd"));
			System.out.println(rs.getString("name"));
			System.out.println(rs.getString("age"));
			System.out.println("------------------");*/
			}


			/*	System.out.println(rs.next());
			System.out.println(rs.getString("id"));
			System.out.println(rs.getString("pwd"));
			System.out.println(rs.getString("name"));
			System.out.println(rs.getString("age"));*/
		}catch(Exception e) {
			e.printStackTrace();
		}
		return arr;

	}
	public MemberDTO selectOne(String userId) {
		String sql = "select * from member_test where id ='"+ userId+"'";
		//System.out.println(sql);
		MemberDTO dto = null;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO(); //if문이 실행되면 객체값을 가지게 되고 실행되지 않으면 null값을 가짐 = 존재 하는 아이디면 객체값을 가지고 존재하지 않으면 null
				dto.setAge(rs.getInt("Age"));
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("pwd"));
				dto.setName(rs.getString("name"));
				/*System.out.println(rs.getString("id"));
				System.out.println(rs.getString("pwd"));
				System.out.println(rs.getString("name"));
				System.out.println(rs.getInt("age"));*/
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto; 
	}
	public int delete(String delId) {
		
		String sql = "delete from member_test where id= ?"; // <-- ?는 나중에 값을 넣겠다는 의미
		
		int result = 0;
		
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, sql); // setString(1,sql) 여기서 1은 위에 ?위에 문자열
			result = ps.executeUpdate();//update는 select를 제외한 나머지에 사용
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return result;
	}
	public int insert(MemberDTO dto) {
		String sql ="insert into member_test values(?,?,?,?)";
		int res =0;
		
		try {
			ps=con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getName());
			ps.setInt(4, dto.getAge());
			ps.executeUpdate();
			res = ps.executeUpdate();
		} catch (Exception e) {
				
			//e.printStackTrace();
		System.out.println("존재하는 ididididid");
		}	return res;
		
		
	}
}
public class MainClass {
	public static void main(String[] args) {
		DB_con db = new DB_con();
		Scanner input = new Scanner(System.in);
		int num;
		while(true) {
			System.out.println("1.모든 목록 보기");
			System.out.println("2.특정 사용자 보기");
			System.out.println("3.데이터 추가");
			System.out.println("4.데이터 삭제");
			System.out.println(">>> :");
			num = input.nextInt();
			switch(num) {
			case 1:	
				ArrayList<MemberDTO>arr = db.select();
				System.out.println("---main---");
				for(MemberDTO dto : arr) {
					System.out.println("id : " + dto.getId());
					System.out.println("pwd : " + dto.getPwd());
					System.out.println("name : " + dto.getName());
					System.out.println("age : " + dto.getAge());
					System.out.println("-----------------------");
				}
				break;
			case 2:
				System.out.println("검색 id 입력");
				String userId = input.next();
				MemberDTO dto = db.selectOne(userId);
				//System.out.println("dto: " + dto);
				if(dto == null) {
					System.out.println("존재하지 않는 id입니다.");
				}else {
					System.out.println("----검색 결과 -----");
					System.out.println("id : "+ dto.getId());
					System.out.println("pwd : "+ dto.getPwd());
					System.out.println("name : "+ dto.getName());
					System.out.println("age : "+ dto.getAge());
				}
				break;
				
			case 3:
				MemberDTO d = new MemberDTO();
				while(true) {
				System.out.println("가입할 id 입력");
				d.setId(input.next());
				MemberDTO dd = db.selectOne(d.getId());
				if(dd == null)
					break;
				System.out.println("존재하는 id .. 다시 입력");
				}
				System.out.println("가입할 pwd 입력");
				d.setPwd(input.next());
				System.out.println("가입할 name 입력");
				d.setName(input.next());
				System.out.println("가입할 age 입력");
				d.setAge(input.nextInt());
				int res = db.insert(d);
				if(res ==1)
					System.out.println("회원 가입 성공");
				else
					System.out.println("존재하는 id는 안됌");
				break;
			case 4:
				System.out.println("삭제 id 입력");
				String delId = input.next();
				db.delete(delId);
				int re = db.delete(delId);;
				if(re ==1) {
					System.out.println("삭제 성공!!!");
					
				}else {
					System.out.println("존재하지 않는 아이디 삭제 불가!");
				}
				break;
			}
		}

	}
}