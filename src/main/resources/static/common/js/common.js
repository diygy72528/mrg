(function($) {
    $.extend({
        table:{
            refresh:function() {
                $('#table').bootstrapTable('refresh',{silent:true});
            },
            getSelections: function() {
            }
        },
        modal:{
            open:function(title,url,width,height,callback,cancel) {
                //如果是移动端，就使用自适应大小弹窗
                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.WebFn.isNull(title)) {
                    title = false;
                }
                if ($.WebFn.isNull(url)) {
                    url = "404";
                }
                if ($.WebFn.isNull(width)) {
                    width = 800;
                }
                if ($.WebFn.isNull(height)) {
                    height = ($(window).height() - 50);
                }
                if ($.WebFn.isNull(callback)) {
                    callback = function (index, layero) {
                        var body = top.layer.getChildFrame('body', index);
                        var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                        //文档地址
                        //调用提交方法
                        iframeWin.contentWindow.doSubmit();
                    }
                }
                if($.WebFn.isNull(cancel)) {
                    cancel = function(index) {
                        return  true;
                    }
                }
                layer.open({
                    type:2,
                    title:title,
                    content:url,
                    area: [width+'px', height+'px'],
                    fix:false,
                    shade:0.3,
                    maxmin:true,
                    yes:callback,
                    btn:['确定','取消'],
                    cancel:function(index) {
                        return true;
                    }
                });
            },
            alert:function(msg,icon,callback) {
                layer.alert(msg,{
                    icon: icon,
                    title: "系统提示",
                    btn:['确认']

                },callback)
            },
            confirm: function(msg,icon,callback) {

            },
            warning: function(msg) {
                layer.open({icon: 2,content:msg});

            },
            //打开遮罩层
            loading: function(message) {
                App.blockUI({
                    boxed: true,
                    message: message,
                    animate: false
                })
                //$.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            //关闭遮罩层
            closeLoading: function() {
                setTimeout(function () {
                    App.unblockUI();
                }, 50);
            }
        },
        operate:{
            submit: function(url, type, dataType, data, callback) {
                $.ajax({
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    success: function(result) {
                        if(!$.WebFn.isNull(result) && result.status == 0) {
                            if((typeof callback) == 'function') {
                                callback(result);
                            }
                            $.operate.ajaxSuccess(result.msg);
                        }else {
                            $.operate.ajaxFailed(!$.WebFn.isNull(result) && !$.WebFn.isNull(result.msg) ? result.msg : "操作失败！");
                        }
                    },
                    beforeSend: function() {
                        $.modal.loading('处理中，请稍后...');
                    }
                })

            },
            post: function(url,data,callback) {
                $.operate.submit(url, 'post', 'json', data, callback)
            },
            get: function(url,data,callback) {
                $.operate.submit(url, 'get', 'json', data, callback)
            },
            edit:function(id) {

            },
            add:function(id) {
                let url = $.table.options.addUrl.replace('{id}',id);
                $.modal.open("新增"+$.table.options.modalName,url);
            },
            addTab: function(id) {
                let tab_id = "_tab" + Math.random().toString(36).substring(2);
                let url = $.table.options.addUrl.replace('{id}',id);
                addTab({id:tab_id,title:'新增' + $.table.options.modalName,close:true,url:url,list_id: window.frameElement.getAttribute('id').substring(7, window.frameElement.getAttribute('id').length)})
            },
            editTab: function() {
            },
            ajaxSuccess: function(msg) {
                $.modal.alert(msg,1);
                $.modal.closeLoading();
            },
            ajaxFailed: function(msg) {
                $.modal.warning(msg);
                $.modal.closeLoading();
            }

        },
        WebFn:{

            // 通过id或带#id创建jq对象
            getJQuery : function(ns_el, dom_id) {
                if ($.WebFn.isNull(ns_el)) {
                    console.log("命名空间对象id为空")
                    return null;
                }
                var $obj = $("#" + $.WebFn.getDomId(ns_el));
                if (!$.WebFn.isNull(dom_id)) {
                    $obj = $obj.find('#' + $.WebFn.getDomId(dom_id));
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

        },
        tree:{
            init:function(options) {
                if($.WebFn.isNull(options.id)) {
                    console.log('id不能为空');
                    return false;
                }
                if($.WebFn.isNull(options.url)) {
                    console.log('url不能为空');
                    return false;
                }
                let treeForm = 'treeForm'+ Math.random().toString(36).substring(2);
                let keyWord = 'keyWord'+ Math.random().toString(36).substring(2);
                let fold = 'fold'+ Math.random().toString(36).substring(2);
                let unFold = 'unFold'+ Math.random().toString(36).substring(2);
                let $id = $('#'+options.id);
                let tool = {
                    search: function() {
                        let nodes = tool.tree.getNodes();
                        let word = $('#' + keyWord).val();
                        if(word === "") {
                            tool.showNodes(nodes);
                            return;
                        }
                        if(tool.lastSearch === word) {
                            //与上次搜索相同,不再搜索
                            return;
                        }
                        tool.hideAllNodes(nodes);
                        let nodesByParamFuzzy = tool.tree.getNodesByParamFuzzy('name',word);
                        tool.showNodes(nodesByParamFuzzy);

                    },
                    hideAllNodes: function(nodes) {
                        nodes = tool.tree.transformToArray(nodes);
                        for(var i=nodes.length-1; i>=0; i--) {
                            tool.tree.hideNode(nodes[i]);
                        }
                    },
                    confirm:function(index) {
                        let node = tool.tree.getSelectedNodes();
                        console.log(node[0].id)
                        console.log(node[0].name)
                        $('#' + options.name).val(node[0].id);
                        $('#' + options.id).val(node[0].name)
                        layer.close(index);

                    },
                    fold: function() {
                        tool.tree.expandAll(false);
                    },
                    unFold: function() {
                        tool.tree.expandAll(true);
                    },
                    showNodes:function(nodes) {
                        tool.tree.showNodes(nodes);
                        for(var i = nodes.length - 1; i>=0 ;i--) {
                            tool.tree.expandNode(nodes[i],true,false);
                            tool.showParent(nodes[i]);
                            tool.showChildren(nodes[i]);
                        }
                    },
                    showParent: function(node) {
                        let parentNode;
                        let treeObj = $.fn.zTree.getZTreeObj("tree");
                        if((parentNode = node.getParentNode()) != null) {
                            tool.tree.showNode(parentNode);
                            tool.tree.expandNode(parentNode,true,false);
                            tool.showParent(parentNode);
                        }
                    },
                    showChildren: function(node) {
                        if(node.isParent == false) {
                            return;
                        }
                        node = tool.tree.transformToArray(node);
                        for(let i = node.length - 1; i >= 0; i--) {
                            tool.tree.showNode(node[i]);
                            tool.tree.expandNode(node[i],true,false);
                        }
                    },
                    doInit:function() {
                        $id.after('<input id="' + options.name + '" name="'+options.name+'" type="hidden">');
                        $('body').before('<div id="'+treeForm+'" style="display: none">\n' +
                            '                    <div class="box-header">\n' +
                            '                        <label for="'+keyWord+'">关键字：</label><input id="'+keyWord+'" style="line-height: 24px; width:70%;border: 1px solid #bbb;padding: 0 4px;" maxlength="50" >' +
                            '                    </div>\n' +
                            '                    <div class="pull-right"><a id="'+unFold+'" href="javascript:void(0)"  class="btn btn-box-tool">展开</a>/<a id="'+fold+'" href="javascript:void(0)" class="btn btn-box-tool">折叠</a></div>\n' +
                            '                    <div>\n' +
                            '                        <ul id="tree" class="ztree"></ul>\n' +
                            '                    </div>\n' +
                            '                </div>');
                        $.post(options.url,function(data) {
                            let defaultOpts = {
                                expandLevel:0,
                                data: {
                                    simpleData: {
                                        enable: true
                                    }
                                },
                                callback: {
                                }

                            };
                            $.extend(defaultOpts,options.setting);
                            tool.tree = $.fn.zTree.init($('#tree'),defaultOpts,data);
                            function open() {
                                let width = '300px';
                                let height = '450px';
                                //如果是移动端，就使用自适应大小弹窗
                                if (navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i)) {
                                    width = 'auto';
                                    height = 'auto';
                                }
                                layer.open({
                                    type:1,
                                    title:options.title,
                                    offset: '50px',
                                    content: $('#'+treeForm),
                                    area: [width, height],
                                    shade:0.3,
                                    shadeClose:false,
                                    yes:tool.confirm,
                                    btn:['确定','取消'],
                                    cancel:function(index) {
                                        return true;
                                    }
                                })
                            }
                            $id.bind('click',open);

                        })
                        $('#'+fold).bind('click',tool.fold);
                        $('#'+unFold).bind('click',tool.unFold);
                        $('#'+keyWord).bind('keyup',tool.search);
                    }
                }
                tool.doInit();
            }
        }
    });
})(jQuery)
function mrgValid(formId,fields) {
    var valid_obj = this;
    if($.WebFn.isNull(formId)) {
        console.log("formId不能为空");
        return;
    }
    if($.WebFn.isNull(fields)) {
        console.log("验证字段不能为空");
        return;
    }
    var $form = $('#' + formId);
    $form.bootstrapValidator({
        message: '验证失败',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields:fields
    }).find('input[type="checkbox"], input[type="radio"]').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue'
    })
        // Called when the radios/checkboxes are changed
        .on('ifChanged', function(e) {
            // Get the field name
            var field = $(this).attr('name');
            $form.bootstrapValidator('revalidateField', field);
        });
    valid_obj.validator = $form.data('bootstrapValidator');
    return valid_obj;




}
function ns(el, fn){
    el=typeof(el)==='string' ? $(el) : el;
    return fn(el);
}
function mrgTable(options) {
    $.table.options = options;
    var tab_obj = this;
    if($.WebFn.isNull(options.id)) {
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







