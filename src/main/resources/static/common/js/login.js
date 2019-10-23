$(function() {
    ns("#LOGIN-PAGE", function ($outer) {

        var FAILED = '1';
        var OK = '0';
        var KAPTCHA = 'kaptcha';
        var REDIRECT = 'redirect';


        /**
         * 验证添加
         */
        function addValid() {
            var fields = {
                username:{
                    message: '用户名验证失败',
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '用户名长度必须在6到18位之间'
                        }
                    }
                },
                password:{
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '密码只能包含大写、小写、数字和下划线'
                        }
                    }
                },
                SESSION_IMAGE_CODE:{
                    validators: {
                        notEmpty: {
                            message: '验证码不能为空'
                        }
                    }
                }
            };
            return new mrgValid("LOGIN-PAGE","form",fields);
        }
        var valid_obj = addValid();


        /**
         * 刷新验证码
         */
        function reloadCode() {
            //清楚样式
            $outer.find(".kaptcha").removeClass('has-success');
            //清空输入框
            $outer.find("#SESSION_IMAGE_CODE").val(null);
            //刷新校验码
            $outer.find("#kaptcha").attr("src", contextPath + "kaptcha?" + Math.random());
        }

        /**
         * 验证码图片点击事件添加
         */
        $outer.find('#kaptcha').click(function() {
            reloadCode();
        })

        /**
         * 提交点击事件添加
         */
        $outer.find("#submit").click(function (e) {
            e.preventDefault();
            if(!valid_obj.validator.validate().isValid()) {
                alert("请完善表单信息");
                return;
            }
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
                            "password": encrypt.encrypt($outer.find('#password').val()),
                            "username": $outer.find('#username').val(),
                            "SESSION_IMAGE_CODE": $outer.find('#SESSION_IMAGE_CODE').val(),
                            "remember-me": $outer.find('#rememberme').val()
                        },
                        success: function (data) {
                            if (OK === data.status) {
                                location.href = '/index';
                            }

                            if (REDIRECT === data.status) {
                                console.log(data.msg);
                                location.href = data.msg;
                            }

                            if (KAPTCHA === data.status) {
                                $outer.find("#msg").empty().append(data.msg);
                                //更新验证码
                                reloadCode();
                            }
                            if (FAILED === data.status) {
                                $outer.find("#msg").empty().append(data.msg);
                                reloadCode();
                                $outer.find(".username").removeClass('has-success');
                                $outer.find(".password").removeClass('has-success');
                                $outer.find("#password").val(null);
                                $outer.find("#username").val(null);
                            }
                        }
                    });
                }
            });
        });
    })
})
