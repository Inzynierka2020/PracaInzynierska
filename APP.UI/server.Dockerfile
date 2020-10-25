### STAGE 1: Build ###
FROM node:12.7-alpine AS build

ARG PROFILE="prod"
WORKDIR /usr/src/app
COPY APP.UI/. .
RUN npm install
RUN echo ${PROFILE}
RUN npm run build-prod

### STAGE 2: Run ###
FROM nginx:1.17.1
RUN apt-get update
RUN apt-get install nano
RUN apt-get install -y certbot python-certbot-nginx

COPY --from=build /usr/src/app/dist/App /usr/share/nginx/html

COPY nginx/server/nginx.conf /etc/nginx/nginx.conf
COPY nginx/server/mime.types /etc/nginx/mime.types
COPY nginx/server/letsencrypt/live/walltedo.com/fullchain.pem /etc/letsencrypt/live/walltedo.com/fullchain.pem
COPY nginx/server/letsencrypt/live/walltedo.com/privkey.pem /etc/letsencrypt/live/walltedo.com/privkey.pem
COPY nginx/server/letsencrypt/options-ssl-nginx.conf /etc/letsencrypt/options-ssl-nginx.conf
COPY nginx/server/letsencrypt/ssl-dhparams.pem /etc/letsencrypt/ssl-dhparams.pem
