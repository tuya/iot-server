import com.tuya.iot.suite.ability.idaas.ability.SpaceAbility
import com.tuya.iot.suite.ability.idaas.model.SpaceApplyReq
import spock.lang.Specification

/**
 * @description
 * @author benguan.zhou@tuya.com
 * @date 2021/06/02
 */
class MyFirstSpec extends Specification {
    void "我的第一个测试"() {
        given: "没有注册权限空间"
        and: "执行权限空间注册"
        SpaceAbility spaceAbility = Mock(SpaceAbility)
        spaceAbility.applySpace(_) >> 10001
        when:
        def spaceId = spaceAbility.applySpace(SpaceApplyReq.builder()
                .spaceGroup("myGroup")
                .spaceCode("1000").build()
        )
        then:
        spaceId == 10001
    }
}
