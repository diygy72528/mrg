function mrgValid(outer_id,inner_id,fields) {
    var valid_obj = this;
    if(WebFn.isNull(outer_id)) {
        console.log("outer_id不能为空");
        return;
    }
    if(WebFn.isNull(inner_id)) {
        console.log("inner_id不能为空");
        return;
    }
    if(WebFn.isNull(fields)) {
        console.log("验证字段不能为空");
        return;
    }
    var $form = WebFn.getJQuery(outer_id,inner_id);
    $form.bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:fields
    });
    valid_obj.validator = $form.data('bootstrapValidator');




}
WebFn = {
    // 通过id或带#id创建jq对象
    getJQuery : function(ns_el, dom_id) {
        if (WebFn.isNull(ns_el)) {
            console.log("命名空间对象id为空")
            return null;
        }
        var $obj = $("#" + WebFn.getDomId(ns_el));
        if (!WebFn.isNull(dom_id)) {
            $obj = $obj.find('#' + WebFn.getDomId(dom_id));
        }
        return $obj;
    },
    getDomId : function(domId) {
        // domId 是"#"+ 和常规字符串 获取纯粹的字符串
        var result = "";
        var a = "abc";
        if (!domId.startWith("#")) {
            result = domId
        } else {
            result = domId.substr(1, domId.length)
        }
        return result;
    },
    /**
     *
     * @param in_str
     * @returns {Boolean}
     */
    isNull : function(in_str) {
        // NULL
        if (in_str == null) {
            return true;
        }
        // 'NULL'
        if (in_str == 'null') {
            return true;
        }
        // 'undefined'
        if (in_str == 'undefined') {
            return true;
        }
        // undefined
        if (typeof (in_str) === "undefined") {
            return true;
        }
        // " "
        if ((in_str + "").replace(/(^s*)|(s*$)/g, "").length == 0) {
            return true;
        }
        return false;
    },
}

function ns(el, fn){
    el=typeof(el)==='string' ? $(el) : el;
    return fn(el);
}


function mrgTable(options) {
    if(WebFn.isNull(options.id)) {
        console.log('tableid不能为空');
    }
    var defaultOptions = {
        url:options.url,//服务端数据源
        method:'post',//请求方式
        contentType:'application/json',//请求格式
        locale:'zh-CN',//汉化
        columns:options.columns,//列
        pagination:true,//展示分页
        sidePagination:'server',//服务端分页
        queryParams:options.queryParams,
        cache:false,//不缓存
        search:true,//展示搜索框
        showSearchButton:true,//展示搜索按钮
        showButtonText:true,//展示按钮文本
        clickToSelect:true,//点击行以选中
        toolbar:options.toolbar,//自定义工具栏
        queryParamsType:''//传输{"searchText":"","sortOrder":"asc","pageSize":10,"pageNumber":1}
    };
    $.extend(defaultOptions,options.table);
    var $table = $('#'+options.id);
    $table.bootstrapTable(defaultOptions)
}



