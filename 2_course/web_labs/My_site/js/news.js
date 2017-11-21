window.addEventListener('load', function() {

    function updateOnlineStatus(event) {
        if(isOnline()){
            ReadOflineNews();
        }
    }
    window.addEventListener('online',  updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
});

function isOnline() {
    return window.navigator.onLine;

}


function ReadOflineNews() {
  if (useLocalStorage) {
    len = localStorage.length + 1;

    for (var k = 1; k < len; k++) {
      news = JSON.parse(localStorage.getItem(k));

      if (news[0].name != null) {
        $("#news_list").prepend("<li class=\"list-group-item col-sm-4 thumbnail\">\n" +
        "    <article>\n" +
        "        <figure><img src=\"img/foto.jpg\" alt=\"\">\n" +
        "        </figure>\n" +
        "        <div class=\"excerpt\">\n" +
        "             <p>" + "<strong class=\"title\"></strong>" + "</p>\n" +
        "            <p class=\"comments news\"></p>\n" +
        "            <p class=\"time\"></p>\n" +
        "        </div>\n" +
        "    </article>\n" +
        "</li>");

      $('#news_list li:first .title').append(news[0].name);
      $('#news_list li:first .comments').append(news[0].comments);
      $('#news_list li:first .time').append(news[0].time);

      localStorage.removeItem(k);
      }
    }
  }
  else {
        var transaction = db.transaction(["reviews"], "readonly");
        var store = transaction.objectStore("reviews");

        store.openCursor().onsuccess = function (e) {
            var cursor = e.target.result;
            if (cursor) {
                cursor.continue();
                $("#news_list").prepend("<li class=\"list-group-item col-sm-4 thumbnail\">\n" +
                "    <article>\n" +
                "        <figure><img src=\"img/foto.jpg\" alt=\"\">\n" +
                "        </figure>\n" +
                "        <div class=\"excerpt\">\n" +
                "             <p>" + "<strong class=\"title\"></strong>" + "</p>\n" +
                "            <p class=\"comments news\"></p>\n" +
                "            <p class=\"time\"></p>\n" +
                "        </div>\n" +
                "    </article>\n" +
                "</li>");

                $('#list li:last .title').append(cursor.value.name);
                $('#list li:last .comments').append(cursor.value.comments);
                $('#list li:last .time').append(cursor.value.time);
            }
        }
    }
}
