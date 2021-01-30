import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ArticleController {
	Scanner sc = new Scanner(System.in);
	ArrayList<Article> articles = new ArrayList<>();
	ArrayList<Reply> replies = new ArrayList<>();
	ArrayList<Like> likes = new ArrayList<>();
	
	
	int lastId = 3; // ���� �������� �߰��� �Խù��� �Խù� ��ȣ
	int lastReplyId = 0; // ���� �������� �߰��� ��� ��ȣ

	ArticleController() { // ������ ��ü �ʿ��� ���� �ʱ� ����
		
		Article article1 = new Article(1, "�ȳ��ϼ���", "�ȳ��ϼ���", "ȫ�浿", "20200817", 10);
		Article article2 = new Article(2, "JAVA ���α׷���", "JAVA ���α׷���", "�͸�", "20200817", 20);
		Article article3 = new Article(3, "����� �ʾƿ�", "����� �ʾƿ�", "�͸�", "20200817", 30);
		
		
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
	}
	
	void doCommand(String cmd) {
		if (cmd.equals("help")) {

			System.out.println("add : �Խù� ���");
			System.out.println("list : �Խù� ���");
			System.out.println("update : �Խù� ����");
			System.out.println("delete : �Խù� ����");
			System.out.println("exit : ���α׷� ����");

		} else if (cmd.equals("add")) {
			
			if(App.loginedMember == null) {
				System.out.println("�α����� �ʿ��� ����Դϴ�.");
			} else {
				lastId++; // �Խù� ��ȣ �ڵ� ����
				int id = lastId;
	
				System.out.println("������ �Է����ּ���");
				String title = sc.nextLine();
	
				System.out.println("������ �Է����ּ���");
				String body = sc.nextLine();
				
				String datetime = MyUtil.getCurrentDate();
				
				Article article = new Article(id, title, body, App.loginedMember.getUserName(), datetime, 0);
				
				articles.add(article);
			}
			
		} else if (cmd.equals("list")) {
			printArticles(articles);
		} else if (cmd.equals("update")) {
			System.out.println("������ �Խù� ��ȣ�� �Է����ּ���.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("���� �Խù��Դϴ�.");
			} else {
				System.out.println("������ ������ �Է����ּ���.");
				String title = sc.nextLine();
				System.out.println("������ ������ �Է����ּ���.");
				String body = sc.nextLine();

				Article article = articles.get(targetIndex);
				article.setTitle(title);
				article.setBody(body);
				

			}

		} else if (cmd.equals("delete")) {
			System.out.println("������ �Խù� ��ȣ�� �Է����ּ���.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("���� �Խù��Դϴ�.");
			} else {
				articles.remove(targetIndex);
			}
		} else if (cmd.equals("search")) {
			System.out.println("�˻�� �Է����ּ���.");
			String keyword = sc.nextLine();
			
			ArrayList<Article> searchedList = new ArrayList<>();
			
			for(int i = 0; i < articles.size(); i++) {
				String title = articles.get(i).getTitle();
				if(title.contains(keyword)) {
					searchedList.add(articles.get(i));
				}
			}
			
			printArticles(searchedList);
			
		} else if (cmd.equals("read")) {
			
			System.out.println("�󼼺��� �� �Խù� ��ȣ�� �Է����ּ���.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("���� �Խù��Դϴ�.");
			} else {
				
				int currentHit = articles.get(targetIndex).getHit();
				articles.get(targetIndex).setHit(currentHit + 1);
				printArticle(articles.get(targetIndex));
				detailMenuStart(targetNo);
				
			}
		} else if(cmd.equals("sort")) {
			System.out.println("���Ĵ���� �������ּ���. (hit : ��ȸ��,  id : ��ȣ)");
			String target = sc.nextLine();
			System.out.println("���Ĺ���� �������ּ���. (asc : ��������,  desc : ��������)");
			String flag = sc.nextLine();
			
			MyComparator com = new MyComparator();
			com.setTarget(target);
			com.setFlag(flag);
			Collections.sort(articles, com);
			printArticles(articles);
		}
	}
	
	void detailMenuStart(int targetNo) {
		int targetIndex = getArticleIndexById(targetNo);
		Article article = articles.get(targetIndex);
		// �󼼺��� ��ɾ� ���α׷� - ���, ����, ����, ���ƿ�
		while(true) {
			System.out.println("�󼼺��� ��ɾ� �Է�:");
			String cmd2 = sc.nextLine();
			if(cmd2.equals("back")) {
				break;
			} else if(cmd2.equals("reply")) {
				System.out.println("��� ������ �Է����ּ���.");
				String rbody = sc.nextLine();
				System.out.println("����� ��ϵǾ����ϴ�.");
				// ���� ��� ������ ���
				
				lastReplyId++; // ��� ��ȣ �ڵ� ����
				int id = lastReplyId;
				
				Reply reply = new Reply(id, targetNo, rbody, "�͸�", "20200817");
				replies.add(reply);
				
				printArticle(article);
			} else if(cmd2.equals("update")) {
				
				if(App.loginedMember == null) {
					System.out.println("�α��� ����� �ʿ��� ����Դϴ�.");
				} else {
					if(App.loginedMember.getUserName().equals(article.getWriter())) {
						
						System.out.println("������ ������ �Է����ּ���.");
						String title = sc.nextLine();
						System.out.println("������ ������ �Է����ּ���.");
						String body = sc.nextLine();

						article.setTitle(title);
						article.setBody(body);
						printArticle(article);
					} else {
						System.out.println("�ڽ��� �Խù��� ���� �����մϴ�.");
					}
				}
			} else if(cmd2.equals("delete")) {
				
				if(App.loginedMember == null) {
					System.out.println("�α��� ����� �ʿ��� ����Դϴ�.");
				} else {
					if(App.loginedMember.getUserName().equals(article.getWriter())) {
						System.out.println("���� �����Ͻðڽ��ϱ�?(Y/N)");
						String isDeleteYn = sc.nextLine();
						
						if(isDeleteYn.equals("Y")) {							
							articles.remove(article);
							System.out.println(article.getId() + "�� �Խù��� �����Ǿ����ϴ�.");
						} 
						break;
					} else {
						System.out.println("�ڽ��� �Խù��� ���� �����մϴ�.");
					}
				}
			} else if(cmd2.equals("like")) {
				if(App.loginedMember == null) {
					System.out.println("�α��� ����� �ʿ��� ����Դϴ�.");
				} else {
					
					
					System.out.println("���ƿ� �Ǵ� �Ⱦ�並 �������ּ���.( like : ���ƿ�,  hate : �Ⱦ��)");
					String likeOrHate = sc.nextLine();
					
					Like target = getLikeByArticleIdAndUserId(article.getId(), App.loginedMember.getLoginId());
					int flag = -1;
					
					if(likeOrHate.equals("like")) {
						flag = 0;
					} else {
						flag = 1;
					}
					
					if(target == null) {
						Like like = new Like();
						like.setArticleId(article.getId());
						like.setUserId(App.loginedMember.getLoginId());
						like.setFlag(flag);
						likes.add(like);
					} else {
						if(target.getFlag() == flag) {
							likes.remove(target);
						} else {
							target.setFlag(flag);
						}
					}
					printArticle(article);
				}
			} 
		}
		
	}
	
	int getArticleIndexById(int targetNo) {
		int targetIndex = -1; // ã�°� ���� �� -1

		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getId() == targetNo) {
				targetIndex = i;
			}
		}

		return targetIndex;
	}
	
	void printArticles(ArrayList<Article> articles) {
		for (int i = 0; i < articles.size(); i++) {
			System.out.println("��ȣ : " + articles.get(i).getId());
			System.out.println("���� : " + articles.get(i).getTitle());
			System.out.println("��ȸ�� : " + articles.get(i).getHit());
			System.out.println("=====================");		
		}
	}
	
	void printArticle(Article article) {
		System.out.println("======== " + article.getId() + "�� �Խù� �󼼺��� =======");
		System.out.println("��ȣ   : " + article.getId());
		System.out.println("����   : " + article.getTitle());
		System.out.println("����   : " + article.getBody());
		System.out.println("�ۼ��� : " + article.getWriter());
		System.out.println("�ۼ��� : " + article.getRegDate());
		System.out.println("��ȸ�� : " + article.getHit());
		System.out.println("------- ���ƿ�/�Ⱦ�� -------");
		
		System.out.println("���ƿ� : " + getCountOfLikesByAritlceId(article.getId()));
		System.out.println("�Ⱦ�� : " + getCountOfHatesByAritlceId(article.getId()));
		// ���� ��� �����͸� �����ͼ� ���
		System.out.println("------- ��� -------");
		for (int i = 0; i < replies.size(); i++) {
			if(replies.get(i).getParentId() == article.getId()) {
				System.out.println("����   : " + replies.get(i).getBody());
				System.out.println("�ۼ��� : " + replies.get(i).getWriter());
				System.out.println("�ۼ��� : " + replies.get(i).getRegDate());
				System.out.println("--------------------");
			}
		}		
	}
	
	ArrayList<Like> getLikesByAritlceId(int articleId) {
		
		ArrayList<Like> likeListByArticleId = new ArrayList<Like>();
		
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getArticleId() == articleId) {
				likeListByArticleId.add(likes.get(i));
			}
		}
		
		return likeListByArticleId;
		
	}
	
	int getCountOfLikesByAritlceId(int articleId) {
		int count = 0;
		ArrayList<Like> likes = getLikesByAritlceId(articleId);
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getFlag() == 0) {
				count++;
			}
		}
		
		return count;
	}
	
	int getCountOfHatesByAritlceId(int articleId) {
		int countOfLikes = getCountOfLikesByAritlceId(articleId);
		int countOfAll = getLikesByAritlceId(articleId).size();
		
		return countOfAll - countOfLikes; 
		
	}
	
	Like getLikeByArticleIdAndUserId(int articleId, String userId) {
		Like target = null;
		ArrayList<Like> likes = getLikesByAritlceId(articleId);
		for(int i = 0; i < likes.size(); i++) {
			if(likes.get(i).getUserId().equals(userId)) {
				target = likes.get(i); 
			}
		}
		
		return target;
	}
}
