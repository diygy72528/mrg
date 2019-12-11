$(function() {
    var options = {
        id:'table',
        detailUrl:contextPath+"menu/detail",
        editUrl:contextPath+"menu/edit",
        addUrl:contextPath+"menu/add",
        deleteUrl:contextPath+"menu/delete",
        modalName:"菜单",
        table:{
            toolbar:'#toolbar',
            url: contextPath+"menu/list",
            customParams:{
                isDelete:0
            },
            clickToSelect:false,
            idField: 'id',
            treeShowField: 'name',
            parentIdField: 'parentId',
            showColumns:true,
            initialState: 'collapsed',
            columns:[{
                checkbox:true
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'菜单名',
                field:'name',
                order:'asc',
            },{
                title:'类型',
                field:'type',
                order:'asc',
                align:'center'
            },{
                title:'图标',
                field:'icon',
                formatter:iconFormatter,
                align:'center'
            },{
                title:'url',
                field:'url',
                align:'center'
            },{
                title:'权限',
                field:'permission',
                align:'center'
            },{
                title:'操作',
                width:50,
                formatter:CURDButtons
            }
            ],

        }
    };
    var table = new mrgTable(options);
    function iconFormatter(value, row, index) {
        return '<span class="'+value+'">'
    }

    function CURDButtons(value, row, index) {
        return "<div class=\"dropdown\">\n" +
            "  <button class=\"btn btn-xs btn-primary dropdown-toggle\" type=\"button\" id=\"dropdownMenu1\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"true\">\n" +
            "    <i class=\"fa fa-cog\"></i>" +
            "    <span class=\"caret\"></span>\n" +
            "  </button>\n" +
            "  <ul class=\"dropdown-menu\" aria-labelledby=\"dropdownMenu1\">\n" +
            "    <li><a href=\"#\">修改</a></li>\n" +
            "    <li><a href=\"#\">删除</a></li>\n" +
            "    <li><a href=\"#\">新增子节点</a></li>\n" +
            "  </ul>\n" +
            "</div>";
    }

})
