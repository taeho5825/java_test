import java.util.Scanner;

public class App {
	
	ArticleController ac = new ArticleController();
	MemberController mc = new MemberController();	
	static Member loginedMember = null; // �����ڿ�
	
	void start() {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.println("����� �������ּ���. (article : �Խù� ���,   member : ȸ�� ���,   exit : ����)");
			String module = sc.nextLine();
			System.out.println("��ɾ �Է����ּ���");
			String s = sc.nextLine();
			
			if(module.equals("article")) {
				ac.doCommand(s);
			} else if(module.equals("member")){
				mc.doCommand(s);
			} else if(s.equals("exit")) {
				System.out.println("���α׷� ����!");
				break;
			} else {
				System.out.println("�ùٸ� ��ɾ �ƴմϴ�.");
			}
		} 
	}	
}
