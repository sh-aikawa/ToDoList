
window.addEventListener("DOMContentLoaded", function () {
    //ヘッダー、フッター
    const register = document.getElementById("register");
    const modoru = document.getElementById("modoru");
    const completed = document.getElementById("completed");

    switch (window.location.pathname) {
        case "/todolist":
        case "/search":
            if (modoru) modoru.style.display = "none";
            if (register) register.style.display = "";
            if (completed) completed.style.display = "";
            break;
        case "/login":
        case "/userRegister":
            if (register) register.style.display = "none";
            if (modoru) modoru.style.display = "none";
            if (completed) completed.style.display = "none";
            break;
        case "/todolist/register":
            if (modoru) modoru.style.display = "";
            if (register) register.style.display = "none";
            if (completed) completed.style.display = "";
            break;
        case "/todolist/finish":
            if (modoru) modoru.style.display = "";
            if (register) register.style.display = "none";
            if (completed) completed.style.display = "none";
            break;
    }

    //背景設定
    let display_moji = document.getElementById("display");
    let enter_date = document.getElementById("date");
    let push = document.getElementById("item");
    const bgUrl = localStorage.getItem("bgImage");
    //nullチェック追加
    if (bgUrl) {
        document.body.style.backgroundImage = `url('${bgUrl}')`;
    } else {
        document.body.style.backgroundImage = "";
    }
    push.addEventListener("click", function (event) {
        if (!enter_date.value) {
            event.preventDefault();
            display_moji.innerHTML = "数字を入力してください！";
            display_moji.style.color = "#ED1A3D";
        }
    });
});

// タスクのチェックボックスが変更されたときに呼び出される関数
function updateCheckedStatus(checkbox) {
    const taskId = checkbox.getAttribute("data-task-id");
    const checked = checkbox.checked;

    const token = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    fetch("/todolist/check", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            [header]: token
        },
        body: `taskId=${taskId}&checked=${checked}`,
    }).then((response) => {
        if (response.ok) {
            // 更新成功時の処理
            window.location.reload(); // ページをリロードして更新を反映
        } else {
            // 更新失敗時の処理
            console.error(`Failed to update task ${taskId}.`);
        }
        if (!response.ok) {
            alert("更新に失敗しました");
        }
    });
}