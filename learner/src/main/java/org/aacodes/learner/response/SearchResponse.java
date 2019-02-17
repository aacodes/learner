package org.aacodes.learner.response;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse<T> {

    private List<T> object = new ArrayList<>();

    public List<T> getResponse() {
        return this.object;
    }

    public void setResponse(List<T> obj) {
        this.object = obj;
    }
}
