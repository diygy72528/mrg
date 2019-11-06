$(function() {
    var options = {
        id:'table',
        table:{
            url: contextPath+"user/list",
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
    };
    new mrgTable(options);

})
