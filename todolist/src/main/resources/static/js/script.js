window.addEventListener("DOMContentLoaded", function () {
    //ボタンアニメーション
    let submit_buttons = document.querySelectorAll("button");
    submit_buttons.forEach(function (submit_button) {
        submit_button.addEventListener('mouseover', function () {
            if (submit_button.dataset.locked === "true") return;

            submit_button.classList.add('anim-box', 'poyopoyo');
            submit_button.dataset.locked = "true";

            setTimeout(function () {
                submit_button.classList.remove('anim-box', 'poyopoyo');
                submit_button.dataset.locked = "false";
            }, 1000); // 1秒ロック
        });
    });

    // ページ読み込み時に2秒後に別のページに遷移
    let ran = Math.random() * (5000 - 2000) + 2000;
    if (this.document.body.id === "effect") {
        if (inFirstVisit) {
            setTimeout(function () {
                fetch('/updateFlag', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Response from server:', data);
                        if (data.success) {
                            window.location.href = "/todolist";
                        } else {
                            console.error('Update failed');
                        }
                    })
            }, ran);
        } else {
            this.window.location.href = "/todolist";
        }
    }

    document.getElementById("app-title").addEventListener("click", function() {
            window.location.href = "/todolist";
    });

    // nexus chatページで1分ごとにchat-listを自動更新
    if (window.location.pathname === "/nexus/chat") {
        setInterval(update, 60000); // 1分ごとにupdate実行
    }
});
