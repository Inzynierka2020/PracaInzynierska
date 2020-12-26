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
