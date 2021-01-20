$(function() {
    initValid();
    initSwitch();
})

function initSwitch() {
    $('#status').bootstrapSwitch({size:'small',onColor:'success',onText:'有效',offText:'无效'});
}

function initValid() {
    var fields = {
        dictName: {
            validators:{
                notEmpty: {
                    message: '请填写字典名称',
                }
            }
        },
        value: {
            message : '请填写字典值',
            validators:{
                notEmpty:{
                },
                remoteValid : {
                    url: contextPath + 'dictData/checkValueUnique',
                    data: {
                        id:$('#id').val(),
                        typeId: $('#dictTypeId').val()
                    },
                    message: '该字典值已经存在',
                    //delay:2000
                }
            }
        }
    }
    $.valid_obj = new mrgValid('dictDataForm',fields,false);
}

function doSubmit() {
    if(!$.valid_obj.validate()){
        return;
    }
    var form_data = $('#dictDataForm').serializeArray();
    $.post(window.parent.$.table.options.saveOrUpdateUrl,
        form_data.concat({name:"status",value:$('#status').is(':checked') == true?1:0}),
        function(result) {
            $.operate.successCallback(result);
        });
}
