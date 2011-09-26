(function($) {
    jQuery.fn.pictureMenu = function(options) {
        var defaults = {
            animateTime: 350,             // The duration of the animations
            menuHeight: 150,              // The height of the menu (usually the same as the height of the images)
            menuWidth: 75,                // The length of a menu item (useMenuWidth must be true)
            menuWidthHover: 40,           // The length of the inactive menu items
            menuActiveWidth: 500,         // The length of the active menu items (useMenuActiveWidth must be true)
            menuInactiveHide: 1,          // The opacity of the inactive menu items
            menuBorder: 2,                // The Border between the menu items
            menuAutoOpen: 0,              // Menu item that should be open
            subMenu: ".submenu",          // Selector of the submenu
            fillColor: "#fff",            // Color of the menu
            useMenuWidth: true,           // If true: use custom menuWidth. If false: use calculated menuWidth
            useMenuActiveWidth: false,    // If true: use custom menuActiveWidth. If false: use calculated menuActiveWidth
            leaveActiveOpen: false,       // If true, active menu item stay's open after mouse leaves
            leaveActiveOpenCookie: false, // Use cookie to remember open menu item!
            cookieExpire: 0,              // Use cookie to remember open menu item!
            debug: false                  // Turn on or off the debugger
        };

        var o = $.extend(defaults, options);

        return this.each(function() {
            var e = $(this), menuItemsCount, menuWidth, menuItemWidth, Offset, hoverWidth, listWidth, i = 1, cookieName;

            // Use the id or class as cookie name
            if (e.attr("id")) {
                cookieName = e.attr("id");
            } else if (e.attr("class")) {
                cookieName = e.attr("class");
            } else {
                cookieName = 'banana';
            }

            // Debug some settings
            if (o.debug) {
                if (o.menuAutoOpen > 1) {
                    if (!o.leaveActiveOpen) {
                        alert(cookieName + ': "leaveActiveOpen" must be enabled when using "menuAutoOpen"');
                    }
                }
            }

            // Number of menu items
            menuItemsCount = e.children().length;
            // Length of the whole menu
            menuWidth = e.width();
            // Calculate (or use) the length of the menu items
            if (o.useMenuWidth) {
                menuItemWidth = o.menuWidth;
            } else {
                menuItemWidth = Math.round(menuWidth / menuItemsCount) - o.menuBorder;

                // Calculate the offset
                Offset = (menuItemWidth + o.menuBorder) * menuItemsCount - menuWidth;
                // In case there is an offset, reduce the menu item size
                if (Offset > 0) {
                    menuItemWidth = menuItemWidth - 1;
                }
            }
            // Calculate (or use) the length a menu item can expand
            if (o.useMenuActiveWidth) {
                hoverWidth = o.menuActiveWidth;
            } else {
                hoverWidth = menuWidth - ((menuItemsCount - 1) * (o.menuWidthHover + o.menuBorder));
            }
            // Calculate the length of the menu inside the menu item
            listWidth = hoverWidth - o.menuWidth - 32;

            // Do some magic
            e.wrap('<div class="menuWrap" />');

            e.css("display", "block")
             .css("height", o.menuHeight + "px")
             .css("width", 5000 + "px")
             .css("background", o.fillColor)
             .css("overflow", "hidden")
             .children()
             .wrap('<div class="pmItem" style="width: ' + menuItemWidth + 'px; border-right: solid ' + o.menuBorder + 'px white;" />')
             .addClass("pmItemWrap")
             .mouseenter(function() {
                 // Make the menu items small when your mouse enters the menu
                 e.find(".pmItem").stop().animate({
                     width: o.menuWidthHover,
                     opacity: o.menuInactiveHide
                 }, o.animateTime);
             })
             .mouseleave(function() {
                 // Close the menu when you leave the menu item
                 e.find(".pmItem").not(".pmActive").stop().animate({
                     width: menuItemWidth,
                     opacity: 1
                 }, o.animateTime).find(o.subMenu).fadeOut("fast");
             })
             .each(function() {
                 // Get the background image
                 var imgBackground = $(this).find("img:first-child").attr("src");

                 // Hide the image and make it a background on the menu item
                 $(this).attr("data-cookie-number", i)
                        .css("background", "url(" + imgBackground + ")")
                        .css("height", o.menuHeight + "px")
                        .find("img:first-child")
                        .hide();

                 // Hide the submenu
                 $(this).find(o.subMenu).css("max-width", listWidth + "px").hide();

                 var lookForMenu = $(this).find(o.subMenu).attr("class");
                 var makeBigLink = $(this).find("a").attr("href");
                 var makeBigLinkTarget = $(this).find("a").attr("target");
                 
                 if (!lookForMenu && makeBigLink) {
                     $(this).addClass("pmLink");
                     $(this).click(function() {
                         if (makeBigLinkTarget !== "_blank") {
                             window.location.href = makeBigLink;
                         } else {
                             window.open(makeBigLink);
                         }
                     });
                 };

                 $(this).mouseenter(function() {
                     if (o.leaveActiveOpen) {
                         if (!$(this).parent().hasClass('pmActive')) {
                             e.find(".pmActive").removeClass("pmActive").find(o.subMenu).fadeOut("fast");
                         };

                         $(this).parent().addClass("pmActive")
                     }
                     // Show the menu item when you enter it with your mouse
                     $(this).parent().stop().animate({
                         width: hoverWidth,
                         opacity: 1
                     }, o.animateTime, function() {
                         // When menu is loaded, show the sub menu
                         $(this).find(o.subMenu).fadeIn("normal");
                     });

                     if (o.leaveActiveOpenCookie) {
                         var cookieNumber = $(this).attr("data-cookie-number");

                         $.cookie(cookieName, cookieNumber, { expires: o.cookieExpire });
                     }
                 });

                 if (o.leaveActiveOpen) {

                     if (o.leaveActiveOpenCookie) {
                         var cookieNumber = $.cookie(cookieName);

                         if (!cookieNumber) {
                             cookieNumber = o.menuAutoOpen;
                         }

                         if (cookieNumber == i) {
                             $(this).trigger("mouseenter").trigger("mouseleave");
                         }
                     } else {
                         if (o.menuAutoOpen == i) {
                             $(this).trigger("mouseenter").trigger("mouseleave");
                         }
                     }
                 }

                 i++;
             });
        });
    }
})(jQuery);