window.addEventListener("DOMContentLoaded", function () {
    const path = window.location.pathname;
    if (!(path == "/login" || path == "/userRegister" || path == "/todolist/roulette_effect" || path == "/effect")) {
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
});

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

