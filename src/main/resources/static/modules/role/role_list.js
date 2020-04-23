$(function() {
    var options = {
        id:'table',
        addUrl:contextPath+"role/form/{id}",
        editUrl:contextPath+"role/form/{id}",
        saveOrUpdateUrl: contextPath + "role/saveOrUpdate",
        deleteUrl:contextPath+"role/delete",
        modalName:"角色",
        table:{
            singleSelect:true,
            toolbar:'#toolbar',
            url: contextPath+"role/page",
            customParams:{
                isDelete:0
            },
            clickToSelect:false,
            idField: 'id',
            showColumns:true,
            columns:[{
                checkbox:true
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'角色名',
                field:'name',
                width:'20',
                widthUnit:'%',
                order:'asc',
            },{
                title:'角色状态',
                field:'status',
                width:'10',
                widthUnit:'%',
                order:'asc',
            },{
                title:'创建时间',
                field:'createTime',
                width:'20',
                widthUnit:'%',
                order:'asc',
            },{
                title:'操作',
                align:'center',
                formatter:CURDButtons
            }
            ],

        }
    };
    var table = new mrgTable(options);

    function CURDButtons(value, row, index) {
        return '<div type="group" onclick="$.operate.edit('+row.id+')" class="btn btn-primary btn-xs"><span class="fa fa-edit"/>编辑</div>\n' +
            '            <div type="group" onclick="$.operate.openTab(\''+contextPath+'role/roleUserList?id='+row.id+'\',\'role_user_'+row.id+'\',{\'title\':\'分配用户\'})" class="btn btn-success btn-xs"><span class="fa fa-user"/>分配用户</div>\n' +
            '            <div type="group" onclick="$.operate.remove('+row.id+')" class="btn btn-danger btn-xs"><span class="fa fa-trash-o"/>删除</div>';
    }

})
