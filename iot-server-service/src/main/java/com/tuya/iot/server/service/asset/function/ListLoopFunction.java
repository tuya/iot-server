package com.tuya.iot.server.service.asset.function;

import java.util.List;

/**
 * @author mickey
 * @date 2021年06月21日 21:27
 */
public interface ListLoopFunction<T> {

   Boolean doListLoop(List<T> ids);
}
