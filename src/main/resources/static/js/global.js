// window.alert("你已进入登录界面，请注意隐私安全");
// toastr.info('这是一个提示！', 'test', {timeOut: 3000, positionClass: 'toast-top-right', closeButton: false});

// 事件处理
function handler(e) {
    e.stopPropagation();
    e.preventDefault();
}

// toastr初始化
function toastrInit() {
    toastr.options.newestOnTop = false;
    toastr.options.timeOut = 3000;
    toastr.options.positionClass = "toast-top-center";
    // toastr.options.preventDuplicates = true;
    toastr.options.tapToDismiss = true;
}

// 登出检测
function loginoutCheck() {
    // 取消页面点击事件
    document.addEventListener("banClick", handler, true);
    // 调用登出接口
    fetch("/user/loginOut", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
        .then(response => response.json())
        .then(resp => {
            console.log(resp);
            toastrInit();
            if (resp.data != null) {
                toastr.success("登出成功");
                // 1秒后刷新页面
                setTimeout(function() {
                    location.reload();
                }, 1000);
                return;
            }
            // 重启页面点击事件
            document.removeEventListener("banClick", handler, true);
            switch (resp.code) {
                case "102" :
                    toastr.error("你尚未登录!", "登出失败");
                    return;
                default:
                    toastr.error("出现了未知错误!", "登出失败");
            }
        })
        .catch(error => {
            console.error("请求出错：", error);
        });

}