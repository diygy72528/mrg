
$(function(){
    //初始化权限输入提示
    $('.ion-help-circled').tooltip();
    //根据选择的不同展示不同的选项
    $('input').on('ifChecked',function(event) {
        let val = $(this).val();
        switch(val) {
            //目录
            case '0':
                //隐藏：请求路径、权限标识；显示：图标
                $('input[name="url"]').parent().parent('.form-group').hide();
                $('input[name="permission"]').parent().parent().parent('.form-group').hide();
                $('input[name="icon"]').parent().parent().parent('.form-group').show();
                $('input[name="url"]').attr('disabled','true');
                $('input[name="permission"]').attr('disabled','true');
                $('input[name="icon"]').removeAttr('disabled');
              break;

            //菜单
            case '1':
                //显示：请求路径、权限标识、图标
                $('input[name="url"]').parent().parent('.form-group').show();
                $('input[name="permission"]').parent().parent().parent('.form-group').show();
                $('input[name="icon"]').parent().parent().parent('.form-group').show();
                $('input[name="url"]').removeAttr('disabled');
                $('input[name="permission"]').removeAttr('disabled');
                $('input[name="icon"]').removeAttr('disabled');
                break;

            //按钮
            case '2':
                //隐藏：图标、请求路径；显示：权限标识
                $('input[name="url"]').parent().parent('.form-group').hide();
                $('input[name="permission"]').parent().parent().parent('.form-group').show();
                $('input[name="icon"]').parent().parent().parent('.form-group').hide();
                $('input[name="url"]').attr('disabled','true');
                $('input[name="permission"]').removeAttr('disabled');
                $('input[name="icon"]').attr('disabled','true');
                break;
            default:
                break;

        }
    });
    $.tree.init({
        url:contextPath + 'menu/treeData',
        id:'treeName',
        name:'parentId',
        title:'请选择'
    });

    //初始化校验
    let fields = {
        type: {
            message: '请选择类型',
            validators: {
                notEmpty: {
                }
            }
        },
        name: {
            message: '名称校验失败',
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                }
            }
        }
    };
    let valid_obj = mrgValid('menuForm',fields);
    //绑定保存和取消
    $('#save').bind('click',function() {
        if(valid_obj.validator.validate().isValid()) {
            $.operate.post(contextPath + 'menu/saveOrUpdate',$('#menuForm').serialize(),$.table.reload);
        }
    });
    $('#cancel').bind('click',function() {
        parent.closeCurrentTab();
    })
});



/**
 * 提交
 */
function doSubmit() {
    console.log($('#menuForm').serialize())
    //parent.location.reload();
}
