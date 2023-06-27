package controller;

import core.Controller;
import core.RequestMapping;
import model.player.PlayerDao;
import model.player.Position;
import model.team.Team;
import model.team.TeamDao;

import java.util.Map;

@Controller
public class PlayerController {
    private final PlayerDao playerDao = new PlayerDao();
    private final TeamDao teamDao = new TeamDao();

    @RequestMapping(uri = "선수등록")
    public boolean addPlayer(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return false;
        }

        Team team = teamDao.findById(Integer.parseInt(paramMap.get("team_id")));

        if (team == null) {
            return false;
        }

        if (paramMap.containsKey("team_id") && paramMap.containsKey("name") && paramMap.containsKey("position")) {
            Position position = Position.findByName(paramMap.get("position"));
            if (position == null) {
                return false;
            }
            return playerDao.add(Integer.parseInt(paramMap.get("team_id")), paramMap.get("name"), position);
        }

        System.out.println("올바르지 않은 요청입니다.");
        return false;
    }

}
