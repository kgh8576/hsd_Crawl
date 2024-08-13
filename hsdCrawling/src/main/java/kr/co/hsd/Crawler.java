package kr.co.hsd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hsd.ygy.YgyDAO;
import kr.co.hsd.ygy.YgyService;

@RestController
public class Crawler {
	
	private final YgyService ygyService;
	
	public Crawler(YgyService ygyService) {
        this.ygyService = ygyService;
    }
	
	@GetMapping("/startCrawl")
	public void startCrawl() {
		
		//점포정보조회
		List<Map<String, Object>> ygyDetailList = ygyService.selectYgyDetail("Y");
		String id = "";
		String pw = "";
		String cdPartnerOrigin = ""; //Pos번호
		String cdPartner = ""; //ERP번호
		String noCompany = ""; //사업자번호
		String lnPartner = ""; //점포명
		for (Map<String, Object> ygyDetail : ygyDetailList) {
			id = (String) ygyDetail.get("id");
			pw = (String) ygyDetail.get("pw");
			//Pos번호
			cdPartnerOrigin = (String) ygyDetail.get("CD_PARTNER_ORIGIN");
			//ERP번호
			cdPartner = (String) ygyDetail.get("CD_PARTNER");
			//사업자번호
			noCompany = (String) ygyDetail.get("NO_COMPANY");
			//점포명
			lnPartner = (String) ygyDetail.get("LN_PARTNER");
	        // WebDriver 경로 설정 (ChromeDriver 예시)
			String url = "https://ceo.yogiyo.co.kr/order-history/list";
	        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
	        
	        // WebDriver 초기화
	        WebDriver driver = new ChromeDriver();
	        driver.manage().window().setSize(new Dimension(900, 900));
//	        String[] targetStartDays = {"2024년 7월 1일 월요일"};
//	        String[] targetEndDays = {"2024년 7월 7일 일요일"};
	        List<Map<String, Object>> CompLogList = ygyService.selectYgyCompLog(cdPartnerOrigin);
	        List<String> targetStartDayList = new ArrayList<>();
	        targetStartDayList.add("2023년 1월 1일 일요일");
	        targetStartDayList.add("2023년 7월 1일 토요일");
	        targetStartDayList.add("2024년 1월 1일 월요일");
	        List<String> targetEndDayList = new ArrayList<>();
	        targetEndDayList.add("2023년 6월 30일 금요일");
	        targetEndDayList.add("2023년 12월 31일 일요일");
	        targetEndDayList.add("2024년 6월 30일 일요일");
	        
	        for (Map<String, Object> compLog : CompLogList) {
	        	int index = targetStartDayList.indexOf(compLog.get("TARGET_START_DAYS"));
	        	targetStartDayList.remove(index);
	        	targetEndDayList.remove(index);
			}
	        
	        String[] targetStartDays = targetStartDayList.toArray(new String[0]);
	        String[] targetEndDays = targetEndDayList.toArray(new String[0]);
	        
	        try {
	            // URL 접근
	            driver.get(url); 
	            WebElement username = driver.findElement(By.name("username"));
	            WebElement password = driver.findElement(By.name("password"));
	            username.sendKeys(id);
	            password.sendKeys(pw);
	            WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/form/button"));
	            login.click();
				Thread.sleep(5000);
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ESCAPE).perform();
				Thread.sleep(1000);
				By closeLocator = By.xpath("//span[contains(@class, 'fvgffZ')]");
				isElementPresent(driver, closeLocator);
				boolean isFirst = true;
	            WebElement calendar = driver.findElement(By.xpath("//div[contains(@class, 'fAhEqz')]"));
	            for (int k = 0; k < targetStartDays.length; k++) {
		            calendar.click();
		            Thread.sleep(2000);
		            //상세정보 클릭
		            driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[2]/div[1]/div[5]")).click();
		            String targetStartMonth = targetStartDays[k].split(" ")[0] + " " +targetStartDays[k].split(" ")[1];
		            String targetEndMonth = targetEndDays[k].split(" ")[0] + " " +targetEndDays[k].split(" ")[1];
		            String compStartMonth = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[1]/div[1]/h3")).getText();
		            //시작일 선택
		            while(!compStartMonth.equals(targetStartMonth)){
		            	if(isFirst) {
		            		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[1]/div[1]/h3/button[1]")).click();
		            	}else {
		            		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div[1]/h3/button[2]")).click();
		            	}
		            	compStartMonth = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[1]/div[1]/h3")).getText();
		            	Thread.sleep(500);
		            }
		            driver.findElement(By.xpath("//div[@aria-label='Choose "+targetStartDays[k]+"']")).click();
		            //종료일 선택
		            String compEndMonth = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div[1]/h3")).getText();
		            while(!compEndMonth.equals(targetEndMonth)){
		            	driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div[1]/h3/button[2]")).click();
		            	compEndMonth = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[1]/div/div[2]/div[1]/h3")).getText();
		            	Thread.sleep(500);
		            }
		            driver.findElement(By.xpath("//div[@aria-label='Choose "+targetEndDays[k]+"']")).click();
		            Thread.sleep(500);
		            driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[1]/div/div[1]/div[2]/div[2]/div/div/div[2]/div[2]/button/div")).click();
		            //조회된 데이터 유무 여부 체크
		            Thread.sleep(5000);
		            boolean orderYn = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[2]/div[1]")).getText().equals("");
		            if(!orderYn) {
		            	List<YgyDAO> orderList = new ArrayList<>();
		            	String orderCnt = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[2]/div[1]/div[1]/div/div/span[1]")).getText();
		            	int intOrdrCnt = Integer.parseInt(orderCnt.replace(",",""));
		            	Double maxPageNum = (double)intOrdrCnt/(double)10;
		            	maxPageNum = Math.ceil(maxPageNum);
		            	
		            	if(maxPageNum > 5) {
		            		//가장 마지막 페이지로 이동
		                	driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[5]/div[2]/div[2]")).click();
		            	}else {
		            		List<WebElement> pageList = driver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[5]/ul/li"));
		            		pageList.get(pageList.size()-1).click();
		            	}
		            	Thread.sleep(5000);
		            	for (int j = maxPageNum.intValue(); j > 0; j--) {
			            	//테이블 row 가져오기
			            	List<WebElement> orderRows = driver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[1]/div/div[2]/div/div/div/div[4]/table/tbody/tr"));
			            	for (int i = orderRows.size(); i > 0; i--) {
			            		WebElement orderRow = orderRows.get(i-1);
			            		List<WebElement> orderTds = orderRow.findElements(By.tagName("td"));
			            		if(!orderTds.get(1).getText().contains("취소")) {
			            			//영수증 상세정보 열기
			            			orderRow.click();
			            			Thread.sleep(1500);
			            			//영수증 상세정보 담기
			            			//상품정보담기
			            			List<WebElement> itemList = driver.findElements(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[2]/div/div/div[1]/div"));
			            			for (WebElement parentsItem : itemList) {
			            				YgyDAO order = new YgyDAO();
			            				//Pos번호
			            				order.setCdPartnerOrigin(cdPartnerOrigin);
			            				//ERP번호
			            				order.setCdPartner(cdPartner);
			            				//사업자번호
			            				order.setNoCompany(noCompany);
			            				//점포명
			            				order.setLnPartner(lnPartner);
			            				order.setNumUserdef(String.valueOf(itemList.size()));
			                			//포장/배달 구분
			                			if(driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[1]/div/h2")).getText().equals("포장")) {
			                				order.setFgPack("T");
			                				order.setlCdYserdef1("YOGIYOINTEGRATION");
			                			}else {
			                				order.setFgPack("D");
			                				if(driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[1]/div/h2")).getText().equals("가게배달")) {
			                					order.setlCdYserdef1("YOGIYOINTEGRATION");
			                				}else {
			                					order.setlCdYserdef1("YOGIYOEXPRESS");
			                				}
			                			}
			                			//주문금액
			                			order.setAmTot(driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[1]/div/li/div[2]")).getText().replace("원", "").replace(",", ""));
			                			//주문일자 ex)2023.01.02 (월) 오후 03:39:14
			                			List<WebElement> subDetailList = driver.findElements(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[2]/div/ul/li"));
			                			for (WebElement subDetail : subDetailList) {
			                				String title = subDetail.findElement(By.xpath(".//span[1]")).getText();
			                				if(title.equals("주문시각")) {
			                					String orderDate = subDetail.findElement(By.xpath(".//span[2]")).getText();
			                					String pattern = "yyyy.MM.dd (E) a hh:mm:ss";
			                					SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.KOREAN);
			                					SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("YYYYMMdd");
			                					SimpleDateFormat dateFormatYYYYMMDDHHmmss = new SimpleDateFormat("YYYYMMddHHmmss");
			                					String date = "";
			                					String time = "";
			                					try {
			                						date = dateFormatYYYYMMDD.format(dateFormat.parse(orderDate));
			                						time = dateFormatYYYYMMDDHHmmss.format(dateFormat.parse(orderDate));
			                					} catch (ParseException e) {
			                						e.printStackTrace();
			                					}
			                					order.setDtSale(date);
			                					 order.setSalesTime(time);
			                				}else if (title.equals("결제방법")) {
			                					//결제방법 /html/body/div[3]/div/div/div[2]/div/div[2]/div/ul/li[2]/span[2]
			                					String lCdYserdef1 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[2]/div/ul/li[2]/span[2]")).getText();
			                					order.setlCdYserdef1(lCdYserdef1);
											}else if (title.equals("주문번호")) {
												//주문번호 /html/body/div[3]/div/div/div[2]/div/div[2]/div/ul/li[3]/span[2]
												String lCdYserdef2 = driver.findElement(By.xpath("/html/body/div[3]/div/div/div[2]/div/div[2]/div/ul/li[3]/span[2]")).getText();
												order.setlCdYserdef2(lCdYserdef2);
			                				}
										}
			            				order.setDcRmk(parentsItem.findElement(By.xpath(".//div[1]/span[1]")).getText().replaceAll(" ","").split("x")[0]);
			            				if(order.getDcRmk().equals("배달요금")) {
			            					order.setQtSale("1");
			            				}else {
			            					order.setQtSale(parentsItem.findElement(By.xpath(".//div[1]/span[1]")).getText().replaceAll(" ","").split("x")[1]);
			            				}
			            				order.setItemTot(parentsItem.findElement(By.xpath(".//div[1]/span[2]")).getText().replaceAll("원","").replace(",", ""));
			        					//자식상품 담기
			            				if(!parentsItem.findElement(By.xpath(".//div[2]")).getText().isBlank()) {
			            					order.setiCdUserdef1("Y");
			            					orderList.add(order);
			            					List<WebElement> childItemList = parentsItem.findElement(By.xpath(".//div[2]")).findElements(By.xpath(".//div"));
			            					for (WebElement childItem : childItemList) {
			            						YgyDAO orderChild = new YgyDAO(order);
			        							orderChild.setDcRmk(childItem.findElement(By.xpath(".//span[1]")).getText().replaceAll(" ","").replace("ㄴ\n", "").split("x")[0]);
			        							orderChild.setQtSale(childItem.findElement(By.xpath(".//span[1]")).getText().replaceAll(" ","").split("x")[1]);
			        							orderChild.setItemTot(childItem.findElement(By.xpath(".//span[2]")).getText().replaceAll("원","").replace(",", ""));
			        							orderList.add(orderChild);
											}
			            				}else {
			            					order.setiCdUserdef1("N");
			            					orderList.add(order);
			            				}
									}
			            			//상세정보 닫기
			            			driver.findElement(By.className("FullScreenModal___StyledIcon-sc-7lyzl-8")).click();
			            		}
							}
			            	if(j != 1) {
			            		System.out.println(maxPageNum.intValue() + "|" + j);
			            		Thread.sleep(500);
			            		//이전 페이지
			            		WebElement selectLi = driver.findElement(By.xpath("//li[contains(@class, 'gcbfNJ')]"));
			            		selectLi.findElement(By.xpath("preceding-sibling::li[1]")).click();
			            		Thread.sleep(500);
			            	}
		            	}
		            //db입력
		            ygyService.insertSale(orderList);
		            ygyService.insertCompleteLog(cdPartnerOrigin, targetStartDays[k]);
		            ygyService.updateComplite(cdPartnerOrigin);
		            }//
		            isFirst = false;
	            }
	        }
	        catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        finally {
	            // 브라우저 닫기
	            driver.quit();
	        }
		}
    }
	
	@GetMapping("/idPwCheck")
	public void idPwCheck() {
		String id = "";
		String pw = "";
		//점포정보조회
		List<Map<String, Object>> ygyDetailList = ygyService.selectYgyDetail("N");
		for (Map<String, Object> ygyDetail : ygyDetailList) {
			id = (String) ygyDetail.get("id");
			pw = (String) ygyDetail.get("pw");
			
			String url = "https://ceo.yogiyo.co.kr/order-history/list";
	        System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver.exe");
	
	        // WebDriver 초기화
	        WebDriver driver = new ChromeDriver();
	        driver.manage().window().setSize(new Dimension(900, 900));
	        driver.get(url);
	        	        
            WebElement username = driver.findElement(By.name("username"));
            WebElement password = driver.findElement(By.name("password"));
            username.sendKeys(id);
            password.sendKeys(pw);
            WebElement login = driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[2]/form/button"));
            login.click();
            try {
				Thread.sleep(2000);
				if(driver.getCurrentUrl().equals("https://ceo.yogiyo.co.kr/order-history/list")){
					ygyService.updateYgyDetailCorrectYn(id,pw,"Y");
				}else {
					ygyService.updateYgyDetailCorrectYn(id,pw,"N");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				driver.quit();
			}
            
		}
	}
	
	public static boolean isElementPresent(WebDriver driver, By locator) {
		try {
			driver.findElement(locator).click();
			return true;
		} catch (Exception e) {
			return false;
		}
    }

}
