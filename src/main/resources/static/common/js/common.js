//弹窗icon状态
var modal_status = {
    ask: 'ask',
    warining: 'warning',
    success: 'success',
    failed: 'failed'
}

var Response_Status = {
    success:'0',
    fail:'1',
    exception:'2',
    accessdenied:'3',
    warn:'6'

};

(function($) {
    $.extend({
        table:{
            refresh:function() {
                $('#table').bootstrapTable('refresh',{silent:true});
            },
            getSelection: function() {
                var selection = $('#table').bootstrapTable('getSelections');
                if(selection.length == 1) {
                    return selection[0].id;
                }
                return null;
            },
            getSelectedIds: function(column) {
                var selections = $('#table').bootstrapTable('getSelections'),ids=new Array();
                $.map(selections,function (r) {
                    return ids.push(r[column]);
                })
                return ids;
            }
        },
        modal:{
            icon: function(type) {
                switch(type) {
                    case 'warning' :
                        return 0;
                    case 'success' :
                        return 1;
                    case 'failed' :
                        return 2;
                    case 'ask':
                        return 3;
                    default :
                        return -1;
                }
            },
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
                    icon: $.modal.icon(icon),
                    title: "系统提示",
                    btn:['确认']

                },callback)
            },
            confirm: function(msg,callback) {
                layer.confirm(msg,{
                    icon: $.modal.icon(modal_status.ask),
                    title: '提示'
                },function (index) {
                    layer.close(index);
                    callback(true)
                })
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
            },
            // 关闭窗体
            close: function () {
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            },
            msg: function(msg,icon) {
                layer.msg(msg,{
                    icon:$.modal.icon(icon)
                })
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
                        if((typeof callback) == 'function' && result.status == Response_Status.success) {
                            callback(result);
                        }
                        $.operate.ajaxSuccess(result);
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
                var url = $.table.options.editUrl.replace('{id}',id?id:$.table.getSelection());
                $.modal.open("修改"+$.table.options.modalName,url);
            },
            editTab: function(id) {
                var tab_id = "_tab" + Math.random().toString(36).substring(2);
                if($.WebFn.isNull(id)) {
                    id = $.table.getSelection();
                    if(id == null) {
                        $.modal.alert('请勾选单个选项',modal_status.warining);
                        return;
                    }
                }
                var url = $.table.options.editUrl.replace('{id}',id);
                addTab({id:tab_id,title:'修改' + $.table.options.modalName,close:true,url:url,list_id: window.frameElement.getAttribute('id').substring(7, window.frameElement.getAttribute('id').length)})
            },
            add:function() {
                var url = $.table.options.addUrl.replace('{id}','');
                $.modal.open("新增"+$.table.options.modalName,url);
            },
            addTab: function(id) {
                var tab_id = "_tab" + Math.random().toString(36).substring(2);
                var url = $.WebFn.isNull(id)?$.table.options.addUrl.replace('{id}',''):$.table.options.addUrl.replace('{id}',id);
                addTab({id:tab_id,title:'新增' + $.table.options.modalName,close:true,url:url,list_id: window.frameElement.getAttribute('id').substring(7, window.frameElement.getAttribute('id').length)});
            },
            openTab:function(url,id,options){
                var tab_id = id||"_tab" + Math.random().toString(36).substring(2);
                if(!url){
                    $.modal.warining(modal_status.warining,'url不存在');
                }
                addTab({id:tab_id,title:options.title,close:true,url:url,list_id:window.frameElement.getAttribute('id').substring(7,window.frameElement.getAttribute('id').length)});
            },
            remove: function(id) {
                id = id?id:$.table.getSelection();
                if(id) {
                    $.modal.confirm("确定删除选中的1条数据吗？",function() {
                        var data = {id:id};
                        $.operate.post($.table.options.deleteUrl,data,function (result) {
                            //刷新当前页面
                            parent.refreshTable();
                        })
                    })
                }else {
                    $.modal.alert("请选择一条记录",modal_status.warining);
                }
            },
            batRemove: function() {
                var ids = $.table.getSelectedIds('id');
                if(ids.length == 0) {
                    $.modal.alert("请至少选择一条记录",modal_status.warining);
                    return;
                }
                $.modal.confirm("确定删除选中的"+ids.length+"条数据吗？",function(index) {
                    var data = {ids:ids.join(",")};
                    $.operate.post($.table.options.deleteUrl,data,function (result) {
                        //刷新当前页面
                        parent.refreshTable();
                    })
                });

            },
            ajaxSuccess: function(result) {
                if(result.status == Response_Status.success) {
                    $.modal.msg(result.msg,modal_status.success);
                }else if((result.status == Response_Status.fail)||(result.status == Response_Status.exception)) {
                    $.modal.alert(result.msg,modal_status.failed);
                }else if(result.status == Response_Status.warn) {
                    $.modal.alert(result.msg,modal_status.warining)
                }

                $.modal.closeLoading();
            },
            successCallback: function(result) {
                if(result.status == Response_Status.success) {
                    $.modal.msg(result.msg,modal_status.success);
                    parent.$.table.refresh();
                    $.modal.close();
                }else {
                    $.modal.alert(result.msg,modal_status.warining);
                }
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
        //此处只能生成一棵树，不能在同一个页面用此方法生成两棵树。否则涉及到方法会冲突。
        tree:{
            initSearchTree: function(options) {
                var treeForm = 'treeForm'+ Math.random().toString(36).substring(2);
                var keyWord = 'keyWord'+ Math.random().toString(36).substring(2);
                var fold = 'fold'+ Math.random().toString(36).substring(2);
                var unFold = 'unFold'+ Math.random().toString(36).substring(2);
                var $id = $('#'+options.name);
                $id.after('<input id="' + options.id + '" name="'+options.id+'" type="hidden">');
                $('body').before('<div id="'+treeForm+'" style="display: none">\n' +
                    '                    <div class="box-header">\n' +
                    '                        <label for="'+keyWord+'">关键字：</label><input id="'+keyWord+'" style="line-height: 24px; width:70%;border: 1px solid #bbb;padding: 0 4px;" maxlength="50" >' +
                    '                    </div>\n' +
                    '                    <div class="pull-right"><a id="'+unFold+'" href="javascript:void(0)"  class="btn btn-box-tool">展开</a>/<a id="'+fold+'" href="javascript:void(0)" class="btn btn-box-tool">折叠</a></div>\n' +
                    '                    <div>\n' +
                    '                        <ul id="tree" class="ztree"></ul>\n' +
                    '                    </div>\n' +
                    '                </div>');
                options.treeId = 'tree';
                function afterInit() {
                    function open() {
                        var width = '300px';
                        var height = '450px';
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
                            yes:$.tree.confirm,
                            btn:['确定','取消'],
                            cancel:function(index) {
                                return true;
                            }
                        })
                    }
                    $('#'+fold).bind('click',$.tree.fold);
                    $('#'+unFold).bind('click',$.tree.unFold);
                    $('#'+keyWord).bind('keyup',{key:keyWord},$.tree.search);
                    $('#' + options.name).bind('click',open);
                }
                $.tree.init(options,null,afterInit);

            },

            init:function(options,beforeInit,afterInit) {
                if($.WebFn.isNull(options.treeId)) {
                    console.log('treeId不能为空');
                    return false;
                }
                if($.WebFn.isNull(options.url)) {
                    console.log('url不能为空');
                    return false;
                }
                var defaultOpts = {
                    treeId: "tree",                    // 属性ID
                    name: '',
                    echoCheckedIds: '',
                    expandLevel: 0,                // 展开等级节点
                    view: {
                        selectedMulti: false,      // 设置是否允许同时选中多个节点
                        nameIsHTML: true           // 设置 name 属性是否支持 HTML 脚本
                    },
                    check: {
                        enable: false,             // 置 zTree 的节点上是否显示 checkbox / radio
                    },
                    data: {
                        key: {
                            title: "title"         // 节点数据保存节点提示信息的属性名称
                        },
                        simpleData: {
                            enable: true           // true / false 分别表示 使用 / 不使用 简单数据模式
                        }
                    }
                };
                $.extend(defaultOpts,options.setting);
                $.post(options.url,function(data) {
                    $.tree._tree = $.fn.zTree.init($('#' + options.treeId),defaultOpts,data);
                    $.tree._tree.id = options.id;
                    $.tree._tree.name = options.name;
                    if(typeof afterInit === 'function') {
                        afterInit();
                    }
                    options.name&&$.tree.echoValueById($('#' + options.name).val());
                    options.echoCheckedIds&&$.tree.echoChecked(options.echoCheckedIds);
                })
            },
            search: function(event) {
                var nodes = $.tree._tree.getNodes();
                var word = $('#' + event.data.key).val()
                if(word === "") {
                    $.tree.showNodes(nodes);
                    return;
                }
                if($.tree.lastSearch === word) {
                    //与上次搜索相同,不再搜索
                    return;
                }
                $.tree.hideAllNodes(nodes);
                var nodesByParamFuzzy = $.tree._tree.getNodesByParamFuzzy('name',word);
                $.tree.showNodes(nodesByParamFuzzy);

            },
            hideAllNodes: function(nodes) {
                nodes = $.tree._tree.transformToArray(nodes);
                for(var i=nodes.length-1; i>=0; i--) {
                    $.tree._tree.hideNode(nodes[i]);
                }
            },
            confirm:function(index) {
                var node = $.tree._tree.getSelectedNodes();
                $('#' + $.tree._tree.id).val(node[0].id);
                $.tree._tree.name&&$('#' + $.tree._tree.name).val(node[0].name);
                layer.close(index);

            },
            fold: function() {
                $.tree._tree.expandAll(false);
            },
            unFold: function() {
                $.tree._tree.expandAll(true);
            },
            showNodes:function(nodes) {
                $.tree._tree.showNodes(nodes);
                for(var i = nodes.length - 1; i>=0 ;i--) {
                    $.tree._tree.expandNode(nodes[i],true,false);
                    $.tree.showParent(nodes[i]);
                    $.tree.showChildren(nodes[i]);
                }
            },
            showParent: function(node) {
                var parentNode;
                var treeObj = $.fn.zTree.getZTreeObj("tree");
                if((parentNode = node.getParentNode()) != null) {
                    $.tree._tree.showNode(parentNode);
                    $.tree._tree.expandNode(parentNode,true,false);
                    $.tree.showParent(parentNode);
                }
            },
            showChildren: function(node) {
                if(node.isParent == false) {
                    return;
                }
                node = $.tree._tree.transformToArray(node);
                for(var i = node.length - 1; i >= 0; i--) {
                    $.tree._tree.showNode(node[i]);
                    $.tree._tree.expandNode(node[i],true,false);
                }
            },
            echoValueById: function(id) {
                var node = $.tree._tree.getNodeByParam('id',id);
                if(node){
                    $.tree._tree.selectNode(node);
                     $.tree._tree.name&&$('#' + $.tree._tree.name).val(node.name);
                    $('#' + $.tree._tree.id).val(node.id);
                }
            },
            echoChecked: function(ids) {
                var idArr = ids.split(',');
                $.each(idArr,function(i,v) {
                    var node = $.tree._tree.getNodesByParam('id',v);
                    $.tree._tree.checkNode(node[0],true,false);
                    $.tree._tree.expandNode(node[0],true,false);
                })
            }
        }
    });
})(jQuery)
function mrgValid(formId,fields,icheck) {
    var valid_obj = this;
    if($.WebFn.isNull(formId)) {
        console.log("formId不能为空");
        return;
    }
    if($.WebFn.isNull(fields)) {
        console.log("验证字段不能为空");
        return;
    }
    valid_obj.init = function() {
        var $form = $('#' + formId);
        $form.bootstrapValidator({
            message: '验证失败',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields:fields
        });
        icheck&&$form.data('bootstrapValidator').find('input[type="checkbox"], input[type="radio"]').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue'
        })
            // Called when the radios/checkboxes are changed
            .on('ifChanged', function(e) {
                // Get the field name
                var field = $(this).attr('name');
                field&&$form.bootstrapValidator('revalidateField', field);
            });
        valid_obj.validator = $form.data('bootstrapValidator');
    }
    valid_obj.validate = function() {
        return valid_obj.validator.validate().isValid();
    }
    valid_obj.init();
    return valid_obj;




}
function ns(el, fn){
    el=typeof(el)==='string' ? $(el) : el;
    return fn(el);
}
function mrgTable(options) {
    if(!$.table.hasOwnProperty('options')) {
        $.table.options = options;
    }
    var tab_obj = this;
    if($.WebFn.isNull(options.id)) {
        console.log('tableid不能为空');
    }
    //初始化输入参数
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
    //初始化工具栏
    tab_obj.initTableButtons = function() {
        tab_obj._table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table load-success.bs.table',function() {
            //初始化工具栏按钮
            var selections = tab_obj._table.bootstrapTable('getSelections');
            //禁用单个选项按钮
            tab_obj._table.parent().parent().prev('.fixed-table-toolbar:first').find('.single').toggleClass('disabled',selections.length!=1);
            //禁用多个选项按钮
            tab_obj._table.parent().parent().prev('.fixed-table-toolbar:first').find('.multiple').toggleClass('disabled',selections.length<=0);
        })
    };
    //获取已选择行id
    tab_obj.getSelection = function() {
        var selection = tab_obj._table.bootstrapTable('getSelections');
        if(selection.length == 1) {
            return selection[0].id;
        }
        return null;
    };
    tab_obj.getSelectedIds = function() {
        var selections = tab_obj._table.bootstrapTable('getSelections');
        return $.map(selections,function (r) {
            return r.id;
        })
    };
    tab_obj.getAllIds = function() {
        var all = tab_obj._table.bootstrapTable('getData');
        return $.map(all,function(v) {
            return v.id;
        })
    };
    tab_obj.appendRow = function(row){
        tab_obj._table.bootstrapTable('append',row);
    };
    tab_obj.removeById = function(id) {
        tab_obj._table.bootstrapTable('removeByUniqueId', id);
    }
    tab_obj.reload = function() {
        tab_obj._table.bootstrapTable('destroy');
        console.log(defaultOptions);
        tab_obj._table.bootstrapTable(defaultOptions);
    }

    var defaultOptions = {
        uniqueId:'id',//唯一id
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
        //height:'760',
        //search:true,//展示搜索框
        //showSearchButton:true,//展示搜索按钮
        //checkBoxHeader:true,//展示全选checkbox
        //showButtonText:true,//展示按钮文本
        clickToSelect:true,//点击行以选中
        //toolbar:options.toolbar,//自定义工具栏
        queryParamsType:'',//传输{"searchText":"","sortOrder":"asc","pageSize":10,"pageNumber":1,"sortName":"name","sortOrder":"desc"}
        onLoadSuccess: function (res) {
            tab_obj._table.treegrid({
                initialState: false,         //不展开
                treeColumn: options.treeColumn,
                expanderExpandedClass: 'treegrid-expander glyphicon glyphicon-chevron-down',
                expanderCollapsedClass: 'treegrid-expander glyphicon glyphicon-chevron-right',
                onChange: function () {
                    tab_obj._table.bootstrapTable('resetWidth');
                }
            })
        },
    };
    $.extend(defaultOptions,options.table);
    tab_obj._table = $('#'+options.id);
    tab_obj._table.bootstrapTable(defaultOptions);


    tab_obj.initTableButtons();
}



