$(function(){
	;!function () {
        
		var $login = $("#login"),
			$submit = $("#login_btn");

		$(document).on("keyup", function(e){ 
			if (e.keyCode == 13)
				$submit.click();
		});

		$('.login-input').focus(function(){
			$("#fm1").find('.error_v').remove();
			if($(this).attr('id')!='captcha'){
				$(this).parent().css('borderColor','#fcc449');
			}else{
				$(this).css('borderColor','#fcc449')
			}
			
		})
		$('.login-input').blur(function(){
			$("#fm1").find('.error_v').remove();
			var oParent = $(this).parent();
			if($(this).attr('id')!='captcha'){
				oParent.css('borderColor','#ccc');
			}else{
				$(this).css('borderColor','#ccc');
			}
			
			if($(this).val()==''){
			  	oParent.find('.tip').fadeIn();
			}else{
				oParent.find('.tip').fadeOut();
			}
		})

		// 验证码
		$("#captchaImg,#captchaRegisteImg").click(function(){
			$(this).hide().attr('src','/captcha?tep=' + new Date().getMilliseconds() ).fadeIn();
		});
		
		// 提交
		$submit.on('click', function(e){
			e.preventDefault();
			var datas = {
				captcha: $('#captcha').val(),
				mobile: $('#account').val(),
				password: $('#pwd').val()
			}
			if (!datas.mobile) {
				_showError('mobile', '账户名必须填写！')
				return;
			}
			if (!datas.password) {
				_showError('password', '密码必须填写！')
				return;
			}
			if (!datas.captcha) {
				_showError('captcha', '验证码必须填写！')
				return;
			}

			//TC.dialog.loading();
			$.post('/login', datas).done(function(rs) {
				if (rs.status == 400) {
					var msg = rs.message;
					if (msg.captcha) {
						_showError('captcha', msg.captcha[0])
						return;
					}
					if (msg.mobile) {
						_showError('mobile', msg.mobile[0])
						return;
					}
					if (msg.password) {
						_showError('password', msg.password[0])
						return;
					}
				}
				if (rs.status == 200) {
					window.location.href = rs.result
				}
			})
		});

		function _showError(type, msg) {
			$login.find('.tip').empty();
			$('#' + type + '_tip').html(msg).show();
		}
	}();

	
})