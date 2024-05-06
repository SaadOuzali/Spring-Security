package com.techsoft.springsecurity.controller;

import com.techsoft.springsecurity.entity.AuthRequest;
import com.techsoft.springsecurity.entity.UserInfo;
import com.techsoft.springsecurity.logout.BlackList;
import com.techsoft.springsecurity.service.JwtService;
import com.techsoft.springsecurity.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private BlackList blackList;

    @GetMapping("/welcome")
    public String welcome(){
Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("hnaaa  "+authentication ==null);
        return "Welcome to Spring Security tutorials !!";
    }

    @GetMapping("/user")
    public Authentication auth(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println("hnaaa     "+SecurityContextHolder.getContext().getAuthentication()==null);
        return authentication;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }
    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest){
        System.out.println("d5al nadi");

        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                                authRequest.getPassword()));
//        UserDetails userDetails = userInfoService.loadUserByUsername(authRequest.getUserName());
//        UsernamePasswordAuthenticationToken authToken=
//                new UsernamePasswordAuthenticationToken(userDetails,null);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
//        }else {
//            throw new UsernameNotFoundException("Invalid user request");
//        }
//        return jwtService.generateToken(authRequest.getUserName());
//        return "all is good";
    }
    @PostMapping("/logout")
//    @PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
    public String logoutUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String token= null;
        if(authHeader !=null && authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
        }
        blackList.blacKListToken(token);
        return "You have successfully logged out !!";
    }

    @GetMapping("/getUsers")
//    @PreAuthorize("hasAuthority('ADMIN_ROLES') or hasAuthority('USER_ROLES')")
    public List<UserInfo> getAllUsers(){
        System.out.println( "dabla"+SecurityContextHolder.getContext().getAuthentication()==null);
        return userInfoService.getAllUser();
    }
    @GetMapping("/getUsers/{id}")
//    @PreAuthorize("hasAuthority('USER_ROLES')")
    public UserInfo getAllUsers(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }
}
