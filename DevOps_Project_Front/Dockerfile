# Utilisation directe de Nginx
FROM nginx:alpine
# Copier les fichiers compilés
ARG BUILD_PATH

RUN rm /etc/nginx/conf.d/default.conf
COPY DevOps_Project_Front/nginx.conf /etc/nginx/conf.d

COPY ${BUILD_PATH} /usr/share/nginx/html
EXPOSE 80