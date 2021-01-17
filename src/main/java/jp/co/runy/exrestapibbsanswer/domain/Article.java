package jp.co.runy.exrestapibbsanswer.domain;
/**
 * 記事を表すエンティティ.
 * 
 * @author igamasayuki
 */
public class Article{
  
  /** id */
  public Integer id;

  /** 名前 */
  public String name;

  /** 内容 */
  public String content;

  public Article() {
  }

  public Article(Integer id, String name, String content) {
    this.id = id;
    this.name = name;
    this.content = content;
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }
 

  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", name='" + getName() + "'" +
      ", content='" + getContent() + "'" +
      "}";
  }


}