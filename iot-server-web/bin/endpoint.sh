#!/bin/bash

CURRENT_DIR=`dirname $0`
API_HOME=`cd "$CURRENT_DIR/.." >/dev/null; pwd`
Jar=`ls $API_HOME/lib/iot-server-web-*[0-9].jar`
RETVAL="0"
LOG="api_stdout.log"

# run redis
nohup /usr/bin/redis-server /etc/redis.conf &
# run nginx
cd /usr/sbin
nginx

base_param="-Dconnector.ak="$AK" -Dconnector.sk="$SK" -Dproject.code="$PC
if [ "$REGION" != "" ]
then
  base_param=${base_param}" -Dconnector.region"=$REGION
fi

base_param=${base_param}" -Dconnector.entry=Docker"

if [ "$SMS_TEMPLATEID_CN" != "" ]
then
  base_param=${base_param}" -Dcaptcha.notice.resetPassword.sms.templateId.cn"=$SMS_TEMPLATEID_CN
fi

if [ "$IN18_HTTP_HEADER_NAME" != "" ]
then
  base_param=${base_param}" -Din18.http.header.name"=$IN18_HTTP_HEADER_NAME
else
    base_param=${base_param}" -Din18.http.header.name=Accept-Language"
fi

if [ "$SMS_TEMPLATEID_EN" != "" ]
then
  base_param=${base_param}" -Dcaptcha.notice.resetPassword.sms.templateId.en"=$SMS_TEMPLATEID_EN
fi

if [ "$MAIL_TEMPLATEID_CN" != "" ]
then
  base_param=${base_param}" -Dcaptcha.notice.resetPassword.mail.templateId.cn"=$MAIL_TEMPLATEID_CN
fi

if [ "$MAIL_TEMPLATEID_EN" != "" ]
then
  base_param=${base_param}" -Dcaptcha.notice.resetPassword.mail.templateId.en"=$MAIL_TEMPLATEID_EN
fi

if [ "$PERMISSION_SPACE_OWNER" != "" ]
then
  base_param=${base_param}" -Dproject.permissionSpaceOwner"=$PERMISSION_SPACE_OWNER
else
    base_param=${base_param}" -Dproject.permissionSpaceOwner=tuya-iot"
fi

if [ "$PERMISSION_SPACE_ID" != "" ]
then
  base_param=${base_param}" -Dproject.permission-space-id"=$PERMISSION_SPACE_ID
fi

if [ "$PERMISSION_SPACE_GROUP" != "" ]
then
  base_param=${base_param}" -Dproject.permission-group"=$PERMISSION_SPACE_GROUP
else
    base_param=${base_param}" -Dproject.permission-group=tuya-iot-prod"
fi

if [ "$PERMISSION_SPACE_CODE" != "" ]
then
  base_param=${base_param}" -Dproject.permission-space-code"=$PERMISSION_SPACE_CODE
else
    base_param=${base_param}" -Dproject.permission-space-code=iot-server"
fi

if [ "$PERMISSION_AUTO_INIT" != "" ]
then
  base_param=${base_param}" -Dproject.permission-auto-init"=$PERMISSION_AUTO_INIT
else
    base_param=${base_param}" -Dproject.permission-auto-init=true"
fi

if [ "$PERMISSION_SPACE_AUTHENTICATION" != "" ]
then
  base_param=${base_param}" -Dproject.permission-space-authentication"=$PERMISSION_SPACE_AUTHENTICATION
else
    base_param=${base_param}" -Dproject.permission-space-authentication=2"
fi

if [ "$SERVER_SESSION_TIMEOUT" != "" ]
then
  base_param=${base_param}" -Dserver.session.timeout"=$SERVER_SESSION_TIMEOUT
else
    base_param=${base_param}" -Dserver.session.timeout=7200"
fi

if [ "$ASSET_AUTH_SIZE" != "" ]
then
  base_param=${base_param}" -Dasset.auth.size"=$ASSET_AUTH_SIZE
else
    base_param=${base_param}" -Dasset.auth.size=50"
fi


echo $base_param

# run java application
cd $API_HOME
java $base_param \
-jar $Jar >> $API_HOME/logs/$LOG 2>&1


