$(function() {
    var options = {
        id:'table',
        addUrl:contextPath+"menu/form/{id}",
        editUrl:contextPath+"menu/form/{id}",
        saveOrUpdateUrl: contextPath + "menu/saveOrUpdate",
        deleteUrl:contextPath+"menu/delete",
        modalName:"菜单",
        treeColumn:1,
        table:{
            singleSelect:true,
            toolbar:'#toolbar',
            url: contextPath+"menu/list",
            customParams:{
            },
            pagination:false,
            clickToSelect:false,
            idField: 'id',
            treeShowField: 'name',
            parentIdField: 'parentId',
            showColumns:true,
            initialState: 'collapsed',
            columns:[{
                radio:true
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'菜单名',
                field:'name',
                width:'20',
                widthUnit:'%'
            },{
                title:'类型',
                field:'type',
                width:'10',
                align:'center',
                widthUnit:'%'
            },{
                title:'图标',
                field:'icon',
                formatter:iconFormatter,
                width:'10',
                align:'center',
                widthUnit:'%'
            },{
                title:'url',
                field:'url',
                width:'20',
                align:'center',
                widthUnit:'%'
            },{
                title:'权限',
                width:'20',
                field:'permission',
                align:'center',
                widthUnit:'%'
            },{
                title:'操作',
                width:'20',
                widthUnit:'%',
                align: 'center',
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
        return "";
    }

})
