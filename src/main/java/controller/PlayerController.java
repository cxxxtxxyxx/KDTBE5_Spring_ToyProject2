package controller;

import core.Controller;
import core.RequestMapping;
import model.player.PlayerFindResponseDto;
import model.player.Position;
import model.team.Team;
import service.PlayerService;
import util.ErrorMessage;
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
            System.out.println(ErrorMessage.NOT_FOUND_PARAMETER);
            return;
        }

        int teamId;
        try {
            teamId = Integer.parseInt(paramMap.get("teamId"));
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessage.INVALID_NUMBER_FORMAT);
            return;
        }
        Team team = playerService.findByTeamId(teamId);

        if (team == null) {
            System.out.println(ErrorMessage.BAD_REQUEST);
            return;
        }

        if (!paramMap.containsKey("teamId") || !paramMap.containsKey("name") || !paramMap.containsKey("position")) {
            System.out.println(ErrorMessage.NOT_FOUND_PARAMETER);
        }

        Position position = Position.findByName(paramMap.get("position"));
        String name = paramMap.get("name");

        if (position == null) {
            System.out.println(ErrorMessage.BAD_REQUEST);
            return;
        }

        QueryExecutionStatus result = playerService.addPlayer(teamId, name, position);
        System.out.println(result);
    }

    @RequestMapping(uri = "선수목록")
    public void findAll(Map<String, String> paramMap) {
        if (paramMap == null) {
            System.out.println(ErrorMessage.NOT_FOUND_PARAMETER);
            return;
        }

        if (!paramMap.containsKey("teamId")) {
            System.out.println(ErrorMessage.INVALID_PARAMETER);
            return;
        }

        int teamId;
        try {
            teamId = Integer.parseInt(paramMap.get("teamId"));
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessage.INVALID_NUMBER_FORMAT);
            return;
        }
        List<PlayerFindResponseDto> result = playerService.findAllPlayer(teamId);

        System.out.println(result);
    }
}
