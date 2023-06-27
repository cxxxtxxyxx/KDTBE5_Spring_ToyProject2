package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.Stadium;
import model.stadium.StadiumDao;

import java.util.List;
import java.util.Map;

@Controller
public class BaseballController {

    private final StadiumDao stadiumDao = new StadiumDao();

    @RequestMapping(uri = "야구장등록")
    public boolean addStadium(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return false;
        }


        if (paramMap.containsKey("name")) {
            return stadiumDao.add(paramMap.get("name"));
        }

        System.out.println("올바르지 않은 요청입니다.");
        return false;


    }

    @RequestMapping(uri = "야구장목록")
    public List<Stadium> stadiumList(){
        System.out.println("BaseballController.stadiumList");
        List<Stadium> all = stadiumDao.findAll();
        System.out.println("all = " + all);
        return all;
    }
}
