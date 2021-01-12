package Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import BlackJack.Config;

@Controller
public class BlackJack {


    @GetMapping("/home")
    public String home(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "home.html";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/blackjack")
    public String blackjack(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "blackjack.html";
    }

    @GetMapping("/test")
    public String test(Model model) {

        if (Config.getInstance().getTheme() == Config.Theme.LIGHT) {
            model.addAttribute("isLightTheme", "light");
        }

        return "test.html";
    }




}
