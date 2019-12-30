$(function() {
    //图标选择初始化
    initIcon();
})

function initIcon() {
    //图标选择展示
    $('input[name="icon"]').click(function() {
        $(".icon-drop").show();
    });

    //确定图标
    $('#confirm').click(function () {
        $('.icon-drop').hide();
        let iClass = $('#icon').val();
        $('input[name="icon"]').val(iClass);
        $('#icon-preview').attr('class',iClass);
    });

    //取消图标选择
    $('#cancel').click(function () {
        $('.icon-drop').hide();
    });

    //图标选中显示
    $('.total-icons .fa').click(function() {
        $('#icon').val($(this).attr('class'));
        $('.total-icons').find('i').removeAttr('style');
        $(this).css({"background-color": "#4F8EF7", "color": "#ffffff", "cursor": "pointer"});
    });

    //双击图标事件
    $('.total-icons .fa').dblclick(function() {
        $('#confirm').click();
    });
}
