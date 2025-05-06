// 页面初始化
window.onload = function () {
    // getInfo();
    setTimeout(getInfo, 100); // 进入页面0.1秒后请求数据
}

// 获取记忆的页面
function getInfo() {
    if (localStorage.getItem('selection') == null) {
        localStorage.setItem('selection', 0);
        return;
    }
    pageGoto(localStorage.getItem('selection'));
}

// 页面跳转
function pageGoto(where) {
    const btnIds = [
        "btn-0",
        "btn-1",
        "btn-2",
        "btn-3",
        "btn-4",
        "btn-5",
        "btn-6",
        "btn-7",
    ];
    const selectIds = [
        "activities",
        "activitiesManage",
        "myActivities",
        "organizations",
        "organizationsManage",
        "myOrganizations",
        "userManage",
        "recommend"
    ];
    // 设置全部btn未选中和div不可见
    for (let i = 0; i < btnIds.length; i++) {
        const btnId = document.getElementById(btnIds[i]);
        const selectId = document.getElementById(selectIds[i]);
        if (btnId) btnId.className = "btn-collapsible";
        selectId.className = "hidden";
    }
    // 显示可见区域
    document.getElementById(btnIds[where]).className = "btn-collapsible btn-select";
    document.getElementById(selectIds[where]).className = "show";
    // 记忆当前选择
    localStorage.setItem('selection', where);
    return;
}

// 刷新推荐
function refreshRecommend() {
    location.reload();
}
