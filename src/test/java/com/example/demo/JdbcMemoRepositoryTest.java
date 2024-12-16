package com.example.demo;

import com.example.demo.domain.Memo;
import com.example.demo.repository.JdbcMemoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
//@Transactional // 각 테스트 후 롤백
public class JdbcMemoRepositoryTest {

    @Autowired
    private JdbcMemoRepository jdbcMemoRepository;

    @Test
    void deleteMemoTest() {

        //given 주어진
        Memo memo = new Memo(null, "insertMemoTest");

        //when 실행
        Memo savedMemo = jdbcMemoRepository.save(memo);

        //then 검증
        List<Memo> memos = jdbcMemoRepository.findAll();
    }
}