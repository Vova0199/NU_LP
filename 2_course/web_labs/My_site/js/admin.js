var i = 0;
var now = new Date();

function isOnline() {
    return window.navigator.onLine;
}
function addLocal() {
  i++;
  var list = [];
  list.push({"name":$('#name').val(),
      "comments":$('#comments').val(),
    "time":now.toDateString()});
  localStorage.setItem(i, JSON.stringify(list));
  $('#name').val('');
  $('#comments').val('');
}

function AddNews() {
    if (($('#name').val()==="") || ($('#comments').val()==="")) {
        alert('Заповніть всі дані');
        return false;
    }

    if (isOnline()) {
        addLocal();
        $('#name').val('');
        $('#comments').val('');
        alert('Новину успішно додано');

    }

    else {
      addLocal();
    }
}
