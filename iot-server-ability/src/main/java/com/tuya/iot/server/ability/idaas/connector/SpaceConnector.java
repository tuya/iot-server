package com.tuya.iot.server.ability.idaas.connector;

import com.tuya.connector.api.annotations.Body;
import com.tuya.connector.api.annotations.GET;
import com.tuya.connector.api.annotations.POST;
import com.tuya.connector.api.annotations.Query;
import com.tuya.iot.server.ability.idaas.model.IdaasSpace;
import com.tuya.iot.server.ability.idaas.ability.SpaceAbility;
import com.tuya.iot.server.ability.idaas.model.SpaceApplyReq;

/**
 *
 * 关于 开发者、项目、应用、权限空间
 * （by benguan.zhou，如有理解偏差，可以一起整理。目前在同事"叔大"的帮助下，加上自己的理解整理如下）
 * 资产、设备、用户等（称为非权限能力） 和 idaas用户、角色、权限等（称为权限能力） 都是由openapi提供。
 *
 * iot开发者、项目、应用：
 * 一个iot开发者，可以拥有多个项目（1对n的关系）。
 * 一个项目下，可以有多个应用（1对n的关系，比如云应用，app应用，微信小程序应用）。
 * 每个iot开发者，由一个iotuid唯一标识。
 * 每个项目，由一个projectCode唯一标识。
 * 每个应用，由一组ak、sk唯一标识（事实上由ak唯一标识，sk为访问密钥）。
 *
 * 权限空间，分为三类作用域。分别是iotuid、projectCode、clientId（即应用的ak）。
 * 它们分别作用于iot开发者，项目，应用。
 * 域的类型，通过传authentication来区分（1，2，3分别对应iotuid、projectCode、clientId）。
 * 一个权限空间，由一个三元组唯一标识（spaceGroup、spaceOwner、spaceCode），由调用者传入。
 * 一个权限空间一旦生成，其域的类型（调用者传入）和spaceId（由openapi生成）都已经固定下来了，不能修改。
 *
 * iotuid域空间下的数据（比如创建的权限、用户、角色等），可以被对应的iot开发者下所有项目下所有应用访问。
 * projectCode域空间下的数据，可以被对应的项目下所有应用访问。
 * clientid域空间下的数据，只能被对应的那一个应用访问。
 *
 * 类比：
 * iotuid域比作小区，project域家庭，clientid域比作人，空间中的数据比作物品。
 * 某个物品如果属于整个小区，那么小区中所有家庭的所有人都可以使用它。
 * 某个物品如果属于某个家庭，那么只有该家庭的人可以使用它。
 * 某个物品如果属于某个人，那么只有这个人可以使用它。
 * （假设物品不能外借）
 *
 * 一般情况下，建议申请projectCode域的空间。
 * 这样可以达到一个iot开发者下不同项目直接的空间隔离，同一个项目下不同端的应用直接共用空间的效果。
 *
 * 改应用配置（ak、sk），
 * 项目配置（projectCode），
 * 或者改权限空间配置（spaceGroup、spaceOwner、spaceCode、spaceId、authentication）时，
 * 一定要注意分析它们之间的关联关系。否则容易出现没有权限访问空间的情况。
 *
 * @author benguan.zhou@tuya.com
 * @description
 * @date 2021/05/31
 */
public interface SpaceConnector extends SpaceAbility {

    @POST("/v1.0/iot-03/idaas/spaces")
    @Override
    String applySpace(@Body SpaceApplyReq spaceApplyRequest);

    @GET("/v1.0/iot-03/idaas/spaces")
    @Override
    IdaasSpace querySpace(@Query("spaceGroup") String spaceGroup,
                          @Query("spaceCode") String spaceCode);
}
