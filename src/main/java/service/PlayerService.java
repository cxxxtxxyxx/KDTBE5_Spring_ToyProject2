package service;

import model.player.PlayerDao;
import dto.PlayerFindResponseDto;
import model.player.Position;
import model.team.Team;
import model.team.TeamDao;
import util.QueryExecutionStatus;

import java.util.List;

public class PlayerService {

    private final static PlayerService playerService = new PlayerService();

    private final PlayerDao playerDao = new PlayerDao();
    private final TeamDao teamDao = new TeamDao();

    public static PlayerService getInstance(){
        return playerService;
    }

    public PlayerService() {
    }

    public Team findByTeamId(int teamId) {
        return teamDao.findById(teamId);
    }

    public List<PlayerFindResponseDto> findAllPlayer(int teamId) {
        return playerDao.findAll(teamId);
    }

    public QueryExecutionStatus addPlayer(int teamId, String name, Position position) {
        return playerDao.add(teamId, name, position);
    }
}
