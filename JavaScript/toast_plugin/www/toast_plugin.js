var exec = require('cordova/exec');

//exports.showToast 需与java中定义的action值一致
exports.showToast = function(arg0, success, error) {
	//success :js调用成功返回信息
	//error: js调用失败返回信息
	//'ToastDemo' :js调用插件名
	//'showToast': js调用方法名 需与java中定义的action值一致
	//[arg0]:传值,如需传递多个参数，可以在[arg0]里面追加[arg0,arg1,arg2....]
	exec(success, error, 'ToastDemo', 'showToast', [arg0]);
};
