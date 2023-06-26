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
        return stadiumDao.add(paramMap.get("name"));
    }

//    @RequestMapping(uri = "야구장목록")
//    public List<Stadium> stadiumList(){
////        return stadiumDao.getList();
//        return false;
//    }
}
