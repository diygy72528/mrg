
var FAILED = '1';
var OK = '0';
var KAPTCHA = 'kaptcha';



/**
 * 刷新验证码
 */
function reloadCode() {
    //清楚样式
    $(".kaptcha").removeClass('has-success');
    //清空输入框
    $("#SESSION_IMAGE_CODE").val(null);
    //刷新校验码
    $("#kaptcha").attr("src", contextPath + "kaptcha?" + Math.random());
}
$(function() {
    $("#submit").click(function(e){
        e.preventDefault();
         //禁止提交表单
         //获取公钥
         var encrypt = new JSEncrypt();
         $.ajax({
             url: contextPath + "publicKey",
             type: "post",
             async: false,
             dataType: 'json',
             success: function (data) {
                 encrypt.setPublicKey(data.result);
                 //登录
                 $.ajax({
                     url: contextPath + "login",
                     type: "post",
                     async: false,
                     dataType: 'json',
                     data: {
                         "password": encrypt.encrypt($('#password').val()),
                         "username": $('#username').val(),
                         "SESSION_IMAGE_CODE": $('#SESSION_IMAGE_CODE').val(),
                     },
                     success: function (data) {
                         if (OK === data.status) {
                             location.href = '/index';
                         }
                         if (KAPTCHA === data.status) {
                             $("#msg").empty().append(data.msg);
                             //更新验证码
                             reloadCode();
                         }
                         if (FAILED === data.status) {
                             $("#msg").empty().append(data.msg);
                             reloadCode();
                             $(".username").removeClass('has-success');
                             $(".password").removeClass('has-success');
                             $("#password").val(null);
                             $("#username").val(null);
                         }
                     }
                 });
             }
         });
    });
})
