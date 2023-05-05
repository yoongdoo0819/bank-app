package shop.mtcoding.bank.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.account.AccountReqDto.AccountSaveReqDto;
import shop.mtcoding.bank.dto.account.AccountRespDto.AccountListRespDto;
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


    /**
     * findUserAccount 동일 사용자임을 확인하는 로직이 존재
    @GetMapping("/s/account/{id}")
    public ResponseEntity<?> findUserAccount(@PathVariable Long id, @AuthenticationPrincipal LoginUser loginUser) {

        // 권한처리를 해야만 하기 때문에 아래 로직이 반드시 필요
        if (id != loginUser.getUser().getId()) {
            throw new CustomForbiddenException("권한이 없습니다");
        }

        AccountListRespDto accountListRespDto = accountService.계좌목록보기_유저별(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }
     */

    /**
     * findUserAccount 동일 사용자임을 확인하는 로직이 존재하지 않음
     */
    @GetMapping("/s/account/login-user")
    public ResponseEntity<?> findUserAccount(@AuthenticationPrincipal LoginUser loginUser) {

        AccountListRespDto accountListRespDto = accountService.계좌목록보기_유저별(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>(1, "계좌목록보기_유저별 성공", accountListRespDto), HttpStatus.OK);
    }
}
