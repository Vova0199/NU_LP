function add() {
  var name, position, resp, res;
  var div = document.createElement("div");
  //div.id = "addresp";
  //div.className = "row";

  name = document.getElementById('name').value;
  position = document.getElementById('position').value;
  resp = document.getElementById('comment').value;

  if (($('#longdescription').val() === "") || ($('#name').val() === "")) {
    alert('Заповніть всі поля');
    return false;
  } else {


    document.getElementById('addresp').innerHTML = '<div class="col-sm-3">' +
      '<img src="img/bg.png" class="img-circle" alt="Cinque Terre" width="150" height="150">' +
      '</div>' +
      '  <div class="col-sm-8">' +
      '  <h2>' + name + '</h2>' +
      '  <p><strong>' + position + '<br></strong>' + resp + '</p>' +
      '</div>';
  }
}
