import com.offcn.po.Sp;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CatShopPic {
    public static void main(String[] args) {
        for (int i = 1; i < 33; i++) {


            String url = "https://www.168.com/gallery-1149--1-0-" + i + "--grid-g.html";
            String html = catUrl(url);
            List<Sp> list = getSp(html);
            for (Sp sp : list) {
                // System.out.println("name:"+sp.getName()+" price:"+sp.getPrice()+" pic:"+sp.getPic()+" url:"+sp.getSpUrl());
                down(sp.getPic());

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //1、通过httpclient获取指定url地址的网页内容
    public static String catUrl(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity, "utf-8");
                return s;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    //2、解析html源码，获取商品信息（名称、价格、配图、详情地址）

    public static List<Sp> getSp(String html) {
        ArrayList<Sp> list = new ArrayList<Sp>();
        Document document = Jsoup.parse(html, "utf-8");

        Elements div = document.select("#gallery-grid-list");

        Elements lis = div.select(".items-gallery");
        for (Element e : lis) {
            Element a = e.select(".entry-title").first();
            String name = a.text();
            String spUrl = "https://www.168.com" + a.attr("href");
            //获取商品价格
            Element eprice = e.select(".sell-price").first();
            Double price = Double.parseDouble(eprice.text().substring(1));
            //商品图片
            Element img = e.select("img").first();
            String pic = img.attr("lazyload");

            Sp sp = new Sp(name, price, pic, spUrl);
            list.add(sp);
        }

        return list;
    }

    //3、下载图片
    public static void down(String picUrl) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(picUrl);

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                //获取输入流
                InputStream inputStream = entity.getContent();
                Random random = new Random();
                int code = random.nextInt(1000000);
                //创建输出流
                FileOutputStream outputStream = new FileOutputStream("d:\\chart\\pic\\" + code + ".jpg");
                System.out.println("下载图片:" + code + ".jpg");
                int len = -1;
                byte[] buf = new byte[2048];

                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }

                outputStream.close();
                inputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
