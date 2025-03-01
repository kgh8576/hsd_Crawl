package kr.co.hsd.operation.management;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.bonigarcia.wdm.WebDriverManager;

@RestController
public class ScreenshotCapture {
	
	@GetMapping("/ScreenshotCapture")
	public ResponseEntity<?> ScreenshotCapture(@RequestParam String url, @RequestParam String key) {
		// 키값 검증
        if (!"a8B3d9Zx2P4kQ".equals(key)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("403 Forbidden: Key값 오류");
        }
		// WebDriverManager를 사용하여 ChromeDriver 설정 (호환 문제 자동 해결)
        WebDriverManager.chromedriver().setup();
        
        // Chrome 옵션 설정
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // 헤드리스 모드로 실행 (브라우저 창이 보이지 않음)
        options.addArguments("--force-device-scale-factor=0.8");
        // WebDriver 인스턴스 생성
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(800, 1000));
        Path destination = null;
        
        try {
            // 캡처할 URL로 이동
            driver.get(url);

            // 스크린샷 찍기
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // 파일 저장 경로 설정
            destination = Paths.get("./src/main/resources/naverPlace.png");
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("Screenshot saved to " + destination.toAbsolutePath());
            
            // 파일을 InputStreamResource로 변환하여 응답으로 반환
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(destination));
            
            // 파일 다운로드를 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=screenshot.png");
            headers.setContentType(MediaType.IMAGE_PNG);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(Files.size(destination))
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            System.err.println("Error saving screenshot: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        } finally {
            // WebDriver 종료
            driver.quit();
            
            // 파일 삭제
            if (destination != null) {
                try {
                    Files.delete(destination);
                    System.out.println("Temporary screenshot file deleted.");
                } catch (IOException e) {
                    System.err.println("Failed to delete temporary screenshot file: " + e.getMessage());
                }
            }
        }
    }
}
