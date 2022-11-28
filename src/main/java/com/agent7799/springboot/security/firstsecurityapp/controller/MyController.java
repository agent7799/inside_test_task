package com.agent7799.springboot.security.firstsecurityapp.controller;

import com.agent7799.springboot.security.firstsecurityapp.dto.MessageDTO;
import com.agent7799.springboot.security.firstsecurityapp.model.Message;
import com.agent7799.springboot.security.firstsecurityapp.security.JWTUtil;
import com.agent7799.springboot.security.firstsecurityapp.services.MessageService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class MyController {

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    public MyController(JWTUtil jwtUtil, ModelMapper modelMapper, MessageService messageService) {
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    @PostMapping("/message")
    @ResponseBody
    public List<String> showUserInfo(HttpServletRequest httpServletRequest, @RequestBody MessageDTO messageDDTO) throws NotFoundException {
        String authHeader = httpServletRequest.getHeader("Authorization");
        Message message = convertToMessage(messageDDTO);

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer_")) {
            String jwt = authHeader.substring(7);
            String token = jwtUtil.generateToken(message.getUsername());
            if(jwt.equals(token)){
                if(message.getMessage().equals("history 10")){
                    List<Message> historyList = messageService.loadMessageByUsername(message.getUsername());
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < Math.min(historyList.size(), 10); i++){
                        list.add(historyList.get(i).getMessage());
                    }
                    System.out.println(list);
                    return list;
                }else{
                    messageService.saveMessage(message);
                    return List.of("message saved", message.getMessage());
                }
            }
        }
        return List.of("message error");
    }

    public Message convertToMessage(MessageDTO messageDTO) {
        return this.modelMapper.map(messageDTO, Message.class);
    }


//    @RequestMapping(value = "/showMessage", method = RequestMethod.POST)
//    @ResponseBody
//    public String postMessage(@RequestBody HttpServletRequest httpServletRequest){
//        String authHeader = httpServletRequest.getHeader("Authorisation");
//        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer_")) {
//            String jwt = authHeader.substring(7);
//        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        String username = jwtUtil.generateToken(httpServletRequest.get);
//
//        System.out.println(personDetails.getPerson());
//
//        return personDetails.getUsername();
//    }



}
