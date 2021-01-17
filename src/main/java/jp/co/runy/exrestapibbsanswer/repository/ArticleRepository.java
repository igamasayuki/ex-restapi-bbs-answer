package jp.co.runy.exrestapibbsanswer.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.exrestapibbsanswer.domain.Article;

/**
 * 記事処理関連のレポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ArticleRepository {

	/**
	 * ＤＢから参照した記事の情報をドメインにセットするRowMappaer.
	 */
	private static final RowMapper<Article> ARTICLE_RESULT_SET_EXTRACTOR = (rs, i) -> {
		Integer id = rs.getInt("id");
		String name = rs.getString("name");
		String content = rs.getString("content");
		return new Article(id, name, content);
	};

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 記事を全件表示するメソッド.
	 * 
	 * @return 記事全件
	 */
	public List<Article> findAll() {
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
		List<Article> articleList = jdbcTemplate.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
		return articleList;
	}

	/**
	 * 記事を1件表示するメソッド.
	 * 
	 * @return 記事１件
	 */
	public Article load(int id) {
		String sql = "SELECT id,name,content FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Article article = jdbcTemplate.queryForObject(sql, param, ARTICLE_RESULT_SET_EXTRACTOR);
		return article;
	}

	/**
	 * 記事を追加するメソッド.
	 * 
	 * @param article 追加する記事オブジェクト
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);

		String insertSql = "INSERT INTO articles (name,content) VALUES(:name,:content)";

		jdbcTemplate.update(insertSql, param);
	}

	/**
	 * 記事を削除するメソッド.
	 * 
	 * @param id 削除したい記事のＩＤ
	 */
	public void deleteById(int id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

		jdbcTemplate.update(deleteSql, param);
	}

}
