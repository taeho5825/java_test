
public class Reply {
	private int id;
	private int parentId; // 원글 번호
	private String body;
	private String writer;
	private String regDate;
	
	public Reply() {
		
	}
	
	public Reply(int id, int parentId, String body, String writer, String regDate) {
		this.id = id;
		this.parentId = parentId;
		this.body = body;
		this.writer = writer;
		this.regDate = regDate;
	}
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
