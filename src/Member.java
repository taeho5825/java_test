
public class Member {
	private String loginId;
	private String loginPw;
	private String userName;
	
	public Member() {
		
	}
	
	public Member(String loginId, String loginPw, String userName) {
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginPw() {
		return loginPw;
	}
	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
