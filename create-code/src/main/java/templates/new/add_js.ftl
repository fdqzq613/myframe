layui.use(['form'], function() {
	var _form = layui.form;

	var showLayer = parent.googTool.showLayer;
	var _data = showLayer.getData();

	if (_data) {
		var formData = {};
		for(key in _data){
			formData[key] = _data[key];
		}
		_form.val("form1", formData);
	} else {

	}

	//监听提交
	_form.on('submit(save)', function(data) {
		dosubmit(data);
		return false;
	});

	var isSubmiting = false; //是否正在提交

	function dosubmit($form) {
		if (isSubmiting) return;
		var updata = $form.field;
		var url = __global_ctx + "${requestmapping_pre}/save";
		if(updata.id){
			url = __global_ctx + "${requestmapping_pre}/update";
		}
		googTool.request({
			url: url,
			type: "post",
			data: updata,
			scb: function(data) {
				if (data.code == 200) {
					googTool.toast("保存成功");
					setTimeout(closeLayer, 1000);
				} else {
					isSubmiting = false;
					googTool.toastE(data.msg);
				}
			},
			fcb: function() {
				isSubmiting = false;
				googTool.toastE("保存发生未知错误");
			}
		});
	}

	function closeLayer() {
		showLayer.callBack();
	};
});
