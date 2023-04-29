FROM node:18.15.0-alpine as build-stage
WORKDIR /app
COPY ./ ./
RUN npm config set registry https://registry.npmmirror.com/
RUN npm install
RUN npm run build

FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist/ /usr/share/nginx/html/
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf
