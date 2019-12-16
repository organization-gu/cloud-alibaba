var login = "http://localhost:8060";

$(function () {
    var padlength = (window.innerHeight - 106 - 106 - $(".login-mid").height()) / 2;
    $(".login-mid").css("padding-top", padlength).css("padding-bottom", padlength);

    var logDivLength = ($(".login-mid").height() - $(".login-div").height() - 110) / 2
    $(".login-div").css("margin-top", logDivLength)


    $(window).resize(function () {
        var padlength = (window.innerHeight - 106 - 106 - $(".login-mid").height()) / 2;
        $(".login-mid").css("padding-top", padlength).css("padding-bottom", padlength);
    });
    // 获取验证码需要的唯一UUID
    localStorage.setItem("uuId", uuid());

    //验证码图片获取 
    $("#img").attr('src', login + '/code/image?deviceId=' + localStorage.getItem("uuId"));

    $("#login").click(function () {
        $.ajax({
            url: login + '/authentication/form',    //请求的url地址
            dataType: "json",   //返回格式为json
            async: false,//请求是否异步，默认为异步，这也是ajax重要特性
            type: "POST",   //请求方式
            // contentType: "application/json;charset=UTF-8",
            crossDomain: true,
            // headers: {"Authorization": "Basic amM6anM="},
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", "Basic "+getUrlParam("client"));
            },
            data: {
                "username": $(".clientId").val()+"&"+$(".username").val(),
                "password": $(".password").val(),
                "imageCode": $(".yzm").val(),
                "deviceId": localStorage.getItem("uuId")
            },
            success: function (req) {
                // if ()
                localStorage.setItem("user_name", $(".username").val());
                localStorage.setItem("access_token", req.access_token);
                localStorage.setItem("refresh_token", req.refresh_token);
                window.location = req.url+"?id="+req.client_id+":"+req.name;
            },
            complete: function (jqXHR) {
                // console.log(jqXHR.status);
            },
            error: function (jqXHR) {
                // console.log(jqXHR.status);
                if (jqXHR.responseJSON.content == "IMAGE验证码不匹配") {
                    layer.msg("验证码不匹配")
                } else {
                    layer.msg(jqXHR.responseJSON.content)
                }

                $("#img").attr('src', login + '/code/image?deviceId=' + localStorage.getItem("uuId"));
            }
        });
    })
})

$("#img").click(function () {
    localStorage.setItem("uuId", uuid());
    $("#img").attr('src', login + '/code/image?deviceId=' + localStorage.getItem("uuId"));
})

// uuid获取方法
function uuid() {
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
    s[8] = s[13] = s[18] = s[23] = "-";

    var uuid = s.join("");
    return uuid;
}

//字符串转base64
function encode(str) {
    // 对字符串进行编码
    var encode = encodeURI(str);
    // 对编码的字符串转化base64
    var base64 = btoa(encode);
    return base64;
}

function getUrlParam(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg); //匹配目标参数
    if (r!=null) return unescape(r[2]); return null; //返回参数值
}
