document.addEventListener('DOMContentLoaded', function () {
    // 初期表示時に未読数の表示制御を実行
    updateUnreadCounts();
    // 5秒ごとに更新
    setInterval(updateUnreadCounts, 5000);
});

function updateUnreadCounts() {
    const unreadCountElements = document.querySelectorAll('.unread-count');
    unreadCountElements.forEach(element => {
        const userId = element.getAttribute('data-user-id');
        if (userId) {
            fetch(`/nexus/unreadCount/${userId}`)
                .then(response => response.text())
                .then(count => {
                    element.textContent = count;
                })
                .catch(error => console.error('Error updating unread count:', error));
        }
        if (element.textContent == 0) {
            element.style.display = "none";
        } else {
            element.style.display = "block";
        }
    });
}

document.addEventListener('DOMContentLoaded', function () {
    const chatList = document.getElementById('chat-list');
    if (chatList) {
        requestAnimationFrame(() => {
            chatList.scrollTop = chatList.scrollHeight;
        });
    }
    setInterval(reloadChatList, 5000);

    // フォームのAjax送信
    const form = document.getElementById('messageForm');
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = new FormData(form);
            fetch(form.action, {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (response.redirected) {
                        // サーバー側リダイレクト時はURLを更新
                        window.history.replaceState(null, '', response.url);
                    }
                    // 入力欄クリア
                    form.content.value = '';
                    reloadChatList();
                });
        });
    }
});

function reloadChatList() {
    const chatList = document.getElementById('chat-list');
    const url = window.location.pathname + '?fragment=true';
    fetch(url, { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
        .then(response => response.text())
        .then(html => {
            const temp = document.createElement('div');
            temp.innerHTML = html;
            const newChatList = temp.querySelector('#chat-list');
            if (newChatList && chatList) {
                chatList.innerHTML = newChatList.innerHTML;
                chatList.scrollTop = chatList.scrollHeight;
            }
        });
}