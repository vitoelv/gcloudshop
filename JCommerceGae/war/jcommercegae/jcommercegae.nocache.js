function jcommercegae(){
  var $intern_0 = '', $intern_27 = '" for "gwt:onLoadErrorFn"', $intern_25 = '" for "gwt:onPropertyErrorFn"', $intern_10 = '"><\/script>', $intern_12 = '#', $intern_34 = '&', $intern_14 = '/', $intern_60 = '<script defer="defer">jcommercegae.onInjectionDone(\'jcommercegae\')<\/script>', $intern_9 = '<script id="', $intern_22 = '=', $intern_13 = '?', $intern_24 = 'Bad handler "', $intern_59 = 'DOMContentLoaded', $intern_51 = "GWT module 'jcommercegae' needs to be (re)compiled, please run a compile or use the Compile/Browse button in hosted mode", $intern_11 = 'SCRIPT', $intern_38 = 'Unexpected exception in locale detection, using default: ', $intern_37 = '_', $intern_35 = '__gwt_Locale', $intern_8 = '__gwt_marker_jcommercegae', $intern_15 = 'base', $intern_4 = 'begin', $intern_3 = 'bootstrap', $intern_17 = 'clear.cache.gif', $intern_21 = 'content', $intern_36 = 'default', $intern_7 = 'end', $intern_45 = 'gecko', $intern_46 = 'gecko1_8', $intern_5 = 'gwt.hybrid', $intern_52 = 'gwt/standard/standard.css', $intern_26 = 'gwt:onLoadErrorFn', $intern_23 = 'gwt:onPropertyErrorFn', $intern_20 = 'gwt:property', $intern_57 = 'head', $intern_49 = 'hosted.html?jcommercegae', $intern_56 = 'href', $intern_58 = 'iShop.css', $intern_44 = 'ie6', $intern_28 = 'iframe', $intern_16 = 'img', $intern_29 = "javascript:''", $intern_1 = 'jcommercegae', $intern_53 = 'link', $intern_48 = 'loadExternalRefs', $intern_32 = 'locale', $intern_33 = 'locale=', $intern_18 = 'meta', $intern_31 = 'moduleRequested', $intern_6 = 'moduleStartup', $intern_43 = 'msie', $intern_19 = 'name', $intern_40 = 'opera', $intern_30 = 'position:absolute;width:0;height:0;border:none', $intern_54 = 'rel', $intern_42 = 'safari', $intern_50 = 'selectingPermutation', $intern_2 = 'startup', $intern_55 = 'stylesheet', $intern_47 = 'unknown', $intern_39 = 'user.agent', $intern_41 = 'webkit';
  var $wnd = window, $doc = document, $stats = $wnd.__gwtStatsEvent?function(a){
    return $wnd.__gwtStatsEvent(a);
  }
  :null, scriptsDone, loadDone, bodyDone, base = $intern_0, metaProps = {}, values = [], providers = [], answers = [], onLoadErrorFunc, propertyErrorFunc;
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_4});
  if (!$wnd.__gwt_stylesLoaded) {
    $wnd.__gwt_stylesLoaded = {};
  }
  if (!$wnd.__gwt_scriptsLoaded) {
    $wnd.__gwt_scriptsLoaded = {};
  }
  function isHostedMode(){
    var result = false;
    try {
      result = $wnd.external && ($wnd.external.gwtOnLoad && $wnd.location.search.indexOf($intern_5) == -1);
    }
     catch (e) {
    }
    isHostedMode = function(){
      return result;
    }
    ;
    return result;
  }

  function maybeStartModule(){
    if (scriptsDone && loadDone) {
      var iframe = $doc.getElementById($intern_1);
      var frameWnd = iframe.contentWindow;
      if (isHostedMode()) {
        frameWnd.__gwt_getProperty = function(name){
          return computePropValue(name);
        }
        ;
      }
      jcommercegae = null;
      frameWnd.gwtOnLoad(onLoadErrorFunc, $intern_1, base);
      $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_6, millis:(new Date()).getTime(), type:$intern_7});
    }
  }

  function computeScriptBase(){
    var thisScript, markerId = $intern_8, markerScript;
    $doc.write($intern_9 + markerId + $intern_10);
    markerScript = $doc.getElementById(markerId);
    thisScript = markerScript && markerScript.previousSibling;
    while (thisScript && thisScript.tagName != $intern_11) {
      thisScript = thisScript.previousSibling;
    }
    function getDirectoryOfFile(path){
      var hashIndex = path.lastIndexOf($intern_12);
      if (hashIndex == -1) {
        hashIndex = path.length;
      }
      var queryIndex = path.indexOf($intern_13);
      if (queryIndex == -1) {
        queryIndex = path.length;
      }
      var slashIndex = path.lastIndexOf($intern_14, Math.min(queryIndex, hashIndex));
      return slashIndex >= 0?path.substring(0, slashIndex + 1):$intern_0;
    }

    ;
    if (thisScript && thisScript.src) {
      base = getDirectoryOfFile(thisScript.src);
    }
    if (base == $intern_0) {
      var baseElements = $doc.getElementsByTagName($intern_15);
      if (baseElements.length > 0) {
        base = baseElements[baseElements.length - 1].href;
      }
       else {
        base = getDirectoryOfFile($doc.location.href);
      }
    }
     else if (base.match(/^\w+:\/\//)) {
    }
     else {
      var img = $doc.createElement($intern_16);
      img.src = base + $intern_17;
      base = getDirectoryOfFile(img.src);
    }
    if (markerScript) {
      markerScript.parentNode.removeChild(markerScript);
    }
  }

  function processMetas(){
    var metas = document.getElementsByTagName($intern_18);
    for (var i = 0, n = metas.length; i < n; ++i) {
      var meta = metas[i], name = meta.getAttribute($intern_19), content;
      if (name) {
        if (name == $intern_20) {
          content = meta.getAttribute($intern_21);
          if (content) {
            var value, eq = content.indexOf($intern_22);
            if (eq >= 0) {
              name = content.substring(0, eq);
              value = content.substring(eq + 1);
            }
             else {
              name = content;
              value = $intern_0;
            }
            metaProps[name] = value;
          }
        }
         else if (name == $intern_23) {
          content = meta.getAttribute($intern_21);
          if (content) {
            try {
              propertyErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_24 + content + $intern_25);
            }
          }
        }
         else if (name == $intern_26) {
          content = meta.getAttribute($intern_21);
          if (content) {
            try {
              onLoadErrorFunc = eval(content);
            }
             catch (e) {
              alert($intern_24 + content + $intern_27);
            }
          }
        }
      }
    }
  }

  function __gwt_isKnownPropertyValue(propName, propValue){
    return propValue in values[propName];
  }

  function __gwt_getMetaProperty(name){
    var value = metaProps[name];
    return value == null?null:value;
  }

  function computePropValue(propName){
    var value = providers[propName](), allowedValuesMap = values[propName];
    if (value in allowedValuesMap) {
      return value;
    }
    var allowedValuesList = [];
    for (var k in allowedValuesMap) {
      allowedValuesList[allowedValuesMap[k]] = k;
    }
    if (propertyErrorFunc) {
      propertyErrorFunc(propName, allowedValuesList, value);
    }
    throw null;
  }

  var frameInjected;
  function maybeInjectFrame(){
    if (!frameInjected) {
      frameInjected = true;
      var iframe = $doc.createElement($intern_28);
      iframe.src = $intern_29;
      iframe.id = $intern_1;
      iframe.style.cssText = $intern_30;
      iframe.tabIndex = -1;
      $doc.body.appendChild(iframe);
      $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_6, millis:(new Date()).getTime(), type:$intern_31});
      iframe.contentWindow.location.replace(base + strongName);
    }
  }

  providers[$intern_32] = function(){
    try {
      var locale;
      if (locale == null) {
        var args = location.search;
        var startLang = args.indexOf($intern_33);
        if (startLang >= 0) {
          var language = args.substring(startLang);
          var begin = language.indexOf($intern_22) + 1;
          var end = language.indexOf($intern_34);
          if (end == -1) {
            end = language.length;
          }
          locale = language.substring(begin, end);
        }
      }
      if (locale == null) {
        locale = __gwt_getMetaProperty($intern_32);
      }
      if (locale == null) {
        locale = $wnd[$intern_35];
      }
      if (locale == null) {
        return $intern_36;
      }
      while (!__gwt_isKnownPropertyValue($intern_32, locale)) {
        var lastIndex = locale.lastIndexOf($intern_37);
        if (lastIndex == -1) {
          locale = $intern_36;
          break;
        }
         else {
          locale = locale.substring(0, lastIndex);
        }
      }
      return locale;
    }
     catch (e) {
      alert($intern_38 + e);
      return $intern_36;
    }
  }
  ;
  values[$intern_32] = {'default':0, en:1, zh:2};
  providers[$intern_39] = function(){
    var ua = navigator.userAgent.toLowerCase();
    var makeVersion = function(result){
      return parseInt(result[1]) * 1000 + parseInt(result[2]);
    }
    ;
    if (ua.indexOf($intern_40) != -1) {
      return $intern_40;
    }
     else if (ua.indexOf($intern_41) != -1) {
      return $intern_42;
    }
     else if (ua.indexOf($intern_43) != -1) {
      var result = /msie ([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 6000) {
          return $intern_44;
        }
      }
    }
     else if (ua.indexOf($intern_45) != -1) {
      var result = /rv:([0-9]+)\.([0-9]+)/.exec(ua);
      if (result && result.length == 3) {
        if (makeVersion(result) >= 1008)
          return $intern_46;
      }
      return $intern_45;
    }
    return $intern_47;
  }
  ;
  values[$intern_39] = {gecko:0, gecko1_8:1, ie6:2, opera:3, safari:4};
  jcommercegae.onScriptLoad = function(){
    if (frameInjected) {
      loadDone = true;
      maybeStartModule();
    }
  }
  ;
  jcommercegae.onInjectionDone = function(){
    scriptsDone = true;
    $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_48, millis:(new Date()).getTime(), type:$intern_7});
    maybeStartModule();
  }
  ;
  computeScriptBase();
  var strongName;
  if (isHostedMode()) {
    if ($wnd.external.initModule && $wnd.external.initModule($intern_1)) {
      $wnd.location.reload();
      return;
    }
    strongName = $intern_49;
  }
  processMetas();
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_50});
  if (!strongName) {
    try {
      alert($intern_51);
      return;
    }
     catch (e) {
      return;
    }
  }
  var onBodyDoneTimerId;
  function onBodyDone(){
    if (!bodyDone) {
      bodyDone = true;
      if (!__gwt_stylesLoaded[$intern_52]) {
        var l = $doc.createElement($intern_53);
        __gwt_stylesLoaded[$intern_52] = l;
        l.setAttribute($intern_54, $intern_55);
        l.setAttribute($intern_56, base + $intern_52);
        $doc.getElementsByTagName($intern_57)[0].appendChild(l);
      }
      if (!__gwt_stylesLoaded[$intern_58]) {
        var l = $doc.createElement($intern_53);
        __gwt_stylesLoaded[$intern_58] = l;
        l.setAttribute($intern_54, $intern_55);
        l.setAttribute($intern_56, base + $intern_58);
        $doc.getElementsByTagName($intern_57)[0].appendChild(l);
      }
      maybeStartModule();
      if ($doc.removeEventListener) {
        $doc.removeEventListener($intern_59, onBodyDone, false);
      }
      if (onBodyDoneTimerId) {
        clearInterval(onBodyDoneTimerId);
      }
    }
  }

  if ($doc.addEventListener) {
    $doc.addEventListener($intern_59, function(){
      maybeInjectFrame();
      onBodyDone();
    }
    , false);
  }
  var onBodyDoneTimerId = setInterval(function(){
    if (/loaded|complete/.test($doc.readyState)) {
      maybeInjectFrame();
      onBodyDone();
    }
  }
  , 50);
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_3, millis:(new Date()).getTime(), type:$intern_7});
  $stats && $stats({moduleName:$intern_1, subSystem:$intern_2, evtGroup:$intern_48, millis:(new Date()).getTime(), type:$intern_4});
  $doc.write($intern_60);
}

jcommercegae();
