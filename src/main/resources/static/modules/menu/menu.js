$(function() {
    var options = {
        id:'table',
        table:{
            url: contextPath+"menu/list",
            customParams:{
                isDelete:0
            },
            columns:[{
                checkbox:true
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'菜单名',
                field:'name',
                sortable:true,
                sortName:'name',
                order:'asc',
                align:'center'
            },{
                title:'类型',
                field:'type',
                sortable:true,
                sortName:'type',
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
            ]
        }
    };
    new mrgTable(options);
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
