/* Copyright (C) 1998-2017 by Northwoods Software Corporation. All Rights Reserved. */

// When adding samples or extensions, modify this file, samples/all.html, and samples/indexList.js
// along with adding a 400x400 screenshot in assets/images/screenshots.

// Load necessary scripts:
if (window.require) {
  // declare required libraries and ensure Bootstrap's dependency on jQuery
  require.config({
    paths: {
      "highlight": "highlight"
    },
    shim: {
      "bootstrap": ["jquery"]
    }
  });
  require(["highlight"], function() {});
} else {
  function goLoadSrc(filenames) {
    var scripts = document.getElementsByTagName("script");
    var script = null;
    for (var i = 0; i < scripts.length; i++) {
      if (scripts[i].src.indexOf("goSamples") > 0) {
        script = scripts[i];
        break;
      }
    }
    for (var i = 0; i < arguments.length; i++) {
      var filename = arguments[i];
      if (!filename) continue;
      var selt = document.createElement("script");
      selt.async = false;
      selt.defer = false;
      selt.src = "../bower_components/goJs/assets/js/" + filename;
      script.parentNode.insertBefore(selt, script.nextSibling);
      script = selt;
    }
  }
  goLoadSrc("highlight.js");
  //goLoadSrc("highlight.js", (window.jQuery ? "" : "jquery.min.js"), "bootstrap.min.js");
}

//var head = document.getElementsByTagName("head")[0];
//
//var link = document.createElement("link");
//link.type = "text/css";
//link.rel = "stylesheet";
//link.href = "../bower_components/goJs/assets/css/bootstrap.min.css";
//head.appendChild(link);
//
//link = document.createElement("link");
//link.type = "text/css";
//link.rel = "stylesheet";
//link.href = "../bower_components/goJs/assets/css/highlight.css";
//head.appendChild(link);
//
//link = document.createElement("link");
//link.type = "text/css";
//link.rel = "stylesheet";
//link.href = "../bower_components/goJs/assets/css/main.css";
//head.appendChild(link);

//function goSamples() {
  // determine if it's an extension
//  var isExtension = (location.pathname.split('/').slice(-2)[0].indexOf("extensions") >= 0);
//  var isTS = (location.pathname.split('/').slice(-2)[0].indexOf("TS") > 0);

  // save the body for goViewSource() before we modify it
//  window.bodyHTML = document.body.innerHTML;
//  window.bodyHTML = window.bodyHTML.replace(/</g, "&lt;");
//  window.bodyHTML = window.bodyHTML.replace(/>/g, "&gt;");

  // look for links to API documentation and convert them
  //_traverseDOM(document);

  // wrap the sample div and sidebar in a fluid container
//  var container = document.createElement('div');
//  container.className = "container-fluid";
//  document.body.appendChild(container);

  // sample content
//  var samplediv = document.getElementById('sample') || document.body.firstChild;
//  samplediv.className = "col-md-10";
//  container.appendChild(samplediv);

//  // side navigation
//  var navindex = document.createElement('div');
//  navindex.id = "navindex";
//  navindex.className = "col-md-2";
//  navindex.innerHTML = isExtension ? myExtensionMenu : mySampleMenu;
//  container.insertBefore(navindex, samplediv);
//
//  // top navbar
//  var navbar = document.createElement('div');
//  navbar.id = "navtop";
//  navbar.innerHTML = myNavbar;
//  document.body.insertBefore(navbar, container);
//
//  // footer
//  window.hdr = document.createElement("div");  // remember for hiding in goViewSource()
//  var p = document.createElement("p");
//  p.innerHTML = "<a href='javascript:goViewSource()'>View this sample page's source in-page</a>";
//  hdr.appendChild(p);
//  var p1 = document.createElement("p");
//  var samplename = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
//  p1.innerHTML = "<a href='https://github.com/NorthwoodsSoftware/GoJS/blob/master/" +
//                 (isExtension ? "extensions" : "samples") + 
//                 (isTS ? "TS/" : "/") +
//                 samplename +
//                 "' target='_blank'>View this sample page's source on GitHub</a>";
//  hdr.appendChild(p1);
//
//  samplediv.appendChild(hdr);
//  var footer = document.createElement("div");
//  footer.className = "footer";
//  var msg = "Copyright &copy; 1998-2017 by Northwoods Software Corporation.";
//  if (go && go.version) {
//    msg = "GoJS&reg; version " + go.version + ". " + msg;
//  }
//  footer.innerHTML = msg;
//  samplediv.appendChild(footer);

  // when the page loads, change the class of navigation LI's
