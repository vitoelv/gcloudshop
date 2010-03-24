function mycarousel_initCallback(carousel)
{
    // Disable autoscrolling if the user clicks the prev or next button.
    carousel.buttonNext.bind('click', function() {
        carousel.startAuto(0);
    });
    carousel.buttonPrev.bind('click', function() {
        carousel.startAuto(0);
    });
    // Pause autoscrolling if the user moves with the cursor over the clip.
    carousel.clip.hover(function() {
        carousel.stopAuto();
    }, function() {
        carousel.startAuto();
    });
};
window.onload = function () {
            jQuery('#carousel').jcarousel({
            auto: 3,
            vertical: true,
            scroll: 2,
            wrap: 'last',
            initCallback: mycarousel_initCallback
        });
        jQuery('#cgoodsbest').jcarousel({
            // auto: 3,
            scroll: 4,
            initCallback: mycarousel_initCallback
        });
        // $("#categorytree .btnopen").click(function() {
        //           var obj = $(this);
        //           if (obj.text() == "+")
        //           {
        //             obj.text("-");
        //             obj.removeClass("btnopen").addClass("btnclose");
        //             $(this).parent().next("p").hide();
        //           }
        //           else
        //           {
        //             obj.text("+");
        //             obj.removeClass("btnclose").addClass("btnopen");
        //             $(this).parent().next("p").show();
        //           }
        //         });
}