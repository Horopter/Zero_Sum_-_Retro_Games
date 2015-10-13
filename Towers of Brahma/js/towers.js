function Game(n) {
  this.stack1 = [];
  this.stack2 = [];
  this.stack3 = [];
  this.n = n;

  for (var i = n; i > 0; i--) {
    this.stack1.push(i);
  }

  buildStack(document.querySelector("#tower1"));

  function buildStack(node) {
    for(var i = 1; i <= n; i++) {
      var disk = document.createElement("div");
      disk.className = "disk color" + (i%11);
      disk.textContent = i;
      disk.style.width = (40 + i*60/n) + "%";
      disk.style.top = (560 - n*42) + "px";
      node.appendChild(disk);
    }
  }
}

Game.prototype = {
  gameOver: function() {
    return document.querySelectorAll("#tower1 > .disk").length == 0 &&
           document.querySelectorAll("#tower2 > .disk").length == 0;
  },

  legalMove: function(fromTowerNum, toTowerNum) {
    if (fromTowerNum == null || toTowerNum == null) { return false; }
    if (fromTowerNum <= 0 || fromTowerNum > this.n ||
        toTowerNum <= 0 || toTowerNum > this.n) { return false; }
    var fromStack = this["stack" + fromTowerNum];
    var toStack = this["stack" + toTowerNum];
    if (fromStack.length == 0) return false;
    if (toStack.length == 0) return true;
    if (fromStack[fromStack.length-1] < toStack[toStack.length-1]) {
      return true;
    } else {
      return false;
    }
  },

  makeMove: function(fromTowerNum, toTowerNum) {
    if (!this.legalMove(fromTowerNum, toTowerNum)) { return false; }
    // Make move locally
      this["stack" + toTowerNum].push(
      this["stack" + fromTowerNum].pop()
      );
    // Make move in html
    var fromTower = document.getElementById("tower" + fromTowerNum);
    var toTower = document.getElementById("tower" + toTowerNum);
    var node = fromTower.getElementsByClassName("disk")[0];
    fromTower.removeChild(node);
    if (toTower.childNodes.length == 0) {
      toTower.appendChild(node);
    } else {
      toTower.insertBefore(node, toTower.childNodes[0]);
    }
    // Re-style towers
    var disks = fromTower.getElementsByClassName("disk");
    for(var i = 0; i < disks.length; i++) {
      disks[i].style.top = (560-disks.length*42) + "px";
    }
    disks = toTower.getElementsByClassName("disk");
    for(var i = 0; i < disks.length; i++) {
      disks[i].style.top = (560-disks.length*42) + "px";
    }

    // Check for game over
    if (this.gameOver()) {
      return true;
    }
    return false;
  }
};
