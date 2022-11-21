package me.whiteship.restapiwithspring.configs;

import me.whiteship.restapiwithspring.accounts.Account;
import me.whiteship.restapiwithspring.accounts.AccountRole;
import me.whiteship.restapiwithspring.accounts.AccountService;
import me.whiteship.restapiwithspring.common.BaseControllerTest;
import me.whiteship.restapiwithspring.common.TestDescription;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest  {

    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰을 발급R 받는 테스트")
    public void getAuthToken() throws Exception {
        // Given
        String userName ="dmyun23@gmail.com";
        String password = "dmyun";
        Account account = Account.builder()
                .email(userName)
                .password(password)
                .build();
        accountService.saveAccount(account);

        String clientId = "myApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(clientId,clientSecret))
                        .param("username", userName)
                        .param("password", password)
                        .param("grant_type", "password"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }

}