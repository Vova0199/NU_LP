var i = 0;
var now = new Date();

function isOnline() {
    return window.navigator.onLine;
}


function AddNews() {
  if (($('#name').val()==="") || ($('#comments').val()==="")) {
      alert('Заповніть всі дані');
      return false;
  }

    if (isOnline()) {
      i++;
        alert('Новину успішно додано');
        var list = [];
        list.push({"name":$('#name').val(),
            "comments":$('#comments').val(),
          "time":now.toDateString()});
        $('#name').val('');
        $('#comments').val('');

            localStorage.setItem(i, JSON.stringify(list));
    }
    else {
      if (useLocalStorage){
        i++;
        var list = [];
        list.push({"name":$('#name').val(),
            "comments":$('#comments').val(),
          "time":now.toDateString()});
        $('#name').val('');
        $('#comments').val('');

            localStorage.setItem(i, JSON.stringify(list));
        }
        else {
            var transaction = db.transaction(["news"], "readwrite");
            var store = transaction.objectStore("news");
            var news1 = {
                name: $('#name').val(),
                comments: $('#comments').val(),
                time :now.toDateString()
              }
              store.add(news1);
            };
        }

        $('#comments').val('');
        $('#name').val('');
    }
