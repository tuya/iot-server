import com.tuya.connector.open.common.constant.TuyaRegion
import org.apache.pulsar.shade.org.apache.http.client.methods.CloseableHttpResponse
import org.apache.pulsar.shade.org.apache.http.client.methods.HttpGet
import org.apache.pulsar.shade.org.apache.http.impl.client.HttpClients
import org.apache.shiro.codec.Hex

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * 用来生成研测平台接口调用测试需要的http header
 * */
def timestamp = System.currentTimeMillis()
def accessToken = "22945f0da866c0cbf9c7349cb16b03e8"

def ak = "n9m754suanesmntqze4f"//"f5ccfrwnaleow1n717on"//
def sk = "8fbdbfd283834f4098a048e0effa5b83"//"d2c53c63334441faa763de3efaa50038"//
def sign = hmacsha256(ak+timestamp,sk).toUpperCase()

def headers = """{
"client_id": "$ak",
"access_token": "$accessToken",
"sign": "$sign",
"t": "$timestamp",
"sign_method":"HMAC-SHA256"
}"""

println headers

def hmacsha256(String data,String secret){
    Mac mac = Mac.getInstance("HmacSHA256")
    SecretKeySpec secretKey = new SecretKeySpec(secret.bytes, "HmacSHA256")
    mac.init(secretKey)
    //Base64.encoder.encodeToString(mac.doFinal(data.getBytes("utf-8")))
    Hex.encodeToString(mac.doFinal(data.getBytes("utf-8")))
}
def client = HttpClients.createDefault()
def apiUrl4getToken = "/v1.0/token?grant_type=1"
def apiUrl4querySpace = "/v1.0/iot-03/idaas/spaces?spaceGroup=123&spaceCode=111"
def get = new HttpGet(TuyaRegion.CN.apiUrl+apiUrl4querySpace)
get.addHeader("client_id",ak)
get.addHeader("access_token",accessToken)
get.addHeader("t",timestamp.toString())
get.addHeader("sign_method","HMAC-SHA256")
get.addHeader("sign",sign)


CloseableHttpResponse resp = client.execute(get)
