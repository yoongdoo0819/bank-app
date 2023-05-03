package shop.mtcoding.bank.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.service.UserService.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void 회원가입_test() throws Exception {


        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("ssar");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("ssar@nate.com");
        joinReqDto.setFullname("쌀");

        // stub 1
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        //when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        // stub 2
        User ssar = User.builder()
                .id(1L)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .fullname("쌀")
                .role(UserEnum.CUSTOMER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(userRepository.save(any())).thenReturn(ssar);

        // when
        JoinRespDto joinRespDto = userService.회원가입(joinReqDto);
        System.out.println("joinResDto = " + joinRespDto);

        // then
        assertThat(joinRespDto.getId()).isEqualTo(1L);
        assertThat(joinRespDto.getUsername()).isEqualTo("ssar");

    }
}