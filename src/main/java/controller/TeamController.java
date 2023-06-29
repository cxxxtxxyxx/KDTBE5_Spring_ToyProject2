package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.Stadium;
import dto.PositionResponseDto;
import dto.TeamResponseDto;
import service.TeamService;
import util.ErrorMessage;
import util.QueryExecutionStatus;

import java.util.List;
import java.util.Map;

@Controller
public class TeamController {

    private final TeamService teamService = TeamService.getInstance();

    private static final TeamController teamController = new TeamController();

    public static TeamController getInstance(){
        return teamController;
    }

    private TeamController() {
    }

    @RequestMapping(uri = "팀등록")
    public void addTeam(Map<String, String> paramMap) {

        if (paramMap == null) {
            System.out.println(ErrorMessage.NOT_FOUND_PARAMETER);
            return;
        }

        if (!(paramMap.containsKey("stadiumId") && paramMap.containsKey("name"))) {
            System.out.println(ErrorMessage.INVALID_PARAMETER);
            return;
        }

        int stadiumId;
        try {
            stadiumId = Integer.parseInt(paramMap.get("stadiumId"));
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessage.INVALID_NUMBER_FORMAT);
            return;
        }

        if (stadiumId < 1) {
            System.out.println(ErrorMessage.INVALID_RANGE);
            return;
        }

        String stadiumName = paramMap.get("name");

        Stadium findStadium = teamService.findStadiumById(stadiumId);

        if (findStadium == null) {
            System.out.println(ErrorMessage.BAD_REQUEST);
            return;
        }

        QueryExecutionStatus result = teamService.addTeam(stadiumId, stadiumName);
        System.out.println(result);
    }

    @RequestMapping(uri = "팀목록")
    public void findAll() {
        List<TeamResponseDto> result = teamService.findAllTeam();
        for (TeamResponseDto teamResponseDto : result) {
            System.out.println("teamResponseDto = " + teamResponseDto);
        }
    }

    @RequestMapping(uri = "포지션별목록")
    public void findListByPosition(){

        List<String> teamNames = teamService.findByTeamNames();

        if (teamNames.isEmpty()) {
            System.out.println(ErrorMessage.BAD_REQUEST);
            return;
        }

        List<PositionResponseDto> result = teamService.findAllTeamByPosition(teamNames);

        for (PositionResponseDto positionResponseDto : result) {
            System.out.println("positionResponseDto = " + positionResponseDto);
        }
    }
}
