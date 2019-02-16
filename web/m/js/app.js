var app = new Framework7(
{
  // App root element
  root: '#app',
  // App Name
  name: 'My App',
  
  theme : 'auto',
  
  // App id
  id: 'com.eng.mobile',
  
   methods: {
    helloWorld: function () {
      app.dialog.alert('Hello World!');
        },
      },
  // Enable swipe panel
  panel: {
    swipe: 'left',
    leftBreakpoint: 960
  },
  // Add default routes
  routes: [
    {
      path: '/about/',
      url: 'about.html',
    },
  ],
  
  lazyModulesPath: '../m/lib/framework7/components',
  // ... other parameters
} );


app.loadModules(
[ 
'actions',
'accordion',
'autocomplete',
'calendar',
'card',
'checkbox',
'chip',
'contacts-list',
'data-table',
'dialog',
'elevation',
'fab',
'form',
'gauge',
'grid',
'infinite-scroll',
'input',
'lazy',
'list-index',
'login-screen',
'menu',
'messagebar',
'messages',
'notification',
'panel',
'photo-browser',
'picker',
'popover',
'popup',
'preloader',
'progressbar',
'pull-to-refresh',
'radio',
'range',
'searchbar',
'sheet',
'skeleton',
'smart-select',
'sortable',
'stepper',
'swipeout',
'swiper',
'tabs',
'timeline',
'toast',
'toggle',
'tooltip',
'typography',
'vi',
'virtual-list' 
] );

// Init/Create left panel view
var leftView = app.views.create('.view-left', {
  url: '/'
});

// Init/Create main view
var mainView = app.views.create('.view-main', {
  url: '/'
});

