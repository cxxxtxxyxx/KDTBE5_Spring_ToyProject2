package service;

import db.DBConnection;
import model.outplayer.OutPlayerDao;
import model.outplayer.OutPlayerResponseDto;
import model.outplayer.Reason;
import model.player.PlayerDao;
import util.QueryExecutionStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private final static OutPlayerService outPlayerService = new OutPlayerService();

    private final OutPlayerDao outPlayerDao = OutPlayerDao.getInstance();
    private final PlayerDao playerDao = PlayerDao.getInstance();
    private Connection connection = DBConnection.getConnection();

    public static OutPlayerService getInstance() {
        return outPlayerService;
    }

    private OutPlayerService() {
    }

    public QueryExecutionStatus addOutPlayer(int playerId, Reason reason) {

        try {
            connection.setAutoCommit(false);

            QueryExecutionStatus updateResult = null;
            QueryExecutionStatus addResult = outPlayerDao.add(playerId, reason);

            if (addResult.equals(QueryExecutionStatus.FAIL)) {
                connection.rollback();
                return QueryExecutionStatus.FAIL;
            }

            updateResult = playerDao.updateStatus(playerId);

            if (updateResult.equals(QueryExecutionStatus.FAIL)) {
                connection.rollback();
                return QueryExecutionStatus.FAIL;
            }

            connection.commit();
            return QueryExecutionStatus.SUCCESS;
        } catch (SQLException e) {
            return QueryExecutionStatus.FAIL;
        }
    }

    public List<OutPlayerResponseDto> findAllOutPlayer() {
        return outPlayerDao.findAllJoinPlayer();
    }
}
