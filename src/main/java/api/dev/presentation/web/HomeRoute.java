package api.dev.presentation.web;

import java.util.Map;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeRoute {

    @GetMapping("/")
    public String index(Model model) {
        Map<String, String> res = new HashMap<>();

        res.put("status", "ok");
        res.put("message", "Welcome to Worktic.");

        model.addAttribute("res", res);

        return "home";
    }
}
