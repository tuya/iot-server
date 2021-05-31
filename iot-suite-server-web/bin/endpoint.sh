#!/bin/bash

CURRENT_DIR=`dirname $0`
API_HOME=`cd "$CURRENT_DIR/.." >/dev/null; pwd`
Jar=`ls $API_HOME/lib/*.jar`
RETVAL="0"
LOG="api_stdout.log"

# run redis
nohup /usr/bin/redis-server /etc/redis.conf &
# run nginx
cd /usr/bin
nginx

base_param="-Dconnector.ak="$AK" -Dconnector.sk="$SK" -Dproject.code="$PC
if [ "$REGION" != "" ]
then
  base_param=${base_param}" -Dconnector.region"=$REGION
fi

if [ "$SMS_TEMPLATEID_CN" != "" ]
then
  base_param=${base_param}" -Dcaptcha.notice.resetPassword.sms.templateId.cn"=$SMS_TEMPLATEID_CN
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

echo $base_param

# run java application
cd $API_HOME
java $base_param \
-jar $Jar >> $API_HOME/logs/$LOG 2>&1


