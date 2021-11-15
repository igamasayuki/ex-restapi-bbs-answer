package jp.co.runy.exrestapibbsanswer.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.exrestapibbsanswer.domain.Article;
import jp.co.runy.exrestapibbsanswer.domain.Comment;

/**
 * 記事処理関連のレポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ArticleRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * articlesとcommentsテーブルを結合したものからarticleリストを作成する.
	 * articleオブジェクト内にはcommentリストを格納する。
	 */
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		// 記事一覧が入るarticleListを生成
		List<Article> articleList = new LinkedList<Article>();
		List<Comment> commentList = null;
		
		// 前の行の記事IDを退避しておく変数
		long beforeArticleId = 0;
		
		while (rs.next()) {
			// 現在検索されている記事IDを退避
			int nowArticleId = rs.getInt("id");
			
			// 現在の記事IDと前の記事IDが違う場合はArticleオブジェクトを生成
			if (nowArticleId != beforeArticleId) {
				Article article = new Article();
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				// 空のコメントリストを作成しArticleオブジェクトにセットしておく
				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				// コメントがセットされていない状態のArticleオブジェクトをarticleListオブジェクトにadd
				articleList.add(article);
			}
			
			// 記事だけあってコメントがない場合はCommentオブジェクトは作らない
			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getLong("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				// コメントをarticleオブジェクト内にセットされているcommentListに直接addしている(参照型なのでこのようなことができる)
				commentList.add(comment);
			}
			
			// 現在の記事IDを前の記事IDを入れる退避IDに格納
			beforeArticleId = nowArticleId;
		}
		return articleList;
	};

	/**
	 * 記事一覧を取得します.記事に含まれているコメント一覧も同時に取得します.
	 * 
	 * @return コメントを含んだ記事一覧情報
	 */
	public List<Article> findAll() {
		String sql = "SELECT a.id, a.name, a.content, com.id com_id, com.name com_name, com.content com_content,com.article_id "
				+ "FROM articles a LEFT JOIN comments com ON a.id = com.article_id ORDER BY a.id DESC, com.id;";
		List<Article> articleList = namedParameterJdbcTemplate.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;
	}

	/**
	 * 記事をインサートします.
	 * 
	 * @param article
	 *            記事
	 * @return 記事
	 */
	public Article insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles(name, content) VALUES(:name, :content)";
		namedParameterJdbcTemplate.update(sql, param);
		return article;
	}

	/**
	 * 記事をDBから削除する. <br>
	 * コメントも同時に削除する。<br>
	 * 参考URL http://aoyagikouhei.blog8.fc2.com/blog-entry-183.html
	 * 
	 * @param id
	 *            削除したい記事ID
	 */
	public void deleteById(int articleId) {
		SqlParameterSource sqlparam = new MapSqlParameterSource().addValue("id", articleId);
		String sql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id)"
				+ "DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted)";

		namedParameterJdbcTemplate.update(sql, sqlparam);
	}
	
//	/**
//	 * 記事を削除するメソッド.
//	 * 
//	 * @param id 削除したい記事のＩＤ
//	 */
//	public void deleteById(int id) {
//		String deleteSql = "DELETE FROM articles WHERE id=:id";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
//
//		namedParameterJdbcTemplate.update(deleteSql, param);
//	}

//	/**
//	 * ＤＢから参照した記事の情報をドメインにセットするRowMappaer.
//	 */
//	private static final RowMapper<Article> ARTICLE_RESULT_SET_EXTRACTOR = (rs, i) -> {
//		Integer id = rs.getInt("id");
//		String name = rs.getString("name");
//		String content = rs.getString("content");
//		return new Article(id, name, content);
//	};
//
//	@Autowired
//	private NamedParameterJdbcTemplate jdbcTemplate;
//
//	/**
//	 * 記事を全件表示するメソッド.
//	 * 
//	 * @return 記事全件
//	 */
//	public List<Article> findAll() {
//		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
//		List<Article> articleList = jdbcTemplate.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);
//		return articleList;
//	}
//
//	/**
//	 * 記事を1件表示するメソッド.
//	 * 
//	 * @return 記事１件
//	 */
//	public Article load(int id) {
//		String sql = "SELECT id,name,content FROM articles WHERE id=:id";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
//		Article article = jdbcTemplate.queryForObject(sql, param, ARTICLE_RESULT_SET_EXTRACTOR);
//		return article;
//	}
//
//	/**
//	 * 記事を追加するメソッド.
//	 * 
//	 * @param article 追加する記事オブジェクト
//	 */
//	public void insert(Article article) {
//		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
//
//		String insertSql = "INSERT INTO articles (name,content) VALUES(:name,:content)";
//
//		jdbcTemplate.update(insertSql, param);
//	}
//


}
