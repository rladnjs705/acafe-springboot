var AKey = "genAkey";
var dynatable;

var alertMsg = {
   'reg': '등록되었습니다.',
   'upd': '수정되었습니다.',
   'del': '삭제되었습니다.',
   'apply': '적용되었습니다.',
   'cancel': '취소되었습니다.',
   'proc': '처리되었습니다.',
   'fileReg' : '파일을 등록해주세요.',
   'pwCheck' : '입력하신 비밀번호와\n비밀번호 확인이 일치하지 않습니다.\n다시 입력해주세요.',
   'save': '저장되었습니다.',
};

function GetCookie(name){
	var result = null;
	var myCookie = " " + document.cookie + ";";
	var searchName = " " + name + "=";
	var startOfCookie = myCookie.indexOf(searchName);
	var endOfCookie;
	if (startOfCookie != -1){
	  startOfCookie += searchName.length;
	  // skip past cookie name
	  endOfCookie = myCookie.indexOf(";", startOfCookie);
	  result = unescape(myCookie.substring(startOfCookie,endOfCookie));
	}
	//alert("cookie::"+name+"::"+result);
	return result;
}

function SetCookie(name, value, expires, path, domain, secure){
	var expString = ((expires == null)? "" : ("; expires=" + expires.toGMTString()));
	var pathString = ((path == null) ? "" : ("; path=" + path));
	var domainString = ((domain == null)? "" : ("; domain=" + domain));
	var secureString = ((secure == true) ? "; secure" : "");
	document.cookie = name + "=" + escape(value)+ expString + pathString + domainString+ secureString;
}

function setSimpleCookie(name,value){
	var c_value= escape(value) + ("; path=/;");
	document.cookie= name + "=" + c_value;
}

function setCookieWithTimeout(name,value,sec){
	var exdate = new Date();
	exdate.setTime(exdate.getTime()+(sec*1000));
	var c_value= escape(value) + ("; path=/; expires="+exdate.toGMTString());
	document.cookie= name + "=" + c_value;
}

function extendsCookie(name, sec){
	var exdate = new Date();
	exdate.setSeconds(exdate.getSeconds()+sec);
	var c_value= escape(GetCookie(name) == null? "" : GetCookie(name)) + ("; path=/; expires="+exdate.toGMTString());
	document.cookie = name + "=" + c_value;
}

function clearCookie(name){
	var ThreeDays = 3 * 24 * 60 * 60 * 1000;
	var expDate = new Date();
	expDate.setTime (expDate.getTime() - ThreeDays);
	document.cookie = name + "=ImOutOfHere; path=/; expires="+ expDate.toGMTString();
}

var projectHeader;
function getProjectHeader(){
	return projectHeader = { 'AKey': AKey, 'apiKey' : GetCookie("apiKey")};
}

$.ajaxPrefilter(function( options ) {

    options.beforeSend = function (xhr) {
    	for ( var i in projectHeader) {
			xhr.setRequestHeader(i, projectHeader[i]);
		}
    };
});

function request(method, url, params) {
	getProjectHeader();
	const ajax = $.ajax({
		type : method,
		url : url,
		data : params,
		dataType : "json",
		contentType: 'application/json',
	});
	ajax.done(
			function(data) {
				if (data.resultCode == "0000") {
					//extendsCookie("apiKey", 3600);
				}
		}).fail(function() {
			console.log("request fail");
		}).always(function() {
		}
	);
	return ajax;
}

function pageTag(pageNo, totalPageNo){

	var html = '';
	html += ' <li class="first"> <a href="javascript:goPage(1);"><span class="sr-only">First</span></a></li>';
	var startPage = pageNo - ((pageNo-1)%10);
	var lPage = pageNo > 1 ? pageNo -1 : pageNo;
	var rPage = pageNo >= totalPageNo ? pageNo : pageNo + 1;

	html += ' <li class="pre"><a href="javascript:goPage('+ lPage +');"><span class="sr-only">Previous</span></a></li>';
	var cnt = 0;
	while(startPage <= totalPageNo && cnt++ < 10 ){
		if(startPage == pageNo){
			html += ' <li class="active"> <a href="javascript:goPage('+startPage+');">'+ (startPage++) +'</a> </li> ';
		}else{
			html += ' <li> <a href="javascript:goPage('+startPage+');">'+ (startPage++) +'</a> </li> ';
		}
	}
	html += ' <li class="next"> <a href="javascript:goPage('+rPage+');"><span class="sr-only">Next</span></a></li>';
	html += ' <li class="last"> <a href="javascript:goPage('+totalPageNo+');"><span class="sr-only">Last</span></a></li>';

	return html;
}

