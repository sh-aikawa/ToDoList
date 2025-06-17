window.addEventListener("DOMContentLoaded", function () {
    const path = window.location.pathname;
    if (!(path == "/login" || path == "/userRegister" || path == "/todolist/roulette_effect" || path == "/effect")) {
        //背景
        const bgUrl = localStorage.getItem('bgImage');
        if (bgUrl) {
            document.body.style.backgroundImage = `url('${bgUrl}')`;
        } else {
            document.body.style.backgroundImage = "";
        }
    }
    
    //隠し要素処理 
    let title_change_url = document.getElementById('title_text');
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
                let sub = document.getElementById('register_button');

                if (boo) {
                    title_change_url.innerText = "背景変更モード有効";
                    url.setAttribute("maxlength", "5000");
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

    //おみくじ演出
    let select_div = document.getElementById("select");
    if (select_div) {
        select_div.addEventListener('click', function () {
            select_div.classList.add('fade');
            setTimeout(function () {
                location.href = "roulette_effect/listRoulette";
            }, 1800); // 2秒ロック
        });
    }
});