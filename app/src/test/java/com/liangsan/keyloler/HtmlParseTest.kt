package com.liangsan.keyloler

import com.fleeksoft.ksoup.Ksoup
import org.junit.Assert.assertEquals
import org.junit.Test

class HtmlParseTest {

    @Test
    fun parse_content_correctly() {
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <!--<title>账号登录 -  其乐 Keylol -  驱动正版游戏的引擎！</title>-->
            <link rel="manifest" href="/site.webmanifest?v=12">
            <link rel="apple-touch-icon" sizes="180x180" href="/apple-touch-icon.png?v=algn46e3Xp">
            <link rel="icon" type="image/png" sizes="32x32" href="/favicon-32x32.png?v=algn46e3Xp">
            <link rel="icon" type="image/png" sizes="16x16" href="/favicon-16x16.png?v=algn46e3Xp">
            <link rel="mask-icon" href="/safari-pinned-tab.svg?v=algn46e3Xp" color="#66bbff">
            <link rel="shortcut icon" href="/favicon.ico?v=algn46e3Xp">
            <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
            <meta name="apple-mobile-web-app-capable" content="yes">
            <meta name="apple-mobile-web-app-title" content="其乐社区">
            <link href="splashscreens/iphone5_splash.png" media="(device-width: 320px) and (device-height: 568px) and (-webkit-device-pixel-ratio: 2)" rel="apple-touch-startup-image" />
            <link href="splashscreens/iphone6_splash.png" media="(device-width: 375px) and (device-height: 667px) and (-webkit-device-pixel-ratio: 2)" rel="apple-touch-startup-image" />
            <link href="splashscreens/iphoneplus_splash.png" media="(device-width: 621px) and (device-height: 1104px) and (-webkit-device-pixel-ratio: 3)" rel="apple-touch-startup-image" />
            <link href="splashscreens/iphonex_splash.png" media="(device-width: 375px) and (device-height: 812px) and (-webkit-device-pixel-ratio: 3)" rel="apple-touch-startup-image" />
            <link href="splashscreens/iphonexr_splash.png" media="(device-width: 414px) and (device-height: 896px) and (-webkit-device-pixel-ratio: 2)" rel="apple-touch-startup-image" />
            <link href="splashscreens/iphonexsmax_splash.png" media="(device-width: 414px) and (device-height: 896px) and (-webkit-device-pixel-ratio: 3)" rel="apple-touch-startup-image" />
            <meta name="application-name" content="其乐社区">
            <meta name="msapplication-TileColor" content="#66bbff">
            <meta name="theme-color" content="#55bbff">
            <link href="https://keylol.com/forum.php?mod=rss" rel="alternate" type="application/rss+xml" title="Keylol RSS Feed" />
            <link rel="search" type="application/opensearchdescription+xml" href="/content-search.xml" title="其乐社区">
            <link rel="preconnect" href="https://hm.baidu.com">
            <link rel="preconnect" href="https://cdn.jsdelivr.net">
            <link rel="preconnect" href="https://www.google-analytics.com">
            <link rel="preconnect" href="https://www.googletagmanager.com"><title>账号登录 - 其乐 Keylol</title>
            <meta name="keywords" content="" />
            <meta name="description" content=",其乐 Keylol" />
            <meta name="generator" content="Keylol X3.5" />
            <meta name="author" content="Keylol.com & 其乐" />
            <meta name="copyright" content="2004-2024 其乐 Keylol" />
            <meta name="robots" content="noarchive">
            <base href="https://keylol.com/" />
            <script>
            (function() {
            if (!window.location.hostname.endsWith('k' + 'eylol.com'))
            window.location = 'https://k' + 'eylol.com' + window.location.pathname;
            })();
                </script><link rel="stylesheet" type="text/css" href="data/cache/style_7_common.css?IvL" /><link rel="stylesheet" type="text/css" href="data/cache/style_7_member_logging.css?IvL" /><script type="text/javascript">var STYLEID = '7', STATICURL = 'static/', IMGDIR = 'static/image/common', VERHASH = 'IvL', charset = 'utf-8', discuz_uid = '0', cookiepre = 'dz_2132_', cookiedomain = '', cookiepath = '/', showusercard = '1', attackevasive = '0', disallowfloat = 'login|newthread', creditnotice = '1|体力|点,3|蒸汽|克,4|动力|点,6|绿意|,8|可用改名次数|次', defaultstyle = '', REPORTURL = 'aHR0cHM6Ly9rZXlsb2wuY29tL21lbWJlci5waHA/bW9kPWxvZ2dpbmcmYWN0aW9uPWxvZ2luJmF1dGg9ZGZiNktlajNJdi9lREpJOXBXeFFOQzREcTRNRWZ6SVVoSTdWaXBXUHFEcDYvRjU2YkFhZzkvdEJPdzlVVXliQnRreDkrZw==', SITEURL = 'https://keylol.com/', JSPATH = 'data/cache/', DYNAMICURL = '';</script>
            <script src="data/cache/common.js?IvL" type="text/javascript"></script>
            <meta name="application-name" content="其乐 Keylol" />
            <meta name="msapplication-tooltip" content="其乐 Keylol" />
            <meta name="msapplication-task" content="name=社区;action-uri=https://keylol.com/forum.php;icon-uri=https://keylol.com/static/image/common/bbs.ico" />
            <link rel="stylesheet" id="css_widthauto" type="text/css" href='data/cache/style_7_widthauto.css?IvL' />
            <script type="text/javascript">HTMLNODE.className += ' widthauto'</script>
            <link rel="stylesheet" id="duceapp_font" type="text/css" href="./source/plugin/duceapp_base/static/css/duceapp_font.css?IvL" />
            <link rel="stylesheet" id="duceapp_smsauth" type="text/css" href="./data/duceapp/smsauth/smsauth.css?IvL" />
            </head><body id="nv_member" class="duceapp_cv_body" onkeydown="if(event.keyCode==27) return false;">
            <div id="append_parent"></div><div id="ajaxwaitid"></div>
            <div class="duceapp_cv_bg"></div><div id="duceapp_cv_header" class="duceapp_cv_header">
            <div class="duceapp_cv_fxd"><div class="duceapp_cv_topbar">
            <div class="duceapp_cv_logo z" style="background-image:url(template/steamcn_metro/src/img//common/icon_with_text_256h.png)"><a href="./" title="其乐 Keylol"><img src="template/steamcn_metro/src/img//common/icon_with_text_256h.png" alt="其乐 Keylol" border="0" /></a></div>
            <div class="duceapp_cv_topnav"></div>
            </div></div>
            </div>
            <div id="wp" class="duceapp_cv_wrap">
            <script type="text/javascript">
            var mswheelspec = "";
            function duceapp_loadjs(url, func, id) {
            if (id && ${'$'}(id)) {
            return typeof func == 'function' && func();
            }
            var script = document.createElement('script');
            script.type = 'text/javascript';
            script.src = url;
            if (id) {
            script.id = id;
            }
            var headNode = document.getElementsByTagName('head')[0];
            headNode.appendChild(script);
            if (script.readyState) {
            script.onreadystatechange = function(){
            if (script.readyState == "loaded" || script.readyState == "complete"){
            script.onreadystatechange = null;
            typeof func == 'function' && func();
            }
            };
            } else {
            script.onload = function(){typeof func == 'function' && func()}
            }
            }
            </script><div id="login_container" class="duceapp_cv_main cl" style="">
            <div class="cv_login_container" id="login_container_LnM70" style="">
            <form method="post" autocomplete="off" name="login" id="loginform_LnM70" class="cl" onsubmit="return false;" action="member.php?mod=logging&action=login&loginsubmit=yes&loginhash=LnM70" style="">
            <input type="hidden" name="duceapp" value="yes" />
            <input type="hidden" name="formhash" value="5be8682b" />
            <input type="hidden" name="referer" value="https://keylol.com/" />
            			
            <input type="hidden" name="handlekey" value="login" />
            <input type="hidden" name="auth" value="033bAB/bcUjtOv1GOq2kjxK4IspnoDo6YsUM9igQuah4" />
            <div class="cv_login_pwContent" id="cv_login_pwContent_LnM70" style="margin-top:-120px;">
            <div id="pwlogin_LnM70" class="pwlogin" style="min-height:112px;">
            				
            <input name="seccodehash" type="hidden" value="SzuOo1V" id="seccodehash_LnM70" />
            <div class="login_seccode" id="login_seccode_LnM70">
            <div class="login_input" id="secinput_LnM70">
            <div class="duceapp_font unselable" unselectable="on">&#xe999;</div>
            <input type="text" class="px" name="seccodeverify" id="seccodeverify_LnM70" placeholder="验证码（点此显示）">
            <span class="checkseccodeverify" id="checkseccodeverify_LnM70"></span>
            </div>
            <div class="sec_button unselable" unselectable="on" onclick="if(${'$'}('login_seccode_LnM70_menu').style.display != 'none') duceapp_updateseccode('LnM70','68638')">换一个</div>
            <div id="login_seccode_LnM70_menu" class="secimg_pop unselable" unselectable="on" style="display:none"><img id="secimg_LnM70" onclick="duceapp_updateseccode('LnM70','68638')" width="100" height="30" src="static/image/common/none.gif" class="vm" alt=""></div>
            </div>
            <script type="text/javascript">
            function duceapp_updateseccode(loginhash, ran) {
            var tmprandom = 'S' + Math.floor(Math.random() * 1000);
            ${'$'}('seccodehash_'+loginhash).value = tmprandom;
            ${'$'}('seccodeverify_'+loginhash).value = '';
            ${'$'}('checkseccodeverify_'+loginhash).innerHTML = '';
            ${'$'}('secimg_'+loginhash).src = 'misc.php?mod=seccode&update='+ran+'&idhash='+ tmprandom;
            }
            </script>
            <script type="text/javascript" reload="1">				
            ${'$'}('append_parent').appendChild(${'$'}('login_seccode_LnM70_menu'));
            ${'$'}('seccodeverify_LnM70').blur();
            </script>
            <div class="login_checkbox unselable" unselectable="on" style="margin-bottom:-8px;">
            <span onclick="toggleCheckbox(this)" ><input type="checkbox" class="pc" name="cookietime" id="cookietime_LnM70" tabindex="1" value="2592000"  />自动登录</span>
            </div>
            </div>
            <div class="login_button unselable" unselectable="on" id="login_button_LnM70" style="letter-spacing:10px;">登录</div>
            <div class="login_method unselable" unselectable="on"><div class="legend">其他帐号登录</div>

            <a href="https://keylol.com/connect.php?mod=login&op=init&referer=https%3A%2F%2Fkeylol.com%2F.%2F&statfrom=login_simple" class="login_icon duceapp_qqlogin"><i class="duceapp_font qq">&#xe92c;</i></a>

            <a href="steamcn_steam_connect-login.html" style="color: #333; float: left; width: 30px; height: 30px;"><svg aria-hidden="true" focusable="false" data-prefix="fab" data-icon="steam" class="svg-inline--fa fa-steam fa-w-16" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 496 512"><path fill="currentColor" d="M496 256c0 137-111.2 248-248.4 248-113.8 0-209.6-76.3-239-180.4l95.2 39.3c6.4 32.1 34.9 56.4 68.9 56.4 39.2 0 71.9-32.4 70.2-73.5l84.5-60.2c52.1 1.3 95.8-40.9 95.8-93.5 0-51.6-42-93.5-93.7-93.5s-93.7 42-93.7 93.5v1.2L176.6 279c-15.5-.9-30.7 3.4-43.5 12.1L0 236.1C10.2 108.4 117.1 8 247.6 8 384.8 8 496 119 496 256zM155.7 384.3l-30.5-12.6a52.79 52.79 0 0 0 27.2 25.8c26.9 11.2 57.8-1.6 69-28.4 5.4-13 5.5-27.3.1-40.3-5.4-13-15.5-23.2-28.5-28.6-12.9-5.4-26.7-5.2-38.9-.6l31.5 13c19.8 8.2 29.2 30.9 20.9 50.7-8.3 19.9-31 29.2-50.8 21zm173.8-129.9c-34.4 0-62.4-28-62.4-62.3s28-62.3 62.4-62.3 62.4 28 62.4 62.3-27.9 62.3-62.4 62.3zm.1-15.6c25.9 0 46.9-21 46.9-46.8 0-25.9-21-46.8-46.9-46.8s-46.9 21-46.9 46.8c.1 25.8 21.1 46.8 46.9 46.8z"></path></svg></a>
            <a href="javascript:;" onclick="toScanQrcode()" class="login_icon duceapp_wxlogin"><i class="duceapp_font weixin">&#xe92f;</i></a><a href="member.php?mod=register" class="toregPage">注册</a>
            </div>
            </div>
            </form>
            </div>
            <div class="cv_login_imgdiv" id="cv_login_imgdiv"><div style="background-image:url('https://keylol.com/source/plugin/duceapp_smsauth/static/image/loginbanner.png');z-index:2;"></div><div style="background-image:url('https://keylol.com/source/plugin/duceapp_smsauth/static/image/smsbanner.png');z-index:1;"></div></div>
            <script type="text/javascript">
            _attachEvent(window, 'load', function() {
            duceapp_loadjs('source/plugin/duceapp_smsauth/static/js/gvslide.js?IvL', function(){
            duceapp_gvslider(${'$'}('cv_login_imgdiv'), 3500);
            });
            });
            </script>	
            </div>
            <script type="text/javascript">
            function formSerialize() {
            var res = [],
            current = null,
            i,
            len,
            k,
            optionLen,
            option,
            optionValue,
            form = this;	
            for(i=0, len=form.elements.length; i<len; i++){		
            current = form.elements[i];
            if(current.disabled) continue;		
            switch(current.type){
            case "file":
            case "submit":
            case "button":
            case "image":
            case "reset":
            case undefined:
            break;
            case "select-one":
            case "select-multiple":
            if(current.name && current.name.length){
            console.log(current)
            for(k=0, optionLen=current.options.length; k<optionLen; k++){

            option = current.options[k];
            optionValue = "";
            if(option.selected){
            if(option.hasAttribute){
            optionValue = option.hasAttribute('value') ? option.value : option.text
            }else{
            optionValue = option.attributes('value').specified ? option.value : option.text;	
            }
            res.push(encodeURIComponent(current.name) + "=" + encodeURIComponent(optionValue));
            }
            }
            }
            break;
            case "radio":
            case "checkbox":
            if(!current.checked) break;			
            default:
            if(current.name && current.name.length){
            res.push(encodeURIComponent(current.name) + "=" + encodeURIComponent(current.value));
            }			
            }
            }	
            return res.join("&");
            }
            function changeLoginType(tab, loginhash, callback){
            var tabs = tab.parentNode.getElementsByTagName(tab.tagName.toLowerCase());
            var pwContent = tab.parentNode.parentNode;
            for (var i=0; i<tabs.length; i++) {
            var t = tabs[i].getAttribute('t');
            var div = t ? ${'$'}(t + '_' + loginhash) : null;
            if (tabs[i] == tab) {
            tabs[i].className = 'curr';
            if (div) {
            div.style.display = '';
            }
            } else {
            tabs[i].className = '';
            if (div) {
            div.style.display = 'none';
            }
            }
            }
            var lotype = tab.getAttribute('t');
            if (tab.getAttribute('_action')) {
            ${'$'}('loginform_' + loginhash).action = tab.getAttribute('_action');
            }
            if (callback && typeof callback == 'function') {
            callback(loginhash);
            }
            }
            function toggleCheckbox(obj){
            if (obj.className == 'checked') {
            obj.className = '';
            obj.getElementsByTagName('input')[0].checked = false;
            } else {
            obj.className = 'checked';
            obj.getElementsByTagName('input')[0].checked = true;
            }
            }
            function inputAddEvent(obj, loginhash, ran, handlekey) {
            _attachEvent(obj, 'blur', function() {
            this.parentNode.className = this.parentNode.className.replace(/ login_inserted/g, '');
            if (this.id.indexOf('seccodeverify_') != -1) {
            var secverify = this.value;
            if (!secverify || this.secverify == secverify) {
            return;
            }
            this.secverify = secverify;
            var obj = ${'$'}C('checkseccodeverify', this.parentNode, 'span')[0];
            obj.innerHTML = '';
            var x = new Ajax();
            x.obj = obj;
            x.get('misc.php?mod=seccode&action=check&inajax=1&idhash=' + ${'$'}('seccodehash_' + loginhash).value + '&secverify=' + (BROWSER.ie && document.charset == 'utf-8' ? encodeURIComponent(secverify) : secverify), function(s, x){
            try{
            if(s.substr(0, 7) == 'succeed') {
            x.obj.innerHTML = '<font style="color:#00CC00">&#xe9ac;</font>';
            } else {
            x.obj.innerHTML = '<font style="color:#FF0033">&#xe94e;</font>';
            }
            }catch(e){}
            });
            }
            });
            _attachEvent(obj, 'focus', function() {
            if (this.parentNode.className.indexOf('login_inserted') == -1) {
            this.parentNode.className += ' login_inserted';
            }
            /*if (this.parentNode.id.indexOf('secinput_') != -1) {
            showMenu({'ctrlid':'login_seccode_' + loginhash, 'win':handlekey});
            }*/
            });
            if (obj.id.indexOf('pwinver_') != -1) {
            _attachEvent(obj, 'input', function() {
            if (!this.targetInput) {
            this.targetInput = this.parentNode.getElementsByTagName('input')[0];
            }
            this.targetInput.value = this.value;
            });
            _attachEvent(obj, 'propertychange', function() {
            if (!this.targetInput) {
            this.targetInput = this.parentNode.getElementsByTagName('input')[0];
            }
            this.targetInput.value = this.value;
            });
            _attachEvent(${'$'}C('pawd', obj.parentNode, 'div')[0], 'click', function(){
            var pt = this.parentNode.getElementsByTagName('input')[1];
            if (!pt.targetInput) {
            pt.targetInput = this.parentNode.getElementsByTagName('input')[0];
            }
            var pw = pt.targetInput;
            if (pw.style.display != 'none') {
            pw.style.display = 'none';
            pt.style.display = '';
            pt.value = pw.value;
            pt.focus();
            this.innerHTML = '&#xe93c;';
            } else {
            pw.style.display = '';
            pt.style.display = 'none';
            pw.focus();
            this.innerHTML = '&#xe9c0;';
            }
            });
            }
            if (obj.parentNode.getAttribute('init') != 'yes') {
            _attachEvent(obj.parentNode, 'click', function(e) {
            e = doane(e);
            var target = e.target || e.srcElement;
            if (target.className.indexOf('intcode') != -1 || target.parentNode.className.indexOf('intcode') != -1) {
            var ctrlid = target.className.indexOf('intcode') != -1 ? target.id : target.parentNode.id;
            var menuid = ctrlid + '_menu';
            if(${'$'}(menuid) && ${'$'}(menuid).style.display == 'none'){
            ${'$'}('append_parent').append(${'$'}(menuid));
            ${'$'}(menuid).style.opacity = 0;
            showMenu({'ctrlid':ctrlid, 'ctrlclass':'intcode_show', 'win':handlekey});
            if (!${'$'}(menuid).transition) {
            ${'$'}(menuid).transition = true;
            ${'$'}(menuid).style['transition'] = 'opacity .25s ease-in-out';
            ${'$'}(menuid).style['-webkit-transition'] = 'opacity .25s ease-in-out';
            }
            ${'$'}(menuid).style.opacity = 1;
            } else {
            hideMenu();
            ${'$'}(menuid).style.opacity = 0;
            }
            return;
            }
            if (target.tagName.toLowerCase() == 'a' || target.parentNode.tagName.toLowerCase() == 'a') {
            var href = target.href || target.parentNode.href;
            if (href) window.open(href);
            return;
            }
            if (this.id.indexOf('secinput_') != -1) {
            if (!this.updated) {
            duceapp_updateseccode(loginhash, ran);
            this.updated = true;
            }
            showMenu({'ctrlid':'login_seccode_' + loginhash, 'win':handlekey});
            }
            var input = this.getElementsByTagName('input')[0];
            input.focus();
            });
            obj.parentNode.setAttribute('init', 'yes')
            }
            _attachEvent(obj, 'keyup', function(e) {
            e = e || window.event;
            var code = e.keyCode || e.which || e.charCode;
            if (code == '13') {
            ${'$'}('login_button_' + loginhash).click();
            }
            });
            }
            function duceapp_setIntcode(obj, c){
            var menuobj = obj.parentNode.parentNode;
            var ctrlid = menuobj.id.substr(0, strlen(menuobj.id)-5);
            if (${'$'}(ctrlid)) {
            var span = ${'$'}(ctrlid).getElementsByTagName("span")[0];
            var input = ${'$'}(ctrlid).getElementsByTagName("input")[0];
            if (!input) {
            input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'intcode';
            ${'$'}(ctrlid).appendChild(input);
            }
            span.innerHTML = c;
            input.value = c;
            var lis = menuobj.getElementsByTagName('li');
            for (var i=0; i<lis.length; i++) {
            if(lis[i].className) lis[i].className = '';
            }
            obj.className = 'curr';
            hideMenu();
            }
            }
            var duceapp_smstimer = {};
            var duceapp_smsinterval = 60;
            var duceapp_timeout = 0;
            var formSerializeInit = false;
            function duceapp_sendsmscode(submitkey, loginhash, sechash, calldone, callerror) {
            var formobj = sechash ? ${'$'}('loginform_' + loginhash) : (${'$'}('cellphoneform_' + loginhash) || ${'$'}('loginform_' + loginhash));
            if (trim(formobj.cellphone.value) == '') {
            formobj.cellphone.focus();
            return;
            }
            var hashkey = sechash || loginhash;
            var duceapp_smssendbtn = ${'$'}('login_smsbutton_' + hashkey);
            if (duceapp_smssendbtn.className.indexOf('sms_sendlock') != -1) {
            return;
            }
            var data = typeof formobj.serialize == 'function' ? formobj.serialize() : formSerialize.call(formobj);
            var f = ${'$'}('smscodefloat');
            var k = 'sendcodeConfirm';
            var x = new Ajax();
            x.smssendbtn = duceapp_smssendbtn;
            x.hashkey = hashkey;
            x.post('https://keylol.com/plugin.php?id=duceapp_smsauth&ac=sendcode&handlekey=sendsmscode&smscodesubmit=' + submitkey + '&inajax=1&loginhash=' + loginhash, data, function(s, x) {
            if (s == 'done') {
            if (f) {
            f.parentNode.className = f.parentNode.className.replace(/ gv_login_showcode/g, '');
            } else {
            hideWindow(k);
            }
            var hashkey = x.hashkey;
            var smssendbtn = x.smssendbtn;
            var time = duceapp_smsinterval;
            if (duceapp_smstimer[hashkey]) {
            clearInterval(duceapp_smstimer[hashkey]);
            }
            smssendbtn.className += ' sms_sendlock';
            smssendbtn.innerHTML = time + '秒后重发';
            duceapp_smstimer[hashkey] = setInterval(function(){
            if (time <= 1) {
            clearInterval(duceapp_smstimer[hashkey]);
            smssendbtn.className = smssendbtn.className.replace(/ sms_sendlock/g, '');
            smssendbtn.innerHTML = '重新发送';
            } else {
            time--;
            smssendbtn.innerHTML = time + '秒后重发';
            }
            }, 1000);
            typeof calldone == 'function' && calldone();
            return;
            } else if(s.indexOf('smscodesubmit') != -1) {			
            if (f) {
            f.innerHTML = s.replace(/<script[^\>]*?>([^\x00]*?)<\/script>/ig, '');
            var form = f.getElementsByTagName('form')[0];
            var main = form.parentNode;
            f.appendChild(form);
            f.removeChild(main);
            var h3 = document.createElement('h3');
            h3.innerHTML = '<em id="returnmessage_LnM70">请输入验证码</em><span><a class="duceapp_font" href="javascript:;" onclick="${'$'}(\''+f.parentNode.id+'\').className=${'$'}(\''+f.parentNode.id+'\').className.replace(/ gv_login_showcode/g,\'\')" title="关闭">&#xe90b;</a></span>';
            f.appendChild(h3);
            f.parentNode.className += ' gv_login_showcode';
            } else {
            var menuid = 'fwin_' + k;
            var menuObj = ${'$'}(menuid);
            if (!menuObj) {
            var menuObj = document.createElement('div');
            menuObj.id = menuid;
            menuObj.className = 'fwinmask';
            menuObj.style.display = 'none';
            ${'$'}('append_parent').appendChild(menuObj);
            evt = ' style="cursor:move" onmousedown="dragMenu(${'$'}(\'' + menuid + '\'), event, 1)" ondblclick="hideWindow(\'' + k + '\')"';
            menuObj.innerHTML = '<table cellpadding="0" cellspacing="0" class="fwin"><tr><td class="t_l"></td><td class="t_c"' + evt + '></td><td class="t_r"></td></tr><tr><td class="m_l"' + evt + '">&nbsp;&nbsp;</td><td class="m_c" id="fwin_content_' + k + '">'
            + '</td><td class="m_r"' + evt + '"></td></tr><tr><td class="b_l"></td><td class="b_c"' + evt + '></td><td class="b_r"></td></tr></table>';
            }
            ${'$'}('fwin_content_' + k).innerHTML = s.replace(/<script[^\>]*?>([^\x00]*?)<\/script>/ig, '');
            hideMenu('fwin_dialog', 'dialog');
            showMenu({'mtype':'win','menuid':menuid,'duration':3,'pos':'00','zindex':JSMENU['zIndex']['win'] + 1});
            }
            } else if(typeof callerror == 'function') {
            callerror();
            }
            evalscript(s);
            });
            }
            function duceapp_showseccode(type, url) {
            var x = new Ajax();
            x.get(url+'&inajax=1&floatlogin=1', function(s, x) {
            var f = ${'$'}('smscodefloat');
            f.innerHTML = s.replace(/<script[^\>]*?>([^\x00]*?)<\/script>/ig, '');
            var form = f.getElementsByTagName('form')[0];
            var main = form.parentNode.parentNode;
            f.appendChild(form);
            f.removeChild(main);
            var h3 = document.createElement('h3');
            h3.innerHTML = '<em id="returnmessage_LnM70">请补充下面的登录信息</em><span><a class="duceapp_font" href="javascript:;" onclick="${'$'}(\''+f.parentNode.id+'\').className=${'$'}(\''+f.parentNode.id+'\').className.replace(/ gv_login_showcode/g,\'\')" title="关闭">&#xe90b;</a></span>';
            f.appendChild(h3);
            f.parentNode.className += ' gv_login_showcode';
            evalscript(s);
            });
            }
            function duceapp_showmsgfromajax(s, j, t, cover) {
            var r = s.match(/(succeed|error)handle_([a-z0-9]+?)\(\'([^\']+)\',\s*\'([^\']+)\'/i);
            if (r != null) {
            if (r[1] == 'succeed') {
            hideWindow(r[2]||'login');
            if (j == 3) {
            window.location.href='https://keylol.com/member.php?mod=logging&action=login';
            return;
            }
            showDialog('<div class="cv_duceapp_popmsg"><div class="cv_duceapp_popmsgContent">'+r[4]+'</div></div>', 'info', t, null, cover);
            setTimeout(function(){
            hideMenu('fwin_dialog', 'dialog');
            if (!r[3] || !j) return;
            if (j == 1) {
            location.reload();
            } else if (j == 2) {
            window.location.href = r[3]
            }
            }, 3000);
            }
            }
            }
            function duceapp_loadsmsauthcss(cssname, csspath) {
            var id = cssname.replace(/duceapp_/g, '');
            if(!${'$'}('duceapp_' + id)) {
            css = document.createElement('link');
            css.id = 'duceapp_' + cssname,
            css.type = 'text/css';
            css.rel = 'stylesheet';
            css.href = csspath + cssname + '.css?IvL';
            var headNode = document.getElementsByTagName("head")[0];
            headNode.appendChild(css);
            duceapp_timeout = 100;
            }
            }
            duceapp_loadsmsauthcss('duceapp_font', 'https://keylol.com/source/plugin/duceapp_smsauth/static/css/');
            duceapp_loadsmsauthcss('smsauth', 'https://keylol.com/data/duceapp/smsauth/');
            </script><script type="text/javascript" reload="1">
            function toduceappwx(){
            if(${'$'}('cv_ricon_LnM70')){
            ${'$'}('cv_ricon_LnM70').click();
            if(scantabs.length > 1){
            scantabs[0].click();
            }
            }
            }
            var login_container = ${'$'}('login_container_LnM70');
            var inputs = ${'$'}C('px', login_container, 'input');
            for (var i=0; i<inputs.length; i++) {
            inputAddEvent(inputs[i], 'LnM70', '68638', '');
            }
            _attachEvent(${'$'}('login_button_LnM70'), 'click', function() {
            pwdclear = 1;
            if (${'$'}('codelogin_LnM70') && ${'$'}('codelogin_LnM70').style.display != 'none' && ${'$'}('agreebbrule') && !${'$'}('agreebbrule').checked) {
            return showError('同意《网站服务条款》继续登录');
            }
            ajaxpost('loginform_LnM70', 'returnmessage_LnM70', 'returnmessage_LnM70', 'onerror', null, '(function(){if(s.indexOf(\'succeedhandle_login\') != -1){evaled=true;duceapp_showmsgfromajax(s, 2);}})()');
            });
            disallowfloat = disallowfloat.replace(/login/,'');
            (function(c){
            if (!c) {
            return;
            }
            var r = c.clientHeight/c.scrollHeight;
            if (r < 1) {		
            if (!c.box) {
            c.box = document.createElement('div');
            c.box.className = 'scrollbarbox';
            c.parentNode.appendChild(c.box);
            c.bar = document.createElement('div');
            c.bar.className = 'scrollslider';
            c.bar.style.top = 0;
            c.box.appendChild(c.bar);
            var speed = typeof mswheelspec == 'undefined' ? 30 : parseInt(mswheelspec);
            speed = isNaN(speed) || speed <= 0 ? 30 : (speed < 15 ? 15 : speed);
            _attachEvent(c, 'scroll', function(e) {
            if (c.bar.isDrag) {
            return;
            }
            this.bar.style.top = this.scrollTop / (this.scrollHeight - this.clientHeight) * this.section + 'px';
            });
            _attachEvent(c, 'mousewheel', function(e) {
            e = e || window.event;			
            var g = e.wheelDelta || e.detail;
            if(g > 0 && this.scrollTop - speed <= 0){
            this.scrollTop = 0;
            return;
            }
            if (g < 0 && this.scrollTop + speed >= this.scrollHeight - this.clientHeight){
            this.scrollTop += speed;
            return;
            }
            e.preventDefault();
            if (g > 0) {
            this.scrollTop -= speed;
            }
            if (g < 0) {
            this.scrollTop += speed;
                        	}
            });
            _attachEvent(c, 'DOMMouseScroll', function(e) {
            e = e || window.event;
            var g = e.wheelDelta || e.detail;
            if(g < 0 && this.scrollTop - speed <= 0){
            this.scrollTop = 0;
            return;
            }
            if (g > 0 && this.scrollTop + speed >= this.scrollHeight - this.clientHeight){
            this.scrollTop += speed;
            return;
            }
            e.preventDefault();
            if (g > 0) {
            this.scrollTop += speed;
            }
            if (g < 0) {
            this.scrollTop -= speed;
                        	}
            });
            _attachEvent(c.bar, 'mousedown', function(e) {
            e = e || window.event;
            this.y = e.clientY;
            this.t = parseInt(this.style.top);
            this.isDrag = true;
            this.className += ' scrollslider_drag'; 
            });
            _attachEvent(document, 'mousemove', function(e) {
            if (!c.bar.isDrag) {
            return;
            }
            e = e || window.event;
                			var d = c.bar.t + e.clientY - c.bar.y;
            if (d < 0) {
            d = 0;
            } else if (d > c.section) {
            d = c.section;
            }
            c.bar.style.top = d + 'px';
            c.scrollTop = d / c.section * (c.scrollHeight - c.clientHeight);
            });
            _attachEvent(document, 'mouseup', function(e) {
            c.bar.isDrag = false;
            c.bar.className = 'scrollslider';
            });
            }
            c.section = Math.max(15, Math.ceil(c.box.clientHeight * (1 - r)));
            c.box.style.display = 'block';
            c.bar.style.height = (c.box.clientHeight - c.section) + "px";
            } else if(c.box){
            c.box.style.display = 'none';
            }
            if (!c.resize) {
            c.resize = 1;
            var func = arguments.callee;
            _attachEvent(window, 'resize', function(){func(c)});
            }
            })(${'$'}('bbrulebox'));
            </script></div>	
            <script>var blocked_words = ["\/(GTA\\s?5|GTA\\s?V|\u4fa0\u76d7\u730e\u8f66\u624b\\s?5|\u4fa0\u76d7\u730e\u8f66\u624b\\s?V|\u4fe0\u76dc\u7375\u8eca\u624b\\s?5|271590|\u4fe0\u76dc\u7375\u8eca\u624b\\s?V|Grand Theft Auto V|\u6a2a\u884c\u9738\u9053\\s?5|\u4e09\u7537\u4e00\u72d7)\/i","\/(\u6218\u57304|\u6230\u57304|\u6218\u5730\u98ce\u4e914|\u6230\u5730\u98a8\u96f24|Battlefield\\s?4|1238860)\/i","\/(\u5947\u5f02\u4eba\u751f\u672c\u8272|\u5947\u7570\u4eba\u751f\u672c\u8272|\u5947\u5f02\u4eba\u751f\uff1a\u672c\u8272|\u5947\u7570\u4eba\u751f\uff1a\u672c\u8272|\u5947\u5f02\u4eba\u751f:\u672c\u8272|\u5947\u5f02\u4eba\u751f\\s?\u672c\u8272|936790|True\\s?colors|\u95ea\u70b9\u884c\u52a8|\u761f\u75ab\u516c\u53f8|246620|Plague\\s?Inc|\u5929\u671d\u4e0a\u56fd|\u82f1\u96c4\u5b66\u9662)\/i","\/(adult|Hentai|\u7ec5\u58eb|\u9a6c\u5934\u793e|subverse|R18|\u7eb3\u8fea\u4e9a\u4e4b\u5b9d|\u7eb3\u8fea\u4e9a|\u6da9\u60c5|\u8272\u60c5|\u60e9\u6212\u9b45\u9b54|\u9b45\u9b54)\/i","\/(\u4f2a\u5a18)\/i","\/(\u6b27\u9646\u98ce\u4e914|\u6b27\u9646\u98ce\u4e91IV|Europa\\s?Universalis\\s?4|Europa\\s?Universalis\\s?IV|EU4|\u6b27\u96464|236850)\/i","\/(\u94a2\u94c1\u96c4\u5fc33|\u94a2\u4e1d3|\u94a2\u94c1\u96c4\u5fc3III|Hearts\\s?of\\s?Iron\\s?III\\Hearts\\s?of\\s?Iron\\s?3|\u94a2\u94c1\u96c4\u5fc34|\u94a2\u4e1d4)\/i","\/(\u7ef4\u591a\u5229\u4e9a2|Victoria\\s?II|Victoria\\s?2|app\/42960)\/i","\/(\u6587\u660e\u65f6\u4ee32|Age\\s?of\\s?History\\s?II|Age\\s?of\\s?History\\s?2|603850)\/i","\/(\u5fc3\u8df3\u6587\u5b66\u90e8|\u5fc3\u8df3\u6587\u5b66\u4ff1\u4e50\u90e8|\u5fc3\u8df3\u6587\u5b66\u793e)\/i","\/((?<!The[ _])humankind|1124300|2229870|2732960)\/i","\/(Detention)\/i","\/(\u8fd4\u6821)\/i","\/(\u6bd2\u67ad|drug\\s?dealer)\/i","\/(t299037-1-1)\/i","\/(\u901a\u53e4\u65af|\u7981\u533a\u5b9e\u5f55)\/i","\/(\u68af\u5b50|\u9b54\u6cd5\u5de5\u5177|shadowsocks|v2ray|xray|iepl|iplc|\u6d41\u5a92\u4f53\u89e3\u9501|nexitelly|trojan|\u9178\u9178\u4e73|clashx|openclash|vmess|\u9632\u706b\u957f\u57ce|\u8f6f\u8def\u7531|\u88ab\u5899|\u8bf4\u9b54\u6cd5|\u4e0a\u7f51\u5de5\u5177|VPN|VPS|Telegram|ti\u5b50|\u4ee3\u7406|telegra|GFW)\/i","\/(\u8981\u9b54\u6cd5|\u5f00\u9b54\u6cd5|\u7528\u9b54\u6cd5|\u6302\u9b54\u6cd5|\u5f00\u4e86\u9b54\u6cd5|\u6302\u4e86\u9b54\u6cd5|\u5168\u9b54\u6cd5|\u79d1\u5b66\u4e0a\u7f51)\/i","\/(\u6709\u9b54\u6cd5|\u6709\u68af)\/i","\/(\u533a\u9b54\u6cd5)\/i","\/(\u9b54\u6cd5\u4e0a\u7f51|\u9b54\u6cd5\u8de8\u754c|\u7ffb\u5899|\u5168\u5c40\u9b54\u6cd5|\u722c\u5899|\u6302\u68af|\u5168\u5c40|\u7528\u9b54\u6cd5)\/i","\/(\u8282\u70b9)\/i","\/(\u673a\u573a)\/i","\/(\u7f8e\u5c11\u5973)\/i","\/(\u9ed1\u624b\u515a|mafia)\/i","\/(\u53bbDRM|\u5171\u515a|\u4e2d\u592e|\u5171\u4ea7|\u4e8c\u5341\u5927|\u4e60\u8fd1\u5e73|\u6253\u8840|\u7269\u8d28\u660e|\u751f\u52a8\u6d3b|\u6233\u529b|\u6253\u6c5f\u5c71)\/i","\/\\\/\\\/.*\\.?mmosvc\\.com\/i","\/(cdkeynogap)\/i","\/(cnbeta)\/i","\/(dick)\/i","\/(302\u5de5\u5177)\/i","\/(\u672c\u5730\u53cd\u4ee3)\/i","\/(OlduBil)\/i","\/(ChatGPT)\/i","\/(dlsite)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b\u96f6\u70b9)\/i","\/(\u56fd\\\/\u6e2f\\\/\u53f0)\/i"]</script>
            <script src="source/plugin/x520_top/template/js/saved_resource.js" type="text/javascript"></script>
            <script src="source/plugin/x520_top/template/js/header.js" type="text/javascript"></script>
            <script type="text/javascript">
            TMall = TMall || {};
            TMall.BackTop = function() {
                function C() {
                    this.start = 0;
                    this.step = 100;
                    this.updateValue = function(K, N, L, M) {
                        E.scrollTo(0, -L * (K /= M) * (K - 2) + N)
                    };
                    this.getValue = function() {
                        return E.pageYOffset || G.body.scrollTop || G.documentElement.scrollTop
                    }
                }
                function H(K) {
                    if (! (this instanceof H)) {
                        return new H(K)
                    }
                    this.config = {};
                    this.scrollBtn = null;
                    this._init(K)
                }
                var I = KISSY,
                J = I.DOM,
                F = I.Event,
                G = document,
                E = window,
                B = I.UA.ie == 6,
                D = "hidden",
                A = {
                    triggerId: "J_ScrollTopBtn",
                    triggerClass: "backToTop",
                    style: "",
                    bottom: 40,
                    right: 50,
                    baseLine: 90
                };
                C.prototype.scrollTop = function(K) {
                    var Q = this,
                    L = Q.getValue(),
                    M = null,
                    P = Q.start,
                    N = 0,
                    O = parseInt(L / 100);
                    if (K !== L) {
                        N = K - L;
                        M = setInterval(function() {
                            if (P <= O) {
                                Q.updateValue(P, L, N, O);
                                P++
                            } else {
                                clearInterval(M)
                            }
                        },
                        5)
                    }
                };
                I.augment(H, {
                    _init: function(K) {
                        var L = this;
                        L.config = I.merge(K, A);
                        I.ready(function(M) {
                            L.createEl();
                            L.scrollBtn = M.get("#" + L.config.triggerId);
                            L.addEvent()
                        })
                    },
                    createEl: function() {
                        var K = this.config,
                        L = J.create("<div>", {
                            css: {
                                display: "block",
                                textIndent: "-9999px",
                                position: B ? "absolute": "fixed",
                                bottom: K.bottom,
                                right: K.right,
                                height: "55px",
                                width: "55px",
                                outline: "none",
                                opacity: "0",
                                overflow: "hidden",
                                cursor: "pointer",
                                background: "url('./source/plugin/x520_top/template/img/tops.png') no-repeat center center #57bae8"
                            },
                            id: K.triggerId,
                            "class": K.triggerClass + " hidden",
                            text: "\u8fd4\u56de\u9876\u90e8",
                            href: "#"
                        });
                        K.style || J.attr(L, "style", J.attr(L, "style") + K.style);
                        J.addStyleSheet(".hidden{visibility:hidden}");
                        J.append(L, "body")
                    },
                    addEvent: function() {
                        function K() {
                            var P = J.hasClass(L, D),
                            O = parseInt(J.scrollTop(G));
                            if (O > M && P) {
                                P = new I.Anim(L, {
                                    opacity: 0.8
                                },
                                0.3);
                                P.run();
                                J.removeClass(L, D)
                            } else {
                                if (O < M && !P) {
                                    P = new I.Anim(L, {
                                        opacity: 0
                                    },
                                    0.3);
                                    P.run();
                                    J.addClass(L, D)
                                }
                            }
                            if (B) {
                                P = J.viewportHeight(G);
                                O = O + P - N.bottom - J.height(L);
                                J.css(L, "top", O)
                            }
                        }
                        var N = this.config,
                        L = I.get("#" + N.triggerId),
                        M = parseInt(N.baseLine);
                        if (L) {
                            F.on(E, "scroll", K);
                            F.on(L, "click",
                            function(O) {
                                O.halt(); (new C(G.documentElement)).scrollTop(0)
                            });
                            F.on(L, "mouseover",
                            function() { (new I.Anim(L, {
                                    opacity: 1
                                },
                                0.3)).run()
                            });
                            F.on(L, "mouseout",
                            function() { (new I.Anim(L, {
                                    opacity: 0.8
                                },
                                0.3)).run()
                            })
                        }
                    }
                });
                H.init = function(K) {
                    H(K)
                };
                return H
            } ();
            </script>
            <script>TMall.BackTop.init();</script>
            <style type="text/css">
            #scrolltop {
                display: none;
            }
            .backToTop {
                z-index: 99;
            }
            </style>
            <div class="duceapp_cv_footer">
            <div class="duceapp_cv_wrap cl">
            <style>
            .duceapp_cv_logo, .duceapp_cv_logo img {
                height: 40px;
                background-size: contain;
                margin-top: 20px;
            }
            </style></div>
            </div>
            <script type="text/javascript">
            function initSmsWin(){
            var dv = ${'$'}('register_container') || ${'$'}('login_container');
            if(dv){
            var bh = document.documentElement.clientHeight || document.documentElement.scrollHeight;
            if(${'$'}('duceapp_cv_header')){
            bh -= ${'$'}('duceapp_cv_header').offsetHeight + 60;
            }
            var dh = dv.offsetHeight;
            var top = (bh - dh) / 2;
            if(top > 80){
            dv.style.margin = (top - 5) + 'px auto 45px';
            } else {
            dv.style.margin = '80px auto 45px';
            }		
            }
            }
            initSmsWin();
            _attachEvent(window,'resize',initSmsWin);
            </script>
            </body>
            </html>
        """.trimIndent()

        val document = Ksoup.parse(html)
        val onclick = document.getElementsByClass("sec_button")[0].attribute("onclick")?.value
        val regex = "duceapp_updateseccode\\('([^']*)','([^']*)'\\)".toRegex()
        val groupValues = regex.find(onclick!!)?.groupValues!!
        val formHash = document.getElementsByAttributeValue("name", "formhash")[0].attribute("value")?.value
        val (_, loginHash, ran) = groupValues
        assertEquals(loginHash, "LnM70")
        assertEquals(ran, "68638")
        assertEquals(formHash, "5be8682b")
    }
}