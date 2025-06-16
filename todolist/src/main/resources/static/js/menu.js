window.addEventListener("DOMContentLoaded", function () {
    const path = window.location.pathname;
    const nav = document.getElementById("hamburger-nav");
    const home = document.getElementById("home");
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
        if (path === "/todolist" && home) home.style.display = "none";
        if (path === "/todolist/finish" && completed) completed.style.display = "none";
        if (path === "/todolist/register" && register) register.style.display = "none";
        if (path === "/comet" && comet) comet.style.display = "none";
        if (path === "/login" && logout) logout.style.display = "none";
        if (path === "/nexus" && nexus) nexus.style.display = "none";
        if (path === "/setting" && setting) setting.style.display = "none";
    }
});