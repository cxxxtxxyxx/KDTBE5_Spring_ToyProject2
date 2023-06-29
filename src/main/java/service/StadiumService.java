package service;

import model.stadium.Stadium;
import model.stadium.StadiumDao;
import util.QueryExecutionStatus;

import java.util.List;

public class StadiumService {

    private final static StadiumService stadiumService = new StadiumService();

    private final StadiumDao stadiumDao = new StadiumDao();

    public static StadiumService getInstance() {
        return stadiumService;
    }

    private StadiumService() {
    }

    public QueryExecutionStatus addStadium(String name) {
        return stadiumDao.add(name);
    }

    public List<Stadium> findAllStadium() {
        return stadiumDao.findAll();
    }
}
