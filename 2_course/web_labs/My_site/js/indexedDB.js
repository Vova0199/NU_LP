var useLocalStorage = true;

var indexedDB = window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB;
IDBTransaction  = window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction;

var request = indexedDB.open('DB_for_lab', 3);
request.onerror = function (e) {
    console.log('Choto ne tak');
};
request.onsuccess = function(e){
  console.log('Vse normas');
    useLocalStorage = false;
    db = e.target.result;
};
request.onupgradeneeded = function(e){
    var db = e.target.result;
    db.createObjectStore("news", {autoIncrement: true});
    var reviews = db.createObjectStore("reviews", {autoIncrement: true});
};
