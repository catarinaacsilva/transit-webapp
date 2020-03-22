var lista = [];

$( window ).ready(function() {
  connect();
});

function connect() {
  var socket = new SockJS('/websocket');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
      stompClient.subscribe('/topic/delay', function (notification) {
          var obj = JSON.parse(notification.body);
          lista.push(obj);
          document.getElementById("no").style.display = "none";

          var node = document.createElement("li");
          var textnode = document.createTextNode("Vendor: " + obj["vendor"] + ", Type: " + obj["type"] + ", Line: " + obj["line"] + " (" + obj["begin"] + " <-> " + obj["end"] + "), Delay: " + obj["delay"] + " min");
          node.appendChild(textnode);
          node.className = "list-group-item"
          document.getElementById("del").insertBefore(node, document.getElementById("del").childNodes[0]);
       });
  });
}