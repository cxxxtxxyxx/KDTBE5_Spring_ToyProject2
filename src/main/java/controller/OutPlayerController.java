package controller;

import core.Controller;
import core.RequestMapping;
import model.outplayer.OutPlayerResponseDto;
import model.outplayer.Reason;
import service.OutPlayerService;
import util.QueryExecutionStatus;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class OutPlayerController {

    private static final OutPlayerController outplayerController = new OutPlayerController();

    private final OutPlayerService outPlayerService = OutPlayerService.getInstance();

    public static OutPlayerController getInstance() {
        return outplayerController;
    }

    private OutPlayerController() {
    }

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
        List<OutPlayerResponseDto> joinPlayerResult = outPlayerService.findAllOutPlayer();
        
        // 데이터 출력
        for (OutPlayerResponseDto outPlayerResponseDto : joinPlayerResult) {
            System.out.println("outPlayerResponseDto = " + outPlayerResponseDto);
        }
    }

    // TODO sout 에러 메시지를 상수로 분리하기
    @RequestMapping(uri = "퇴출등록")
    public void addOutPlayer(Map<String, String> paramMap) throws SQLException {
        if (paramMap == null) {
            System.out.println("올바르지 않은 요청입니다.");
            return;
        }

        if (!(paramMap.containsKey("playerId") && paramMap.containsKey("reason"))) {
            System.out.println("쿼리 파라미터를 제대로 입력해주세요");
            return;
        }

        int playerId;
        try {
            playerId = Integer.parseInt(paramMap.get("playerId"));
        } catch (NumberFormatException e) {
            System.out.println("playerId는 숫자로 입력해 주세요.");
            return;
        }
        Reason reason = Reason.findByName(paramMap.get("reason"));

        QueryExecutionStatus result = outPlayerService.addOutPlayer(playerId, reason);
        System.out.println(result.toString());


    }
}