function dtString(longDate, sap){
	if(longDate==null) return "-";
	var date =  new Date(longDate);
	return date.getFullYear()+sap+ pad0((date.getMonth() + 1)) +sap+ pad0(date.getDate());
}

function dttmString(longDate, sap){
	if(longDate==null) return "-";
	var date =  new Date(longDate);
	return date.getFullYear()+sap+ pad0((date.getMonth() + 1)) +sap+ pad0(date.getDate()) + ' ' + pad0(date.getHours()) + ':' + pad0(date.getMinutes());
}

function pad0(i) {
    return (i < 10) ? "0" + i : "" + i;
}

function ifNull(s,r){
	if(s==""){
		return r;
	}
	else if(s==null){
		return r;
	}
	else if(s==" "){
		return r;
	}
	else {
		return s;
	}
}

function isNull(s){
	if(s==null){
		return true;
	}
	else if(s==""){
		return true;
	}
	else if(s==" "){
		return true;
	}
	else {
		return false;
	}
}

function startLoadingBar(){
	$('#loadingProgress').show();
}

function stopLoadingBar(){
	$('#loadingProgress').hide();
}

function getListNum(page, totalPage, mod, row){
	if(mod==0){
		page-=1;
	}
	return (10 * (totalPage-page) + mod) - row;
}

function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) {
        	sval = temp[1];
        }
    }
    return sval;
}

/**
 * 파일업로드
 *
 */
function fileUpload(files){
	var isUpload = true;
	var data = new FormData();
	jQuery.each(files, function(i, file) {
		data.append('files', file);
	});

	var ajax = $.ajax({
	    url: "/upload",
	    data: data,
	    enctype: 'multipart/form-data',
	    cache: false,
	    contentType: false,
	    processData: false,
	    type: 'POST',
		dataType : "json"
	});
	ajax.done(
			function(data) {
				if (data.resultCode == "0000") {
					console.log("success");
				}
		}).fail(function() {
			console.log("request fail");
		}).always(function() {
		}
	);
	return ajax;
}

function commaNum(num) {
    var len, point, str;

    num = num + "";
    point = num.length % 3
    len = num.length;

    str = num.substring(0, point);
    while (point < len) {
        if (str != "") str += ",";
        str += num.substring(point, point + 3);
        point += 3;
    }
    return str;
}

function goExcelDown(url, params){
//	var params = {
//		'page' : 1,
//		'max' : 10000
//	};

	var str = jQuery.param( params );

	var request_url = url;//"/api/testexcel";

	location.href = request_url + "?" + str;
}

/**
 *  alert 관련
 * **/
function sweetToast(msg)
{
	Swal.fire({
		toast: true,
		position: 'center',
		icon: 'warning',
		title: msg,
		showConfirmButton: false,
		timer: 1500
	})
}

function sweetToastAndCallback(data, callback)
{
	Swal.fire({
		toast: true,
		position: 'center',
		icon: isSuccessResp(data) ? 'success' : 'error',
		title: getStatusMessage(data),
		showConfirmButton: false,
		timer: 1500
	}).then((result) => {
		if (result.isDismissed)
		{
			if (isSuccessResp(data))
				callback();
			/*else
                sweetError(invalidResp(data));*/
		}
	})
}

function sweetError(msg)
{
	Swal.fire({
		icon: 'error',
		text: msg
	})
}

function sweetConfirm(msg, callback)
{
	Swal.fire({
		text: msg,
		showCancelButton: true,
		confirmButtonText: label.confirm,
		cancelButtonText: label.cancel
	}).then((result) => {
		if (result.value)
			callback();
	})
}

function sweetConfirmWithCancelCallback(msg, okCallback, cancelCallback)
{
	Swal.fire({
		text: msg,
		showCancelButton: true,
		confirmButtonText: label.confirm,
		cancelButtonText: label.cancel
	}).then((result) => {
		if (result.value)
			okCallback();
		else
			cancelCallback();
	})
}

function sweetConfirmWithContent(content, callback)
{
	Swal.fire({
		html: content,
		showCancelButton: true,
		confirmButtonText: label.confirm,
		cancelButtonText: label.cancel
	}).then((result) => {
		if (result.value)
			callback();
	})
}

function swConfirm(msg, callback)
{
	Swal.fire({
		text: msg,
		showCancelButton: true,
		confirmButtonText: label.confirm,
		cancelButtonText: label.cancel
	}).then((result) => {
		if (result.value)
			callback();
	})
}

function toast(msg)
{
	Swal.fire({
		toast: true,
		position: 'center',
		icon: 'warning',
		title: msg,
		showConfirmButton: false,
		timer: 1500
	})
}