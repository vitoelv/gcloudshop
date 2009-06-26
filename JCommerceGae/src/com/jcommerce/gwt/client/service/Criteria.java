/**
* Author: Bob Chen
*/

package com.jcommerce.gwt.client.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Criteria implements IsSerializable {
    private List<Condition> conditions = new ArrayList<Condition>();

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }
    
    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    public void removeCondition(Condition condition) {
        conditions.remove(condition);
    }

    public void removeAllConditions() {
        conditions.clear();
    }
}
