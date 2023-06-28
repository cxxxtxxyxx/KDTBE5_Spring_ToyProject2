package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.Stadium;
import service.StadiumService;
import util.QueryExecutionStatus;

import java.util.List;
import java.util.Map;

@Controller
public class StadiumController {

    // TODO 싱글톤 객체로 만들기
    private final StadiumService stadiumService = StadiumService.getInstance();

    @RequestMapping(uri = "야구장등록")
    public void addStadium(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return;
        }


        if (!paramMap.containsKey("name")) {
            return;
        }

        String name = paramMap.get("name");

        QueryExecutionStatus result = stadiumService.addStadium(name);
        System.out.println(result);



    }

    @RequestMapping(uri = "야구장목록")
    public List<Stadium> stadiumList(){
        return stadiumService.findAllStadium();
    }
}
