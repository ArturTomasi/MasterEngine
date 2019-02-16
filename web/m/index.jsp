<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="Content-Security-Policy" content="default-src * 'self' 'unsafe-inline' 'unsafe-eval' data: gap: content:">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui, viewport-fit=cover">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="default">
  <meta name="theme-color" content="#2196f3">
  <meta name="format-detection" content="telephone=no">
  <meta name="msapplication-tap-highlight" content="no">
  <title>My App</title>

  <link rel="stylesheet" href="lib/framework7/css/framework7.min.css">
  <!-- Path to your custom app styles-->
  <link rel="stylesheet" href="css/app.css">
</head>
<body>
  <div id="app">
    <!-- Status bar overlay for fullscreen mode-->
    <div class="statusbar"></div>
    <!-- Left panel with reveal effect when hidden -->
    <div class="panel panel-left panel-reveal">
      <div class="view view-left">
        <div class="page">
          <div class="navbar">
            <div class="navbar-inner sliding">
              <div class="title">Left Panel</div>
            </div>
          </div>
          <div class="page-content">
            <div class="block-title">Left View Navigation</div>
            <div class="list links-list">
              <ul>
                <li><a href="/left-page-1/">Left Page 1</a></li>
                <li><a href="/left-page-2/">Left Page 2</a></li>
              </ul>
            </div>
            <div class="block-title">Control Main View</div>
            <div class="list links-list">
              <ul>
                <li>
                  <a href="/about/" data-view=".view-main" class="panel-close">About</a>
                </li>
                <li>
                  <a href="/form/" data-view=".view-main" class="panel-close">Form</a>
                </li>
                <li>
                  <a href="#" data-view=".view-main" class="back">Back in history</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Right panel with cover effect -->
    <div class="panel panel-right panel-cover theme-dark">
      <div class="view">
        <div class="page">
          <div class="navbar">
            <div class="navbar-inner">
              <div class="title">Right Panel</div>
            </div>
          </div>
          <div class="page-content">
            <div class="block">Right panel content goes here</div>
          </div>
        </div>
      </div>
    </div>

    <!-- Your main view, should have "view-main" class -->
    <div class="view view-main ios-edges">
      <!-- Page, data-name contains page name which can be used in callbacks -->
      <div class="page" data-name="home">
        <!-- Top Navbar -->
        <div class="navbar">
          <div class="navbar-inner">
            <div class="left">
              <a href="#" class="link icon-only panel-open" data-panel="left">
                <i class="icon f7-icons ios-only">menu</i>
                <i class="icon material-icons md-only">menu</i>
              </a>
            </div>
            <div class="title sliding">My App</div>
            <div class="right">
              <a href="#" class="link icon-only panel-open" data-panel="right">
                <i class="icon f7-icons ios-only">menu</i>
                <i class="icon material-icons md-only">menu</i>
              </a>
            </div>
          </div>
        </div>
        <!-- Toolbar-->
        <div class="toolbar">
          <div class="toolbar-inner">
            <a href="#" class="link">Left Link</a>
            <a href="#" class="link">Right Link</a>
          </div>
        </div>
        <!-- Scrollable page content-->
        <div class="page-content">
          <div class="block block-strong">
            <p>This is an example of split view application layout, commonly used on tablets. The main approach of such kind of layout is that you can see different views at the same time.</p>

            <p>Each view may have different layout, different navbar type (dynamic, fixed or static) or without navbar.</p>

            <p>The fun thing is that you can easily control one view from another without any line of JavaScript just using "data-view" attribute on links.</p>
          </div>

          <div class="block-title">Navigation</div>
          <div class="list">
            <ul>
              <li>
                <a href="/about/" class="item-content item-link">
                  <div class="item-inner">
                    <div class="item-title">About</div>
                  </div>
                </a>
              </li>
              <li>
                <a href="/form/" class="item-content item-link">
                  <div class="item-inner">
                    <div class="item-title">Form</div>
                  </div>
                </a>
              </li>
            </ul>
          </div>

          <div class="block-title">Modals</div>
          <div class="block block-strong">
            <div class="row">
              <div class="col-50">
                <a href="#" class="button button-raised button-fill popup-open" data-popup="#my-popup">Popup</a>
              </div>
              <div class="col-50">
                <a href="#" class="button button-raised button-fill login-screen-open" data-login-screen="#my-login-screen">Login Screen</a>
              </div>
            </div>
          </div>

          <div class="block-title">Panels</div>
          <div class="block block-strong">
            <div class="row">
              <div class="col-50">
                <a href="#" class="button button-raised button-fill panel-open" data-panel="left">Left Panel</a>
              </div>
              <div class="col-50">
                <a href="#" class="button button-raised button-fill panel-open" data-panel="right">Right Panel</a>
              </div>
            </div>
          </div>

          <div class="block-title searchbar-hide-on-search">Page Loaders & Router</div>
          <div class="list links-list searchbar-hide-on-search">
            <ul>
              <li>
                <a href="/page-loader-template7/vladimir/123/about-me/1/?start=0&end=30#top">Template7 Page</a>
              </li>
              <li>
                <a href="/page-loader-component/vladimir/123/about-me/1/?start=0&end=30#top">Component Page</a>
              </li>
              <li>
                <a href="/load-something-that-doesnt-exist/">Default Route (404)</a>
              </li>
              <li>
                <a href="/request-and-load/user/123456/">Request Data & Load</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Popup -->
    <div class="popup" id="my-popup">
      <div class="view">
        <div class="page">
          <div class="navbar">
            <div class="navbar-inner">
              <div class="title">Popup</div>
              <div class="right">
                <a href="#" class="link popup-close">Close</a>
              </div>
            </div>
          </div>
          <div class="page-content">
            <div class="block">
              <p>Popup content goes here.</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Login Screen -->
    <div class="login-screen" id="my-login-screen">
      <div class="view">
        <div class="page">
          <div class="page-content login-screen-content">
            <div class="login-screen-title">Login</div>
            <div class="list">
              <ul>
                <li class="item-content item-input">
                  <div class="item-inner">
                    <div class="item-title item-label">Username</div>
                    <div class="item-input-wrap">
                      <input type="text" name="username" placeholder="Your username">
                    </div>
                  </div>
                </li>
                <li class="item-content item-input">
                  <div class="item-inner">
                    <div class="item-title item-label">Password</div>
                    <div class="item-input-wrap">
                      <input type="password" name="password" placeholder="Your password">
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="list">
              <ul>
                <li>
                  <a href="#" class="item-link list-button login-button">Sign In</a>
                </li>
              </ul>
              <div class="block-footer">Some text about login information.<br>Click "Sign In" to close Login Screen</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Cordova -->
  <!--
  <script src="cordova.js"></script>
  -->

  <!-- Framework7 library -->
  <script type="text/javascript" src="lib/framework7/js/framework7.min.js"></script>

  <!-- App routes -->
  <!--<script src="js/routes.js"></script>-->

  <!-- Your custom app scripts -->
  <script type="text/javascript" src="js/app.js"></script>
</body>
</html>