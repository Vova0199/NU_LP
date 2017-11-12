// window.addEventListener('load', function() {
//
//     function updateOnlineStatus(event) {
//         if(isOnline()){
//             ReadOflineNews();
//         }
//     }
//     window.addEventListener('online',  updateOnlineStatus);
//     window.addEventListener('offline', updateOnlineStatus);
// });
//
// function isOnline() {
//     return window.navigator.onLine;
//
// }


function ReadOflineNews() {
    len = localStorage.length + 1;
    for (var k = 1; k < len; k++) {
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

        news = JSON.parse(localStorage.getItem(k));
        console.log(news[0].name);
        console.log(news[0].comments);
        console.log(news[0].time);


        $('#news_list li:first .title').append(news[0].name);
        $('#news_list li:first .comments').append(news[0].comments);
        $('#news_list li:first .time').append(news[0].time);

        localStorage.removeItem(k);
    }
}
ReadOflineNews();
