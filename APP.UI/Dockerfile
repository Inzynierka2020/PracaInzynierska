### STAGE 1: Build ###
FROM node:12.7-alpine AS build

ARG PROFILE="prod"

WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN echo ${PROFILE}
RUN npm run "build ${PROFILE}"

### STAGE 2: Run ###
FROM nginx:1.17.1-alpine
COPY --from=build /usr/src/app/dist/App /usr/share/nginx/html