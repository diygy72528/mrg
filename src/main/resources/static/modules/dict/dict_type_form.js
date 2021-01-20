$(function() {
    initValid();
    initSwitch();
})

function initSwitch() {
    $('#status').bootstrapSwitch({size:'small',onColor:'success',onText:'有效',offText:'无效'});
}

function initValid() {
    var fields = {
        dictTypeEnName: {
            message : '请填写英文名称',
            validators:{
                notEmpty:{
                },
                //remote校验有严重弊端，无法实现同步校验,所以使用自定义校验
                /*remote:{
                    data:{
                        dictTypeEnName:$('#dictTypeEnName').val(),
                        id:$('#id').val()
                    },
                    url: contextPath + 'dictType/checkUniqueName',
                    message:'该字典已经存在',
                    type: 'get'
                }*/
                remoteValid : {
                    url: contextPath + 'dictType/checkUniqueName',
                    data: {
                        id:$('#id').val()
                    },
                    message: '该字典已经存在',
                    //delay:2000
                }
            }
        }
    }
    $.valid_obj = new mrgValid('dictTypeForm',fields,false);
}

function doSubmit() {
    if(!$.valid_obj.validate()){
        return;
    }
    var form_data = $('#dictTypeForm').serializeArray();
    $.post(window.parent.$.table.options.saveOrUpdateUrl,
        form_data.concat({name:"status",value:$('#status').is(':checked') == true?1:0}),
        function(result) {
            $.operate.successCallback(result);
        });
}
