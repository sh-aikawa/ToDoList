
window.addEventListener("DOMContentLoaded", function () {
    //ヘッダー
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
    const path = window.location.pathname;
    let display_moji = document.getElementById("display");
    let enter_date = document.getElementById("date");
    let push = document.getElementById("item");
    let title_change_url = document.getElementById('title_text');
    //ログイン、登録画面以外に適応
    if (path !== "/login" && path !== "/userRegister") {
        const bgUrl = localStorage.getItem('bgImage');
        //nullチェック
        if (bgUrl) {
            document.body.style.backgroundImage = `url('${bgUrl}')`;
        } else {
            document.body.style.backgroundImage = "";
        }
    }

    //ボタンアニメーション
    let submit_buttons = document.querySelectorAll("button");
    submit_buttons.forEach(function(submit_button) {
        submit_button.addEventListener('mouseover', function(){
            if (submit_button.dataset.locked === "true") return;

            submit_button.classList.add('anim-box', 'poyopoyo');
            submit_button.dataset.locked = "true";

            setTimeout(function(){
                submit_button.classList.remove('anim-box', 'poyopoyo');
                submit_button.dataset.locked = "false";
            }, 1000); // 1秒ロック
        });
    });

    //隠し要素処理 
    let count = 0;
    let boo = true;
    if (title_change_url) {
        title_change_url.addEventListener('click', function(){
            if (title_change_url.dataset.locked === "true") return;

            title_change_url.classList.add('anim-box', 'poyopoyo');
            title_change_url.dataset.locked = "true";

            setTimeout(function(){
                title_change_url.classList.remove('anim-box', 'poyopoyo');
                title_change_url.dataset.locked = "false";
            }, 1000); // 1秒ロック
            if (count < 3) {
                count++;
            } else {
                let yotei = document.getElementById('yotei');
                let url = document.getElementById('title');
                let sub = document.getElementById('submit');

                if (boo) {
                    title_change_url.innerText = "背景変更モード有効";
                    if (yotei) yotei.innerText = "URL(resetと入力すると背景をリセットできます。)";
                    if (sub) sub.remove();
                    boo = false;

                } else if (url && url.value == "reset") {
                    title_change_url.innerText = "背景がリセットされました";
                    document.body.style.backgroundImage = "";
                    document.body.style.backgroundColor = "white";
                    localStorage.setItem('bgImage', "");

                } else if (url && !(url.value.startsWith("http"))) {
                    title_change_url.innerText = "URLを入力してください！";
                } else if (url) {
                    title_change_url.innerText = "URLを適用しました";
                    document.body.style.backgroundImage = `url('${url.value}')`;
                    localStorage.setItem('bgImage', url.value);
                }
            }
        });
    }

    push.addEventListener("click", function (event) {
        if (push && enter_date && display_moji) {
            if (!enter_date.value){
                event.preventDefault();
                display_moji.innerHTML = "数字を入力してください！";
                display_moji.style.color = "#ED1A3D";
            }
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