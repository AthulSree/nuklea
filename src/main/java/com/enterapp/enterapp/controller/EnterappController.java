package com.enterapp.enterapp.controller;

import com.enterapp.enterapp.entity.ChatDetails;
import com.enterapp.enterapp.entity.MessageMaster;
import com.enterapp.enterapp.entity.User;
import com.enterapp.enterapp.repository.ChatDetailsRepository;
import com.enterapp.enterapp.repository.MessageMasterRepository;
import com.enterapp.enterapp.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class EnterappController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageMasterRepository messageMasterRepository;
    @Autowired
    private ChatDetailsRepository chatDetailsRepository;



    @Value("${file.upload-dir}")
    private String uploadDir;



    @GetMapping("/")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/adminoski")
    public String adminoski(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("propic_path",uploadDir);
//        System.out.println(users.get(0).getName());
        return "adminoski";
    }

    @GetMapping("index_chat")
    public String chat_index(HttpSession session, Model model){
        int logged_id = (int) session.getAttribute("logged_id");
        String logged_uname = (String) session.getAttribute("logged_uname");

        System.out.println(logged_id+logged_uname);
        Optional<User> logged_user = userRepository.findUserById((long) logged_id);
        System.out.println(logged_user.get().getName());

        model.addAttribute("logged_user",logged_user.get());

        return "index_chat";
    }

    @PostMapping("chat_list")
    public String chat_list(Model model, @RequestParam("activeUser") int activeUser, HttpSession session){
        session.setAttribute("activeUser",activeUser);
        int logged_id = (int) session.getAttribute("logged_id");
        List<User> nukleaUsers = userRepository.findUserOtherthanLoggedin(logged_id);
        System.out.println(activeUser);
//        User loggedUser = userRepository.findById((long) logged_id).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<ChatDetails> lastChatData = chatDetailsRepository.fetchLastChat((long) logged_id);

//        System.out.println("------->"+chatData.get(0).getId());

        model.addAttribute("nukleaUsers",nukleaUsers);
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("lastMsg",lastChatData);

        return "chat_list";
    }

    @PostMapping("send_message")
    public ResponseEntity<Map<String, Object>> sendMessage(@RequestParam("content") String content, HttpSession session){
        MessageMaster msg = new MessageMaster();
        msg.setType('T');
        msg.setContent(content);
        MessageMaster rowInserted  = messageMasterRepository.save(msg);

        int senderRaw = (int) session.getAttribute("logged_id");
        System.out.println(senderRaw);
        int receiverRaw = (int) session.getAttribute("activeUser");
        System.out.println(receiverRaw);

        Optional<User> sender = userRepository.findById((long) senderRaw);
        Optional<User> activeUser = userRepository.findById((long) receiverRaw);

        if(sender.isPresent() && activeUser.isPresent()) {
            ChatDetails chat_det = new ChatDetails();
            chat_det.setDeleted('N');
            chat_det.setDelivered('N');
            chat_det.setSeen('N');
            chat_det.setSender(sender.get());
            chat_det.setReceiver(activeUser.get());
            chat_det.setMsg_id(rowInserted);
            chatDetailsRepository.save(chat_det);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Message sent successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/load_chats")
    public String loadChats(HttpSession session, Model model){
        int activeUser = (int) session.getAttribute("activeUser");
        int logged_id = (int) session.getAttribute("logged_id");
    System.out.println(">>>>>>");
    System.out.println(activeUser);
    System.out.println(logged_id);
        User loadedUser = userRepository.findById((long) activeUser).orElseThrow(() -> new RuntimeException("Sender not found"));
        User loggedUser = userRepository.findById((long) logged_id).orElseThrow(() -> new RuntimeException("Receiver not found"));
        List<ChatDetails> chatData = chatDetailsRepository.fetchChats(loadedUser, loggedUser);
        System.out.println(chatData.size());
        // Iterate through the chatData list and replace newline characters
//        for (ChatDetails chatDetail : chatData) {
//            String content = chatDetail.getMsg_id().getContent();
//            content = content.replaceAll("\n", " <br> ");
//            chatDetail.getMsg_id().setContent(content);
//        }

        model.addAttribute("chatData",chatData);
        model.addAttribute("loggedId", logged_id);
        return "message_list";
    }



    @PostMapping("/onboard_nuclues")
    public String onboardNucleus(@RequestParam("user_name") String user_name,
                                 @RequestParam("phone_number") long phone_number,
                                 @RequestParam("profile_pic") MultipartFile profile_pic) throws IOException{
        User user = new User();
        user.setName(user_name);
        user.setPhone(phone_number);

        if(!profile_pic.isEmpty()){
            File uploadDirFile = new File(uploadDir);
            if(!uploadDirFile.exists()){
                uploadDirFile.mkdirs();
            }
            String filename = profile_pic.getOriginalFilename();
            File destinationFile = new File(uploadDir+filename);
            profile_pic.transferTo(destinationFile);
            user.setProfilePic(filename);
        }
        userRepository.save(user);
        return "redirect:/adminoski";
    }

    @PostMapping("/signup")
    public String signup(HttpSession session, @RequestParam("formatted_ph") String formatted_ph) throws IOException {
        User user = new User();
        Optional<User> user_enrolled = userRepository.findByPhone(Long.valueOf(formatted_ph));
            if(user_enrolled.isPresent()) {
                int logged_id = user_enrolled.get().getId();
                String logged_uname = user_enrolled.get().getName();

                session.setAttribute("logged_id",logged_id);
                session.setAttribute("logged_uname",logged_uname);

                System.out.println(user_enrolled.get().getName()+"Logged in");
                return "redirect:/index_chat";
            }else{
                System.out.println("Aareda nee?");
                return "redirect:/";
            }
    }


}
