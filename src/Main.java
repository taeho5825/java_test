import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		App app = new App();
		app.start();
		
		String url = "jdbc:mysql://192.168.0.1:3306/t1?serverTimezone = UTC";
		String id = "root";
		String pw = "";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, id, pw);
		
		String sql = "SELECT title, `body` FROM article";
		
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		
		String body = rs.getString("title");
		System.out.println(body);
	}
}
