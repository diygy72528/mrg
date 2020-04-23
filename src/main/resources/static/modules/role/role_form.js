$(function() {

    //初始化树
    initTree();
    initValidate();
    initSwitch();
})
function initSwitch() {
    $('#status').bootstrapSwitch({size:'small',onColor:'success',onText:'启用',offText:'停用'});
}
function initValidate() {
    $.valid_obj = new mrgValid('roleForm',{
        name:{
            validators:{
                notEmpty:{
                    message:'请输入角色名称'
                }
            }
        }
    },false);
}
function initTree() {
    var options = {
        treeId:'tree',
        url:contextPath + 'menu/treeData',
        echoCheckedIds:$('#menuIds').val(), //需要回显的数据
        setting: {
            check: {
                enable: true,             // 置 zTree 的节点上是否显示 checkbox / radio
            }
        }
    };
    $.tree.init(options);
}


function doSubmit() {
    if(!$.valid_obj.validate()){
        return;
    }
    var form_data = $('#roleForm').serializeArray();
    var treeObj = $.fn.zTree.getZTreeObj('tree');
    var selectedNodes = treeObj.getCheckedNodes(true);
    var menuIds = $.map(selectedNodes,function(v){
        return v.id;
    }).join();
    $.post(window.parent.$.table.options.saveOrUpdateUrl,
        form_data.concat({name:"menuIds",value:menuIds}).concat({name:"status",value:$('#status').is(':checked') == true?1:0}),
        function(result) {
        $.operate.successCallback(result);
    });

}



