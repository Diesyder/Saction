// 页面初始化
window.onload = function () {
    // getInfo();
    setTimeout(getInfo, 200); // 进入页面0.2秒后请求数据
    // setInterval(getInfo, 1000); // 每隔1秒向后端请求发送一次数据
}

// 获取当前用户的数据库
function getInfo() {
    fetch("/user/getUser", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    })
        .then(response => response.json())
        .then(resp => {
            console.log(resp);
            if (resp.data != null) {
                document.getElementById(    "id").value = resp.data.id;
                document.getElementById("account").value = resp.data.account;
                document.getElementById("nameNick").value = resp.data.nameNick;
                document.getElementById("nameReal").value = resp.data.nameReal;
                if (resp.data.gender === "male" || resp.data.gender === "female") {
                    document.getElementById("gender_" + resp.data.gender).checked = true;
                } else {
                    document.getElementById("gender_male").checked = true;
                }
                document.getElementById("phone").value = resp.data.phone;
                document.getElementById("email").value = resp.data.email;
                document.getElementById("address").value = resp.data.address;
                document.getElementById("description").value = resp.data.description;
            }
        })
        .catch(error => {
            console.error("请求出错：", error);
        });
}

// 页面跳转
function pageGoto(where) {
    // 罗列可用编辑的输入栏
    const radioIds = document.getElementsByName("gender");
    const inputIds = [
        "account",
        "nameNick",
        "nameReal",
        "email",
        "phone",
        "address",
        "description"
    ];
    switch (where) {
        // 编辑个人信息
        case "edit" :
            editEnter(radioIds, inputIds);
            return;
        // 返回主界面
        case "back" :
            window.location.href = "../main";
            return;
        // 取消保存
        case "cancel" :
            toastr.info("", "已取消保存");
            editBack(radioIds, inputIds); // 设置按钮为不可编辑
            getInfo(); // 获取用户信息
            return;
        // 保存个人信息
        case "save" :
            // 账号昵称不能为空
            if (document.getElementById(inputIds[0]).value === null) {
                toastr.info("", "账号不可为空");
                return;
            }
            if (document.getElementById(inputIds[1]).value === null) {
                toastr.info("", "昵称不可为空");
                return;
            }
            editBack(radioIds, inputIds); // 设置按钮为不可编辑
            // 根据radio设置性别
            var gender = "male";
            if (radioIds[1].checked === true) {
                gender = "female";
            }
            // 将更新输入传入服务器
            fetch("/user/updateInfo", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `id=${encodeURIComponent(document.getElementById("id").value)}&account=${encodeURIComponent(document.getElementById(inputIds[0]).value)}&nameNick=${encodeURIComponent(document.getElementById(inputIds[1]).value)}&nameReal=${encodeURIComponent(document.getElementById(inputIds[2]).value)}&email=${encodeURIComponent(document.getElementById(inputIds[3]).value)}&phone=${encodeURIComponent(document.getElementById(inputIds[4]).value)}&address=${encodeURIComponent(document.getElementById(inputIds[5]).value)}&description=${encodeURIComponent(document.getElementById(inputIds[6]).value)}&gender=${encodeURIComponent(gender)}`
            })
                .then(response => response.json())
                .then(resp => {
                    console.log(resp);
                    if (resp.data != null) {
                        toastr.success("", "保存成功");
                    } else {
                        switch (resp.code) {
                        case "005" :
                            toastr.error("出现了未知错误", "保存失败");
                            getInfo(); // 获取用户信息
                            return;
                        case "006" :
                            toastr.error("该账号已存在", "保存失败");
                            getInfo(); // 获取用户信息
                            return;
                        default:
                            toastr.error("出现了未知错误", "保存失败");
                        }
                    }
                })
                .catch(error => {
                    console.error("请求出错：", error);
                });
            return;
    }
}

// 进入编辑状态
function editEnter(radioIds, inputIds) {
    document.getElementById("edit").className = "input-btn hidden";
    document.getElementById("back").className = "input-btn hidden";
    document.getElementById("cancel").className = "input-btn";
    document.getElementById("save").className = "input-btn";
    // 、使输入栏变得可编辑
    for (let i = 0; i < inputIds.length; i++) {
        const inputId = document.getElementById(inputIds[i]);
        inputId.removeAttribute('readonly');
        inputId.classList.add('input-edit');
    }
    for (let i = 0; i < radioIds.length; i++) {
        radioIds[i].disabled = false; // 设置为可编辑状态
    }
}

// 离开编辑状态
function editBack(radioIds, inputIds) {
    document.getElementById("edit").className = "input-btn";
    document.getElementById("back").className = "input-btn";
    document.getElementById("cancel").className = "input-btn hidden";
    document.getElementById("save").className = "input-btn hidden";
    // 使输入栏变得不可编辑
    for (let i = 0; i < inputIds.length; i++) {
        const inputId = document.getElementById(inputIds[i]);
        inputId.setAttribute('readonly', 'readonly');
        inputId.classList.remove('input-edit');
    }
    for (let i = 0; i < radioIds.length; i++) {
        radioIds[i].disabled = true; // 设置为可编辑状态
    }
}
