layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    //关闭弹出层
    $("#closeBtn")  .click(function (){
        //当你在iframe页面关闭自身时
        var index =parent.layer.getFrameIndex(window.name);     //先得到当前iframe层的索引
        parent.layer.close(index);   //在执行关闭
    })

    // $.post(ctx+"/user/queryAllCustomerManager",function (res) {
    //     for (var i = 0; i < res.length; i++) {
    //         if($("input[name='man']").val() == res[i].id ){
    //             $("#assignMan").append("<option value=\"" + res[i].id + "\" selected='selected' >" + res[i].name + "</option>");
    //         }else {
    //             $("#assignMan").append("<option value=\"" + res[i].id + "\">" + res[i].name + "</option>");
    //         }
    //     }
    //     //重新渲染
    //     layui.form.render("select");
    // });


    form.on("submit(addOrUpdateSaleChance)", function (data) {
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        //弹出loading
        var url=ctx + "/sale_chance/save";
        if($("input[name='id']").val()){
            url=ctx + "/sale_chance/update";
        }
        $.post(url, data.field, function (res) {
            if (res.code == 200) {
                setTimeout(function () {
                    top.layer.close(index);
                    top.layer.msg("操作成功！");
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }, 500);
            } else {
                layer.msg(
                    res.msg, {
                        icon: 5
                    }
                );
            }
        });
        return false;
    });
});

        $.ajax({
        type: "get",
        url: ctx + "/user/queryAllSales",
        data:{},
        success:function (data) {
            if (data != null) {
                for (var i = 0; i < data.length; i++){
                    var opt = "<option value='"+data[i].id+"'>"+data[i].uname+"<option/>"
                    $("assignMan").append(opt);
                }
            }
            layui.form.render("select");
        }
    })