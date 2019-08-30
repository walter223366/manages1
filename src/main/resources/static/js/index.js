var appid, openid, code, userinfo, qrcode, billFileName, tqm;
var ajurl = "http://www.hydzfp.com";
$(function() {
    //获取用户openid
    wxJSParam();
    /*code = GetRequest().code;
    alert("用户code=" + code);
    alert("用户openid=" + openid);

    $('#my-confirm').modal({
        relatedTarget: this,
        onConfirm: function(e) {
            wx.closeWindow();
            $('#my-confirm').modal('close');
        },
        onCancel: function(e) {
            wx.closeWindow();
            //saomiao();
        }
    });
    $('#my-confirm').modal('close');

    $('#kp').click(function() {
        kaipiao();
    })

    $('#close').click(function() {
        console.log("关闭页面");
        wx.closeWindow();
    })*/
})

function kaipiao() {
    //点击开票
    var obj = {};
    obj.ghdwmc = $('input[name="ghdwmc"]').val();
    obj.ghdwsbh = $('input[name="ghdwsbh"]').val();
    obj.ghdwdzdh = $('input[name="ghdwdzdh"]').val();
    obj.ghdwyhzh = $('input[name="ghdwyhzh"]').val();
    obj.ghdwdzyx = $('input[name="ghdwdzyx"]').val();
    obj.contacts = $('input[name="contacts"]').val();
    obj.phone = $('input[name="phone"]').val();
    obj.code = qrcode;
    obj.openid = openid;
    obj.tqm = tqm;
    obj.billFileName = billFileName;
    console.log(obj);
    $('#my-modal-loading').modal('open');
    var adurl = "/piaoju/repost/sendParams.do";
    if(billFileName) {
        adurl = "/piaoju/goago/scanCode.do";
    }
    if(tqm) {
        adurl = "/piaoju/goago/uniqueID.do";
    }

    $.ajax({
        url: ajurl + adurl,
        type: "GET",
        data: obj,
        dataType: 'json',
        success: function(data) {
            if(data['resultType'] == 'ERROR') {
                $('#msg').html(data['msg']);
            } else {
                var url = data.data.url;
                if(url == ''){
                    $('#msg').html(data.data.msg);
                }else{
                    location = url;
                }
            }
            $('#my-modal-loading').modal('close');
            $('#my-confirm').modal('open');
        }
    })
}

function GetRequest() {
    var url = location.search;
    //获取url中"?"符后的字串
    var theRequest = new Object();
    if(url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function wxJSParam() {
    $('#my-modal-loading').modal('open');
    var qr = {
        "invoke": "getWxParam",
        "p": {
            "url": location.href
        }
    };

    $.ajax({
        url: "http://www.hydzfp.com/piaoju/cdhy/service.do",
        type: 'post',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(qr),
        success: function(data) {
            var js_param = data.data;
            appid = js_param.appId;
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: js_param.appId, // 必填，公众号的唯一标识
                timestamp: js_param.timestamp, // 必填，生成签名的时间戳
                nonceStr: js_param.nonceStr, // 必填，生成签名的随机串
                signature: js_param.signature, // 必填，签名，见附录1
                jsApiList: [
                    'scanQRCode', 'chooseImage'
                ]
                // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });

            //获取用户OPENID
            if(GetRequest().openid) {
                openid = GetRequest().openid;
                if(GetRequest().data) {
                    qrcode = GetRequest().data;
                    checkParams(openid);
                }else if(GetRequest().billFileName){
                    billFileName = GetRequest().billFileName;
                    checkParams(openid);
                }else if(GetRequest().tqm){
                    tqm = GetRequest().tqm;
                    checkParams(openid);
                } else {
                    if(!GetRequest().tqm) {
                        wx.ready(function() {
                            $('#my-modal-loading').modal('close');
                            wx.scanQRCode({
                                needResult: 1,
                                scanType: ["qrCode"],
                                success: function(data) {
                                    if(data.resultStr.indexOf("data") > 0){
                                        qrcode = data.resultStr.substr(data.resultStr.indexOf("?") + 6);
                                    }else{
                                        billFileName = data.resultStr.substr(data.resultStr.indexOf("?") + 14);
                                    }
                                    checkParams(openid);
                                }
                            })
                        })
                    }
                }
            } else {
                qrcode = GetRequest().data;
                billFileName = GetRequest().billFileName;
                getopenid();
            }
        }
    });
    wx.error(function(res) {
        alert("出错了：" + res.errMsg); //这个地方的好处就是wx.config配置错误，会弹出窗口哪里错误，然后根据微信文档查询即可。
    });
};

function getopenid() {
    //通过code换取网页授权access_token
    $.ajax({
        url: ajurl + "/piaoju/weChatInter/getOpenid.do",
        type: "POST",
        data: {
            qrcode: qrcode,
            billFileName: billFileName,
            code: code
        },
        dataType: 'json',
        success: function(data) {
            if(data['resultType'] == 'ERROR') {
                $('#my-confirm').modal('open');
                $('#msg').html(data['msg']);
            } else {
                if(data.data.subscribe == 0) {
                    location = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI4NDM1MzU2MQ==&scene=124#wechat_redirect";
                } else {
                    console.log(data['msg']);
                    var row = data.data;
                    openid = row.openid;
                    $('input[name="ghdwmc"]').val(row.ghdwmc);
                    $('input[name="ghdwsbh"]').val(row.ghdwsbh);
                    $('input[name="ghdwdzdh"]').val(row.ghdwdzdh);
                    $('input[name="ghdwyhzh"]').val(row.ghdwyhzh);
                    $('input[name="ghdwdzyx"]').val(row.ghdwdzyx);
                    $('input[name="contacts"]').val(row.contacts);
                    $('input[name="phone"]').val(row.phone);
                }
            }
            $('#my-modal-loading').modal('close');
        }
    })
}

function checkParams(openid) {
    $.ajax({
        url: ajurl + "/piaoju/weChatInter/checkParams.do",
        type: "POST",
        data: {
            qrcode: qrcode,
            billFileName: billFileName,
            openid: openid,
            tqm : tqm
        },
        dataType: 'json',
        success: function(data) {
            if(data['resultType'] == 'ERROR') {
                $('#my-confirm').modal('open');
                $('#msg').html(data['msg']);
            } else {
                console.log(data['msg']);
                var row = data.data;
                $('input[name="ghdwmc"]').val(row.ghdwmc);
                $('input[name="ghdwsbh"]').val(row.ghdwsbh);
                $('input[name="ghdwdzdh"]').val(row.ghdwdzdh);
                $('input[name="ghdwyhzh"]').val(row.ghdwyhzh);
                $('input[name="ghdwdzyx"]').val(row.ghdwdzyx);
                $('input[name="contacts"]').val(row.contacts);
                $('input[name="phone"]').val(row.phone);
            }
            $('#my-modal-loading').modal('close');
        }
    })
}