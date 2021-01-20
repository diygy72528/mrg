$(function() {
    initTable();
})
function initTable() {
    var options = {
        //tableid
        id:'table',
        addUrl:contextPath+"dictData/form/{id}?typeId=" + $('#typeId').val(),
        editUrl:contextPath+"dictData/form/{id}?typeId=" + $('#typeId').val(),
        saveOrUpdateUrl: contextPath + "dictData/saveOrUpdate",
        deleteUrl:contextPath+"dictData/delete?typeId=" + $('#typeId').val(),
        modalName:"字典值",
        //bootstrap-table options
        table:{
            toolbar:'#toolbar',
            url: contextPath+"dictData/listByTypeId",
            pagination:true,
            customParams:{
                typeId:$('#typeId').val()
            },
            columns:[{
                checkbox:true
            },{
                title:'id',
                field:'id',
                visible:false
            },{
                title:'字典名称',
                field:'dictName',
            },{
                title:'字典值',
                field:'value',
            },{
                title:'状态',
                field:'status',
                formatter:function(v) {
                    $.WebFn.getDict('status',v);
                }
            },{
                title:'操作',
                formatter:CURDButtons
            }
            ]
        }
    };
    new mrgTable(options);
}

function CURDButtons(value, row, index) {
    return '<div type="group" onclick="$.operate.edit(\''+row.id+'\')" class="btn btn-primary btn-xs"><span class="fa fa-edit"/>编辑</div>\n' +
        '            <div type="group" onclick="$.operate.remove(\''+row.id+'\')" class="btn btn-danger btn-xs"><span class="fa fa-trash-o"/>删除</div>';
}
