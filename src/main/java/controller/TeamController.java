package controller;

import core.Controller;
import core.RequestMapping;
import model.stadium.Stadium;
import model.stadium.StadiumDao;
import model.team.PositionResponseDto;
import model.team.TeamDao;
import model.team.TeamResponseDto;
import service.TeamService;
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
            System.out.println("올바르지 않은 요청입니다.");
            return;
        }

        if (!(paramMap.containsKey("stadiumId") && paramMap.containsKey("name"))) {
            return;
        }

        // TODO try catch
        int stadiumId = Integer.parseInt(paramMap.get("stadiumId"));
        String stadiumName = paramMap.get("name");

        Stadium findStadium = teamService.findStadiumById(stadiumId);

        if (findStadium == null) {
            return;
        }

        QueryExecutionStatus result = teamService.addTeam(stadiumId, stadiumName);
        System.out.println();
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

        if (teamNames.isEmpty())
            return;

        List<PositionResponseDto> result = teamService.findAllTeamByPosition(teamNames);

        for (PositionResponseDto positionResponseDto : result) {
            System.out.println("positionResponseDto = " + positionResponseDto);
        }
    }
}
