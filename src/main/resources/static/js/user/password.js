layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    form.on('submit(saveBtn)',function (data){
        console.log(data.field);
        $.ajax({
            type: 'POST',
            url: ctx + "/user/updatePwd",
            data:{
                oldPassWord:data.field.old_password,
                newPassWord:data.field.new_password,
                repeatPassWord:data.field.again_password
            },
            success:function (result){
                if (result.code == 200){
                    layer.msg("用户登录成功，账号三秒中会后退出",function (){
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"})
                        $.removeCookie("userName",{domain:"localhost",path:"/crm"})
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"})
                        window.parent.location.href = ctx + "/index";
                    })
                }else {
                    layer.msg(result.msg,{icon:5})
                }
            }
        })
    })
});

