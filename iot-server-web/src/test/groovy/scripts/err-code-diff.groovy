import com.tuya.iot.server.core.constant.ErrorCode

/**
 * compare ErrorCode and properties file,
 * output key-values where properties file missed.
 * */
def errMap = ErrorCode.values().collectEntries{
    [(it.code):it]
}
def bundle = ResourceBundle.getBundle("messages",Locale.US)
errMap.removeAll{
    it.key in bundle.keySet()
}

println errMap.collect{
    "${it.key}=${it.value.name()}".toLowerCase()
}.join('\n')

println errMap.collect{
    "${it.key}=${it.value.msg}"
}.join('\n')

