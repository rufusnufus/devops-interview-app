package co.jp.soramitsu.devopsinterviewapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/api/v2")
public class MainController {

    @Value("${application.file-path}")
    private String filePath;

    @GetMapping("/do-something")
    public @ResponseBody
    ResponseEntity<String> doSomething() {
        return ResponseEntity.ok("I'm doing something");
    }

    @GetMapping("/do-something-persistent")
    public @ResponseBody
    ResponseEntity<List<String>> doSomethingPersistent() {
        try {
            initFile();

            String current = readFile();
            writeOnFile(current + "\n" + getCurrentDate());

            List<String> accessTimes = Arrays.stream(
                            readFile().split("\n"))
                    .filter((s) -> s.length() > 0)
                    .collect(Collectors.toList());

            log.info("Successful access to " + filePath);
            return ResponseEntity.ok(accessTimes);
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean initFile() throws IOException {
        File file = new File(filePath);
        return file.createNewFile();
    }

    private String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(cal.getTime());
    }

    private void writeOnFile(String string) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        byte[] strToBytes = string.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

    private String readFile() throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
