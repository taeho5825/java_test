import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ArticleController {
	Scanner sc = new Scanner(System.in);
	ArrayList<Article> articles = new ArrayList<>();
	ArrayList<Reply> replies = new ArrayList<>();
	ArrayList<Like> likes = new ArrayList<>();
	
	
	int lastId = 3; // 가장 마지막에 추가된 게시물의 게시물 번호
	int lastReplyId = 0; // 가장 마지막에 추가된 댓글 번호

	ArticleController() { // 생성자 객체 필요한 값을 초기 세팅
		
		Article article1 = new Article(1, "안녕하세요", "안녕하세요", "홍길동", "20200817", 10);
		Article article2 = new Article(2, "JAVA 프로그래밍", "JAVA 프로그래밍", "익명", "20200817", 20);
		Article article3 = new Article(3, "어렵지 않아요", "어렵지 않아요", "익명", "20200817", 30);
		
		
		articles.add(article1);
		articles.add(article2);
		articles.add(article3);
		
	}
	
	void doCommand(String cmd) {
		if (cmd.equals("help")) {

			System.out.println("add : 게시물 등록");
			System.out.println("list : 게시물 목록");
			System.out.println("update : 게시물 수정");
			System.out.println("delete : 게시물 삭제");
			System.out.println("exit : 프로그램 종료");

		} else if (cmd.equals("add")) {
			
			if(App.loginedMember == null) {
				System.out.println("로그인이 필요한 기능입니다.");
			} else {
				lastId++; // 게시물 번호 자동 증가
				int id = lastId;
	
				System.out.println("제목을 입력해주세요");
				String title = sc.nextLine();
	
				System.out.println("내용을 입력해주세요");
				String body = sc.nextLine();
				
				String datetime = MyUtil.getCurrentDate();
				
				Article article = new Article(id, title, body, App.loginedMember.getUserName(), datetime, 0);
				
				articles.add(article);
			}
			
		} else if (cmd.equals("list")) {
			printArticles(articles);
		} else if (cmd.equals("update")) {
			System.out.println("수정할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				System.out.println("수정할 제목을 입력해주세요.");
				String title = sc.nextLine();
				System.out.println("수정할 내용을 입력해주세요.");
				String body = sc.nextLine();

				Article article = articles.get(targetIndex);
				article.setTitle(title);
				article.setBody(body);
				

			}

		} else if (cmd.equals("delete")) {
			System.out.println("삭제할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				articles.remove(targetIndex);
			}
		} else if (cmd.equals("search")) {
			System.out.println("검색어를 입력해주세요.");
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
			
			System.out.println("상세보기 할 게시물 번호를 입력해주세요.");
			String target = sc.nextLine();
			int targetNo = Integer.parseInt(target);

			int targetIndex = getArticleIndexById(targetNo);

			if (targetIndex == -1) {
				System.out.println("없는 게시물입니다.");
			} else {
				
				int currentHit = articles.get(targetIndex).getHit();
				articles.get(targetIndex).setHit(currentHit + 1);
				printArticle(articles.get(targetIndex));
				detailMenuStart(targetNo);
				
			}
		} else if(cmd.equals("sort")) {
			System.out.println("정렬대상을 선택해주세요. (hit : 조회수,  id : 번호)");
			String target = sc.nextLine();
			System.out.println("정렬방법을 선택해주세요. (asc : 오름차순,  desc : 내림차순)");
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
		// 상세보기 명령어 프로그램 - 댓글, 수정, 삭제, 좋아요
		while(true) {
			System.out.println("상세보기 명령어 입력:");
			String cmd2 = sc.nextLine();
			if(cmd2.equals("back")) {
				break;
			} else if(cmd2.equals("reply")) {
				System.out.println("댓글 내용을 입력해주세요.");
				String rbody = sc.nextLine();
				System.out.println("댓글이 등록되었습니다.");
				// 실제 댓글 데이터 등록
				
				lastReplyId++; // 댓글 번호 자동 증가
				int id = lastReplyId;
				
				Reply reply = new Reply(id, targetNo, rbody, "익명", "20200817");
				replies.add(reply);
				
				printArticle(article);
			} else if(cmd2.equals("update")) {
				
				if(App.loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					if(App.loginedMember.getUserName().equals(article.getWriter())) {
						
						System.out.println("수정할 제목을 입력해주세요.");
						String title = sc.nextLine();
						System.out.println("수정할 내용을 입력해주세요.");
						String body = sc.nextLine();

						article.setTitle(title);
						article.setBody(body);
						printArticle(article);
					} else {
						System.out.println("자신의 게시물만 수정 가능합니다.");
					}
				}
			} else if(cmd2.equals("delete")) {
				
				if(App.loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					if(App.loginedMember.getUserName().equals(article.getWriter())) {
						System.out.println("정말 삭제하시겠습니까?(Y/N)");
						String isDeleteYn = sc.nextLine();
						
						if(isDeleteYn.equals("Y")) {							
							articles.remove(article);
							System.out.println(article.getId() + "번 게시물이 삭제되었습니다.");
						} 
						break;
					} else {
						System.out.println("자신의 게시물만 삭제 가능합니다.");
					}
				}
			} else if(cmd2.equals("like")) {
				if(App.loginedMember == null) {
					System.out.println("로그인 기능이 필요한 기능입니다.");
				} else {
					
					
					System.out.println("좋아요 또는 싫어요를 선택해주세요.( like : 좋아요,  hate : 싫어요)");
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
		int targetIndex = -1; // 찾는게 없을 때 -1

		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).getId() == targetNo) {
				targetIndex = i;
			}
		}

		return targetIndex;
	}
	
	void printArticles(ArrayList<Article> articles) {
		for (int i = 0; i < articles.size(); i++) {
			System.out.println("번호 : " + articles.get(i).getId());
			System.out.println("제목 : " + articles.get(i).getTitle());
			System.out.println("조회수 : " + articles.get(i).getHit());
			System.out.println("=====================");		
		}
	}
	
	void printArticle(Article article) {
		System.out.println("======== " + article.getId() + "번 게시물 상세보기 =======");
		System.out.println("번호   : " + article.getId());
		System.out.println("제목   : " + article.getTitle());
		System.out.println("내용   : " + article.getBody());
		System.out.println("작성자 : " + article.getWriter());
		System.out.println("작성일 : " + article.getRegDate());
		System.out.println("조회수 : " + article.getHit());
		System.out.println("------- 좋아요/싫어요 -------");
		
		System.out.println("좋아요 : " + getCountOfLikesByAritlceId(article.getId()));
		System.out.println("싫어요 : " + getCountOfHatesByAritlceId(article.getId()));
		// 실제 댓글 데이터를 가져와서 출력
		System.out.println("------- 댓글 -------");
		for (int i = 0; i < replies.size(); i++) {
			if(replies.get(i).getParentId() == article.getId()) {
				System.out.println("내용   : " + replies.get(i).getBody());
				System.out.println("작성자 : " + replies.get(i).getWriter());
				System.out.println("작성일 : " + replies.get(i).getRegDate());
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
