package com.liangsan.keyloler

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.liangsan.keyloler.domain.model.Slide
import com.liangsan.keyloler.domain.model.Thread
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
        val formHash =
            document.getElementsByAttributeValue("name", "formhash")[0].attribute("value")?.value
        val (_, loginHash, ran) = groupValues
        assertEquals(loginHash, "LnM70")
        assertEquals(ran, "68638")
        assertEquals(formHash, "5be8682b")
    }

    @Test
    fun parse_user_nickname() {
        val html = """
                <!DOCTYPE html>
                <html>
                <head>
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <!--<title>星鸢的个人资料 -  其乐 Keylol -  驱动正版游戏的引擎！</title>-->
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
                <link rel="preconnect" href="https://www.googletagmanager.com"><title>星鸢的个人资料 - 其乐 Keylol</title>
                <meta name="keywords" content="星鸢的个人资料" />
                <meta name="description" content="星鸢的个人资料 ,其乐 Keylol" />
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
                    </script><link rel="stylesheet" type="text/css" href="data/cache/style_7_common.css?emL" /><link rel="stylesheet" type="text/css" href="data/cache/style_7_home_space.css?emL" /><script type="text/javascript">var STYLEID = '7', STATICURL = 'static/', IMGDIR = 'static/image/common', VERHASH = 'emL', charset = 'utf-8', discuz_uid = '1056547', cookiepre = 'dz_2132_', cookiedomain = '', cookiepath = '/', showusercard = '1', attackevasive = '0', disallowfloat = 'login|newthread', creditnotice = '1|体力|点,3|蒸汽|克,4|动力|点,6|绿意|,8|可用改名次数|次', defaultstyle = '', REPORTURL = 'aHR0cHM6Ly9rZXlsb2wuY29tL3N1aWQtNTkzNjY3', SITEURL = 'https://keylol.com/', JSPATH = 'data/cache/', DYNAMICURL = '';</script>
                <script src="data/cache/common.js?emL" type="text/javascript"></script>
                <meta name="application-name" content="其乐 Keylol"/>
                <meta name="msapplication-tooltip" content="其乐 Keylol"/><meta name="msapplication-task"
                      content="name=社区;action-uri=https://keylol.com/forum.php;icon-uri=https://keylol.com/static/image/common/bbs.ico"/><link rel="stylesheet" id="css_widthauto" type="text/css" href="data/cache/style_7_widthauto.css?emL"/>
                <script type="text/javascript">HTMLNODE.className += ' widthauto'</script><script src="data/cache/home.js?emL" type="text/javascript"></script><link rel="stylesheet" type="text/css" href="template/steamcn_metro/src/css/tiny-bootstrap.css"/>
                <link rel="stylesheet" type="text/css" href="template/steamcn_metro/src/css/site.css?emL"/>
                <link rel="stylesheet" type="text/css" href="template/steamcn_metro/src/css/new-style.css?emL"/>
                <script src="template/steamcn_metro/src/js/jquery-2.2.4.min.js" type="text/javascript"></script>
                <script src="template/steamcn_metro/src/js/bootstrap-3.3.7.min.js" type="text/javascript"></script>
                <script>jq = jQuery.noConflict();</script>
                <script src="template/steamcn_metro/src/js/site.js?emL" type="text/javascript"></script><!--Append, Sing_did--><!--[if IE 6]>
                <script src="template/steamcn_metro/src/js/iepngfix_tilebg.js" type="text/javascript"></script><![endif]-->
                <style>    <!--
                {
                    loop ${'$'} _G [ 'setting' ] [ 'navs' ] ${'$'} nav
                }
                -->
                .tiled-menu li#

                ${'$'}
                nav[navid] a {
                    background-image: url(template/steamcn_metro/src/img/header/.png);
                }

                <!--
                {
                /
                loop
                }
                -->
                .tiled-menu li a {
                    behavior: url(template/steamcn_metro/src/js/iepngfix.htc)
                }</style><!--Append, Sing_did--></head>
                <body id="nv_home"
                      class="pg_space"
                      onkeydown="if(event.keyCode==27) return false;">
                <div id="append_parent"></div>
                <div id="ajaxwaitid"></div>    <nav id="nav-menu" class="align-center">
                    <ul>
                        <li class="has-sub"><a href="javascript:void(0)"> <span>蒸汽</span> </a>
                            <ul>
                                <li><a href="f161-1"> <span>热点</span> </a></li>
                                <li><a href="f319-1"> <span>福利</span> </a></li>
                                <li><a href="f234-1"> <span>购物</span> </a></li>
                                <li><a href="f271-1"> <span>慈善包</span> </a></li>
                                <li><a href="f257-1"> <span>汉化</span> </a></li>
                                <li><a href="f127-1"> <span>工具</span> </a></li>
                                <li><a href="f235-1"> <span>成就</span> </a></li>
                                <li><a href="f129-1"><span>互鉴</span></a></li>
                                <li><a href="f254-1"><span>分享互赠</span></a></li>
                            </ul>
                        </li>
                        <li class="has-sub"><a href="javascript:void(0)"> <span>互助</span> </a>
                            <ul>
                                <li><a href="f301-1"> <span>平台技术</span> </a></li>
                                <li><a href="f302-1"> <span>平台购物</span> </a></li>
                                <li><a href="f304-1"> <span>其乐社区</span> </a></li>
                                <li><a href="f318-1"> <span>资源索取</span> </a></li>
                                <li><a href="f303-1" style="padding: 10px 10px; line-height: 20px;"> <span>游戏成就汉化</span> </a></li>
                                <li><a href="f322-1" style="padding: 10px 10px; line-height: 20px;"> <span>软件硬件其他</span> </a></li>
                            </ul>
                        </li>
                        <li class="has-sub"><a href="javascript:void(0)"> <span>平台</span> </a>
                            <ul>
                                <li><a href="f275-1"><span>主机</span></a></li>
                                <li><a href="f328-1"><span>手游</span></a></li>
                                <li><a href="f232-1"> <span>Origin</span> </a></li>
                                <li><a href="f274-1"> <span>uPlay</span> </a></li>
                                <li><a href="f276-1"> <span>GOG</span> </a></li>
                                <li><a href="f345-1"> <span>战网</span> </a></li>
                                <li><a href="f326-1"><span>Windows</span></a></li>
                                <li><a href="f335-1"><span>Epic</span></a></li>
                                <li><a href="f316-1"> <span>杉果</span> </a></li>
                                <li><a href="f332-1"><span>方块</span></a></li>
                                <li><a href="f325-1"><span>WeGame</span></a></li>
                                <li><a href="f277-1"> <span>其他</span> </a></li>
                            </ul>
                        </li>
                        <li class="has-sub"><a href="javascript:void(0)"> <span>休闲</span> </a>
                            <ul>
                                <li><a href="f148-1"> <span>水区</span> </a></li>
                                <li><a href="f259-1"> <span>摄影旅游</span> </a></li>
                                <li><a href="f273-1"> <span>美食烹饪</span> </a></li>
                                <li><a href="f200-1"> <span>软硬兼施</span> </a></li>
                                <li><a href="f330-1"> <span>开箱</span> </a></li>
                            </ul>
                        </li>
                        <li class="has-sub"><a href="forum.php"> <span>游戏</span> </a>
                            <ul>
                                <!--<li class="dual-line"><a href="f251-1"><p>游戏综合</p>
                                  <!--  <p>交换观点/资源</p></a></li> -->
                                <li><a href="f251-1"><span>游戏综合</span></a></li>
                                <li><a href="f305-1"><span>刀塔</span></a></li>
                                <li><a href="f337-1"><span>明日方舟</span></a></li>
                                <li><a href="f299-1"><span>反恐精英</span></a></li>
                                <li><a href="f244-1"><span>威乐</span></a></li>
                                <li><a href="f246-1"><span>艺电</span></a></li>
                                <li><a href="f245-1"><span>育碧</span></a></li>
                                <li><a href="f248-1"><span>动视暴雪</span></a></li>
                                <li><a href="f339-1"><span>虚拟现实</span></a></li>
                            </ul>
                        </li>
                                <!-- <li class="nav-logo"><a href="/"> <span>&nbsp;</span> </a></li> -->
                        <li class="has-sub"></a>
                        <li class="has-sub nav-forum"><a href="javascript:void(0)"> <span>服务</span> </a>
                            <ul>
                                <li><a href="f140-1"> <span>公告</span> </a></li>
                                <li><a href="f197-1"> <span>反馈</span> </a></li>
                                <li><a href="f238-1"> <span>活动</span> </a></li>
                                <li><a href="steamcn_steam_connect-statistics.html"> <span>排行榜</span> </a></li>
                                <li><a href="javascript:setcookie(`dark_mode`, 1-getcookie(`dark_mode`), 3600*24*365);location.reload();"> <span>夜间</span> </a></li>
                                <li><a href="forum.php?mod=collection"> <span>淘帖</span> </a></li>
                                <li><a href="misc.php?mod=tag"> <span>标签</span> </a></li>
                                <li><a href="keylol_forum_subscription-threadlist.html"> <span>订阅</span> </a></li>
                            </ul>
                        </li>
                        <li class="has-sub nav-keylol"><a href="https://store.steampowered.com/curator/9730205-Keylol-Player-Club/" target="_blank"> <span>评测</span> </a>
                            <ul>
                                <li class="dual-line"><a href="https://store.steampowered.com/curator/9730205-Keylol-Player-Club/" target="_blank"><p>其乐 Keylol 鉴赏家</p>
                                    <p>游戏评测社区</p></a></li>
                            </ul>
                        </li>
                        <li class="has-sub nav-7l"><a href="steamcn_gift-7l.html"> <span>赠楼</span> </a>
                            <ul>
                                <li class="dual-line"><a href="steamcn_gift-7l.html"><p>互赠平台</p>
                                    <p>抽取正版游戏</p></a></li>
                            </ul>
                        </li>
                        <li class="has-sub nav-trade"><a href="f201-1"> <span>交易</span> </a>
                            <ul>
                                <li class="dual-line"><a href="f201-1"><p>交易中心</p>
                                    <p>便捷游戏市集</p></a></li>
                            </ul>
                        </li>
                        <li class="has-sub nav-srcn nav-last"><a href="https://steamrepcn.com/" target="_blank"> <span>信誉</span> </a>
                            <ul>
                                <li class="dual-line"><a href="https://steamrepcn.com/" target="_blank"><p>SRCN</p>
                                    <p>反诈骗数据库</p></a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
                <header class="tb-container">
                    <a id="nav-logo" href="/">
                        <span>原蒸汽动力社区</span>
                    </a>
                    <section id="nav-additional" class="pull-right">
                        <div id="nav-search-bar">
                            <div class="search-bar" style="display: none;">
                    <!-- <img src="template/steamcn_metro/src/img/common/nav-search.png"> -->
                    <form class="search-bar-form" method="post" action="search.php?searchsubmit=yes" target="_blank">
                        <input type="hidden" name="formhash" value="8b4647a7">
                        <input type="hidden" name="srchtype" value="title">
                        <input type="hidden" name="srhfid" value="0">
                        <input type="hidden" name="srhlocality" value="home::space">
                                <input class="search-box" type="search" name="srchtxt" placeholder="请输入搜索内容">
                        <input type="hidden" name="mod" value="baidu">
                        <div class="dropdown">
                            <button type="button" class="btn btn-default" data-toggle="dropdown"><span>百度</span><span class="caret"></span></button>
                            <ul class="dropdown-menu">
                                                                                <li><a href="javascript:void(0)" data-mod="forum">帖子</a></li>
                                                                                                                <li><a href="javascript:;" data-mod="collection">淘贴</a></li>                <li class="divider"></li>
                                <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="google">Google</a></li>
                                <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="baidu">百度</a></li>
                                <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="bing">必应</a></li>
                            </ul>
                        </div>
                        <button class="btn btn-default" type="submit" name="searchsubmit" value="true"></button>
                    </form>
                </div>
                        </div>
                        <div id="nav-user-action-bar">
                            <ul class="list-inline">                              <span id="xunjie_attention_s"></span>                 <li><a class="btn btn-user-action"
                                       href="home.php?mod=space&amp;do=pm"> 消息                        </a></li>
                                <li>
                                    <a class="btn btn-user-action"
                                       href="home.php?mod=space&amp;do=notice"> 提醒                        </a></li>
                                <li class="dropdown"><a href="suid-1056547"> <img class="avatar img-circle" src="https://keylol.com/uc_server/data/avatar/001/05/65/47_avatar_middle.jpg?ts=1696781036">
                                </a>
                                    <ul class="dropdown-menu dropdown-menu-right">
                                        <li><a href="home.php?mod=spacecp">设置</a></li>
                                                                    <li><a href="connect.php?mod=config">绑定 QQ</a></li>                        <li class="divider"></li>                                                                                                    <li><a href="home.php?mod=space&do=friend" _style="background-image:url(https://keylol.com/static/image/feed/friend_b.png) !important">好友</a></li>                                                                                                                            <li><a href="forum.php?mod=guide&view=my" _style="background-image:url(https://keylol.com/static/image/feed/thread_b.png) !important">帖子</a></li>                                                                                                                            <li><a href="home.php?mod=space&do=favorite&type=thread">收藏</a></li>                                                                                                                            <li><a href="home.php?mod=magic" _style="background-image:url(https://keylol.com/static/image/feed/magic_b.png) !important">道具</a></li>                                                                                                                            <li><a href="home.php?mod=medal" _style="background-image:url(https://keylol.com/static/image/feed/medal_b.png) !important">勋章</a></li>                                                                                                                            <li><a href="forum.php?mod=collection&op=my" _style="background-image:url(https://keylol.com/static/image/feed/collection_b.png) !important">淘帖</a></li>                                                                                                                                                                                                                                                                            <li><a href="keylol_forum_subscription-threadlist.html">订阅</a></li>
                                        <li class="divider"></li>
                                                                                                                                        <li><a href="member.php?mod=logging&amp;action=logout&amp;formhash=8b4647a7">退出</a></li>
                                    </ul>
                                </li>                                                </ul>
                        </div>
                    </section>
                </header>    <div class="floatcontainer doc_header wp cl">            <div class="index_navi">
                        <div class="index_navi_left">
                            <div class="index_left_title">
                                <div>关注重点</div>
                            </div>
                            <div class="index_left_detail">                                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="t531046-1-1" target="_blank">SteamCN 新品牌其乐</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="t307370-1-1" target="_blank">社区帖子代码指南</a></div>
                                </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t521974-1-1" target="_blank">Steam和社区脚本索引</a></div>
                                </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t450232-1-1" target="_blank">【获得积分】用户组手册</a></div>
                                </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t353357-1-1" target="_blank">慈善包购物手册</a></div>
                                </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t803672-1-1" target="_blank">入库其他区的免费内容</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t301016-1-1" target="_blank">ASF 挂卡配置与命令教程</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t234499-1-1" target="_blank">SteamDB 使用指南</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/forum.php?mod=collection&action=view&ctid=72" target="_blank">Steam低价获取余额</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="t96430-1-1" target="_blank"> 赠楼使用手册</a></div>
                                </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t152125-1-1" target="_blank">可免费 +1</a></div>
                                </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t675265-1-1" target="_blank">Steam汉化补丁汇总</a></div>
                                </div>                                                </div>
                        </div>            <div class="index_navigation_mid rnd_ai_h" style="line-height:107px;height:107px"><a href="https://keylol.com/hello/518790" target="_blank"><img src="https://blob.keylol.com/forum/202412/02/191256uuxa33j0aqaxa7gw.jpg?a=a" height="107" width="240" border="0"></a></div>        <div class="index_navi_right">
                            <div class="index_right_title">
                                <div>站点公告</div>
                            </div>
                            <div class="index_right_detail">
                                <div class="blue_dot"></div>
                                <div><a href="/t273107-1-1" target="_blank">游戏开发和发行的咨询服务</a></div>
                            </div>
                            <div class="index_right_detail">
                                <div class="dot" style="margin-top:3px;"></div>
                                <div style="margin-top:-2px;"><a href="/t46755-1-1" target="_blank">其乐用户守则</a></div>
                            </div>
                            <div class="index_right_detail" style="margin-top:-5px;">
                                <div class="dot"></div>
                                <div><a href="/advertising_with_keylol_20210216.pdf?emL" target="_blank">在其乐投放广告</a></div>
                            </div>
                        </div>
                    </div>            <div class="p_pop h_pop" id="mn_userapp_menu" style="display: none"></div>                        <style>
                .html5video { width: 100%; height: 200px; overflow: hidden; max-width:690px; }
                @media only screen and (min-width: 360px) {
                .html5video { width: 100%; height: 160px; }
                }
                @media only screen and (min-width: 480px) {
                .html5video { width: 90%; height: 300px; }
                }
                @media only screen and (min-width: 769px) {
                .html5video { width: 80%; height: 360px; }
                }
                @media only screen and (min-width: 1200px) {
                .html5video { /*width: 550px;*/ height: 400px; }
                }
                </style><link rel="stylesheet" href="https://keylol.com/source/plugin/steamcn_steam_connect/static/style.css"><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/jquery.cookie.js?emL'><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/json2.js?emL'></script><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/jquery.storageapi.min.js?emL'></script><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/recent_smiles.js?emL'></script><script type="text/javascript" charset="UTF-8" src="https://keylol.com/source/plugin/steamcn_lang/static/tongwen.js?emL_v2"></script>    </div>    <div id="wp" class="wp cl" style="min-width: 1206px;">
                <div id="pt" class="bm cl">
                <div class="z">
                <a href="./" class="nvhm" title="首页">其乐 Keylol</a> <em>&rsaquo;</em>
                <a href="suid-593667">星鸢</a> <em>&rsaquo;</em>
                个人资料
                </div>
                </div>
                <style id="diy_style" type="text/css"></style>
                <div class="wp">
                <!--[diy=diy1]--><div id="diy1" class="area"></div><!--[/diy]-->
                </div><div id="uhd">
                <div class="mn">
                <ul>
                <li class="addf">
                <a href="home.php?mod=spacecp&amp;ac=friend&amp;op=add&amp;uid=593667&amp;handlekey=addfriendhk_593667" id="a_friend_li_593667" onclick="showWindow(this.id, this.href, 'get', 0);" class="xi2">加为好友</a>
                </li>
                <li class="pm2">
                <a href="home.php?mod=spacecp&amp;ac=pm&amp;op=showmsg&amp;handlekey=showmsg_593667&amp;touid=593667&amp;pmid=0&amp;daterange=2" id="a_sendpm_593667" onclick="showWindow('showMsgBox', this.href, 'get', 0)" title="发送消息">发送消息</a>
                </li>
                </ul>
                </div>
                <div class="h cl">
                <div class="icn avt"><a href="suid-593667"><img src="https://keylol.com/uc_server/data/avatar/000/59/36/67_avatar_small.jpg?ts=1695310525" onerror="this.onerror=null;this.src='https://keylol.com/uc_server/images/noavatar.svg'" alt="UID: 593667 " /></a></div>
                <h2 class="mt">星鸢</h2>
                <p>
                <a href="https://keylol.com/?593667" class="xg1">https://keylol.com/?593667</a>
                </p>
                </div>

                <ul class="tb cl" style="padding-left: 75px;">
                <li><a href="home.php?mod=space&amp;uid=593667&amp;do=thread&amp;view=me&amp;from=space">主题</a></li>
                <li class="a"><a href="home.php?mod=space&amp;uid=593667&amp;do=profile&amp;from=space">个人资料</a></li>
                </ul>
                </div>
                <div id="ct" class="ct1 wp cl">
                <div class="mn">
                <!--[diy=diycontenttop]--><div id="diycontenttop" class="area"></div><!--[/diy]-->
                <div class="bm bw0">
                <div class="bm_c">
                <div class="bm_c u_profile">

                <div class="pbm mbm bbda cl" style="border-bottom: none; margin-bottom: initial !important; padding-bottom: initial !important;">
                <h2 class="mbn">星鸢<span class="xw0">(UID: 593667)</span>
                </h2>
                <ul class="pf_l cl pbm mbm">
                <li><em>邮箱状态</em>已验证</li>
                </ul>
                <ul>
                <li><em class="xg1">个人签名&nbsp;&nbsp;</em><table><tr><td><strong>覆盖数码，科技，游戏，娱乐相关新闻和数据<br />
                不定期发布文章<br />
                </strong><img id="aimg_WpfRN" onclick="zoom(this, this.src, 0, 0, 1)" class="zoom" src="https://i.pinimg.com/originals/8c/c9/3d/8cc93d290995109bd07b29698ef1804b.gif" onmouseover="img_onmouseoverfunc(this)" onload="thumbImg(this)" border="0" alt="" style="" referrerpolicy="no-referrer" /></td></tr></table></li></ul>
                <ul class="cl bbda pbm mbm">
                <li>
                <em class="xg2">统计信息</em>
                <a href="home.php?mod=space&amp;uid=593667&amp;do=friend&amp;view=me&amp;from=space" target="_blank">好友数 6</a>
                <span class="pipe">|</span><a href="home.php?mod=space&uid=593667&do=thread&view=me&type=reply&from=space" target="_blank">回帖数 5091</a>
                <span class="pipe">|</span>
                <a href="home.php?mod=space&uid=593667&do=thread&view=me&type=thread&from=space" target="_blank">主题数 1764</a>
                </li>
                </ul>
                </div>
                <div class="pbm mbm bbda cl">
                <h2 class="mbn">勋章</h2>
                <p class="md_ctrl">
                <a href="home.php?mod=medal"><img src="static//image/common/medal_md2020_powerengine_lv4.png" alt="▘冲程循环 四重" id="md_79" onmouseover="showMenu({'ctrlid':this.id, 'menuid':'md_79_menu', 'pos':'12!'});" />
                <img src="static//image/common/medal_md2016_H2O.png" alt="▘干渴水滴" id="md_55" onmouseover="showMenu({'ctrlid':this.id, 'menuid':'md_55_menu', 'pos':'12!'});" />
                <img src="static//image/common/medal_md2016_SEV5.png" alt="▘片十字花瓣" id="md_54" onmouseover="showMenu({'ctrlid':this.id, 'menuid':'md_54_menu', 'pos':'12!'});" />
                </a>
                </p>
                </div><div id="md_79_menu" class="tip tip_4" style="display: none;">
                <div class="tip_horn"></div>
                <div class="tip_c">
                <h4>▘冲程循环 四重</h4>
                <p>「动力」积分勋章第四个等级</p>
                </div>
                </div>
                <div id="md_55_menu" class="tip tip_4" style="display: none;">
                <div class="tip_horn"></div>
                <div class="tip_c">
                <h4>▘干渴水滴</h4>
                <p>优质内容贡献者</p>
                </div>
                </div>
                <div id="md_54_menu" class="tip tip_4" style="display: none;">
                <div class="tip_horn"></div>
                <div class="tip_c">
                <h4>▘片十字花瓣</h4>
                <p>在线时间(小时) ≥ 999   并且   在线时间(小时) / 主题数 ≤ 9.9</p>
                </div>
                </div>
                <div class="pbm mbm bbda cl">
                <h2 class="mbn">活跃概况</h2>
                <ul>
                <li><em class="xg1">用户组&nbsp;&nbsp;</em><span style="color:" class="xi2" onmouseover="showTip(this)" tip="积分 16014, 距离下一级还需 33986 积分"><a href="home.php?mod=spacecp&amp;ac=usergroup&amp;gid=37" target="_blank">旗舰会员</a></span> <img src="https://blob.keylol.com/common/usergroup/m7.png" alt="" class="vm" /> </li>
                </ul>
                <ul id="pbbs" class="pf_l">
                <li><em>在线时间</em>3075 小时</li><li><em>注册时间</em>2017-12-4 02:58</li>
                <li><em>最后访问</em>2024-12-6 12:51</li>
                <li><em>IP 属地</em>美国</li>
                <li><em>上次活动时间</em>2024-12-6 11:01</li><li><em>上次发表时间</em>2024-12-6 10:35</li><li><em>所在时区</em>(GMT -08:00) 太平洋时间(美国和加拿大), 提华纳</li>
                </ul>
                </div>
                <div id="psts" class="cl">
                <h2 class="mbn">统计信息</h2>
                <ul class="pf_l">
                <li><em>已用空间</em>   0 B </li>
                <li><em>积分</em>16014</li><li><em>体力</em>14250 点</li>
                <li><em>蒸汽</em>1226 克</li>
                <li><em>动力</em>1138 点</li>
                <li><em>绿意</em>0 </li>
                <li><em>可用改名次数</em>0 次</li>
                </ul>
                </div>
                <script>var sff_ka = document.createElement('li');sff_ka.innerHTML = '<em>最佳答案</em>6';document.querySelector('#psts > ul').appendChild(sff_ka);</script>
                <div class="ptm mtm btda cl">
                <h2 class="mbn">备注信息 - <a href="javascript:;" onclick="showWindow('freeaddon_remarks', 'plugin.php?id=freeaddon_remarks&uid=593667', 'get', 0);">修改备注信息</a></h2>
                <ul id="pbbs" class="pf_l cl">
                </ul>

                <ul>
                <li>
                <em>详细备注</em><br>

                </li>
                </ul>
                </div>
                <br><div class='pbm mbm bbda cl'>
                <h2 class='mbn'>赠楼信息</h2>
                <ul class='pf_l'>
                <li><em>创建</em><a href='https://keylol.com/plugin.php?id=steamcn_gift:search&q=593667&type=create'>0 ( &#36; 0 )</a></li>
                <li><em>参加</em><a href='https://keylol.com/plugin.php?id=steamcn_gift:search&q=593667&type=enter'>0 ( &#36; 0 )</a></li>
                <li><em>获赠</em><a href='https://keylol.com/plugin.php?id=steamcn_gift:search&q=593667&type=win'>0</a></li>
                <li><em>Steam资料页</em><a href='https://steamcommunity.com/profiles/76561198342520744'>点此进入</a></li>
                <li><em>赠楼率</em>0%</li>
                <li><em>好评率</em>NA%</li>
                <li><em>获赠率</em>0%</li>
                <li><em>馈赠比</em>0%</li>
                <li><em>赠出礼物份数</em>0</li>
                </ul>
                </div></div><!--[diy=diycontentbottom]--><div id="diycontentbottom" class="area"></div><!--[/diy]--></div>
                </div>
                </div>
                </div>

                <div class="wp mtn">
                <!--[diy=diy3]--><div id="diy3" class="area"></div><!--[/diy]-->
                </div>
                </div>
                <div class="rnd_ai_f"><a href="https://www.humblebundle.com/membership?utm_source=Keylol&utm_medium=Paid&utm_campaign=December_Choice24" target="_blank"><img src="https://blob.keylol.com/forum/202412/03/235950lfjvrbm6zz1fmrfr.jpg?a=a" height="120" width="1206" border="0"></a></div><div class="subforunm_foot">
                <div class="subforunm_foot_bg"><img src="pic/foot_bg.png"></div>
                <div class="subforunm_foot_banner">
                <div class="subforunm_foot_text">
                <div>　　作为民间站点，自 2004 年起为广大中文 Steam 用户提供技术支持与讨论空间。历经二十余载风雨，如今已发展为国内最大的正版玩家据点。</div>
                </div>
                <div class="subforunm_foot_text_bottom">
                <div>
                <div><br /><a href="forum.php?mod=guide&amp;view=newthread" title="列表模式 - 最新主题">列表模式</a> · <div class="wechat-hover-qrcode"><div class="qrcode"><img src="/template/steamcn_metro/src/img/wechat-qrcode-v2.jpg"></div><a href="/t326248-1-1" title="其乐社区官方微信公众号">微信公众号</a></div> · <a href="http://weibo.com/steamcn" target="_blank" title="其乐社区官方微博">微博</a> · <a href="https://space.bilibili.com/531279346" target="_blank" title="其乐茸茸">Bilibili频道</a> · <a href="//steamcommunity.com/groups/keylol-player-club" target="_blank" title="其乐社区官方 Steam 群组">Steam 群组</a> · <a href="http://tieba.baidu.com/f?ie=utf-8&amp;kw=keylol" target="_blank" title="百度 Keylol 贴吧">贴吧</a> · <a href="t8598-1-1" target="_blank" title="其乐社区官方 Q 群">QQ群</a>&nbsp;</div>
                <div>Keylol 其乐 &copy;2004-2024 Chinese Steam User Fan Site.</div>
                </div>
                </div>
                </div>
                <script>var blocked_words = ["\/(GTA\\s?5|GTA\\s?V|\u4fa0\u76d7\u730e\u8f66\u624b\\s?5|\u4fa0\u76d7\u730e\u8f66\u624b\\s?V|\u4fe0\u76dc\u7375\u8eca\u624b\\s?5|271590|\u4fe0\u76dc\u7375\u8eca\u624b\\s?V|Grand Theft Auto V|\u6a2a\u884c\u9738\u9053\\s?5|\u4e09\u7537\u4e00\u72d7)\/i","\/(\u6218\u57304|\u6230\u57304|\u6218\u5730\u98ce\u4e914|\u6230\u5730\u98a8\u96f24|Battlefield\\s?4|1238860)\/i","\/(\u5947\u5f02\u4eba\u751f\u672c\u8272|\u5947\u7570\u4eba\u751f\u672c\u8272|\u5947\u5f02\u4eba\u751f\uff1a\u672c\u8272|\u5947\u7570\u4eba\u751f\uff1a\u672c\u8272|\u5947\u5f02\u4eba\u751f:\u672c\u8272|\u5947\u5f02\u4eba\u751f\\s?\u672c\u8272|936790|True\\s?colors|\u95ea\u70b9\u884c\u52a8|\u761f\u75ab\u516c\u53f8|246620|Plague\\s?Inc|\u5929\u671d\u4e0a\u56fd|\u82f1\u96c4\u5b66\u9662)\/i","\/(adult|Hentai|\u7ec5\u58eb|\u9a6c\u5934\u793e|subverse|R18|\u7eb3\u8fea\u4e9a\u4e4b\u5b9d|\u7eb3\u8fea\u4e9a|\u6da9\u60c5|\u8272\u60c5|\u60e9\u6212\u9b45\u9b54|\u9b45\u9b54)\/i","\/(\u4f2a\u5a18)\/i","\/(\u6b27\u9646\u98ce\u4e914|\u6b27\u9646\u98ce\u4e91IV|Europa\\s?Universalis\\s?4|Europa\\s?Universalis\\s?IV|EU4|\u6b27\u96464|236850)\/i","\/(\u94a2\u94c1\u96c4\u5fc33|\u94a2\u4e1d3|\u94a2\u94c1\u96c4\u5fc3III|Hearts\\s?of\\s?Iron\\s?III\\Hearts\\s?of\\s?Iron\\s?3|\u94a2\u94c1\u96c4\u5fc34|\u94a2\u4e1d4)\/i","\/(\u7ef4\u591a\u5229\u4e9a2|Victoria\\s?II|Victoria\\s?2|app\/42960)\/i","\/(\u6587\u660e\u65f6\u4ee32|Age\\s?of\\s?History\\s?II|Age\\s?of\\s?History\\s?2|603850)\/i","\/(\u5fc3\u8df3\u6587\u5b66\u90e8|\u5fc3\u8df3\u6587\u5b66\u4ff1\u4e50\u90e8|\u5fc3\u8df3\u6587\u5b66\u793e)\/i","\/((?<!The[ _])humankind|1124300|2229870|2732960)\/i","\/(Detention)\/i","\/(\u8fd4\u6821)\/i","\/(\u6bd2\u67ad|drug\\s?dealer)\/i","\/(t299037-1-1)\/i","\/(\u901a\u53e4\u65af|\u7981\u533a\u5b9e\u5f55)\/i","\/(\u68af\u5b50|\u9b54\u6cd5\u5de5\u5177|shadowsocks|v2ray|xray|iepl|iplc|\u6d41\u5a92\u4f53\u89e3\u9501|nexitelly|trojan|\u9178\u9178\u4e73|clashx|openclash|vmess|\u9632\u706b\u957f\u57ce|\u8f6f\u8def\u7531|\u88ab\u5899|\u8bf4\u9b54\u6cd5|\u4e0a\u7f51\u5de5\u5177|VPN|VPS|Telegram|ti\u5b50|\u4ee3\u7406|telegra|GFW)\/i","\/(\u8981\u9b54\u6cd5|\u5f00\u9b54\u6cd5|\u7528\u9b54\u6cd5|\u6302\u9b54\u6cd5|\u5f00\u4e86\u9b54\u6cd5|\u6302\u4e86\u9b54\u6cd5|\u5168\u9b54\u6cd5|\u79d1\u5b66\u4e0a\u7f51)\/i","\/(\u6709\u9b54\u6cd5|\u6709\u68af)\/i","\/(\u533a\u9b54\u6cd5)\/i","\/(\u9b54\u6cd5\u4e0a\u7f51|\u9b54\u6cd5\u8de8\u754c|\u7ffb\u5899|\u5168\u5c40\u9b54\u6cd5|\u722c\u5899|\u6302\u68af|\u5168\u5c40|\u7528\u9b54\u6cd5)\/i","\/(\u8282\u70b9)\/i","\/(\u673a\u573a)\/i","\/(\u7f8e\u5c11\u5973)\/i","\/(\u9ed1\u624b\u515a|mafia)\/i","\/(\u53bbDRM|\u5171\u515a|\u4e2d\u592e|\u5171\u4ea7|\u4e8c\u5341\u5927|\u4e60\u8fd1\u5e73|\u6253\u8840|\u7269\u8d28\u660e|\u751f\u52a8\u6d3b|\u6233\u529b|\u6253\u6c5f\u5c71)\/i","\/\\\/\\\/.*\\.?mmosvc\\.com\/i","\/(cdkeynogap)\/i","\/(cnbeta)\/i","\/(dick)\/i","\/(302\u5de5\u5177)\/i","\/(\u672c\u5730\u53cd\u4ee3)\/i","\/(OlduBil)\/i","\/(ChatGPT)\/i","\/(dlsite)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b\u96f6\u70b9)\/i","\/(\u56fd\\\/\u6e2f\\\/\u53f0)\/i"]</script>
                <link rel="stylesheet" type="text/css" href="source/plugin/xunjie_attention/template/xunjie_attention.css">
                <div id="xunjie_attention_userlist" style="position:absolute;display:none;border:1px solid;min-width:0px;border-color:#DDD;background:#FEFEFE;width:220px;z-index:301;" onmouseleave="xunjie_attention_mOut(this);"></div>
                <div id="xunjie_attention_show" style="position:absolute;display:none;border:1px solid;min-width:60px;border-color:#DDD;background:#FEFEFE;z-index:301;" onmouseleave="xunjie_attention_mOut(this);"></div>
                <div id="xunjie_attention_w" style="display:none;"></div>
                <script>
                function xunjie_attention_getTop(e) {
                var offset = e.offsetTop;
                if(e.offsetParent != null) {
                offset += xunjie_attention_getTop(e.offsetParent);
                }         
                return offset;
                }
                function xunjie_attention_getLeft(e) {
                var offset = e.offsetLeft;
                if(e.offsetParent != null) {
                offset += xunjie_attention_getLeft(e.offsetParent);
                } 
                return offset;
                } 
                function xunjie_attention_follow(a, href) {
                if(a == 1) {
                document.getElementById('follow_a').href = href;
                document.getElementById('follow_li').classList.remove('fo_add');
                document.getElementById('follow_li').classList.add('fo_cancel');
                }
                if(a == 2) {
                document.getElementById('follow_a').href = href;
                document.getElementById('follow_li').classList.remove('fo_cancel');
                document.getElementById('follow_li').classList.add('fo_add');
                }
                }
                function xunjie_attention_mOver(div, id, link) {
                document.getElementById(id).style.display = '';
                document.getElementById(id).style.left = xunjie_attention_getLeft(div) + 'px';
                document.getElementById(id).style.top = xunjie_attention_getTop(div) + div.offsetHeight + 0.3 + 'px';
                if(typeof xunjie_attention_isexist == 'undefined') {
                xunjie_attention_isexist = 1;
                ajaxget(link, id, id);
                }
                }
                function xunjie_attention_mleave(id) {
                    var div = document.getElementById(id);
                    var x = event.clientX + window.pageXOffset;
                    var y = event.clientY + window.pageYOffset;
                    var divx1 = div.offsetLeft;
                    var divy1 = div.offsetTop;
                    var divx2 = div.offsetLeft + div.offsetWidth;
                    var divy2 = div.offsetTop + div.offsetHeight;
                if(x < divx1 || x > divx2 || y < divy1 || y > divy2) {
                div.style.display = 'none';
                }	
                }
                function xunjie_attention_mOut(div) {
                div.style.display = 'none';
                }
                function xunjie_attention_userlist(div, id) {
                if(div.href != 'javascript:;') {
                ajaxget(div.href, id);
                div.href = 'javascript:;';
                }
                }
                </script>
                <script>xunjie_attention_2 = '关注TA';xunjie_attention_5 = '取消关注';ajaxget('plugin.php?id=xunjie_attention:attention&type=5&formhash=8b4647a7', 'xunjie_attention_s', 'xunjie_attention_w');</script>
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
                <div class="subforunm_foot_intro clearfix">
                <div class="subforunm_foot_intro_left">
                <div>Designed by <a href="//steamcommunity.com/profiles/76561198020599404/" target="_blank">Lee</a> in <a href="//www.bing.com/search?q=Balestier" target="_blank">Balestier</a>, Powered by <a href="https://www.discuz.vip/" target="_blank">Discuz!</a></div>
                <div style="margin-top:2px; margin-top:6px\0;">推荐使用 <a href="https://www.google.cn/chrome/" target="_blank">Chrome</a> 或 <a href="https://www.microsoft.com/edge" target="_blank">Microsoft Edge</a> 来浏览本站</div>
                </div>
                <div class="subforunm_foot_intro_right">
                <div>
                <a href="advertising_with_keylol_20210216.pdf" >广告投放</a><span class="pipe">|</span><a href="forum.php?mobile=yes" >手机版</a><span class="pipe">|</span><a href="#" >广州数趣信息科技有限公司 版权所有</a><span class="pipe">|</span><strong><a href="https://keylol.com" target="_blank">其乐 Keylol</a></strong>
                ( <a href="http://beian.miit.gov.cn/" target="_blank">粤ICP备17068105号</a> )<!-- Steam 蓝绿插件 -->
                <script src="https://steamdb.keylol.com/steam_info.js?v=0520"></script>

                <!-- 追加 Steam 来源 -->
                <script>
                (function (params) {
                	params.forEach(function (param) {
                		function e(e,r,t){
                			var a=new RegExp("([?&])"+r+"=.*?(&|${'$'})","i"),
                				n=-1!==e.indexOf("?")?"&":"?",
                				b='#app_reviews_hash',
                				s=-1!==e.indexOf(b)?'':b,
                				e=e.replace(b,'');
                			return e.match(a) ? e.replace(a,"${'$'}1"+r+"="+t+"${'$'}2"+s):e+n+r+"="+t+s
                		}
                		jQuery(param[0]).each(function(){
                			var r = jQuery(this);
                			r.attr("href", e(r.attr("href"), param[1], param[2]));
                			setInterval(function () {r.attr("href", e(r.attr("href"), param[1], param[2]))}, 1000);
                		});
                	});
                })([
                	// 追加 Steam 鉴赏家来源
                	['a[href^="http://store.steampowered.com/"],a[href^="https://store.steampowered.com/"]', 'utm_source', 'keylol'],
                	['a[href^="http://store.steampowered.com/"],a[href^="https://store.steampowered.com/"]', 'curator_clanid', '9730205'],
                	// 追加 Steam 来源：无敌号（731040）
                	['a[href^="http://store.steampowered.com/app/731040"],a[href^="https://store.steampowered.com/app/731040"]', 'utm_campaign', 'release'],
                ]);
                </script>

                <!-- 追加其他网站来源 -->
                <script>
                (function (params) {
                	params.forEach(function (param) {
                		function e(e,r,t){
                			var a=new RegExp("([?&])"+r+"=.*?(&|${'$'})","i"),
                				n=-1!==e.indexOf("?")?"&":"?";
                			return e.match(a)?e.replace(a,"${'$'}1"+r+"="+t+"${'$'}2"):e+n+r+"="+t
                		}
                		jQuery(param[0]).each(function(){
                			var r = jQuery(this);
                			r.attr("href", e(r.attr("href"), param[1], param[2]));
                			setInterval(function () {r.attr("href", e(r.attr("href"), param[1], param[2]))}, 1000);
                		});
                	});
                })([
                	// 追加育碧网站来源
                	['a[href*="ubi.com/"],a[href*="ubisoft.com/"],a[href*="ubisoft.com.cn/"]', 'ucid', 'DDP-ID_189202'],
                	['a[href*="ubi.com/"],a[href*="ubisoft.com/"],a[href*="ubisoft.com.cn/"]', 'maltcode', 'ubisoftstore_acqst_DDP_other___STORE____'],
                	// 追加 doaxvv 来源
                	['a[href^="https://game.doaxvv.com/"]', 'utm_source', 'universe'],
                	['a[href^="https://game.doaxvv.com/"]', 'utm_medium', 'cpm'],
                	['a[href^="https://game.doaxvv.com/"]', 'utm_campaign', '4th'],
                	['a[href^="https://game.doaxvv.com/"]', 'utm_content', '03_keylol_864x598'],
                	// 追加 HB 来源
                	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_source', 'Keylol'],
                	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_campaign', 'December_Choice24'],
                	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_medium', 'Paid'],
                	// 追加 GMG 来源
                	['a[href^="http://www.greenmangaming.com/"],a[href^="https://www.greenmangaming.com/"]', 'utm_source', 'Keylol'],
                	['a[href^="http://www.greenmangaming.com/"],a[href^="https://www.greenmangaming.com/"]', 'utm_medium', 'Referral'],
                	// 追加杉果来源
                	['a[href^="http://www.sonkwo.cn/"],a[href^="https://www.sonkwo.cn/"]', 'rcode', 'w84nno'],
                	['iframe[src^="https://activity.sonkwo.com/card/product.html"]', 'rcode', 'w84nno'],
                	// 追加杉果hk来源
                	['a[href^="http://www.sonkwo.hk/"],a[href^="https://www.sonkwo.hk/"]', 'rcode', 'w84nno'],
                	['iframe[src^="https://activity.sonkwo.com/card/product_in.html"]', 'rcode', 'w84nno'],
                	// 追加 2Game 来源
                	['a[href^="http://2game.com"],a[href^="http://2game.hk"],a[href^="https://2game.com"],a[href^="https://2game.hk"]', 'dir', 'asc'],
                	['a[href^="http://2game.com"],a[href^="http://2game.hk"],a[href^="https://2game.com"],a[href^="https://2game.hk"]', 'order', 'position'],
                ]);
                </script>

                <!-- 注册 Service Worker 加速访问 -->
                <script>
                if ('serviceWorker' in navigator) {
                  window.addEventListener('load', function() {
                    navigator.serviceWorker.register('/sw.js?v=23');
                  });
                }
                </script>

                <!-- 修复夜间模式下iframe白底 -->
                <style>
                iframe[src^="https://store.steampowered.com/widget/"], iframe[src^="https://steamdb.keylol.com/tooltip"] {
                    color-scheme: auto;
                }
                </style>
                </div>
                <div style="margin-top:2px; margin-top:6px;">
                GMT+8, 2024-12-6 15:05</div>
                </div>
                </div>

                <!-- 百度统计 -->
                <script>
                var _hmt = _hmt || [];
                _hmt.push(['_setUserId', window.discuz_uid]);
                (function() {
                var hm = document.createElement("script");
                hm.src = "https://hm.baidu.com/hm.js?8cf683f51eb4795c5d09446e920329c3";
                var s = document.getElementsByTagName("script")[0]; 
                s.parentNode.insertBefore(hm, s);
                })();
                </script>

                <!-- Google 统计 -->
                <script src="https://www.googletagmanager.com/gtag/js?id=UA-17880378-3" type="text/javascript"></script>
                <script>
                window.dataLayer = window.dataLayer || [];
                function gtag(){dataLayer.push(arguments);}
                gtag('js', new Date());
                gtag('config', 'UA-17880378-3', { 'user_id': window.discuz_uid });
                </script>
                </div>
                <div class="focus plugin" id="ip_notice"></div>
                <script type="text/javascript">ipNotice();</script>

                <div id="scrolltop">
                <span hidefocus="true"><a title="返回顶部" onclick="window.scrollTo('0','0')" class="scrolltopa" ><b>返回顶部</b></a></span>
                </div>
                <script type="text/javascript">_attachEvent(window, 'scroll', function () { showTopLink(); });checkBlind();</script>
                </body>
                </html>
            """.trimIndent()
        val nickname = Ksoup.parse(html).title().substringBefore("的个人资料")
        assertEquals("星鸢", nickname)
    }

    @Test
    fun parse_index_content() {
        val html = """
            <!DOCTYPE html>
            <html>
            <body id="nv_forum"
                  class="pg_index"
                  onkeydown="if(event.keyCode==27) return false;">
            <div id="append_parent"></div>
            <div id="ajaxwaitid"></div>    <nav id="nav-menu" class="align-center">
                <ul>
                    <li class="has-sub"><a href="javascript:void(0)"> <span>蒸汽</span> </a>
                        <ul>
                            <li><a href="f161-1"> <span>热点</span> </a></li>
                            <li><a href="f319-1"> <span>福利</span> </a></li>
                            <li><a href="f234-1"> <span>购物</span> </a></li>
                            <li><a href="f271-1"> <span>慈善包</span> </a></li>
                            <li><a href="f257-1"> <span>汉化</span> </a></li>
                            <li><a href="f127-1"> <span>工具</span> </a></li>
                            <li><a href="f235-1"> <span>成就</span> </a></li>
                            <li><a href="f129-1"><span>互鉴</span></a></li>
                            <li><a href="f254-1"><span>分享互赠</span></a></li>
                        </ul>
                    </li>
                    <li class="has-sub"><a href="javascript:void(0)"> <span>互助</span> </a>
                        <ul>
                            <li><a href="f301-1"> <span>平台技术</span> </a></li>
                            <li><a href="f302-1"> <span>平台购物</span> </a></li>
                            <li><a href="f304-1"> <span>其乐社区</span> </a></li>
                            <li><a href="f318-1"> <span>资源索取</span> </a></li>
                            <li><a href="f303-1" style="padding: 10px 10px; line-height: 20px;"> <span>游戏成就汉化</span> </a></li>
                            <li><a href="f322-1" style="padding: 10px 10px; line-height: 20px;"> <span>软件硬件其他</span> </a></li>
                        </ul>
                    </li>
                    <li class="has-sub"><a href="javascript:void(0)"> <span>平台</span> </a>
                        <ul>
                            <li><a href="f275-1"><span>主机</span></a></li>
                            <li><a href="f328-1"><span>手游</span></a></li>
                            <li><a href="f232-1"> <span>Origin</span> </a></li>
                            <li><a href="f274-1"> <span>uPlay</span> </a></li>
                            <li><a href="f276-1"> <span>GOG</span> </a></li>
                            <li><a href="f345-1"> <span>战网</span> </a></li>
                            <li><a href="f326-1"><span>Windows</span></a></li>
                            <li><a href="f335-1"><span>Epic</span></a></li>
                            <li><a href="f316-1"> <span>杉果</span> </a></li>
                            <li><a href="f332-1"><span>方块</span></a></li>
                            <li><a href="f325-1"><span>WeGame</span></a></li>
                            <li><a href="f277-1"> <span>其他</span> </a></li>
                        </ul>
                    </li>
                    <li class="has-sub"><a href="javascript:void(0)"> <span>休闲</span> </a>
                        <ul>
                            <li><a href="f148-1"> <span>水区</span> </a></li>
                            <li><a href="f259-1"> <span>摄影旅游</span> </a></li>
                            <li><a href="f273-1"> <span>美食烹饪</span> </a></li>
                            <li><a href="f200-1"> <span>软硬兼施</span> </a></li>
                            <li><a href="f330-1"> <span>开箱</span> </a></li>
                        </ul>
                    </li>
                    <li class="has-sub"><a href="forum.php"> <span>游戏</span> </a>
                        <ul>
                            <!--<li class="dual-line"><a href="f251-1"><p>游戏综合</p>
                              <!--  <p>交换观点/资源</p></a></li> -->
                            <li><a href="f251-1"><span>游戏综合</span></a></li>
                            <li><a href="f305-1"><span>刀塔</span></a></li>
                            <li><a href="f337-1"><span>明日方舟</span></a></li>
                            <li><a href="f299-1"><span>反恐精英</span></a></li>
                            <li><a href="f244-1"><span>威乐</span></a></li>
                            <li><a href="f246-1"><span>艺电</span></a></li>
                            <li><a href="f245-1"><span>育碧</span></a></li>
                            <li><a href="f248-1"><span>动视暴雪</span></a></li>
                            <li><a href="f339-1"><span>虚拟现实</span></a></li>
                        </ul>
                    </li>
                            <!-- <li class="nav-logo"><a href="/"> <span>&nbsp;</span> </a></li> -->
                    <li class="has-sub"></a>
                    <li class="has-sub nav-forum"><a href="javascript:void(0)"> <span>服务</span> </a>
                        <ul>
                            <li><a href="f140-1"> <span>公告</span> </a></li>
                            <li><a href="f197-1"> <span>反馈</span> </a></li>
                            <li><a href="f238-1"> <span>活动</span> </a></li>
                            <li><a href="steamcn_steam_connect-statistics.html"> <span>排行榜</span> </a></li>
                            <li><a href="javascript:setcookie(`dark_mode`, 1-getcookie(`dark_mode`), 3600*24*365);location.reload();"> <span>夜间</span> </a></li>
                            <li><a href="forum.php?mod=collection"> <span>淘帖</span> </a></li>
                            <li><a href="misc.php?mod=tag"> <span>标签</span> </a></li>
                            <li><a href="keylol_forum_subscription-threadlist.html"> <span>订阅</span> </a></li>
                        </ul>
                    </li>
                    <li class="has-sub nav-keylol"><a href="https://store.steampowered.com/curator/9730205-Keylol-Player-Club/" target="_blank"> <span>评测</span> </a>
                        <ul>
                            <li class="dual-line"><a href="https://store.steampowered.com/curator/9730205-Keylol-Player-Club/" target="_blank"><p>其乐 Keylol 鉴赏家</p>
                                <p>游戏评测社区</p></a></li>
                        </ul>
                    </li>
                    <li class="has-sub nav-7l"><a href="steamcn_gift-7l.html"> <span>赠楼</span> </a>
                        <ul>
                            <li class="dual-line"><a href="steamcn_gift-7l.html"><p>互赠平台</p>
                                <p>抽取正版游戏</p></a></li>
                        </ul>
                    </li>
                    <li class="has-sub nav-trade"><a href="f201-1"> <span>交易</span> </a>
                        <ul>
                            <li class="dual-line"><a href="f201-1"><p>交易中心</p>
                                <p>便捷游戏市集</p></a></li>
                        </ul>
                    </li>
                    <li class="has-sub nav-srcn nav-last"><a href="https://steamrepcn.com/" target="_blank"> <span>信誉</span> </a>
                        <ul>
                            <li class="dual-line"><a href="https://steamrepcn.com/" target="_blank"><p>SRCN</p>
                                <p>反诈骗数据库</p></a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <header class="tb-container">
                <a id="nav-logo" href="/">
                    <span>原蒸汽动力社区</span>
                </a>
                <section id="nav-additional" class="pull-right">
                    <div id="nav-search-bar">
                        <div class="search-bar" style="display: none;">
                <!-- <img src="template/steamcn_metro/src/img/common/nav-search.png"> -->
                <form class="search-bar-form" method="post" action="search.php?searchsubmit=yes" target="_blank">
                    <input type="hidden" name="formhash" value="e5dc730e">
                    <input type="hidden" name="srchtype" value="title">
                    <input type="hidden" name="srhfid" value="">
                    <input type="hidden" name="srhlocality" value="forum::index">
                            <input class="search-box" type="search" name="srchtxt" placeholder="请输入搜索内容">
                    <input type="hidden" name="mod" value="baidu">
                    <div class="dropdown">
                        <button type="button" class="btn btn-default" data-toggle="dropdown"><span>百度</span><span class="caret"></span></button>
                        <ul class="dropdown-menu">
                                                                            <li><a href="javascript:void(0)" data-mod="forum">帖子</a></li>
                                                                                                            <li><a href="javascript:;" data-mod="collection">淘贴</a></li>                <li class="divider"></li>
                            <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="google">Google</a></li>
                            <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="baidu">百度</a></li>
                            <li><a href="javascript:void(0)" class="search-bar-remember" data-mod="bing">必应</a></li>
                        </ul>
                    </div>
                    <button class="btn btn-default" type="submit" name="searchsubmit" value="true"></button>
                </form>
            </div>
                    </div>
                    <div id="nav-user-action-bar">
                        <ul class="list-inline">                              <span id="xunjie_attention_s"></span>                 <li><a class="btn btn-user-action"
                                   href="home.php?mod=space&amp;do=pm"> 消息                        </a></li>
                            <li>
                                <a class="btn btn-user-action"
                                   href="home.php?mod=space&amp;do=notice"> 提醒                        </a></li>
                            <li class="dropdown"><a href="suid-1056547"> <img class="avatar img-circle" src="https://keylol.com/uc_server/data/avatar/001/05/65/47_avatar_middle.jpg?ts=1696781036">
                            </a>
                                <ul class="dropdown-menu dropdown-menu-right">
                                    <li><a href="home.php?mod=spacecp">设置</a></li>
                                                                <li><a href="connect.php?mod=config">绑定 QQ</a></li>                        <li class="divider"></li>                                                                                                    <li><a href="home.php?mod=space&do=friend" _style="background-image:url(https://keylol.com/static/image/feed/friend_b.png) !important">好友</a></li>                                                                                                                            <li><a href="forum.php?mod=guide&view=my" _style="background-image:url(https://keylol.com/static/image/feed/thread_b.png) !important">帖子</a></li>                                                                                                                            <li><a href="home.php?mod=space&do=favorite&type=thread">收藏</a></li>                                                                                                                            <li><a href="home.php?mod=magic" _style="background-image:url(https://keylol.com/static/image/feed/magic_b.png) !important">道具</a></li>                                                                                                                            <li><a href="home.php?mod=medal" _style="background-image:url(https://keylol.com/static/image/feed/medal_b.png) !important">勋章</a></li>                                                                                                                            <li><a href="forum.php?mod=collection&op=my" _style="background-image:url(https://keylol.com/static/image/feed/collection_b.png) !important">淘帖</a></li>                                                                                                                                                                                                                                                                            <li><a href="keylol_forum_subscription-threadlist.html">订阅</a></li>
                                    <li class="divider"></li>
                                                                                                                                    <li><a href="member.php?mod=logging&amp;action=logout&amp;formhash=e5dc730e">退出</a></li>
                                </ul>
                            </li>                                                </ul>
                    </div>
                </section>
            </header>    <div class="floatcontainer doc_header wp cl">            <div class="index_navi">
                    <div class="index_navi_left">
                        <div class="index_left_title">
                            <div>关注重点</div>
                        </div>
                        <div class="index_left_detail">                                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="t531046-1-1" target="_blank">SteamCN 新品牌其乐</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="t307370-1-1" target="_blank">社区帖子代码指南</a></div>
                            </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t521974-1-1" target="_blank">Steam和社区脚本索引</a></div>
                            </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t450232-1-1" target="_blank">【获得积分】用户组手册</a></div>
                            </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t353357-1-1" target="_blank">慈善包购物手册</a></div>
                            </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t803672-1-1" target="_blank">入库其他区的免费内容</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t301016-1-1" target="_blank">ASF 挂卡配置与命令教程</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t234499-1-1" target="_blank">SteamDB 使用指南</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/forum.php?mod=collection&action=view&ctid=72" target="_blank">Steam低价获取余额</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="t96430-1-1" target="_blank"> 赠楼使用手册</a></div>
                            </div>                                    <div class="row_top">                                            <div class="dot"></div>                                            <div><a href="https://keylol.com/t152125-1-1" target="_blank">可免费 +1</a></div>
                            </div>                                    <div class="row_top">                                            <div class="blue_dot"></div>                                            <div><a href="https://keylol.com/t675265-1-1" target="_blank">Steam汉化补丁汇总</a></div>
                            </div>                                                </div>
                    </div>            <div class="index_navigation_mid rnd_ai_h" style="line-height:107px;height:107px"><a href="https://keylol.com/hello/1551360" target="_blank"><img src="https://blob.keylol.com/forum/202412/19/200214stgj5jjj5g9wj5bo.jpg?a=a" height="107" width="240" border="0"></a></div>        <div class="index_navi_right">
                        <div class="index_right_title">
                            <div>站点公告</div>
                        </div>
                        <div class="index_right_detail">
                            <div class="blue_dot"></div>
                            <div><a href="/t273107-1-1" target="_blank">游戏开发和发行的咨询服务</a></div>
                        </div>
                        <div class="index_right_detail">
                            <div class="dot" style="margin-top:3px;"></div>
                            <div style="margin-top:-2px;"><a href="/t46755-1-1" target="_blank">其乐用户守则</a></div>
                        </div>
                        <div class="index_right_detail" style="margin-top:-5px;">
                            <div class="dot"></div>
                            <div><a href="/advertising_with_keylol_20210216.pdf?iEE" target="_blank">在其乐投放广告</a></div>
                        </div>
                    </div>
                </div>            <div class="p_pop h_pop" id="mn_userapp_menu" style="display: none"></div>                        <link rel="stylesheet" href="https://keylol.com/source/plugin/steamcn_steam_connect/static/style.css"><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/jquery.cookie.js?iEE'><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/json2.js?iEE'></script><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/jquery.storageapi.min.js?iEE'></script><script type='text/javascript' src='./source/plugin/sq_recent_smiles/js/recent_smiles.js?iEE'></script><script type="text/javascript" charset="UTF-8" src="https://keylol.com/source/plugin/steamcn_lang/static/tongwen.js?iEE_v2"></script><div id="ajax_lang"></div><script>jQuery.get("https://keylol.com/plugin.php?id=steamcn_lang:header",function(data){jQuery("#ajax_lang").append(data)})</script>    </div>    <div id="wp" class="wp cl" style="min-width: 1206px;">
            <style id="diy_style" type="text/css"></style>

            <!--<script src="template/steamcn_metro/src/js/jquery-2.2.4.min.js" type="text/javascript" type="text/javascript"></script>-->
            <!--<script>
                jQuery.noConflict();
            </script>-->
            <div id="steam_monitor">
                <p>
                    <a href=" https://keylol.com/t644082-1-1 ">Steam
                        商店状态 : <span id="steam_monitor_store" style="color:#6BCC6A; " >正常</span></a> | <a
                        href=" https://keylol.com/t644082-1-1 ">Steam
                    社区状态 : <span id="steam_monitor_community" style="color:#6BCC6A; " >正常</span></a> | <a
                        href=" https://keylol.com/t937324-1-1 ">「Steam 冬季特卖」 : <span id="steam_monitor_countdown">正在进行中</span></a></p>
            </div>

            <style>
                #steam_monitor {
                    
                    background: rgb(240, 243, 244);
                    
                    color: #333333;
                    height: 40px;
                    text-align: center;
                    margin-bottom: 18px;
                    /* padding: 7px 0; */
                    font-size: 16px;
                    font-weight: 400;
                    /* line-height: 26px; */
                }
                #steam_monitor > p {
                    
                    line-height: 40px;
                }
            </style>

            <script>
                if (true) {
                    var count_down_date = new Date("2025-01-03 02:00".replace(/\s/, "T") + ":00.000+08:00").getTime();
                    var count_down_format = "正在进行中（预计 timer 后结束）";
                    var steam_monitor_timer = setInterval(function () {
                        var now = new Date().getTime();
                        var distance = count_down_date - now;

                        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
                        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

                        if (distance < 0) {
                            clearInterval(steam_monitor_timer);
                            document.getElementById("steam_monitor_countdown").innerHTML = "促销已经结束";
                            return;
                        }

                        var html = days === 0 ? "" : days + " 天 ";
                        html += (days === 0 && hours === 0) ? "" : hours + " 小时 ";
                        html += (days === 0 && hours === 0 && minutes === 0) ? "" : minutes + " 分钟 ";
                        html += seconds + " 秒";

                        document.getElementById("steam_monitor_countdown").innerHTML = count_down_format.replace("timer", html);
                    }, 1000);
                }
            </script>
            <div class="wp cl">
                <script src="data/cache/common_extra.js?iEE" type="text/javascript"></script>
                <script>JSLOADED[JSPATH + 'common_extra.js?' + VERHASH] = 1;</script>
                <!--[diy=diy1]--><div id="diy1" class="area"><div id="frameCX66dD" class="bbs_top frame move-span cl frame-1-1"><div id="frameCX66dD_left" class="column frame-1-1-l"><div id="frameCX66dD_left_temp" class="move-span temp"></div><div id="portal_block_431" class="block move-span"><div id="portal_block_431_content" class="dxb_bc"><div class="module cl slidebox">
            <ul class="slideshow"><li style="width: 432px; height: 289px;"><a href="t981574-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/10/233204i8fzi2z10m1zqqo3.jpg?a=a" width="432" height="289" title="板块: 综合讨论 
            分类: 活动推广 
            作者: 社区活动帐号 (2024-12-14)
            浏览: 0 / 回复: 0
            最后: 社区活动帐号 (2024-12-14)" /></a><span class="title">【福利】全新的双人冒险《怪镇奇旅》即将展开</span></li><li style="width: 432px; height: 289px;"><a href="t982450-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/094817citz89ia799978ig.jpg" width="432" height="289" title="板块: 购物心得 
            分类: 2024冬季特卖 
            作者: nightnine (2024-12-20)
            浏览: 401 / 回复: 8
            最后: HGO147062 (2024-12-20)" /></a><span class="title">DOA6（死或生6）季票1-4新史低 -70%OFF</span></li><li style="width: 432px; height: 289px;"><a href="t982447-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/095242feeug51g5z45ge2j.jpg" width="432" height="289" title="板块: 购物心得 
            分类: 发售折扣 
            作者: 时间入世 (2024-12-20)
            浏览: 52 / 回复: 2
            最后: 皮酱 (2024-12-20)" /></a><span class="title">【24-12-20】中式恐怖剧情解谜游戏《纸嫁衣6千秋魇》</span></li><li style="width: 432px; height: 289px;"><a href="t982443-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/093046rf0uffgj8fngx8k3.jpg" width="432" height="289" title="板块: 购物心得 
            分类: 2024冬季特卖 
            作者: kabpy (2024-12-20)
            浏览: 904 / 回复: 11
            最后: 摸摸耳朵 (2024-12-20)" /></a><span class="title">yuno新史低85%off/￥36，Someday You&#39;ll Return导</span></li><li style="width: 432px; height: 289px;"><a href="t982435-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/090914kwcnnwbnen4ybvbh.png" width="432" height="289" title="板块: 购物心得 
            分类: 2024冬季特卖 
            作者: 莱布妮子 (2024-12-20)
            浏览: 882 / 回复: 12
            最后: HGO147062 (2024-12-20)" /></a><span class="title">《保持通话没人爆炸》-90% 5.8￥</span></li><li style="width: 432px; height: 289px;"><a href="t982426-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/075428tc6333wqexm6u88l.png" width="432" height="289" title="板块: 艺电 
            分类: 战地风云系列 
            作者: nuanshu52 (2024-12-20)
            浏览: 456 / 回复: 7
            最后: ufo19851007 (2024-12-20)" /></a><span class="title">《战地五》登陆即可领取三款皮肤和2万连队币</span></li><li style="width: 432px; height: 289px;"><a href="t982425-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/074622u8dk5f3mikq8azar.png" width="432" height="289" title="板块: 购物心得 
            分类: Steam日常特惠 
            作者: 樱小路游星 (2024-12-20)
            浏览: 1011 / 回复: 4
            最后: ErenJaeger117 (2024-12-20)" /></a><span class="title">【热门平史低折扣 #5】《Noita》《拉比哩比》《对马岛</span></li><li style="width: 432px; height: 289px;"><a href="t982424-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/072926eelucmbjmuvjv7e6.jpg" width="432" height="289" title="板块: 购物心得 
            分类: Steam日常特惠 
            作者: 樱小路游星 (2024-12-20)
            浏览: 502 / 回复: 2
            最后: TFBOYMAN (2024-12-20)" /></a><span class="title">【热门平史低折扣 #4】《半条命 Alyx》《博德之门3》</span></li><li style="width: 432px; height: 289px;"><a href="t982303-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/19/211633g9pnpacq9lrccf6w.jpg" width="432" height="289" title="板块: 综合讨论 
            分类: 活动推广 
            作者: 社区活动帐号 (2024-12-20)
            浏览: 1014 / 回复: 121
            最后: 蛋炒饭加蛋 (2024-12-20)" /></a><span class="title">【福利】冬促来袭，《极限竞速：地平线5》年度收官折</span></li><li style="width: 432px; height: 289px;"><a href="t982423-1-1" target="_blank"><img src="https://blob.keylol.com/forum/202412/20/065259nxlc3vvwzy3mwtay.png" width="432" height="289" title="板块: 购物心得 
            分类: 2024冬季特卖 
            作者: 樱小路游星 (2024-12-20)
            浏览: 1856 / 回复: 6
            最后: 风火小妖 (2024-12-20)" /></a><span class="title">【特别好评新史低 #3】《弹丸论破V3》《以撒的结合》</span></li></ul>
            </div>
            <script type="text/javascript">
            runslideshow();
            </script></div></div></div><div id="frameCX66dD_center" class="column frame-1-1-r"><div id="frameCX66dD_center_temp" class="move-span temp"></div><div id="tabPAhn0P" class=" frame-tab move-span cl"><div id="tabPAhn0P_title" class="tab-title title column cl" switchtype="mouseover"><div id="portal_block_432" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="forum.php?mod=guide&amp;view=newthread" target="_blank" style="color: !important;">最新主题</a></span></div><div id="portal_block_432_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,238,307,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,364,550,551,552,553,554,556,645,741,318,844,847,848,761,758,762,857,689,875,774,763,676,924,845,888,764,238,687,401,867,953,952,852,894,767,102,965,786,652,931,760,933,653,796,756,859,261,771,475,772,996,979,770,915-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-1307992" target="_blank">发帖机器人</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t572814-1-1" title="板块: 福利放送
            作者: 发帖机器人 (2020-03-20)
            浏览: 4326353 / 回复: 94
            最后: EIMEN (2019-08-04)" target="_blank"><font style="font-weight: 900;color: #0000FF;">限免游戏福利《Distant Space 2》《吸血鬼幸存者》《小鸡刺客：重装上阵》《野性铁血战士》</font></a></li><li><em><a href="suid-841150" target="_blank">cvisy</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t982377-1-1" title="板块: 热点聚焦
            作者: cvisy (2024-12-20)
            浏览: 579 / 回复: 17
            最后: happystandby (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #EC1282;">2024年Steam冬季特卖综合导航帖（先行版）：重点折扣收集和Steam大奖投票活动消息前瞻</font></a></li><li><em><a href="suid-390432" target="_blank">nightnine</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982450-1-1" title="板块: 购物心得
            作者: nightnine (2024-12-20)
            浏览: 401 / 回复: 8
            最后: HGO147062 (2024-12-20)" target="_blank">DOA6（死或生6）季票1-4新史低 -70%OFF</a></li><li><em><a href="suid-1246801" target="_blank">时间入世</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982447-1-1" title="板块: 购物心得
            作者: 时间入世 (2024-12-20)
            浏览: 52 / 回复: 2
            最后: 皮酱 (2024-12-20)" target="_blank">【24-12-20】中式恐怖剧情解谜游戏《纸嫁衣6千秋魇》发售特惠-10%/¥28.8</a></li><li><em><a href="suid-1557057" target="_blank">kabpy</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982443-1-1" title="板块: 购物心得
            作者: kabpy (2024-12-20)
            浏览: 904 / 回复: 11
            最后: 摸摸耳朵 (2024-12-20)" target="_blank">yuno新史低85%off/￥36，Someday You&#39;ll Return导剪版新史低90%off/￥7.9</a></li><li><em><a href="suid-193046" target="_blank">iamdaxin</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t982442-1-1" title="板块: 华语汉化
            作者: iamdaxin (2024-12-20)
            浏览: 134 / 回复: 7
            最后: 清照 (2024-12-20)" target="_blank">《月下之癫》现已支持简体中文</a></li><li><em><a href="suid-1273443" target="_blank">luowuming</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982437-1-1" title="板块: 购物心得
            作者: luowuming (2024-12-20)
            浏览: 303 / 回复: 0
            最后: luowuming (2024-12-20)" target="_blank">Crypt of the NecroDancer联动初音未来dlc新史低8.8</a></li><li><em><a href="suid-605212" target="_blank">莱布妮子</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982435-1-1" title="板块: 购物心得
            作者: 莱布妮子 (2024-12-20)
            浏览: 882 / 回复: 12
            最后: HGO147062 (2024-12-20)" target="_blank">《保持通话没人爆炸》-90% 5.8￥</a></li><li><em><a href="suid-1380807" target="_blank">aokprc</a></em><a href="f299-1" target="_blank">【反恐精英】</a> <a href="t982432-1-1" title="板块: 反恐精英
            作者: aokprc (2024-12-20)
            浏览: 359 / 回复: 4
            最后: wyrmmm (2024-12-20)" target="_blank">CS2 2024/12/20更新日志 沙鹰特殊检视回归</a></li><li><em><a href="suid-1025876" target="_blank">nuanshu52</a></em><a href="f246-1" target="_blank">【艺电】</a> <a href="t982426-1-1" title="板块: 艺电
            作者: nuanshu52 (2024-12-20)
            浏览: 456 / 回复: 7
            最后: ufo19851007 (2024-12-20)" target="_blank">《战地五》登陆即可领取三款皮肤和2万连队币</a></li><li><em><a href="suid-1392584" target="_blank">樱小路游星</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982425-1-1" title="板块: 购物心得
            作者: 樱小路游星 (2024-12-20)
            浏览: 1011 / 回复: 4
            最后: ErenJaeger117 (2024-12-20)" target="_blank">【热门平史低折扣 #5】《Noita》《拉比哩比》《对马岛之魂》《环世界》等164款汇总</a></li><li><em><a href="suid-1392584" target="_blank">樱小路游星</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982424-1-1" title="板块: 购物心得
            作者: 樱小路游星 (2024-12-20)
            浏览: 502 / 回复: 2
            最后: TFBOYMAN (2024-12-20)" target="_blank">【热门平史低折扣 #4】《半条命 Alyx》《博德之门3》《暗喻幻想》《老头环》等176款汇总</a></li><li><em><a href="suid-178889" target="_blank">社区活动帐号</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982303-1-1" title="板块: 综合讨论
            作者: 社区活动帐号 (2024-12-20)
            浏览: 1014 / 回复: 121
            最后: 蛋炒饭加蛋 (2024-12-20)" target="_blank">【福利】冬促来袭，《极限竞速：地平线5》年度收官折扣！晒愿望单赢取游戏激活码！</a></li><li><em><a href="suid-1392584" target="_blank">樱小路游星</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982423-1-1" title="板块: 购物心得
            作者: 樱小路游星 (2024-12-20)
            浏览: 1856 / 回复: 6
            最后: 风火小妖 (2024-12-20)" target="_blank">【特别好评新史低 #3】《弹丸论破V3》《以撒的结合》《严阵以待》《森林之子》等216款</a></li><li><em><a href="suid-1392584" target="_blank">樱小路游星</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=875" target="_blank">【2024冬季特卖】</a> <a href="t982420-1-1" title="板块: 购物心得
            作者: 樱小路游星 (2024-12-20)
            浏览: 1726 / 回复: 3
            最后: m3635022816 (2024-12-20)" target="_blank">【特别好评新史低 #2】《神话时代 重述版》《潜行者2》《星际战士2》217款折扣汇总</a></li></ol>
            </div></div></div><div id="portal_block_433" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="forum.php?mod=guide&amp;view=new" target="_blank" style="color: !important;">最新回复</a></span></div><div id="portal_block_433_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-200379" target="_blank">のの不想想名字</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t982383-1-1" title="板块: 华语汉化
            作者: のの不想想名字 (2024-12-20)
            浏览: 323 / 回复: 2
            最后: cat_l_z (2024-12-21)" target="_blank"><font style="font-weight: 900;color: #996600;">狙击精英 1代+V2+V2 Remaster 绿色汉化包 修订版</font></a></li><li><em><a href="suid-1640586" target="_blank">冰雪聪明琪露诺</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t982408-1-1" title="板块: 热点聚焦
            作者: 冰雪聪明琪露诺 (2024-12-20)
            浏览: 584 / 回复: 1
            最后: 生侠就是我 (2024-12-21)" target="_blank">太空沙盒游戏《太空工程师2》开启预购，国区售价¥108.00，2025年1月28日发行</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t982196-1-1" title="板块: 热点聚焦
            作者: 万狐飞仙 (2024-12-19)
            浏览: 6223 / 回复: 141
            最后: QFTFHT (2024-12-21)" target="_blank"><font style="color: #EC1282;">2024 年 STEAM 回顾页面上线 | 查看回顾获取徽章</font></a></li><li><em><a href="suid-190381" target="_blank">Hazardosu</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t982315-1-1" title="板块: 福利放送
            作者: Hazardosu (2024-12-19)
            浏览: 4771 / 回复: 151
            最后: AncientOne (2024-12-21)" target="_blank"><font style="font-weight: 900;color: #EE5023;">Fanatical 限量免费领取《灾厄逆刃（ScourgeBringer）》</font></a></li><li><em><a href="suid-211041" target="_blank">qqq888555ppp</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982248-1-1" title="板块: 综合讨论
            作者: qqq888555ppp (2024-12-19)
            浏览: 1606 / 回复: 53
            最后: weiamao (2024-12-20)" target="_blank"><font style="color: #2897C5;">《空之轨迹1st》2025秋季PS5/NS/STEAM同步发售</font></a></li><li><em><a href="suid-1201846" target="_blank">天启星老蝙蝠</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="forum.php?mod=forumdisplay&fid=275&filter=typeid&typeid=352" target="_blank">【XBOX游戏】</a> <a href="t982229-1-1" title="板块: 主机平台
            作者: 天启星老蝙蝠 (2024-12-19)
            浏览: 1615 / 回复: 25
            最后: 天启星老蝙蝠 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #3C9D40;">XGPU用户可免费领取10款EA游戏/DLC，每天一款</font></a></li><li><em><a href="suid-218128" target="_blank">sffxzzp</a></em><a href="f127-1" target="_blank">【平台工具】</a> <a href="t982213-1-1" title="板块: 平台工具
            作者: sffxzzp (2024-12-19)
            浏览: 1447 / 回复: 67
            最后: smilez (2024-12-20)" target="_blank"><font style="color: #EE1B2E;">[脚本]GOG拥有游戏链接变色</font></a></li><li><em><a href="suid-476296" target="_blank">dblouis</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="t982205-1-1" title="板块: 主机平台
            作者: dblouis (2024-12-19)
            浏览: 1351 / 回复: 32
            最后: Lacroix (2024-12-20)" target="_blank"><font style="color: #EC1282;">Nintendo Switch 2024 年度回顾现已上线</font></a></li><li><em><a href="suid-200379" target="_blank">のの不想想名字</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t982197-1-1" title="板块: 华语汉化
            作者: のの不想想名字 (2024-12-19)
            浏览: 1507 / 回复: 15
            最后: 风起陇西 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #EE5023;">Warhammer 40,000 Space Marine 战锤40K 星际战士 汉化+多核修复整合</font></a></li><li><em><a href="suid-1198358" target="_blank">playerwhy</a></em><a href="f326-1" target="_blank">【Windows 商店】</a> <a href="t982105-1-1" title="板块: Windows 商店
            作者: playerwhy (2024-12-18)
            浏览: 2838 / 回复: 31
            最后: TFBOYMAN (2024-12-20)" target="_blank"><font style="color: #3C9D40;">（疑似临时工） 方舟 鲍伯的捆绑包 土区23r，PC可玩</font></a></li><li><em><a href="suid-605212" target="_blank">莱布妮子</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982435-1-1" title="板块: 购物心得
            作者: 莱布妮子 (2024-12-20)
            浏览: 882 / 回复: 12
            最后: HGO147062 (2024-12-20)" target="_blank">《保持通话没人爆炸》-90% 5.8￥</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982399-1-1" title="板块: 购物心得
            作者: 万狐飞仙 (2024-12-20)
            浏览: 1352 / 回复: 30
            最后: 清白的很 (2024-12-20)" target="_blank">《摇滚史密斯2014》恢复销售并低价促销</a></li><li><em><a href="suid-178889" target="_blank">社区活动帐号</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982303-1-1" title="板块: 综合讨论
            作者: 社区活动帐号 (2024-12-20)
            浏览: 1014 / 回复: 121
            最后: 蛋炒饭加蛋 (2024-12-20)" target="_blank">【福利】冬促来袭，《极限竞速：地平线5》年度收官折扣！晒愿望单赢取游戏激活码！</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t982387-1-1" title="板块: 福利放送
            作者: 万狐飞仙 (2024-12-20)
            浏览: 1820 / 回复: 28
            最后: www126com (2024-12-20)" target="_blank">【无需本体】Steam商店限时免费领取《Train Sim World 2》DLC《黑色沙漠》《Distant Space 2》</a></li><li><em><a href="suid-1246801" target="_blank">时间入世</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t982418-1-1" title="板块: 福利放送
            作者: 时间入世 (2024-12-20)
            浏览: 563 / 回复: 3
            最后: 钟爱她一生 (2024-12-20)" target="_blank">亚马逊 Prime 会员 12 月第四周福利已开始：EPIC《Predator: Hunting Grounds》</a></li></ol>
            </div></div></div><div id="portal_block_498" class="block move-span"><div class="blocktitle title" style="background-repeat: repeat;"><span class="titletext" style="float:;margin-left:px;font-size:"><a href="f234-1" target="_blank" style="color: !important;">热门购物</a></span></div><div id="portal_block_498_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982198-1-1" title="板块: 购物心得
            作者: 万狐飞仙 (2024-12-19)
            浏览: 11312 / 回复: 264
            最后: orzcccc (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">【新增 4 款】不久前 KONAMI 旗下短暂解锁国区的游戏再次解除锁区</font></a></li><li><em><a href="suid-1645454" target="_blank">油迹</a></em><a href="f271-1" target="_blank">【慈善包】</a> <a href="t982144-1-1" title="板块: 慈善包
            作者: 油迹 (2024-12-18)
            浏览: 5864 / 回复: 109
            最后: 辰＿蘇 (2024-12-19)" target="_blank"><font style="color: #EC1282;">【仅限国区】HB双旦1个月/66元优惠券</font></a></li><li><em><a href="suid-515026" target="_blank">方块游戏</a></em><a href="f332-1" target="_blank">【方块游戏平台】</a> <a href="t981994-1-1" title="板块: 方块游戏平台
            作者: 方块游戏 (2024-12-18)
            浏览: 2556 / 回复: 98
            最后: weiamao (2024-12-20)" target="_blank"><font style="color: #2897C5;">《仙剑奇侠传四》重制版前导PV公开</font></a></li><li><em><a href="suid-637225" target="_blank">xiaoanan123</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982034-1-1" title="板块: 购物心得
            作者: xiaoanan123 (2024-12-17)
            浏览: 7561 / 回复: 84
            最后: moonish (2024-12-20)" target="_blank">建议号上有黑猴的兄弟每天开一个补充包</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f271-1" target="_blank">【慈善包】</a> <a href="t982064-1-1" title="板块: 慈善包
            作者: 万狐飞仙 (2024-12-18)
            浏览: 4623 / 回复: 79
            最后: lzm520cc (2024-12-20)" target="_blank">【更新优惠码】外区 Humble Choice 假日优惠：六个月 49 美元（月付 8 美元/月）</a></li><li><em><a href="suid-1640586" target="_blank">冰雪聪明琪露诺</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982381-1-1" title="板块: 购物心得
            作者: 冰雪聪明琪露诺 (2024-12-20)
            浏览: 3660 / 回复: 53
            最后: 考拉与火 (2024-12-20)" target="_blank">KONAMI 旗下游戏打折</a></li><li><em><a href="suid-435780" target="_blank">llwy</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="forum.php?mod=forumdisplay&fid=275&filter=typeid&typeid=352" target="_blank">【XBOX游戏】</a> <a href="t981982-1-1" title="板块: 主机平台
            作者: llwy (2024-12-17)
            浏览: 3356 / 回复: 57
            最后: a15246753690 (2024-12-20)" target="_blank">XGPU会员限时免费领取《战地1》，主机版</a></li><li><em><a href="suid-1607313" target="_blank">櫻小路露娜</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t981978-1-1" title="板块: 购物心得
            作者: 櫻小路露娜 (2024-12-17)
            浏览: 5656 / 回复: 36
            最后: a15246753690 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">【Square Enix 冬季大促】《歧路旅人 II》《浪漫沙加2 七英雄的复仇》新史低 | 百余款折扣</font></a></li><li><em><a href="suid-190381" target="_blank">Hazardosu</a></em><a href="f271-1" target="_blank">【慈善包】</a> <a href="t982054-1-1" title="板块: 慈善包
            作者: Hazardosu (2024-12-18)
            浏览: 2269 / 回复: 36
            最后: Rancho (2024-12-20)" target="_blank">[12.18] Fanatical 自选包 BYO Card, Board and Dice Bundle 上线，${'$'}9.99 可选 7</a></li><li><em><a href="suid-1201846" target="_blank">天启星老蝙蝠</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="forum.php?mod=forumdisplay&fid=275&filter=typeid&typeid=352" target="_blank">【XBOX游戏】</a> <a href="t982229-1-1" title="板块: 主机平台
            作者: 天启星老蝙蝠 (2024-12-19)
            浏览: 1615 / 回复: 25
            最后: 天启星老蝙蝠 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #3C9D40;">XGPU用户可免费领取10款EA游戏/DLC，每天一款</font></a></li><li><em><a href="suid-476296" target="_blank">dblouis</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="t982205-1-1" title="板块: 主机平台
            作者: dblouis (2024-12-19)
            浏览: 1351 / 回复: 32
            最后: Lacroix (2024-12-20)" target="_blank"><font style="color: #EC1282;">Nintendo Switch 2024 年度回顾现已上线</font></a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=786" target="_blank">【临时工】</a> <a href="t982412-1-1" title="板块: 购物心得
            作者: 万狐飞仙 (2024-12-20)
            浏览: 2334 / 回复: 34
            最后: liangziyingze (2024-12-20)" target="_blank">《ABI-DOS》疑似临时工《咕喵怪物》0.5折等</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f276-1" target="_blank">【GOG】</a> <a href="t982200-1-1" title="板块: GOG
            作者: 万狐飞仙 (2024-12-19)
            浏览: 2252 / 回复: 36
            最后: 莩槿 (2024-12-20)" target="_blank"><font style="color: #996600;">[12.19] Humble Silent Night, Deadly Night: A GOG Bundle 上线，${'$'}12 可获得全部内容</font></a></li><li><em><a href="suid-1580299" target="_blank">2game</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982047-1-1" title="板块: 购物心得
            作者: 2game (2024-12-17)
            浏览: 3047 / 回复: 40
            最后: cavalr (2024-12-19)" target="_blank">2Game冬促：自杀小队¥19，哈利波特魁地奇¥85，喋血复仇究极版¥42，华纳全家桶来了！</a></li><li><em><a href="suid-299136" target="_blank">HelloFuN</a></em><a href="f316-1" target="_blank">【杉果】</a> <a href="t982242-1-1" title="板块: 杉果
            作者: HelloFuN (2024-12-19)
            浏览: 1858 / 回复: 43
            最后: robinlin (2024-12-19)" target="_blank">杉果  四海兄弟:最终版 ￥13（Steam版）</a></li></ol>
            </div></div></div><div id="portal_block_442" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="f251-1" target="_blank" style="color: !important;">游戏专区</a></span></div><div id="portal_block_442_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-1380807" target="_blank">aokprc</a></em><a href="f299-1" target="_blank">【反恐精英】</a> <a href="t982432-1-1" title="板块: 反恐精英
            作者: aokprc (2024-12-20)
            浏览: 359 / 回复: 4
            最后: wyrmmm (2024-12-20)" target="_blank">CS2 2024/12/20更新日志 沙鹰特殊检视回归</a></li><li><em><a href="suid-1025876" target="_blank">nuanshu52</a></em><a href="f246-1" target="_blank">【艺电】</a> <a href="t982426-1-1" title="板块: 艺电
            作者: nuanshu52 (2024-12-20)
            浏览: 456 / 回复: 7
            最后: ufo19851007 (2024-12-20)" target="_blank">《战地五》登陆即可领取三款皮肤和2万连队币</a></li><li><em><a href="suid-178889" target="_blank">社区活动帐号</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982303-1-1" title="板块: 综合讨论
            作者: 社区活动帐号 (2024-12-20)
            浏览: 1002 / 回复: 118
            最后: Karaxebury (2024-12-20)" target="_blank">【福利】冬促来袭，《极限竞速：地平线5》年度收官折扣！晒愿望单赢取游戏激活码！</a></li><li><em><a href="suid-470312" target="_blank">无月</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982404-1-1" title="板块: 综合讨论
            作者: 无月 (2024-12-20)
            浏览: 700 / 回复: 8
            最后: 明天有新鲜事烦 (2024-12-20)" target="_blank">《饥荒联机版》冬季盛宴+新增点数链接+新皮肤</a></li><li><em><a href="suid-805871" target="_blank">VeryTRT</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="forum.php?mod=forumdisplay&fid=251&filter=typeid&typeid=666" target="_blank">【游戏杂谈】</a> <a href="t982358-1-1" title="板块: 综合讨论
            作者: VeryTRT (2024-12-19)
            浏览: 651 / 回复: 17
            最后: 西部苦力怕 (2024-12-20)" target="_blank">关于我终于成为全平台玩家却沉迷小丑牌这件事</a></li><li><em><a href="suid-1561797" target="_blank">TaTaLe_</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="forum.php?mod=forumdisplay&fid=251&filter=typeid&typeid=761" target="_blank">【怪物猎人】</a> <a href="t982348-1-1" title="板块: 综合讨论
            作者: TaTaLe_ (2024-12-19)
            浏览: 752 / 回复: 10
            最后: 上心。 (2024-12-20)" target="_blank">《怪物猎人：荒野》主机平台性能参数规格公布：PS5性能模式1080P 60帧</a></li><li><em><a href="suid-439543" target="_blank">huobaohuangxu</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982321-1-1" title="板块: 综合讨论
            作者: huobaohuangxu (2024-12-19)
            浏览: 891 / 回复: 12
            最后: yufei86 (2024-12-20)" target="_blank">万代、SE、育碧将在12月21日直播的Grand Game Awards上公开新预告</a></li><li><em><a href="suid-279707" target="_blank">noah_mio</a></em><a href="f275-1" target="_blank">【主机平台】</a> <a href="t982301-1-1" title="板块: 主机平台
            作者: noah_mio (2024-12-19)
            浏览: 222 / 回复: 1
            最后: Nevein (2024-12-19)" target="_blank">日本主机游戏软硬件销量【12/9～12/15】《超马派对空前盛会》第6周登顶! 年末多人游戏</a></li><li><em><a href="suid-299136" target="_blank">HelloFuN</a></em><a href="f328-1" target="_blank">【移动平台】</a> <a href="t982292-1-1" title="板块: 移动平台
            作者: HelloFuN (2024-12-19)
            浏览: 423 / 回复: 2
            最后: 伸手潜水看看 (2024-12-19)" target="_blank">都市二次元游戏《望月》最新实机内容 &amp; 首轮测试「朔月测试」招募现已开始</a></li><li><em><a href="suid-175501" target="_blank">Licarus</a></em><a href="f129-1" target="_blank">【游戏互鉴】</a> <a href="t982277-1-1" title="板块: 游戏互鉴
            作者: Licarus (2024-12-19)
            浏览: 509 / 回复: 7
            最后: Licarus (2024-12-20)" target="_blank">一次性锐评个够，评价一下本年度我深入体验的十款游戏</a></li><li><em><a href="suid-436971" target="_blank">YouNg、</a></em><a href="f337-1" target="_blank">【明日方舟】</a> <a href="t982260-1-1" title="板块: 明日方舟
            作者: YouNg、 (2024-12-19)
            浏览: 101 / 回复: 0
            最后: YouNg、 (2024-12-19)" target="_blank">【新增服饰】邀雪 - 哈洛德</a></li><li><em><a href="suid-1360343" target="_blank">constont</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="forum.php?mod=forumdisplay&fid=251&filter=typeid&typeid=666" target="_blank">【游戏杂谈】</a> <a href="t982237-1-1" title="板块: 综合讨论
            作者: constont (2024-12-19)
            浏览: 384 / 回复: 9
            最后: roccoLiang (2024-12-20)" target="_blank">年度游戏总结加碎碎念</a></li><li><em><a href="suid-561601" target="_blank">superfinal</a></em><a href="f245-1" target="_blank">【育碧】</a> <a href="t982251-1-1" title="板块: 育碧
            作者: superfinal (2024-12-19)
            浏览: 801 / 回复: 11
            最后: lurker@@ (2024-12-19)" target="_blank">最后的波纹：《不羁联盟》第3赛季现已推出</a></li><li><em><a href="suid-211041" target="_blank">qqq888555ppp</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982250-1-1" title="板块: 综合讨论
            作者: qqq888555ppp (2024-12-19)
            浏览: 1211 / 回复: 38
            最后: zhaoqijiadekeng (2024-12-19)" target="_blank">《伊苏X Proud NORDICS》2025年推出</a></li><li><em><a href="suid-211041" target="_blank">qqq888555ppp</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t982248-1-1" title="板块: 综合讨论
            作者: qqq888555ppp (2024-12-19)
            浏览: 1606 / 回复: 53
            最后: weiamao (2024-12-20)" target="_blank">《空之轨迹1st》2025秋季PS5/NS/STEAM同步发售</a></li></ol>
            </div></div></div><div id="portal_block_436" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="f301-1" target="_blank" style="color: !important;">问题互助</a></span></div><div id="portal_block_436_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-355549" target="_blank">忆梦忘今</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982434-1-1" title="板块: 平台技术问题
            作者: 忆梦忘今 (2024-12-20)
            浏览: 69 / 回复: 4
            最后: 忆梦忘今 (2024-12-20)" target="_blank">steam客户端浏览时会自己关上</a></li><li><em><a href="suid-182950" target="_blank">suyiyi520</a></em><a href="f302-1" target="_blank">【平台购物问题】</a> <a href="t982430-1-1" title="板块: 平台购物问题
            作者: suyiyi520 (2024-12-20)
            浏览: 244 / 回复: 6
            最后: zcbgood (2024-12-20)" target="_blank">《RimWorld》没有大包什么的吗....</a></li><li><em><a href="suid-575641" target="_blank">云中星球</a></em><a href="f322-1" target="_blank">【软件硬件／其他问题】</a> <a href="t982355-1-1" title="板块: 软件硬件／其他问题
            作者: 云中星球 (2024-12-19)
            浏览: 535 / 回复: 16
            最后: suzuya625 (2024-12-20)" target="_blank">为什么铠侠RC20寿命用的这么快？</a></li><li><em><a href="suid-1633884" target="_blank">KooTi</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982350-1-1" title="板块: 平台技术问题
            作者: KooTi (2024-12-19)
            浏览: 78 / 回复: 3
            最后: Lynn_gr (2024-12-20)" target="_blank">steam测试版bug之消失的截图键</a></li><li><em><a href="suid-1168678" target="_blank">TheRambler</a></em><a href="f322-1" target="_blank">【软件硬件／其他问题】</a> <a href="t982344-1-1" title="板块: 软件硬件／其他问题
            作者: TheRambler (2024-12-19)
            浏览: 257 / 回复: 12
            最后: pl0okm (2024-12-20)" target="_blank">现在更新游戏本是不是最好的时刻。</a></li><li><em><a href="suid-1761632" target="_blank">星之夜</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982318-1-1" title="板块: 平台技术问题
            作者: 星之夜 (2024-12-19)
            浏览: 225 / 回复: 7
            最后: JadeQing (2024-12-20)" target="_blank">steam探索列队推荐游戏</a></li><li><em><a href="suid-1504813" target="_blank">STEAMMZF666</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982300-1-1" title="板块: 平台技术问题
            作者: STEAMMZF666 (2024-12-19)
            浏览: 341 / 回复: 6
            最后: 夏侯仪 (2024-12-19)" target="_blank">19 级 2,999 点经验值。升到 20 级还需 1 点经验值</a></li><li><em><a href="suid-1636271" target="_blank">小马同学</a></em><a href="f302-1" target="_blank">【平台购物问题】</a> <a href="t982282-1-1" title="板块: 平台购物问题
            作者: 小马同学 (2024-12-19)
            浏览: 378 / 回复: 3
            最后: chaselau007 (2024-12-19)" target="_blank">STEAM有哪些游戏免费的并且可以+1啊？</a></li><li><em><a href="suid-1205975" target="_blank">zyl727</a></em><a href="f322-1" target="_blank">【软件硬件／其他问题】</a> <a href="t982279-1-1" title="板块: 软件硬件／其他问题
            作者: zyl727 (2024-12-19)
            浏览: 245 / 回复: 11
            最后: cavalr (2024-12-19)" target="_blank">求助如何在Word或者PDF软件中打出这样子排列的数字</a></li><li><em><a href="suid-670312" target="_blank">飞起玉龙三百万</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982270-1-1" title="板块: 平台技术问题
            作者: 飞起玉龙三百万 (2024-12-19)
            浏览: 409 / 回复: 4
            最后: Duchozol (2024-12-19)" target="_blank">年度回顾怎么买第三个展示柜啊？</a></li><li><em><a href="suid-518223" target="_blank">咖喱給給</a></em><a href="f302-1" target="_blank">【平台购物问题】</a> <a href="t982268-1-1" title="板块: 平台购物问题
            作者: 咖喱給給 (2024-12-19)
            浏览: 212 / 回复: 12
            最后: 啪啪啪嘿嘿嘿 (2024-12-19)" target="_blank">steam的apex客户端无法用余额充值</a></li><li><em><a href="suid-1817420" target="_blank">Serenatata</a></em><a href="f304-1" target="_blank">【其乐社区问题】</a> <a href="t982255-1-1" title="板块: 其乐社区问题
            作者: Serenatata (2024-12-19)
            浏览: 230 / 回复: 9
            最后: 烤红薯 (2024-12-19)" target="_blank">20积分指日可待 doge</a></li><li><em><a href="suid-362327" target="_blank">shingouki123</a></em><a href="f322-1" target="_blank">【软件硬件／其他问题】</a> <a href="t982252-1-1" title="板块: 软件硬件／其他问题
            作者: shingouki123 (2024-12-19)
            浏览: 373 / 回复: 11
            最后: Rikkaoo (2024-12-20)" target="_blank">无线键鼠插在电脑上巨卡无比怎么解决</a></li><li><em><a href="suid-1710500" target="_blank">瑞秋秋</a></em><a href="f301-1" target="_blank">【平台技术问题】</a> <a href="t982249-1-1" title="板块: 平台技术问题
            作者: 瑞秋秋 (2024-12-19)
            浏览: 272 / 回复: 3
            最后: juken (2024-12-19)" target="_blank">买完点数商店所有展柜以后，再次升到10的整数倍等级还能获得新展柜吗？</a></li><li><em><a href="suid-1117965" target="_blank">slander03477</a></em><a href="f302-1" target="_blank">【平台购物问题】</a> <a href="t982240-1-1" title="板块: 平台购物问题
            作者: slander03477 (2024-12-19)
            浏览: 340 / 回复: 7
            最后: oceanleemy (2024-12-19)" target="_blank">stm上的恶魔城系列有哪些，求科普</a></li></ol>
            </div></div></div><div id="portal_block_437" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="f148-1" target="_blank" style="color: !important;">水区杂谈</a></span></div><div id="portal_block_437_content" class="dxb_bc"><!--prefix_typeid:375,290,457,407,291,288,585,590,620,601,710,731,286,287,318,319,453,593,594,595,602,596,318,846,847,848,857,965,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-348726" target="_blank">龙葵</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=731" target="_blank">【每周话题】</a> <a href="t981845-1-1" title="板块: 谈天说地
            作者: 龙葵 (2024-12-16)
            浏览: 20 / 回复: 4
            最后: 初恋那一件事 (2024-12-16)" target="_blank">[每周话题] 本届TGA中有哪些你期待的新游戏？</a></li><li><em><a href="suid-1685131" target="_blank">fhlsy</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982457-1-1" title="板块: 谈天说地
            作者: fhlsy (2024-12-20)
            浏览: 0 / 回复: 0
            最后: fhlsy (2024-12-20)" target="_blank">《Tormented Souls》只需3.8</a></li><li><em><a href="suid-1281756" target="_blank">夜星の星夜</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982446-1-1" title="板块: 谈天说地
            作者: 夜星の星夜 (2024-12-20)
            浏览: 0 / 回复: 0
            最后: 夜星の星夜 (2024-12-20)" target="_blank">河南移动评价得10g区域流量（不填小区等于省内流量）</a></li><li><em><a href="suid-504645" target="_blank">GarenMorbid</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982444-1-1" title="板块: 谈天说地
            作者: GarenMorbid (2024-12-20)
            浏览: 174 / 回复: 8
            最后: 西部苦力怕 (2024-12-20)" target="_blank">Frontwing的游戏是真的保值啊</a></li><li><em><a href="suid-1281756" target="_blank">夜星の星夜</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=710" target="_blank">【好价实物】</a> <a href="t982429-1-1" title="板块: 谈天说地
            作者: 夜星の星夜 (2024-12-20)
            浏览: 280 / 回复: 29
            最后: hzcsc (2024-12-20)" target="_blank">工行必中立减金（随机，最低1，也看到5，先到先得，晚来的估计全是空包）</a></li><li><em><a href="suid-257427" target="_blank">jeancharles2009</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982406-1-1" title="板块: 谈天说地
            作者: jeancharles2009 (2024-12-20)
            浏览: 212 / 回复: 12
            最后: chizunegoto (2024-12-20)" target="_blank">提车开箱，日产天籁 Nissan Altima</a></li><li><em><a href="suid-583674" target="_blank">s1a7m13</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982395-1-1" title="板块: 谈天说地
            作者: s1a7m13 (2024-12-20)
            浏览: 230 / 回复: 10
            最后: s1a7m13 (2024-12-20)" target="_blank">無聊水一下~紓壓!樂樂 &gt;~&lt;</a></li><li><em><a href="suid-236485" target="_blank">vimiaozi</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982386-1-1" title="板块: 谈天说地
            作者: vimiaozi (2024-12-20)
            浏览: 368 / 回复: 3
            最后: 苦不堪言fbk (2024-12-20)" target="_blank">杀戮尖塔平史低21.25元</a></li><li><em><a href="suid-1503178" target="_blank">ShadowMistress</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982360-1-1" title="板块: 谈天说地
            作者: ShadowMistress (2024-12-19)
            浏览: 487 / 回复: 9
            最后: hzcsc (2024-12-20)" target="_blank">哈基粮！这次先不骂你了</a></li><li><em><a href="suid-1447053" target="_blank">游鱼有余</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982354-1-1" title="板块: 谈天说地
            作者: 游鱼有余 (2024-12-19)
            浏览: 321 / 回复: 13
            最后: skped (2024-12-20)" target="_blank">晒一下我的多邻国年度报告</a></li><li><em><a href="suid-439543" target="_blank">huobaohuangxu</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=291" target="_blank">【影音时尚】</a> <a href="t982329-1-1" title="板块: 谈天说地
            作者: huobaohuangxu (2024-12-19)
            浏览: 263 / 回复: 16
            最后: Walcott (2024-12-20)" target="_blank">DC新《超人》电影将于2025年7月11日北美上映</a></li><li><em><a href="suid-1622689" target="_blank">m5472746787</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982309-1-1" title="板块: 谈天说地
            作者: m5472746787 (2024-12-19)
            浏览: 773 / 回复: 50
            最后: xenosum (2024-12-20)" target="_blank">啊要考研了，好紧张</a></li><li><em><a href="suid-1641283" target="_blank">cocfive</a></em><a href="forum.php?mod=forumdisplay&fid=148&filter=typeid&typeid=375" target="_blank">【灌水杂谈】</a> <a href="t982307-1-1" title="板块: 谈天说地
            作者: cocfive (2024-12-19)
            浏览: 982 / 回复: 34
            最后: smilez (2024-12-20)" target="_blank">啊啊啊，好纠结等不等临时工啊</a></li><li><em><a href="suid-1706498" target="_blank">55555u</a></em><a href="forum.php?mod=forumdisplay&fid=330&filter=typeid&typeid=594" target="_blank">【数码硬件】</a> <a href="t982288-1-1" title="板块: 开箱晒物
            作者: 55555u (2024-12-19)
            浏览: 466 / 回复: 10
            最后: smilez (2024-12-20)" target="_blank">开个vidda 发现X 85寸电视</a></li><li><em><a href="suid-288168" target="_blank">eli8200</a></em><a href="forum.php?mod=forumdisplay&fid=273&filter=typeid&typeid=318" target="_blank">【佳肴分享】</a> <a href="t982284-1-1" title="板块: 美食烹饪
            作者: eli8200 (2024-12-19)
            浏览: 364 / 回复: 21
            最后: Theshy8 (2024-12-20)" target="_blank">【小白开始学做饭】葱爆羊肉</a></li></ol>
            </div></div></div><div id="portal_block_434" class="block move-span"><div class="blocktitle title"><span class="titletext" style="float:;margin-left:px;font-size:"><a href="forum.php?mod=guide&amp;view=hot" target="_blank" style="color: !important;">热门主题</a></span></div><div id="portal_block_434_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982198-1-1" title="板块: 购物心得
            作者: 万狐飞仙 (2024-12-19)
            浏览: 11211 / 回复: 264
            最后: orzcccc (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">【新增 4 款】不久前 KONAMI 旗下短暂解锁国区的游戏再次解除锁区</font></a></li><li><em><a href="suid-472159" target="_blank">Xiao-Nan</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t981444-1-1" title="板块: 热点聚焦
            作者: Xiao-Nan (2024-12-13)
            浏览: 8766 / 回复: 172
            最后: shevasun (2024-12-19)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">TGA 2024游戏内容汇总，巫师4 | 艾尔登法环 衍生作 | 幻裂奇境 | 鬼武者 剑道 | 顽皮狗新作 等</font></a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t981947-1-1" title="板块: 福利放送
            作者: 万狐飞仙 (2024-12-17)
            浏览: 7839 / 回复: 112
            最后: Ailuen_Ran (2024-12-19)" target="_blank">【更新Steam大奖贴纸样式】2024 年 Steam 冬季特卖将有 20 张动画贴纸可以免费获取</a></li><li><em><a href="suid-178889" target="_blank">社区活动帐号</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t981574-1-1" title="板块: 热点聚焦
            作者: 社区活动帐号 (2024-12-14)
            浏览: 7671 / 回复: 647
            最后: 似梦羽惜 (2024-12-20)" target="_blank">【愿望单抽奖】由《胡闹厨房》原班团队打造，全新的双人冒险《怪镇奇旅》即将展开</a></li><li><em><a href="suid-637225" target="_blank">xiaoanan123</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t982034-1-1" title="板块: 购物心得
            作者: xiaoanan123 (2024-12-17)
            浏览: 7561 / 回复: 84
            最后: moonish (2024-12-20)" target="_blank">建议号上有黑猴的兄弟每天开一个补充包</a></li><li><em><a href="suid-1456045" target="_blank">朧月猫</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t981844-1-1" title="板块: 福利放送
            作者: 朧月猫 (2024-12-16)
            浏览: 7209 / 回复: 80
            最后: Guooooooo (2024-12-20)" target="_blank"><font style="color: #8F2A90;">好评《Sky Is Arrows》将转为付费，预计15刀，现在限时免费入库</font></a></li><li><em><a href="suid-1640586" target="_blank">冰雪聪明琪露诺</a></em><a href="f271-1" target="_blank">【慈善包】</a> <a href="t981714-1-1" title="板块: 慈善包
            作者: 冰雪聪明琪露诺 (2024-12-15)
            浏览: 6715 / 回复: 89
            最后: 凤龙逆鳞 (2024-12-17)" target="_blank">HB 假日返场包 Humble Heroines: Action, Adventure, &amp; Intrigue Bundle上线，${'$'}12 可获得全部内容</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t981944-1-1" title="板块: 热点聚焦
            作者: 万狐飞仙 (2024-12-17)
            浏览: 6349 / 回复: 83
            最后: 狐火 (2024-12-20)" target="_blank">今年冬促以及未来大促很可能不再有“探索队列获取活动卡牌”</a></li><li><em><a href="suid-163965" target="_blank">万狐飞仙</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t982196-1-1" title="板块: 热点聚焦
            作者: 万狐飞仙 (2024-12-19)
            浏览: 6223 / 回复: 141
            最后: QFTFHT (2024-12-21)" target="_blank"><font style="color: #EC1282;">2024 年 STEAM 回顾页面上线 | 查看回顾获取徽章</font></a></li><li><em><a href="suid-1697899" target="_blank">RainCandyi</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t981711-1-1" title="板块: 购物心得
            作者: RainCandyi (2024-12-15)
            浏览: 6141 / 回复: 160
            最后: NuXiaoMiao (2024-12-18)" target="_blank">《魔戒：咕噜》一折新史低：19.9</a></li><li><em><a href="suid-1645454" target="_blank">油迹</a></em><a href="f271-1" target="_blank">【慈善包】</a> <a href="t982144-1-1" title="板块: 慈善包
            作者: 油迹 (2024-12-18)
            浏览: 5864 / 回复: 109
            最后: 辰＿蘇 (2024-12-19)" target="_blank"><font style="color: #EC1282;">【仅限国区】HB双旦1个月/66元优惠券</font></a></li><li><em><a href="suid-1361151" target="_blank">好马配好鞍</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t981945-1-1" title="板块: 购物心得
            作者: 好马配好鞍 (2024-12-17)
            浏览: 5685 / 回复: 101
            最后: hsly233 (2024-12-19)" target="_blank">《文明6》本体￥1.98</a></li><li><em><a href="suid-1607313" target="_blank">櫻小路露娜</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t981978-1-1" title="板块: 购物心得
            作者: 櫻小路露娜 (2024-12-17)
            浏览: 5555 / 回复: 36
            最后: a15246753690 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">【Square Enix 冬季大促】《歧路旅人 II》《浪漫沙加2 七英雄的复仇》新史低 | 百余款折扣</font></a></li><li><em><a href="suid-190381" target="_blank">Hazardosu</a></em><a href="f319-1" target="_blank">【福利放送】</a> <a href="t982315-1-1" title="板块: 福利放送
            作者: Hazardosu (2024-12-19)
            浏览: 4678 / 回复: 149
            最后: sbin456 (2024-12-21)" target="_blank"><font style="font-weight: 900;color: #EE5023;">Fanatical 限量免费领取《灾厄逆刃（ScourgeBringer）》</font></a></li><li><em><a href="suid-1557057" target="_blank">kabpy</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="t981630-1-1" title="板块: 购物心得
            作者: kabpy (2024-12-14)
            浏览: 4675 / 回复: 65
            最后: zxy19981215 (2024-12-19)" target="_blank">ff7rebirth预购好价43%off/￥256（仅国区）</a></li></ol>
            </div></div></div><div id="portal_block_435" class="block move-span"><div class="blocktitle title" style=""><span class="titletext" style="float:;margin-left:px;font-size:"><a href="forum.php?mod=guide&amp;view=digest" target="_blank" style="color: !important;">精华主题</a></span></div><div id="portal_block_435_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-200379" target="_blank">のの不想想名字</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t981928-1-1" title="板块: 华语汉化
            作者: のの不想想名字 (2024-12-16)
            浏览: 1064 / 回复: 26
            最后: 小学生制裁者 (2024-12-20)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">【20241216】伊苏 系列汉化汇总贴with伊苏6FO手柄震动恢复</font></a></li><li><em><a href="suid-472159" target="_blank">Xiao-Nan</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t981444-1-1" title="板块: 热点聚焦
            作者: Xiao-Nan (2024-12-13)
            浏览: 8766 / 回复: 172
            最后: shevasun (2024-12-19)" target="_blank"><font style="font-weight: 900;color: #2B65B7;">TGA 2024游戏内容汇总，巫师4 | 艾尔登法环 衍生作 | 幻裂奇境 | 鬼武者 剑道 | 顽皮狗新作 等</font></a></li><li><em><a href="suid-1511408" target="_blank">chrisevansbian</a></em><a href="f235-1" target="_blank">【成就指南】</a> <a href="t981229-1-1" title="板块: 成就指南
            作者: chrisevansbian (2024-12-12)
            浏览: 1008 / 回复: 4
            最后: 顽皮的赛尔 (2024-12-19)" target="_blank"><font style="font-weight: 900;color: #2897C5;">《蜗牛鲍勃2：小麻烦/Snail Bob 2: Tiny Troubles》全成就指南，附全收集存档</font></a></li><li><em><a href="suid-472159" target="_blank">Xiao-Nan</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t979294-1-1" title="板块: 热点聚焦
            作者: Xiao-Nan (2024-11-30)
            浏览: 10129 / 回复: 104
            最后: @默舞 (2024-12-18)" target="_blank"><font style="color: #EC1282;">2024年12月发售游戏简单总结，流放之路2 | 夺宝奇兵 古老之圈 | 纪念碑谷3 | 妖精的尾巴2 等</font></a></li><li><em><a href="suid-1361151" target="_blank">好马配好鞍</a></em><a href="f234-1" target="_blank">【购物心得】</a> <a href="forum.php?mod=forumdisplay&fid=234&filter=typeid&typeid=786" target="_blank">【临时工】</a> <a href="t979162-1-1" title="板块: 购物心得
            作者: 好马配好鞍 (2024-11-29)
            浏览: 17481 / 回复: 155
            最后: Huyvie (2024-12-19)" target="_blank">[12/04更新5]控制台法可购买的临时工包整理</a></li><li><em><a href="suid-213219" target="_blank">weiyun</a></em><a href="f129-1" target="_blank">【游戏互鉴】</a> <a href="t977711-1-1" title="板块: 游戏互鉴
            作者: weiyun (2024-11-24)
            浏览: 1700 / 回复: 22
            最后: caimao (2024-11-28)" target="_blank">weplay 2024 逛展体验以及部分游戏试玩报告</a></li><li><em><a href="suid-200379" target="_blank">のの不想想名字</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t978400-1-1" title="板块: 华语汉化
            作者: のの不想想名字 (2024-11-24)
            浏览: 2446 / 回复: 43
            最后: @默舞 (2024-12-15)" target="_blank"><font style="color: #8F2A90;">【Linux/SteamDeck&amp;GOG测试可用】Saints Row 黑道圣徒系列 绿色汉化包 最终版（大概）</font></a></li><li><em><a href="suid-1506674" target="_blank">西部苦力怕</a></em><a href="f257-1" target="_blank">【华语汉化】</a> <a href="t965821-1-1" title="板块: 华语汉化
            作者: 西部苦力怕 (2024-09-03)
            浏览: 11970 / 回复: 65
            最后: @默舞 (2024-12-18)" target="_blank">缄默法则 - 黑帮之城/Omerta - City of Gangsters Steam简繁中文补丁，支持解锁成就</a></li><li><em><a href="suid-472159" target="_blank">Xiao-Nan</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t965390-1-1" title="板块: 热点聚焦
            作者: Xiao-Nan (2024-08-31)
            浏览: 19790 / 回复: 192
            最后: 帝瞎弑天 (2024-12-16)" target="_blank">2024年9月发售游戏简单总结，宇宙机器人 | 逆转检事 | 星际战士2 | 冰汽时代2 | 塞尔达传说</a></li><li><em><a href="suid-453956" target="_blank">mzxjq</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t965148-1-1" title="板块: 综合讨论
            作者: mzxjq (2024-08-29)
            浏览: 12204 / 回复: 94
            最后: dfbvf (2024-12-13)" target="_blank">【黑神话悟空】剧情解读、原著参考、个人见解。（8w字）</a></li><li><em><a href="suid-360740" target="_blank">fuckingretard</a></em><a href="f251-1" target="_blank">【综合讨论】</a> <a href="t964851-1-1" title="板块: 综合讨论
            作者: fuckingretard (2024-08-27)
            浏览: 17248 / 回复: 130
            最后: @默舞 (2024-12-15)" target="_blank">黑神话：悟空3周目通关了，水一贴</a></li><li><em><a href="suid-472159" target="_blank">Xiao-Nan</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t963871-1-1" title="板块: 热点聚焦
            作者: Xiao-Nan (2024-08-21)
            浏览: 17117 / 回复: 116
            最后: @默舞 (2024-12-18)" target="_blank">科隆开幕之夜汇总，无主之地4 | 消逝的光芒 困兽 | 文明7 | 夺宝奇兵 | 四海兄弟 故乡 等</a></li><li><em><a href="suid-593667" target="_blank">星鸢</a></em><a href="f161-1" target="_blank">【热点聚焦】</a> <a href="t961542-1-1" title="板块: 热点聚焦
            作者: 星鸢 (2024-08-11)
            浏览: 23556 / 回复: 258
            最后: @默舞 (2024-12-18)" target="_blank">【第五期】Arrowhead和索尼把《绝地潜兵2》的巨大成功变成了一本反面教材</a></li><li><em><a href="suid-213219" target="_blank">weiyun</a></em><a href="f235-1" target="_blank">【成就指南】</a> <a href="t960058-1-1" title="板块: 成就指南
            作者: weiyun (2024-07-27)
            浏览: 13883 / 回复: 32
            最后: guoguoguo (2024-11-25)" target="_blank">王国保卫战5（Kingdom Rush 5: Alliance）所有塔，英雄和敌人数据提取</a></li><li><em><a href="suid-213219" target="_blank">weiyun</a></em><a href="f129-1" target="_blank">【游戏互鉴】</a> <a href="t957593-1-1" title="板块: 游戏互鉴
            作者: weiyun (2024-07-08)
            浏览: 13462 / 回复: 37
            最后: wangling01shi (2024-11-04)" target="_blank">2023以及2024年上半年解谜游戏综合盘点（也可作为夏促参考）</a></li></ol>
            </div></div></div><div id="portal_block_438" class="block move-span"><div class="blocktitle title"><span style="float:;margin-left:px;font-size:" class="titletext"><a href="steamcn_gift-7l.html" target="_blank" style="color: !important;">赠楼</a></span></div><div id="portal_block_438_content" class="dxb_bc"><!--prefix_fid:127,129,140,148,161,189,200,232,235,234,238,244,245,246,248,251,254,257,259,271,273,274,275,276,277,279,280,291,292,299,300,301,302,304,332,313,318,303,322,201,305,312,316,319,325,326,328,330,335,337,338,339,340,343,262,345-->
            <!--prefix_typeid:352,550,553,651,654,658,688,690,691,708,770,660,467,730,789,364,550,551,552,553,554,556,645,741,318,844,847,848,761,763,676,401,666,102,786,652,653,993,770-->
            <div class="module cl xl xl1">
            <ol><li><em><a href="suid-293327" target="_blank">杜兰特</a></em><a href="t982422-1-1" title="板块: 蒸汽赠楼
            作者: 杜兰特 (2024-12-20)
            浏览: 0 / 回复: 0
            最后: 杜兰特 (2024-12-20)" target="_blank">[Steam 游戏] ScourgeBringer</a></li><li><em><a href="suid-1246801" target="_blank">时间入世</a></em><a href="t982419-1-1" title="板块: 蒸汽赠楼
            作者: 时间入世 (2024-12-20)
            浏览: 0 / 回复: 0
            最后: 时间入世 (2024-12-20)" target="_blank">[Steam 游戏] Turbo Golf Racing</a></li><li><em><a href="suid-1362145" target="_blank">scroogeyeye</a></em><a href="t982402-1-1" title="板块: 蒸汽赠楼
            作者: scroogeyeye (2024-12-20)
            浏览: 0 / 回复: 0
            最后: scroogeyeye (2024-12-20)" target="_blank">[Steam 游戏] ScourgeBringer</a></li><li><em><a href="suid-286375" target="_blank">IceWM</a></em><a href="t982389-1-1" title="板块: 蒸汽赠楼
            作者: IceWM (2024-12-20)
            浏览: 0 / 回复: 0
            最后: IceWM (2024-12-20)" target="_blank">[Steam 游戏] ScourgeBringer</a></li><li><em><a href="suid-1724371" target="_blank">Ferris6712</a></em><a href="t982376-1-1" title="板块: 蒸汽赠楼
            作者: Ferris6712 (2024-12-20)
            浏览: 0 / 回复: 0
            最后: Ferris6712 (2024-12-20)" target="_blank">[Steam 游戏] ScourgeBringer</a></li><li><em><a href="suid-250694" target="_blank">Lily</a></em><a href="t982371-1-1" title="板块: 蒸汽赠楼
            作者: Lily (2024-12-20)
            浏览: 0 / 回复: 0
            最后: Lily (2024-12-20)" target="_blank">[Steam 游戏] Trivia Vault: Literature Trivia</a></li><li><em><a href="suid-840810" target="_blank">天覇横空烈轟</a></em><a href="t982367-1-1" title="板块: 蒸汽赠楼
            作者: 天覇横空烈轟 (2024-12-20)
            浏览: 0 / 回复: 0
            最后: 天覇横空烈轟 (2024-12-20)" target="_blank">[Steam 游戏] Hidden FPS Shooting Top-Down 3D</a></li><li><em><a href="suid-558773" target="_blank">laog890</a></em><a href="t982356-1-1" title="板块: 蒸汽赠楼
            作者: laog890 (2024-12-19)
            浏览: 0 / 回复: 0
            最后: laog890 (2024-12-19)" target="_blank">[Steam 游戏] Rhino&#39;s Rage</a></li><li><em><a href="suid-556235" target="_blank">H2X3L2</a></em><a href="t982352-1-1" title="板块: 蒸汽赠楼
            作者: H2X3L2 (2024-12-19)
            浏览: 0 / 回复: 0
            最后: H2X3L2 (2024-12-19)" target="_blank">[Steam 游戏] Inexperienced Driver</a></li><li><em><a href="suid-556079" target="_blank">Sorer</a></em><a href="t982340-1-1" title="板块: 蒸汽赠楼
            作者: Sorer (2024-12-19)
            浏览: 0 / 回复: 0
            最后: Sorer (2024-12-19)" target="_blank">[Steam 游戏] CARGAME</a></li><li><em><a href="suid-556079" target="_blank">Sorer</a></em><a href="t982339-1-1" title="板块: 蒸汽赠楼
            作者: Sorer (2024-12-19)
            浏览: 0 / 回复: 0
            最后: Sorer (2024-12-19)" target="_blank">[Steam 游戏] Office Racer</a></li><li><em><a href="suid-556079" target="_blank">Sorer</a></em><a href="t982338-1-1" title="板块: 蒸汽赠楼
            作者: Sorer (2024-12-19)
            浏览: 0 / 回复: 0
            最后: Sorer (2024-12-19)" target="_blank">[Steam 游戏] Find the cubs</a></li><li><em><a href="suid-1393731" target="_blank">是神仙爸爸呀</a></em><a href="t982337-1-1" title="板块: 蒸汽赠楼
            作者: 是神仙爸爸呀 (2024-12-19)
            浏览: 0 / 回复: 0
            最后: 是神仙爸爸呀 (2024-12-19)" target="_blank">[Steam 游戏] ScourgeBringer</a></li><li><em><a href="suid-334780" target="_blank">是阑珊</a></em><a href="t982327-1-1" title="板块: 蒸汽赠楼
            作者: 是阑珊 (2024-12-19)
            浏览: 0 / 回复: 0
            最后: 是阑珊 (2024-12-19)" target="_blank">[Steam 游戏] School Trip</a></li><li><em><a href="suid-334780" target="_blank">是阑珊</a></em><a href="t982326-1-1" title="板块: 蒸汽赠楼
            作者: 是阑珊 (2024-12-19)
            浏览: 0 / 回复: 0
            最后: 是阑珊 (2024-12-19)" target="_blank">[Steam 游戏] Office Racer</a></li></ol>
            </div></div></div></div><div id="tabPAhn0P_content" class="tb-c"></div><script type="text/javascript">initTab("tabPAhn0P","mouseover");</script></div></div></div></div><!--[/diy]-->
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div style="width: 104px;">Steam 平台周边</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                                    <div class="subject_row_detail_pic"><a href="/f161-1" class="forum-icon hot">
                            <img src="template/steamcn_metro/src/img/forum-icons-v3/pc/f161.png">
                        </a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f161-1">
                                热点聚焦
                                 <span class="forum_today_post">(97)</span>                </a></div><br>
                            <div class="subject_row_detail_text_down">
                                热门话题讨论
                            </div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                                    <div class="subject_row_detail_pic"><a href="/f127-1" class="forum-icon hot">
                            <img src="template/steamcn_metro/src/img/forum-icons-v3/pc/f127.png">
                        </a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f127-1">
                                平台工具
                                 <span class="forum_today_post">(16)</span>                </a></div><br>
                            <div class="subject_row_detail_text_down">
                                进阶的技术分享和教程指引
                            </div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f257-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f257.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f257-1">华语汉化 <span
                                    class="forum_today_post">(17)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">适用于正版游戏的汉化</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f235-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f235.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f235-1">成就指南 <span
                                    class="forum_today_post">(1)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">游戏成就和达成攻略</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f129-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f129.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f129-1">游戏互鉴 <span
                                    class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">发布原创游戏评测</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f319-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f319.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f319-1">福利放送 <span
                                    class="forum_today_post">(369)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">免费领取游戏的机会</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f234-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f234.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f234-1">购物心得 <span
                                    class="forum_today_post">(467)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">购物经验和优惠信息</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f271-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f271.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f271-1">慈善包 <span
                                    class="forum_today_post">(9)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">物美价廉的游戏集合</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f201-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f201.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f201-1">交易中心 <span
                                    class="forum_today_post">(29)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">游戏、卡片、道具、市场</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f254-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f254.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f254-1">分享互赠 <span
                                    class="forum_today_post">(30)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">送出余裕的游戏或道具</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div>游戏平台</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f232-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f232.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f232-1">Origin</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">艺电旗下游戏平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f274-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f274.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f274-1">Uplay</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">育碧旗下游戏平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f276-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f276.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f276-1">GOG <span
                                    class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">无 DRM 机制的平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f326-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f326.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f326-1">Windows 商店                    <span class="forum_today_post">(1)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">微软游戏与软件平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f335-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f335.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f335-1">Epic Games <span
                                    class="forum_today_post">(1)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">虚幻引擎开发商旗下平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f345-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f345.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f345-1">战网</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">动视暴雪旗下游戏平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f316-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f316-a.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f316-1">杉果 <span
                                    class="forum_today_post">(3)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">中电博亚旗下代理平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f332-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f332.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f332-1">方块 <span
                                    class="forum_today_post">(1)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">皖新传媒旗下游戏平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f325-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f325.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f325-1">WeGame</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">腾讯旗下游戏平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f277-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f277-1.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f277-1">其他平台</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">Bethesda/Itch/R星/Stadia等</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f275-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f275.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f275-1">主机平台 <span
                                    class="forum_today_post">(11)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">PS，XBOX与任天堂等平台</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f328-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f328.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f328-1">移动平台</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">安卓与 iOS 手机游戏</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div>游戏讨论</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f251-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f251.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f251-1">综合讨论 <span
                                    class="forum_today_post">(174)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">综合游戏交流区</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                                    <div class="subject_row_detail_pic"><a href="/f337-1" class="forum-icon">
                            <img src="template/steamcn_metro/src/img/forum-icons-v3/pc/f337.png">
                        </a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f337-1">
                                明日方舟
                                                </a></div><br>
                            <div class="subject_row_detail_text_down">
                                明日方舟 专区
                            </div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f305-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f305.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f305-1">刀塔 <span
                                    class="forum_today_post">(4)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">DOTA</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f299-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f299.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f299-1">反恐精英 <span
                                    class="forum_today_post">(6)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">CS:GO / CS 2</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f244-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f244.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f244-1">威乐</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">半条命/军团要塞/求生之路等</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f246-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f246.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f246-1">艺电 <span
                                    class="forum_today_post">(3)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">战地/质量效应/极品飞车等</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f245-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f245.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f245-1">育碧</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">彩虹六号/刺客信条/魔法门等</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f248-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f248.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f248-1">动视暴雪</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">使命召唤/魔兽/炉石传说等</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                                    <div class="subject_row_detail_pic"><a href="/f339-1" class="forum-icon">
                            <img src="template/steamcn_metro/src/img/forum-icons-v3/pc/f339.png">
                        </a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f339-1">
                                虚拟现实
                                                </a></div><br>
                            <div class="subject_row_detail_text_down">
                                VR，AR，MR
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div>问题互助</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f301-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f301.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f301-1">平台技术问题 <span
                                    class="forum_today_post">(10)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">游戏平台相关的技术问题</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f302-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f302.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f302-1">平台购物问题 <span
                                    class="forum_today_post">(7)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">游戏平台相关的购物问题</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f304-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f304.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f304-1">其乐社区问题</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">积分、账号和制度的疑问</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f318-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f318.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f318-1">资源索取问题 <span
                                    class="forum_today_post">(35)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">寻求资源和帮忙索取</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f303-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f303.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f303-1">游戏 / 成就 / 汉化问题                    <span class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">围绕游戏相关的具体问题</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f322-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f322.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f322-1">软件硬件 / 其他问题                    <span class="forum_today_post">(27)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">软件硬件、网络和其他问题</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div>休闲杂谈</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f148-1"
                                                               class="forum-icon hot"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f148.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f148-1">谈天说地 <span
                                    class="forum_today_post">(190)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">论坛指定水区</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f259-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f259.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f259-1">摄影旅游 <span
                                    class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">摄影作品/游记/人物写真</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f273-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f273.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f273-1">美食烹饪 <span
                                    class="forum_today_post">(3)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">厨房心得/食谱/分享佳肴</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f200-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f200.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f200-1">软硬兼施 <span
                                    class="forum_today_post">(7)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">软件/硬件/网络话题</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f330-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f330.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f330-1">开箱晒物 <span
                                    class="forum_today_post">(4)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">我有一个大宝贝给你看嗷</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_middle_subject clearfix">
                <div class="middle_subject_left">
                    <div class="middle_subject_tiltle">
                        <div>天邈汉化组</div>
                    </div>
                    <div class="middle_subject_row">
                        <div class="middle_subject_detail">
                            <div class="middle_subject_detail_pic"><a href="/f280-1" class="forum-icon f280"><img
                                    src="template/steamcn_metro/src/img/forum-icons-v3/pc/f280.png"></a></div>
                            <div class="middle_subject_detail_text">
                                <div class="middle_subject_detail_text_up"><a href="/f280-1">汉化发布                        <span class="forum_today_post">(6)</span></a></div>
                                <br>
                                <div class="middle_subject_detail_text_down">天邈汉化组</div>
                            </div>
                        </div>
                        <div class="middle_subject_detail_right">
                            <div>
                                <div>
                                    <div class="dot"></div>
                                    <a href="/f281-1">成员招募</a>
                                </div>
                                <div style="margin-left:20px;">
                                    <div class="dot"></div>
                                    <a href="/f282-1">反馈讨论</a>
                                </div>
                            </div>
                            <br>
                            <div style="margin-top:7px;">
                                <div class="left_dot"></div>
                                <a href="https://steamcommunity.com/groups/tianmiao">Steam 群组 🗗</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="middle_subject_mid">
                    <div class="middle_subject_mid_tiltle">
                        <div>蒹葭汉化组</div>
                    </div>
                    <div class="middle_subject_row">
                        <div class="middle_subject_detail">
                            <div class="middle_subject_detail_pic"><a href="/f293-1" class="forum-icon f293"><img
                                    src="template/steamcn_metro/src/img/forum-icons-v3/pc/f293.png"></a></div>
                            <div class="middle_subject_detail_text">
                                <div class="middle_subject_mid_detail_text_up"><a href="/f293-1">汉化发布</a></div>
                                <br>
                                <div class="middle_subject_detail_text_down">蒹葭汉化组</div>
                            </div>
                        </div>
                        <div class="middle_subject_detail_right">
                            <div>
                                <div>
                                    <div class="dot"></div>
                                    <a href="/f294-1">成员招募</a>
                                </div>
                                <div style="margin-left:20px;">
                                    <div class="dot"></div>
                                    <a href="/f295-1">反馈讨论</a>
                                </div>
                            </div>
                            <br>
                            <div style="margin-top:8px;">
                                <div class="mid_dot"></div>
                                <a href="https://steamcommunity.com/groups/REEDjianjia">Steam 群组 🗗</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="middle_subject_right">
                    <div class="middle_subject_right_tiltle">
                        <div>起源汉化组</div>
                    </div>
                    <div class="middle_subject_row">
                        <div class="middle_subject_detail">
                            <div class="middle_subject_detail_pic"><a href="/f307-1" class="forum-icon f307"><img
                                    src="template/steamcn_metro/src/img/forum-icons-v3/pc/f307.png"></a></div>
                            <div class="middle_subject_detail_text">
                                <div class="middle_subject_right_detail_text_up"><a href="/f307-1">汉化发布</a></div>
                                <br>
                                <div class="middle_subject_detail_text_down">起源汉化组</div>
                            </div>
                        </div>
                        <div class="middle_subject_detail_right">
                            <div>
                                <div>
                                    <div class="dot"></div>
                                    <a href="/f308-1">成员招募</a>
                                </div>
                                <div style="margin-left:20px;">
                                    <div class="dot"></div>
                                    <a href="/f309-1">反馈讨论</a>
                                </div>
                            </div>
                            <br>
                            <div style="margin-top:8px;">
                                <div class="right_dot"></div>
                                <a href="https://steamcommunity.com/groups/sourcect">Steam 群组 🗗</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="index_subject">
                <div class="index_subject_title">
                    <div>社区服务</div>
                </div>
                <div class="index_subject_row">
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f140-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f140.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f140-1">公告发布</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">社区告示和动态</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f197-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f197.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f197-1">投诉反馈 <span
                                    class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">意见、建议和投诉</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f238-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f238.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f238-1">社区活动 <span
                                    class="forum_today_post">(2)</span></a></div>
                            <br>
                            <div class="subject_row_detail_text_down">社区与节日活动</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f141-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f141.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f141-1">管理团队</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">社区职员内部板块</div>
                        </div>
                    </div>
                    <div class="subject_row_detail">
                        <div class="subject_row_detail_pic"><a href="/f163-1"
                                                               class="forum-icon"><img
                                src="template/steamcn_metro/src/img/forum-icons-v3/pc/f163.png"></a></div>
                        <div class="subject_row_detail_text">
                            <div class="subject_row_detail_text_up"><a href="/f163-1">历史存档</a></div>
                            <br>
                            <div class="subject_row_detail_text_down">无效帖子归档</div>
                        </div>
                    </div>
                </div>
            </div>
            <p class="bbs_daily_stats">
                今日：<em>1540</em>｜昨日：<em>3096</em>｜帖子：<em>19547911</em>｜会员：<em>1963897</em>
                    ｜<a href="home.php?mod=space&amp;do=friend&amp;view=online&amp;type=member">在线会员</a>：<em>4349</em>
                人，最高记录是 <em>27540</em> 于 <em>2024-11-13</em>&nbsp;&nbsp;
            </p></div>
            <div class="subforunm_foot">
            <div class="subforunm_foot_bg"><img src="pic/foot_bg.png"></div>
            <div class="subforunm_foot_banner">
            <div class="subforunm_foot_text">
            <div>　　作为民间站点，自 2004 年起为广大中文 Steam 用户提供技术支持与讨论空间。历经二十余载风雨，如今已发展为国内最大的正版玩家据点。</div>
            </div>
            <div class="subforunm_foot_text_bottom">
            <div>
            <div><br /><a href="forum.php?mod=guide&amp;view=newthread" title="列表模式 - 最新主题">列表模式</a> · <div class="wechat-hover-qrcode"><div class="qrcode"><img src="/template/steamcn_metro/src/img/wechat-qrcode-v2.jpg"></div><a href="/t326248-1-1" title="其乐社区官方微信公众号">微信公众号</a></div> · <a href="http://weibo.com/steamcn" target="_blank" title="其乐社区官方微博">微博</a> · <a href="https://space.bilibili.com/531279346" target="_blank" title="其乐茸茸">Bilibili频道</a> · <a href="//steamcommunity.com/groups/keylol-player-club" target="_blank" title="其乐社区官方 Steam 群组">Steam 群组</a> · <a href="http://tieba.baidu.com/f?ie=utf-8&amp;kw=keylol" target="_blank" title="百度 Keylol 贴吧">贴吧</a> · <a href="t8598-1-1" target="_blank" title="其乐社区官方 Q 群">QQ群</a>&nbsp;</div>
            <div>Keylol 其乐 &copy;2004-2024 Chinese Steam User Fan Site.</div>
            </div>
            </div>
            </div>
            <script>var blocked_words = ["\/(GTA\\s?5|GTA\\s?V|\u4fa0\u76d7\u730e\u8f66\u624b\\s?5|\u4fa0\u76d7\u730e\u8f66\u624b\\s?V|\u4fe0\u76dc\u7375\u8eca\u624b\\s?5|271590|\u4fe0\u76dc\u7375\u8eca\u624b\\s?V|Grand Theft Auto V|\u6a2a\u884c\u9738\u9053\\s?5|\u4e09\u7537\u4e00\u72d7)\/i","\/(\u6218\u57304|\u6230\u57304|\u6218\u5730\u98ce\u4e914|\u6230\u5730\u98a8\u96f24|Battlefield\\s?4|1238860)\/i","\/(\u5947\u5f02\u4eba\u751f\u672c\u8272|\u5947\u7570\u4eba\u751f\u672c\u8272|\u5947\u5f02\u4eba\u751f\uff1a\u672c\u8272|\u5947\u7570\u4eba\u751f\uff1a\u672c\u8272|\u5947\u5f02\u4eba\u751f:\u672c\u8272|\u5947\u5f02\u4eba\u751f\\s?\u672c\u8272|936790|True\\s?colors|\u95ea\u70b9\u884c\u52a8|\u761f\u75ab\u516c\u53f8|246620|Plague\\s?Inc|\u5929\u671d\u4e0a\u56fd|\u82f1\u96c4\u5b66\u9662)\/i","\/(adult|Hentai|\u7ec5\u58eb|\u9a6c\u5934\u793e|subverse|R18|\u7eb3\u8fea\u4e9a\u4e4b\u5b9d|\u7eb3\u8fea\u4e9a|\u6da9\u60c5|\u8272\u60c5|\u60e9\u6212\u9b45\u9b54|\u9b45\u9b54)\/i","\/(\u4f2a\u5a18)\/i","\/(\u6b27\u9646\u98ce\u4e914|\u6b27\u9646\u98ce\u4e91IV|Europa\\s?Universalis\\s?4|Europa\\s?Universalis\\s?IV|EU4|\u6b27\u96464|236850)\/i","\/(\u94a2\u94c1\u96c4\u5fc33|\u94a2\u4e1d3|\u94a2\u94c1\u96c4\u5fc3III|Hearts\\s?of\\s?Iron\\s?III\\Hearts\\s?of\\s?Iron\\s?3|\u94a2\u94c1\u96c4\u5fc34|\u94a2\u4e1d4)\/i","\/(\u7ef4\u591a\u5229\u4e9a2|Victoria\\s?II|Victoria\\s?2|app\/42960)\/i","\/(\u6587\u660e\u65f6\u4ee32|Age\\s?of\\s?History\\s?II|Age\\s?of\\s?History\\s?2|603850)\/i","\/(\u5fc3\u8df3\u6587\u5b66\u90e8|\u5fc3\u8df3\u6587\u5b66\u4ff1\u4e50\u90e8|\u5fc3\u8df3\u6587\u5b66\u793e)\/i","\/((?<!The[ _])humankind|1124300|2229870|2732960)\/i","\/(Detention)\/i","\/(\u8fd4\u6821)\/i","\/(\u6bd2\u67ad|drug\\s?dealer)\/i","\/(t299037-1-1)\/i","\/(\u901a\u53e4\u65af|\u7981\u533a\u5b9e\u5f55)\/i","\/(\u68af\u5b50|\u9b54\u6cd5\u5de5\u5177|shadowsocks|v2ray|xray|iepl|iplc|\u6d41\u5a92\u4f53\u89e3\u9501|nexitelly|trojan|\u9178\u9178\u4e73|clashx|openclash|vmess|\u9632\u706b\u957f\u57ce|\u8f6f\u8def\u7531|\u88ab\u5899|\u8bf4\u9b54\u6cd5|\u4e0a\u7f51\u5de5\u5177|VPN|VPS|Telegram|ti\u5b50|\u4ee3\u7406|telegra|GFW)\/i","\/(\u8981\u9b54\u6cd5|\u5f00\u9b54\u6cd5|\u7528\u9b54\u6cd5|\u6302\u9b54\u6cd5|\u5f00\u4e86\u9b54\u6cd5|\u6302\u4e86\u9b54\u6cd5|\u5168\u9b54\u6cd5|\u79d1\u5b66\u4e0a\u7f51)\/i","\/(\u6709\u9b54\u6cd5|\u6709\u68af)\/i","\/(\u533a\u9b54\u6cd5)\/i","\/(\u9b54\u6cd5\u4e0a\u7f51|\u9b54\u6cd5\u8de8\u754c|\u7ffb\u5899|\u5168\u5c40\u9b54\u6cd5|\u722c\u5899|\u6302\u68af|\u5168\u5c40|\u7528\u9b54\u6cd5)\/i","\/(\u8282\u70b9)\/i","\/(\u673a\u573a)\/i","\/(\u7f8e\u5c11\u5973)\/i","\/(\u9ed1\u624b\u515a|mafia)\/i","\/(\u53bbDRM|\u5171\u515a|\u4e2d\u592e|\u5171\u4ea7|\u4e8c\u5341\u5927|\u4e60\u8fd1\u5e73|\u6253\u8840|\u7269\u8d28\u660e|\u751f\u52a8\u6d3b|\u6233\u529b|\u6253\u6c5f\u5c71)\/i","\/\\\/\\\/.*\\.?mmosvc\\.com\/i","\/(cdkeynogap)\/i","\/(cnbeta)\/i","\/(dick)\/i","\/(302\u5de5\u5177)\/i","\/(\u672c\u5730\u53cd\u4ee3)\/i","\/(OlduBil)\/i","\/(ChatGPT)\/i","\/(dlsite)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b)\/i","\/(\u547d\u4ee4\u4e0e\u5f81\u670d\uff1a\u5c06\u519b\u96f6\u70b9)\/i","\/(\u56fd\\\/\u6e2f\\\/\u53f0)\/i"]</script>
            <link rel="stylesheet" type="text/css" href="source/plugin/xunjie_attention/template/xunjie_attention.css">
            <div id="xunjie_attention_userlist" style="position:absolute;display:none;border:1px solid;min-width:0px;border-color:#DDD;background:#FEFEFE;width:220px;z-index:301;" onmouseleave="xunjie_attention_mOut(this);"></div>
            <div id="xunjie_attention_show" style="position:absolute;display:none;border:1px solid;min-width:60px;border-color:#DDD;background:#FEFEFE;z-index:301;" onmouseleave="xunjie_attention_mOut(this);"></div>
            <div id="xunjie_attention_w" style="display:none;"></div>
            <script>
            function xunjie_attention_getTop(e) {
            var offset = e.offsetTop;
            if(e.offsetParent != null) {
            offset += xunjie_attention_getTop(e.offsetParent);
            }         
            return offset;
            }
            function xunjie_attention_getLeft(e) {
            var offset = e.offsetLeft;
            if(e.offsetParent != null) {
            offset += xunjie_attention_getLeft(e.offsetParent);
            } 
            return offset;
            } 
            function xunjie_attention_follow(a, href) {
            if(a == 1) {
            document.getElementById('follow_a').href = href;
            document.getElementById('follow_li').classList.remove('fo_add');
            document.getElementById('follow_li').classList.add('fo_cancel');
            }
            if(a == 2) {
            document.getElementById('follow_a').href = href;
            document.getElementById('follow_li').classList.remove('fo_cancel');
            document.getElementById('follow_li').classList.add('fo_add');
            }
            }
            function xunjie_attention_mOver(div, id, link) {
            document.getElementById(id).style.display = '';
            document.getElementById(id).style.left = xunjie_attention_getLeft(div) + 'px';
            document.getElementById(id).style.top = xunjie_attention_getTop(div) + div.offsetHeight + 0.3 + 'px';
            if(typeof xunjie_attention_isexist == 'undefined') {
            xunjie_attention_isexist = 1;
            ajaxget(link, id, id);
            }
            }
            function xunjie_attention_mleave(id) {
                var div = document.getElementById(id);
                var x = event.clientX + window.pageXOffset;
                var y = event.clientY + window.pageYOffset;
                var divx1 = div.offsetLeft;
                var divy1 = div.offsetTop;
                var divx2 = div.offsetLeft + div.offsetWidth;
                var divy2 = div.offsetTop + div.offsetHeight;
            if(x < divx1 || x > divx2 || y < divy1 || y > divy2) {
            div.style.display = 'none';
            }	
            }
            function xunjie_attention_mOut(div) {
            div.style.display = 'none';
            }
            function xunjie_attention_userlist(div, id) {
            if(div.href != 'javascript:;') {
            ajaxget(div.href, id);
            div.href = 'javascript:;';
            }
            }
            </script>
            <script>xunjie_attention_2 = '关注TA';xunjie_attention_5 = '取消关注';ajaxget('plugin.php?id=xunjie_attention:attention&type=5&formhash=e5dc730e', 'xunjie_attention_s', 'xunjie_attention_w');</script>
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
            <div class="subforunm_foot_intro clearfix">
            <div class="subforunm_foot_intro_left">
            <div>Designed by <a href="//steamcommunity.com/profiles/76561198020599404/" target="_blank">Lee</a> in <a href="//www.bing.com/search?q=Balestier" target="_blank">Balestier</a>, Powered by <a href="https://www.discuz.vip/" target="_blank">Discuz!</a></div>
            <div style="margin-top:2px; margin-top:6px\0;">推荐使用 <a href="https://www.google.cn/chrome/" target="_blank">Chrome</a> 或 <a href="https://www.microsoft.com/edge" target="_blank">Microsoft Edge</a> 来浏览本站</div>
            </div>
            <div class="subforunm_foot_intro_right">
            <div>
            <a href="advertising_with_keylol_20210216.pdf" >广告投放</a><span class="pipe">|</span><a href="forum.php?mobile=yes" >手机版</a><span class="pipe">|</span><a href="#" >广州数趣信息科技有限公司 版权所有</a><span class="pipe">|</span><strong><a href="https://keylol.com" target="_blank">其乐 Keylol</a></strong>
            ( <a href="http://beian.miit.gov.cn/" target="_blank">粤ICP备17068105号</a> )<!-- Steam 蓝绿插件 -->
            <script src="https://steamdb.keylol.com/steam_info.js?v=0520"></script>

            <!-- 追加 Steam 来源 -->
            <script>
            (function (params) {
            	params.forEach(function (param) {
            		function e(e,r,t){
            			var a=new RegExp("([?&])"+r+"=.*?(&|${'$'})","i"),
            				n=-1!==e.indexOf("?")?"&":"?",
            				b='#app_reviews_hash',
            				s=-1!==e.indexOf(b)?'':b,
            				e=e.replace(b,'');
            			return e.match(a) ? e.replace(a,"${'$'}1"+r+"="+t+"${'$'}2"+s):e+n+r+"="+t+s
            		}
            		jQuery(param[0]).each(function(){
            			var r = jQuery(this);
            			r.attr("href", e(r.attr("href"), param[1], param[2]));
            			setInterval(function () {r.attr("href", e(r.attr("href"), param[1], param[2]))}, 1000);
            		});
            	});
            })([
            	// 追加 Steam 鉴赏家来源
            	['a[href^="http://store.steampowered.com/"],a[href^="https://store.steampowered.com/"]', 'utm_source', 'keylol'],
            	['a[href^="http://store.steampowered.com/"],a[href^="https://store.steampowered.com/"]', 'curator_clanid', '9730205'],
            	// 追加 Steam 来源：无敌号（731040）
            	['a[href^="http://store.steampowered.com/app/731040"],a[href^="https://store.steampowered.com/app/731040"]', 'utm_campaign', 'release'],
            ]);
            </script>

            <!-- 追加其他网站来源 -->
            <script>
            (function (params) {
            	params.forEach(function (param) {
            		function e(e,r,t){
            			var a=new RegExp("([?&])"+r+"=.*?(&|${'$'})","i"),
            				n=-1!==e.indexOf("?")?"&":"?";
            			return e.match(a)?e.replace(a,"${'$'}1"+r+"="+t+"${'$'}2"):e+n+r+"="+t
            		}
            		jQuery(param[0]).each(function(){
            			var r = jQuery(this);
            			r.attr("href", e(r.attr("href"), param[1], param[2]));
            			setInterval(function () {r.attr("href", e(r.attr("href"), param[1], param[2]))}, 1000);
            		});
            	});
            })([
            	// 追加育碧网站来源
            	['a[href*="ubi.com/"],a[href*="ubisoft.com/"],a[href*="ubisoft.com.cn/"]', 'ucid', 'DDP-ID_189202'],
            	['a[href*="ubi.com/"],a[href*="ubisoft.com/"],a[href*="ubisoft.com.cn/"]', 'maltcode', 'ubisoftstore_acqst_DDP_other___STORE____'],
            	// 追加 doaxvv 来源
            	['a[href^="https://game.doaxvv.com/"]', 'utm_source', 'universe'],
            	['a[href^="https://game.doaxvv.com/"]', 'utm_medium', 'cpm'],
            	['a[href^="https://game.doaxvv.com/"]', 'utm_campaign', '4th'],
            	['a[href^="https://game.doaxvv.com/"]', 'utm_content', '03_keylol_864x598'],
            	// 追加 HB 来源
            	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_source', 'Keylol'],
            	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_campaign', 'December_Choice24'],
            	['a[href^="http://www.humblebundle.com/"],a[href^="https://www.humblebundle.com/"],a[href^="http://zh.humblebundle.com/"],a[href^="https://zh.humblebundle.com/"]', 'utm_medium', 'Paid'],
            	// 追加 GMG 来源
            	['a[href^="http://www.greenmangaming.com/"],a[href^="https://www.greenmangaming.com/"]', 'utm_source', 'Keylol'],
            	['a[href^="http://www.greenmangaming.com/"],a[href^="https://www.greenmangaming.com/"]', 'utm_medium', 'Referral'],
            	// 追加杉果来源
            	['a[href^="http://www.sonkwo.cn/"],a[href^="https://www.sonkwo.cn/"]', 'rcode', 'w84nno'],
            	['iframe[src^="https://activity.sonkwo.com/card/product.html"]', 'rcode', 'w84nno'],
            	// 追加杉果hk来源
            	['a[href^="http://www.sonkwo.hk/"],a[href^="https://www.sonkwo.hk/"]', 'rcode', 'w84nno'],
            	['iframe[src^="https://activity.sonkwo.com/card/product_in.html"]', 'rcode', 'w84nno'],
            	// 追加 2Game 来源
            	['a[href^="http://2game.com"],a[href^="http://2game.hk"],a[href^="https://2game.com"],a[href^="https://2game.hk"]', 'dir', 'asc'],
            	['a[href^="http://2game.com"],a[href^="http://2game.hk"],a[href^="https://2game.com"],a[href^="https://2game.hk"]', 'order', 'position'],
            ]);
            </script>

            <!-- 注册 Service Worker 加速访问 -->
            <script>
            if ('serviceWorker' in navigator) {
              window.addEventListener('load', function() {
                navigator.serviceWorker.register('/sw.js?v=23');
              });
            }
            </script>

            <!-- 修复夜间模式下iframe白底 -->
            <style>
            iframe[src^="https://store.steampowered.com/widget/"], iframe[src^="https://steamdb.keylol.com/tooltip"] {
                color-scheme: auto;
            }
            </style>
            </div>
            <div style="margin-top:2px; margin-top:6px;">
            GMT+8, 2024-12-20 10:21</div>
            </div>
            </div>

            <!-- 百度统计 -->
            <script>
            var _hmt = _hmt || [];
            _hmt.push(['_setUserId', window.discuz_uid]);
            (function() {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?8cf683f51eb4795c5d09446e920329c3";
            var s = document.getElementsByTagName("script")[0]; 
            s.parentNode.insertBefore(hm, s);
            })();
            </script>

            <!-- Google 统计 -->
            <script src="https://www.googletagmanager.com/gtag/js?id=UA-17880378-3" type="text/javascript"></script>
            <script>
            window.dataLayer = window.dataLayer || [];
            function gtag(){dataLayer.push(arguments);}
            gtag('js', new Date());
            gtag('config', 'UA-17880378-3', { 'user_id': window.discuz_uid });
            </script>
            </div>
            <script src="home.php?mod=spacecp&ac=pm&op=checknewpm&rand=1734661297" type="text/javascript"></script>
            <script src="home.php?mod=misc&ac=sendmail&rand=1734661297" type="text/javascript"></script>
            <div class="focus plugin" id="ip_notice"></div>
            <script type="text/javascript">ipNotice();</script>

            <div id="scrolltop">
            <span hidefocus="true"><a title="返回顶部" onclick="window.scrollTo('0','0')" class="scrolltopa" ><b>返回顶部</b></a></span>
            </div>
            <script type="text/javascript">_attachEvent(window, 'scroll', function () { showTopLink(); });checkBlind();</script>
            </body>
            </html>
        """.trimIndent()

        fun parseSlides(document: Document): List<Slide> {
            val slideShow =
                document.getElementsByClass("slideshow").getOrNull(0) ?: return emptyList()

            val elements = slideShow.getElementsByTag("li")
            return elements.mapNotNull { element ->
                val href = element.getElementsByTag("a").getOrNull(0) ?: return@mapNotNull null
                val tid = href.attribute("href")?.value?.substringBefore("-")?.drop(1) ?: return@mapNotNull null
                val img = href.getElementsByTag("img").getOrNull(0)?.attribute("src")?.value
                    ?: return@mapNotNull null
                val title =
                    element.getElementsByClass("title").getOrNull(0)?.text()
                        ?: return@mapNotNull null
                Slide(
                    tid = tid,
                    img = img,
                    title = title
                )
            }
        }

        fun parseThreadsList(document: Document): Map<String, List<Thread>> {
            val tab = document.getElementById("tabPAhn0P_title")
            return tab?.children()?.associate { children ->
                val id = children.attribute("id")?.value ?: return emptyMap()
                val title =
                    children.getElementsByClass("titletext").getOrNull(0)?.text()
                        ?: return emptyMap()
                val threads = children.getElementById("${id}_content")?.getElementsByTag("li")
                title to (threads?.mapNotNull { element ->
                    element.getElementsByTag("a").let {
                        var author: String? = null
                        var authorId: String? = null
                        var subject: String? = null
                        var tid: String? = null
                        var forum: String? = null
                        it.first()?.let { firstElement ->
                            author = firstElement.text()
                            authorId = firstElement.attribute("href")?.value?.substringAfter("suid-")
                        }
                        it.last()?.let { lastElement ->
                            tid = lastElement.attribute("href")?.value?.substringBefore("-")?.drop(1)
                            subject = lastElement.text()
                            forum = lastElement.attribute("title")?.value
                                ?.substringBefore("\n")
                                ?.substringAfter("板块: ")
                        }
                        Thread(
                            tid = tid ?: return@mapNotNull null,
                            author = author ?: return@mapNotNull null,
                            authorId = authorId ?: return@mapNotNull null,
                            subject = subject ?: return@mapNotNull null,
                            readPerm = "0",
                            dateline = "",
                            lastPost = "",
                            lastPoster = "",
                            views = "",
                            replies = "",
                            digest = "",
                            attachment = "",
                            dbDateline = "",
                            dbLastPost = "",
                            forum = forum
                        )
                    }
                } ?: emptyList())
            } ?: emptyMap()
        }

        val start = System.currentTimeMillis()
        val document = Ksoup.parseBodyFragment(html)
        val slides = parseSlides(document)
        val index = parseThreadsList(document)

        println("parsing time spent: ${System.currentTimeMillis() - start} milliseconds")
        prettyPrintln(slides)
        prettyPrintln(index)
    }

}