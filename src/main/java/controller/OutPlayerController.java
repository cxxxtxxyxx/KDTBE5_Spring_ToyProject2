package controller;

import core.Controller;
import core.RequestMapping;
import db.DBConnection;
import model.outplayer.OutPlayer;
import model.outplayer.OutPlayerDao;
import model.outplayer.OutPlayerResponseDto;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class OutPlayerController {
    private final OutPlayerDao outPlayerDao = new OutPlayerDao();
    private final PlayerDao playerDao = new PlayerDao();

    private Connection connection = DBConnection.getConnection();


    /**
     * 1. 선수 퇴출 목록
     *
     * > 요청 : 퇴출목록
     * ```

     * ```
     * > 아래는 player 테이블과 out_player 테이블이 join 된 결과입니다.
     * 퇴출 된 선수는 옆에 표시가 되고, 퇴출 되지 않은 선수는 표시되지 않습니다. 이것은 OuterJoin에 대해서 이해
     * 하고 있어야 풀 수 있는 문제입니다.
     * 해당 문제는 스칼라 서브쿼리로도 해결할 수 있습니다.
     * >
     */
    @RequestMapping(uri = "퇴출목록")
    public void findAll() {
        List<OutPlayerResponseDto> joinPlayerResult = outPlayerDao.findAllJoinPlayer();
        for (OutPlayerResponseDto outPlayerResponseDto : joinPlayerResult) {
            System.out.println("outPlayerResponseDto = " + outPlayerResponseDto);
        }
    }
    
    @RequestMapping(uri = "퇴출등록")
    public boolean addOutPlayer(Map<String, String> paramMap) throws SQLException {
        connection.setAutoCommit(false);
        boolean isUpdateSuccess = false;

        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return false;
        }

        if (paramMap.containsKey("player_id") && paramMap.containsKey("reason")) {
            boolean isSuccessInsert = outPlayerDao.add(Integer.parseInt(paramMap.get("player_id")), paramMap.get("reason"));

            if (isSuccessInsert) {
                isUpdateSuccess = playerDao.updateStatus(Integer.parseInt(paramMap.get("player_id")));
            } else {
                connection.rollback();
            }
        }

        if (isUpdateSuccess) {
            connection.commit();
            return true;
        }

        connection.rollback();

        System.out.println("올바르지 않은 요청입니다.");
        return false;
    }
}
