import java.util.Comparator;

public class MyComparator implements Comparator<Article> {
	private String target;
	private String flag;
	@Override
	public int compare(Article a1, Article a2) {
		int c1 = 0;
		int c2 = 0;
		if(target.equals("hit")) {
			c1 = a1.getHit();
			c2 = a2.getHit();
		} else if(target.equals("id")) {
			c1 = a1.getId();
			c2 = a2.getId();
		}
		if(flag.equals("asc")) {			
			if(c1 > c2) { // 오름차순
				return 1; 
			}
		} else {
			if(c1 < c2) { // 오름차순
				return 1; 
			}
		}
		return -1;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
