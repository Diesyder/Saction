function spread() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight) {
        content.style.maxHeight = null;
    } else {
        content.style.maxHeight = content.scrollHeight + "px";
    }
}

// 添加事件监听器
var coll = document.getElementsByClassName("collapsible");
for (var i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", spread);
}

// 定义 showMoreButtons 函数
function toggleButtons() {
    // 获取所有隐藏的按钮
    var buttons = document.querySelectorAll('.hidden');
    // 遍历并切换按钮的显示状态
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].classList.toggle('fade-in');
    }
}
function tempC() {
    document.getElementsByClassName("hidden").className = "fade-in";
}


function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip) {
    var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
    var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
    var openTip = oOpenTip || "";
    var shutTip = oShutTip || "";
    if (targetObj.style.display != "none") {
        if (shutAble) return;
        targetObj.style.display = "none";
        if (openTip && shutTip) {
            sourceObj.innerHTML = shutTip;
        }
    } else {
        targetObj.style.display = "block";
        if (openTip && shutTip) {
            sourceObj.innerHTML = openTip;
        }
    }
}