//  var url = window.location.href;
//  var lindex = url.lastIndexOf('/');
//  url = url.slice(lindex+1).toLowerCase();  // include "/" to avoid matching prefixes
//  var lis = document.getElementById("sections").getElementsByTagName("li");
//  var l = lis.length;
//  var listed = false;
//  for (var i = 0; i < l; i++) {
//    var anchor = lis[i].childNodes[0];
//    // ....../samples/X.html becomes X.html becomes X
//    var split = anchor.href.split('/').pop().split('.');
//    var imgname = split[0];
//    if (imgname === "index" || imgname === "all") continue;
//    var imgtype = split[1];
//    if (imgtype === "js") continue;
//    var span = document.createElement('span');
//    span.className = "samplespan";
//    var img = document.createElement('img');
//    img.height = "200";
//    img.src = "../assets/images/screenshots/" + imgname + ".png";
//    span.appendChild(img);
//    anchor.appendChild(span);
//    if (!anchor.href) continue;
//    var lowerhref = anchor.href.toLowerCase();
//    if (!listed && lowerhref.indexOf('/' + url) !== -1) {
//      anchor.className = "selected";
//      listed = true;
//    }
//  }
//  if (!listed) {
//    lis[lis.length -1].childNodes[0].className = "selected";
//  }
//
//}

// Traverse the whole document and replace <a>TYPENAME</a> with:
//    <a href="../api/symbols/TYPENAME.html">TYPENAME</a>
// and <a>TYPENAME.MEMBERNAME</a> with:
//    <a href="../api/symbols/TYPENAME.html#MEMBERNAME">TYPENAME.MEMBERNAME</a>
//function _traverseDOM(node) {
//  if (node.nodeType === 1 && node.nodeName === "A" && !node.getAttribute("href")) {
//    var text = node.innerHTML.split(".");
//    if (text.length === 1) {
//      node.setAttribute("href", "../api/symbols/" + text[0] + ".html");
//      node.setAttribute("target", "api");
//    } else if (text.length === 2) {
//      node.setAttribute("href", "../api/symbols/" + text[0] + ".html" + "#" + text[1]);
//      node.setAttribute("target", "api");
//    } else {
//    }
//  }
//  for (var i = 0; i < node.childNodes.length; i++) {
//    _traverseDOM(node.childNodes[i]);
//  }
//}

//function goViewSource() {
//  // show the code:
//  var script = document.getElementById("code");
//  if (!script) {
//    var scripts = document.getElementsByTagName("script");
//    script = scripts[scripts.length - 1];
//  }
//  var sp1 = document.createElement("pre");
//  sp1.setAttribute("class", "javascript");
//  sp1.innerHTML = script.innerHTML;
//  var samplediv = document.getElementById("sample") || document.body;
//  samplediv.appendChild(sp1);
//
//  // show the body:
//  var sp2 = document.createElement("pre");
//  sp2.innerHTML = window.bodyHTML;
//  samplediv.appendChild(sp2);
//
//  window.hdr.children[0].style.display = "none"; // hide the "View Source" link
//
//  // apply formatting
//  hljs.highlightBlock(sp1);
//  hljs.highlightBlock(sp2);
//  window.scrollBy(0,100);
//}

