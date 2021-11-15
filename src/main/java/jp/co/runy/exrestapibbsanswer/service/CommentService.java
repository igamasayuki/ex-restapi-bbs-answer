package jp.co.runy.exrestapibbsanswer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.runy.exrestapibbsanswer.domain.Comment;
import jp.co.runy.exrestapibbsanswer.repository.CommentRepository;

/**
 * コメント関連サービス.
 * 
 * @author igamasayuki
 *
 */
@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	/**
	 * コメントを登録します.
	 * 
	 * @param comment
	 *            コメント情報
	 * @return 登録したコメント情報
	 */
	public void save(Comment comment) {
		commentRepository.insert(comment);
	}
}
