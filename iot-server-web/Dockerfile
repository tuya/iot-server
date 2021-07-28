FROM iotportal/iot-suite-base

COPY /bin/nginx.conf /etc/nginx/nginx.conf
COPY /bin/dist /www/

ARG source_path=/target
ARG target_path=/data/iot-server

RUN bash -c 'mkdir -p ${target_path}/{lib,logs,bin}'

COPY ${source_path}/*.jar ${target_path}/lib/
COPY /bin/endpoint.sh ${target_path}/bin/

STOPSIGNAL SIGTERM

WORKDIR ${target_path}/bin/

EXPOSE 80
STOPSIGNAL SIGTERM

CMD bash -C endpoint.sh