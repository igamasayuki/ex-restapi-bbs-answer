package jp.co.runy.exrestapibbsanswer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.runy.exrestapibbsanswer.domain.Article;
import jp.co.runy.exrestapibbsanswer.repository.ArticleRepository;

/**
 * 記事関連サービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	/**
	 * 投稿一覧を取得します.
	 * 
	 * @return 記事一覧情報
	 */
	public List<Article> findAll() {
		return articleRepository.findAll();
	}

		/**
	 * 記事を１件検索します.
	 * 
	 * @param id　検索したい記事ID
	 */
	public Article load(int id) {
		return articleRepository.load(id);
	}

	/**
	 * 記事を登録します.
	 * 
	 * @param article
	 *            記事情報
	 * @return 登録した記事情報
	 */
	public void save(Article article) {
		articleRepository.insert(article);
	}

	/**
	 * 記事を削除します.
	 * 
	 * @param id　削除したい記事ID
	 */
	public void delete(int id) {
		articleRepository.deleteById(id);
	}
	
}
