package accountData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dbutil.ConnectionProvider;

public class SignUp {
	public static boolean verifyUserId (String userId) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{1,30}");
		Matcher m = p.matcher(userId);
		
		if(m.matches()) {
			return true; // 아이디 사용 가능 양식이면 true
		}
		return false; // 아니면 false
	}
	
	public static boolean verifyUserNickName (String userNickName) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{1,30}");
		Matcher m = p.matcher(userNickName);
		
		if(m.matches()) {
			return true; // 닉네임 사용 가능 양식이면 true
		}
		return false; // 아니면 false
	}

	public static boolean verifyUserPw (String userPw) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]{1,30}");
		Matcher m = p.matcher(userPw);
		
		if(m.matches()) {
			return true; // 비밀번호 사용 가능 양식이면 true
		}
		return false; // 아니면 false
	}
	
	public static boolean useridDuplicationCheck (String userId) {
		String sql = "SELECT * FROM team4.user WHERE userId = ?";
		try(Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					return false; // 아이디 중복이면 false 반환
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true; // 중복 아니면 true 반환
	}
	
	public static boolean userNickNameDuplicationCheck (String userNickName) {
		String sql = "SELECT * FROM team4.user WHERE userNickname = ?";
		try(Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userNickName);
			try (ResultSet rs = stmt.executeQuery()) {
				if(rs.next()) {
					return false; // 닉네임 중복이면 false 반환
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true; // 중복 아니면 true 반환
	}
	
	public static void signUp(String userId, String userPw, String nick) { // 회원가입
		String sql = "INSERT INTO user (userId, userPw, userNickname) values (?, ?, ?)";
		try (Connection conn = ConnectionProvider.makeConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, userId);
			stmt.setString(2, userPw);
			stmt.setString(3, nick);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}