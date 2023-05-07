package shop.mtcoding.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.account.AccountRepository;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.domain.transaction.TransactionRepository;
import shop.mtcoding.bank.dto.transaction.TransactionRespDto.TransactionListRespDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    private TransactionListRespDto 입출금목록보기(Long userId, Long accountNumber, String gubun, int page) {
        Account accountPS = accountRepository.findByNumber(accountNumber).orElseThrow(
                () -> new CustomApiException("해당 계좌를 찾을 수 없습니다")
        );

        accountPS.checkOwner(userId);

        List<Transaction> transactionListPS = transactionRepository.findTransactionList(accountPS.getId(), gubun, page);
        return new TransactionListRespDto(accountPS, transactionListPS);
    }


}
