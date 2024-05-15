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

// 验证码刷新
function refreshCaptcha() {
    document.getElementById('imageCaptcha').src='/user/getCode?'+Math.random();
}

// 登录检测
function loginCheck() {
    // 取消页面点击事件
    document.addEventListener("banClick", handler, true);
    // 数据初始化
    toastrInit();
    const act = document.getElementById("userAccount").value;
    const pwd = document.getElementById("userPassword").value;
    const cpc = document.getElementById("userCaptcha").value;
    // 判断账号密码验证码是否为空
    if (act == null || act === "") {
        toastr.info("请输入账号");
        return;
    }
    if (pwd == null || pwd === "") {
        toastr.info("请输入密码");
        return;
    }
    if (cpc == null || cpc === "") {
        toastr.info("请输入验证码");
        return;
    }
    // 调用登录接口
    fetch("/user/loginCheck", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `account=${encodeURIComponent(act)}&password=${encodeURIComponent(pwd)}&captcha=${encodeURIComponent(cpc)}`
    })
        .then(response => response.json())
        .then(resp => {
            console.log(resp);
            if (resp.data != null) {
                // 根据权限判断是否为管理员
                if (resp.data.priority >= 9) {
                    toastr.success("欢迎管理员：" + resp.data.account , "登录成功");
                } else {
                    toastr.success("欢迎用户：" + resp.data.account, "登录成功");
                }
                // 1秒后跳转到下一个页面，同时刷新验证码和解锁按钮
                setTimeout(function() {
                    window.location.href = "../main";
                    refreshCaptcha();
                }, 1000);
                return;
            }
            refreshCaptcha();
            // 重启页面点击事件
            document.removeEventListener("banClick", handler, true);
            switch (resp.code) {
                case "000" :
                    toastr.error("验证码错误!", "登录失败");
                    return;
                case "001" :
                    toastr.error("该账号不存在!", "登录失败");
                    return;
                case "002" :
                    toastr.error("该账号密码错误!", "登录失败");
                    return;
                case "101" :
                    // toastr.error(resp.message, "登录失败");
                    toastr.error("当前已有用户登录!", "登录失败");
                    return;
                default:
                    toastr.error("出现了未知错误!", "登录失败");
            }
        })
        .catch(error => {
            console.error("请求出错：", error);
        });
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

// 注册检测
function registerCheck() {
    toastrInit();
    const act = document.getElementById("userAccountRegister").value;
    const pwd = document.getElementById("userPasswordRegister").value;
    const pwdConfirm = document.getElementById("userPasswordRegisterConfirm").value;
    // 判断账号密码是否为空
    if (act == null || act === "") {
        toastr.info("请输入账号");
        return;
    }
    if (pwd == null || pwd === "") {
        toastr.info("请输入密码");
        return;
    }
    if (pwdConfirm == null || pwdConfirm === "") {
        toastr.info("请再次输入密码");
        return;
    }
    if (pwd !== pwdConfirm) {
        toastr.warning("你两次输入的密码不同!", "警告");
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
                toastr.success("欢迎新用户：" + resp.data.account, "注册成功");
                document.getElementById("userAccount").value = resp.data.account;
                document.getElementById("userPassword").value = "";
                document.getElementById("userAccountRegister").value = "";
                document.getElementById("userPasswordRegister").value = "";
                document.getElementById("userPasswordRegisterConfirm").value = "";
                loginGoto();
                return;
            }
            switch (resp.code) {
                case "004" :
                    toastr.error("该账号已存在!", "注册失败");
                    return;
                default:
                    toastr.error("出现了未知错误!", "注册失败");
            }
        })
        .catch(error => {
            console.error("请求出错：", error);
        });
}

// 重置密码检测
function resetCheck() {
    toastrInit();
    const act = document.getElementById("userAccountReset").value;
    const pwd = document.getElementById("userPasswordReset").value;
    const pwdConfirm = document.getElementById("userPasswordResetConfirm").value;
    // 判断账号密码是否为空
    if (act == null || act === "") {
        toastr.info("请输入账号");
        return;
    }
    if (pwd == null || pwd === "") {
        toastr.info("请输入密码");
        return;
    }
    if (pwdConfirm == null || pwdConfirm === "") {
        toastr.info("请再次输入密码");
        return;
    }
    if (pwd !== pwdConfirm) {
        toastr.warning("你两次输入的密码不同!", "警告");
        return;
    }
    // 调用注册接口
    fetch("/user/resetCheck", {
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
                toastr.success("欢迎用户：" + resp.data.account, "密码重置成功");
                document.getElementById("userAccount").value = resp.data.account;
                document.getElementById("userPassword").value = "";
                document.getElementById("userAccountReset").value = "";
                document.getElementById("userPasswordReset").value = "";
                document.getElementById("userPasswordResetConfirm").value = "";
                loginGoto();
                return;
            }
            switch (resp.code) {
                case "005" :
                    toastr.error("该账号不存在!", "重置失败");
                    return;
                case "006" :
                    toastr.error("该密码与原密码相同!", "重置失败");
                    return;
                case "100" :
                    toastr.error("该账号禁止重置密码!", "重置失败");
                    return;
                default:
                    toastr.error("出现了未知错误!", "注册失败");
            }
        })
        .catch(error => {
            console.error("请求出错：", error);
        });
}

// 页面跳转
function pageGoto(where) {
    document.getElementById("login").className = "sign-div hidden";
    document.getElementById("register").className = "sign-div hidden";
    document.getElementById("reset").className = "sign-div hidden";
    switch (where) {
        case "login" :
            document.getElementById("login").className = "sign-div show";
            return;
        case "register" :
            document.getElementById("register").className = "sign-div show";
            return;
        case "reset" :
            document.getElementById("reset").className = "sign-div show";
            return;
    }
}