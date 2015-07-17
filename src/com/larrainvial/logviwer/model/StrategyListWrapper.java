package com.larrainvial.logviwer.model;

import com.larrainvial.logviwer.vo.StrategyVO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "StrategyVO")

public class StrategyListWrapper {

    private List<StrategyVO> strategy;

    @XmlElement(name = "person")
    public List<StrategyVO> getPersons() {
        return strategy;
    }

    public void setPersons(List<StrategyVO> persons) {
        this.strategy = persons;
    }
}


