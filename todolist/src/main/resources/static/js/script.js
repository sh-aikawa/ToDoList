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