function multiUserSelector() {
    var options = {
        title:"选择用户",
        sourceTableOptions: {
            //tableid
            id:'sourceTable',
            //bootstrap-table options
            table:{
                url: contextPath+"user/list",
                pagination:true,
                showRefresh:false,
                showToggle:false,
                showColumns:false,
                queryParams:function(params) {
                    var result = {
                        size:params.pageSize,
                        current:params.pageNumber,
                        searchText:params.searchText,
                        sortName:params.sortName,
                        sortOrder:params.sortOrder
                    };
                    var customParams = {
                        roleId:$('#roleId').val(),
                        userName:$('#multiSelect #name').val(),
                        mobile:$('#multiSelect #mobile').val()
                    }
                    $.extend(result,customParams);
                    return result;
                },
                columns:[{
                    checkbox:true
                },{
                    title:'id',
                    field:'id',
                    visible:false
                },{
                    title:'用户名',
                    field:'userName',
                    sortable:true,
                    sortName:'userName',
                    order:'asc'
                },{
                    title:'账号',
                    field:'account',
                    sortable:true,
                    sortName:'account',
                    order:'asc'
                },{
                    title:'手机',
                    field:'mobile'
                },{
                    title:'邮件',
                    field:'email'
                },{
                    title:'登陆次数',
                    field:'loginCount'
                },{
                    title:'备注',
                    field:'remark'
                }
                ]
            }
        },
        targetTableOptions:{

            //tableid
            id:'targetTable',
            //bootstrap-table options
            table:{
                toolbar:'#targetToolbar',
                showRefresh:false,
                showToggle:false,
                showColumns:false,
                height:$(window).height()-150,
                url: contextPath+"user/listByRoleId",
                pagination:false,
                customParams:{
                  roleId:$('#roleId').val()
                },
                columns:[{
                    checkbox:true
                },{
                    title:'id',
                    field:'id',
                    visible:false
                },{
                    title:'用户名',
                    field:'userName',
                    sortable:true,
                    sortName:'userName',
                    order:'asc'
                },{
                    title:'账号',
                    field:'account',
                    sortable:true,
                    sortName:'account',
                    order:'asc'
                },{
                    title:'手机',
                    field:'mobile'
                },{
                    title:'邮件',
                    field:'email'
                },{
                    title:'登陆次数',
                    field:'loginCount'
                },{
                    title:'备注',
                    field:'remark'
                }
                ]
            },
        },
        submitUrl:contextPath + 'role/addRoleUserRela',
        searchStr :'                    <div class="col-sm-5">\n' +
            '                        <div class="form-group">\n' +
            '                        <lable class="control-label">姓名：</lable><input type="text" class="form-control" id="name" value="">\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="col-sm-5">\n' +
            '                        <div class="form-group">\n' +
            '                        <label class="control-label">手机号：</label><input type="text" class="form-control" id="mobile"\n' +
            '                                   value="">\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                    <div class="col-xs-2">\n' +
            '                        <div class="btn btn-success btn-sm" id="search" style="margin-top: 15px">搜索</div>\n' +
            '                    </div>\n'
    }
    var selector = new multiSelector(options);
    selector.init();
}

