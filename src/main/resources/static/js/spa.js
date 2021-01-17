;(function(){
  'use strict';
  
  //URLの一覧を配列で用意
  const url_list = [
    'page1',
    'page2',
    'page3',
  ];
  
  function init(){
    $.get('/html/page1.html').done((data) => {
        $('.spa').html(data);
    }).fail(() => {
        error();
    });
  }
  
  function hashchange(){
    const page = location.hash.slice(1);
    console.log(page);
    const in_url = $.inArray(page, url_list);
    console.log(in_url);
    if(in_url !== -1){
      $.get(`/html/${page}.html`).done((data) => {
        $('.spa').html(data);
      }).fail(() => {
        error();
      });
    }
  }
  
  function error(){
    $('.spa').html('読み込みエラー');
  }
  
  $(window).on("hashchange", () => {
    hashchange();
    //console.log('ハッシュが変わりましたsample');
  });
  
  $(function(){
    init();
  });
  
  })();