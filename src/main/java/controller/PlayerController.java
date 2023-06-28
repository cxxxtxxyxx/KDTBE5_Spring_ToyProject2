package controller;

import core.Controller;
import core.RequestMapping;
import model.player.Player;
import model.player.PlayerDao;
import model.player.PlayerFindResponseDto;
import model.player.Position;
import model.team.Team;
import model.team.TeamDao;

import java.util.List;
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

    @RequestMapping(uri = "선수목록")
    public void findAll(Map<String, String> paramMap) {
        if (paramMap == null) {
            System.out.println("PlayerController.findAll, 쿼리 파라미터를 입력해주세요");
            return;
        }

        if (!paramMap.containsKey("teamId")) {
            System.out.println("PlayerController.findAll, teamId 쿼리 파라미터를 입력해주세요");
            return;
        }

        // TODO Try Catch로 에러 처리
        int teamId = Integer.parseInt(paramMap.get("teamId"));

        List<PlayerFindResponseDto> result = playerService.findAllPlayer(teamId);

        System.out.println(result);
    }

}
