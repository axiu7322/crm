$(function () {
    loadModuleInfo();
});


var zTreeObj;
function loadModuleInfo() {
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules?roleId="+$("input[name='roleId']").val(),
        dataType:"json",
        success:function (data) {

            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
        }
    })
}


function zTreeOnCheck(event, treeId, treeNode) {
    //alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
    var nodes= zTreeObj.getCheckedNodes(true);
    var mids="mIds=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mIds=";
        }else{
            mids=mids+nodes[i].id;
        }
    }


    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+$("input[name='roleId']").val(),
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    })

}