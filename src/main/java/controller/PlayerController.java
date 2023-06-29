package controller;

import core.Controller;
import core.RequestMapping;
import model.player.PlayerFindResponseDto;
import model.player.Position;
import model.team.Team;
import service.PlayerService;
import util.QueryExecutionStatus;

import java.util.List;
import java.util.Map;

@Controller
public class PlayerController {

    private static final PlayerController playerController = new PlayerController();

    private final PlayerService playerService = PlayerService.getInstance();

    public static PlayerController getInstance() {
        return playerController;
    }

    private PlayerController() {
    }

    @RequestMapping(uri = "선수등록")
    public void addPlayer(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return;
        }

        int teamId;
        try {
            teamId = Integer.parseInt(paramMap.get("teamId"));
        } catch (NumberFormatException e) {
            System.out.println("teamId는 숫자로 입력해 주세요.");
            return;
        }
        Team team = playerService.findByTeamId(teamId);

        if (team == null) {
            System.out.println("존재하지 않는 팀입니다.");
            return;
        }

        if (!paramMap.containsKey("teamId") || !paramMap.containsKey("name") || !paramMap.containsKey("position")) {
            System.out.println("올바르지 않은 쿼리파라미터 입니다.");
        }

        Position position = Position.findByName(paramMap.get("position"));
        String name = paramMap.get("name");

        if (position == null) {
            System.out.println("존재하지 않는 포지션입니다.");
            return;
        }

        QueryExecutionStatus result = playerService.addPlayer(teamId, name, position);
        System.out.println(result);
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

        int teamId;
        try {
            teamId = Integer.parseInt(paramMap.get("teamId"));
        } catch (NumberFormatException e) {
            System.out.println("teamId는 숫자로 입력해 주세요.");
            return;
        }
        List<PlayerFindResponseDto> result = playerService.findAllPlayer(teamId);

        System.out.println(result);
    }
}
