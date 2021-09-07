package co.jp.soramitsu.devopsinterviewapp;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MainController {

    @GetMapping("/do-something")
    public @ResponseBody
    ResponseEntity<String> doSomething() {
        return ResponseEntity.ok("I'm doing something");
    }
}
