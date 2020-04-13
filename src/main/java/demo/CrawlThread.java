package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlThread extends Thread {
    private String url;

    public CrawlThread(String url){
        this.url = url;
    }

    @Override
    public void run() {
        try {
            System.out.println("Lay tin tu url:"+url);
            Document document = Jsoup.connect(url).get();
            String title = document.select("h1.title_news_detail.mb10").text();
            String description = document.select("p.description").text();
            String content = document.select("article.content_detail").text();
            Article article = new Article(url);
            article.setSource("Vnexpress");
            article.setContent(content);
            article.setDescription(description);
            article.setTitle(title);
            System.out.println("Lay duoc tin voi tieu de:"+article.getTitle());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
