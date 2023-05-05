package shop.mtcoding.bank.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountSaveRespDto;
import shop.mtcoding.bank.service.AccountService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class AccountController {

    private final AccountService accountService;

    // uri 경로 /s/**를 들어왔다는 의미는 스프링 시큐리티를 통과했다는 뜻
    // 세션에 LoginUser (UserDetails를 상속)가 저장되어 있으며 @AuthenticationPrincipal LoginUser를 통해 사용 가능
    @PostMapping("/s/account")
    public ResponseEntity<?> saveAccount(@RequestBody AccountSaveReqDto accountSaveReqDto,
                                         BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {

        AccountSaveRespDto accountSaveRespDto = accountService.계좌등록(accountSaveReqDto, loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌등록 성공", accountSaveRespDto), HttpStatus.CREATED);
    }
}
