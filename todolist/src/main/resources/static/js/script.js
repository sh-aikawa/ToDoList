window.addEventListener("DOMContentLoaded", function () {
    let isDescending = localStorage.getItem('isDescending') === 'true';//ソート用

    const path = window.location.pathname;
    const nav = document.getElementById("hamburger-nav");
    const modoru = document.getElementById("modoru");
    const completed = document.getElementById("completed");
    const register = document.getElementById("register");
    const comet = document.getElementById("comet");
    const logout = document.getElementById("logout");
    const nexus = document.getElementById("nexus");
    const setting = document.getElementById("setting");

    if (path === "/login" || path === "/userRegister") {
        if (nav) nav.style.display = "none";
    } else {
        // 現在ページに該当するリンクを非表示
        if (path === "/todolist" && modoru) modoru.style.display = "none";
        if (path === "/todolist/finish" && completed) completed.style.display = "none";
        if (path === "/todolist/register" && register) register.style.display = "none";
        if (path === "/comet" && comet) comet.style.display = "none";
        if (path === "/login" && logout) logout.style.display = "none";
        if (path === "/nexus" && nexus) nexus.style.display = "none";
        if (path === "/setting" && setting) setting.style.display = "none";
    }

    if (!(path === "/effect")) {
        //テーマカラー
        let themeColor = localStorage.getItem('theme');
        let header = document.querySelector(".header-area");
        let footer = document.querySelector(".footer-area");
        let menu = document.querySelector(".menu");
        if (themeColor) {
            header.style.backgroundColor = themeColor;
            footer.style.backgroundColor = themeColor;
            menu.style.backgroundColor = themeColor;
        }

        //文字色
        let textColor = localStorage.getItem('text');
        let hedTitle = document.querySelector(".app-title");
        let menuIcons = document.querySelectorAll(".menu-icon");
        let menuLinks = document.querySelectorAll(".menu-link");
        let fotTitle = document.querySelector(".footer-title");
        if (textColor) {
            if (hedTitle) hedTitle.style.color = textColor;
            menuIcons.forEach(function (icon) {
                icon.querySelectorAll("span").forEach(function (span) {
                    span.style.backgroundColor = textColor;
                });
            });
            menuLinks.forEach(function (link) {
                link.style.color = textColor;
            });
            if (fotTitle) fotTitle.style.color = textColor;
        }

        //テーブルの色変更
        // localStorage から色を取得
        let tableColor = localStorage.getItem('table');
        let even = localStorage.getItem('even');
        let odd = localStorage.getItem('odd');

        if (tableColor && even && odd) {
            // ToDoリスト
            let toDoFirstRow = document.querySelector('.toDolist tr:nth-child(1)');
            if (toDoFirstRow) {
                toDoFirstRow.style.backgroundColor = tableColor;
            }

            let toDoEvenRows = document.querySelectorAll('.toDolist tr:nth-child(2n+2)');
            toDoEvenRows.forEach(function (row) {
                row.style.backgroundColor = even;
            });

            let toDoOddRows = document.querySelectorAll('.toDolist tr:nth-child(2n+3)');
            toDoOddRows.forEach(function (row) {
                row.style.backgroundColor = odd;
            });

            // 完了リスト
            let completedFirstRow = document.querySelector('.completedList tr:nth-child(1)');
            if (completedFirstRow) {
                completedFirstRow.style.backgroundColor = tableColor;
            }

            let completedEvenRows = document.querySelectorAll('.completedList tr:nth-child(2n+2)');
            completedEvenRows.forEach(function (row) {
                row.style.backgroundColor = even;
            });

            let completedOddRows = document.querySelectorAll('.completedList tr:nth-child(2n+3)');
            completedOddRows.forEach(function (row) {
                row.style.backgroundColor = odd;
            });

            let demoColor = document.querySelector(".table-img");
            if (demoColor) {
                demoColor.style.backgroundColor = tableColor;
            }
        }

    }

    //背景設定
    let display_moji = document.getElementById("display");
    let enter_date = document.getElementById("date");
    let push = document.getElementById("item");
    let title_change_url = document.getElementById('title_text');
    //ログイン、登録画面以外に適応
    if (path !== "/login" && path !== "/userRegister" && path !== "/todolist/roulette_effect" && path !== "/efect") {
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

    //おみくじ演出
    let select_div = document.getElementById("select");
    if (select_div) {
        select_div.addEventListener('click', function () {
            select_div.classList.add('fade');
            setTimeout(function () {
                location.href = "roulette_effect/listroulette";
            }, 1800); // 2秒ロック
        });
    }

    // ページ読み込み時に2秒後に別のページに遷移
    let ran = Math.random() * ( 5000 - 2000 ) + 2000;
    console.log(ran);
    if (this.document.body.id === "effect") {
        setTimeout(function () {
            window.location.href = "/todolist";
        }, ran);
    }



    //隠し要素処理 
    let count = 0;
    let boo = true;
    if (title_change_url) {
        title_change_url.addEventListener('click', function () {
            if (title_change_url.dataset.locked === "true") return;

            title_change_url.classList.add('anim-box', 'poyopoyo');
            title_change_url.dataset.locked = "true";

            setTimeout(function () {
                title_change_url.classList.remove('anim-box', 'poyopoyo');
                title_change_url.dataset.locked = "false";
            }, 1000); // 1秒ロック

            if (count < 3) {
                count++;
            } else {
                let yotei = document.getElementById('yotei');
                let url = document.getElementById('title');
                let sub = document.getElementById('registar_button');

                if (boo) {
                    title_change_url.innerText = "背景変更モード有効";
                    if (yotei) yotei.innerText = "URL(resetと入力すると背景をリセットできます。)";
                    if (sub) sub.remove();
                    boo = false;

                } else if (url && url.value == "reset") {
                    title_change_url.innerText = "背景がリセットされました";
                    document.body.style.backgroundImage = "";
                    document.body.style.backgroundColor = "white";
                    localStorage.setItem('bgImage', "");;

                }
                //httpではない場合に特定の文字を入力すると処理を行う。 
                else if (url && !(url.value.startsWith("http"))) {
                    switch (url.value) {
                        case "packman":
                            window.open("https://www.google.co.jp/logos/2010/pacman10-i.html");
                            break;

                        case "proseka":
                            window.open("https://pjsekai.sega.jp/");
                            break;

                        case "cybercom":
                            window.open("https://www.cy-com.co.jp/");
                            break;

                        case "givery":
                            window.open("https://givery.co.jp/");
                            break;

                        case "gacha":
                            location.href = "roulette_effect";
                            break;

                        default:
                            title_change_url.innerText = "URLを入力してください！"
                            break;
                    }
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
            if (!enter_date.value) {
                event.preventDefault();
                display_moji.innerHTML = "数字を入力してください！";
                display_moji.style.color = "#ED1A3D";
            }
        }
    });

    const descForm = document.getElementById("descForm");
    const ascForm = document.getElementById("ascForm");


    if (isDescending) {
        ascForm.style.display = "none";
        descForm.style.display = "block";
    } else {
        descForm.style.display = "none";
        ascForm.style.display = "block";
    }

    function toggleSort() {

        if (isDescending) {
            descForm.style.display = "none";
            ascForm.style.display = "block";
            descForm.submit();
        } else {
            ascForm.style.display = "none";
            descForm.style.display = "block";
            ascForm.submit();
        }

        isDescending = !isDescending;
        localStorage.setItem('isDescending', isDescending);
    }

    document.getElementById("sortDesc").onclick = toggleSort;
    document.getElementById("sortAsc").onclick = toggleSort;

    // nexus chatページで1分ごとにchat-listを自動更新
    if (window.location.pathname === "/nexus/chat") {
        setInterval(update, 60000); // 1分ごとにupdate実行
    }
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
    // nexusのチャットエリアのスクロールを最新にする
}

//色変え処理
function changeColor(flag) {
    let defaultThemeColor = "#e0e0e0";
    let defaultTextColor = "#555";
    let defaultTableColors = ["#e0e0e0", "#f8f8f8", "#f0f0f0"];
    let themeColor = document.getElementById("themeColor").value;
    let textColor = document.getElementById("textColor").value;
    let tableColor = document.getElementById("tableColor").value;
    const hexPattern = /^([0-9A-F]{3}){1,2}$/i;

    if (flag == 0) {
        // デフォルトの色にリセット
        localStorage.setItem('theme', defaultThemeColor);
        localStorage.setItem('text', defaultTextColor);
        localStorage.setItem('table', defaultTableColors[0]);
        localStorage.setItem('even', defaultTableColors[1]);
        localStorage.setItem('odd', defaultTableColors[2]);
        location.reload(true);
    } else if (flag == 1) {
        //テーマ
        if (themeColor && hexPattern.test(themeColor)) {
            localStorage.setItem('theme', "#" + themeColor);
            location.reload(true);
        } else {
            alert("正しい色を入力してください（例: ff5733）");
        }
    } else if (flag == 2) {
        //文字
        if (textColor && hexPattern.test(textColor)) {
            localStorage.setItem('text', "#" + textColor);
            location.reload(true);
        } else {
            alert("正しい色を入力してください（例: ff5733）");
        }
    } else if (flag == 3) {
        //テーブル
        //[1行目、偶数、奇数]
        const tableColors = [
            "#FF6666", "#FFE6E6", "#FF9999", // 赤
            "#6666FF", "#E6E6FF", "#9999FF", // 青
            "#FFFF00", "#FFFFE6", "#FFFF66", // 黄色
            "#00FF00", "#E6FFE6", "#66FF66", // 緑
            "#ffb2ff", "#ffe6ff", "#ffd1ff", // ピンク
            "#9effff", "#c6ffff", "#b0ffff", // 水色
            "#d1a3ff", "#e0c2ff", "#d9b3ff"  // 紫
        ];
        let index = tableColor * 3
        localStorage.setItem('table', tableColors[index]);
        localStorage.setItem('even', tableColors[index + 1]);
        localStorage.setItem('odd', tableColors[index + 2]);
        location.reload(true);
    }
}


//削除処理の実装

let button = document.getElementById("button_delete");
button.addEventListener('click', function () {
    function delete_update_task(taskId) {
        const token = document.querySelector('meta[name="_csrf"]').getAttribute("content");
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");
    }

});