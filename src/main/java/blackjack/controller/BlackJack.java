package blackjack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import blackjack.application.Config;

@Controller
public class BlackJack {

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "home.html";
    }

    @GetMapping("/blackjack")
    public String blackjack(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "blackjack.html";
    }

    @GetMapping("/placeholder")
    public String placeholder(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "placeholder.html";
    }

}
