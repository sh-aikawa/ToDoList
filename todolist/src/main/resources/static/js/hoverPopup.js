
document.addEventListener('DOMContentLoaded', () => {
    const titles = document.querySelectorAll('.todo-titles');
    const globalPopupContent = document.getElementById('global-popup-content');
    let hoverTimeout;

    // ポップアップを隠す関数
    const hidePopup = () => {
        globalPopupContent.style.display = 'none';
    };

    // ポップアップを表示・位置調整する関数
    const showPopup = (hoveredTitle) => {
        globalPopupContent.textContent = hoveredTitle.dataset.description;
        const hoveredTitleRect = hoveredTitle.getBoundingClientRect();

        // 一旦表示してサイズを取得
        globalPopupContent.style.visibility = 'hidden';
        globalPopupContent.style.display = 'block';

        const popupWidth = globalPopupContent.offsetWidth;
        const popupHeight = globalPopupContent.offsetHeight;

        // ポップアップをホバー要素の下に配置
        let top = hoveredTitleRect.bottom + 10; // ホバー要素の下端から10px下に配置

        // ポップアップをホバー要素の中央に配置
        let left = hoveredTitleRect.left + (hoveredTitleRect.width / 2) - (popupWidth / 2);

        // 画面右端からはみ出す場合の調整
        if (left + popupWidth > window.innerWidth - 20) {
            left = window.innerWidth - popupWidth - 20; // 画面右端に合わせる
            if (left < 10) { // それでもはみ出す場合は左端に
                left = 10;
            }
        }

        // 画面左端からはみ出す場合の調整
        if (left < 10) {
            left = 10;
        }

        // 画面下端からはみ出す場合の調整
        if (top + popupHeight > window.innerHeight - 20) {
            top = hoveredTitleRect.top - popupHeight - 10; // ホバー要素の上に表示（上端から10px上）
            // それでもはみ出す場合は画面上端に
            if (top < 10) {
                top = 10;
            }
        }

        globalPopupContent.style.left = `${left + window.scrollX}px`;
        globalPopupContent.style.top = `${top + window.scrollY}px`;

        globalPopupContent.style.visibility = 'visible';
    };

    titles.forEach(hoveredTitle => {
        hoveredTitle.addEventListener('mouseenter', () => {
            hoverTimeout = setTimeout(() => showPopup(hoveredTitle), 300);
        });

        hoveredTitle.addEventListener('mouseleave', () => {
            clearTimeout(hoverTimeout);
            hidePopup();
        });
    });

    globalPopupContent.addEventListener('mouseenter', () => clearTimeout(hoverTimeout));
    globalPopupContent.addEventListener('mouseleave', hidePopup);
});