$(function() {
    var options = {
        id:'table',
        table:{
            url:contextPath + "dictType/list",
            customParams:{
                isDelete:0
            },
            treeShowField: 'dictTypeChName',
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
                visible:true
            }]
        }
    };
    new mrgTable(options);
})
