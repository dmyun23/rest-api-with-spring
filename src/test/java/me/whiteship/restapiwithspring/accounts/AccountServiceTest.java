package me.whiteship.restapiwithspring.accounts;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void findByUserName() {

        String password = "dmyun";
        String userName = "dmyun23@gmail.com";

        //Given
        Account account = Account.builder()
                .email("dmyun23@gmail.com")
                .password("dmyun")
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();

        this.accountService.saveAccount(account);

        // WHEN
        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        // THEN
        assertThat(passwordEncoder.matches(password , userDetails.getPassword())).isTrue();
    }

    //예외 처리 테스트 3가지
//    @Test(expected = UsernameNotFoundException.class)
//    public void findByUserNameFail() {
//        String name = "dmyun23@gmail.com";
//        accountService.loadUserByUsername(name)
//    }
//    @Test
//    public void findByNameFail() {
//        String name = "dmyun23@gmail.com";
//        try{
//            accountService.loadUserByUsername(name);
//        }catch(UsernameNotFoundException e) {
//            assertThat(e.getMessage().contains(name));
//        }
//    }
    @Test
    public void findByNameFail() {

        String username = "dmyun23@gmail.com";
        expectedException.expect(UsernameNotFoundException.class);
        expectedException.expectMessage(Matchers.contains(username));
        accountService.loadUserByUsername(username) ;
    }
}