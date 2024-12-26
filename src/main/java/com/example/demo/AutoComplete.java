package com.example.demo;

import org.apache.commons.collections4.Trie;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 자동완성 기능을 위한 Trie 자료구조 설정
 * PatriciaTrie는 문자열 검색에 최적화된 트라이 구조를 제공
 */
@Configuration
public class AutoComplete {

    /**
     * 자동완성을 위한 Trie 빈 등록
     * PatriciaTrie는 메모리 사용을 최적화한 트라이 구현체
     *
     * @return Trie<String, String> 문자열 키와 값을 저장하는 트라이
     */
    @Bean
    public Trie<String, String> trie() {
        return new PatriciaTrie<>();
    }
}