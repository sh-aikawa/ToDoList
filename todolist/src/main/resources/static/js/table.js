window.addEventListener("DOMContentLoaded", function () {
    let isDescending = localStorage.getItem('isDescending') === 'true';//ソート用
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

