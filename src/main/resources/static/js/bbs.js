'use strict';
let url = location.href;
let path = location.pathname;
let uri = url.replace(path, '');


$(function () {
  // 初期描画時の処理
  index();
  function index() {
    $.ajax({
      url: uri + '/bbs',
      type: 'get',
      dataType: 'json', // ←省略すると文字列のjsonを取得する
    }).done(function (json_articles_data) {
      console.dir('JSONデータ：' + JSON.stringify(json_articles_data));
      console.log('JSONデータ：' + json_articles_data)

      // dataType: 'json'を省略すると以下のように文字列のJSONからJavaScriptのObjectに変換する必要あり
      // var articles = JSON.parse(json_articles_data);

      let article_list_html = '';
      $.each(json_articles_data,    // json_articles_dataをforeachで回す構文
        function (index, article) { // index：0から始まる番号、article:1つの記事オブジェクト
          console.log('ID：' + article.id + ' 名前：' + article.name + ' 内容：' + article.content);

          article_list_html += '<div id="article' + article.id + '">';
          article_list_html += 'ID：<a id="load_article"  value="' + article.id + '" href="javascript:void(0);">' + article.id + '</a><br>';
          article_list_html += '投稿者名：<span>' + article.name + '</span><br>';
          article_list_html += '投稿内容：<pre><span>' + article.content + '</span></pre>';
          article_list_html += '<button type="button" id="delete_article" value="' + article.id + '">記事削除</button>';
          article_list_html += '</div>';
          article_list_html += '<hr>';

        }
      );
      // htmlファイルに上記で作成したhtmlタグを挿入
      $('#spa').html(article_list_html);
    }); // end .ajax()
  } // end index function



  // 記事投稿時の処理
  $(document).on('click', '#post_article', function () {

    // リクエストパラメータにJSONを指定する
    var json_data_object = {
      name: $('#name').val(),
      content: $('#content').val()
    };

    console.dir('JSONデータ：' + JSON.stringify(json_data_object));

    $.ajax({
      url: uri + '/bbs/postarticle',
      type: 'post',
      // dataType: 'json', // 記事情報を送るだけなので受け取り用のdataTypeは指定しない
      data: JSON.stringify(json_data_object), // リクエストパラメータにJSONを文字列として指定する
      contentType: 'application/JSON', // JSONを送るときのContent-Typeを指定
      async: true   // 非同期で処理を行う
    }).done(function () {

      index(); // index()関数を読んで記事一覧を再表示
      $('#name').val('');    // 名前入力欄を空白にする
      $('#content').val(''); // 内容入力欄を空白にする

      // history.pushState(null, null, '#post'); // URL書き換え
      location.hash = 'post';

    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
      alert('エラーが発生しました！');
      console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
      console.log('textStatus     : ' + textStatus);
      console.log('errorThrown    : ' + errorThrown.message);
    });
  }); // end 記事投稿時の処理


  // 記事１件検索時の処理
  $(document).on('click', '#load_article', function () {
    var articleId = $(this).attr("value");
    console.log(articleId);

    $.ajax({
      url: uri + '/bbs/loadarticle/' + articleId,
      type: 'get',
      async: true,   // 非同期で処理を行う
      dataType: 'json', // ←省略すると文字列のjsonを取得する
    }).done(function (json_article_data) {
      console.dir('JSONデータ：' + JSON.stringify(json_article_data));
      console.log('JSONデータ：' + json_article_data)

      let article_html = '';
      article_html += '<div id="article' + json_article_data.id + '">';
      article_html += 'ID：<a id="load_article"  value="' + json_article_data.id + '" href="javascript:void(0);">' + json_article_data.id + '</a><br>';
      article_html += '投稿者名：<span>' + json_article_data.name + '</span><br>';
      article_html += '投稿内容：<pre><span>' + json_article_data.content + '</span></pre>';
      article_html += '<button type="button" id="show_article_list" >一覧に戻る</button>';
      article_html += '</div>';

      // htmlファイルに上記で作成したhtmlタグを挿入
      $('#spa').html(article_html);
    }); // end .ajax()

  }); // end 記事１件検索時の処理

  // 記事一覧表示 詳細画面から一覧に戻る際に使用
  $(document).on('click', '#show_article_list', function () {
    index();
  }); // end 記事一覧表示
  
  // 記事削除時の処理
  $(document).on('click', '#delete_article', function () {
    var articleId = $(this).val();
    console.log(articleId);

    $.ajax({
      url: uri + '/bbs/deletearticle/' + articleId,
      type: 'delete',
      async: true,   // 非同期で処理を行う
    }).done(function () {

      index(); // index()関数を読んで記事一覧を再表示

      // history.pushState(null, null, '#delete'); // URL書き換え
      location.hash = 'delete';

    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
      alert('エラーが発生しました！');
      console.log('XMLHttpRequest : ' + XMLHttpRequest.status);
      console.log('textStatus     : ' + textStatus);
      console.log('errorThrown    : ' + errorThrown.message);
    });

  }); // end 記事削除時の処理


  // $(window).on("hashchange", () => {
  //   // ハッシュ文字列を取得
  //   const page = location.hash.slice(1);
  //   console.log(page);
  //   $.get(`/html/${page}.html`).done((data) => {
  //     $('.spa').html(data);
  //   }).fail(() => {
  //     error();
  //   });
  //   // const in_url = $.inArray(page, url_list);
  //   // if (in_url !== -1) {
  //   //   $.get(`/html/${page}.html`).done((data) => {
  //   //     $('.spa').html(data);
  //   //   }).fail(() => {
  //   //     error();
  //   //   });
  //   // }
  // });



}); // end ready function

