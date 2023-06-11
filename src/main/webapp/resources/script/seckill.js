var seckill = {

    URL: {

        now: function () {
            return '/seckill/time/now';
        },
        exposer: function(seckillId){
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function(seckillId, md5){
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },

    handleSeckill: function(seckillId, node){
    	//获取秒杀地址，控制显示逻辑，执行秒杀
        node.hide()
            .html('<button class="btn bg-primary btn-lg" id="killBtn">start seckill</button>');
        $.post(seckill.URL.exposer(seckillId),{}, function(result){
            console.log(result.success);
            console.log(result.data);
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //start seckill 开启秒杀
                    //get seckill address 获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl:"+killUrl);
                    //只绑定一次点击事件
                    $('#killBtn').one('click', function(){
                    	//执行秒杀请求
                    	//1: 先禁用按钮
                        $(this).addClass('disabled');
                        //2： 发送秒杀请求执行秒杀
                        $.post(killUrl, {}, function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];

                                //3: 显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    
                    //显示秒杀按钮
                    node.show();
                }else{
                    //do not start seckill
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end);
                }
            }else{
                console.log('result : ' + result);
            }
        });
    },

    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    countdown : function(seckillId, nowTime, startTime, endTime){
        var seckillBox = $('#seckill-box');
        if(nowTime > endTime){
            seckillBox.html('seckill is over');
        }else if(nowTime < startTime){
        	//秒杀未开始，计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function(event){//每隔1s倒计时事件
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function(){ //计时结束事件
            	//获取秒杀地址，控制显示逻辑，执行秒杀操作
                seckill.handleSeckill(seckillId, seckillBox);
            });
        }else{
            seckill.handleSeckill(seckillId, seckillBox);
        }
    },

    detail: {
        //
        init: function (params) {
            var killPhone = $.cookie('killPhone');
            console.info(killPhone);
            //模拟登录
            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true, //显示弹出层
                    backdrop: 'static', //禁止位置关闭
                    keyboard: false  //关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killphoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killphoneMessage').hide().html('<label class="label label-danger">wrong phone number！</label>').show(300);
                    }
                });
            }

            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];

            //登录后
            $.get(seckill.URL.now(), {}, function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                }else{
                    console.log('result : ' + result);
                }
            });
        }
    }
};
