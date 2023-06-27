package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.Stadium;
import model.stadium.StadiumDao;
import model.team.TeamDao;

import java.util.Map;

@Controller
public class TeamController {
    private final StadiumDao stadiumDao = new StadiumDao();
    private final TeamDao teamDao = new TeamDao();

    @RequestMapping(uri = "팀등록")
    public boolean addTeam(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return false;
        }

        Stadium stadium = stadiumDao.findById(Integer.parseInt(paramMap.get("stadium_id")));

        if (stadium == null){
            return false;
        }

        if (paramMap.containsKey("stadium_id") || paramMap.containsKey("name")) {
            return teamDao.add(Integer.parseInt(paramMap.get("stadium_id")), paramMap.get("name"));
        }

        System.out.println("올바르지 않은 요청입니다.");
        return false;
    }
}
