window.addEventListener('load', function () {

    function updateOnlineStatus(event) {
        if (isOnline()) {
            ReadOflineReview();
        }
    }

    window.addEventListener('online', updateOnlineStatus);
    window.addEventListener('offline', updateOnlineStatus);
});

function isOnline() {
  return window.navigator.onLine;
}


    var now = new Date();
var i = 0;
var resp = $('#list');
var respMask = 'resp_';


function showResp() {
  if ($('#name').val() === "") {
        alert('Заповніть всі поля');
        return false;
    }

  if (isOnline()) {
    i++;
    var list = [];
    $("#list").append("<li>\n" +
            "                <article>\n" +
            "                    <header>\n" +

            "                        <time class =\"review_time\"></time>\n" +
            "                    </header>\n" +
            "                    <div class=\"comcont\">\n" +
            "                        <p class= 'review'> </p>\n" +
            "                    </div>\n" +
            "                </article>\n" +
            "            </li>");
        $('#list li:last .user_adress').append(
            "Вася Пупкін")
        $('#list li:last .review').append($('#name').val());
        $('#list li:last .review_time').append(now.toDateString());
    list.push({"name":$('#name').val(),
      "time":now.toDateString()});
    $('#name').val('');

        localStorage.setItem(respMask + i, JSON.stringify(list));
  }
  else {
    if (useLocalStorage){

        i++;
        var list = [];
        list.push({"name":$('#name').val(),
          "time":now.toDateString()});
        $('#name').val('');
            localStorage.setItem(respMask +i, JSON.stringify(list));
  }
  else {
    var transaction = db.transaction(["reviews"], "readwrite");
           var store = transaction.objectStore("reviews");
           var review = {
               message: $('#name').val(),
               time: now.toDateString()
           };
           store.add(review);
       }
   }
   $('#name').val('');
}




function ReadOflineReview() {
    if (useLocalStorage) {
        len = localStorage.length + 1;
        for (var k = 1; k < len; k++) {
            $("#list").append("<li>\n" +
                "                <article>\n" +
                "                    <header>\n" +

                "                        <time class =\"review_time\"></time>\n" +
                "                    </header>\n" +
                "                    <div class=\"comcont\">\n" +
                "                        <p class= 'review'> </p>\n" +
                "                    </div>\n" +
                "                </article>\n" +
                "            </li>");

            review = JSON.parse(localStorage.getItem(k));

            $('#list li:last .review').append(review[0].message);
            $('#list li:last .review_time').append(review[0].time);
        }
    }
    else {
        var transaction = db.transaction(["reviews"], "readonly");
        var store = transaction.objectStore("reviews");

        store.openCursor().onsuccess = function (e) {
            var cursor = e.target.result;
            if (cursor) {
                cursor.continue();
                $("#list").append("<li>\n" +
                    "                <article>\n" +
                    "                    <header>\n" +
                    "                        <address class = 'user_adress'>\n" +
                    "\n" +
                    "                        </address>\n" +
                    "                        <time class =\"review_time\"></time>\n" +
                    "                    </header>\n" +
                    "                    <div class=\"comcont\">\n" +
                    "                        <p class= 'review'> </p>\n" +
                    "                    </div>\n" +
                    "                </article>\n" +
                    "            </li>");

                $('#list li:last .review').append(cursor.value.message);
                $('#list li:last .review_time').append(cursor.value.time);
            }
        }
    }
}
