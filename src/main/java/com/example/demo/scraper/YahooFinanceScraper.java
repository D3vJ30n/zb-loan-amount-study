package com.example.demo.scraper;

import com.example.demo.model.Company;
import com.example.demo.model.Dividend;
import com.example.demo.model.ScrapedResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper {

    private static final String STATISTICS_URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1y";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";

    private static final long START_TIME = 86400;  // 60 * 60 * 24, 1일

    public ScrapedResult scrap(String ticker) {
        var scrapResult = new ScrapedResult();
        try {
            // 회사 정보 스크래핑
            Company company = this.scrapCompanyByTicker(ticker);
            scrapResult.setCompany(company);

            // 배당금 정보 스크래핑
            List<Dividend> dividends = this.scrapDividend(company);
            scrapResult.setDividends(dividends);

        } catch (IOException e) {
            throw new RuntimeException("스크래핑 중 예외가 발생했습니다.", e);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("배당금 정보를 찾을 수 없습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("예기치 못한 오류가 발생했습니다.", e);
        }

        return scrapResult;
    }

    private Company scrapCompanyByTicker(String ticker) throws IOException {
        String url = String.format(SUMMARY_URL, ticker, ticker);

        Document document = Jsoup.connect(url)
            .userAgent("Mozilla/5.0") // 브라우저 정보를 주입
            .get();

        Element titleElement = document.select("h1").first();
        String title = titleElement.text().split("\\(")[0].trim();

        return Company.builder()
            .ticker(ticker)
            .name(title)
            .build();
    }

    private List<Dividend> scrapDividend(Company company) throws IOException {
        String url = String.format(STATISTICS_URL, company.getTicker(), START_TIME, System.currentTimeMillis() / 1000);

        Document document = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .get();

        Elements parsingDivs = document.getElementsByAttributeValue("data-test", "historical-prices");
        Element tableElement = parsingDivs.get(0);
        Element tbody = tableElement.children().get(1);

        List<Dividend> dividends = new ArrayList<>();
        for (Element e : tbody.children()) {
            String txt = e.text();
            if (!txt.endsWith("Dividend")) {
                continue;
            }

            String[] splits = txt.split(" ");
            int month = Month.strToNumber(splits[0]);
            int day = Integer.valueOf(splits[1].replace(",", ""));
            int year = Integer.valueOf(splits[2]);
            String dividend = splits[3];

            if (month < 0) {
                throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
            }

            dividends.add(new Dividend(LocalDateTime.of(year, month, day, 0, 0), dividend));
        }

        return dividends;
    }

    private enum Month {
        JAN("Jan", 1),
        FEB("Feb", 2),
        MAR("Mar", 3),
        APR("Apr", 4),
        MAY("May", 5),
        JUN("Jun", 6),
        JUL("Jul", 7),
        AUG("Aug", 8),
        SEP("Sep", 9),
        OCT("Oct", 10),
        NOV("Nov", 11),
        DEC("Dec", 12);

        private String s;
        private int number;

        Month(String s, int n) {
            this.s = s;
            this.number = n;
        }

        static int strToNumber(String s) {
            for (var m : Month.values()) {
                if (m.s.equals(s)) {
                    return m.number;
                }
            }
            return -1;
        }
    }
}