function multiSelector(options) {
    var _ = this;
    _.init = function() {
        if($.WebFn.isNull(options.submitUrl)) {
            console.log('submitUrl不能为空');
        }
        var queryComponents = $('#multiSelector');

        function submitHandler(index) {
            var data = {
                roleId: $('#roleId').val(),
                userIds: targetTable.getAllIds().join()
            }
            console.log(data);
            $.post(options.submitUrl,data,function(result) {
                if(result.status == Response_Status.success) {
                    $.modal.msg(result.msg,modal_status.success);
                    $.table.refresh();
                    layer.close(index);
                }else {
                    $.modal.alert(result.msg,modal_status.warining);
                }
            })
        }
        var contentStr = '<div id="multiSelect" class="col-xs-12">\n' +
            '                <div class="col-xs-6">\n' + options.searchStr +
            '                    <table id="sourceTable" class="table table-bordered table-striped"\n' +
            '                           data-mobile-responsive="true"></table>\n' +
            '                </div>\n' +
            '                <div class="col-xs-6">\n' +
            '                    <div id="targetToolbar" class="btn btn-danger btn-xs multiple">删除</div>\n' +
            '                    <table id="targetTable" class="table table-bordered table-striped"\n' +
            '                           data-mobile-responsive="true"></table>\n' +
            '                </div>\n' +
            '            </div>';


        var index = layer.open({
            type:1,
            title:options.title,
            content: contentStr,
            area: ['800px', ($(window).height() - 50)+'px'],
            fix:false,
            shade:0.3,
            maxmin:true,
            yes:submitHandler,
            btn:['确定','取消'],
            cancel:function(index) {
                return true;
            }
        });
        var sourceTable = new mrgTable(options.sourceTableOptions);
        var targetTable = new mrgTable(options.targetTableOptions);
        sourceTable._table.on('check.bs.table',function(e,row) {
            var allIds = targetTable.getAllIds();
            if(allIds.indexOf(row.id)<0) {
                //复制时删除checkbox的值
                delete row['0'];
                targetTable.appendRow(row);
            }
        });
        sourceTable._table.on('uncheck.bs.table',function(e,row) {
            targetTable.removeById(row.id);
        })
        $('#multiSelect #search').click(function() {
            sourceTable.reload();
        });
    }
    return _;
}







