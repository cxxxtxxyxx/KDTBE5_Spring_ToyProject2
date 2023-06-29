package service;

import model.stadium.Stadium;
import model.stadium.StadiumDao;
import model.team.PositionResponseDto;
import model.team.TeamDao;
import model.team.TeamResponseDto;
import util.QueryExecutionStatus;

import java.util.List;

public class TeamService {

    private final static TeamService teamService = new TeamService();

    private final StadiumDao stadiumDao = new StadiumDao();
    private final TeamDao teamDao = new TeamDao();

    public static TeamService getInstance() {
        return teamService;
    }

    public TeamService() {
    }

    public List<TeamResponseDto> findAllTeam() {
        return teamDao.findAllJoinStadium();
    }

    public Stadium findStadiumById(int stadiumId) {
        return stadiumDao.findById(stadiumId);
    }

    public QueryExecutionStatus addTeam(int stadiumId, String stadiumName) {

        if (stadiumDao.findById(stadiumId) == null) {
            return QueryExecutionStatus.FAIL;
        }

        return teamDao.add(stadiumId, stadiumName);
    }

    public List<String> findByTeamNames() {
        return teamDao.findAll();
    }

    public List<PositionResponseDto> findAllTeamByPosition(List<String> teamNames) {
        return teamDao.findAllTeamJoinPlayerByPosition(teamNames);
    }
}
