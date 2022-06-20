$(function ($) {
    let url = window.location.href;
    $('nav a').each(function () {
        if (this.href === url) {
            $(this).closest('a').addClass('active');
        }
    });
});