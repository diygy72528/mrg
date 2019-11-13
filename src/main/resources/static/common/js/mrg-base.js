
var Response_Status = {
    exception:'3',
    accessdenied:'4'

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

//todo:完成异常
$.ajaxSetup({
    complete:function(xhr,ts) {
        var status = xhr.responseJSON.status;
        var msg = xhr.responseJSON.msg;
        switch (status) {


        }

    }
})
