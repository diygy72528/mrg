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
    table:{},
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
    open:function(title,url,width,height,callback) {
        //如果是移动端，就使用自适应大小弹窗
        if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
            width = 'auto';
            height = 'auto';
        }
        if (WebFn.isNull(title)) {
            title = false;
        }
        if (WebFn.isNull(url)) {
            url = "404";
        }
        if (WebFn.isNull(width)) {
            width = 800;
        }
        if (WebFn.isNull(height)) {
            height = ($(window).height() - 50);
        }
        if (WebFn.isNull(callback)) {
            callback = function (index, layero) {
                var body = top.layer.getChildFrame('body', index);
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                //文档地址
                //调用提交方法
                iframeWin.contentWindow.doSubmit();
            }
        }
        layer.open({
            type:2,
            title:title,
            content:url,
            area: [width+'px', height+'px'],
            fixed:false,
            shade:0.3,
            maxmin:true,
            yes:callback,
            btn:['确定','取消']
        });
    },
    alert:function(msg,icon,callback) {
        layer.alert(msg,{
            icon: icon,
            title: "系统提示",
            btn:['确认']

        },callback)
    },
    edit:function() {

    },
    add:function() {
        WebFn.open("新增"+WebFn.table.options.modalName,WebFn.table.addUrl)
    }
}

function ns(el, fn){
    el=typeof(el)==='string' ? $(el) : el;
    return fn(el);
}


function mrgTable(options) {
    WebFn.table.options = options;
    var tab_obj = this;
    if(WebFn.isNull(options.id)) {
        console.log('tableid不能为空');
    }
    tab_obj.queryParams = function(params) {
        var result = {
            size:params.pageSize,
            current:params.pageNumber,
            searchText:params.searchText,
            sortName:params.sortName,
            sortOrder:params.sortOrder
        };
        $.extend(result,options.table.customParams);
        return result;
    }
    var defaultOptions = {
        //url:options.table.url,//服务端数据源
        method:'post',//请求方式
        locale:'zh-CN',//汉化
        //columns:options.table.columns,//列
        pagination:true,//展示分页
        contentType: "application/x-www-form-urlencoded",//此种格式对应post请求数据
        sidePagination:'server',//服务端分页
        queryParams:tab_obj.queryParams,
        showRefresh:true,//展示刷新按钮
        showColumns:true,//展示列菜单
        showToggle:true,//展示列表切换按钮（卡片：表单）
        cache:false,//不缓存
        //search:true,//展示搜索框
        //showSearchButton:true,//展示搜索按钮
        //checkBoxHeader:true,//展示全选checkbox
        //showButtonText:true,//展示按钮文本
        clickToSelect:true,//点击行以选中
        //toolbar:options.toolbar,//自定义工具栏
        queryParamsType:'',//传输{"searchText":"","sortOrder":"asc","pageSize":10,"pageNumber":1,"sortName":"name","sortOrder":"desc"}
        onLoadSuccess: function (res) {
            $table.treegrid({
                initialState: false,         //不展开
                treeColumn: 1,
                expanderExpandedClass: 'treegrid-expander glyphicon glyphicon-chevron-down',
                expanderCollapsedClass: 'treegrid-expander glyphicon glyphicon-chevron-right',
                onChange: function () {
                    $table.bootstrapTable('resetWidth');
                }
            })
        },
    };
    $.extend(defaultOptions,options.table);
    var $table = $('#'+options.id);
    $table.bootstrapTable(defaultOptions);

    //初始化工具栏
    function initTableButtons() {
        $table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table load-success.bs.table',function() {
            //初始化工具栏按钮
            let selections = $table.bootstrapTable('getSelections');
            //禁用单个选项按钮
            $('.single').toggleClass('disabled',selections.length!=1);
            //禁用多个选项按钮
            $('.multiple').toggleClass('disabled',selections.length<=0);
        })
    }
    initTableButtons();
    return $table;

}







