$(document).ready(function(){
     $("#chat_textarea").hide();
//     var container = $(".chat-container");
//     container.scrollTop(container.prop("scrollHeight"));

    load_chatList(0);

    $(document).on('click','.chat-box',function(){
        var userid = this.dataset.userid;
        load_chatList(userid);
        var username = $(this).find('.username_chat').text();
        var dp = $(this).find('.img-cover').attr("src")
        $("#leo_name").html(username+"<br><span>Online</span>")
        $("#leo_dp").attr("src",dp)
    })

    function load_chatList(activeUser){
        var path = $("#chat_list").data('path')
        $.ajax({
            url: path,
            type: "POST",
            data:{activeUser},
            success: function(data){
                $("#chat_list").html(data)
                    if(activeUser>0){
                       $("#chat_textarea").show();
                        load_messages();
                    }
            }
        })
    }


    function load_messages(){
            var path = $("#chat-container").data('path')
            $.ajax({
                url: path,
                type: "POST",
                success: function(data){
                    $("#chat-container").html(data)

                        $('.findtext').each(function() {
                            var messageContent = $(this).text();
                            messageContent = messageContent.replace(/\n/g, '<br>');
                            $(this).html(messageContent);
                        });

                         var container = $(".chat-container");
                         container.scrollTop(container.prop("scrollHeight"));
                }
            })
    }

//    ------Send message
    $(document).on('click','#sendMessage', function(){
        var path = this.dataset.path;
        var content = $(".messageContent").val();
        if(content==""){
            return false;
        }
        $.ajax({
            url : path,
            type: "POST",
            data: {content},
            success: function(data){
                $(".messageContent").val('');
                load_messages();
                $("#messageContent").css("height","125px")
            }
        })
    })
})


        function autoResize(textarea) {
            // Reset height to calculate new height properly
            textarea.style.height = 'auto';
            // Set height based on scrollHeight
            textarea.style.height = textarea.scrollHeight + 'px';
        }
