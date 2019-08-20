FROM mhart/alpine-node:latest

MAINTAINER Your Name <you@example.com>

# Create app directory
RUN mkdir -p /mongotest
WORKDIR /mongotest

# Install app dependencies
COPY package.json /mongotest
RUN npm install pm2 -g
RUN npm install

# Bundle app source
COPY target/release/mongotest.js /mongotest/mongotest.js
COPY public /mongotest/public

ENV HOST 0.0.0.0

EXPOSE 3000
CMD [ "pm2-docker", "/mongotest/mongotest.js" ]
