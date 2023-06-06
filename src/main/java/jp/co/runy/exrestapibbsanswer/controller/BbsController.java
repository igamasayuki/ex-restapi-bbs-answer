package jp.co.runy.exrestapibbsanswer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.runy.exrestapibbsanswer.domain.Article;
import jp.co.runy.exrestapibbsanswer.domain.Comment;
import jp.co.runy.exrestapibbsanswer.service.ArticleService;
import jp.co.runy.exrestapibbsanswer.service.CommentService;

/**
 * 掲示板処理関連のコントローラークラス. REST APIについて https://wp.tech-style.info/archives/683
 * 
 * HTMLファイルからPOSTでJSONデータを送信する
 * https://qiita.com/kidatti/items/21cc5c5154dbbb1aa27f
 * 
 * SpringBoot JSONの送信と受信のサンプル https://itsakura.com/java-springboot-json
 * 
 * @author igamasayuki
 *
 */
@RestController
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
@RequestMapping("/bbs")
public class BbsController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;

	/**
	 * 記事一覧を返すメソッド.
	 * 
	 * @return 記事一覧
	 */
	@GetMapping("/articles")
	public Map<String,Object> articles() {

		Map<String,Object> responseMap = new HashMap<>();
		List<Article> articles = articleService.findAll();
		
		responseMap.put("articles", articles);
		
		return responseMap;

	}

	/**
	 * 記事を追加するメソッド.
	 * 
	 * @param article 記事
	 * @return 記事一覧
	 */
	@PostMapping("/article")
	public void postArticle(@RequestBody Article article) {
		articleService.save(article);
	}

//	/**
//	 * 記事を１件返すメソッド.
//	 * 
//	 * @param id 記事ID
//	 * @return 記事
//	 */
//	@RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
//	public Article loadArticle(@PathVariable("id") int id) {
//		return articleService.load(id);
//	}

	/**
	 * コメントを投稿します.
	 * 
	 * @param form
	 *            フォーム
	 * @param result
	 *            リザルト
	 * @param model
	 *            モデル
	 * @return 掲示板画面
	 */
	@PostMapping("/comment")
	public void postcomment(@RequestBody Comment comment) {
		commentService.save(comment);
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param form
	 *            記事フォーム
	 * @return 記事登録画面
	 */
	@DeleteMapping("/article/{articleId}")
	public void deletearticle(@PathVariable("articleId") int articleId) {
		articleService.delete(articleId);
	}
	
	
//	/**
//	 * ファイルを投稿するメソッド.
//	 * 
//	 * @param article 記事
//	 * @return 記事一覧
//	 */
//	@RequestMapping(value = "/postfile", method = RequestMethod.POST)
//	public void postFile(String fileName, MultipartFile file) {
//		// articleService.save(article);
//		System.out.println("ファイル名取得");
//		System.out.println(fileName);
//		System.out.println(file.getOriginalFilename());
//	}

}
