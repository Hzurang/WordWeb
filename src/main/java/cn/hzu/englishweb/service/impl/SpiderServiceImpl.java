package cn.hzu.englishweb.service.impl;

import cn.hzu.englishweb.dao.WordDao;
import cn.hzu.englishweb.model.Listen;
import cn.hzu.englishweb.model.Word;
import cn.hzu.englishweb.service.SpiderService;

import cn.hzu.englishweb.utils.StringUtils;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SpiderServiceImpl implements SpiderService {
    @Resource
    private WordDao wordDao;
    /**
     * 获取四级数据
     * @return 单词表
     * @throws Exception
     */
    public List<Word> getDataCET4() throws Exception{
        List<Word> res = new ArrayList<>();
        List<Word> res1 = new ArrayList<>();
        res = wordDao.queryAllWord();
        System.out.println(res.size());
        System.out.println(res.get(5832).getWordEng());
        for (int i = 0; i <= 5832; i++) {
            res1.addAll(spiderDataCET4(new StringBuilder("https://www.quword.com/w/")
                    .append(res.get(i).getWordEng()).toString()));
        }
        return res1;
    }
    /**
     * 爬取四级数据
     * @param url :https://www.quword.com/w/abandon (例子)
     * @return 列表
     * @throws Exception
     */
    private List<Word> spiderDataCET4(String url)throws Exception{
        return analysisHtmlCET4(getHtmlPageCET4(url));
    }

    /**
     * 获取Html页面
     * @param url 数据来源:https://www.quword.com/w/abandon (例子)
     * @return html页面
     * @throws Exception
     */
    private String getHtmlPageCET4(String url)throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"); // 伪装搜索引擎
        httpGet.setHeader("Cookie", "UM_distinctid=17d73dbe5dec51-0fca4dbd5b19b7-978183a-144000-17d73dbe5df342; Hm_lvt_b98d5a4c985b16117a3eb5bd26322264=1638326331,1638524580,1638536523,1638699405; CNZZDATA1278225540=23526587-1638323859-%7C1638843852; Hm_lpvt_b98d5a4c985b16117a3eb5bd26322264=1638844061");
        httpGet.setHeader("Host", "www.quword.com");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);//获取网址的返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("开始了11111111");
                HttpEntity entity = response.getEntity(); //获取返回结果中的实体
                return EntityUtils.toString(entity,"utf-8");// utf-8 根据页面header提供的charset相对应
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Jsoup解析html页面，代码写死的方式
     * @param html html页面
     * @return 单词表
     * @throws Exception
     */
    private List<Word> analysisHtmlCET4(String html)throws Exception{
        List<Word> res = new ArrayList<>();
        Word word = new Word();
        String str = null;
        Document document = Jsoup.parse(html); // 解析html
        Element element =document.getElementsByTag("body").first(); // <body></body>
        Elements elements = element.getElementsByClass("container"); // class定位
        Element iNeed = elements.get(2); // 想要的数据在第二个container
        Elements realData = iNeed.getElementsByClass("col-sm-8"); // 找到 一页最多共15个单词
        Element col = realData.first();

        Elements row1 = col.getElementsByClass("row");  // 单词英文
        Elements data = row1.get(0).getElementsByClass("col-md-7");
        Element col2 = data.first();

        Elements row_r = col2.getElementsByClass("row");
        Element id = col2.getElementById("yd-word-meaning");

        Element eng = row_r.get(0);
        Element eng1 = eng.getElementById("yd-word");
        word.setWordEng(eng1.text());
        Element EN = row_r.get(1);   //单词的读音
        word.setWordEN(EN.text());
        Elements Ch = id.getElementsByTag("ul"); // 单词的中文意思
        System.out.println(Ch);
        if (Ch != null) {
            str = Ch.text();
            word.setWordChi(str);
            System.out.println("1111111111  "+str);
        }

        Element sj = col.getElementById("yd-liju");    // 单词例句
        if (sj != null) {
            Elements dl = sj.getElementsByTag("dl");
            System.out.println(sj);
            Element gg = dl.first();
            Elements bb = gg.getElementsByTag("dt");
            Elements dd = gg.getElementsByTag("dd");
            word.setWordSentence("<p>"+bb.text()+"</p><p>"+dd.text()+"</p>");
        }
        word.setWordPhotoPath("https://www.quword.com/images/words/" + eng1.text() + "1.jpg");
        word.setWordEnPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=1");
        word.setWordUkPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=2");
        word.setWordStyle(0);

        res.add(word);
        return res;
    }

    /**
     * 获取数据
     * @return 单词表
     * @throws Exception
     */
    public List<Word> getWord() throws Exception{
        List<Word> res = new ArrayList<>();
        final int pageNum = 226;
        for (int i = 0; i <= pageNum; i++)
            res.addAll(spiderWord(new StringBuilder("https://www.quword.com/ciku/id_7_0_0_0_")
                    .append(i).append(".html").toString()));
        return res;
    }
    /**
     * 爬取数据
     * @param url :https://www.quword.com/ciku/id_1_0_2_0_0.html -
     *          https://www.quword.com/ciku/id_1_0_2_0_93.html
     * @return 列表
     * @throws Exception
     */
    private List<Word> spiderWord(String url)throws Exception{
        return analysisHtml(getHtmlPage(url));
    }

    /**
     * 获取Html页面
     * @param url 数据来源:https://www.quword.com/ciku/id_1_0_2_0_0.html
     * @return html页面
     * @throws Exception
     */
    private String getHtmlPage(String url)throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","spider"); // 伪装搜索引擎
        CloseableHttpResponse response=httpClient.execute(httpGet);//获取网址的返回结果
        HttpEntity entity = response.getEntity(); //获取返回结果中的实体
        return EntityUtils.toString(entity,"utf-8");// utf-8 根据页面header提供的charset相对应
    }

    /**
     * Jsoup解析html页面
     * @param html
     * @return
     * @throws Exception
     */
    private List<Word> analysisHtml(String html)throws Exception{
        List<Word> res = new ArrayList<>();
        Document document = Jsoup.parse(html); // 解析html
        Element element =document.getElementsByTag("body").first(); // <body></body>
        Elements elements = element.getElementsByClass("container"); // class定位
        Element iNeed = elements.get(1); // 想要的数据在第二个container
        Elements rows = iNeed.getElementsByClass("row"); // 单词数据所在
        Element row = rows.first(); // 发现row只有一个
        Elements realData = row.getElementsByClass("col-sm-6"); // 找到 一页最多共15个单词
        // 批处理
        realData.forEach(e-> {
            Word word = new Word();
            e = e.getElementsByClass("thumbnail").first();
            Element wordAndSemantic = e.getElementsByClass("caption").first();
            word.setWordEng(wordAndSemantic.getElementsByTag("h3").first()
                    .getElementsByTag("a").first().text());
            res.add(word);
        });
        return res;
    }


    /**
     * 获取六级数据
     * @return 单词表
     * @throws Exception
     */
    public List<Word> getDataCET6() throws Exception{
        List<Word> res = new ArrayList<>();
        List<Word> res1 = new ArrayList<>();
        res = wordDao.queryAllWord();
        System.out.println(res.size());
        System.out.println(res.get(5833).getWordEng());
        for (int i = 5833; i <= 13861; i++) {
            res1.addAll(spiderDataCET6(new StringBuilder("https://www.quword.com/w/")
                    .append(res.get(i).getWordEng()).toString()));
        }
        return res1;
    }
    /**
     * 爬取六级数据
     * @param url :https://www.quword.com/w/abandon (例子)
     * @return 列表
     * @throws Exception
     */
    private List<Word> spiderDataCET6(String url)throws Exception{
        return analysisHtmlCET6(getHtmlPageCET6(url));
    }

    /**
     * 获取Html页面
     * @param url 数据来源:https://www.quword.com/w/abandon (例子)
     * @return html页面
     * @throws Exception
     */
    private String getHtmlPageCET6(String url)throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"); // 伪装搜索引擎
        httpGet.setHeader("Cookie", "UM_distinctid=17d73dbe5dec51-0fca4dbd5b19b7-978183a-144000-17d73dbe5df342; Hm_lvt_b98d5a4c985b16117a3eb5bd26322264=1638326331,1638524580,1638536523,1638699405; CNZZDATA1278225540=23526587-1638323859-%7C1638865462; Hm_lpvt_b98d5a4c985b16117a3eb5bd26322264=1638874815");
        httpGet.setHeader("Host", "www.quword.com");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);//获取网址的返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("开始了11111111");
                HttpEntity entity = response.getEntity(); //获取返回结果中的实体
                return EntityUtils.toString(entity,"utf-8");// utf-8 根据页面header提供的charset相对应
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Jsoup解析html页面，代码写死的方式
     * @param html html页面
     * @return 单词表
     * @throws Exception
     */
    private List<Word> analysisHtmlCET6(String html)throws Exception{
        List<Word> res = new ArrayList<>();
        Word word = new Word();
        String str = null;
        Document document = Jsoup.parse(html); // 解析html
        Element element =document.getElementsByTag("body").first(); // <body></body>
        Elements elements = element.getElementsByClass("container"); // class定位
        Element iNeed = elements.get(2); // 想要的数据在第二个container
        Elements realData = iNeed.getElementsByClass("col-sm-8"); // 找到 一页最多共15个单词
        Element col = realData.first();

        Elements row1 = col.getElementsByClass("row");  // 单词英文
        Elements data = row1.get(0).getElementsByClass("col-md-7");
        Element col2 = data.first();

        Elements row_r = col2.getElementsByClass("row");
        Element id = col2.getElementById("yd-word-meaning");

        Element eng = row_r.get(0);
        Element eng1 = eng.getElementById("yd-word");
        word.setWordEng(eng1.text());
        Element EN = row_r.get(1);   //单词的读音
        word.setWordEN(EN.text());
        Elements Ch = id.getElementsByTag("ul"); // 单词的中文意思
        System.out.println(Ch);
        if (Ch != null) {
            str = Ch.text();
            word.setWordChi(str);
            System.out.println("1111111111  "+str);
        }

        Element sj = col.getElementById("yd-liju");    // 单词例句
        if (sj != null) {
            Elements dl = sj.getElementsByTag("dl");
            System.out.println(sj);
            Element gg = dl.first();
            Elements bb = gg.getElementsByTag("dt");
            Elements dd = gg.getElementsByTag("dd");
            word.setWordSentence("<p>"+bb.text()+"</p><p>"+dd.text()+"</p>");
        }
        word.setWordPhotoPath("https://www.quword.com/images/words/" + eng1.text() + "1.jpg");
        word.setWordEnPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=1");
        word.setWordUkPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=2");
        word.setWordStyle(1);

        res.add(word);
        return res;
    }


    /**
     * 获取托福单词数据
     * @return 单词表
     * @throws Exception
     */
    public List<Word> getDataTF() throws Exception{
        List<Word> res = new ArrayList<>();
        List<Word> res1 = new ArrayList<>();
        res = wordDao.queryAllWord();
        System.out.println(res.size());
        System.out.println(res.get(18722).getWordEng());
        System.out.println(res.get(18728).getWordEng());
        for (int i = 18722; i <= 18728; i++) {
            res1.addAll(spiderDataTF(new StringBuilder("https://www.quword.com/w/")
                    .append(res.get(i).getWordEng()).toString()));
        }
        return res1;
    }
    /**
     * 爬取托福单词数据
     * @param url :https://www.quword.com/w/abandon (例子)
     * @return 列表
     * @throws Exception
     */
    private List<Word> spiderDataTF(String url)throws Exception{
        return analysisHtmlTF(getHtmlPageTF(url));
    }

    /**
     * 获取Html页面
     * @param url 数据来源:https://www.quword.com/w/abandon (例子)
     * @return html页面
     * @throws Exception
     */
    private String getHtmlPageTF(String url)throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36"); // 伪装搜索引擎
        httpGet.setHeader("Cookie", "UM_distinctid=17d73dbe5dec51-0fca4dbd5b19b7-978183a-144000-17d73dbe5df342; Hm_lvt_b98d5a4c985b16117a3eb5bd26322264=1638326331,1638524580,1638536523,1638699405; CNZZDATA1278225540=23526587-1638323859-%7C1638843852; Hm_lpvt_b98d5a4c985b16117a3eb5bd26322264=1638844061");
        httpGet.setHeader("Host", "www.quword.com");

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);//获取网址的返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("开始了11111111");
                HttpEntity entity = response.getEntity(); //获取返回结果中的实体
                return EntityUtils.toString(entity,"utf-8");// utf-8 根据页面header提供的charset相对应
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Jsoup解析html页面，代码写死的方式
     * @param html html页面
     * @return 单词表
     * @throws Exception
     */
    private List<Word> analysisHtmlTF(String html)throws Exception{
        List<Word> res = new ArrayList<>();
        Word word = new Word();
        String str = null;
        Document document = Jsoup.parse(html); // 解析html
        Element element =document.getElementsByTag("body").first(); // <body></body>
        Elements elements = element.getElementsByClass("container"); // class定位
        Element iNeed = elements.get(2); // 想要的数据在第二个container
        Elements realData = iNeed.getElementsByClass("col-sm-8"); // 找到 一页最多共15个单词
        Element col = realData.first();

        Elements row1 = col.getElementsByClass("row");  // 单词英文
        Elements data = row1.get(0).getElementsByClass("col-md-7");
        Element col2 = data.first();

        Elements row_r = col2.getElementsByClass("row");
        Element id = col2.getElementById("yd-word-meaning");

        Element eng = row_r.get(0);
        Element eng1 = eng.getElementById("yd-word");
        word.setWordEng(eng1.text());
        Element EN = row_r.get(1);   //单词的读音
        word.setWordEN(EN.text());
        Elements Ch = id.getElementsByTag("ul"); // 单词的中文意思
        System.out.println(Ch);
        if (Ch != null) {
            str = Ch.text();
            word.setWordChi(str);
            System.out.println("1111111111  "+str);
        }

        Element sj = col.getElementById("yd-liju");    // 单词例句
        if (sj != null) {
            Elements dl = sj.getElementsByTag("dl");
            System.out.println(sj);
            Element gg = dl.first();
            Elements bb = gg.getElementsByTag("dt");
            Elements dd = gg.getElementsByTag("dd");
            word.setWordSentence("<p>"+bb.text()+"</p><p>"+dd.text()+"</p>");
        }
        word.setWordPhotoPath("https://www.quword.com/images/words/" + eng1.text() + "1.jpg");
        word.setWordEnPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=1");
        word.setWordUkPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=2");
        word.setWordStyle(2);

        res.add(word);
        return res;
    }


    /**
     * 获取雅思单词数据
     * @return 单词表
     * @throws Exception
     */
    public List<Word> getDataYS() throws Exception{
        List<Word> res = new ArrayList<>();
        List<Word> res1 = new ArrayList<>();
        res = wordDao.queryAllWord();
        System.out.println(res.size());
        System.out.println(res.get(18729).getWordEng());
        System.out.println(res.get(23251).getWordEng());
        for (int i = 18729; i <= 23251; i++) {
            res1.addAll(spiderDataYS(new StringBuilder("https://www.quword.com/w/")
                    .append(res.get(i).getWordEng()).toString()));
        }
        return res1;
    }
    /**
     * 爬取雅思单词数据
     * @param url :https://www.quword.com/w/abandon (例子)
     * @return 列表
     * @throws Exception
     */
    private List<Word> spiderDataYS(String url)throws Exception{
        return analysisHtmlYS(getHtmlPageYS(url));
    }

    /**
     * 获取Html页面
     * @param url 数据来源:https://www.quword.com/w/abandon (例子)
     * @return html页面
     * @throws Exception
     */
    private String getHtmlPageYS(String url)throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Mobile Safari/537.36 Edg/96.0.1054.43"); // 伪装搜索引擎

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);//获取网址的返回结果
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("开始了11111111");
                HttpEntity entity = response.getEntity(); //获取返回结果中的实体
                return EntityUtils.toString(entity,"utf-8");// utf-8 根据页面header提供的charset相对应
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * Jsoup解析html页面，代码写死的方式
     * @param html html页面
     * @return 单词表
     * @throws Exception
     */
    private List<Word> analysisHtmlYS(String html)throws Exception{
        List<Word> res = new ArrayList<>();
        Word word = new Word();
        String str = null;
        Document document = Jsoup.parse(html); // 解析html
        Element element =document.getElementsByTag("body").first(); // <body></body>
        Elements elements = element.getElementsByClass("container"); // class定位
        Element iNeed = elements.get(2); // 想要的数据在第二个container
        Elements realData = iNeed.getElementsByClass("col-sm-8"); // 找到 一页最多共15个单词
        Element col = realData.first();

        Elements row1 = col.getElementsByClass("row");  // 单词英文
        Elements data = row1.get(0).getElementsByClass("col-md-7");
        Element col2 = data.first();

        Elements row_r = col2.getElementsByClass("row");
        Element id = col2.getElementById("yd-word-meaning");

        Element eng = row_r.get(0);
        Element eng1 = eng.getElementById("yd-word");
        word.setWordEng(eng1.text());
        Element EN = row_r.get(1);   //单词的读音
        word.setWordEN(EN.text());
        Elements Ch = id.getElementsByTag("ul"); // 单词的中文意思
        System.out.println(Ch);
        if (Ch != null) {
            str = Ch.text();
            word.setWordChi(str);
            System.out.println("1111111111  "+str);
        }

        Element sj = col.getElementById("yd-liju");    // 单词例句
        if (sj != null) {
            Elements dl = sj.getElementsByTag("dl");
            System.out.println(sj);
            Element gg = dl.first();
            Elements bb = gg.getElementsByTag("dt");
            Elements dd = gg.getElementsByTag("dd");
            word.setWordSentence("<p>"+bb.text()+"</p><p>"+dd.text()+"</p>");
        }
        word.setWordPhotoPath("https://www.quword.com/images/words/" + eng1.text() + "1.jpg");
        word.setWordEnPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=1");
        word.setWordUkPath("https://dict.youdao.com/dictvoice?audio=" + eng1.text() + "&type=2");
        word.setWordStyle(0);

        res.add(word);
        return res;
    }

    /**
     * 获取html
     * @param url 爬取的网页链接
     * @return 爬取网页的html信息
     * @throws Exception 抛出异常
     */
    public static String getHtmlPageListen(String url) throws Exception{
        CloseableHttpClient httpClient= HttpClients.createDefault();//建立一个新的请求客户端
        HttpGet httpGet = new HttpGet(url);//使用HttpGet的方式请求网址
        httpGet.setHeader("User-agent","spider"); // 伪装搜索引擎
        CloseableHttpResponse response=httpClient.execute(httpGet);//获取网址的返回结果
        HttpEntity entity = response.getEntity();//获取返回结果中的实体
        return EntityUtils.toString(entity,"utf-8");//设置字符集utf-8
    }

    /**
     * 爬取沪江英语听力美文
     * @param url  https://www.hjenglish.com/new/ting/c1040/
     */
    public List<Listen> spider(String url) throws Exception{
        String page = "";
        List<Listen> listenList = new ArrayList<>();
        for (int i = 2; i <= 20; i++) {
            getDataWhere(getHtmlPageListen(url+page)).forEach(
                    listen -> {
                        try {
                            analysisSampleHtml(getHtmlPageListen(listen.getPageUrl()),listen);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listenList.add(listen);
                    }
            );
            page="page"+i;
        }
        return listenList;
    }

    public static List<Listen> getDataWhere(String html) throws ParseException {
        Document document = Jsoup.parse(html);//解析
        Element element = document.getElementsByTag("body").first();//body
        element = element.getElementsByClass("pane").get(2)
                .getElementsByClass("row").first()
                .getElementsByClass("col").first()
                .getElementsByClass("module").first();//他这个是静态页面直接这样无所谓的 除非他想花大代价全修改
        Elements elements = element.getElementsByClass("article-item");//一页几项内容 都是同样的
        List<Listen> listens = new ArrayList<>();
        for (Element e:elements) {
            Listen listen = new Listen();
            listen.setDescription(e.getElementsByClass("article-content").text());
            listen.setListenName(e.getElementsByTag("a").get(1).attr("title"));
            listen.setPageUrl("https://www.hjenglish.com"
                    +e.getElementsByTag("a").get(1).attr("href"));
            Elements element1 = e.getElementsByClass("time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            String dateStr = element1.text();
            Date date = simpleDateFormat.parse(dateStr);
            listen.setCreateTime(date);
            Element element2 = e.getElementsByClass("article-tags").get(0);
            String str1 = element2.getElementsByTag("a").get(0).attr("title");
            listen.setGradeLis(str1);
            listens.add(listen);
        }
        return listens;
    }

    public static void analysisSampleHtml(String html, Listen listen)throws Exception{
        Document document = Jsoup.parse(html);//解析
        Element element = document.getElementsByTag("body").first();//body
        element=element.getElementsByClass("pane").get(5)
                .getElementsByClass("row").first()
                .getElementsByClass("col").first()
                .getElementsByClass("module").get(1)
                .getElementsByClass("module").first()
                .getElementsByClass("article-body").first()
                .getElementsByClass("module").first()
                .getElementsByClass("article-main").first()
                .getElementsByClass("article-content").first();//他这个是静态页面直接这样无所谓的 除非他想花大代价全修改
        String ss = element.getElementsByTag("script").get(1).html();
        Integer dd = StringUtils.getIndexStr1("https", ss);
        System.out.println(dd);
        Integer gg = StringUtils.getIndexStr1(".mp3", ss);
        String audio = ss.substring(dd, gg+4);
        listen.setListenPath(audio);
        StringBuilder content=new StringBuilder("<p><p>");
        Elements langs_en = element.getElementsByClass("langs_en");
        Elements langs_cn = element.getElementsByClass("langs_cn");
        for (int i = 0; i < langs_cn.size(); i++)
            content.append(langs_cn.get(i)).append(langs_en.get(i));
        listen.setContent(content.toString());
    }
}

