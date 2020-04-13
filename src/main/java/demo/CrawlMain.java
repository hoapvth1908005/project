package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class CrawlMain {
    public static void main(String[] args) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        try {
            ArrayList<String> listLink = new ArrayList<String>();
            System.out.println("Bat dau lay tin tu Vnexpress");
            Document document = Jsoup.connect("https://vnexpress.net/").get();
            Elements aelements = document.select("a");
            if (aelements.size() == 0) {
                return;
            }
            for (int i = 0; i < aelements.size(); i++) {
                Element item = aelements.get(i);
                String Link = item.attr("href");
                if (Link.contains(".html") && !Link.contains("#box_comment")) {
                    listLink.add(Link);
                }
            }
            ArrayList<CrawlThread> listThread = new ArrayList<CrawlThread>();
            System.out.println("Tong Cong Lay Duoc: " + listLink.size() + " link");
            for (int i = 0; i < listLink.size(); i++) {
                String url = listLink.get(i);
                CrawlThread crawl = new CrawlThread(url);
                listThread.add(crawl);
                crawl.start();
            }
            for (CrawlThread crawl : listThread) {
                crawl.join();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("Thoi gian thuc thi: " + (endTime - startTime) + " (mls)");

    }
}
