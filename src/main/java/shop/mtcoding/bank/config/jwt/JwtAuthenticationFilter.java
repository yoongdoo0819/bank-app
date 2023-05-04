package shop.mtcoding.bank.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.user.UserReqDto.LoginReqDto;
import shop.mtcoding.bank.dto.user.UserRespDto.LoginRespDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/api/login"); // url 경로 /api/login 요청에 대해 attemptAuthentication 진행
        this.authenticationManager = authenticationManager;
    }

    // Post : /api/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            LoginReqDto loginReqDto = om.readValue(request.getInputStream(), LoginReqDto.class);

            // 강제 로그인
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginReqDto.getUsername(), loginReqDto.getPassword());
            // UserServiceDetails의 loadUserByUsername 호출
            // JWT를 쓴다 하더라도, 컨트롤러 진입을 하면 시큐리티의 권한체크, 인증체크의 도움을 받을 수 있도록 세션을 생성
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e) {
            // authenticationEntryPoint에 걸림
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    // attemptAuthentication에서 return authentication이 잘 동작하면 아래 successfulAuthentication 메서드 호출 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        LoginRespDto loginRespDto = new LoginRespDto(loginUser.getUser());
        CustomResponseUtil.success(response, loginRespDto);

    }
}
