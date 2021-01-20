String.prototype.startWith = function(s) {
    if (s == null || s == "" || this.length == 0 || s.length > this.length)
        return false;
    if (this.substr(0, s.length) == s)
        return true;
    else
        return false;
    return true;
}
$.ajaxSetup({
    complete:function(xhr,ts) {

        //异步ajax请求返回登陆页面时则session失效
        if(xhr.responseText != null && xhr.responseText.indexOf('LOGIN-PAGE')>0) {
            //登陆页面
            $.modal.alert('登陆超时，请重新登陆！',modal_status.warining,function () {
                location.href = contextPath + 'login';
            })
        }
        if(!xhr.responseJSON)
            return
        /*var status = xhr.responseJSON.status;
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
                $.modal.alert(msg,modal_status.warining)
                break;
            }
            case Response_Status.accessdenied:{
                $.modal.alert(msg,modal_status.warining,window.parent.closeCurrentTab);
                break;
            }
            case Response_Status.warn:{
                $.modal.alert(msg,modal_status.warining)
                break;
            }
            default:{
                break;
            }


        }*/

    }
})
