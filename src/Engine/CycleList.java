package Engine;

import java.util.ArrayList;
import java.util.List;

public class CycleList {

    private List<String> list;
    private int index;

    public CycleList() {
        list = new ArrayList<>();
    }

    public CycleList(List<String> list) {
        this.list = list;
        index = 0;
    }
    public String getCurrent() throws IndexOutOfBoundsException
    {
        return list.get(index);
    }

    public String getNext() {
        if (list.size() == 0) return null;
        String res = list.get(index);
        index = (index+1 >= list.size()) ? 0 : index + 1;
        return res;
    }

    public String getPrevious() {
        if (list.size() == 0) return null;
        String res = list.get(index);
        index = (index-1 < 0) ? list.size() - 1 : index - 1;
        return res;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
