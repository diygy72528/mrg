
var Response_Status = {
    success:'0',
    fail:'1',
    exception:'2',
    accessdenied:'3'

};


String.prototype.startWith = function(s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
}
//todo 完成异常
$.ajaxSetup({
    complete:function(xhr,ts) {

        //异步ajax请求返回登陆页面时则session失效
        if(xhr.responseText != null && xhr.responseText.indexOf('LOGIN-PAGE')>0) {
            //登陆页面
            WebFn.alert('登陆超时，请重新登陆！','warning',function () {
                location.href = contextPath + 'login';
            })
        }
        if(!xhr.responseJSON)
            return
        var status = xhr.responseJSON.status;
        var msg = xhr.responseJSON.msg;
        switch (status) {
            case Response_Status.success:{
                //成功则不处理
                break;
            }
            case Response_Status.fail:{
                break;
            }
            case Response_Status.exception: {
                WebFn.alert(msg,"warning")
                break;
            };
            case Response_Status.accessdenied:{
                WebFn.alert(msg,"warning",window.parent.closeCurrentTab);
            }
            default:{
                break;
            }


        }

    }
})
