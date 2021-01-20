$(function() {
    var options = {
        addUrl:contextPath+"dictType/form/{id}",
        editUrl:contextPath+"dictType/form/{id}",
        saveOrUpdateUrl: contextPath + "dictType/saveOrUpdate",
        deleteUrl:contextPath+"dictType/delete",
        id:'table',
        modalName:'字典',
        table:{
            toolbar:'#toolbar',
            url:contextPath + "dictType/list",
            customParams:{
                isDelete:0
            },
            columns:[{
                checkbox:true,
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'中文名',
                field:'dictTypeChName',
                visible:true
            },{
                title:'英文名',
                field:'dictTypeEnName',
                visible:true
            },{
                title:'类型描述',
                field:'dictTypeDescript',
                visible:true
            },{
                title:'字典状态',
                field:'status',
                visible:true,
                formatter:function(v) {
                    return $.WebFn.getDict('status',v);
                }
            },{
                title:'操作',
                formatter:CURDButtons
            }]
        }
    };
    new mrgTable(options);

})
function CURDButtons(value, row, index) {
    return '<div type="group" onclick="$.operate.edit(\''+row.id+'\')" class="btn btn-primary btn-xs"><span class="fa fa-edit"/>编辑</div>\n' +
        '            <div type="group" onclick="$.operate.openTab(\''+contextPath+'dictType/typeDataList?id='+row.id+'\',\'dict_type_data_'+row.id+'\',{\'title\':\'设置字典值\'})" class="btn btn-success btn-xs"><span class="fa fa-user"/>设置字典值</div>\n' +
        '            <div type="group" onclick="$.operate.remove(\''+row.id+'\')" class="btn btn-danger btn-xs"><span class="fa fa-trash-o"/>删除</div>';
}
