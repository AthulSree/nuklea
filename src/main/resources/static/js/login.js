$(document).ready( function(){
    $('.firstdigit').focus();

    $('#loginForm').on('submit', function(event){
        event.preventDefault(); // Prevent form submission
        var phoneno = "";
        $('.phonenumber').each(function(){
            // Log the value of each element with class 'phonenumber'
            phoneno += $(this).val();
        });
        console.log(phoneno);
        $("#formatted_ph").val(phoneno);
        this.submit();
    });
});


function moveToNext(current, nextFieldName) {
    if ($(current).val().length == $(current).attr('maxlength')) {
        $(`[name="${nextFieldName}"]`).focus();
    }
}


document.addEventListener("DOMContentLoaded", function () {
  const image = document.querySelector('.login_logo');

  // When the image is fully loaded, add the 'loaded' class to apply the rotation.
  image.onload = function () {
    image.classList.add('loaded');
  };
});
