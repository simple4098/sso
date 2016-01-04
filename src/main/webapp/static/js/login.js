$(function(){

	var message = $("#error_v").text();
	if("密码错误"==message){
		$("#password_tip").css("display","block").html(message);
	}else if("用户名有误,请您联系管理员"==message){
		$("#mobile_tip").css("display","block").html(message);
	}else if("验证码错误"==message || "验证码必填"==message){
		$("#captcha_tip").css("display","block").html(message);
	}else if("用户待审核中"==message){
		$("#mobile_tip").css("display","block").html(message);
	}

	;!function () {

		var $login = $("#login"),
			$submit = $("#login_btn");

		$(document).on("keyup", function(e){
			if (e.keyCode == 13)
				$submit.click();
		});

		$('.login-input').focus(function(){
			$(this).parent().css('borderColor','#fcc449');
			var oParent = $(this).parent();
			if(oParent.attr('id')!='captcha'){
				oParent.find('.tip').fadeOut();
			}else{
				oParent.parent().find('.tip').fadeOut();
			}

		})
		$('.login-input').blur(function(){
			var oParent = $(this).parent();

			oParent.css('borderColor','#ccc');

			if($(this).val()==''){
				if(oParent.attr('id')!='captcha'){
					oParent.find('.tip').fadeIn();
				}else{
					oParent.parent().find('.tip').fadeIn();
				}
			}else{
				if(oParent.attr('id')!='captcha'){
					oParent.find('.tip').fadeOut();
				}else{
					oParent.parent().find('.tip').fadeOut();
				}
			}
		})
	}();
})
// 验证码
function refresh(obj){
	obj.src = "/randomImg?"+Math.random();
}