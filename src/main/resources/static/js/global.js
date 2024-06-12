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
                localStorage.removeItem('selection');
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

// 异步用的延迟方式
// function delay(ms) {
//     return new Promise(resolve => setTimeout(resolve, ms));
// }

// 通用操作
function generalOps(ops, id) {
    let userid = "";
    let orgid = "";
    let actid = "";
    switch (ops) {
        case 'joinAct' :
            userid = document.getElementById("userid").value;
            actid = document.getElementById("actid").value;
            if (userid == null || userid === "" || actid == null || actid === "") {
                window.alert("两个输入栏不能为空");
                return;
            }
            // 调用注册接口
            fetch("/service/joinAct", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `userid=${encodeURIComponent(userid)}&actid=${encodeURIComponent(actid)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                        return;
                    }
                    switch (resp.code) {
                        case "001" :
                            window.alert("该id所属账号不存在");
                            return;
                        case "002" :
                            window.alert("该id所属活动不存在");
                            return;
                        case "003" :
                            window.alert("该用户已参与该活动");
                            return;
                        default:
                            window.alert("发生未知错误");
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
        case 'joinAct-d' :
            fetch("/service/deleteJA", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
        case 'joinOrg' :
            userid = document.getElementById("userid").value;
            orgid = document.getElementById("orgid").value;
            if (userid == null || userid === "" || orgid == null || orgid === "") {
                window.alert("两个输入栏不能为空");
                return;
            }
            // 调用注册接口
            fetch("/service/joinOrg", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `userid=${encodeURIComponent(userid)}&orgid=${encodeURIComponent(orgid)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                        return;
                    }
                    switch (resp.code) {
                        case "001" :
                            window.alert("该id所属账号不存在");
                            return;
                        case "002" :
                            window.alert("该id所属组织不存在");
                            return;
                        case "003" :
                            window.alert("该用户已加入该组织");
                            return;
                        default:
                            window.alert("发生未知错误");
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
        case 'joinOrg-d' :
            fetch("/service/deleteJO", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
    }
}

// 用户操作
function userOps(ops, id) {
    switch (ops) {
        case 'c' :
            const act = document.getElementById("account").value;
            const pwd = document.getElementById("password").value;
            // 判断账号密码是否为空
            if (act == null || act === "" || pwd == null || pwd === "") {
                window.alert("账号或密码不能为空");
                return;
            }
            // 调用注册接口
            fetch("/user/registerCheck", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `account=${encodeURIComponent(act)}&password=${encodeURIComponent(pwd)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                        return;
                    }
                    switch (resp.code) {
                        case "004" :
                            window.alert("该账号已存在!");
                            return;
                        default:
                            window.alert("出现了未知错误!");
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
        case 'u' :
            const id_2 = document.getElementById("id").value;
            const pri_2 = document.getElementById("priority").value;
            // 判断账号密码是否为空
            if (id_2 == null || id_2 === "" || pri_2 == null || pri_2 === "") {
                window.alert("id或权限不能为空");
                return;
            }
            fetch("/user/updatePriority", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id_2)}&priority=${encodeURIComponent(pri_2)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                        return;
                    }
                    switch (resp.code) {
                        case "005" :
                            window.alert("该账号不存在!");
                            return;
                        default:
                            window.alert("出现了未知错误!");
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
        case 'd' :
            fetch("/user/deleteUser", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
    }
    return;
}

// 组织操作
function orgOps(ops, id) {
    switch (ops) {
        case 'd' :
            fetch("/org/deleteOrg", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
    }
    return;
}

// 活动操作
function actOps(ops, id) {
    switch (ops) {
        case 'd' :
            fetch("/act/deleteAct", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(id)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        location.reload(); // 刷新页面
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
    }
    return;
}