### STAGE 1: Build ###
FROM node:12.7-alpine AS build

ARG PROFILE="prod"

WORKDIR /usr/src/app
COPY APP.UI/package.json ./
RUN npm install
COPY APP.UI/. .
RUN echo ${PROFILE}
RUN npm run "build-${PROFILE}"

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY --from=build /usr/src/app/dist/App /usr/share/nginx/html

COPY nginx/localhost/nginx.conf /etc/nginx/nginx.conf
COPY nginx/localhost/mime.types /etc/nginx/mime.types
COPY nginx/localhost/localhost.crt /etc/ssl/certs/localhost.crt
COPY nginx/localhost/localhost.key /etc/ssl/private/localhost.key