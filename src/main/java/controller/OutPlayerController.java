package controller;

import core.Controller;
import core.RequestMapping;
import dto.OutPlayerResponseDto;
import model.outplayer.Reason;
import service.OutPlayerService;
import util.ErrorMessage;
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

    @RequestMapping(uri = "퇴출목록")
    public void findAll() {
        List<OutPlayerResponseDto> joinPlayerResult = outPlayerService.findAllOutPlayer();

        for (OutPlayerResponseDto outPlayerResponseDto : joinPlayerResult) {
            System.out.println("outPlayerResponseDto = " + outPlayerResponseDto);
        }
    }

    // TODO sout 에러 메시지를 상수로 분리하기
    @RequestMapping(uri = "퇴출등록")
    public void addOutPlayer(Map<String, String> paramMap) throws SQLException {
        if (paramMap == null) {
            System.out.println(ErrorMessage.NOT_FOUND_PARAMETER);
            return;
        }

        if (!(paramMap.containsKey("playerId") && paramMap.containsKey("reason"))) {
            System.out.println(ErrorMessage.INVALID_PARAMETER);
            return;
        }

        int playerId;
        try {
            playerId = Integer.parseInt(paramMap.get("playerId"));
        } catch (NumberFormatException e) {
            System.out.println(ErrorMessage.INVALID_NUMBER_FORMAT);
            return;
        }
        Reason reason = Reason.findByName(paramMap.get("reason"));

        QueryExecutionStatus result = outPlayerService.addOutPlayer(playerId, reason);
        System.out.println(result);
    }
}
