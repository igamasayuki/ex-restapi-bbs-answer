package jp.co.runy.exrestapibbsanswer.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.runy.exrestapibbsanswer.domain.Article;
import jp.co.runy.exrestapibbsanswer.service.ArticleService;

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
@RequestMapping("/bbs")
public class BbsController {

	@Autowired
	private ArticleService articleService;

	/**
	 * 記事一覧を返すメソッド.
	 * 
	 * @return 記事一覧
	 */
	@RequestMapping
	public String index() {

		List<Article> articleList = articleService.findAll();

		// 取得した記事情報をJSON文字列に変換し返却
		return getJsonData(articleList);
	}

	/**
	 * 記事を追加するメソッド.
	 * 
	 * @param article 記事
	 * @return 記事一覧
	 */
	@RequestMapping("/postarticle")
	public void postArticle(@RequestBody Article article) {
		articleService.save(article);
	}

		/**
	 * 記事を追加するメソッド.
	 * 
	 * @param article 記事
	 * @return 記事一覧
	 */
	@RequestMapping("/loadarticle/{id}")
	public Article loadArticle(@PathVariable("id") int id) {
		return articleService.load(id);
	}

	/**
	 * 記事を削除するメソッド.
	 * 
	 * @param id 削除する記事のＩＤ
	 * @return 記事一覧
	 */
	@RequestMapping("/deletearticle/{id}")
	public void deleteArticle(@PathVariable("id") int id) {
		articleService.delete(id);
	}

	/**
	 * 引数のオブジェクトをJSON文字列に変換する
	 * 
	 * @param data オブジェクトのデータ
	 * @return 変換後JSON文字列
	 */
	private String getJsonData(Object data) {
		String retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			System.err.println(e);
		}
		return retVal;
	}
}
