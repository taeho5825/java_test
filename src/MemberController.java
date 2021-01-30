import java.util.ArrayList;
import java.util.Scanner;

public class MemberController {
	Scanner sc = new Scanner(System.in);
	ArrayList<Member> members = new ArrayList<>();
	
	MemberController() { // ������ ��ü �ʿ��� ���� �ʱ� ����
		
		Member member1 = new Member("hong123", "h1234", "ȫ�浿");
		Member member2 = new Member("lee123", "l1234", "�̼���");
		Member member3 = new Member("lim123", "l4321", "�Ӳ���");
		
		members.add(member1);
		members.add(member2);
		members.add(member3);
		
	}
	
	void doCommand(String cmd) {
		if(cmd.equals("signup")) {
			Member member = new Member();

			System.out.println("���̵� �Է����ּ���");
			member.setLoginId(sc.nextLine());

			System.out.println("��й�ȣ�� �Է����ּ���");
			member.setLoginPw(sc.nextLine());
			
			System.out.println("�̸��� �Է����ּ���");
			member.setUserName(sc.nextLine());

			members.add(member);
			
		} else if(cmd.equals("login")) {
			System.out.println("���̵� �Է����ּ���");
			String loginId = sc.nextLine();
			
			System.out.println("��й�ȣ�� �Է����ּ���");
			String loginPw = sc.nextLine();
			
			
			int targetIndex = getMemberIndexById(loginId);

			if (targetIndex == -1) {
				System.out.println("�α��� ����");
			} else {
				
				Member member = members.get(targetIndex);
				if(loginPw.equals(member.getLoginPw())) {
					System.out.println(member.getUserName() + "�� �ݰ����ϴ�!!");
					
					App.loginedMember = member;
					//loginedMember = member;
				} else {
					System.out.println("�α��� ����");
				}
			}
		} else if(cmd.equals("logout")) {
			if(App.loginedMember == null) {
				System.out.println("�α����� �ʿ��� ����Դϴ�.");
			} else {
				System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
				App.loginedMember = null;
			}
		}
		
	}
	
	int getMemberIndexById(String loginId) {
		int targetIndex = -1; // ã�°� ���� �� -1

		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getLoginId().equals(loginId)) {
				targetIndex = i;
			}
		}
		
		return targetIndex;
	}
}
