package jp.co.runy.exrestapibbsanswer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.exrestapibbsanswer.domain.Comment;

/**
 * commentsテーブル操作用のリポジトリクラス.
 * 
 * @author igamasayuki
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * コメントを新規作成します.
	 * 
	 * @param comment
	 *            コメント
	 * @return 作成されたコメント情報
	 */
	public Comment insert(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		if (comment.getId() != null) {
			throw new NullPointerException();
		}
		jdbcTemplate.update("INSERT INTO comments(name, content, article_id) VALUES(:name, :content, :articleId)",
				param);
		return comment;
	}
}
