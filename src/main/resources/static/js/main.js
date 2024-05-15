// 页面跳转
function pageGoto(where) {
    // toastr.info('这是一个提示=' + where, 'test', {timeOut: 3000, positionClass: 'toast-top-right', closeButton: false});
    document.getElementById("btn-0").className = "btn-collapsible";
    if (document.getElementById("btn-1")) {
        document.getElementById("btn-1").className = "btn-collapsible";
    }
    if (document.getElementById("btn-2")) {
        document.getElementById("btn-2").className = "btn-collapsible";
    }
    document.getElementById("btn-3").className = "btn-collapsible";
    if (document.getElementById("btn-4")) {
        document.getElementById("btn-4").className = "btn-collapsible";
    }
    if (document.getElementById("btn-5")) {
        document.getElementById("btn-5").className = "btn-collapsible";
    }
    document.getElementById("activities").className = "hidden";
    document.getElementById("activitiesManage").className = "hidden";
    document.getElementById("MyActivities").className = "hidden";
    document.getElementById("organizations").className = "hidden";
    document.getElementById("organizationsManage").className = "hidden";
    document.getElementById("MyOrganizations").className = "hidden";
    switch (where) {
        case "0" :
            document.getElementById("btn-0").className = "btn-collapsible btn-select";
            document.getElementById("activities").className = "show";
            return;
        case "1" :
            document.getElementById("btn-1").className = "btn-collapsible btn-select";
            document.getElementById("activitiesManage").className = "show";
            return;
        case "2" :
            document.getElementById("btn-2").className = "btn-collapsible btn-select";
            document.getElementById("MyActivities").className = "show";
            return;
        case "3" :
            document.getElementById("btn-3").className = "btn-collapsible btn-select";
            document.getElementById("organizations").className = "show";
            return;
        case "4" :
            document.getElementById("btn-4").className = "btn-collapsible btn-select";
            document.getElementById("organizationsManage").className = "show";
            return;
        case "5" :
            document.getElementById("btn-5").className = "btn-collapsible btn-select";
            document.getElementById("MyOrganizations").className = "show";
            return;
    }
}

// function spread() {
//     this.classList.toggle("active");
//     var content = this.nextElementSibling;
//     if (content.style.maxHeight) {
//         content.style.maxHeight = null;
//     } else {
//         content.style.maxHeight = content.scrollHeight + "px";
//     }
// }
//
// // 添加事件监听器
// var coll = document.getElementsByClassName("collapsible");
// for (var i = 0; i < coll.length; i++) {
//     coll[i].addEventListener("click", spread);
// }
//
// // 定义 showMoreButtons 函数
// function toggleButtons() {
//     // 获取所有隐藏的按钮
//     var buttons = document.querySelectorAll('.hidden');
//     // 遍历并切换按钮的显示状态
//     for (var i = 0; i < buttons.length; i++) {
//         buttons[i].classList.toggle('fade-in');
//     }
// }
// function tempC() {
//     document.getElementsByClassName("hidden").className = "fade-in";
// }
//
//
// function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip) {
//     var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
//     var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
//     var openTip = oOpenTip || "";
//     var shutTip = oShutTip || "";
//     if (targetObj.style.display != "none") {
//         if (shutAble) return;
//         targetObj.style.display = "none";
//         if (openTip && shutTip) {
//             sourceObj.innerHTML = shutTip;
//         }
//     } else {
//         targetObj.style.display = "block";
//         if (openTip && shutTip) {
//             sourceObj.innerHTML = openTip;
//         }
//     }
// }