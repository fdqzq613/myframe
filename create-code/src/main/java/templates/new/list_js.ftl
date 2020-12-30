layui.use(['form', 'layer', 'table', 'laypage', 'laytpl'], function() {
	var $ = layui.jquery,
		form = layui.form,
		layer = layui.layer,
		table = layui.table;


	//---------------------我的行动--------------------------
	//添加资源
	$("#addBtn").on("click", function() {
		popupEdit();
	});
	
	function popupEdit(data) {
		var w = 800;
		var h = window.innerHeight - 50;
		googTool.showLayer.show({
			title: false,
			url: __global_ctx + "mg/${lowEntity}/editPage",
			offset: 'auto',
			width: w,
			height: h,
			scroll: true,
			data: data,
			callback: function(d) {
				reload(localStorage.pageNo);
			}
		});
	}

	//删除资源
	function del(id) {
		googTool.confirm('确定要删除吗?', '', function(index) {
			googTool.request({
				url: __global_ctx + "mg/${lowEntity}/delete",
				data: {
					ids: id
				},
				scb: function(d) {
					if (d.code == 200) {
						googTool.toastS(d.msg, function() {
							reload(localStorage.pageNo);
						});
					} else {
						googTool.toastE(d.msg);
					}
				},
				fcb: function() {
					googTool.alertE("保存失败！", "保存失败！");
				}
			});
		});
	}

	var searchName = "";

	$("#js_search").on("click", function() {
		searchName = $("#search_name").val();
		reload(1);
	})

	//渲染行动
	function reload(pageNo) {
		if (typeof pageNo === 'undefined') {
			pageNo = list.pageNo;
		}
		list.reload({
			page: {
				curr: pageNo //重新从第 1 页开始
			},
			where: {
				name: searchName,
			}
		});
	}



	var getListInfo = function() {
		return {
			elem: '#list',
			page: {
				curr: localStorage.pageNo
			},
			url: __global_ctx + "${requestmapping_pre}/list",
			autoLoad: true,
			cols: [
				[
						{
							field: 'no',
							type: "numbers",
							title: '编号',
							align: "center"
						},
				<#-- ----------  BEGIN 字段循环遍历  ---------->
				 <#list jspFields as field>
					<#if field.propertyName?contains("userid")||field.propertyName?contains("createTime")||field.propertyName?contains("Userid")||field.propertyName?contains("time")||field.propertyName=="id"||field.propertyName=="status">
					<#else>
						{
							field: '${field.propertyName}',
						   <#if field.comment!?length gt 0>
							title: '${field.comment}',
						   <#else>
							title: '${field.propertyName}',
						   </#if>
							align: "center"
						},
					</#if>
				</#list>
						{
								field: 'op',
								title: '操作',
								align: "center",
								templet: function(obj) {
									var html =
										'<a class="oprate" lay-event="edit">编辑</a><a class="oprate" lay-event="del">删除</a>';
									return html;
								}
							}
				]
			],
			where: {
				name: searchName,
			},
			text: {
				none: '暂无相关数据'
			},
			done: pageDone
		};
	}

	function pageDone(res, curr, data) {
		console.log(res, curr, data)
		localStorage.pageNo = curr;
		if (res.data.length == 0 && data != 0) {
			reload(1);
		}
	}
	/**
	 * 任务表格
	 */
	var list = table.render(getListInfo());

	table.on('tool(list)', function(obj) {
		var data = obj.data;
		var id = data.id;
		//删除资源关系
		if (obj.event === 'del') {
			del(data.id);
		} else if (obj.event == 'edit') {
			popupEdit(data);
		}
	});

	//向window提供关闭的方法
	window.closeIframe = function(name, returnObj) {
		var index = layer.getFrameIndex(name); //先得到当前iframe层的索引
		layer.close(index, returnObj); //再执行关闭 
	}
});
