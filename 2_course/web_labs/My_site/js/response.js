var resp = $('#list');
var respMask = 'resp_';
function showResp(){
  var respLen = localStorage.length;
  if(respLen > 0){
    for (var i = 0; i < respLen; i++) {
      var key = localStorage.key(i);
      if(key.indexOf(respMask) === 0){
        $('<li></li>').addClass('lis').attr('data-itemId', key)
        .text(localStorage.getItem(key))
        .appendTo(resp);
      }
    }
  }
}
showResp();

$('#btn1').bind('click',function(e){
  if ($('#name').val()===""){
       alert('Заповніть всі поля');
       return false;
   }


  var str = $("#name").val();
  $('#name').val('');
  if (str.length > 0) {
    var nId = 0;
    resp.children().each(function(index, el){
      var jelId = $(el).attr('data-itemId').slice(5);
      if (jelId > nId)
        nId = jelId;

    })
    nId++;
    localStorage.setItem(respMask+nId, str);
    $('<li></li>').addClass('lis').attr('data-itemId', respMask+nId).text(str).appendTo(resp);
  }
});
