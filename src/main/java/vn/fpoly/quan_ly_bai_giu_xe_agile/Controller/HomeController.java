package vn.fpoly.quan_ly_bai_giu_xe_agile.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "View/home";
    }

    @GetMapping("/trangChu")
    public String trangChu() {
        return "View/page/trangChu";
    }
    @GetMapping("/tinTuc")
    public String tinTuc() {
        return "View/page/tinTuc";
    }
    @GetMapping("/gioiThieu")
    public String gioiThieu() {
        return "View/page/gioiThieu";
    }

    @GetMapping("/lienHe")
    public String lienHe() {
        return "View/page/lienHe";
    }

}
