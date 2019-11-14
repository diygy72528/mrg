
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
        console.log(xhr)
        var status = xhr.responseJSON.status;
        var msg = xhr.responseJSON.msg;
        switch (status) {
            case Response_Status.success:{
                break;
            }
            case Response_Status.fail:{
                break;
            }
            case Response_Status.exception: {
                WebFn.alert(msg,"warning")
                break;
            };
            case Response_Status.accessdenied: {
                break;
            };
            default:{
                break;
            }


        }

    }
})
