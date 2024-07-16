package com.study.kakaologintest.Controller;

import com.study.kakaologintest.Service.KakaoApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import jdk.jfr.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserController {

    /*
    카카오
    restapi key     -   bcf01b2b8581399bae10b8be7d4e9be1
    javascript key  -   6697724cbaa014c25458bd99a129a5c3
    redirect url    -   http://localhost:8080/login/kakao
     */
    @Autowired
    private final KakaoApi kakaoApi;

    @GetMapping("/")
    public String main(Model model){

        model.addAttribute("kakaoApiKey","bcf01b2b8581399bae10b8be7d4e9be1");
        model.addAttribute("redirectUri","http://localhost:8080/login/kakao");
        return "index.html";
    }

    @Description("회원이 소셜 로그인을 마치면 자동으로 실행되는 API입니다. 인가 코드를 이용해 토큰을 받고, 해당 토큰으로 사용자 정보를 조회합니다." +
            "사용자 정보를 이용하여 서비스에 회원가입합니다.")
    @GetMapping("/login/kakao")
    @ResponseBody
    public String kakaoLogin(@RequestParam("code") String code) {
        System.out.println(code);
        System.out.println("getToken==============");
        String accessToken = kakaoApi.getAccessToken(code);
        System.out.println("getUserInfo==============");
        Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
//        System.out.println("logout=================");
//        kakaoApi.kakaoLogout(accessToken);

        String email = (String)userInfo.get("email");
        String nickname = (String)userInfo.get("nickname");

        System.out.println("email = " + email);
        System.out.println("nickname = " + nickname);
        System.out.println("accessToken = " + accessToken);

        return "redirect:/result";
    }
}