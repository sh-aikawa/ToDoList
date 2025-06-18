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

document.addEventListener('DOMContentLoaded', function () {
    const timeline = document.getElementsByClassName('timeline-container');

    setInterval(reloadTimeline, 5000);

});

function reloadTimeline() {
    const timeline = document.getElementsByClassName('timeline-container')[0];
    const url = window.location.pathname + '?fragment=true';
    fetch(url, { headers: { 'X-Requested-With': 'XMLHttpRequest' } })
        .then(response => response.text())
        .then(html => {
            const temp = document.createElement('div');
            temp.innerHTML = html;
            const newTimeline = temp.querySelector('.timeline-container');
            if (newTimeline && timeline) {
                timeline.innerHTML = newTimeline.innerHTML;
            }
        });
}