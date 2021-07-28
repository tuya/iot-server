package com.tuya.iot.server.service.util;

import com.tuya.iot.server.service.asset.function.ListLoopFunction;

import java.util.List;

/**
 * @author mickey
 * @date 2021年06月21日 21:25
 */
public class PageHelper {

    public static <T> boolean doListBySize(int size, List<T> ids, ListLoopFunction function) {
        boolean res = true;
        int start = 0;
        int end = 0;
        List<T> tempList;
        do{
            start = end;
            end += size;
            if (start >= ids.size()) {
                break;
            }
            if (end > ids.size()) {
                end = ids.size();
            }
            tempList= ids.subList(start, end);
            res = res && function.doListLoop(tempList);
        } while (tempList.size() > 0);
        return res;
    }
}

