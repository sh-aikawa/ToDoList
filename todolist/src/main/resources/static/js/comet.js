document.addEventListener("DOMContentLoaded", function () {
        const textarea = document.getElementById("content");
        const counter = document.getElementById("charCount");
        const maxLength = 280;

        const updateCount = () => {
            const currentLength = textarea.value.length;
            counter.textContent = `${currentLength} / ${maxLength}`;
            counter.style.color = currentLength > maxLength ? 'red' : '';
        };

        textarea.addEventListener("input", updateCount);
        updateCount(); // 初期表示用
    });