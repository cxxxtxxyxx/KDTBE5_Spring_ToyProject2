package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.StadiumDao;

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

//    @RequestMapping(uri = "야구장목록")
//    public List<Stadium> stadiumList(){
////        return stadiumDao.getList();
//        return false;
//    }
}
